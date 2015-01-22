package com.m2dl.nater.sharing;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.m2dl.nater.data.GlobalVars;
import com.m2dl.nater.data.Picture;

import com.m2dl.nater.utils.GMailSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SendPictureEmail implements ISendPicture {
    final GMailSender sender = new GMailSender(SenderProvider.APPLICATION_EMAIL, SenderProvider.APPLICATION_PASSWORD);

    @Override
    public void sendPicture(final Picture picture) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (sender) {
                        sender.addAttachment(picture.mImageUri.getPath(), "Photo portant la clé d'identification : " + picture.mIdentificationKey.toString() + " - pris le " + picture.mDate.toString() + " par " + picture.mUser + " à " + getCity(picture.mLocation));
                        sender.sendMail("Nater image upload", picture.toString(), SenderProvider.APPLICATION_EMAIL, SenderProvider.SHARING_EMAIL);
                    }
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
        t.start();
    }

    private String getCity(Location location) {
        if (location==null) {
            return "(absence de données GPS)";
        }
        Geocoder gcd = new Geocoder(GlobalVars.context, Locale.getDefault());
        List<Address> addresses = new ArrayList<Address>();
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0)
            return addresses.get(0).getLocality();
        else
            return "";
    }
}
