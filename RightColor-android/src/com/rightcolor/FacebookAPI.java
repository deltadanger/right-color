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
import com.rightcolor.comunication.ConfirmParameter;
import com.rightcolor.comunication.ISocialNetworkAPI;

public class FacebookAPI implements ISocialNetworkAPI {
    
    private final String APPLICATION_ID = "529071227210911";

    private Activity ctx;
    
    public FacebookAPI(Activity ctx) {
        this.ctx = ctx;
    }
    
    @Override
    public void updateStatus(final String status, final String url, final String success, final String failure, final ConfirmParameter param) {

        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(ctx)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(param.getDialogTitle())
                .setMessage(param.getDialogContent())
                .setPositiveButton(param.getOkBtn(), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        final String openPermission = "basic_info";
                        final String publishPermission = "publish_actions";
                        
                        Session.StatusCallback callback = new Session.StatusCallback() {
                            
                            @Override
                            public void call(Session session, SessionState state, Exception exception) {
                                Log.d("test", "state: " + state);
                                Log.d("test", "session.isOpened(): " + session.isOpened());
                                
                                if (session.isOpened()) {
                                    if (session.getPermissions().contains(publishPermission)) {
                                        Log.d("test", "got permission");
                                        doUpdateStatus(session, status, success);
                                    } else {
                                        Log.d("test", "need permission");
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
                .setNegativeButton(param.getCancelBtn(), null)
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
