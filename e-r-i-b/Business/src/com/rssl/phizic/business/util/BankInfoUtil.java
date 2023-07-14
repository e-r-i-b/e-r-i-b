package com.rssl.phizic.business.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Mescheryakova
 * @ created 24.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class BankInfoUtil extends Config
{
	public static final String FNS_REGISTRATION_URL = "com.rssl.iccs.fns.registration.url";
	private static final String BANK_INFO_URL_PREFIX = "com.rssl.iccs.";

	private static final String PROPERTY_KEY = "com.rssl.iccs.check.bank.name";
	public static final String BANK_LOAN_LINK_KEY = "com.rssl.business.simple.bankLoanLink";
	public static final String BANK_CARD_LINK_KEY = "com.rssl.business.simple.bankCardLink";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public BankInfoUtil(PropertyReader reader)
	{
		super(reader);
	}

	private static BankInfoUtil getIt()
	{
		return ConfigFactory.getConfig(BankInfoUtil.class);
	}

	public static String getBankNameForPrintCheck()
	{
		return getIt().getProperty(PROPERTY_KEY);
	}

	/**
	 * @return Ссылка на старницу кредитных продуктов на сайте банка
	 */
	public static String getBankLoanLink() throws BusinessException
	{
		return getIt().getProperty(BANK_LOAN_LINK_KEY);
	}

	/**
	 * @return Ссылка на страницу с предложениями по кредитным картам на сайте банка
	 */
	public static String getBankCardLoanLink() throws BusinessException
	{
		return getIt().getProperty(BANK_CARD_LINK_KEY);
	}

	/**
	 * Получение ссылки на сайт ФНС для регистрации предпринимателя
	 * @return ссылку на сайт ФНС для регистрации предпринимателя
	 */
	public static String getBusinessmanRegistrationUrl()
	{
		//URL перехода на сайт ФНС для регистрации предпринимателя
		String  fnsUrl = getIt().getProperty(FNS_REGISTRATION_URL);
		if (StringHelper.isEmpty(fnsUrl))
			log.error("В файле настроек \"iccs.properties\" не задан \"" + FNS_REGISTRATION_URL + "\"");
		return fnsUrl;
	}

	/**
	 * Получение URL ресурса банка по её суффиксу
	 * @return ссылку на сайт ФНС для регистрации предпринимателя
	 */
	public static String getBankInfoUrlBySuffix(String suffix)
	{
		String paramName = BANK_INFO_URL_PREFIX + suffix;
		String paramValue = getIt().getProperty(paramName);
		if (StringHelper.isEmpty(paramValue))
			log.error("В файле настроек \"iccs.properties\" не задан \"" + paramName + "\"");
		return paramValue;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{

	}
}
