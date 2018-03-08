package ie.app.projectplanner.database;

/**
 * Created by michealin on 12/5/2016.
 */

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.internal.ImageRequest;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Downloader;

import ie.app.projectplanner.models.Project;
import ie.app.projectplanner.models.User;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper{
    DatabaseReference db;
    Boolean saved;
    public List<User> users;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }


    public Boolean save(Project Project)
    {
        if(Project==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.push().setValue(Project);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }



    public Boolean delete(Project Project)
    {
        if(Project==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.push().setValue(Project);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }


}