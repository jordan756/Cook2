Clone Repo with Andriod Studio:
1. Create an empty folder to clone project into.
2. In the welcome screen of Andriod Studio, press "Get from VCS" on the top right.
3. Login into your github, second tab on the left side. I logged in using the token generation method. You can try the other login method if it works.
4. Copy URL of this repository into the url box, then change the directory to the empty folder. Make sure to refresh the directory list.
5. After cloning the project you might see Gradle errors. I usually close out of Andriod Studio and open the project again to fix it.

When I first downloaded the project my emulator didn't work correctly. I fixed this by downloading a new emulator, the Pixel 3 and it worked.


Database instructions:
1. Update Android Studio to latest version 
2. Pull project
3. In Android Studio, open file tab on top left. Click on "Invalidate Caches..."
4. Check all the options
5. Click "Invalidate and Restart"
6. Make sure your emulator is a Pixel 3 or at least has Google Play.
7. Minimum Android API: 19

More installation details here: https://firebase.google.com/docs/android/setup
Database Documentation: https://firebase.google.com/docs/firestore
