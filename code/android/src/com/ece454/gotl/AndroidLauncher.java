package com.ece454.gotl;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import handlers.SocialMediaHandler;

public class AndroidLauncher extends AndroidApplication {
    private GotlGame game;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		game = new GotlGame();
		game.setSocialMediaHandler(new SocialMediaHandler(this));
		initialize(game, config);
	}
}
