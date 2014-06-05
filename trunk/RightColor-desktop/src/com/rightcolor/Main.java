package com.rightcolor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rightcolor.comunication.ConfirmParameter;
import com.rightcolor.comunication.ISocialNetworkAPI;

public class Main{
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "RightColor";
		cfg.useGL20 = false;
		cfg.width = 400;
		cfg.height = 600;
		
		new LwjglApplication(new MainGame(new ISocialNetworkAPI() {

            @Override
            public void updateStatus(String status, String url, String success,
                    String failure, ConfirmParameter param) {
                System.out.println("Facebook " + status);
                
            }
        }, new ISocialNetworkAPI() {

            @Override
            public void updateStatus(String status, String url, String success,
                    String failure, ConfirmParameter param) {
                System.out.println("Twitter " + status);
                
            }
        }), cfg);
	}
}
