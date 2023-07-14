package com.rssl.phizic;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Egorova
 * @ created 27.03.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class BankContextConfig extends Config
{
	public static final String BANK_NAME = "com.rssl.iccs.our.bank.name";
	public static final String BANK_PHONE = "com.rssl.iccs.our.bank.phone";
	public static final String BANK_ADDRESS = "com.rssl.iccs.our.bank.address";
	public static final String SYSTEM_NAME = "com.rssl.iccs.our.system.name";
	public static final String SYSTEM_URL = "com.rssl.iccs.our.system.url";
	public static final String WEB_URL = "com.rssl.iccs.our.web.url";
	public static final String BANK_CORACC ="com.rssl.iccs.our.bank.coracc";
	public static final String BANK_BIC ="com.rssl.iccs.our.bank.bic";
	public static final String NATIONAL_CURRENCY_CODE = "com.rssl.iccs.our.bank.national.currency.cod";

	protected BankContextConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return  �������� �����
	 */
	public abstract String getBankName();

	/**
	 * @return  ������� �����
	 */
	public abstract String getBankPhone();

	/**
	 * @return  ����� �����
	 */
	public abstract String getBankAddress();

	/**
	 * @return  �������� �������
	 */
	public abstract String getSystemName();

	/**
	 * @return  ������ �� ���� � �������
	 */
	public abstract String getSystemUrl();

	/**
	 * @return  web-������ �����
	 */
	public abstract String getWebUrl();

	/**
	 * @return ��������� iso ��� ������������ ������.
	 */
	public abstract String getNationalCurrencyCode();

	/**
	 *
	 * @return ��� ������ �����
	 */
	public abstract String getBankBIC();

	/**
	 *
	 * @return ����. ���� ������ �����
	 */
	public abstract String getBankCorAcc();
}
