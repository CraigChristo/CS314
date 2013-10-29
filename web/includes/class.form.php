<?PHP
    //Autoload all class.*.form.php
    spl_autoload_register('form_autoload');
    function form_autoload($class_name)
    {
        $filename = DOC_ROOT . '/includes/class.' . strtolower($class_name) . '.form.php';
        if(file_exists($filename))
            require $filename;
    }

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

                echo $i->getHTML();
                
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

    public function add($elem)
    {
        if(is_subclass_of($elem, "formElem"))
            $this->items[] = $elem;
        else
            throw new InvalidArgumentException("Form section " . $this->name . " expects formElem. Got " . gettype($elem));
    }
  }

  class formElem
  {
    public $name;
    public $id;
    public $options;

    public function formElem($name = null, $options = array(), $defaults = array())
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

    public function getHTML(){
        return null;
    }
    public function getScript(){
        return null;
    }
    public function getListeners(){
        return null;
    }
  }