package com.m2dl.nater.data;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.m2dl.nater.R;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Data structure representing the identification key of a species. Currently, the XML file is
 * parsed for each instance ; in the future, it would be good to do this only one time to avoid
 * spilled resources.
 */
public class IdentificationKey {
    private static final DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

    private KeyNode parsed;
    private List<KeyNode> path = new ArrayList<>();

    public IdentificationKey(Context context) throws Exception {
        Document document = domFactory.newDocumentBuilder().parse(context.getResources().openRawResource(R.raw.determination));
        parsed = parseNode(document.getChildNodes().item(0));
}

    public String[] getCurrentChoices() {
        // Size of the amount of choices available in the last element in path
        String[] ret = new String[getCurrentKeyNode().getChoices().size()];

        for (int i = 0; i < getCurrentKeyNode().getChoices().size(); i++) {
            ret[i] = "" + getCurrentKeyNode().getChoices().get(i).getValue();
        }

        return ret;
    }

    public void registerChoice(String choice) {
        for (KeyNode it : getCurrentKeyNode().getChoices()) {
            if (it.getValue().equals(choice)) {
                path.add(it);
                return;
            }
        }
    }

    public KeyNode parseNode(Node node) {
        KeyNode ret = new KeyNode();

        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeName().equalsIgnoreCase("value")) {
                ret.setValue(n.getTextContent());
            } else if (n.getNodeName().equals("#text")) {

            } else {
                    ret.getChoices().add(parseNode(n));
             }

        }

        return ret;
    }

    @Override
    public String toString() {
        String ret = "";

        for (int i = 0; i < path.size(); i++) {
            ret = ret + " | " + path.get(i).getValue();
        }

        return ret;
    }

    private KeyNode getCurrentKeyNode() {
        if (path.size() == 0) {
            return parsed;
        }
        return path.get(path.size() - 1);
    }
}
