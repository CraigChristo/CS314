<?PHP
  class Form
  {
    public $name;
    public $id;
    public $action;
    public $method;
    public $sections;

    public function __construct($name = '', $listOfSections = null, $action = '', $method = 'post')
    {
        $this->name = $name;
        $this->id = slugify($name);
        $this->action = $action;
        $this->method = $method;
        $this->sections = array();
        $this->script = '';

        if (is_array($listOfSections))
        {
            foreach($listOfSections as $sec)
                $this->sections[$sec] = new FormSection($sec);
        }
        $this->sections['default'] = new FormSection('default');
    }

    public function __get($key)
    {
        if(array_key_exists($key, $this->sections))
            return $this->sections[$key];

        if((substr($key, 0, 2) == '__') && array_key_exists(substr($key, 2), $this->sections))
            return htmlspecialchars($this->sections[substr($key, 2)]);

        return $this->sections['default'];
    }

    public function __set($key, $value)
    {
        if(array_key_exists($key, $this->sections))
            $this->sections[$key] = $value;

        return $value; // Seriously.
    } 

    public function __isset($key)
    {
        return array_key_exists($key, $this->sections);
    }

    public function __unset($key)
    {
        unset($this->sections[$key]);
    }

    public function printHTML()
    {
        echo "<form id=\"$this->id\" action=\"$this->action\" method=\"$this->method\">\n";
        foreach ($this->sections as $sec)
        {
            $sec->printHTML();
        }
        echo "</form>\n";
    }

    public function printScript()
    {
        foreach ($this->sections as $sec)
        {
            $sec->printScript();
        }
    }
  }

  class FormSection
  {
    public $name;
    private $items;
    public $print_labels;

    public function __construct($name = '') {
        $this->name = $name;
        $this->items = array();
        $this->print_labels = false;
    }

    public function printHTML()
    {
        if (!empty($this->items))
        {
            echo "<div id=\"$this->name\">\n";
            echo "<span class=\"formSectionName\">$this->name</span>\n";
            echo "<div class=\"formSectionControls\">";
            foreach($this->items as $i)
            {
                if ($this->print_labels)
                    echo "<div><label for=\"$i->name\">$i->name</label>";
                $i->printHTML();
                if ($this->print_labels)
                    echo "</div>";
            }
            echo "</div>\n</div>\n";
        }
    }

    public function printScript()
    {                           
        $empty = true;
        foreach($this->items as $i)
        {
            $script = $i->getScript();
            if (isset($script))
            {
              if ($empty) 
              {
                $empty = false;
                echo "$(function() {\n";
              }
              echo "\t" . $script . "\n\n";  
            }
        }
        if (!$empty) echo "});\n";
    }

    public function addText($name = '', $text = '')
    {
        $this->items[] = new Text($name, $text);
    }

    public function addButton($name = '', $options = array(), $onClick = null)
    {
        $this->items[] = new Button($name, $options, $onClick);
    }

    public function addInput($name = '', $options = array())
    {
        $this->items[] = new Input($name, $options);
    }

    public function addSlider($name, $options = array())
    {
        $this->items[] = new Slider($name, $options);
    }

    public function addSelect($name, $options = null, $onChange = null)
    {
        $this->items[] = new Select($name, $options, $onChange);
    }

    public function addRadio($name, $options = null, $onChange = null)
    {
        $this->items[] = new Radio($name, $options, $onChange);
    }
  }

  class formable
  {
    public $name;
    public $id;
    public $options;

    public function formable($name = null, $options = array(), $defaults = array())
    {
        $this->name = $name;
        $this->id = slugify($name);
        $this->options = $options;
        $this->setDefaults($defaults);
    }

    public function __get($key)
    {
        if(array_key_exists($key, $this->options))
            return $this->options[$key];

        if((substr($key, 0, 2) == '__') && array_key_exists(substr($key, 2), $this->options))
            return htmlspecialchars($this->options[substr($key, 2)]);

        return null;
    }

    public function __set($key, $value)
    {
        if(array_key_exists($key, $this->options))
            $this->options[$key] = $value;

        return $value; // Seriously.
    }

    protected function setDefaults(&$defaults = array())
    {
        foreach($defaults as $k=>$d)
        {
            if (!array_key_exists($k, $this->options))
                $this->options[$k] = $d;
        }
    }

    public function printHTML(){
        return null;
    }
    public function getScript(){
        return null;
    }
    public function getListeners(){
        return null;
    }
  }

  class Text extends formable
  {

    public function __construct($name = null, $textOrOptions = '')
    {
        if (is_string($textOrOptions))
        {
            $text = $textOrOptions;
            $textOrOptions = array('text'=>$text);
        }
        parent::__construct($name, $textOrOptions);
    }

    public function printHTML()
    {
        echo $this->text;
    }
  }

  class Input extends formable
  {
    static $defaults = array(
                            'type'=>'text',
                            'value'=>'',
                            'readOnly'=>'false'
                        );

    public function __construct($name = null, $options = array())
    {
        parent::__construct($name, $options, self::$defaults);
    }

    public function printHTML()
    {
        echo "<input " . 
            (isset($this->id) ? "name=\"$this->id\" id=\"$this->id\"" : '') .
            " type=\"$this->type\" value=\"$this->value\"" .
            ($this->readOnly ? 'readonly' : '') .
            ">";
    }
  }

  class Button extends Input
  {
    public $onClick;

    public function __construct($name, $options = array(), $onClick = null)
    {
        $this->onClick = $onClick;
        $options['type'] = 'submit';
        parent::__construct($name, $options);
    }

    public function getScript()
    {
        return $this->onClick;
    }
  }

  class Slider extends formable
  {
    static $defaults = array(
                                    'min'=>0,
                                    'max'=>10,
                                    'value'=>5,
                                    'step'=>'any',
                                    'ticked'=>false,
                                    'tickPercent'=>10,
                                    'tickLabel'=>false
                                );

    function __construct($name=null, $options=array())
    {
        parent::__construct($name, $options, self::$defaults);
    }

    function printHTML()
    {
        // $iValue = ($this->inputScience) ? toScience($this->value) : $this->value; 
        echo "<input type=\"number\" name=\"$this->id\" id=\"$this->id\" value=\"$this->value\" min=\"$this->min\" max=\"$this->max\" step=\"any\">";
        echo $this->unit . "\n";
        echo "<div id=\"{$this->id}Slider\" class=\"slider\"></div>\n";
    }

    function getScript()
    {
        $step = ($this->step == 'any') ? 1 : $this->step;

        if ($this->ticked)
        {
            $interval;
            if ($this->tickTens)
            {
                //logrithmic resolution
                $num = is_numeric($this->tickTens) ? $this->tickTens : 0;
                $interval = round($this->max-$this->min);
                $num = sigFigs($interval)-$num;
                $interval = pow(10,$num);

            }
            else
            {
                $interval = (round($this->max-$this->min)*$this->tickPercent)/ 100;
                $interval = round($interval, -sigFigs($interval));
            }
            return "
                $( '#{$this->id}Slider' ).labeledslider({ 
                    min: $this->min,
                    max: $this->max,
                    value: $this->value,
                    step: $step,
                    tickInterval: $interval,
                    change: function(event, ui) {
                        $('input#$this->id').val(ui.value);
                    } 
                });
                $( '$this->id' ).change(function() {
                    $( '#{$this->id}Slider' ).slider( 'value', this.value );
                });";
        }
        else
        {
            return "
                $( '#{$this->id}Slider' ).slider({ 
                    min: $this->min,
                    max: $this->max,
                    value: $this->value,
                    step: $step,
                    change: function(event, ui) {
                        $('input#$this->id').val(ui.value);
                    } 
                });
                $( '$this->id' ).change(function() {
                    $( '#{$this->id}Slider' ).slider( 'value', this.value );
                });";
        }
    }
  }

  class Select extends formable
  {
    public $items;

    static $defaults = array(
                                    'selected'=>false,
                                    'hasDefault'=>true,
                                    'onChange'=>null
                                );

    public function __construct($name = null, $options = array(), $items = array())
    {
        $this->items = $items;

        parent::__construct($name, $options, self::$defaults);
    }

    public function printHTML()
    {
        echo "<select name=\"$this->id\" id=\"$this->id\">\n";

        //Print -- option
        if ($this->hasDefault) $this->printOption();
        
        foreach ($this->items as $key=>$name)
        {
            $select = ($key == $this->selected) ? true : false;
            $this->printOption($key, $name, $select);
        }
        echo "</select>";
    }

    private function printOption($value = 0, $name = '--', $selected = false)
    {
        echo "<option value=\"$value\" " . ($selected ? 'selected' : '') . ">$name</option>\n";
    }

    public function getScript()
    {
        if (isset($this->onChange))
            return "$('$this->id').change(function() {\n
                        $this->onChange\n
                    });";
        return null;
    }
  }

  class Radio extends formable
  {
    public $items;

    static $defaults = array(
                                'selected'=>false,
                                'onChange'=>null
                            );

    public function __construct($name = null, $options = array(), $items = array())
    {
        $this->items = $items;

        parent::__construct($name, $options, self::$defaults);
    }

    public function printHTML()
    {
        foreach ($this->items as $key=>$name)
        {
            $select = ($key == $this->selected) ? true : false;
            $this->printRadio($this->id, $key, $name, $select);
        }
        echo "</select>";
    }

    private function printRadio($name, $value, $display, $selected = false)
    {
        echo "<input type=\"radio\" name=\"$name\" value=\"$value\" " . ($selected ? 'checked' : '') . " />$display\n";
    }

    public function getScript()
    {
        if (isset($this->onChange))
            return "$('$this->id').change(function() {\n
                        $this->onChange\n
                    });";
        return null;
    }
  }