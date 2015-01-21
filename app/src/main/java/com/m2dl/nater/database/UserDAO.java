package com.m2dl.nater.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserDAO extends DAOBase{

    public UserDAO(Context pContext) {
        super(pContext);
    }


    public static final String TABLE_NAME = "USER";
    public static final String ID = "_id";
    public static final String PSEUDO = "Speudo";
    public static final String MAIL = "Mail";



    // Creation de la table
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    PSEUDO + " TEXT, " +
                    MAIL + " TEXT);";


    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " +
            TABLE_NAME + ";";

    public void ajouter(String speudo, String mail) {
        ContentValues value = new ContentValues();
        value.put(UserDAO.ID, 1);
        value.put(UserDAO.PSEUDO, speudo);
        value.put(UserDAO.MAIL, mail);
        mDb.insert(UserDAO.TABLE_NAME, null, value);
    }

    public void supprimer() {
        mDb.delete(TABLE_NAME,null,null);
    }

    public String getMail() {
        Cursor cursor = mDb.rawQuery("select " + MAIL +" from " + TABLE_NAME , null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getPseudo() {
        Cursor cursor = mDb.rawQuery("select " + PSEUDO +" from " + TABLE_NAME , null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public boolean isEmpty() {
        Cursor cursor = mDb.rawQuery("select " + ID +" from " + TABLE_NAME, null);
        if(cursor.getCount()==0)
            return true;
        else
            return false;
    }


}
