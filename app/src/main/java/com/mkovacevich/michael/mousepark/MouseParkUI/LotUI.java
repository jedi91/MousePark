package com.mkovacevich.michael.mousepark.MouseParkUI;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.mkovacevich.michael.mousepark.MouseParkApp.Lot;
import com.mkovacevich.michael.mousepark.MouseParkApp.LotName;
import com.mkovacevich.michael.mousepark.MouseParkApp.LotStatus;
import com.mkovacevich.michael.mousepark.R;

/**
 * Created by Michael on 3/7/2018.
 */

public class LotUI extends AppCompatActivity {
    private ImageButton btnDirections;
    private RadioButton btnOpen;
    private RadioButton btnClosed;
    private TextView txtOpen;
    private TextView txtClosed;
    private TableRow row;
    private Lot lot;

    //Change to a fragment, create a loadLot() method to act in place of a constructor

    public LotUI(LotName name)
    {
        setContentView(R.layout.activity_parking_lots_3);
        lot = new Lot(LotUI.this, name);

        btnDirections = (ImageButton)findViewById(R.id.mickeyAndFriends);
        btnDirections.setOnClickListener(buttonListener);

        btnOpen = (RadioButton)findViewById(R.id.mickeyOpenBtn);
        btnOpen.setOnClickListener(buttonListener);

        btnClosed = (RadioButton)findViewById(R.id.mickeyClosedBtn);
        btnClosed.setOnClickListener(buttonListener);

        txtOpen = (TextView)findViewById(R.id.mickeyOpenText);
        txtClosed = (TextView)findViewById(R.id.mickeyClosedText);

        row = (TableRow)findViewById(R.id.mickeyAndFriendsRow);

        if(lot.isSimpleLot()){
            btnOpen.setVisibility(View.GONE);
            btnClosed.setVisibility(View.GONE);
            txtOpen.setVisibility(View.GONE);
            txtClosed.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mickeyAndFriends:
                    btnDirectionsOnClick();
                    break;
                case R.id.mickeyOpenBtn:
                    btnOpenOnClick();
                    break;
                case R.id.mickeyClosedBtn:
                    btnClosedOnClick();
                    break;
            }
        }
    };

    private void btnDirectionsOnClick(){
        lot.openDirections(this);
    }

    private void btnOpenOnClick(){
        row.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_open));
        txtOpen.setTextColor(ContextCompat.getColor(this, R.color.font));
        if(btnClosed.isChecked())
            btnClosed.setChecked(false);
        txtClosed.setTextColor(ContextCompat.getColor(this, R.color.lot_light));

        lot.updateStatus(LotStatus.OPEN);
    }

    private void btnClosedOnClick(){
        row.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_closed));
        txtClosed.setTextColor(ContextCompat.getColor(this, R.color.font));
        if(btnOpen.isChecked())
            btnOpen.setChecked(false);
        txtOpen.setTextColor(ContextCompat.getColor(this, R.color.lot_light));

        lot.updateStatus(LotStatus.CLOSED);
    }

    private void refresh(){
        if(lot.getStatus() == LotStatus.OPEN) {
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_open));
            btnOpen.setChecked(true);
            txtOpen.setTextColor(ContextCompat.getColor(this, R.color.font));
            btnClosed.setChecked(false);
            txtClosed.setTextColor(ContextCompat.getColor(this, R.color.lot_light));
        }
        else{
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_closed));
            btnClosed.setChecked(true);
            txtClosed.setTextColor(ContextCompat.getColor(this, R.color.font));
            btnOpen.setChecked(false);
            txtOpen.setTextColor(ContextCompat.getColor(this, R.color.lot_light));
        }
    }
}
