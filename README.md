# **DJXp**

### Function
A crude application for one to have the **DJ experience**. The application allows one to create song lists and save 
them as sets to be loaded & played whenever. The application lets users edit set content and sets themselves. 
Various current and next song information is displayed to the user as well as set information. Users are able to 
choose, then  view and listen to their sets in real-time. Various useful DJ & producer tools are available to the 
user to have control over the set and perform in real-time. For a more detailed overview of the program's functions, 
please refer to the "User Stories" Section below.


### Audience
**DJ**s & **Producer**s would be the main target audience, while it also contains features useful for the average joe
(though there are other programs better suited for that).


### Value
I'm big into multimedia. I do graphic design, edit video, and of course, **I produce music**. So why not combine 
(at least) one of those with another passion—programming. I'm also only into the producing side of the industry, with 
a history in performance. I've never delved into the realm of DJ-ing even though producers are mostly DJs and DJs are 
mostly producers; so this is a good opportunity for me to get into it.


## User Stories
- As a user, I want to be able to add and name a set
- As a user, I want to be able to mark a set's overall theme/genre
- As a user, I want to be able to delete an existing set
- As a user, I want to be able to get the set length in # of songs
- As a user, I want to be able to get the set length in time (HH:MM:SS)
- As a user, I want to be able to look through and view a song from the local database
- As a user, I want to be able to add a song to a set
- As a user, I want to be able to remove a song from a set
- As a user, I want to be able to move a song's position in a set
- As a user, I want to be able to play a set (view & listen)
- As a user, I want to be able to get the current song's BPM
- As a user, I want to be able to get the next song's BPM
- As a user, I want to be able to get the current song's Key
- As a user, I want to be able to get the next song's Key
- As a user, I want to be able to get the current song's Title
- As a user, I want to be able to get the next song's Title
- As a user, I want to be able to get the current song's Contributing Artist(s)
- As a user, I want to be able to get the next song's Contributing Artist(s)
- As a user, I want to be able to get the current song's genre
- As a user, I want to be able to get the next song's genre
- As a user, I want to be able to edit the speed of the current song in real-time
- As a user, I want to be able to edit the speed of the next song in real-time
- As a user, I want to be able to edit the gain of the current song in real-time
- As a user, I want to be able to edit the gain of the next song in real-time
- As a user, I want to be able to skip to the next song early/cut the current song early
- As a user, I want to be able to load SFX & Ambience audio files in
- As a user, I want to be able to use key binds to play SFX & Ambience in real-time during a set
- As a user, I want to be able to add a song (audio file, title, artist, rest of song data) to the local database
- As a user, I want the option to save my sets to file
- As a user, I want the option to load my sets from file

# Instructions for Grader
- You can add sets to the application by clicking on the up arrow next to SETS and entering in appropriate data
- You can add songs to a set by (after selecting a set (top bar)) clicking on the up arrow next to SONGS and choosing
- You can locate my visual component by the logo's in the top left. View sets/songs pops up tables.
- You can save the state of my application by clicking on the floppy disc icon
- You can reload the state of my application by clicking on the load icon

# Phase 4: Task 2
Wed Apr 12 23:57:25 PDT 2023
Made new set: Daniel Set

Wed Apr 12 23:57:32 PDT 2023
Added Used To Love to Daniel Set

Wed Apr 12 23:57:38 PDT 2023
Added Why Did You Run? (JILOON Remix) to Daniel Set

Wed Apr 12 23:57:43 PDT 2023
Added Think About You | Nobody (JILOON Remix) to Daniel Set

Wed Apr 12 23:57:49 PDT 2023
Added Freak | Closer (JILOON Remix) to Daniel Set

Wed Apr 12 23:57:55 PDT 2023
Added I'm Gonna Love Ya (JILOON Remix) to Daniel Set

Wed Apr 12 23:58:02 PDT 2023
Added SOS ft. Aloe Blacc (JILOON Remix) to Daniel Set

Wed Apr 12 23:58:08 PDT 2023
Added Nothing Good Comes Easy (JILOON Remix) to Daniel Set

Wed Apr 12 23:58:38 PDT 2023
Playing audio

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 1.0

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.98

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.97

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.95

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.94

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.93

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.91

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.9

Wed Apr 12 23:59:04 PDT 2023
Set the current volume to 0.89

# Phase 4: Task 3
Each of my classes is doing way too much. Each class is handling multiple tasks, making it cluttered and really not
straightforward. The Song class is mostly fine, although I could think of possibly splitting the MP3 parser into a
separate class and just have 1 singular universal MP3 parser instance that handles all the mp3 parsing, and then
acquire the data from the song class. The Playback class is the meat of the program, which in other words means
it handles way too much. The actual audio handler should be completely separate, while the rest should be split into
a song handler and a set handler. The GUI & ConsoleUI are both single classes which should really be split into
more bite-sized portions. It's just one big blob now while they should be split into more organized sections of the
UI. The model can also make use of more interfaces for less replicated code.

Furthermore, I feel that I misplaced some work loads. As of now, the UIs handle the user input, which is completely
right, but it then sends raw user inputted strings straight to the playback object, and the playback object converts
the user inputted strings into their appropriate objects, and then applies the respective function on that object.
There should be an intermediary class that handles the conversion from user inputted strings into the objects, then
sends those objects to have their function applied to them. It's just more straightforward and a cleaner design that
way.