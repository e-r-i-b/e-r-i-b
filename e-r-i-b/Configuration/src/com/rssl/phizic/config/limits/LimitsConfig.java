package com.rssl.phizic.config.limits;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Конфиг для настроек работы лимитов
 * @author niculichev
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
public class LimitsConfig extends Config
{
	private static final String USE_REPLICATE_LIMIT_SETTING_NAME = "com.rssl.phizic.config.limits.use.replicate.limits";
	public static final String LIMIT_CLAIME_FROM_ONE_PHONE_NUMBER_KEY = "com.rssl.phizic.config.sbnkd.limits.limitClaimeFromOnePhoneNumber";
	public static final String LIMIT_CLAIME_CARD_PER_MONTH_KEY = "com.rssl.phizic.config.sbnkd.limits.limitClaimeCardPerMonth";
	public static final String TEXT_MESSAGE_CARD_CLAIME_LIMIT_KEY = "com.rssl.phizic.config.sbnkd.limits.textMessageCardClaimeLimit";

	private boolean useReplicateLimits = false;
	private int limitClaimeFromOnePhoneNumber;
	private int limitClaimeCardPerMonth;
	private String textMessageCardClaimeLimit;

	public LimitsConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		useReplicateLimits = getBoolProperty(USE_REPLICATE_LIMIT_SETTING_NAME);
		limitClaimeFromOnePhoneNumber = getIntProperty(LIMIT_CLAIME_FROM_ONE_PHONE_NUMBER_KEY, 0);
		limitClaimeCardPerMonth = getIntProperty(LIMIT_CLAIME_CARD_PER_MONTH_KEY, 0);
		textMessageCardClaimeLimit = getProperty(TEXT_MESSAGE_CARD_CLAIME_LIMIT_KEY);
	}

	/**
	 * @return true - необходимо испльзовать внешнюю базу с лимитами
	 */
	public boolean isUseReplicateLimits()
	{
		return useReplicateLimits;
	}

	/**
	 * @return -  Лимит количества заявок с одного номера мобильного телефона в месяц
	 */
	public int getLimitClaimeFromOnePhoneNumber()
	{
		return limitClaimeFromOnePhoneNumber;
	}

	/**
	 * @return - Лимит количества заказанных карт в месяц
	 */
	public int getLimitClaimeCardPerMonth()
	{
		return limitClaimeCardPerMonth;
	}

	/**
	 * @return - Текст отображаемого сообщения при достижении лимита
	 */
	public String getTextMessageCardClaimeLimit()
	{
		return textMessageCardClaimeLimit;
	}
}
