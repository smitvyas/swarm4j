/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.swarm4J.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author william
 */
public class Utils {

    public static double getMTRandom() {
        return new MersenneTwisterFast().nextDouble();
    }

    public static MersenneTwisterFast getMTInstance() {
        return new MersenneTwisterFast();
    }

    public static Object getInstanceByReflection(String qName) {
        Object o = null;
        try {
            o = Class.forName(qName).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }

    public static List<Double> doubleListDeepCopy(List<Double> original) {
        List<Double> copy = new ArrayList<Double>();
        for (int i=0; i<original.size();i++) {
            copy.add(original.get(i).doubleValue());
        }
        return copy;
    }
}
