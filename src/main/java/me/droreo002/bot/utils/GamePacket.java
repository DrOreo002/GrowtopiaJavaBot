package me.droreo002.bot.utils;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static me.droreo002.bot.utils.ServerUtil.*;

public class GamePacket {

	@Getter
	private ByteBuffer data;
	@Getter
	private int len = 0;
	@Getter
	private int indices = 0;

	public GamePacket(int len) {
		setData(ByteBuffer.allocate(this.len = len));
	}

	public void setData(ByteBuffer data) {
		this.data = data;
		this.len = data.array().length;
	}

	public static GamePacket createPacket() {
		byte[] data = new byte[61];
		String asdf = "0400000001000000FFFFFFFF00000000080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		for (int i = 0; i < asdf.length(); i += 2) {
			byte x = (byte) (ch2n(asdf.charAt(i)));
			x = (byte) (x << 4);
			x += (ch2n(asdf.charAt(i + 1)));
			data[i / 2] = x;
		}
		GamePacket packet = new GamePacket(61);
		packet.getData().put(data);
		packet.getData().position(0);
		packet.indices = 0;
		return packet;
	}

	public static GamePacket appendString(GamePacket packet, String str) {
		int packlen = packet.len;
		byte[] strBytes = str.getBytes();
		byte[] data = new byte[packet.len + strBytes.length + 2 + 4];
		System.arraycopy(packet.getData().array(), 0, data, 0, packet.len);
		data[packet.len] = (byte) (packet.indices & 0xff);
		data[packet.len + 1] = 2;
		System.arraycopy(strBytes, 0, data, packet.len + 6, strBytes.length);
		packet.setData(ByteBuffer.wrap(data));
		packet.getData().order(ByteOrder.LITTLE_ENDIAN);
		packet.getData().putInt(packlen + 2, strBytes.length);
		packet.getData().order(ByteOrder.BIG_ENDIAN);
		packet.indices++;
		return packet;
	}
	
	public static GamePacket packetEnd(GamePacket p) {
		// Copy the array from data to array n
		byte[] n = new byte[p.len + 1];
		System.arraycopy(p.getData().array(), 0, n, 0, p.len);

		// Copy array zero to n or append 0
		byte[] zero = {0};
		System.arraycopy(zero, 0, n, p.len, 1);

		p.getData().position(0);
		p.setData(ByteBuffer.wrap(n));
		p.getData().order(ByteOrder.LITTLE_ENDIAN);
		p.getData().putInt(56, p.indices);
		p.getData().put(60, (byte)(p.indices & 0xff));
		p.getData().order(ByteOrder.BIG_ENDIAN);
		p.getData().position(0);
		return p;
	}

	public static ByteBuffer createDataPacket(byte[] num, byte[] data, int len) {
		/*
		System.arraycopy(sourceArray,
                 sourceStartIndex,
                 targetArray,
                 targetStartIndex,
                 length);

         memcpy(dest, source, length)
		 */
		// Basically append number to array n on index 4
		byte[] n = new byte[len + 5];
		System.arraycopy(num, 0, n, 0, len);
		if (data != null) {
			System.arraycopy(data, 0, n, 4, len);
		}
		System.arraycopy(new byte[]{0}, 0, n, 4 + len, 1);
		return ByteBuffer.wrap(n);
	}
}
