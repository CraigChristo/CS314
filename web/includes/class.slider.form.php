<?PHP

  class Slider extends formElem
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

    function getHTML()
    {
        $result = "";

        $result .= "<input type=\"number\" name=\"$this->id\" id=\"$this->id\" value=\"$this->value\" step=\"any\"";
        if ($this->validate)
            $result .= " min=\"$this->min\" max=\"$this->max\"";

        $result .= ">";
        $result .= $this->unit . "\n";

        $result .= "<div id=\"{$this->id}Slider\" class=\"slider\"></div>\n";

        return $result;
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