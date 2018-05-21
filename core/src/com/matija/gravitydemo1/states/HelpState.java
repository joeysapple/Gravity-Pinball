package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.matija.gravitydemo1.states.Menus;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by mikul on 5.4.2018..
 */

public class HelpState extends State{
    private Texture background;
    private Texture backBtn;
    private Texture title;
    private Rectangle boundBack;
    private TextButton helpText;
    private Skin skin;
    Preferences pref = Gdx.app.getPreferences("Highscore");
    public HelpState(GameStateManager gsm) {
        super(gsm);
        //za mob
        cam.setToOrtho(false, Menus.WIDTH/2, Menus.HEIGHT /2);

        background = new Texture("background.png");
        title = new Texture("help.png");
        backBtn = new Texture("button_back.png");

        //Smooth
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backBtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        boundBack=new Rectangle(cam.position.x - backBtn.getWidth() / 2, cam.position.y*0.75f,backBtn.getWidth(),backBtn.getHeight());
        //skin = new Skin();
        //helpText = new TextButton("This is the help text", skin);
        if( pref.getString("1", "No data").equals("No data")){
            pref.putString("1", "a:10");
        }
        else{
            String score = pref.getString("1", "aaa:0");
            if (score.equals("")){
                score = "aaa:0";
            }
            int val = Integer.parseInt(score.split(":")[1]);
            String name = pref.getString("1").split(":")[0];
            val += 10;
            pref.putString("1", name+":"+Integer.toString(val) );
        }
        pref.flush();


    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 tmp=new Vector3( Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(tmp);
            //Rectangle textureBounds=new Rectangle(cam.position.x - playBtn.getWidth() / 2, cam.position.y,playBtn.getWidth(),playBtn.getHeight());

            if(boundBack.contains(tmp.x,tmp.y)) {
                gsm.set(new MenuState(gsm));
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, Menus.WIDTH/2, Menus.HEIGHT /2);
        sb.draw(title, cam.position.x +7 - title.getWidth() / 2, cam.position.y*2f - title.getHeight()*1.1f);
        sb.draw(backBtn, cam.position.x - backBtn.getWidth()/2, cam.position.y*0.75f);
        //helpText.draw(sb, 0);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        backBtn.dispose();
        title.dispose();

    }
}
