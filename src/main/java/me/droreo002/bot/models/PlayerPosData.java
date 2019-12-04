package me.droreo002.bot.models;

import lombok.Data;

@Data
public class PlayerPosData {
    private int netId;
    private float x;
    private float y;
    private int characterState;
    private int plantingTree;
    private float xSpeed;
    private float ySpeed;
    private int punchX;
    private int punchY;
}
