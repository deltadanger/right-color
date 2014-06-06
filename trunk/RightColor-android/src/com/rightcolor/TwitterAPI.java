package com.rightcolor;

//import twitter4j.Status;
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.rightcolor.comunication.ConfirmParameter;
import com.rightcolor.comunication.ISocialNetworkAPI;

public class TwitterAPI implements ISocialNetworkAPI {
    
    private Context ctx;
    
    public TwitterAPI(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void updateStatus(final String status, final String url, final String success, final String failure, ConfirmParameter param) {
//        
//        new AlertDialog.Builder(ctx)
//        .setIcon(android.R.drawable.ic_dialog_info)
//        .setTitle(param.getDialogTitle())
//        .setMessage(param.getDialogContent())
//        .setPositiveButton(param.getOkBtn(), new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                ConfigurationBuilder cb = new ConfigurationBuilder();
//                cb.setDebugEnabled(true)
//                  .setOAuthConsumerKey("*********************")
//                  .setOAuthConsumerSecret("******************************************")
//                  .setOAuthAccessToken("**************************************************")
//                  .setOAuthAccessTokenSecret("******************************************");
//                
//                TwitterFactory tf = new TwitterFactory(cb.build());
//                Twitter twitter = tf.getInstance();
//                
//                try {
//                    
//                    Status result = twitter.updateStatus(status + " " + url);
//                    if (result.equals(Status.READ)) {
//                        Toast.makeText(ctx, success, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(ctx, failure, Toast.LENGTH_LONG).show();
//                    }
//                } catch (TwitterException e) {
//                    e.printStackTrace();
//                    Toast.makeText(ctx, failure, Toast.LENGTH_LONG).show();
//                }
//            }
//        })
//        .setNegativeButton(param.getCancelBtn(), null)
//        .show();
    }

}
