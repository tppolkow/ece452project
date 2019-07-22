package states;

import com.badlogic.gdx.maps.tiled.TiledMap;

import handlers.GameStateManager;

public class OutroStoryState extends StoryState
{
    class OutroCompleteCommand implements StoryCompleteCommand
    {
        @Override
        public void execute()
        {
            gsm.resetLvl();
            GameStateManager.resetInputProcessor = true;
            gsm.pop();
        }
    }

    public OutroStoryState(GameStateManager gsm)
    {
        super(gsm);
        setCompleteCommand(new OutroCompleteCommand());
        addLine("Somewhere up in the clouds Jerry notices something odd...");
        addLine("A red speck appears in the distance of the foggy clouds. He flies a bit closer...");
        addLine("");
        addLine("He squints a bit... it can't be... OMG IT'S IS! IT'S HIS RED BALL!!! You helped Jerry find his ball!");
        addLine("Jerry returns to the ground with his beloved ball and lives the rest of his goose life happily ever after :)");
    }
}
