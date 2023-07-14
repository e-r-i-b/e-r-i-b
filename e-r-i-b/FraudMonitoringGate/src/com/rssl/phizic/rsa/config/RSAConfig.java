package com.rssl.phizic.rsa.config;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyCategory;

/**
 * @author tisov
 * @ created 26.01.15
 * @ $Author$
 * @ $Revision$
 * Конфиг фрод-мониторинга
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
	 * Проверка на активность системы фрод-мониторинга
	 * @return true - активна
	 */
	public boolean isSystemActive()
	{
		return HOLDER_INSTANCE.isSystemActive();
	}

	/**
	 * Должна ли система как-то влиять на операцию
	 * @return true - да
	 */
	public boolean isSystemEffectOnOperation()
	{
		return HOLDER_INSTANCE.isSystemEffectOnOperation();
	}

	/**
	 * Состояние системы фрод-мониторинга
	 * @return статус
	 */
	public State getState()
	{
		return HOLDER_INSTANCE.getState();
	}

	/**
	 * @return логин пользователя
	 */
	public String getLogin()
	{
		return HOLDER_INSTANCE.getLogin();
	}

	/**
	 * @return пароль пользователя
	 */
	public String getPassword()
	{
		return HOLDER_INSTANCE.getPassword();
	}

	/**
	 * @return url системы фрод мониторинга
	 */
	public String getUrl()
	{
		return HOLDER_INSTANCE.getUrl();
	}

	/**
	 * @return время ожидания отклика от системы фрод мониторинга (ws)
	 */
	public int getWSTimeOut()
	{
		return HOLDER_INSTANCE.getWSTimeOut();
	}

	/**
	 * @return время ожидания отклика от системы фрод мониторинга (jms)
	 */
	public int getJMSTimeOut()
	{
		return HOLDER_INSTANCE.getJMSTimeOut();
	}

	/**
	 * @return true - необходимо логировать сообщения во ФМ
	 */
	public boolean isNeedMessagesExchangeLogging()
	{
		return HOLDER_INSTANCE.isNeedMessagesExchangeLogging();
	}

	/**
	 * Активность проверки по флеш объекту
	 * @return true - проверка активна
	 */
	public boolean isFSOActive()
	{
		return HOLDER_INSTANCE.isFSOActive();
	}

	/**
	 * Активность возможности заполнения причины подтверждения документа сотрудником
	 * @return - режим активности
	 */
	public boolean isSendingVerdictCommentActive()
	{
		return HOLDER_INSTANCE.isSendingVerdictCommentActive();
	}

	/**
	 * УРЛ веб-сервиса предоставляющего методы updateActivity и getResolution
	 * @return урл
	 */
	public String getActivityEngineUrl()
	{
		return HOLDER_INSTANCE.getActivityEngineUrl();
	}

	/**
	 * Количество фрод-оповещений, посылаемых за одну сессию работы джоба отправки
	 * @return
	 */
	public int getFraudNotificationPackSize()
	{
		return HOLDER_INSTANCE.getFraudNotificationPackSize();
	}

	/**
	 * Ограничение на количество фрод-оповещений, посылаемых за одну сессию работы джоба отправки
	 * @return
	 */
	public int getFraudNotificationPackSizeLimit()
	{
		return HOLDER_INSTANCE.getFraudNotificationPackSizeLimit();
	}

	/**
	 * Период актуальности сохранённых фрод-оповещений (в часах)
	 * @return
	 */
	public int getFraudNotificationRelevancePeriod()
	{
		return HOLDER_INSTANCE.getFraudNotificationRelevancePeriod();
	}

	/**
	 * #Активность джоба рассылки оповещений
	 * @return
	 */
	public boolean isNotificationJobActivity()
	{
		return HOLDER_INSTANCE.isNotificationJobActivity();
	}

	/**
	 * Возвращает настройку DomainName для cookie
	 * @return DomainName
	 */
	public String getCookieDomainName()
	{
		return HOLDER_INSTANCE.getCookieDomainName();
	}

	/**
	 * Возвращает настройку Path для cookie
	 * @return DomainName
	 */
	public String getCookiePath()
	{
		return HOLDER_INSTANCE.getCookiePath();
	}

	/**
	 * Возвращает настройку флага Secure для cookie
	 * @return true - только для https
	 */
	public boolean isCookieSecure()
	{
		return HOLDER_INSTANCE.isCookieSecure();
	}

	/**
	 * Возвращает настройку флага активности отправки cookie
	 * @return true - только для https
	 */
	public boolean isCookieSendActive()
	{
		return HOLDER_INSTANCE.isCookieSendActive();
	}
}
