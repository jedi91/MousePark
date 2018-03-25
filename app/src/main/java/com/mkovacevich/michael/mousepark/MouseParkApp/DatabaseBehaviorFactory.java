package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;

/**
 * Created by Michael on 3/24/2018.
 */

public class DatabaseBehaviorFactory {
    public static DatabaseBehavior createDatabaseBehavior(Context context, LotName name){
        switch (name){
            case STRUCTURE_MAIN_ENTRANCE:
            case STRUCTURE_BRIDGE_ENTRANCE:
                return new DynamoBehavior(context, name);
            default:
                return new SimpleBehavior();
        }
    }
}
