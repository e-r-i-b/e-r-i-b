package com.rssl.phizic.config.ermb;

import com.rssl.phizic.config.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: moshenko
 * Date: 13.03.2013
 * Time: 10:18:43
 * Конфиги для ЕРМБ
 */
public class ErmbConfig extends Config
{

	//Классы продуктов
	//Настройки отвечающся за списоки типов карт
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

	//Вид карты в рамках МПС (CardLevelType)
	//Льготный калсс
	private List<String> preferential;
	//Премиум класс
	private List<String> premium;
	//Социальный класс
	private List<String> social;
	//Стандарт класс
	private List<String> standart;

	/**
	 * url вызова веб-сервиса журнала сообщений
	 */
	private String smsLogUrl;

	/**
	 * флаг использования логирования сообщений axis в веб-сервисе журнала сообщений
	 */
	private boolean smsLogLoggerUse;
	/**
	 * флаг использования логирования,при обработке текстовых SMS-запросов (SmsRq)
	 */
	private boolean smsLoggerUse;

	/**
	 * таймаут ожидания ответа от веб-сервиса уведомления ОСС о новых клиентах
	 */
	private int newClientTimeout;

	/**
	 * url сервиса для включения/отключения признака подключения к ЕРМБ
	 */
	private String codServiceUrl;

	/**
	 * Флаг использования ЕРМБ. Нужен для определения приоритетной карты.
	 */
	private boolean ermbUse;

	/**
	 * настройка "использовать общий признак видимости продуктов в ЕРМБ"
	 */
	private boolean productsAvailabilityCommonAttribute;

	/**
	 * настройка "отправлять оповещения по кредитам"
	 */
	private boolean loanNotificationAvailability;

	/**
	 * Время жизни confirm-бинов в секундах
	 */
	private int confirmBeanTimeToLife;

	/**
	 * Время активности кода подтверждения в секундах
	 */
	private int confirmBeanSecondsActive;

	/**
	 * Флаг миграции на лету при создании и редактировании профиля
	 */
	private boolean migrationOnTheFlyUse;

	/**
	 *Адрес веб-сервиса, отправляющего оповещение об успешном исполнении платежа в СОС
	 */
	private String smsPaymentUrl;

	/**
	 * признак, необходимо ли отправлять оповещения в СОС
	 */
	private boolean smsPaymentNeedNotify;

	/**
	 * Флаг передачи признака подключения ермб профиля в way4
	 */
	private boolean activateErmbToWay4;

	//Флажок "Искать в mnp-телефонах при поиске мобильного оператора"
	private boolean useMNPPhones;

	/**
	 * Флажок "режим оптимального использования ЕРМБ-очередей"
	 * Если опущен, отдельная очередь используется для каждого вида сообщений ЕРМБ
	 * Если поднят, отдельная очередь используется для каждого ЕРМБ-канала
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
