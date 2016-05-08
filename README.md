# AudioServer
Some details of the project in terms of functionalities of the player controls:

1. First run the server app - AudioServer

2. Secondly run the client app - PlayerClient

3. At the PlayerClient, enter the clip number of the clip you wish to play and 
   then click on play button.

4. To  pause/resume a clip, click on the pause or resume button however make sure that the clip number entered 
   in the edit text is present

5. Stop button does not allow the user to resume the currently playing song, it stops the song and user has to enter 
the song he wishes to play in the edit text again

6. Structure of the requests on clicking get Transactions:

Timestamp, Type of request received, Clip Number, the current state of the player when a request was received
Ex: 2016-04-09 18:00:00 Play Song #1 while Song #1 was paused
Ex: 2016-04-09 18:01:00 Play Song #6 while Song #3 was playing
