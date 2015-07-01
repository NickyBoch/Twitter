package com.twitter.Controls;

import com.twitter.actions.GeneralActions;

/**
 * Created by Admin on 6/30/2015.
 */
public class ActionControls {
    private static GeneralActions generalActions;

    public static GeneralActions getGeneralAction() {
        return (generalActions != null) ? generalActions : new GeneralActions();
    }
}
