# Localization-The-Daily-Path

The application build as a part of assignment 3 basically acquires and maps device locations. The application obtains the phone’s current location and displays the latitude, longitude values in a TextView. It also displays the corresponding address of the obtained location by using the Android Geocoder class. A callback method is written so as to display the location in the TextView when a new location is available. A “CHECK IN” button is added to the interface and on clicking it the current location and time are stored in SQLite database. Also, all the stored locations are shown in the list in the app. This is done by using a ListView.
The application also has an EditText where the users can add a custom name for the location when checking in by clicking the “ADD” button. These names are stores in the Database. The database is named “LocationDatabase”. The table is named “locationInfo5”. The ListView displays the latitude, longitude, time and Address. The custom Names are stored in the database. I have also displayed it for convenience by retrieving it from the Database.
There is also a code where after checking in for 5 positions of Rutgers Location and naming them, follows a red route on the map.
Further, the map is centered on the location. The map is displayed after clicking on the “VIEW MAP” button. It goes to another activity. It includes standard UI controls: zoom in/out buttons, compass and “My Location” button. All the check-in locations are shown on the map along with markers. There is an “ ADD LOCATION” button which takes the user to another activity. The user will enter a new named location and click on “ADD MARKER NAME” button. This will take the user back to the MapActivity and after pressing on any location on the map and then clicking on the marker the new named location added by the user is displayed as the marker title on the map.
For the Graduate Student Part, after installing the application on the phone, the accuracy in meters found was about 20meters. We use getAccuracy method to execute this feature. It gets the estimated accuracy of the location in meters.
