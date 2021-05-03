package com.parth.clevertapcustomappinbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxMessage;
import com.clevertap.android.sdk.CTInboxMessageContent;
import com.clevertap.android.sdk.CleverTapAPI;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CTInboxListener {

    Button appinboxpayload, pushevents, clearcache;

    CleverTapAPI cleverTapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(this);
        if (cleverTapDefaultInstance != null) {
            //Set the Notification Inbox Listener
            cleverTapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
            cleverTapDefaultInstance.initializeInbox();
        }

        String CleverTapID = CleverTapAPI.getDefaultInstance(getApplicationContext()).getCleverTapID();

        pushevents = findViewById(R.id.pushevents);
        clearcache = findViewById(R.id.clearcache);

        pushevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CleverTapAPI.getDefaultInstance(getApplicationContext()).pushEvent("Product Viewed");

            }
        });

        clearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                deleteCache(getApplicationContext());
            }
        });


    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    public void inboxDidInitialize() {

        appinboxpayload = findViewById(R.id.appinboxpayload);
        appinboxpayload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CTInboxMessage> inboxMessageArrayList = new ArrayList<>();
                inboxMessageArrayList = cleverTapDefaultInstance.getAllInboxMessages();
                for(int i=0; i<= inboxMessageArrayList.size()-1; i++) {
                    //inboxMessageArrayList.get(i).getInboxMessageContents().get(i);
                    /**
                     * Returns an ArrayList of the contents of {@link CTInboxMessage}
                     * For Simple Message and Icon Message templates the size of this ArrayList is by default 1.
                     * For Carousel templates, the size of the ArrayList is the number of slides in the Carousel
                     * @return ArrayList of {@link CTInboxMessageContent} objects
                     */
                    Log.e("TAG", inboxMessageArrayList.get(i).getData().toString());

                    String type = String.valueOf(inboxMessageArrayList.get(i).getType());
                    Log.e("TAG",type);

                    try {
                       //To get title:
                        if(type.contains("carousel")) {
                            for(int j=0; j<= inboxMessageArrayList.get(i).getInboxMessageContents().size()-1; j++)
                            {
                                Log.e("TAG","Title"+j+" "+inboxMessageArrayList.get(i).getInboxMessageContents().get(j).getTitle());
                            }
                        }
                        else {
                            Log.e("TAG", inboxMessageArrayList.get(i).getInboxMessageContents().get(0).getTitle());
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void inboxMessagesDidUpdate() {

    }


}
