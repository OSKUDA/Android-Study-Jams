# Android-Study-Jams

MyNote

<b> Problem Statement: </b>

Students in school or college level require to keep track of multiple leads in a session. They may miss out on some important tracks and face consequences later. Nowadays students deal with multiple work at a time and require to have all important notes listed out.

<b> Proposed Solution : </b>

This project proposes a “MyNote” application to keep track of user's notes. Its features include add, store, and list out notes. Users can edit and update the note on the fly. They also have an option to mark each note with complete or pending. This allows them to track their progress. Users can also share their individual note via external application. The notes are saved in their local storage.

<img width="559" alt="sampleimages" src="https://user-images.githubusercontent.com/41532513/148671469-74b13b3e-4c93-4ff5-8928-30da1fb62abc.png">
    	  	
<b> Functionality & Concepts used : </b>

- The App has a very simple and interactive interface which helps the students add, edit, delete, and share their notes. Following are few android concepts used to achieve the functionalities in app : 
- Constraint Layout : Most of the activities in the app uses a flexible constraint layout, which is easy to handle for different screen sizes.
- Simple & Easy Views Design : Use of familiar audience EditText with hints and interactive buttons made it easier for students to add & delete notes.
- Apps also uses App Navigation to switch between different screens.
- RecyclerView : To present the list of different notes we used the efficient recyclerview.
- ViewBinding : To write concise, runtime safe code to bind view and omit usage of findViewByID method
- LiveData & Room Database : We store user's notes in local database using Room library. Changes on the database is observed using livedata. The database changes are reflected on the UI aswell. 

<b> Application Link & Future Scope : </b>

The app is currently in initial phase of development, You can access the app : https://github.com/OSKUDA/Android-Study-Jams

For now the app has basic functionality only. Later on, in upcomming sprint, we plan to add features like user accounts, cloud storage, share notes, and UI updates.

