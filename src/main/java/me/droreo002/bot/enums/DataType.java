package me.droreo002.bot.enums;

import lombok.Getter;

public enum DataType {

    STRING(2),
    INT(9, 5),
    FLOAT(1),
    VECTOR2(3), // Actual math representation of a 2D Vector
    VECTOR3(4); // Actual math representation of a 3D Vector

    @Getter
    private int[] aliases;

    DataType(int... aliases) {
        this.aliases = aliases;
    }

    public static DataType fromAlias(int alias) {
        for (DataType type : values()) {
            for (int i : type.getAliases()) {
                if (i == alias) return type;
            }
        }
        return null;
    }
}
