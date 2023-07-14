package com.rssl.phizic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * User: Moshenko
 * Date: 15.07.2011
 * Time: 10:54:03
 * класс для шифрования/дешифрования
 * работы с сертификатами,
 * цифровыми отпечатками
 */
public class CihperHelper
{
	/**
     * Получить шифратор(RSA)
     * @param cert      сертификат публичного ключа
     * @param mode      режим (шифрование/дешифрование)
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     *
     */
    public Cipher getChipherRSA(X509Certificate cert, int mode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException
	{
        RSAPublicKey pub = (RSAPublicKey) cert.getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(mode, pub);
        return cipher;
    }

	/**
     * Получить подпись
     * @param priv приватный ключ
     * @return подпись
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.InvalidKeyException
     */
	public Signature getSignatureRSA(RSAPrivateKey priv) throws NoSuchAlgorithmException, InvalidKeyException

	{
        Signature sig = Signature.getInstance("SHA1WithRSA");
		sig.initSign(priv);
		return sig;
    }

	 /**
     * Получить сертификат(X509) из входного потока
     * @param inputStream входной поток от хранилища
     * @param keyStroreType тип хранилища
     * @param password пароль от хранилища
     * @param alias алиас сертификата
     * @return X509Certificate
     * @throws NoSuchAlgorithmException
     * @throws java.security.cert.CertificateException
     */
    public X509Certificate getCertX509(InputStream inputStream,String password,String keyStroreType,String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException
	 {
		KeyStore keyStore =  KeyStore.getInstance(keyStroreType);
        keyStore.load(inputStream,password.toCharArray());
        return (X509Certificate) keyStore.getCertificate(alias);
    }
	 /**
     * Получить приватный ключ
     * @param inputStream входной поток от хранилища
     * @param keyStroreType тип хранилища
     * @param password пароль от хранилища
     * @param alias алиас сертификата
     * @return RSAPrivateKey
     * @throws NoSuchAlgorithmException
     * @throws java.security.cert.CertificateException
     */
    public RSAPrivateKey getPrivateKey(InputStream inputStream,String password,String keyStroreType,String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException
	 {
		KeyStore keyStore =  KeyStore.getInstance(keyStroreType);
        keyStore.load(inputStream, password.toCharArray());
        return  (RSAPrivateKey) keyStore.getKey(alias, password.toCharArray());
    }

	/**
     * Получить отпечаток строки(SHA-1)
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

	 /**
     * Преобразуем входной массив в HEX представление
     * @param data
     * @return
     */
    private String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
