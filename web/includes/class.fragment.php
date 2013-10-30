<?PHP
    //Autoload all class.*.fragment.php
    spl_autoload_register('fragment_autoload');
    function fragment_autoload($class_name)
    {
        $filename = DOC_ROOT . '/fragments/fragment.' . strtolower($class_name) . '.php';
        if(file_exists($filename))
            require $filename;
    }

    class Fragment {
      private static $root = DOC_ROOT;

      private function __construct() {}

      public static function print()
      {
          echo "";
      }
    }