package com.rightcolor.client;

import com.rightcolor.MainGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480, 320);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new MainGame(new ISocialNetworkAPI() {

            @Override
            public void updateStatus(String status, String url, String success,
                    String failure, ConfirmParameter param) {
                System.out.println("Facebook " + status);
                
            }
        }, new ITwitterAPI() {

            @Override
            public void updateStatus(String status, String url, String success,
                    String failure, ConfirmParameter param) {
                System.out.println("Twitter " + status);
                
            }
        });
	}
}