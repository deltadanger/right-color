package com.rightcolor;

import helper.AssetLoader;

import com.badlogic.gdx.Game;
import com.rightcolor.comunication.ISocialNetworkAPI;

public class MainGame extends Game {
    
    private ISocialNetworkAPI facebook;
    private ISocialNetworkAPI twitter;
    
    public MainGame(ISocialNetworkAPI facebook, ISocialNetworkAPI twitter) {
        this.facebook = facebook;
        this.twitter = twitter;
    }

	@Override
    public void create() {
        AssetLoader.load();
        setScreen(new GameScreen(facebook, twitter));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
    
}
