package com.example.morkince.okasyonv2.activities.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String user_first_name= null;
    String user_last_name = null;
    String user_gender = null;
    String user_birth_date = null;
    String user_contact_no = null;
    String user_email = null;
    String user_profPic = null;
    String user_role = null;
    String user_uid = null;

    public User(String user_first_name, String user_last_name, String user_gender, String user_birth_date, String user_contact_no, String user_email, String user_profPic, String user_role, String user_uid) {
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_gender = user_gender;
        this.user_birth_date = user_birth_date;
        this.user_contact_no = user_contact_no;
        this.user_email = user_email;
        this.user_profPic = user_profPic;
        this.user_role = user_role;
        this.user_uid = user_uid;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_birth_date() {
        return user_birth_date;
    }

    public void setUser_birth_date(String user_birth_date) {
        this.user_birth_date = user_birth_date;
    }

    public String getUser_contact_no() {
        return user_contact_no;
    }

    public void setUser_contact_no(String user_contact_no) {
        this.user_contact_no = user_contact_no;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_profPic() {
        return user_profPic;
    }

    public void setUser_profPic(String user_profPic) {
        this.user_profPic = user_profPic;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    protected User(Parcel in) {
        user_first_name = in.readString();
        user_last_name = in.readString();
        user_gender = in.readString();
        user_birth_date = in.readString();
        user_contact_no = in.readString();
        user_email = in.readString();
        user_profPic = in.readString();
        user_role = in.readString();
        user_uid = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_first_name);
        dest.writeString(user_last_name);
        dest.writeString(user_gender);
        dest.writeString(user_birth_date);
        dest.writeString(user_contact_no);
        dest.writeString(user_email);
        dest.writeString(user_profPic);
        dest.writeString(user_role);
        dest.writeString(user_uid);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
