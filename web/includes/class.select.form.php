<?PHP

  class Select extends formElem
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

    public function getHTML()
    {
        $result = "";

        $result .= "<select name=\"$this->id\" id=\"$this->id\">\n";

        //Print -- option
        if ($this->hasDefault) $result .= $this->getOption();
        
        foreach ($this->items as $key=>$name)
        {
            $select = ($key == $this->selected) ? true : false;
            $result .= $this->getOption($key, $name, $select);
        }
        $result .= "</select>";

        return $result;
    }

    private function getOption($value = 0, $name = '--', $selected = false)
    {
        return "<option value=\"$value\" " . ($selected ? 'selected' : '') . ">$name</option>\n";
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