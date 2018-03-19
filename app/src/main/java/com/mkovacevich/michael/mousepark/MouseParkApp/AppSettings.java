package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;

import com.mkovacevich.michael.mousepark.R;

/**
 * Created by Michael on 2/26/2018.
 */

public class AppSettings {
    public static String getDynamoDBIdentityPool(Context context) {
        return context.getString(R.string.identity_pool);
    }

    public static String getGoogleMapsUri(Context context, LotName name) throws EnumConstantNotPresentException {
        switch (name) {
            case STRUCTURE_MAIN_ENTRANCE:
                return context.getString(R.string.structure_main_entrance_uri);
            case STRUCTURE_BRIDGE_ENTRANCE:
                return context.getString(R.string.structure_bridge_entrance_uri);
            case TOY_STORY_LOT:
                return context.getString(R.string.toy_story_lot_uri);
            case DOWNTOWN_DISNEY_LOT:
                return context.getString(R.string.downtown_disney_lot_uri);
            default:
                throw new EnumConstantNotPresentException(name.getClass(), String.format("Lot name: %s not found", name.toString()));
        }
    }

    public static int getLotId(Context context, LotName name) throws IllegalArgumentException, EnumConstantNotPresentException {
        switch (name) {
            case STRUCTURE_MAIN_ENTRANCE:
                return context.getResources().getInteger(R.integer.structure_main_id);
            case STRUCTURE_BRIDGE_ENTRANCE:
                return context.getResources().getInteger(R.integer.structure_bridge_id);
            case TOY_STORY_LOT:
            case DOWNTOWN_DISNEY_LOT:
                return context.getResources().getInteger(R.integer.simple_lot);
            default:
                throw new EnumConstantNotPresentException(name.getClass(), String.format("Lot name: %s not found", name.toString()));
        }
    }

    public static int getLotStatusId(Context context, LotStatus status) throws EnumConstantNotPresentException
    {
        switch (status) {
            case OPEN:
                return context.getResources().getInteger(R.integer.open_status);
            case CLOSED:
                return context.getResources().getInteger(R.integer.closed_status);
            default:
                throw new EnumConstantNotPresentException(status.getClass(), String.format("Lot status: %s not found", status.toString()));
        }
    }

    public static LotStatus getLotStatus(Context context, int id)
    {
        switch (id){
            case 1:
                return LotStatus.OPEN;
            case 0:
                return  LotStatus.CLOSED;
            default:
                throw new IllegalArgumentException(String.format("Lot statusId: %d does not exist.", id));
        }
    }
}
