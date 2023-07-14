package com.rssl.phizic.rsa.config;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyCategory;

/**
 * @author tisov
 * @ created 26.01.15
 * @ $Author$
 * @ $Revision$
 * ������ ����-�����������
 */
public class RSAConfig
{
	private RSAHolderConfig HOLDER_INSTANCE;

	private RSAConfig()
	{
		HOLDER_INSTANCE = ConfigFactory.getConfig(RSAHolderConfig.class, ApplicationConfig.getIt().getApplicationInfo().getApplication(), PropertyCategory.RSA.getValue());
	}

	/**
	 * @return INSTANCE
	 */
	public static RSAConfig getInstance()
	{
		return new RSAConfig();
	}

	/**
	 * �������� �� ���������� ������� ����-�����������
	 * @return true - �������
	 */
	public boolean isSystemActive()
	{
		return HOLDER_INSTANCE.isSystemActive();
	}

	/**
	 * ������ �� ������� ���-�� ������ �� ��������
	 * @return true - ��
	 */
	public boolean isSystemEffectOnOperation()
	{
		return HOLDER_INSTANCE.isSystemEffectOnOperation();
	}

	/**
	 * ��������� ������� ����-�����������
	 * @return ������
	 */
	public State getState()
	{
		return HOLDER_INSTANCE.getState();
	}

	/**
	 * @return ����� ������������
	 */
	public String getLogin()
	{
		return HOLDER_INSTANCE.getLogin();
	}

	/**
	 * @return ������ ������������
	 */
	public String getPassword()
	{
		return HOLDER_INSTANCE.getPassword();
	}

	/**
	 * @return url ������� ���� �����������
	 */
	public String getUrl()
	{
		return HOLDER_INSTANCE.getUrl();
	}

	/**
	 * @return ����� �������� ������� �� ������� ���� ����������� (ws)
	 */
	public int getWSTimeOut()
	{
		return HOLDER_INSTANCE.getWSTimeOut();
	}

	/**
	 * @return ����� �������� ������� �� ������� ���� ����������� (jms)
	 */
	public int getJMSTimeOut()
	{
		return HOLDER_INSTANCE.getJMSTimeOut();
	}

	/**
	 * @return true - ���������� ���������� ��������� �� ��
	 */
	public boolean isNeedMessagesExchangeLogging()
	{
		return HOLDER_INSTANCE.isNeedMessagesExchangeLogging();
	}

	/**
	 * ���������� �������� �� ���� �������
	 * @return true - �������� �������
	 */
	public boolean isFSOActive()
	{
		return HOLDER_INSTANCE.isFSOActive();
	}

	/**
	 * ���������� ����������� ���������� ������� ������������� ��������� �����������
	 * @return - ����� ����������
	 */
	public boolean isSendingVerdictCommentActive()
	{
		return HOLDER_INSTANCE.isSendingVerdictCommentActive();
	}

	/**
	 * ��� ���-������� ���������������� ������ updateActivity � getResolution
	 * @return ���
	 */
	public String getActivityEngineUrl()
	{
		return HOLDER_INSTANCE.getActivityEngineUrl();
	}

	/**
	 * ���������� ����-����������, ���������� �� ���� ������ ������ ����� ��������
	 * @return
	 */
	public int getFraudNotificationPackSize()
	{
		return HOLDER_INSTANCE.getFraudNotificationPackSize();
	}

	/**
	 * ����������� �� ���������� ����-����������, ���������� �� ���� ������ ������ ����� ��������
	 * @return
	 */
	public int getFraudNotificationPackSizeLimit()
	{
		return HOLDER_INSTANCE.getFraudNotificationPackSizeLimit();
	}

	/**
	 * ������ ������������ ���������� ����-���������� (� �����)
	 * @return
	 */
	public int getFraudNotificationRelevancePeriod()
	{
		return HOLDER_INSTANCE.getFraudNotificationRelevancePeriod();
	}

	/**
	 * #���������� ����� �������� ����������
	 * @return
	 */
	public boolean isNotificationJobActivity()
	{
		return HOLDER_INSTANCE.isNotificationJobActivity();
	}

	/**
	 * ���������� ��������� DomainName ��� cookie
	 * @return DomainName
	 */
	public String getCookieDomainName()
	{
		return HOLDER_INSTANCE.getCookieDomainName();
	}

	/**
	 * ���������� ��������� Path ��� cookie
	 * @return DomainName
	 */
	public String getCookiePath()
	{
		return HOLDER_INSTANCE.getCookiePath();
	}

	/**
	 * ���������� ��������� ����� Secure ��� cookie
	 * @return true - ������ ��� https
	 */
	public boolean isCookieSecure()
	{
		return HOLDER_INSTANCE.isCookieSecure();
	}

	/**
	 * ���������� ��������� ����� ���������� �������� cookie
	 * @return true - ������ ��� https
	 */
	public boolean isCookieSendActive()
	{
		return HOLDER_INSTANCE.isCookieSendActive();
	}
}
