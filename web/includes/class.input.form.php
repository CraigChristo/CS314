<?PHP

  class Input extends formElem
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

    public function getHTML()
    {
        return "<input " . 
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
        return "$('$this->id').onClick(function() {\n\t" .
                    $this->onClick .
                "\n});";
    }
  }