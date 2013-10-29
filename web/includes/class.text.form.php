<?PHP

  class Text extends formElem
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

    public function getHTML()
    {
        return $this->text;
    }
  }