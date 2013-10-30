<?PHP

  class RestObject
  {
    public $id;
    protected $data;

    // route -- the URL after our context, e.g. localhost:8080/music <-context route->users/{username}
    //
    protected function __construct($idField = null, $route = null, $requestBody = null, $requestHeader= null)
    {
      $rest = new RestRequest('GET', Config::get('restURL').$route, $requestBody);

      if (isset($requestHeader))
        foreach ($requestHeader as $key => $value)
          $rest->setExtraHeader($key, $value);

      $rest->exec();

      $this->data = getOrElse($rest->getJson(), array());

      if (isset($idField))
        if (array_key_exists($idField, $this->data))
          $this->id = $data[$idField];
    }

    public function __get($key)
    {
      if (array_key_exists($key, $this->data))
        return $this->data[$key];

      return null;
    }

    public function __set($key, $value)
    {
      $this->data[$key] = $value;

      return $value;
    }
  }