package com.m2dl.nater.data;

import android.location.Location;
import android.net.Uri;

import java.util.Date;

/**
 * Data structure representing a picture taken by a user.
 */
public class Picture {
    private Uri mImageUri;
    private Location mLocation;
    private Date mDate;
    private String mUser;
    private IdentificationKey mIdentificationKey;
}
