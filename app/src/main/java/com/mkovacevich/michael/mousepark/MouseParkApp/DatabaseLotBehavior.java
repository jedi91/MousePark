package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Michael on 3/7/2018.
 */

public class DatabaseLotBehavior implements DynamoBehavior {
    private int lotId;
    private int statusValue;
    private DynamoMapper mapper;

    public DatabaseLotBehavior(Context context, int lotId)
    {
        this.mapper = new DynamoMapper(context);
        this.lotId = lotId;
    }

    @Override
    public int getValue() {
        try {
            return new DatabaseLotBehavior.getStatusAsync().execute().get();
        }
        catch (Exception ex)
        {
            return 0;
        }
    }

    private class getStatusAsync extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            ParkingLot dynamoLot = mapper.loadParkingLot(lotId);
            statusValue = dynamoLot.getOpen();
            return statusValue;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    @Override
    public void updateValue(int value) {
        this.statusValue = value;
        new DatabaseLotBehavior.updateStatusAsync().execute();
    }

    private class updateStatusAsync extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            ParkingLot dynamoLot = mapper.loadParkingLot(lotId);
            dynamoLot.setOpen(statusValue);
            mapper.saveParkingLot(dynamoLot);

            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
