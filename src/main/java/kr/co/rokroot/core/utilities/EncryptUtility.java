/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.utilities;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

public class EncryptUtility {

	public static String encode(CharSequence rawPassword) {
		return sha256Hex(rawPassword.toString());
	}

	public static boolean matches(CharSequence rawPassword, String encodedPassword) {
		return StringUtils.equals(sha256Hex(rawPassword.toString()), encodedPassword);
	}


	protected static String sha256Hex(String str) {
		String result = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());

			byte[] byteData = md.digest();

			// convert to hex code
			StringBuilder sb = new StringBuilder();
			for (byte byteDatum : byteData) {
				sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
			}

			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}