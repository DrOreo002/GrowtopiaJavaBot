package me.droreo002.bot.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteReader implements Cloneable {

    @Getter
    private byte[] rawByte;
    @Getter
    private int length;
    @Getter @Setter
    private int currentIndex;

    public ByteReader(byte[] rawByte) {
        this.rawByte = rawByte;
        this.currentIndex = 0;
        this.length = rawByte.length;
    }

    public void skip(int i) {
        if ((currentIndex + i) >= length) throw new ArrayIndexOutOfBoundsException("Cannot exceed the array length!");
        currentIndex += i;
        byte[] nByte = new byte[rawByte.length - currentIndex];
        System.arraycopy(rawByte, currentIndex, nByte, 0, nByte.length);
        rawByte = nByte;
        currentIndex = 0;
    }

    public int getByte(int i) {
        return rawByte[i];
    }

    public byte readByte() {
        byte b = rawByte[currentIndex];
        skip(1);
        return b;
    }

    public byte[] read(int length) {
        byte[] nByte = new byte[length];
        System.arraycopy(rawByte, currentIndex, nByte, 0, length);
        skip(length);
        return nByte;
    }

    public int readInt() {
        ByteBuffer buffer = ByteBuffer.wrap(rawByte);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int i = buffer.getInt(currentIndex);
        skip(4);
        return i;
    }

    public float readFloat() {
        ByteBuffer buffer = ByteBuffer.wrap(rawByte);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        float i = buffer.getFloat(currentIndex);
        skip(4);
        return i;
    }

    @Override
    public ByteReader clone() {
        try {
            return (ByteReader) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
