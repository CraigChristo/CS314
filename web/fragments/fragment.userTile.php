<?PHP

  class UserTile extends Fragment {
    public static function print($user, $friends = false, $self = false)
    {
      $result = "

      <div class=\"user-tile\" name=\"{$user->username}Tile\">\n
        <h2>$user->username</h2>\n
        ";

      if ($friends) {
        $result .= "
          <a href=\"{$root}/library.php?friend=$user->username\">View Library</a>\n
        ";
      } else {
        $result .= "
          <a href=\"{$root}/friends.php?action=invite&user=$user->username\">Add Friend</a>\n
        ";
      } else if ($self) {
        $result .= "<a href=\"{$root}/settings.php\">Account Settings</a>\n";
        if (isset($user->social->invites))
          $result .= "<a href=\"{$root}/friends.php?view=invites\">" . sizeof($user->social->invites) . "Invites</a>\n";
      }

      $result .= "</div>";

      echo $result;
    }
  }