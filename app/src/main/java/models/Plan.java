package models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Plan extends RealmObject{
    private String id;
    private String name;
    private int image;
    private RealmList<Weekday> weekdays;

    public Plan() {
    }

    public Plan(String name, int image, String id, RealmList<Weekday> weekdays) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.weekdays = weekdays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Weekday> getWeekdaysList() {
        return weekdays;
    }

    public void setWeekdaysList(RealmList<Weekday> weekdaysList) {
        this.weekdays = weekdaysList;
    }
}
