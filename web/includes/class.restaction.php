<?PHP

  class RestAction
  {
    public $response;

    // route -- the URL after our context, e.g. localhost:8080/music <-context route->users/{username}
    protected function __construct($verb = 'GET', $route = null, $requestBody = null, $requestHeader= null)
    {
      $rest = new RestRequest($verb, Config::get('restURL').$route, $requestBody);

      if (isset($requestHeader))
        foreach ($requestHeader as $key => $value)
          $rest->setExtraHeader($key, $value);

      $rest->exec();

      $this->response = json_decode($rest->getResponseBody(), true);
    }
  }