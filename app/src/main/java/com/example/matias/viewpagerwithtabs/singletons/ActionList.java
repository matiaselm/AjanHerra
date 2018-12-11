package com.example.matias.viewpagerwithtabs.singletons;

import com.example.matias.viewpagerwithtabs.classes.Action;
import com.example.matias.viewpagerwithtabs.classes.User;

import java.util.ArrayList;

/**
 * This singleton manages one set of action list. The action list is saved and loaded as gson in saved preference. When new user logs in we change the action list in here.
 * Lists are saved as format user*index*ActionList
 * If a new user logs in a new default list is added to account.
 */
public class ActionList {

    private ArrayList<Action> activities;
    private static final ActionList ourInstance = new ActionList();
    private ArrayList<Action> defaultList;

    public static ActionList getInstance() {
        return ourInstance;
    }

    private ActionList() {
        setDefaultList();
    }

    /**
     * Create a new empty default list of actions.
     */
    public void setDefaultList() {
        defaultList = new ArrayList<>();

        defaultList.add(new Action("Nukkuminen", true, 7, "Uni on tärkeä fyysisen ja henkisen hyvinvoinnin lähde. Riittävä uni auttaa mm. painonhallinnassa ja oppimisessa."));
        defaultList.add(new Action("Liikunta", true, 1, "Säännöllinen liikunta pitää yleiskunnon ja painon hyvässä tilassa. Kehon hyvä vointi auttaa jaksamaan ja pidentää ikää."));
        defaultList.add(new Action("Syöminen", true, 1, "Ruoka on tärkeä energian ja hivenaineiden lähde. Säännöllinen ruokailu parantaa kehon toimintaa."));
        defaultList.add(new Action("Työskentely", true, -1, "Töiden teko on toisille välttämätön, toisille nautinnollinen keino ansaita elanto."));
        defaultList.add(new Action("Opiskelu", true, -1, "Opiskelu kannattaa aina. Hyvä sivistys edesauttaa työsuhteiden, mutta myös ihmissuhteiden hankintaa."));
        defaultList.add(new Action("Ruutuaika", false, 3, "Television, mobiililaitteen tai tietokoneen viihdekäyttö on mukavaa. Liiallisissa määrissä voi aiheuttaa fyysisiä ja psyykkisiä ongelmia."));
        defaultList.add(new Action("Harrastukset", false, 3, "Harrastukset ovat tärkeitä ihmisen hyvinvoinnin kannalta. Kuitenkin on muistettava vanha sananlasku: ensin työ, sitten hupi."));
        defaultList.add(new Action("Viihde", false, 3, "Viihde on jokaiselle meistä erilainen käsite. Nauru pidentää ikää, mutta kohtuus kaikessa."));
        //defaultList.add(new Action("Määrittelemätön", true, -1, "Meillä kaikilla on omat juttume, hyvä niin, vaali omaa itseäsi."));

        activities = defaultList;
    }

    /**
     * When we spend time doing the action we add it.
     *
     * @param type          Action name
     * @param lenghtMinutes minutes
     */
    public void addAction(int type, int lenghtMinutes) {
        activities.get(type).addTime(lenghtMinutes);
    }

    /**
     * Create a brand new users action type
     *
     * @param type         Name of action
     * @param needMoreThan If we need more time than refHours
     * @param refHours     How many hours is suggested per day
     * @param description  Small text about it
     */
    public void addNewActionType(String type, boolean needMoreThan, double refHours, String description) {
        getActivities().add(new Action(type, needMoreThan, refHours, description));
    }

    /**
     * Get User object with requested index
     *
     * @param index
     * @return User User object
     */
    public Action getActivity(int index) {
        return getActivities().get(index);
    }


    /**
     * Check if the username is taken. Go through every index of userList and compare.
     *
     * @param testName
     * @return
     */
    public boolean testNewActivityName(String testName) {

        for (int i = 0; (getActivities().size()) > i; i++) {
            if (testName.equals(getActivity(i).getType())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get whole Action type array, which is our current array of activities.
     *
     * @return
     */
    public ArrayList<Action> getActivities() {
        return activities;
    }

    /**
     * Set new Array list of actions.
     *
     * @param activityList new list
     */
    public void setActivities(ArrayList activityList) {
        activities = activityList;
    }

    @Override
    public String toString() {
        return "Moiiii";
    }

}
