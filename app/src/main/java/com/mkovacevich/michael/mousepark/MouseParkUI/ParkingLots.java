package com.mkovacevich.michael.mousepark.MouseParkUI;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mkovacevich.michael.mousepark.MouseParkApp.Lot;
import com.mkovacevich.michael.mousepark.MouseParkApp.LotName;
import com.mkovacevich.michael.mousepark.MouseParkApp.LotStatus;
import com.mkovacevich.michael.mousepark.MouseParkApp.MouseParkApp;
import com.mkovacevich.michael.mousepark.R;

public class ParkingLots extends AppCompatActivity {
    private ImageButton mickeyAndFriends;
    private RadioButton mickeyOpen;
    private RadioButton mickeyClosed;
    private TextView mickeyOpenText;
    private TextView mickeyClosedText;
    private TableRow mickeyAndFriendsRow;
    private ImageButton mickeyAndFriendsBridge;
    private RadioButton bridgeOpen;
    private RadioButton bridgeClosed;
    private TextView bridgeOpenText;
    private TextView bridgeClosedText;
    private TableRow bridgeRow;
    private ImageButton toyStoryLot;
    private ImageButton downtonwnDisneyLot;
    private MouseParkApp app;
    private AdView mAdView;
    private Lot structureMain;
    private Lot structureBridge;
    private Lot toyStory;
    private Lot downtownDisney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lots_3);
        app = new MouseParkApp(ParkingLots.this);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        //MickAndFrieds Main Entrance
        mickeyAndFriends = (ImageButton)findViewById(R.id.mickeyAndFriends);
        mickeyAndFriends.setOnClickListener(buttonListener);

        mickeyOpen = (RadioButton)findViewById(R.id.mickeyOpenBtn);
        mickeyOpen.setOnClickListener(buttonListener);

        mickeyClosed = (RadioButton)findViewById(R.id.mickeyClosedBtn);
        mickeyClosed.setOnClickListener(buttonListener);

        mickeyOpenText = (TextView)findViewById(R.id.mickeyOpenText);
        mickeyClosedText = (TextView)findViewById(R.id.mickeyClosedText);

        mickeyAndFriendsRow = (TableRow)findViewById(R.id.mickeyAndFriendsRow);

        //MickAndFrieds Bridge Entrance
        mickeyAndFriendsBridge = (ImageButton)findViewById(R.id.mickeyAndFriendsBridge);
        mickeyAndFriendsBridge.setOnClickListener(buttonListener);

        bridgeOpen = (RadioButton)findViewById(R.id.bridgeOpenBtn);
        bridgeOpen.setOnClickListener(buttonListener);

        bridgeClosed = (RadioButton)findViewById(R.id.bridgeClosedBtn);
        bridgeClosed.setOnClickListener(buttonListener);

        bridgeOpenText = (TextView)findViewById(R.id.bridgeOpenText);
        bridgeClosedText = (TextView)findViewById(R.id.bridgeClosedText);

        bridgeRow = (TableRow)findViewById(R.id.mickeyAndFriendsBridgeRow);

        app.refreshUI(mickeyAndFriendsRow, bridgeRow, mickeyOpen, mickeyClosed, bridgeOpen, bridgeClosed, mickeyOpenText, mickeyClosedText, bridgeOpenText, bridgeClosedText);

        toyStoryLot = (ImageButton)findViewById(R.id.toyStoryLot);
        toyStoryLot.setOnClickListener(buttonListener);

        downtonwnDisneyLot = (ImageButton)findViewById(R.id.downtownDisneyBtn);
        downtonwnDisneyLot.setOnClickListener(buttonListener);

        structureMain = new Lot(ParkingLots.this, LotName.STRUCTURE_MAIN_ENTRANCE);
        structureBridge = new Lot(ParkingLots.this, LotName.STRUCTURE_BRIDGE_ENTRANCE);
        toyStory = new Lot(ParkingLots.this, LotName.TOY_STORY_LOT);
        downtownDisney = new Lot(ParkingLots.this, LotName.DOWNTOWN_DISNEY_LOT);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(300000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                app.refreshUI(mickeyAndFriendsRow, bridgeRow, mickeyOpen, mickeyClosed, bridgeOpen, bridgeClosed, mickeyOpenText, mickeyClosedText, bridgeOpenText, bridgeClosedText);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mickeyAndFriends:
                    mickeyAndFriendsOnClick();
                    break;
                case R.id.mickeyOpenBtn:
                    mickeyFriendsYesOnClick();
                    break;
                case R.id.mickeyClosedBtn:
                    mickeyFriendsNoOnClick();
                    break;
                case R.id.mickeyAndFriendsBridge:
                    mickeyAndFriendsBridgeOnClick();
                    break;
                case R.id.bridgeOpenBtn:
                    bridgeYesOnClick();
                    break;
                case R.id.bridgeClosedBtn:
                    bridgeNoOnClick();
                    break;
                case R.id.toyStoryLot:
                    toyStoryLotOnClick();
                    break;
                case R.id.downtownDisneyBtn:
                    downtownDisneyLotOnClick();
            }
        }
    };

    private void mickeyAndFriendsOnClick(){
        structureMain.openDirections(this);
    }

    private void mickeyFriendsYesOnClick(){
        mickeyAndFriendsRow.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_open));
        mickeyOpenText.setTextColor(ContextCompat.getColor(this, R.color.font));
        if(mickeyClosed.isChecked())
            mickeyClosed.setChecked(false);
        mickeyClosedText.setTextColor(ContextCompat.getColor(this, R.color.lot_light));

        structureMain.updateStatus(LotStatus.OPEN);
    }

    private void mickeyFriendsNoOnClick(){
        mickeyAndFriendsRow.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_closed));
        mickeyClosedText.setTextColor(ContextCompat.getColor(this, R.color.font));
        if(mickeyOpen.isChecked())
            mickeyOpen.setChecked(false);
        mickeyOpenText.setTextColor(ContextCompat.getColor(this, R.color.lot_light));

        structureMain.updateStatus(LotStatus.CLOSED);
    }

    private void mickeyAndFriendsBridgeOnClick(){
        structureBridge.openDirections(this);
    }

    private void bridgeYesOnClick(){
        bridgeRow.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_open));
        bridgeOpenText.setTextColor(ContextCompat.getColor(this, R.color.font));
        if(bridgeClosed.isChecked())
            bridgeClosed.setChecked(false);
        bridgeClosedText.setTextColor(ContextCompat.getColor(this, R.color.lot_light));

        structureBridge.updateStatus(LotStatus.OPEN);
    }

    private void bridgeNoOnClick(){
        bridgeRow.setBackgroundColor(ContextCompat.getColor(this, R.color.lot_closed));
        bridgeClosedText.setTextColor(ContextCompat.getColor(this, R.color.font));
        if(bridgeOpen.isChecked())
            bridgeOpen.setChecked(false);
        bridgeOpenText.setTextColor(ContextCompat.getColor(this, R.color.lot_light));

        structureBridge.updateStatus(LotStatus.CLOSED);
    }

    private void toyStoryLotOnClick(){
        toyStory.openDirections(this);
    }

    private void downtownDisneyLotOnClick(){
        downtownDisney.openDirections(this);
    }
}

