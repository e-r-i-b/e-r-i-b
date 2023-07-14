package com.rssl.phizic.config.ermb;

import com.rssl.phizic.config.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: moshenko
 * Date: 13.03.2013
 * Time: 10:18:43
 * ������� ��� ����
 */
public class ErmbConfig extends Config
{

	//������ ���������
	//��������� ���������� �� ������� ����� ����
	private static final String PREFERENTIAL_PRODUCT_KIND = "com.rssl.iccs.ermb.product.class.preferential";
	private static final String PREMIUM_PRODUCT_KIND = "com.rssl.iccs.ermb.product.class.premium";
	private static final String SOCIAL_PRODUCT_KIND = "com.rssl.iccs.ermb.product.class.social";
	private static final String STANDART_PRODUCT_KIND = "com.rssl.iccs.ermb.product.class.standart";
	private static final String SMS_LOG_URL = "com.rssl.iccs.ermb.sms.log.url";
	private static final String SMS_LOG_LOGGER_USE = "com.rssl.iccs.ermb.sms.log.logger.use";
	private static final String SMS_LOGGER_USE = "com.rssl.iccs.ermb.sms.logger.use";
	private static final String NEW_CLIENT_TIMEOUT = "com.rssl.iccs.ermb.new.client.service.timeout";
	private static final String COD_ERMB_CONN = "com.rssl.iccs.ermb.cod.service.url";
	private static final String ERMB_USE = "com.rssl.iccs.ermb.use";
	private static final String PRODUCT_AVAILABILITY_COMMON_ATTRIBUTE = "com.rssl.iccs.ermb.productAvailability.commonAttribute";
	private static final String LOAN_NOTIFICATION_AVAILABILITY = "com.rssl.iccs.ermb.loanNotificationAvailability";
	private static final String CONFIRM_BEAN_LIFETIME = "com.rssl.iccs.ermb.confirm.bean.lifetime";
	private static final String CONFIRM_BEAN_ACTIVE = "com.rssl.iccs.ermb.confirm.bean.active";
	private static final String MIGRATION_ON_THE_FLY_USE = "com.rssl.iccs.ermb.migration.on.the.fly.use";
	private static final String SMS_PAYMENT_URL = "com.rssl.phizic.sms.payment.url";
	private static final String SMS_PAYMENT_NEED_NOTIFY = "com.rssl.phizic.sms.payment.needNotify";
	private static final String ACTIVATE_ERMB_TO_WAY4 = "com.rssl.iccs.ermb.activate.ermb.to.way4";
	private static final String USE_MNP_PHONES = "com.rssl.iccs.sms.useMNPPhones";

	private static final String splitter = "\\,";

	//��� ����� � ������ ��� (CardLevelType)
	//�������� �����
	private List<String> preferential;
	//������� �����
	private List<String> premium;
	//���������� �����
	private List<String> social;
	//�������� �����
	private List<String> standart;

	/**
	 * url ������ ���-������� ������� ���������
	 */
	private String smsLogUrl;

	/**
	 * ���� ������������� ����������� ��������� axis � ���-������� ������� ���������
	 */
	private boolean smsLogLoggerUse;
	/**
	 * ���� ������������� �����������,��� ��������� ��������� SMS-�������� (SmsRq)
	 */
	private boolean smsLoggerUse;

	/**
	 * ������� �������� ������ �� ���-������� ����������� ��� � ����� ��������
	 */
	private int newClientTimeout;

	/**
	 * url ������� ��� ���������/���������� �������� ����������� � ����
	 */
	private String codServiceUrl;

	/**
	 * ���� ������������� ����. ����� ��� ����������� ������������ �����.
	 */
	private boolean ermbUse;

	/**
	 * ��������� "������������ ����� ������� ��������� ��������� � ����"
	 */
	private boolean productsAvailabilityCommonAttribute;

	/**
	 * ��������� "���������� ���������� �� ��������"
	 */
	private boolean loanNotificationAvailability;

	/**
	 * ����� ����� confirm-����� � ��������
	 */
	private int confirmBeanTimeToLife;

	/**
	 * ����� ���������� ���� ������������� � ��������
	 */
	private int confirmBeanSecondsActive;

	/**
	 * ���� �������� �� ���� ��� �������� � �������������� �������
	 */
	private boolean migrationOnTheFlyUse;

	/**
	 *����� ���-�������, ������������� ���������� �� �������� ���������� ������� � ���
	 */
	private String smsPaymentUrl;

