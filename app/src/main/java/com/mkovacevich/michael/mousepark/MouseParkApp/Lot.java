package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

/**
 * Created by Michael on 3/4/2018.
 */

public class Lot
{
    private int statusId;
    private boolean isSimpleLot;
    private Context context;
    private LotName name;
    private DynamoMapper mapper;
    private DynamoBehavior behavior;

    public Lot(Context context, LotName name)
    {
        this.context = context;
        this.name = name;
        this.mapper = new DynamoMapper(context);
        this.statusId = 0;

        int id = AppSettings.getLotId(context, name);
        if (id == -1) {
            isSimpleLot = true;
            behavior = new SimpleLotBehavior();
        }
        else {
            isSimpleLot = false;
            behavior = new DatabaseLotBehavior(context, id);
        }
    }

    public boolean isSimpleLot(){
        return  isSimpleLot;
    }

    public void openDirections(AppCompatActivity activity)
    {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(AppSettings.getGoogleMapsUri(context, name)));
        activity.startActivity(viewIntent);
    }

    public LotStatus getStatus()  {
        statusId = behavior.getValue();
        return  AppSettings.getLotStatus(context, statusId);
    }

    public void updateStatus(LotStatus status){
        statusId = AppSettings.getLotStatusId(context, status);
        behavior.updateValue(statusId);
    }
}
