package states;

import android.content.SharedPreferences;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.ece454.gotl.AndroidLauncher;

import handlers.GameStateManager;

import static com.ece454.gotl.GotlGame.FIRST_TIME_PLAYING_STR;
import static com.ece454.gotl.GotlGame.PREFS_NAME;

public class IntroStoryState extends StoryState
{
    class IntroCompleteCommand implements StoryCompleteCommand
    {
        @Override
        public void execute()
        {
            AndroidLauncher.getAppContext()
                    .getSharedPreferences(PREFS_NAME, 0)
                    .edit()
                    .putBoolean(FIRST_TIME_PLAYING_STR, false)
                    .commit();
            gsm.set(new PlayState(gsm));
        }
    }

    public IntroStoryState(GameStateManager gsm)
    {
        super(gsm);
        setCompleteCommand(new IntroCompleteCommand());
        addLine("This is a story of Jerry the goose.");
        addLine("When Jerry was a little gosling, he would always play around with his red ball. He would kick it around for hours on end, and it brought him endless joy.");
        addLine("");
        addLine("One day, Jerry was out on a walk when a group of humans decided to kick his ball up to the sky.");
        addLine("To this day, Jerry climbs whatever he can to reach the sky and find his ball.");
    }
}

