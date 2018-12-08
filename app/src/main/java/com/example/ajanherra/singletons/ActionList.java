package com.example.ajanherra.singletons;

import com.example.ajanherra.classes.Action;

import java.util.ArrayList;

public class ActionList {

    private ArrayList<Action> activities;
    private static final ActionList ourInstance = new ActionList();

    public static ActionList getInstance() {
        return ourInstance;
    }

    private ActionList() {
        activities = new ArrayList<>();

        activities.add(new Action("Nukkuminen", true, 7, "Uni on tärkeä fyysisen ja henkisen hyvinvoinnin lähde. Riittävä uni auttaa mm. painonhallinnassa ja oppimisessa."));
        activities.add(new Action("Liikunta", true, 1, "Säännöllinen liikunta pitää yleiskunnon ja painon hyvässä tilassa. Kehon hyvä vointi auttaa jaksamaan ja pidentää ikää."));
        activities.add(new Action("Syöminen", true, 1, "Ruoka on tärkeä energian ja hivenaineiden lähde. Säännöllinen ruokailu parantaa kehon toimintaa."));
        activities.add(new Action("Työskentely", true, -1, "Töiden teko on toisille välttämätön, toisille nautinnollinen keino ansaita elanto."));
        activities.add(new Action("Opiskelu", true, -1, "Opiskelu kannattaa aina. Hyvä sivistys edesauttaa työsuhteiden, mutta myös ihmissuhteiden hankintaa."));
        activities.add(new Action("Ruutuaika", false, 3, "Television, mobiililaitteen tai tietokoneen viihdekäyttö on mukavaa. Liiallisissa määrissä voi aiheuttaa fyysisiä ja psyykkisiä ongelmia." ));
        activities.add(new Action("Harrastukset", false, 3, "Harrastukset ovat tärkeitä ihmisen hyvinvoinnin kannalta. Kuitenkin on muistettava vanha sananlasku: ensin työ, sitten hupi."));
        activities.add(new Action("Viihde", false, 3, "Viihde on jokaiselle meistä erilainen käsite. Nauru pidentää ikää, mutta kohtuus kaikessa."));
        activities.add(new Action("Määrittelemätön", true, -1, "Meillä kaikilla on omat juttume, hyvä niin, vaali omaa itseäsi."));
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
