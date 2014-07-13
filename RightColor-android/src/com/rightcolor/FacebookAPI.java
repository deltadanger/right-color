package com.rightcolor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private Activity ctx;
    
    public FacebookAPI(Activity ctx) {
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
                        
                        final String openPermission = "basic_info";
                        final String publishPermission = "publish_actions";
                        
                        Session.StatusCallback callback = new Session.StatusCallback() {
                            
                            @Override
                            public void call(Session session, SessionState state, Exception exception) {
                                if (session.isOpened()) {
                                    if (session.getPermissions().contains(publishPermission)) {
                                        doUpdateStatus(session, status, STATUS_UPDATE_SUCCESS);
                                    } else {
                                        Session.NewPermissionsRequest newPermRequest = new Session.NewPermissionsRequest(ctx, publishPermission);
                                        session.requestNewPublishPermissions(newPermRequest);
                                    }
                                }
                            }
                        };

                        Session session = new Session.Builder(ctx)
                            .setApplicationId(APPLICATION_ID)
                            .build();
                        
                        Session.setActiveSession(session);
                        
                        if (!session.isOpened()) {
                            Session.OpenRequest openRequest = new Session.OpenRequest(ctx);
                            openRequest.setPermissions(new String[]{openPermission});
                            session.addCallback(callback);
                            session.openForRead(openRequest);
                        } else {
                            callback.call(session, session.getState(), null);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
            }
        });
    }
    
    private void doUpdateStatus(Session session, String status, final String success) {
        
        Request.newStatusUpdateRequest(session, status, new Request.Callback() {
            
            @Override
            public void onCompleted(Response response) {
                if (response.getError() == null) {
                    Toast.makeText(ctx, success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeAsync();
    }   
}
