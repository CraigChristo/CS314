<?PHP

  require 'includes/master.inc.php';

  $error = "";

  if (array_key_exists('username', $_POST) && array_key_exists('password', $_POST)) {
      $user = new User($_POST['username']);

      if (isset($user))
        if ($user->password == $_POST['password']) {
          $_SESSION['user'] = $_POST['username'];

          redirect('index.php');
        }
      else $error = "Incorrect username or password";
  }

  $form = new Form('LogIn', array('Info', 'Submit'), $_SERVER['PHP_SELF']);

  $form->Info->add(new Input('username'));
  $form->Info->add(new Input('password'));

  $form->Submit->add(new Button('logInBtn', array('value'=>'Log In')));

  $title = 'Log in';

  include 'header.inc.php';
  echo $error . "\n"; $form->printHTML();
  include 'footer.inc.php';