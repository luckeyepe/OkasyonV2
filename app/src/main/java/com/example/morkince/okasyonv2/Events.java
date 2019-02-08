package com.example.morkince.okasyonv2;

public class Events {
double event_budget_spent;
String event_category_id;
String event_creator_id;
String event_date;
String event_description;
String event_event_uid;
boolean event_is_private;
String event_location;
String event_name;
int event_num_of_sponsors;
int event_num_of_attendees;
String event_picture;
double event_set_budget;
Long event_timestamp;
String event_event_organizer_uid;
String event_tags;

    public Events(){}

    public Events(double event_budget_spent, String event_category_id, String event_creator_id, String event_date, String event_description, String event_event_uid_, boolean event_is_private, String event_location, String event_name, int event_num_of_sponsors, int event_num_of_attendees, String event_picture, double event_set_budget, Long event_timestamp, String event_event_organizer_uid, String event_tags) {
        this.event_budget_spent = event_budget_spent;
        this.event_category_id = event_category_id;
        this.event_creator_id = event_creator_id;
        this.event_date = event_date;
        this.event_description = event_description;
        this.event_event_uid = event_event_uid_;
        this.event_is_private = event_is_private;
        this.event_location = event_location;
        this.event_name = event_name;
        this.event_num_of_sponsors = event_num_of_sponsors;
        this.event_num_of_attendees = event_num_of_attendees;
        this.event_picture = event_picture;
        this.event_set_budget = event_set_budget;
        this.event_timestamp = event_timestamp;
        this.event_event_organizer_uid=event_event_organizer_uid;
        this.event_tags=event_tags;
    }

    public double getEvent_budget_spent() {
        return event_budget_spent;
    }

    public void setEvent_budget_spent(double event_budget_spent) {
        this.event_budget_spent = event_budget_spent;
    }

    public String getEvent_category_id() {
        return event_category_id;
    }

    public void setEvent_category_id(String event_category_id) {
        this.event_category_id = event_category_id;
    }

    public String getEvent_creator_id() {
        return event_creator_id;
    }

    public void setEvent_creator_id(String event_creator_id) {
        this.event_creator_id = event_creator_id;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_event_uid() {
        return event_event_uid;
    }

    public void setEvent_event_uid(String event_event_uid) {
        this.event_event_uid = event_event_uid;
    }

    public boolean isEvent_is_private() {
        return event_is_private;
    }

    public void setEvent_is_private(boolean event_is_private) {
        this.event_is_private = event_is_private;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getEvent_num_of_sponsors() {
        return event_num_of_sponsors;
    }

    public void setEvent_num_of_sponsors(int event_num_of_sponsors) {
        this.event_num_of_sponsors = event_num_of_sponsors;
    }

    public int getEvent_num_of_attendees() {
        return event_num_of_attendees;
    }

    public void setEvent_num_of_attendees(int event_num_of_attendees) {
        this.event_num_of_attendees = event_num_of_attendees;
    }

    public String getEvent_picture() {
        return event_picture;
    }

    public void setEvent_picture(String event_picture) {
        this.event_picture = event_picture;
    }

    public double getEvent_set_budget() {
        return event_set_budget;
    }

    public void setEvent_set_budget(double event_set_budget) {
        this.event_set_budget = event_set_budget;
    }

    public Long getEvent_timestamp() {
        return event_timestamp;
    }

    public void setEvent_timestamp(Long event_timestamp) {
        this.event_timestamp = event_timestamp;
    }

    public String getEvent_event_organizer_uid() {
        return event_event_organizer_uid;
    }

    public void setEvent_event_organizer_uid(String event_event_organizer_uid) {
        this.event_event_organizer_uid = event_event_organizer_uid;
    }

    public String getEvent_tags() {
        return event_tags;
    }

    public void setEvent_tags(String event_tags) {
        this.event_tags = event_tags;
    }
}
