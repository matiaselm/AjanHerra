package com.example.matias.viewpagerwithtabs;

import java.util.ArrayList;

public class ActionList {

    private ArrayList<Action> activities;
    private static final ActionList ourInstance = new ActionList();

    public static ActionList getInstance() {
        return ourInstance;
    }

    private ActionList() {
        activities = new ArrayList<>();

        activities.add(new Action("Nukkuminen", true, 7));
        activities.add(new Action("Syöminen", true, 1));
        activities.add(new Action("Työskentely", true, -1));
        activities.add(new Action("Opiskelu", true, -1));
        activities.add(new Action("Ruutuaika", false, 3 ));
        activities.add(new Action("Harrastukset", false, 3));
        activities.add(new Action("Viihde", false, 3));
        activities.add(new Action("Liikunta", true, 1));
        activities.add(new Action("Määrittelemätön", true, -1));
    }

    public void addAction(int type, int lenghtMinutes) {
        activities.get(type).addTime(lenghtMinutes);
    }

    public ArrayList<Action> getActivities() {
        return activities;
    }

    @Override
    public String toString() {
        return "Moiiii";
    }
}
