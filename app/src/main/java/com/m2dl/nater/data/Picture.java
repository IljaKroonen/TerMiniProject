package com.m2dl.nater.data;

import android.location.Location;
import android.net.Uri;

import java.util.Date;

/**
 * Data structure representing a picture taken by a user.
 */
public class Picture {
    public Uri mImageUri;
    public Location mLocation;
    public Date mDate;
    public String mUser;
    public IdentificationKey mIdentificationKey;

    @Override
    public String toString(){
        return mLocation + "\n" + mDate + "\n" + mUser + "\n" + mIdentificationKey;
    }
}
