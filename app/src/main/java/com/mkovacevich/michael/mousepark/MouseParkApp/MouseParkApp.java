package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.mkovacevich.michael.mousepark.R;

import java.util.concurrent.ExecutionException;

public class MouseParkApp extends AppCompatActivity {
    private AmazonDynamoDBClient ddbClient;
    private DynamoMapper mapper;
    private int id;
    private int idValue;
    private Context context;

    public MouseParkApp(Context mContext) {
        context = mContext;
        mapper = new DynamoMapper(context);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void updateLot(int id, int value){
        this.id = id;
        this.idValue = value;

        new updateLotAsync().execute();
    }

    private class updateLotAsync extends AsyncTask<Void, Integer, Integer>{
        @Override
        protected Integer doInBackground(Void... params) {
            ParkingLot lot = mapper.loadParkingLot(id);
            lot.setOpen(idValue);
            mapper.saveParkingLot(lot);

            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

        }
    }

    public int getLotValue(int id) throws ExecutionException, InterruptedException {
        this.id = id;
        return  new getLotValueAsync().execute().get();
    }

    private class getLotValueAsync extends AsyncTask<Void, Integer, Integer>{
        @Override
        protected Integer doInBackground(Void... params) {
            ParkingLot lot = mapper.loadParkingLot(id);
            idValue = lot.getOpen();
            return idValue;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    public void refreshUI(TableRow mickeyAndFriends, TableRow mickeyAndFriendsBridge,
                          RadioButton mickeyOpen, RadioButton mickeyClosed, RadioButton bridgeOpen, RadioButton bridgeClosed,
                          TextView mickeyOpenText, TextView mickeyClosedText, TextView bridgeOpenText, TextView bridgeClosedText)
    {
        try {
            if(getLotValue(1) == 1) {
                mickeyAndFriends.setBackgroundColor(ContextCompat.getColor(context, R.color.lot_open));
                mickeyOpen.setChecked(true);
                mickeyOpenText.setTextColor(ContextCompat.getColor(context, R.color.font));
                mickeyClosed.setChecked(false);
                mickeyClosedText.setTextColor(ContextCompat.getColor(context, R.color.lot_light));
            }
            else {
                mickeyAndFriends.setBackgroundColor(ContextCompat.getColor(context, R.color.lot_closed));
                mickeyClosed.setChecked(true);
                mickeyClosedText.setTextColor(ContextCompat.getColor(context, R.color.font));
                mickeyOpen.setChecked(false);
                mickeyOpenText.setTextColor(ContextCompat.getColor(context, R.color.lot_light));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if(getLotValue(2) == 1) {
                mickeyAndFriendsBridge.setBackgroundColor(ContextCompat.getColor(context, R.color.lot_open));
                bridgeOpen.setChecked(true);
                bridgeOpenText.setTextColor(ContextCompat.getColor(context, R.color.font));
                bridgeClosed.setChecked(false);
                bridgeClosedText.setTextColor(ContextCompat.getColor(context, R.color.lot_light));
            }
            else {
                mickeyAndFriendsBridge.setBackgroundColor(ContextCompat.getColor(context, R.color.lot_closed));
                bridgeClosed.setChecked(true);
                bridgeClosedText.setTextColor(ContextCompat.getColor(context, R.color.font));
                bridgeOpen.setChecked(false);
                bridgeOpenText.setTextColor(ContextCompat.getColor(context, R.color.lot_light));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
