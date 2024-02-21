package com.prayer.live.im.core.server.common;

import com.prayer.live.im.constants.ImConstants;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 15:58
 **/
public class ImMsg implements Serializable {
	@Serial
	private static final long serialVersionUID = -543462286047032621L;

	private short magic;
	private int code;
	private int len;
	private byte[] body;


	public static ImMsg build(int code, String data){
		ImMsg imMsg = new ImMsg();
		imMsg.setMagic(ImConstants.DEFAULT_MAGIC);
		imMsg.setCode(code);
		imMsg.setBody(data.getBytes());
		imMsg.setLen(imMsg.getBody().length);
		return imMsg;
	}

	public short getMagic() {
		return magic;
	}

	public void setMagic(short magic) {
		this.magic = magic;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ImMsg{" +
			"magic=" + magic +
			", code=" + code +
			", len=" + len +
			", body=" + Arrays.toString(body) +
			'}';
	}
}
