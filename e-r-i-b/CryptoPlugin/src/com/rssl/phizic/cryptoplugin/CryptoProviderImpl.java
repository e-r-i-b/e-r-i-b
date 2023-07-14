package com.rssl.phizic.cryptoplugin;

import com.rssl.crypto.Plugin;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.crypto.*;

import java.util.Iterator;

/**
 * @author Omeliyanchuk
 * @ created 28.02.2007
 * @ $Author$
 * @ $Revision$
 */

@Deprecated
//методы checkSignature и makeSignature не реализованы с учетом использования certId
public class CryptoProviderImpl implements CryptoProvider
{
	private Plugin cryptoPlugin;

	/**
	 * ctor
	 * @param plugin криптоплагин
	 */
	public CryptoProviderImpl(Plugin plugin)
	{
		this.cryptoPlugin = plugin;
	}

	public Certificate findCertificate(String certId) throws SecurityLogicException
	{
		try
		{
			Plugin.Template template = cryptoPlugin.getTemplate(Plugin.KeyObjType.CERT_ANY);
			template.addField("id", certId);
			Plugin.Certificate templCertificate = template.getObject();
			return new CertificateImpl(cryptoPlugin.findObject(templCertificate));
		}
		catch (Plugin.Failure ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public Signature makeSignature(String certId, String data)
	{
		throw new UnsupportedOperationException();
	}

	public CheckSignatureResult checkSignature(String certId, String data, Signature signature) throws SecurityException
	{
        throw new UnsupportedOperationException("не умею работать с certId");
	}

	public Iterator<Certificate> enumerateCertificates()
	{
		return null;
	}

	public Iterator<CertificateRequest> enumerateCertRequest()
	{
		return null;
	}

	/**
	 * загружаем файл с сертификатом в хранилище.
	 * @param data данные из файла
	 */
	public void loadCertFromFileToStorage(byte[] data)
	{
		try
		{
			cryptoPlugin.loadCertData(data);
		}
		catch (Plugin.Failure ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * получить информацию о сертификате
	 * @param data данные из файла
	 * @return сертификат прочитанный из массива
	 */
	public Certificate getCertInFileInfo(byte[] data)
	{
		try
		{
			return new CertificateImpl(cryptoPlugin.getObjectFromExchange(data));
		}
		catch (Plugin.Failure ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * удаляет сертификат из хранилища
	 * @param cert сетрификат который следует удалить
	 */
	public void deleteCertificate(Certificate cert)
	{
		try
		{
			Plugin.Template template = cryptoPlugin.getTemplate(Plugin.KeyObjType.CERT_ANY);
			template.addField("id", cert.getId());
			cryptoPlugin.deleteObject(template.getObject());
		}
		catch (Plugin.Failure ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Освобождает тесурсы связанные с криптопровайдером.
	 * После вызова метода дальнейшее обращение к методам невозможно.
	 */
	public synchronized void release()
	{
		cryptoPlugin.release();
		cryptoPlugin = null;
	}

	private CheckSignatureResult convertSignature(Plugin.Signature[] signs) throws Plugin.Failure
	{
		if ((signs == null) || (signs.length == 0))
		{
			CheckSignatureResult res = new CheckSignatureResult();
			res.setSignTime(null);
			res.setSuccessful(false);
			res.setCertificate(null);
			return res;
		}
		else
		{
			Plugin.Signature sign = signs[0];
			CheckSignatureResult res = new CheckSignatureResult();
			CertificateImpl cert = new CertificateImpl(sign.getCertificate());
			res.setSignTime(sign.getDate());
			res.setSuccessful(sign.getIsTrue());
			res.setCertificate(cert);
			return res;
		}
	}
}
