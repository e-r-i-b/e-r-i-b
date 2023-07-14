package com.rssl.phizic.rsa.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

/**
 * Холдер к конфигу взаимодействия с Фрод Мониторингом
 *
 * @author khudyakov
 * @ created 16.07.15
 * @ $Author$
 * @ $Revision$
 */
public class RSAHolderConfig extends Config
{
	private static final String RSA_ACTIVITY_STATE                      = "com.rssl.rsa.activity.state";
	private static final String RSA_FSO_ACTIVITY                        = "com.rssl.rsa.fso.activity";
	private static final String RSA_NEED_MESSAGES_EXCHANGE_LOGGING      = "com.rssl.rsa.need.messages.exchange.logging";
	private static final String RSA_URL                                 = "com.rssl.rsa.url";
	private static final String RSA_WS_CONNECTION_TIMEOUT               = "com.rssl.rsa.connection.timeout";
	private static final String RSA_JMS_RECEIVED_TIMEOUT                = "com.rssl.rsa.jms.received.timeout";
	private static final String RSA_LOGIN                               = "com.rssl.rsa.login";
	private static final String RSA_PASSWORD                            = "com.rssl.rsa.password";
	private static final String SENDING_VERDICT_COMMENT_ACTIVITY        = "com.rssl.rsa.verdict.comment.activity";
	private static final String ACTIVITY_ENGINE_URL                     = "com.rssl.rsa.engine.url";
	private static final String FRAUD_NOTIFICATION_PACK_SIZE            = "com.rssl.rsa.notification.pack.size";
	private static final String FRAUD_NOTIFICATION_PACK_SIZE_LIMIT      = "com.rssl.rsa.notification.pack.size.limit";
	private static final String NOTIFICATION_RELEVANCE_PERIOD           = "com.rssl.rsa.notification.relevance.period";
	private static final String NOTIFICATION_JOB_ACTIVITY               = "com.rssl.rsa.notification.job.activity";
	private static final String RSA_COOKIE_DOMAIN_NAME                  = "com.rssl.rsa.cookie.domain.name";
	private static final String RSA_COOKIE_PATH                         = "com.rssl.rsa.cookie.path";
	private static final String RSA_COOKIE_IS_SECURE_MODE               = "com.rssl.rsa.cookie.is.secure.mode";
	private static final String RSA_COOKIE_IS_SEND_ACTIVE               = "com.rssl.rsa.cookie.send.active";

	private State state;                                //состояние системы фрод-мониторинга
	private String url;                                 //УРЛ системы RSA
	private int WSTimeOut;                              //таймаут соединения для WS(в секундах)
	private int JMSTimeOut;                             //таймаут соединения для JMS(в секундах)
	private String login;                               //логин для авторизации в RSA
	private String password;                            //пароль для авторизации в RSA
	private boolean needMessagesExchangeLogging;        //необходимость логирования сообщений во ФМ
	private boolean fsoActive;                          //активность проверки по флеш объекту
	private boolean sendingVerdictCommentActive;        //активность функции заполнения причины подтверждения документа сотрудником
	private String activityEngineUrl;                   //урл дополнительного RSA-веб-сервиса
	private int fraudNotificationPackSize;              //размер пачки единовременно отбираемых для отправки фрод-оповещений
	private int fraudNotificationPackSizeLimit;         //ограничение на размер пачки единовременно отбираемых для отправки фрод-оповещений

	private int fraudNotificationRelevancePeriod;
	private boolean notificationJobActivity;
	private String cookieDomainName;                    //настройка парамметра DomainName для cookie
	private String cookiePath;                          //настройка парамметра path для cookie
	private boolean cookieSecure;                       //настройка флага Secure для cookie
	private boolean cookieSendActive;                   //настройка флага активности отправки cookie


