package com.mkovacevich.michael.mousepark.MouseParkApp;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * Created by Michael on 3/5/2018.
 */

public class DynamoMapper {
    private Context context;
    private DynamoDBMapper mapper;

    public DynamoMapper(Context context) {
        this.context = context;

        try {
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    context,
                    AppSettings.getDynamoDBIdentityPool(context), // Identity Pool ID
                    Regions.US_WEST_2 // Region
            );

            AmazonDynamoDBClient ddbClient;
            DynamoDBMapper mapper;

            ddbClient = new AmazonDynamoDBClient(credentialsProvider);
            mapper = new DynamoDBMapper(ddbClient);
        }
        catch (Exception ex)
        {
        }
    }

    public ParkingLot loadParkingLot(int id) {
        if(mapper != null) {
            return mapper.load(ParkingLot.class, id);
        }

        return  new ParkingLot();
    }

    public void saveParkingLot(ParkingLot lot) {
        if(mapper != null) {
            mapper.save(lot);
        }
    }
}
