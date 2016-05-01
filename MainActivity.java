package com.example.kuisfisika;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Database db;
    private TextView txtnama, txtno, txtwaktu, txtsoal;
    private ImageView img;
    private RadioGroup rg;
    private RadioButton rdA, rdB, rdC;
    private List<Soal> listSoal;
    private CountDownTimer mCountDownTimer;
    private int detik = 300 * 1000; // --> 5 menit
    private Button btnPrev, btnNext, btnSelesai;
    int jawabanYgDiPilih[] = null;
    int jawabanYgBenar[] = null;
    boolean cekPertanyaan = false;
    int urutanPertanyaan = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        txtnama = (TextView) findViewById(R.id.textViewNama);
        txtno = (TextView) findViewById(R.id.textViewHalaman);
        txtwaktu = (TextView) findViewById(R.id.textViewWaktu);
        txtsoal = (TextView) findViewById(R.id.textViewSoal);
        img = (ImageView) findViewById(R.id.imageView1);
        rg = (RadioGroup) findViewById(R.id.radioGroup1);
        rdA = (RadioButton) findViewById(R.id.radio0);
        rdB = (RadioButton) findViewById(R.id.radio1);
        rdC = (RadioButton) findViewById(R.id.radio2);
        btnPrev = (Button) findViewById(R.id.buttonPrev);
        btnNext = (Button) findViewById(R.id.buttonNext);
        btnSelesai = (Button) findViewById(R.id.buttonSelesai);

        listSoal = new ArrayList<Soal>();
        listSoal = db.getSoal();

        btnSelesai.setOnClickListener(klikSelesai);
        btnPrev.setOnClickListener(klikSebelum);
        btnNext.setOnClickListener(klikBerikut);
        //new GetSoal().execute();
        jawabanYgDiPilih = new int[listSoal.size()];
        java.util.Arrays.fill(jawabanYgDiPilih, -1);
        jawabanYgBenar = new int[listSoal.size()];
        java.util.Arrays.fill(jawabanYgBenar, -1);
        showInputUser();
    }

    private void showInputUser() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View v = mInflater.inflate(R.layout.activity_nama, null);

        final AlertDialog dialog = new AlertDialog.Builder(this).create();

        dialog.setView(v);
        dialog.setTitle("Type your name");

        dialog.setCancelable(false);

        final Button btnOk = (Button) v.findViewById(R.id.buttonOK);
        final EditText inputUser = (EditText) v.findViewById(R.id.editTextNama);

        btnOk.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(inputUser.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Please fill", Toast.LENGTH_LONG).show();
                }else{
                    txtnama.setText(inputUser.getText().toString());
                    mulaiKuis();
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
    }

    protected void mulaiKuis() {
        setUpWaktu();
        setUpSoal();

    }

    private void setUpSoal() {
        this.tunjukanPertanyaan(0, cekPertanyaan);
    }

    private void tunjukanPertanyaan(int urutan_soal_soal, boolean review) {
        try {
            rg.clearCheck();
            Soal soal = new Soal();
            soal = listSoal.get(urutan_soal_soal);
            String pertanyaan = soal.getSoal();
            if (jawabanYgBenar[urutan_soal_soal] == -1) {
                jawabanYgBenar[urutan_soal_soal] = soal.getJwban();
            }

            int gambar = soal.getGambar();
            txtsoal.setText(pertanyaan.toCharArray(), 0, pertanyaan.length());
            img.setImageResource(gambar);
            rg.check(-1);
            String jwb_a = soal.getPil_a();
            rdA.setText(jwb_a.toCharArray(), 0,
                    jwb_a.length());
            String jwb_b = soal.getPil_b();
            rdB.setText(jwb_b.toCharArray(), 0,
                    jwb_b.length());
            String jwb_c = soal.getPil_c();
            rdC.setText(jwb_c.toCharArray(), 0,
                    jwb_c.length());

            Log.d("", jawabanYgDiPilih[urutan_soal_soal] + "");
            if (jawabanYgDiPilih[urutan_soal_soal] == 0)
                rg.check(R.id.radio0);
            if (jawabanYgDiPilih[urutan_soal_soal] == 1)
                rg.check(R.id.radio1);
            if (jawabanYgDiPilih[urutan_soal_soal] == 2)
                rg.check(R.id.radio2);

            pasangLabelDanNomorUrut();

            if (urutan_soal_soal == (listSoal.size() - 1))
                btnNext.setEnabled(false);

            if (urutan_soal_soal == 0)
                btnPrev.setEnabled(false);

            if (urutan_soal_soal > 0)
                btnPrev.setEnabled(true);

            if (urutan_soal_soal < (listSoal.size() - 1))
                btnNext.setEnabled(true);

        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage(), e.getCause());
        }
    }

    private void setUpWaktu() {
        mCountDownTimer = new CountDownTimer(detik, 1000)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {
                // TODO Auto-generated method stub
                txtwaktu.setText("Time Left: " +(int) (millisUntilFinished / 60000) +
                        " min");
            }

            @Override
            public void onFinish()
            {
                // TODO Auto-generated method stub
                txtwaktu.setText("Time Left: 0 min");
                Toast.makeText(getApplicationContext(), "Time Out!",
                        Toast.LENGTH_SHORT).show();
            }
        };

        mCountDownTimer.start();
    }

    private OnClickListener klikSelesai = new OnClickListener() {
        public void onClick(View v) {
            aturJawaban_nya();
            // hitung berapa yg benar
            int jumlahJawabanYgBenar = 0;
            for (int i = 0; i < jawabanYgBenar.length; i++) {
                if ((jawabanYgBenar[i] != -1) && (jawabanYgBenar[i] == jawabanYgDiPilih[i]))
                    jumlahJawabanYgBenar++;
            }
            AlertDialog tampilKotakAlert;
            tampilKotakAlert = new AlertDialog.Builder(MainActivity.this).create();
            tampilKotakAlert.setTitle("Score");
            tampilKotakAlert.setMessage(""+jumlahJawabanYgBenar+"");

            tampilKotakAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "Again",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            cekPertanyaan = false;
                            urutanPertanyaan = 0;

                            MainActivity.this.tunjukanPertanyaan(0,
                                    cekPertanyaan);
                        }
                    });

            tampilKotakAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            cekPertanyaan = false;
                            finish();
                        }
                    });

            tampilKotakAlert.show();

        }
    };

    private void aturJawaban_nya() {
        if (rdA.isChecked())
            jawabanYgDiPilih[urutanPertanyaan] = 0;
        if (rdB.isChecked())
            jawabanYgDiPilih[urutanPertanyaan] = 1;
        if (rdC.isChecked())
            jawabanYgDiPilih[urutanPertanyaan] = 2;

        Log.d("", Arrays.toString(jawabanYgDiPilih));
        Log.d("", Arrays.toString(jawabanYgBenar));

    }

    private OnClickListener klikBerikut = new OnClickListener() {
        public void onClick(View v) {
            aturJawaban_nya();
            urutanPertanyaan++;
            if (urutanPertanyaan >= listSoal.size())
                urutanPertanyaan = listSoal.size() - 1;

            tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        }
    };

    private OnClickListener klikSebelum = new OnClickListener() {
        public void onClick(View v) {
            aturJawaban_nya();
            urutanPertanyaan--;
            if (urutanPertanyaan < 0)
                urutanPertanyaan = 0;

            tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
        }
    };

    private void pasangLabelDanNomorUrut() {
        txtno.setText((urutanPertanyaan + 1) + " of "
                + listSoal.size());
    }
}