	/**
	 * @param reader ридер.
	 */
	public RSAHolderConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		state = StringHelper.isEmpty(getProperty(RSA_ACTIVITY_STATE)) ? State.NOT_ACTIVE : State.valueOf(getProperty(RSA_ACTIVITY_STATE));
		login = getProperty(RSA_LOGIN);
		password = getProperty(RSA_PASSWORD);
		url = getProperty(RSA_URL);
		WSTimeOut = getIntProperty(RSA_WS_CONNECTION_TIMEOUT);
		JMSTimeOut = getIntProperty(RSA_JMS_RECEIVED_TIMEOUT);
		needMessagesExchangeLogging = getBoolProperty(RSA_NEED_MESSAGES_EXCHANGE_LOGGING);
		fsoActive = getBoolProperty(RSA_FSO_ACTIVITY);
		sendingVerdictCommentActive = getBoolProperty(SENDING_VERDICT_COMMENT_ACTIVITY);
		activityEngineUrl = getProperty(ACTIVITY_ENGINE_URL);
		fraudNotificationPackSize = getIntProperty(FRAUD_NOTIFICATION_PACK_SIZE);
		fraudNotificationPackSizeLimit = getIntProperty(FRAUD_NOTIFICATION_PACK_SIZE_LIMIT);
		fraudNotificationRelevancePeriod = getIntProperty(NOTIFICATION_RELEVANCE_PERIOD);
		notificationJobActivity = getBoolProperty(NOTIFICATION_JOB_ACTIVITY);
		cookieDomainName = getProperty(RSA_COOKIE_DOMAIN_NAME);
		cookiePath = getProperty(RSA_COOKIE_PATH);
		cookieSecure = getBoolProperty(RSA_COOKIE_IS_SECURE_MODE);
		cookieSendActive = getBoolProperty(RSA_COOKIE_IS_SEND_ACTIVE);
	}

	/**
	 * Проверка на активность системы фрод-мониторинга
	 * @return true - активна
	 */
	boolean isSystemActive()
	{
		return state != State.NOT_ACTIVE;
	}

	/**
	 * Должна ли система как-то влиять на операцию
	 * @return true - да
	 */
	boolean isSystemEffectOnOperation()
	{
		return State.ACTIVE_INTERACTION == state;
	}

	/**
	 * Состояние системы фрод-мониторинга
	 * @return статус
	 */
	State getState()
	{
		return state;
	}

	/**
	 * @return логин пользователя
	 */
	String getLogin()
	{
		return login;
	}

	/**
	 * @return пароль пользователя
	 */
	String getPassword()
	{
		return password;
	}

	/**
	 * @return url системы фрод мониторинга
	 */
	String getUrl()
	{
		return url;
	}

	/**
	 * @return время ожидания отклика от системы фрод мониторинга (ws)
	 */
	int getWSTimeOut()
	{
		return WSTimeOut;
	}

	/**
	 * @return время ожидания отклика от системы фрод мониторинга (jms)
	 */
	int getJMSTimeOut()
	{
		return JMSTimeOut;
	}

	/**
	 * @return true - необходимо логировать сообщения во ФМ
	 */
	boolean isNeedMessagesExchangeLogging()
	{
		return needMessagesExchangeLogging;
	}

	/**
	 * Активность проверки по флеш объекту
	 * @return true - проверка активна
	 */
	boolean isFSOActive()
	{
		return isSystemActive() && fsoActive;
	}

	/**
	 * Активность возможности заполнения причины подтверждения документа сотрудником
	 * @return - режим активности
	 */
	boolean isSendingVerdictCommentActive()
	{
		return sendingVerdictCommentActive;
	}

	/**
	 * УРЛ веб-сервиса предоставляющего методы updateActivity и getResolution
	 * @return урл
	 */
	String getActivityEngineUrl()
	{
		return activityEngineUrl;
	}

	/**
	 * Количество фрод-оповещений, посылаемых за одну сессию работы джоба отправки
	 * @return
	 */
	int getFraudNotificationPackSize()
	{
		return fraudNotificationPackSize;
	}

	/**
	 * ограничение на размер пачки единовременно отбираемых для отправки фрод-оповещений
	 * @return
	 */
	int getFraudNotificationPackSizeLimit()
	{
		return fraudNotificationPackSizeLimit;
	}

	/**
	 * Период актуальности сохранённых фрод-оповещений (в часах)
	 * @return
	 */
	int getFraudNotificationRelevancePeriod()
	{
		return fraudNotificationRelevancePeriod;
	}

	/**
	 * #Активность джоба рассылки оповещений
	 * @return
	 */
	boolean isNotificationJobActivity()
	{
		return notificationJobActivity;
	}

	/**
	 * Возвращает настройку DomainName для cookie
	 * @return DomainName
	 */
	String getCookieDomainName()
	{
		return cookieDomainName;
	}

	/**
	 * Возвращает настройку DomainName для cookie
	 * @return DomainName
	 */
	String getCookiePath()
	{
		return cookiePath;
	}

	/**
	 * Возвращает настройку флага Secure для cookie
	 * @return true - только для https
	 */
	boolean isCookieSecure()
	{
		return cookieSecure;
	}

	/**
	 * Возвращает настройку флага активности отправки cookie
	 * @return true - только для https
	 */
	boolean isCookieSendActive()
	{
		return cookieSendActive;
	}
}