	/**
	 * �������, ���������� �� ���������� ���������� � ���
	 */
	private boolean smsPaymentNeedNotify;

	/**
	 * ���� �������� �������� ����������� ���� ������� � way4
	 */
	private boolean activateErmbToWay4;

	//������ "������ � mnp-��������� ��� ������ ���������� ���������"
	private boolean useMNPPhones;

	/**
	 * ������ "����� ������������ ������������� ����-��������"
	 * ���� ������, ��������� ������� ������������ ��� ������� ���� ��������� ����
	 * ���� ������, ��������� ������� ������������ ��� ������� ����-������
	 */
	private boolean queueOptimizedMode;

	public ErmbConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		preferential = Collections.unmodifiableList(Arrays.asList(getProperty(PREFERENTIAL_PRODUCT_KIND).split(splitter)));
		premium = Collections.unmodifiableList(Arrays.asList(getProperty(PREMIUM_PRODUCT_KIND).split(splitter)));
		social = Collections.unmodifiableList(Arrays.asList(getProperty(SOCIAL_PRODUCT_KIND).split(splitter)));
		standart = Collections.unmodifiableList(Arrays.asList(getProperty(STANDART_PRODUCT_KIND).split(splitter)));
		smsLogUrl = getProperty(SMS_LOG_URL);
		smsLogLoggerUse = getBoolProperty(SMS_LOG_LOGGER_USE);
		smsLoggerUse = getBoolProperty(SMS_LOGGER_USE);
		newClientTimeout = getIntProperty(NEW_CLIENT_TIMEOUT);
		codServiceUrl = getProperty(COD_ERMB_CONN);
		ermbUse = getBoolProperty(ERMB_USE);
		productsAvailabilityCommonAttribute = getBoolProperty(PRODUCT_AVAILABILITY_COMMON_ATTRIBUTE);
		loanNotificationAvailability = getBoolProperty(LOAN_NOTIFICATION_AVAILABILITY);
		confirmBeanTimeToLife = getIntProperty(CONFIRM_BEAN_LIFETIME);
		confirmBeanSecondsActive = getIntProperty(CONFIRM_BEAN_ACTIVE);
		migrationOnTheFlyUse = getBoolProperty(MIGRATION_ON_THE_FLY_USE);
		smsPaymentUrl = getProperty(SMS_PAYMENT_URL);
		smsPaymentNeedNotify = getBoolProperty(SMS_PAYMENT_NEED_NOTIFY);
		activateErmbToWay4 = getBoolProperty(ACTIVATE_ERMB_TO_WAY4);
		queueOptimizedMode = getBoolProperty("com.rssl.iccs.ermb.queueOptimizedMode");
		useMNPPhones = getBoolProperty(USE_MNP_PHONES);
	}

	public List<String> getPreferential()
	{
		return preferential;
	}

	public List<String> getPremium()
	{
		return premium;
	}

	public List<String> getSocial()
	{
		return social;
	}

	public List<String> getStandart()
	{
		return standart;
	}

	public String getSmsLogUrl()
	{
		return smsLogUrl;
	}

	public boolean isSmsLogLoggerUse()
	{
		return smsLogLoggerUse;
	}

	public int getNewClientTimeout()
	{
		return newClientTimeout;
	}

	public String getCodServiceUrl()
	{
		return codServiceUrl;
	}

	public boolean getProductAvailabilityCommonAttribute()
	{
		return productsAvailabilityCommonAttribute;
	}

	public boolean getLoanNotificationAvailability()
	{
		return loanNotificationAvailability;
	}

	public boolean isErmbUse()
	{
		return ermbUse;
	}

	public int getConfirmBeanTimeToLife()
	{
		return confirmBeanTimeToLife;
	}

	public int getConfirmBeanSecondsActive()
	{
		return confirmBeanSecondsActive;
	}

	public boolean isSmsLoggerUse()
	{
		return smsLoggerUse;
	}

	public boolean isMigrationOnTheFlyUse()
	{
		return migrationOnTheFlyUse;
	}

	public String getPaymentSmsWServiceUrl()
	{
		return smsPaymentUrl;
	}

	public Boolean needSendPaymentSmsNotification()
	{
	   return smsPaymentNeedNotify;
	}

	public boolean isActivateErmbToWay4()
	{
		return activateErmbToWay4;
	}

	public boolean isQueueOptimizedMode()
	{
		return queueOptimizedMode;
	}

	public boolean isUseMNPPhones()
	{
		return useMNPPhones;
	}
}
