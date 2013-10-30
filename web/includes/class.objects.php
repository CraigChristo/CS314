<?PHP
  //Put REST classes here

  //Objects
  

  class User extends RestObject
  {
    public function __construct($username)
    {
      parent::__construct('username', '/users/'.$username);
    }
  }

  class Library extends RestObject
  {
    public function __construct($username, $sortBy = null, $viewername = null)
    {
      if (isset($viewer))
        parent::__construct('owner', '/users/'.$username.'/library', isset($sortBy) ? array('sortBy'=>$sortBy) : null, array('user'=>$viewername));
      else
        parent::__construct('owner', '/library', isset($sortBy) ? array('sortBy'=>$sortBy) : null, array('user'=>$username));
    }
  }


  //Actions


  class sendInvite extends RestAction
  {
    public function __construct($from, $to) {
      parent::exec('PUT', Config::get('restURL') . '/friends', array('user'=>$to), array('user'=>$from));
    }
  }

  class addSong extends RestAction
  {
    public function __construct($user, $song) {

    }
  }



