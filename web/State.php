<?PHP

  $require 'includes/master.inc.php';

  class State {
    public static $user;

    public static function update() {
      if (array_key_exists('user', $_SESSION)) {
        $username = $_SESSION['user'];
        self::$user = new User($username);
      }

      if (self::$user == null)
        self::redirect_login();
    }

    public static function redirect_login() {
      redirect('login.php');
    }
  }