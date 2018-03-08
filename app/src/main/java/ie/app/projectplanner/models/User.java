package ie.app.projectplanner.models;


import android.net.Uri;


/**
 * Created by michealin on 12/5/2016.
 */

public class User {

    public  String userEmail;
    public  String userName;
    public  Uri userPhoto;


    public User(String userEmail, String userName, Uri userPhoto) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhoto = userPhoto;

    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoto(Uri userPhoto) {
        this.userPhoto = userPhoto;
    }



}
