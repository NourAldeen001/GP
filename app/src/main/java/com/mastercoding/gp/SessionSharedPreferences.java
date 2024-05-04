package com.mastercoding.gp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionSharedPreferences {
    private static final String PREF_NAME = "Session";

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "pass";

    private static final String KEY_ROLE = "role";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserDetails(int id, String username, String pass, String role) {
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASS, pass);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public int getID() {
        return sharedPreferences.getInt(KEY_ID, 0);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getPass() {
        return sharedPreferences.getString(KEY_PASS, null);
    }

    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, null);
    }

    public void setPass(String pass){
        editor.putString(KEY_PASS, pass);
        editor.apply();
    }

    public void clearUserDataSession() {
        editor.clear();
        editor.apply();
    }
}
