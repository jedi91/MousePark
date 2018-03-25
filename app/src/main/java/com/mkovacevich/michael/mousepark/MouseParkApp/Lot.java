package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Michael on 3/4/2018.
 */

public class Lot
{
    private int statusId;
    private Context context;
    private LotName name;
    private DatabaseBehavior database;

    public Lot(Context context, LotName name)
    {
        this.context = context;
        this.name = name;
        this.statusId = 0;
        this.database = DatabaseBehaviorFactory.createDatabaseBehavior(context, name);;
    }

    public boolean isSimpleLot(){
        return  database instanceof SimpleBehavior;
    }

    public void openDirections(AppCompatActivity activity)
    {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(AppSettings.getGoogleMapsUri(context, name)));
        activity.startActivity(viewIntent);
    }

    public LotStatus getStatus()  {
        statusId = database.getValue();
        return  AppSettings.getLotStatus(context, statusId);
    }

    public void updateStatus(LotStatus status){
        statusId = AppSettings.getLotStatusId(context, status);
        database.updateValue(statusId);
    }
}
