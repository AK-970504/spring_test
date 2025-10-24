package jp.co.sss.spring_test.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordsUtil {
	// 設定値
	private static final int SALT_LENGTH = 16;  // 16バイト
	private static final int ITERATIONS = 65536; // 繰り返し回数
	private static final int KEY_LENGTH = 256;  // 256ビット（SHA-256）
	/**
	 * パスワードをハッシュ化（PBKDF2 with HmacSHA256）
	 * @param passwords プレーンパスワード
	 * @return Base64エンコード済みの "salt:hash"
	 */
	public static String hashPasswords(String passwords) {
		try {
			// ソルト生成
			byte[] salt = new byte[SALT_LENGTH];
			SecureRandom random = new SecureRandom();
			random.nextBytes(salt);
			// ハッシュ生成
			byte[] hash = pbkdf2(passwords.toCharArray(), salt);
			// ソルトとハッシュを Base64 エンコードして結合
			return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			throw new RuntimeException("パスワードハッシュ化中にエラーが発生しました", e);
		}
	}
	/**
	 * パスワードの照合
	 * @param passwords 入力された平文パスワード
	 * @param storedHash DBに保存されている"salt:hash"
	 * @return 一致していれば true
	 */
	public static boolean verifyPasswords(String passwords, String storedHash) {
		try {
			String[] parts = storedHash.split(":");
			byte[] salt = Base64.getDecoder().decode(parts[0]);
			byte[] expectedHash = Base64.getDecoder().decode(parts[1]);
			byte[] actualHash = pbkdf2(passwords.toCharArray(), salt);
			return slowEquals(expectedHash, actualHash);
		} catch (Exception e) {
			return false;
		}
	}
	// PBKDF2ハッシュ生成
	private static byte[] pbkdf2(char[] passwords, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(passwords, salt, ITERATIONS, KEY_LENGTH);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		return skf.generateSecret(spec).getEncoded();
	}
	// 時間的に安全な比較
	private static boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++) {
			diff |= a[i] ^ b[i];
		}
		return diff == 0;
	}
}