package com.m2dl.nater.sharing;

import android.util.Log;

import com.m2dl.nater.data.Picture;

import com.m2dl.nater.utils.GMailSender;

import java.util.HashSet;
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
                        sender.addAttachment(picture.mImageUri.getPath(), "pic");
                        sender.sendMail("Nater image upload", picture.toString(), SenderProvider.APPLICATION_EMAIL, SenderProvider.SHARING_EMAIL);
                    }
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
        t.start();
    }
}
