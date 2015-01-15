package com.m2dl.nater.sharing;

public final class SenderProvider {
    public static final String APPLICATION_EMAIL = "nater-m2dl@gmail.com";
    public static final String APPLICATION_PASSWORD = "bobbycarrot21";
    public static final String SHARING_EMAIL = "nater-m2dl@gmail.com";

    public static final ISendPicture[] availableSenders = { new SendPictureEmail() };
}
