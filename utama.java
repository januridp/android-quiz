package com.example.kuisfisika;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class utama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
    }
    public void actPlay(View o) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void actHelp(View o) {
        AlertDialog.Builder builder=
                new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Help Me!")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
        AlertDialog dialogHasil=builder.create();
        dialogHasil.show();
    }
    public void actAbout(View o) {
        AlertDialog.Builder builder=
                new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage("Made by Januri Dwi Prasetyo\nVersion : 0.8 (Beta)")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
        AlertDialog dialogHasil=builder.create();
        dialogHasil.show();
    }

}
