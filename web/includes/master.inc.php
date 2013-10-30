<?PHP
    // Application flag
    define('CS314', true);

    // https://twitter.com/#!/marcoarment/status/59089853433921537
    date_default_timezone_set('America/Denver');

    // Determine our absolute document root
    define('DOC_ROOT', realpath(dirname(__FILE__) . '/../'));

    // Global include files
    require DOC_ROOT . '/includes/functions.inc.php';  // spl_autoload_register() is contained in this file
    require DOC_ROOT . '/includes/class.objects.php';

    // Fix magic quotes
    if(get_magic_quotes_gpc())
    {
        $_POST    = fix_slashes($_POST);
        $_GET     = fix_slashes($_GET);
        $_REQUEST = fix_slashes($_REQUEST);
        $_COOKIE  = fix_slashes($_COOKIE);
    }

    // Load our config settings
    $Config = Config::getConfig();

    // Initialize our session
    session_name('cs314_pa2');
    session_start();