package com.camelot.kuka.common.utils.pay.payment.common.security;

/**
 *  
 * @description 
 * 		
 * @author  
 *
 * @modified_by 
 */
public final class Base64Coder implements Coder {

	protected static boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\r' || c == '\n' || c == '\t';
	}

	protected static boolean isPad(char c) {
		return c == '=';
	}

	protected static boolean isData(char c) {
		return c < '\200' && base64Alphabet[c] != -1;
	}

	protected static boolean isBase64(char c) {
		return isWhiteSpace(c) || isPad(c) || isData(c);
	}

	public String encode(byte abyte0[]) {
		if (abyte0 == null)
			return null;
		int i = abyte0.length * 8;
		if (i == 0)
			return "";
		int j = i % 24;
		int k = i / 24;
		int l = j == 0 ? k : k + 1;
		char ac[] = null;
		ac = new char[l * 4];
		int i1 = 0;
		int j1 = 0;
		for (int k1 = 0; k1 < k; k1++) {
			byte byte5 = abyte0[j1++];
			byte byte8 = abyte0[j1++];
			byte byte10 = abyte0[j1++];
			byte byte3 = (byte) (byte8 & 15);
			byte byte0 = (byte) (byte5 & 3);
			byte byte11 = (byte5 & -128) != 0 ? (byte) (byte5 >> 2 ^ 192) : (byte) (byte5 >> 2);
			byte byte14 = (byte8 & -128) != 0 ? (byte) (byte8 >> 4 ^ 240) : (byte) (byte8 >> 4);
			byte byte16 = (byte10 & -128) != 0 ? (byte) (byte10 >> 6 ^ 252) : (byte) (byte10 >> 6);
			ac[i1++] = lookUpBase64Alphabet[byte11];
			ac[i1++] = lookUpBase64Alphabet[byte14 | byte0 << 4];
			ac[i1++] = lookUpBase64Alphabet[byte3 << 2 | byte16];
			ac[i1++] = lookUpBase64Alphabet[byte10 & 63];
		}

		if (j == 8) {
			byte byte6 = abyte0[j1];
			byte byte1 = (byte) (byte6 & 3);
			byte byte12 = (byte6 & -128) != 0 ? (byte) (byte6 >> 2 ^ 192) : (byte) (byte6 >> 2);
			ac[i1++] = lookUpBase64Alphabet[byte12];
			ac[i1++] = lookUpBase64Alphabet[byte1 << 4];
			ac[i1++] = '=';
			ac[i1++] = '=';
		} else if (j == 16) {
			byte byte7 = abyte0[j1];
			byte byte9 = abyte0[j1 + 1];
			byte byte4 = (byte) (byte9 & 15);
			byte byte2 = (byte) (byte7 & 3);
			byte byte13 = (byte7 & -128) != 0 ? (byte) (byte7 >> 2 ^ 192) : (byte) (byte7 >> 2);
			byte byte15 = (byte9 & -128) != 0 ? (byte) (byte9 >> 4 ^ 240) : (byte) (byte9 >> 4);
			ac[i1++] = lookUpBase64Alphabet[byte13];
			ac[i1++] = lookUpBase64Alphabet[byte15 | byte2 << 4];
			ac[i1++] = lookUpBase64Alphabet[byte4 << 2];
			ac[i1++] = '=';
		}
		return new String(ac);
	}

	public byte[] decode(String s) {
		if (s == null)
			return null;
		char ac[] = s.toCharArray();
		int i = removeWhiteSpace(ac);
		if (i % 4 != 0)
			return null;
		int j = i / 4;
		if (j == 0)
			return new byte[0];
		byte abyte0[] = null;
		byte byte0 = 0;
		byte byte1 = 0;
		char c = '\0';
		char c1 = '\0';
		char c2 = '\0';
		char c3 = '\0';
		int k = 0;
		int l = 0;
		int i1 = 0;
		abyte0 = new byte[j * 3];
		for (; k < j - 1; k++) {
			if (!isData(c = ac[i1++]) || !isData(c1 = ac[i1++]) || !isData(c2 = ac[i1++]) || !isData(c3 = ac[i1++]))
				return null;
			byte0 = base64Alphabet[c];
			byte1 = base64Alphabet[c1];
			byte byte2 = base64Alphabet[c2];
			byte byte5 = base64Alphabet[c3];
			abyte0[l++] = (byte) (byte0 << 2 | byte1 >> 4);
			abyte0[l++] = (byte) ((byte1 & 15) << 4 | byte2 >> 2 & 15);
			abyte0[l++] = (byte) (byte2 << 6 | byte5);
		}

		if (!isData(c = ac[i1++]) || !isData(c1 = ac[i1++]))
			return null;
		byte0 = base64Alphabet[c];
		byte1 = base64Alphabet[c1];
		c2 = ac[i1++];
		c3 = ac[i1++];
		if (!isData(c2) || !isData(c3)) {
			if (isPad(c2) && isPad(c3)) {
				if ((byte1 & 15) != 0)
					return null;
				byte abyte1[] = new byte[k * 3 + 1];
				System.arraycopy(abyte0, 0, abyte1, 0, k * 3);
				abyte1[l] = (byte) (byte0 << 2 | byte1 >> 4);
				return abyte1;
			}
			if (isPad(c2) && isPad(c3))
				return null;
			byte byte3 = base64Alphabet[c2];
			if ((byte3 & 3) != 0)
				return null;
			byte abyte2[] = new byte[k * 3 + 2];
			System.arraycopy(abyte0, 0, abyte2, 0, k * 3);
			abyte2[l++] = (byte) (byte0 << 2 | byte1 >> 4);
			abyte2[l] = (byte) ((byte1 & 15) << 4 | byte3 >> 2 & 15);
			return abyte2;
		}
		byte byte4 = base64Alphabet[c2];
		byte byte6 = base64Alphabet[c3];
		abyte0[l++] = (byte) (byte0 << 2 | byte1 >> 4);
		abyte0[l++] = (byte) ((byte1 & 15) << 4 | byte4 >> 2 & 15);
		abyte0[l++] = (byte) (byte4 << 6 | byte6);
		return abyte0;
	}

	protected static int removeWhiteSpace(char ac[]) {
		if (ac == null)
			return 0;
		int i = 0;
		int j = ac.length;
		for (int k = 0; k < j; k++)
			if (!isWhiteSpace(ac[k]))
				ac[i++] = ac[k];

		return i;
	}

	private static final byte base64Alphabet[];

	private static final char lookUpBase64Alphabet[];

	static {
		base64Alphabet = new byte[128];
		lookUpBase64Alphabet = new char[64];
		for (int i = 0; i < 128; i++)
			base64Alphabet[i] = -1;

		for (int j = 90; j >= 65; j--)
			base64Alphabet[j] = (byte) (j - 65);

		for (int k = 122; k >= 97; k--)
			base64Alphabet[k] = (byte) ((k - 97) + 26);

		for (int l = 57; l >= 48; l--)
			base64Alphabet[l] = (byte) ((l - 48) + 52);

		base64Alphabet[43] = 62;
		base64Alphabet[47] = 63;
		for (int i1 = 0; i1 <= 25; i1++)
			lookUpBase64Alphabet[i1] = (char) (65 + i1);

		int j1 = 26;
		for (int k1 = 0; j1 <= 51; k1++) {
			lookUpBase64Alphabet[j1] = (char) (97 + k1);
			j1++;
		}

		int l1 = 52;
		for (int i2 = 0; l1 <= 61; i2++) {
			lookUpBase64Alphabet[l1] = (char) (48 + i2);
			l1++;
		}

		lookUpBase64Alphabet[62] = '+';
		lookUpBase64Alphabet[63] = '/';
	}
}
