package com.rightcolor;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.rightcolor.comunication.ISocialNetworkAPI;

public class TwitterAPI implements ISocialNetworkAPI {

	private static final String DIALOG_TITLE = "Status update confirmation";
	private static final String DIALOG_MESSAGE = "The following message will be sent to Twitter: ";
	
    private Activity ctx;
    
    public TwitterAPI(Activity ctx) {
        this.ctx = ctx;
    }
 
    @Override
    public void updateStatus(final String status) {

        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
		        new AlertDialog.Builder(ctx)
		        .setIcon(android.R.drawable.ic_dialog_info)
		        .setTitle(DIALOG_TITLE)
		        .setMessage(DIALOG_MESSAGE + status)
		        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		
		                ConfigurationBuilder cb = new ConfigurationBuilder();
		                cb.setDebugEnabled(true)
		                  .setOAuthConsumerKey("gZSs6xylB3NYHhrwBt5uPdoSv")
		                  .setOAuthConsumerSecret("Bj1iyD3NxzjDjEonNJXXwQSMQiD3Qj6ZabSqIOlBlg0HUA6FBW")
		                  .setOAuthAccessToken("147532169-ocDj5C7v9l6GU4ZZVEall63divqCTsCs7qrJZYfr")
		                  .setOAuthAccessTokenSecret("f3EVSUG1DJRsa4moylZzu3NAPdzTCyc1dFfHZwZg3JtDt");
		                
		                TwitterFactory tf = new TwitterFactory(cb.build());
		                Twitter twitter = tf.getInstance();
		                
		                try {
		                    twitter.updateStatus(status + " " + STATUS_UPDATE_URL);
		                    Toast.makeText(ctx, STATUS_UPDATE_SUCCESS, Toast.LENGTH_SHORT).show();
		                } catch (TwitterException e) {
		                    e.printStackTrace();
		                    Toast.makeText(ctx, STATUS_UPDATE_FAILURE, Toast.LENGTH_LONG).show();
		                }
		            }
		        })
		        .setNegativeButton("Cancel", null)
		        .show();
            }
        });
    }

}
