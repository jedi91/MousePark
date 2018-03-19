package com.mkovacevich.michael.mousepark.MouseParkApp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "ParkingLots")
public class ParkingLot {
    private int lot;
    private int open;

    @DynamoDBHashKey(attributeName = "Lot")
    public int getLot(){
        return lot;
    }

    public void setLot(int lot) {this.lot = lot;}

    @DynamoDBAttribute(attributeName = "Open")
    public int getOpen(){
        return open;
    }

    public void setOpen(int open){
        this.open = open;
    }
}
