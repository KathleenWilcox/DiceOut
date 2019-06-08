package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //Field to hold roll result text
    TextView rollResult;

    //Field to hold the score.
    int score;

    //Field to hold random number generator
    Random rand;

    //Fields to hold the dice value
    int die1;
    int die2;
    int die3;

    //Field to hold the score
    TextView scoreText;

    //ArrayList to hold all 3 dice values

    ArrayList<Integer> dice;

    //ArrayList to hold all 3 dice images
    ArrayList<ImageView> diceImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        //set initial score
        score = 0;

        //create greeting
        Toast.makeText(getApplicationContext(),"Welcome to Diceout!", Toast.LENGTH_SHORT).show();

        //connects widgets in xml to java variables so we can control them
        rollResult = (TextView) findViewById(R.id.rollResult);
        scoreText = (TextView) findViewById(R.id.scoreText);

        //initialize the random number generator
        rand = new Random();

        //create ArrayList containers for dice values
        dice = new ArrayList<Integer>();

        //link images to java variables

        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageView = new ArrayList<ImageView>();
        diceImageView.add(die1Image);
        diceImageView.add(die2Image);
        diceImageView.add(die3Image);

    }

    public void rollDice(View v){
        rollResult.setText("Clicked!");

        //Roll dice
        die1 = rand.nextInt(6) + 1;
        die2= rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;

        //Set dice values into an ArrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for(int dieOfSet = 0; dieOfSet < 3; dieOfSet++)
        {
            String imageName = "die_" + dice.get(dieOfSet) + ".png";

            try{

                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream,null);
                diceImageView.get(dieOfSet).setImageDrawable(d);

            }catch(IOException e){

                e.printStackTrace();
            }
        }

        // Build message with the result

        String msg;

        if( die1 == die2 && die1 == die3){
            //Triples
            int scoreDelta = die1 * 100;
            msg = "You rolled a triple " + die1 + "! You scored a " + scoreDelta + " points!";
            score += scoreDelta;
        }else if( die1 == die2 || die1 == die3 || die2 == die3){

            msg = "You rolled a doubles for 50 points!";
            score += 50;
        }else{
            msg = "You didn't score. Try again!";
        }

        //Update the app to display the result message

        rollResult.setText(msg);
        scoreText.setText("Score: " + score);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
