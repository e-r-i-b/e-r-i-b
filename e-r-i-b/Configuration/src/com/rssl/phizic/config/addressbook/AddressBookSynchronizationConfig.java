package com.rssl.phizic.config.addressbook;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lepihina
 * @ created 10.06.14
 * $Author$
 * $Revision$
 *
 * Конфиг для настроек синхронизации адресной книги
 */
public class AddressBookSynchronizationConfig extends Config
{
	public static final String PERIOD_TYPE_KEY = "addressBookSync.thresholdNotification.periodType";
	public static final String THRESHOLD_COUNT_KEY = "addressBookSync.thresholdNotification.thresholdCount";
	public static final String EMAIL_TEXT_KEY = "addressBookSync.thresholdNotification.emailText";
	public static final String EMAIL_KEY = "addressBookSync.thresholdNotification.email";
	public static final String SHOW_MESSAGE_KEY = "com.rssl.phizic.web.configure.addressBookSettings.address.book.show";

	private String periodType;
	private Integer thresholdCount;
	private String emailText;
	private String email;
	private Boolean showMessage;

	/**
	 * @param reader - ридер
	 */
	public AddressBookSynchronizationConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		periodType = getProperty(PERIOD_TYPE_KEY);
		thresholdCount = getIntProperty(THRESHOLD_COUNT_KEY);
		emailText = getProperty(EMAIL_TEXT_KEY);
		email = getProperty(EMAIL_KEY);
		showMessage = getBoolProperty(SHOW_MESSAGE_KEY);
	}

	/**
	 * @return период времени
	 */
	public PeriodType getPeriodType()
	{
		if(StringHelper.isNotEmpty(periodType))
			return PeriodType.valueOf(periodType);
		return PeriodType.DAY;
	}

	/**
	 * @return порог обращений к сервису синхронизации
	 */
	public Integer getThresholdCount()
	{
		return thresholdCount;
	}

	/**
	 * @return текст email-уведомления
	 */
	public String getEmailText()
	{
		return emailText;
	}

	/**
	 * @return email-адрес, на который будет отправляться сформированный отчет
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return необходимо ли отображать сообщение
	 */
	public Boolean getShowMessage()
	{
		return showMessage;
	}
}
