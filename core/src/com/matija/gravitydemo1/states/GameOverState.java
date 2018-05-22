package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.matija.gravitydemo1.states.Menus;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by mikul on 5.4.2018..
 */

public class GameOverState extends State{
    private Texture background;
    private Texture menuBtn;
    private Texture highBtn;
    private Texture saveBtn;
    private final String newHigh = "NEW HIGHSCORE:";
    private final String noHigh = "   Your score:";
    private boolean highscore = false;
    private String message = noHigh;
    private Texture title;
    private int score, i, j, k;
    private Character[] signs = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    Preferences pref = Gdx.app.getPreferences("Highscore");
    private BitmapFont font;
    private BitmapFont scoreFont;
    private BitmapFont fontFlipped;
    private FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Comic_sans.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private Rectangle boundMenu;
    private Rectangle boundHigh;
    private Rectangle boundSave;
    private Rectangle boundUp1;
    private Rectangle boundUp2;
    private Rectangle boundUp3;
    private Rectangle boundDown1;
    private Rectangle boundDown2;
    private Rectangle boundDown3;


    public GameOverState(GameStateManager gsm, int points, boolean win) {
        super(gsm);
        cam.setToOrtho(false, Menus.WIDTH/2, Menus.HEIGHT /2);
        background = new Texture("background.png");
        if (win == true) {
            title = new Texture("win.png");
        }
        else{
            title = new Texture("gameover.png");
        }
        menuBtn = new Texture("button_menu.png");
        highBtn = new Texture("button_highscore.png");
        saveBtn = new Texture("button_save.png");

        boundHigh=new Rectangle(cam.position.x*1.05f, cam.position.y*0.25f-highBtn.getHeight(),highBtn.getWidth(),highBtn.getHeight());
        boundMenu=new Rectangle(cam.position.x - menuBtn.getWidth()*1.05f, cam.position.y*0.25f-menuBtn.getHeight(),menuBtn.getWidth(),menuBtn.getHeight());
        boundSave=new Rectangle(cam.position.x - saveBtn.getWidth()/2, cam.position.y*0.25f-saveBtn.getHeight(),saveBtn.getWidth(),saveBtn.getHeight());


        boundUp1 = new Rectangle(cam.position.x*0.75f, cam.position.y*0.58f, 10,10);
        boundUp2 = new Rectangle(cam.position.x*0.96f, cam.position.y*0.58f, 10,10);
        boundUp3 = new Rectangle(cam.position.x*1.17f, cam.position.y*0.58f, 10,10);

        boundDown1 = new Rectangle(cam.position.x*0.75f, cam.position.y*0.43f, 10, 10);
        boundDown2 = new Rectangle(cam.position.x*0.96f, cam.position.y*0.43f, 10, 10);
        boundDown3 = new Rectangle(cam.position.x*1.17f, cam.position.y*0.43f, 10, 10);


        i = 0;
        j = 0;
        k = 0;
        //dohvati score
        score = points;

        if (pref.getString("5").equals("") && score > 0 || score > Integer.parseInt(pref.getString("5", "aaa:0").split(":")[1])){
            highscore = true;
            message = newHigh;
        }

        parameter.size=20;
        parameter.color= Color.WHITE;
        parameter.borderColor= Color.BLUE;
        parameter.borderWidth = 1;
        font = fontGen.generateFont(parameter);
        parameter.flip=true;
        fontFlipped = fontGen.generateFont(parameter);
        parameter.flip=false;
        parameter.size=30;
        scoreFont=fontGen.generateFont(parameter);

        fontGen.dispose();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 tmp=new Vector3( Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(tmp);

            if( highscore == false) {

                if (boundMenu.contains(tmp.x, tmp.y)) {
                    gsm.set(new MenuState(gsm));
                }

                if (boundHigh.contains(tmp.x, tmp.y)) {
                    gsm.set(new HighState(gsm));
                }

            }
            else {
                if (boundSave.contains(tmp.x, tmp.y)) {
                    String nick = Character.toString(signs[i]) + Character.toString(signs[j]) + Character.toString(signs[k]) + ":" + Integer.toString(score);

                    /*System.out.println("i: " + i + " j: " + j + " k: " + k);
                    System.out.println(signs[i]);

                    System.out.println(nick);*/
                    for( int key = 5; key > 0; key--){
                        String curHigh = pref.getString(Integer.toString(key-1), "aaa:0");
                        if (curHigh.equals("")){
                            curHigh="aaa:0";
                        }
                        //System.out.println(key);

                        //System.out.println(curHigh);
                        if (score > Integer.parseInt(curHigh.split(":")[1])){
                            pref.putString( Integer.toString(key), pref.getString(Integer.toString(key-1), "aaa:0"));
                            pref.flush();

                        }
                        else{
                            pref.putString( Integer.toString(key), nick);
                            pref.flush();

                            break;
                        }
                        if (key==1){
                            pref.putString( Integer.toString(key-1),nick);
                            pref.flush();

                        }
                    }
                    gsm.set(new HighState(gsm));
                }

                if (boundUp1.contains(tmp.x, tmp.y)) {
                    i = i - 1;
                    if (i < 0) {
                        i = signs.length - 1;
                    }
                }
                if (boundDown1.contains(tmp.x, tmp.y)) {
                    i = i + 1;
                    if (i == signs.length) {
                        i = 0;
                    }
                }

                if (boundUp2.contains(tmp.x, tmp.y)) {
                    j = j - 1;
                    if (j < 0) {
                        j = signs.length - 1;
                    }
                }
                if (boundDown2.contains(tmp.x, tmp.y)) {
                    j = j + 1;
                    if (j == signs.length) {
                        j = 0;
                    }
                }

                if (boundUp3.contains(tmp.x, tmp.y)) {
                    k = k - 1;
                    if (k < 0) {
                        k = signs.length - 1;
                    }
                }
                if (boundDown3.contains(tmp.x, tmp.y)) {
                    k = k + 1;
                    if (k == signs.length) {
                        k = 0;
                    }
                }
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
        font.draw(sb, message, cam.position.x*0.28f, cam.position.y+font.getLineHeight()*0.75f);
        scoreFont.draw(sb, Integer.toString(score), cam.position.x*0.85f, cam.position.y-font.getLineHeight()*0.5f);

        if( highscore == true) {
            font.draw(sb, "Enter your nickname:", cam.position.x * 0.15f, cam.position.y * 0.75f);
            font.draw(sb, "^  ^  ^", cam.position.x * 0.75f, cam.position.y * 0.6f);
            font.draw(sb, Character.toString(signs[i]), cam.position.x * 0.75f, cam.position.y * 0.57f);
            font.draw(sb, Character.toString(signs[j]), cam.position.x * 0.96f, cam.position.y * 0.57f);
            font.draw(sb, Character.toString(signs[k]), cam.position.x * 1.17f, cam.position.y * 0.57f);
            fontFlipped.draw(sb, "^  ^  ^", cam.position.x * 0.75f, cam.position.y * 0.45f);

            sb.draw(saveBtn, cam.position.x - saveBtn.getWidth()/2, cam.position.y*0.25f-saveBtn.getHeight());

        }
        else{
            sb.draw(highBtn, cam.position.x*1.05f , cam.position.y*0.25f-highBtn.getHeight());
            sb.draw(menuBtn, cam.position.x - menuBtn.getWidth()*1.05f, cam.position.y*0.25f-menuBtn.getHeight());
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        font.dispose();
        fontFlipped.dispose();
        scoreFont.dispose();
        menuBtn.dispose();
        highBtn.dispose();
        saveBtn.dispose();

    }
}
