package com.example.matias.viewpagerwithtabs.singletons;

import com.example.matias.viewpagerwithtabs.classes.Action;

import java.util.ArrayList;

public class ActionList {

    private ArrayList<Action> activities;
    private static final ActionList ourInstance = new ActionList();
    private ArrayList defaultList;

    public static ActionList getInstance() {
        return ourInstance;
    }

    private ActionList() {
        defaultList = new ArrayList<>();

        defaultList.add(new Action("Nukkuminen", true, 7, "Uni on tärkeä fyysisen ja henkisen hyvinvoinnin lähde. Riittävä uni auttaa mm. painonhallinnassa ja oppimisessa."));
        defaultList.add(new Action("Liikunta", true, 1, "Säännöllinen liikunta pitää yleiskunnon ja painon hyvässä tilassa. Kehon hyvä vointi auttaa jaksamaan ja pidentää ikää."));
        defaultList.add(new Action("Syöminen", true, 1, "Ruoka on tärkeä energian ja hivenaineiden lähde. Säännöllinen ruokailu parantaa kehon toimintaa."));
        defaultList.add(new Action("Työskentely", true, -1, "Töiden teko on toisille välttämätön, toisille nautinnollinen keino ansaita elanto."));
        defaultList.add(new Action("Opiskelu", true, -1, "Opiskelu kannattaa aina. Hyvä sivistys edesauttaa työsuhteiden, mutta myös ihmissuhteiden hankintaa."));
        defaultList.add(new Action("Ruutuaika", false, 3, "Television, mobiililaitteen tai tietokoneen viihdekäyttö on mukavaa. Liiallisissa määrissä voi aiheuttaa fyysisiä ja psyykkisiä ongelmia." ));
        defaultList.add(new Action("Harrastukset", false, 3, "Harrastukset ovat tärkeitä ihmisen hyvinvoinnin kannalta. Kuitenkin on muistettava vanha sananlasku: ensin työ, sitten hupi."));
        defaultList.add(new Action("Viihde", false, 3, "Viihde on jokaiselle meistä erilainen käsite. Nauru pidentää ikää, mutta kohtuus kaikessa."));
        defaultList.add(new Action("Määrittelemätön", true, -1, "Meillä kaikilla on omat juttume, hyvä niin, vaali omaa itseäsi."));

        setDefaultList();
    }

    public void setDefaultList(){
        activities = defaultList;
    }

    public void addAction(int type, int lenghtMinutes) {
        activities.get(type).addTime(lenghtMinutes);
    }

    public ArrayList<Action> getActivities() {
        return activities;
    }

    public void setActivities (ArrayList activityList){
        activities = activityList;
    }

    @Override
    public String toString() {
        return "Moiiii";
    }

}
