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
 * ���������� ���������������� JCP (Java Crypto Provider)
 */
class JCPCryptoProvider implements CryptoProvider
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	///////////////////////////////////////////////////////////////////////////

	public com.rssl.phizic.security.crypto.Signature makeSignature(String certId, String data) throws SecurityException
	{
		if (StringHelper.isEmpty(data))
			throw new IllegalArgumentException("�������� 'data' �� ����� ���� ������");

		if (StringHelper.isEmpty(certId))
			throw new SecurityException("�� ������ ID ��������� �����. ");

		log.trace("�������� ��� �������� ������ " + certId);

		// 1. ��������� �������� ����
		PrivateKey privateKey = loadPrivateKey(certId);
		if (privateKey == null)
			throw new SecurityException("�� ������ �������� ����/���������� " + certId);

		// 2. �������� ����������� ���
		String algName = ConfigFactory.getConfig(CriptoProviderConfig.class).getAlgorithmName(certId);
		Signature signatureMaker;
		try
		{
			signatureMaker = Signature.getInstance(algName, PROVIDER_NAME);
			signatureMaker.initSign(privateKey);
		}
		catch (NoSuchProviderException ex)
		{
			throw new SecurityException("�� ������ ��������������� " + PROVIDER_NAME, ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new SecurityException("��������������� �� ��������� �������� " + algName, ex);
		}
		catch (InvalidKeyException ex)
		{
			// ���������� ����� ���� �������,
			// ���� � <signatureMaker>.initSign() ��� ������� ������������ ����
			throw new IllegalArgumentException(ex);
		}

		// 3. �������� ���
		try
		{
			signatureMaker.update(data.getBytes("UTF-8"));
		}
		catch (SignatureException ex)
		{
			// ���������� ����� ���� �������,
			// ���� <signatureMaker> �� ��������������� � ������� ������ initSign()
			throw new IllegalStateException(ex);
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new SecurityException("� ������� ����������� ��������� UTF-8", ex);
		}

		try
		{
			byte[] result = signatureMaker.sign();
			return com.rssl.phizic.security.crypto.Signature.fromBinary(result);
		}
		catch (SignatureException ex)
		{
			// ���������� ����� ���� �������,
			// - ���� <signatureMaker> �� ��������������� � ������� ������ initSign()
			// - ���� �������� ���������� �� ��������� � ������������ �������
			throw new SecurityException("���� ��� ����������� ���. " +
					"���������������: " + PROVIDER_NAME + ", " +
					"��������: " + algName + ", " +
					"�������� ����: " + certId, ex);
		}
	}

	public CheckSignatureResult checkSignature(String certId, String data, com.rssl.phizic.security.crypto.Signature signature)
			throws SecurityException
	{
		if (StringHelper.isEmpty(data))
			throw new IllegalArgumentException("�������� 'data' �� ����� ���� ������");
		if (signature == null)
			throw new NullPointerException("�������� 'signature' �� ����� ���� null");
		if (StringHelper.isEmpty(certId))
			throw new SecurityException("�� ������ ID ��������� �����. ");

		log.trace("�������� ��� �������� ������������ " + certId);

		// 1. �������� ���������� �� ���������
		// ��� ��������� ��������� ����� ������ �� �����
		Certificate certificate = loadCertificate(certId);
		if (certificate == null)
			throw new SecurityException("�� ������ ���������� " + certId);

		String algName = ConfigFactory.getConfig(CriptoProviderConfig.class).getAlgorithmName(certId);
		Signature signatureValidator;
		try
		{
			// 2. ���������� ����������� ���
			PublicKey publicKey = certificate.getPublicKey();
//			System.out.println("Public key: " + new sun.misc.BASE64Encoder().encode(publicKey.getEncoded()));
			signatureValidator = Signature.getInstance(algName, PROVIDER_NAME);
			signatureValidator.initVerify(publicKey);

		}
		catch (NoSuchProviderException ex)
		{
			throw new SecurityException("�� ������ ��������������� " + PROVIDER_NAME, ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new SecurityException("��������������� �� ��������� �������� " + algName, ex);
		}
		catch (InvalidKeyException ex)
		{
			// ���������� ����� ���� �������,
			// ���� � <signatureValidator>.initVerify() ��� ������� ������������ ����
			throw new IllegalArgumentException(ex);
		}

		// 3. ��������� ���
		try
		{
			signatureValidator.update(data.getBytes("UTF-8"));
		}
		catch (SignatureException ex)
		{
			// ���������� ����� ���� �������,
			// ���� <signatureValidator> �� ��������������� � ������� ������ initSign()
			throw new IllegalStateException(ex);
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new SecurityException("� ������� ����������� ��������� UTF-8", ex);
		}

		try
		{
			if (!signatureValidator.verify(signature.getBytes()))
				return makeFailCheckSignatureResult();
			else return makeSuccessfulCheckSignatureResult();
		}
		catch (SignatureException ex)
		{
			// ���������� ����� ���� �������,
			// - ���� ������� ������ ���,
			// - ���� ������� ��� ������������� ����,
			// - ���� �������� ���������� �� ��������� � ������� �������� �������
			throw new SecurityException("���� ��� �������� ���. " +
					"���������������: " + PROVIDER_NAME + ", " +
					"��������: " + algName + ", " +
					"����������: " + certId, ex);
		}
	}

	/**
	 * ��������� ���������� �� ��������� ������������
	 * @param certId - ID ����������� � ���������
	 * @return ���������� ���� null, ���� ����������� � ��������� ������� ��� � ���������
	 */
	private Certificate loadCertificate(String certId) throws SecurityException
	{
		// 1. ��������� ��������� ������������ � ������
		KeyStore store = loadCertificateStore();

		// 2. �� ��������� �������� ����������
		try
		{
			return store.getCertificate(certId);
		}
		catch (KeyStoreException ex)
		{
			// ���������� ����� ���� �������,
			// ���� ��������� ������������ �� ���������������� � ������� ������ load()
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * ��������� �������� ���� � ��������� ��������
	 * @param keyId - ID ����� � ��������� (���������� ��� ��������� ����������)
	 * @return �������� ���� ���� null, ���� ����� � ��������� ID ���
	 */
	private PrivateKey loadPrivateKey(String keyId) throws SecurityException
	{
		String password = ConfigFactory.getConfig(CriptoProviderConfig.class).getPassword();
		char[] passwordAsChars = !StringHelper.isEmpty(password) ? password.toCharArray() : null;

		// 1. ��������� �������� ��������
		KeyStore keyStore = loadKeyStore();

		// 2. �������� �������� ����
		try
		{
			return (PrivateKey)keyStore.getKey(keyId, passwordAsChars);
		}
		catch (NoSuchAlgorithmException ex)
		{
			// �� �������� �������� ��������� ��������� �����
			throw new SecurityException("���� ��� ��������� ��������� ����� " + keyId + ". ", ex);
		}
		catch (UnrecoverableKeyException ex)
		{
			throw new SecurityException("���� ��� ��������� ��������� ����� " + keyId + ". " +
					"������ ������������ ������", ex);
		}
		catch (KeyStoreException ex)
		{
			// ���������� ����� ���� �������,
			// ���� ��������� ������������ �� ���������������� � ������� ������ load()
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * ��������� �������� ��������
	 * @return �������� �������� (never null)
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
			throw new SecurityException("�� �������� ��������������� " + PROVIDER_NAME, ex);
		}
		catch (KeyStoreException ex)
		{
			// ��������� �� ������������ ������ � ��������� ���� HDIMAGE_STORE
			throw new SecurityException("���� ��� �������� ��������� ��������", ex);
		}
		catch (CertificateException ex)
		{
			// ��������� �������� ���� ����� ������������ ������
			throw new SecurityException("���� ��� �������� ��������� ��������", ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			// �� �������� ��������, �������������� ����������� ���������
			throw new SecurityException("���� ��� �������� ��������� ��������", ex);
		}
		catch (IOException ex)
		{
			throw new SecurityException("���� ��� �������� ��������� ��������", ex);
		}
	}

	/**
	 * ��������� ��������� ������������
	 * @return ��������� ������������ (never null)
	 */
	private KeyStore loadCertificateStore() throws SecurityException
	{
		String storePath = ConfigFactory.getConfig(CriptoProviderConfig.class).getStorePath();
		if (StringHelper.isEmpty(storePath))
			throw new SecurityException("� ����� �������� �� ������ ���� � ��������� ������������. " +
					"������� ��������� " + CriptoProviderConfig.CERTIFICATE_STORE_PATH_KEY);

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
			throw new SecurityException("�� �������� ��������������� " + PROVIDER_NAME, ex);
		}
		catch (KeyStoreException ex)
		{
			// ��������� �� ������������ ������ � ���������� ���� HDIMAGE_STORE
			throw new SecurityException("���� ��� �������� ��������� ������������", ex);
		}
		catch (CertificateException ex)
		{
			// ��������� CERTIFICATE_STORE_FILE �������� (���� ����� ������������ ������)
			throw new SecurityException("���� ��� �������� ��������� ������������", ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			// ���������� ��������, �������������� ����������� ���������
			throw new SecurityException("���� ��� �������� ��������� ������������", ex);
		}
		catch (FileNotFoundException ex)
		{
			// �� ������� ��������� ������������
			throw new SecurityException("���� ��� �������� ��������� ������������. " +
					"������� ��������� ��������� ������������ �� ���� " + storePath + " " +
					"(��. ��������� " + CriptoProviderConfig.CERTIFICATE_STORE_PATH_KEY + ")", ex);
		}
		catch (IOException ex)
		{
			// ������ ������ ������ / �� ������ ������ / ������ �������
			throw new SecurityException("���� ��� �������� ��������� ������������. " +
					"������� ������ � ��������� � ��������� " + CriptoProviderConfig.CERTIFICATE_PASSWORD_PROPERTY_KEY, ex);
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
		// TODO: ����������� � ������ ������� TAS025204
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
		// TODO: ����������� � ������ ������� TAS025204
		throw new UnsupportedOperationException();
	}

	public Iterator<com.rssl.phizic.security.crypto.Certificate> enumerateCertificates()
	{
		// TODO: ����������� � ������ ������� TAS025204
		throw new UnsupportedOperationException();
	}

	public Iterator<com.rssl.phizic.security.crypto.CertificateRequest> enumerateCertRequest()
	{
		// TODO: ����������� � ������ ������� TAS025204
		throw new UnsupportedOperationException();
	}

	public void loadCertFromFileToStorage(byte[] data)
	{
		// TODO: ����������� � ������ ������� TAS025204
		throw new UnsupportedOperationException();
	}

	public com.rssl.phizic.security.crypto.Certificate getCertInFileInfo(byte[] data)
	{
		// TODO: ����������� � ������ ������� TAS025204
		throw new UnsupportedOperationException();
	}

	public void deleteCertificate(com.rssl.phizic.security.crypto.Certificate cert)
	{
		// TODO: ����������� � ������ ������� TAS025204
		throw new UnsupportedOperationException();
	}
}
