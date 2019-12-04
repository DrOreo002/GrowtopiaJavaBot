package me.droreo002.bot.models;

import lombok.Data;

@Data
public class GameObject {
    private int netId;
    private int userId;
    private String name;
    private String country;
    private String objectType;
    private float objX;
    private float objY;
    private boolean gone;
    // Collision stuff
    private int rectX;
    private int rectY;
    private int rectWidth;
    private int rectHeight;
    private boolean moderator;
    private boolean local;

    public static GameObject getEmpty() {
        GameObject object = new GameObject();
        object.setNetId(-1);
        object.setUserId(-1);
        object.setObjX(-1);
        object.setObjY(-1);
        return object;
    }
}
