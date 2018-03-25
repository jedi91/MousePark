package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Michael on 3/7/2018.
 */

public class DynamoBehavior implements DatabaseBehavior {
    private int lotId;
    private int statusValue;
    private DynamoMapper mapper;

    public DynamoBehavior(Context context, LotName name)
    {
        this.mapper = new DynamoMapper(context);
        this.lotId = AppSettings.getLotId(context, name);
    }

    @Override
    public int getValue() {
        try {
            return new DynamoBehavior.getStatusAsync().execute().get();
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
        new DynamoBehavior.updateStatusAsync().execute();
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
