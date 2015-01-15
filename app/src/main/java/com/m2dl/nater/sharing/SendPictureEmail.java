package com.m2dl.nater.sharing;

import android.util.Log;

import com.m2dl.nater.data.Picture;

import com.m2dl.nater.utils.GMailSender;

public class SendPictureEmail implements ISendPicture {
    @Override
    public void sendPicture(Picture picture) {
        GMailSender sender = new GMailSender(SenderProvider.APPLICATION_EMAIL, SenderProvider.APPLICATION_PASSWORD);

        try {
            sender.addAttachment(picture.mImageUri.getPath(), "pic");
            sender.sendMail("Nater image upload", picture.toString(), SenderProvider.APPLICATION_EMAIL, SenderProvider.SHARING_EMAIL);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }
}
