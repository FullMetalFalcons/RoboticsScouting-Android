package org.fullmetalfalcons.androidscouting.fileio;

import android.app.Activity;
import android.content.res.AssetManager;

import org.fullmetalfalcons.androidscouting.elements.Element;
import org.fullmetalfalcons.androidscouting.elements.ElementParseException;
import org.fullmetalfalcons.androidscouting.equations.Equation;
import org.fullmetalfalcons.androidscouting.equations.EquationParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by djher on 2/20/2016.
 */
public class ConfigManager {

    private static final ArrayList<Equation> EQUATIONS = new ArrayList<>();
    private static final ArrayList<Element> ELEMENTS = new ArrayList<>();

    public static void loadConfig(Activity a){
        //Located in the assets folder
        AssetManager am = a.getAssets();
        try {
            BufferedReader config = new BufferedReader(new InputStreamReader(am.open("config.txt")));
            String line;
            //While there are still lines to read
            while ((line=config.readLine())!=null) {
                line = line.trim();
                if (line.length() < 2) {
                    continue;
                }
                //If the line does not start with ##, which indicates a comment, or @ which indicated an equation
                if (!line.substring(0, 2).equals("##") && line.charAt(0) != '@') {
                    //Attempt to add an Element to the main array
                    addElement(line);
                }

                if (line.startsWith("@")){
                    addEquation(line);
                }
            }
        } catch (IOException e) {
            //This exception signifies the entire app is useless
            //sendError("fuck",true);
            e.printStackTrace();
        }
    }

    private static void addEquation(String line){
        try {
            Equation e = new Equation(line);
            //debug("Equation " + line + " added");
            EQUATIONS.add(e);
        } catch (EquationParseException e){
            //sendError(e.getMessage(), false);
        }
    }

    /**
     * Adds a new element from a string to the main array
     *
     * @param line line from config file
     */
    private static void addElement(String line){
        try {
            //Element will throw an exception if it is improperly formed
            Element e = new Element(line);
            ELEMENTS.add(e);
        } catch (ElementParseException e1) {
            e1.printStackTrace();
        }
    }


    public static ArrayList<Equation> getEquations() {
        return EQUATIONS;
    }

    public static ArrayList<Element> getElements() {
        return ELEMENTS;
    }
}
