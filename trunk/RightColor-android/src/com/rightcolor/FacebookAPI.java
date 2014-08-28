package com.rightcolor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.rightcolor.comunication.ISocialNetworkAPI;

public class FacebookAPI implements ISocialNetworkAPI {
	
	private static final String DIALOG_TITLE = "Status update confirmation";
	private static final String DIALOG_MESSAGE = "The following message will be sent to Facebook: ";
    
    private static final String APPLICATION_ID = "745059875558518";

    
    private static final String OPEN_PERMISSION = "basic_info";
    private static final String PUBLISH_PERMISSION = "publish_actions";

    private Activity ctx;
    
    private String status;
    
    public FacebookAPI(Activity ctx) {
        this.ctx = ctx;
    }
	
    Session.StatusCallback  sessionOpenCallback = new Session.StatusCallback() {
        
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            Log.d("test", "callback session opened " + session.isOpened() + " " + exception);
            if (session.isOpened()) {
                Log.d("test", "callback session permissions " + session.getPermissions());
                if (session.getPermissions().contains(PUBLISH_PERMISSION)) {
                    doUpdateStatus(session, STATUS_UPDATE_SUCCESS);
                } else {
                    Session.NewPermissionsRequest newPermRequest = new Session.NewPermissionsRequest(ctx, PUBLISH_PERMISSION);
                    session.removeCallback(this);
                    session.addCallback(statusUpdatePermissionCallback);
                    session.requestNewPublishPermissions(newPermRequest);
                }
            }
        }
    };
    
    Session.StatusCallback statusUpdatePermissionCallback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			Log.d("test", "status update callback session opened " + session.isOpened() + " " + exception);
            if (session.isOpened()) {
                Log.d("test", "status update callback session permissions " + session.getPermissions());
                if (session.getPermissions().contains(PUBLISH_PERMISSION)) {
                    doUpdateStatus(session, STATUS_UPDATE_SUCCESS);
                } else {
                	session.closeAndClearTokenInformation();
                	createNewSession();
                }
            }
		}
	};
	
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
                    	FacebookAPI.this.status = status;
                        createNewSession();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
            }
        });
    }

	private void createNewSession() {
        Session session = new Session.Builder(ctx)
            .setApplicationId(APPLICATION_ID)
            .build();
        
        Session.setActiveSession(session);
        
        Log.d("test", "session opened " + session.isOpened());
        if (!session.isOpened()) {
            Session.OpenRequest openRequest = new Session.OpenRequest(ctx);
            openRequest.setPermissions(new String[]{OPEN_PERMISSION});
            session.addCallback(sessionOpenCallback);
            session.openForRead(openRequest);
        } else {
        	sessionOpenCallback.call(session, session.getState(), null);
        }
	}
	
    private void doUpdateStatus(Session session, final String success) {

        Log.d("test", "update status");
        Request.newStatusUpdateRequest(session, status + " " + STATUS_UPDATE_URL, new Request.Callback() {
            
            @Override
            public void onCompleted(Response response) {
                Log.d("test", "status update completed " + response.getError());
                if (response.getError() == null) {
                    Toast.makeText(ctx, success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeAsync();
    }   
}
