<?PHP

  class Radio extends formElem
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

    public function getHTML()
    {
        $result = "";

        foreach ($this->items as $key=>$name)
        {
            $select = ($key == $this->selected) ? true : false;
            $result .= $this->getRadio($this->id, $key, $name, $select);
        }

        return $result;
    }

    private function getRadio($name, $value, $display, $selected = false)
    {
        return "<input type=\"radio\" name=\"$name\" value=\"$value\" " . ($selected ? 'checked' : '') . " />$display\n";
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