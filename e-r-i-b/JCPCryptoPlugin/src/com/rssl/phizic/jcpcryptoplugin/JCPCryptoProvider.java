package com.rssl.phizic.jcpcryptoplugin;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import static com.rssl.phizic.jcpcryptoplugin.JCPConstants.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.crypto.CheckSignatureResult;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.utils.StringHelper;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Iterator;

/**
 * @author Erkin
 * @ created 15.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация криптопровайдера JCP (Java Crypto Provider)
 */
class JCPCryptoProvider implements CryptoProvider
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	///////////////////////////////////////////////////////////////////////////

	public com.rssl.phizic.security.crypto.Signature makeSignature(String certId, String data) throws SecurityException
	{
		if (StringHelper.isEmpty(data))
			throw new IllegalArgumentException("Аргумент 'data' не может быть пустым");

		if (StringHelper.isEmpty(certId))
			throw new SecurityException("Не указан ID закрытого ключа. ");

		log.trace("Создание ЭЦП закрытым ключом " + certId);

		// 1. Загружаем закрытый ключ
		PrivateKey privateKey = loadPrivateKey(certId);
		if (privateKey == null)
			throw new SecurityException("Не найден закрытый ключ/сертификат " + certId);

		// 2. Получаем составитель ЭЦП
		String algName = ConfigFactory.getConfig(CriptoProviderConfig.class).getAlgorithmName(certId);
		Signature signatureMaker;
		try
		{
			signatureMaker = Signature.getInstance(algName, PROVIDER_NAME);
			signatureMaker.initSign(privateKey);
		}
		catch (NoSuchProviderException ex)
		{
			throw new SecurityException("Не найден криптопровайдер " + PROVIDER_NAME, ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new SecurityException("Криптопровайдер не реализует алгоритм " + algName, ex);
		}
		catch (InvalidKeyException ex)
		{
			// Исключение может быть брошено,
			// если в <signatureMaker>.initSign() был передан неправильный ключ
			throw new IllegalArgumentException(ex);
		}

		// 3. Получаем ЭЦП
		try
		{
			signatureMaker.update(data.getBytes("UTF-8"));
		}
		catch (SignatureException ex)
		{
			// Исключение может быть брошено,
			// если <signatureMaker> не инициализирован с помощью метода initSign()
			throw new IllegalStateException(ex);
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new SecurityException("В системе отсутствует поддержка UTF-8", ex);
		}

		try
		{
			byte[] result = signatureMaker.sign();
			return com.rssl.phizic.security.crypto.Signature.fromBinary(result);
		}
		catch (SignatureException ex)
		{
			// Исключение может быть брошено,
			// - если <signatureMaker> не инициализирован с помощью метода initSign()
			// - если алгоритм шифрования не справился с составлением подписи
			throw new SecurityException("Сбой при составлении ЭЦП. " +
					"Криптопровайдер: " + PROVIDER_NAME + ", " +
					"Алгоритм: " + algName + ", " +
					"Закрытый ключ: " + certId, ex);
		}
	}

	public CheckSignatureResult checkSignature(String certId, String data, com.rssl.phizic.security.crypto.Signature signature)
			throws SecurityException
	{
		if (StringHelper.isEmpty(data))
			throw new IllegalArgumentException("Аргумент 'data' не может быть пустым");
		if (signature == null)
			throw new NullPointerException("Аргумент 'signature' не может быть null");
		if (StringHelper.isEmpty(certId))
			throw new SecurityException("Не указан ID открытого ключа. ");

		log.trace("Проверка ЭЦП открытым сертификатом " + certId);

		// 1. Получаем сертификат из хранилища
		// Для получения открытого ключа пароль не нужен
		Certificate certificate = loadCertificate(certId);
		if (certificate == null)
			throw new SecurityException("Не найден сертификат " + certId);

		String algName = ConfigFactory.getConfig(CriptoProviderConfig.class).getAlgorithmName(certId);
		Signature signatureValidator;
		try
		{
			// 2. Подгружаем верификатор ЭЦП
			PublicKey publicKey = certificate.getPublicKey();
//			System.out.println("Public key: " + new sun.misc.BASE64Encoder().encode(publicKey.getEncoded()));
			signatureValidator = Signature.getInstance(algName, PROVIDER_NAME);
			signatureValidator.initVerify(publicKey);

		}
		catch (NoSuchProviderException ex)
		{
			throw new SecurityException("Не найден криптопровайдер " + PROVIDER_NAME, ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new SecurityException("Криптопровайдер не реализует алгоритм " + algName, ex);
		}
		catch (InvalidKeyException ex)
		{
			// Исключение может быть брошено,
			// если в <signatureValidator>.initVerify() был передан неправильный ключ
			throw new IllegalArgumentException(ex);
		}

		// 3. Проверяем ЭЦП
		try
		{
			signatureValidator.update(data.getBytes("UTF-8"));
		}
		catch (SignatureException ex)
		{
			// Исключение может быть брошено,
			// если <signatureValidator> не инициализирован с помощью метода initSign()
			throw new IllegalStateException(ex);
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new SecurityException("В системе отсутствует поддержка UTF-8", ex);
		}

		try
		{
			if (!signatureValidator.verify(signature.getBytes()))
				return makeFailCheckSignatureResult();
			else return makeSuccessfulCheckSignatureResult();
		}
		catch (SignatureException ex)
		{
			// Исключение может быть брошено,
			// - если указана кривая ЭЦП,
			// - если указана ЭЦП неприемлемого типа,
			// - если алгоритм шифрования не справился с задачей проверки подписи
			throw new SecurityException("Сбой при проверке ЭЦП. " +
					"Криптопровайдер: " + PROVIDER_NAME + ", " +
					"Алгоритм: " + algName + ", " +
					"Сертификат: " + certId, ex);
		}
	}

	/**
	 * Загружает сертификат из хранилища сертификатов
	 * @param certId - ID сертификата в хранилище
	 * @return сертификат либо null, если сертификата с указанным алиасом нет в хранилище
	 */
	private Certificate loadCertificate(String certId) throws SecurityException
	{
		// 1. Загружаем хранилище сертификатов и ключей
		KeyStore store = loadCertificateStore();

		// 2. Из хранилища вынимаем сертификат
		try
		{
			return store.getCertificate(certId);
		}
		catch (KeyStoreException ex)
		{
			// Исключение может быть брошено,
			// если хранилище сертификатов не инициализировано с помощью метода load()
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * Загружает закрытый ключ с ключевого носителя
	 * @param keyId - ID ключа в хранилище (фактически имя ключевого контейнера)
	 * @return закрытый ключ либо null, если ключа с указанным ID нет
	 */
	private PrivateKey loadPrivateKey(String keyId) throws SecurityException
	{
		String password = ConfigFactory.getConfig(CriptoProviderConfig.class).getPassword();
		char[] passwordAsChars = !StringHelper.isEmpty(password) ? password.toCharArray() : null;

		// 1. Загружаем ключевой носитель
		KeyStore keyStore = loadKeyStore();

		// 2. Получаем закрытый ключ
		try
		{
			return (PrivateKey)keyStore.getKey(keyId, passwordAsChars);
		}
		catch (NoSuchAlgorithmException ex)
		{
			// Не доступен алгоритм получения закрытого ключа
			throw new SecurityException("Сбой при получении закрытого ключа " + keyId + ". ", ex);
		}
		catch (UnrecoverableKeyException ex)
		{
			throw new SecurityException("Сбой при получении закрытого ключа " + keyId + ". " +
					"Указан неправильный пароль", ex);
		}
		catch (KeyStoreException ex)
		{
			// Исключение может быть брошено,
			// если хранилище сертификатов не инициализировано с помощью метода load()
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * Загружает ключевой носитель
	 * @return ключевой носитель (never null)
	 */
	private KeyStore loadKeyStore() throws SecurityException
	{
		try
		{
			KeyStore keyStore = KeyStore.getInstance(HDIMAGE_STORE, PROVIDER_NAME);
			keyStore.load(null);
			return keyStore;
		}
		catch (NoSuchProviderException ex)
		{
			throw new SecurityException("Не доступен криптопровайдер " + PROVIDER_NAME, ex);
		}
		catch (KeyStoreException ex)
		{
			// Провайдер не поддерживает работу с носителем типа HDIMAGE_STORE
			throw new SecurityException("Сбой при загрузке ключевого носителя", ex);
		}
		catch (CertificateException ex)
		{
			// Хранилище поломано либо имеет недопустимый формат
			throw new SecurityException("Сбой при загрузке ключевого носителя", ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			// Не доступен алгоритм, контролирующий целостность хранилища
			throw new SecurityException("Сбой при загрузке ключевого носителя", ex);
		}
		catch (IOException ex)
		{
			throw new SecurityException("Сбой при загрузке ключевого носителя", ex);
		}
	}

	/**
	 * Загружает хранилище сертификатов
	 * @return хранилище сертификатов (never null)
	 */
	private KeyStore loadCertificateStore() throws SecurityException
	{
		String storePath = ConfigFactory.getConfig(CriptoProviderConfig.class).getStorePath();
		if (StringHelper.isEmpty(storePath))
			throw new SecurityException("В файле настроек не указан путь к хранилищу сертификатов. " +
					"Проверь настройку " + CriptoProviderConfig.CERTIFICATE_STORE_PATH_KEY);

		String password = ConfigFactory.getConfig(CriptoProviderConfig.class).getCertificatePassword();
		char[] passwordAsChars = !StringHelper.isEmpty(password) ? password.toCharArray() : null;

		InputStream storeStream = null;
		try
		{
			//noinspection IOResourceOpenedButNotSafelyClosed
			storeStream = new FileInputStream(storePath);
			KeyStore keyStore = KeyStore.getInstance(HDIMAGE_STORE, PROVIDER_NAME);
			keyStore.load(storeStream, passwordAsChars);
			return keyStore;
		}
		catch (NoSuchProviderException ex)
		{
			throw new SecurityException("Не доступен криптопровайдер " + PROVIDER_NAME, ex);
		}
		catch (KeyStoreException ex)
		{
			// Провайдер не поддерживает работу с хранилищем типа HDIMAGE_STORE
			throw new SecurityException("Сбой при загрузке хранилища сертификатов", ex);
		}
		catch (CertificateException ex)
		{
			// Хранилище CERTIFICATE_STORE_FILE поломано (либо имеет недопустимый формат)
			throw new SecurityException("Сбой при загрузке хранилища сертификатов", ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			// Недоступен алгоритм, контролирующий целостность хранилища
			throw new SecurityException("Сбой при загрузке хранилища сертификатов", ex);
		}
		catch (FileNotFoundException ex)
		{
			// Не найдено хранилище сертификатов
			throw new SecurityException("Сбой при загрузке хранилища сертификатов. " +
					"Проверь налиличие хранилища сертификатов по пути " + storePath + " " +
					"(см. настройку " + CriptoProviderConfig.CERTIFICATE_STORE_PATH_KEY + ")", ex);
		}
		catch (IOException ex)
		{
			// Ошибка чтения потока / не указан пароль / пароль неверен
			throw new SecurityException("Сбой при загрузке хранилища сертификатов. " +
					"Проверь пароль к хранилищу в настройке " + CriptoProviderConfig.CERTIFICATE_PASSWORD_PROPERTY_KEY, ex);
		}
		finally
		{
			if (storeStream != null)
				try { storeStream.close(); } catch (IOException ignore) {}
		}
	}

	private static CheckSignatureResult makeSuccessfulCheckSignatureResult()
	{
		CheckSignatureResult result = new CheckSignatureResult();
		result.setSuccessful(true);
		// TODO: реализовать в рамках запроса TAS025204
		result.setCertificate(null);
		result.setSignTime(null);
		return result;
	}

	private static CheckSignatureResult makeFailCheckSignatureResult()
	{
		CheckSignatureResult result = new CheckSignatureResult();
		result.setSuccessful(false);
		return result;
	}

	public com.rssl.phizic.security.crypto.Certificate findCertificate(String certId)
	{
		// TODO: реализовать в рамках запроса TAS025204
		throw new UnsupportedOperationException();
	}

	public Iterator<com.rssl.phizic.security.crypto.Certificate> enumerateCertificates()
	{
		// TODO: реализовать в рамках запроса TAS025204
		throw new UnsupportedOperationException();
	}

	public Iterator<com.rssl.phizic.security.crypto.CertificateRequest> enumerateCertRequest()
	{
		// TODO: реализовать в рамках запроса TAS025204
		throw new UnsupportedOperationException();
	}

	public void loadCertFromFileToStorage(byte[] data)
	{
		// TODO: реализовать в рамках запроса TAS025204
		throw new UnsupportedOperationException();
	}

	public com.rssl.phizic.security.crypto.Certificate getCertInFileInfo(byte[] data)
	{
		// TODO: реализовать в рамках запроса TAS025204
		throw new UnsupportedOperationException();
	}

	public void deleteCertificate(com.rssl.phizic.security.crypto.Certificate cert)
	{
		// TODO: реализовать в рамках запроса TAS025204
		throw new UnsupportedOperationException();
	}
}
