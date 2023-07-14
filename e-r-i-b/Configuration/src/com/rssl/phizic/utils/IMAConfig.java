package com.rssl.phizic.utils;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author bogdanov
 * @ created 22.01.14
 * @ $Author$
 * @ $Revision$
 */

public class IMAConfig extends Config
{
	public static final String IMA_PRODUCT_MODE = "com.rssl.iccs.imaproduct.uploading.mode";
	public static final String IMA_PRODUCT_USED_KINDS = "com.rssl.iccs.imaproduct.kinds.allowed.uploading";
	// Ключ для получения кодов видов ОМС
	public static final String IMA_TYPES_ALLOWED_DOWNLOADING = "com.rssl.iccs.ima.kinds.allowed.downloading";
	private static final String IMA_PRODUCT_TYPES_SEPARATOR = ",";
	private static final String IMA_NUMBER_REGEXP = "com.rssl.iccs.imaccounts.imaccountnumber.regexp";
	private static final String IMA_NUMBER_MASK = "com.rssl.iccs.imaccounts.imaccountnumber.mask";

	private String imaProductMode;
	private String unformatLoadIMAProductInfo;
	private String[] imaTypes;
	private String numberRegexp;
	private String numberMask;

	public IMAConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		imaProductMode = getProperty(IMA_PRODUCT_MODE);
		unformatLoadIMAProductInfo = getProperty(IMA_PRODUCT_USED_KINDS);
		imaTypes = getProperty(IMA_TYPES_ALLOWED_DOWNLOADING).split(IMA_PRODUCT_TYPES_SEPARATOR);
		numberRegexp = getProperty(IMA_NUMBER_REGEXP);
		numberMask = getProperty(IMA_NUMBER_MASK);
	}

	/**
	 * @return режим загрузки ОМС.
	 */
	public String getImaProductMode()
	{
		return imaProductMode;
	}

	/**
	 * @return список разрешенных для загрузки ОМС.
	 */
	public String getUnformatLoadIMAProductInfo()
	{
		return unformatLoadIMAProductInfo;
	}

	public String[] getImaTypes()
	{
		return imaTypes;
	}

	public String getNumberMask()
	{
		return numberMask;
	}

	public String getNumberRegexp()
	{
		return numberRegexp;
	}
}
