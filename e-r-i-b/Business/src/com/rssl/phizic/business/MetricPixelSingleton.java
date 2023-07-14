package com.rssl.phizic.business;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.resources.ResourceHelper;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author saharnova
 * @ created 30.12.14
 * @ $Author$
 * @ $Revision$
 */

public final class MetricPixelSingleton
{
	private static final String FILE_NAME = "pixel_public_key.der";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final MetricPixelSingleton instance = new MetricPixelSingleton();
	private final PublicKey publicKey;

	private MetricPixelSingleton()
	{
		PublicKey key = null;
		try
		{
			key = getPublicKeyFromFile();
		}
		catch (Exception e)
		{
			log.warn("Не удалось получить открытый ключ шифрования RSA из файла", e);
		}
		publicKey = key;
	}

	private PublicKey getPublicKeyFromFile() throws Exception
	{
		InputStream fis = null;
		try
		{
			fis = ResourceHelper.loadResourceAsStream(FILE_NAME);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(IOUtils.toByteArray(fis));
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey publicKey = null;
			publicKey = kf.generatePublic(spec);
			return publicKey;
		}
		finally
		{
			fis.close();
		}
	}

	/**
	 * @return  ключ для шифрования RSA для метрики Pixel
	 */
	public static PublicKey getPublicKey()
	{
		return instance.publicKey;
	}
}
