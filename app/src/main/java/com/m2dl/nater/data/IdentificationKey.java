package com.m2dl.nater.data;

import android.content.Context;
import android.content.res.Resources;

import com.m2dl.nater.R;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Data structure representing the identification key of a species.
 */
public class IdentificationKey {
    private static final DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

    private Context context;
    private Document document;
    private List<String> path = new ArrayList<>();

    public IdentificationKey(Context context) throws Exception {
        this.context = context;
        this.document = domFactory.newDocumentBuilder().parse(context.getResources().getString(R.xml.determination));
    }

    public String[] getCurrentChoices() {
        return null;
    }

    public void registerChoice(String choice) {
    }

    @Override
    public String toString() {
        return "";
    }
}
