<?PHP

class RestRequest  
{ 
    protected $verb;
    protected $url;  

    protected $requestBody;  
    protected $requestLength;  

    protected $username;  
    protected $password;  

    protected $acceptType;  

    protected $responseBody;  
    protected $responseInfo;  
  
    public function __construct($verb = 'GET', $url = null, $requestBody = null)  
    {  
        $this->url               = $url;  
        $this->verb              = $verb;  
        $this->requestBody       = $requestBody;  
        $this->requestLength     = 0;  
        $this->username          = null;  
        $this->password          = null;  
        $this->acceptType        = 'application/json';  
        $this->responseBody      = null;  
        $this->responseInfo      = null;  
  
        if($this->requestBody !== null)  
        {  
            $this->buildPostBody();  
        }  
    } 
  
    public function flush()  
    {  
        $this->requestBody       = null;  
        $this->requestLength     = 0;  
        $this->verb              = 'GET';  
        $this->responseBody      = null;  
        $this->responseInfo      = null;  
    }  
  
    public function do()  
    {  
        $curlHandle = curl_init();

        $this->setAuth($curlHandle);
        
        switch(strtoupper($this->verb)) {
            case 'GET':
                $this->executeGet($curlHandle);
                break;
            case 'POST':
                $this->executePost($curlHandle);
                break;
            case 'PUT':
                $this->executePut($curlHandle);
                break;
            case 'DELETE':
                $this->executeDelete($curlHandle);
                break;
            default:
                curl_close($curlHandle);
                throw new InvalidArgumentException('Invalid REST verb: ' . $this->verb);
        }
    }  
  
    public function buildPostBody($data = null)  
    {  
        if(!isset($data)) 
            $data = $this->requestBody;

        if(!is_array($data))
            throw new InvalidArgumentException('Invalid POST body. Expected Array.');

        $this->requestBody = http_build_query($data, '', '&');
    }  
  
    protected function executeGet($curlHandle)  
    {         
        $this->execute($curlHandle);
    }  
  
    protected function executePost($curlHandle)  
    {  
        if(!is_string($this->requestBody)) {
            $this->buildPostBody();
        }

        curl_setopt($ch, CURLOPT_POSTFIELDS, $this->requestBody);  
        curl_setopt($ch, CURLOPT_POST, 1);
    }  
  
    protected function executePut($curlHandle)  
    {  
        if(!is_string($this->requestBody))  
        {  
            $this->buildPostBody();  
        }  

        $this->requestLength = strlen($this->requestBody);  

        $fh = fopen('php://memory', 'rw');  
        fwrite($fh, $this->requestBody);  
        rewind($fh);

        curl_setopt($ch, CURLOPT_INFILE, $fh);  
        curl_setopt($ch, CURLOPT_INFILESIZE, $this->requestLength);  
        curl_setopt($ch, CURLOPT_PUT, true);  

        $this->execute($curlHandle);  

        fclose($fh);
    }  
  
    protected function executeDelete($curlHandle)  
    {  
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'DELETE'); 

        $this->execute($curlHandle);  
    }  
  
    protected function execute(&$curlHandle)  
    {  
        $this->setCurlOpts($curlHandle);

        $this->responseBody = curl_exec($curlHandle);  
        $this->responseInfo  = curl_getinfo($curlHandle);  

        curl_close($curlHandle);
    }  
  
    protected function setCurlOpts(&$curlHandle)  
    {  
        curl_setopt($curlHandle, CURLOPT_TIMEOUT, 10);
        curl_setopt($curlHandle, CURLOPT_URL, $this->url);
        curl_setopt($curlHandle, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curlHandle, CURLOPT_HTTPHEADER, array(
                        'Accept: ' . $this->acceptType
                    ));
    }  
  
    protected function setAuth(&$curlHandle)  
    {  
        if(isset($this->username) && isset($this->password)) {
            curl_setopt($curlHandle, CURLOPT_HTTPAUTH, CURLAUTH_DIGEST);
            curl_setopt($curlHandle, CURLOPT_USERPWD, $this->username . ':' . $this->password);
        }
    }

    public function getAcceptType()
    {
        return $this->acceptType;
    } 
    
    public function setAcceptType($acceptType)
    {
        $this->acceptType = $acceptType;
    } 
    
    public function getPassword()
    {
        return $this->password;
    } 
    
    public function setPassword($password)
    {
        $this->password = $password;
    } 
    
    public function getResponseBody()
    {
        return $this->responseBody;
    } 
    
    public function getResponseInfo()
    {
        return $this->responseInfo;
    } 
    
    //Return an associative array if the response is valid json
    //Return null if response isn't json
    public function getJson()
    {
        return json_decode($this->responseBody, true);
    }

    public function getUrl()
    {
        return $this->url;
    } 
    
    public function setUrl($url)
    {
        $this->url = $url;
    } 
    
    public function getUsername()
    {
        return $this->username;
    } 
    
    public function setUsername($username)
    {
        $this->username = $username;
    } 
    
    public function getVerb()
    {
        return $this->verb;
    } 
    
    public function setVerb($verb)
    {
        $this->verb = $verb;
    } 
}