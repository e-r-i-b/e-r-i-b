package com.rssl.phizic.rsa.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

/**
 * ������ � ������� �������������� � ���� ������������
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

	private State state;                                //��������� ������� ����-�����������
	private String url;                                 //��� ������� RSA
	private int WSTimeOut;                              //������� ���������� ��� WS(� ��������)
	private int JMSTimeOut;                             //������� ���������� ��� JMS(� ��������)
	private String login;                               //����� ��� ����������� � RSA
	private String password;                            //������ ��� ����������� � RSA
	private boolean needMessagesExchangeLogging;        //������������� ����������� ��������� �� ��
	private boolean fsoActive;                          //���������� �������� �� ���� �������
	private boolean sendingVerdictCommentActive;        //���������� ������� ���������� ������� ������������� ��������� �����������
	private String activityEngineUrl;                   //��� ��������������� RSA-���-�������
	private int fraudNotificationPackSize;              //������ ����� ������������� ���������� ��� �������� ����-����������
	private int fraudNotificationPackSizeLimit;         //����������� �� ������ ����� ������������� ���������� ��� �������� ����-����������

	private int fraudNotificationRelevancePeriod;
	private boolean notificationJobActivity;
	private String cookieDomainName;                    //��������� ���������� DomainName ��� cookie
	private String cookiePath;                          //��������� ���������� path ��� cookie
	private boolean cookieSecure;                       //��������� ����� Secure ��� cookie
	private boolean cookieSendActive;                   //��������� ����� ���������� �������� cookie


	/**
	 * @param reader �����.
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
	 * �������� �� ���������� ������� ����-�����������
	 * @return true - �������
	 */
	boolean isSystemActive()
	{
		return state != State.NOT_ACTIVE;
	}

	/**
	 * ������ �� ������� ���-�� ������ �� ��������
	 * @return true - ��
	 */
	boolean isSystemEffectOnOperation()
	{
		return State.ACTIVE_INTERACTION == state;
	}

	/**
	 * ��������� ������� ����-�����������
	 * @return ������
	 */
	State getState()
	{
		return state;
	}

	/**
	 * @return ����� ������������
	 */
	String getLogin()
	{
		return login;
	}

	/**
	 * @return ������ ������������
	 */
	String getPassword()
	{
		return password;
	}

	/**
	 * @return url ������� ���� �����������
	 */
	String getUrl()
	{
		return url;
	}

	/**
	 * @return ����� �������� ������� �� ������� ���� ����������� (ws)
	 */
	int getWSTimeOut()
	{
		return WSTimeOut;
	}

	/**
	 * @return ����� �������� ������� �� ������� ���� ����������� (jms)
	 */
	int getJMSTimeOut()
	{
		return JMSTimeOut;
	}

	/**
	 * @return true - ���������� ���������� ��������� �� ��
	 */
	boolean isNeedMessagesExchangeLogging()
	{
		return needMessagesExchangeLogging;
	}

	/**
	 * ���������� �������� �� ���� �������
	 * @return true - �������� �������
	 */
	boolean isFSOActive()
	{
		return isSystemActive() && fsoActive;
	}

	/**
	 * ���������� ����������� ���������� ������� ������������� ��������� �����������
	 * @return - ����� ����������
	 */
	boolean isSendingVerdictCommentActive()
	{
		return sendingVerdictCommentActive;
	}

	/**
	 * ��� ���-������� ���������������� ������ updateActivity � getResolution
	 * @return ���
	 */
	String getActivityEngineUrl()
	{
		return activityEngineUrl;
	}

	/**
	 * ���������� ����-����������, ���������� �� ���� ������ ������ ����� ��������
	 * @return
	 */
	int getFraudNotificationPackSize()
	{
		return fraudNotificationPackSize;
	}

	/**
	 * ����������� �� ������ ����� ������������� ���������� ��� �������� ����-����������
	 * @return
	 */
	int getFraudNotificationPackSizeLimit()
	{
		return fraudNotificationPackSizeLimit;
	}

	/**
	 * ������ ������������ ���������� ����-���������� (� �����)
	 * @return
	 */
	int getFraudNotificationRelevancePeriod()
	{
		return fraudNotificationRelevancePeriod;
	}

	/**
	 * #���������� ����� �������� ����������
	 * @return
	 */
	boolean isNotificationJobActivity()
	{
		return notificationJobActivity;
	}

	/**
	 * ���������� ��������� DomainName ��� cookie
	 * @return DomainName
	 */
	String getCookieDomainName()
	{
		return cookieDomainName;
	}

	/**
	 * ���������� ��������� DomainName ��� cookie
	 * @return DomainName
	 */
	String getCookiePath()
	{
		return cookiePath;
	}

	/**
	 * ���������� ��������� ����� Secure ��� cookie
	 * @return true - ������ ��� https
	 */
	boolean isCookieSecure()
	{
		return cookieSecure;
	}

	/**
	 * ���������� ��������� ����� ���������� �������� cookie
	 * @return true - ������ ��� https
	 */
	boolean isCookieSendActive()
	{
		return cookieSendActive;
	}
}
