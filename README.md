## Similar Songs Recognizer



`Supported APIs:`

**Add User:**

Arguments:
- username: choose a name you like
- plan: from the following list: [FreeTrial, Fifty, Hundred, Unlimited, PerUse]

Curl Command:

curl -X POST -H "Content-type: application/json" -d "username=<your username>&plan=<plan from list>" http://localhost:12345/user/api/v0/addUser

Response:

JSON containing the user id created for you.
i.e. {"username":"elad","user_id":0}

**Get Allowed Searches Left:**

Arguments:

- user_id : as you received from the add user API

Curl Command:

curl -X POST -H "Content-type: application/json" -d "user_id=<your user id>" http://localhost:12345/enforcement/api/v0/getNumberOfAllowedResults

Response:

JSON containing the allowed searches left for the user_id account 
i.e. {"number_of_searches_left":"10","user_id":0}

**Get Similar Songs:**

Arguments:

- user_id : as you received from the add user API

Curl Command:

curl -X POST -H "Content-type: application/json" -d "user_id=<your user id>&song_name=<name of a song>" http://localhost:12345/songs/api/v0/getSimilarSongs

Response:

JSON containing the found list of songs which are similar to your song 
i.e. {"similar_songs":[{"year":1914,"artist":"Artist_8950","name":"SongLike_blue0895"},{"year":1914,"artist":"Artist_8951","name":"SongLike_blue1895"}]}

`How to run `
- compile the project (using gradle)
- run the main class: com.perfecto.assignment.Main
- run API commands as mentioned here - starting with add user

`restrictions:`

- Assuming proper behavior - not looking for non existing users/plans.
- DB simulated.
- The amount of songs you will receive as a response is randomly chosen and so are the songs.
- I did not consider in the code the time frame restrictions (no monthly restriction - but a general restriction)
- Logs were avoided 

`libs used`
- javalin - allowing the API support easily. Apache License 2.0.
- unirest - to send HTTP requests easily. MIT license.
- gson - parsing and creating JSON structures. Apache License 2.0.
- junit - for testing. Eclipse Public License 1.0
 
 