package mx.com.mit.paygen.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import mx.com.mit.paygen.exception.AesException;

/**
 * Cifra/descifra un mensaje con algoritmo AES.
 */
public final class AesHelper {
	private static final String ALGORITHM = "AES";

	public static final String MODE = "CBC";
	public static final String PADDING = "PKCS5PADDING";

	/**
	 * Cifra el arreglo de bytes.
	 * 
	 * @param clearBytes Bytes a cifrar.
	 * @param key        Bytes de la llave de cifrado
	 * @param mode       Modo de cifrado
	 * @param padding    Padding a usar
	 * @return arreglo de bytes cifrados.
	 */
	public static byte[] encrypt(byte[] clearBytes, byte[] key, String mode, String padding) {

		SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);

		String transformation = loadTransformation(mode, padding);

		byte[] encrypted = null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encrypted = cipher.doFinal(clearBytes);
			if (cipher.getIV() != null) {
				outputStream.write(cipher.getIV());
			}
			outputStream.write(encrypted);
			encrypted = outputStream.toByteArray();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IOException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e) {
			throw new AesException("Error while encrypting " + e.getMessage());
		}

		return encrypted;
	}

	/**
	 * Descifra encryptedBytes, utilizando la llave proporcionada.
	 * 
	 * @param encryptedBytes Bytes a descifrar
	 * @param key            Bytes de la llave de cifrado
	 * @param mode           Modo de cifrado
	 * @param padding        Padding a usar
	 * @return arreglo de bytes descifrados.
	 */
	public static byte[] decrypt(byte[] encryptedBytes, byte[] key, String mode, String padding) {
		SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);

		String transformation = loadTransformation(mode, padding);

		IvParameterSpec ivSpecs = null;
		if (MODE.equalsIgnoreCase(mode)) {
			byte[] iv = Arrays.copyOfRange(encryptedBytes, 0, 16);
			ivSpecs = new IvParameterSpec(iv);
			encryptedBytes = Arrays.copyOfRange(encryptedBytes, 16, encryptedBytes.length);
		}

		byte[] original = null;
		try {
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpecs);
			original = cipher.doFinal(encryptedBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new AesException("Error while decrypting " + e.getMessage());
		}
		return original;
	}

	private static String loadTransformation(String mode, String padding) {
		String transformation = ALGORITHM;
		transformation += mode == null ? "/ECB" : "/" + mode;
		transformation += padding == null ? "/PKCS5PADDING" : "/" + padding;
		return transformation;
	}

	private String mode;
	private String padding;
	private byte[] rawKey;

	public AesHelper(String encodedKey, String mode, String padding) {
		this.mode = mode;
		this.padding = padding;
		this.rawKey = DatatypeConverter.parseHexBinary(encodedKey);
	}

	/**
	 * Cifra el mensaje en claro, utilizando la llave y los algoritmos de
	 * enmascarado proporcionados.
	 * 
	 * @param clearText Mensaje en claro
	 * @return Mensaje cifrado y ennmascarado por resultEncoder
	 */
	public String encrypt(final String clearText) {
		byte[] cipher = encrypt(clearText.getBytes(StandardCharsets.UTF_8), rawKey, mode, padding);
		return Base64.getEncoder().encodeToString(cipher);
	}

	/**
	 * Descifra un mensaje, utilizando la llave y los algoritmos de enmascarado
	 * proporcionados.
	 * 
	 * @param cipherText mensaje cifrado y enmascarado en base64
	 * @return Mensaje original (en claro)
	 */
	public String decrypt(final String cipherText) {
		byte bytes[] = Base64.getDecoder().decode(cipherText);
		byte[] clear = decrypt(bytes, rawKey, mode, padding);
		return new String(clear, StandardCharsets.UTF_8);
	}
}
