#UseCase: CreateFriendship  
**Actor**: User  

###Main flow  
    1. User Enters friend name.
    2. If friend is registered, System creates friend request
    3. Friend accepts request

###Alt. Flow
    2a. Friend doesn't exist, notify user
    3a. Friend declines request, delete request, notify user

==============================================

#UseCase: SearchLibrary  
**Actor**: User  

###Main flow  
    1. User enters Library and Song metadata.
    2. If library exists, System gets it
    3. If song is in library, system displays it to user

###Alt. Flow
    2a. Library doesn't exist, notify user
    2b. Library is private and user is not a friend, notify user
    3a. Song not in library, notify user

===========

#UseCase: BrowseFriendsLibrary  
**Actor**: User  

###Main flow  
    1. User Enters friend name.
    2. If friend is registered, System gets friend library

###Alt. Flow
    2a. Friend doesn't exist, notify user

==============================================

#UseCase: BorrowFriendSong  
**Actor**: User  

###Main flow  
    1. User Enters friend name and song
    2. If friend is registered, System gets friend library
    3. If song is in library, System gets song
    4. If song is borrowable, System transfers song

###Alt. Flow
    2a. Friend doesn't exist, notify user
    3a. Song is not in library, notify user
    4a. Song is playing, wait list request
    4b. Song is already borrowed, wait list request
    4c. Song is private, notify user, delete request

==============================================

#UseCase: AddSongToLibrary  
**Actor**: User  

###Main flow  
    1. User enters Song Metadata
    2. System creates song
    3. System adds song to library

###Alt. Flow
    2a. Song metadata is bad, notify user

==============================================

#UseCase: CreatePlaylist  
**Actor**: User  

###Main flow  
    1. User enters playlist name, and list of songs
    2. System creates playlist
    3. System adds list of songs to playlist.
    4. System adds playlist to user library

###Alt. Flow
    2a. If playlist name already exists, add list of songs to existing playlist
    3a. Song list is empty, create empty playlist
    4a. System updates existing playlist in library

================

#UseCase: ManageLibraryPrivacy  
**Actor**: User  

###Main flow  
    1. User selects public or friends-only
    2. System sets library state

###Alt. Flow
    2a. If state is identical, notify user

================ 

#UseCase: ManageSongAvailibility 
**Actor**: User  

###Main flow  
    1.  User enters Friend, borrowable, lengthOfBorrow
    2. If friend is registered, System gets friendship
    3. System sets availability of library for friendship

###Alt. Flow
    2a. Friend doesn't exist, notify user
    3a. Bad values for borrowable, lengthOfBorrow, notify user

============ 

#UseCase: ManageSongBorowability  
**Actor**: User  

###Main flow  
    1.  User enters Song name and [always, approved, none]
    2. System searches library for song, gets Song
    3. System sets Song borrowability

###Alt. Flow
    2a. Song doesn't exist, notify user
    3a. Bad value for borrowability, notify user
    3b. Song is already borrowed, revoke the song, set borrowability
