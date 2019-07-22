package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

import handlers.AssetHandler;
import handlers.GameStateManager;

interface StoryCompleteCommand
{
    void execute();
}

public abstract class StoryState extends State
{
    private Table table = new Table();
    private Stage stage =  new Stage(new ScreenViewport());
    private int lineCounter = 0;
    private List<String> lines = new ArrayList<>();
    private StoryCompleteCommand completeCommand;

    public StoryState(GameStateManager gsm)
    {
        super(gsm);
        Gdx.input.setInputProcessor(stage);
    }

    protected void setCompleteCommand(StoryCompleteCommand cc)
    {
        this.completeCommand = cc;
    }

    private Label createLabel(String str)
    {
        AssetHandler assetHandler = gsm.getGame().getAssetHandler();
        int row_height = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Label label = new Label(str, labelStyle);
        label.setSize(Gdx.graphics.getWidth(), row_height);
        label.setAlignment(Align.center);
        label.setWidth(Gdx.graphics.getWidth() * 0.80f);
        label.setWrap(true);
        label.addAction(Actions.fadeIn(0.30f));
        return label;
    }

    protected void addLine(String line)
    {
        lines.add(line);
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched())
        {
            if (lineCounter == lines.size())
            {
                if (completeCommand != null)
                {
                    completeCommand.execute();
                }
            }
            else
            {
                table.clearChildren();
                table.add(createLabel(lines.get(lineCounter++)))
                        .align(Align.center)
                        .width(Gdx.graphics.getWidth() * 0.9f);
                table.setWidth(Gdx.graphics.getWidth());
                table.align(Align.center|Align.top);
                table.setPosition(0, Gdx.graphics.getHeight() / 1.5f);
                stage.addActor(table);
            }
        }
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        stage.act();
        stage.draw();
    }

    @Override
    public void update() { }

    @Override
    public void dispose()
    {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }
}
