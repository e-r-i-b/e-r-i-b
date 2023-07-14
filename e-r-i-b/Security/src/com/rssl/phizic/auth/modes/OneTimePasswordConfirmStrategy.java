package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.sms.NoActiveSmsPasswordException;
import com.rssl.phizic.auth.sms.SmsPasswordService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.IKFLMessagingLogicException;
import com.rssl.phizic.security.*;
import com.rssl.phizic.security.password.OneTimePassword;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * базовая стратегия подтверждения
 * @author basharin
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class OneTimePasswordConfirmStrategy implements ConfirmStrategy
{
	public static final String CLIENT_SEND_MESSAGE_KEY = "SEND_INFO";
	public static final String NAME_FIELD_LIFE_TIME = "smsPasswordLifeTime";
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Exception warning;

	public OneTimePasswordConfirmStrategy()
	{
	}

	protected abstract Class<? extends OneTimePasswordConfirmRequest> getPasswordRequestClass();

	protected abstract ConfirmType getConfirmType();

	protected abstract String getPasswordKey();

	public Exception getWarning()
	{
		return warning;
	}

	public void setWarning(Exception warning)
	{
		this.warning = warning;
	}

	/**
	 * @return настройка количество неудачных попыток подтверждения
	 */
	protected boolean checkPassword(OneTimePassword oneTimePassword, OneTimePasswordConfirmResponse res)
	{
		return !SmsPasswordService.checkPassword(oneTimePassword, new String(res.getPassword()));
	}

	/**
	 * Использует подпись, которую можно сохранить с помощю DocumentSignature
	 * @return true=использует
	 */
	public boolean hasSignature()
	{
		return false;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	/**
	 * @return настройка количество неудачных попыток подтверждения
	 */
	public static long getWrongAttemptsCount()
	{
		return SmsPasswordService.getWrongAttemptsCount();
	}

	/**
	 * @return настройка времени жизни пароля
	 */
	public static long getLifeTimePassword()
	{
		return SmsPasswordService.getLifeTime();
	}

	/**
	 * Получить SignatureCreator
	 */
	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException
	{
		throw new UnsupportedOperationException();
	}

	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object)
	{
		ConfirmStrategyType type = getType();
		List<StrategyCondition> currentConditions = conditions.get(type);
		//Если не задано условий фильтрации стратегий, то вообще ничего не фильтруем
		if (currentConditions == null)
			return true;

		for (StrategyCondition condition : currentConditions)
		{
			//Проверка выполняемости условия
			if (!condition.checkCondition(object))
			{
				//Если условие не выполнилось, то смотрим
				//  возможно есть предупреждения которые необходимо отобразить пользоватиелю,
				//  если есть, записываем предупреждения в стратегию.
				String message = condition.getWarning();
				setWarning(message == null ? null : new SecurityLogicException(message));
				return false;
			}
		}
		return true;
	}

	public void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		invalidatePassword(confirmableObject);
	}

	public PreConfirmObject preConfirmActions(final CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		final ConfirmableObject confirmableObject = callBackHandler.getConfimableObject();
		String returnMessage = null;
		try
		{
			// чтобы в случае ошибки в RSAlarm пароль не сохранился в базе
			returnMessage = HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					String passString = createPassword(confirmableObject);
					callBackHandler.setPassword(passString);
					return callBackHandler.proceed();
				}
			});
		}
		catch (SmsPasswordExistException ex)
		{
			throw new SecurityLogicException("Повторная отправка сообщения возможна через " + ex.getLeftTime() + " сек.", ex);
		}
		catch (IKFLMessagingLogicException e)
		{
			//инвалидируем сохраненный пароль, чтобы клиент не угадывал.
			invalidatePassword(callBackHandler.getConfimableObject());
			//если смс отправить не удалось запысаваем в лог
			throw new SecurityLogicException(e.getMessage(), e);
		}
		catch (InactiveExternalSystemException e)
		{
			//инвалидируем сохраненный пароль, чтобы клиент не угадывал.
			invalidatePassword(callBackHandler.getConfimableObject());
			throw new SecurityLogicException(e.getMessage(), e);
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage(),ex);
			//инвалидируем сохраненный пароль, чтобы клиент не угадывал.
			invalidatePassword(callBackHandler.getConfimableObject());
			throw new SecurityLogicException("Не удалось отправить пароль. Вам необходимо обратиться в Банк или повторить попытку позднее", ex);
		}
		if (StringHelper.isEmpty(returnMessage))
			return new PreConfirmObject(NAME_FIELD_LIFE_TIME, getLifeTimePassword());
		else
		{
			Map<String,Object> res = new HashMap<String,Object>();
			res.put(NAME_FIELD_LIFE_TIME, getLifeTimePassword());
			res.put(CLIENT_SEND_MESSAGE_KEY, returnMessage);
			return new PreConfirmObject(res);
		}
	}

	/**
	 * Проверить ответ
	 * @param login логин для которого выполняется проверка
	 * @param request запрос
	 * @param response ответ
	 * @throws com.rssl.phizic.security.SecurityLogicException неверный ответ (пароль, подпись etc)
	 */
	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (request == null)
			throw new SecurityException("Не установлен запрос на подтверждение");

		if (response == null)
			throw new SecurityException("Не установлен ответ на подтверждение");

		if (!(getPasswordRequestClass().isInstance(request)))
			throw new SecurityException("некорректный тип ConfirmRequest, ожидался SmsPasswordConfirmRequest");

		OneTimePasswordConfirmResponse res = (OneTimePasswordConfirmResponse) response;
		OneTimePasswordConfirmRequest req = (OneTimePasswordConfirmRequest) request;
		req.setErrorFieldPassword(false);
		req.resetMessages();

		OneTimePassword oneTimePassword = restorePassword(req.getConfirmableObject(), getPasswordKey());
		String confirmCode = res.getPassword();

		if (oneTimePassword == null)
		{
			req.setRequredNewPassword(true);
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmTimeout(getConfirmType(), confirmCode);
			throw new NoActiveSmsPasswordException("Срок действия пароля истек, " +
					"либо Вы несколько раз неправильно ввели пароль. Получите новый пароль, нажав на кнопку " + getNameButton() + ".");
		}

		synchronized (oneTimePassword)
		{
			if (checkPassword(oneTimePassword, res))
			{
				oneTimePassword.incWrongAttempts();
				long lastAttemptsCount = getWrongAttemptsCount() - oneTimePassword.getWrongAttempts();
				if (lastAttemptsCount < 1)
				{
					invalidatePassword(req.getConfirmableObject());
					req.setRequredNewPassword(true);
					ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(getConfirmType(), confirmCode);
					throw new SecurityLogicException("Срок действия пароля истек, " +
							"либо Вы несколько раз неправильно ввели пароль. Получите новый пароль, нажав на кнопку " + getNameButton() + ".");
				}
				req.setErrorFieldPassword(true);
				ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(getConfirmType(), confirmCode);
				throw new SmsErrorLogicException("Неверно введен пароль. Осталось попыток: " + lastAttemptsCount, lastAttemptsCount);
			}
			invalidatePassword(req.getConfirmableObject());
		}
		ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmSuccess(getConfirmType(), confirmCode);
		return new ConfirmStrategyResult(false);
	}

	protected abstract String getNameButton();

	/**
	 * Получить активный пароль из хранилища
	 * @param confirmableObject объект, для которого был сгенерен пароль.
	 * @return пароль иди null, если отсутсвует активный пароль.
	 */
	public static OneTimePassword restorePassword(ConfirmableObject confirmableObject, String passwordKey)
	{
		if (confirmableObject == null)
		{
			throw new IllegalArgumentException("Подтверждаемый объект не может быть null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			OneTimePassword password = (OneTimePassword) store.restore(passwordKey);
			if (password == null)
			{
				return null;
			}
			//Пароль от другой сущности.
			if (!password.getEntityId().equals(confirmableObject.getId()))
			{
				return null;
			}
			//Пароль от другого типа сущности.
			if (!password.getEntityType().equals(confirmableObject.getClass().getName()))
			{
				return null;
			}
			//Пароль от этой сущности. а активен ли он?
			if (password.getExpireDate().after(Calendar.getInstance()))
			{
				//активен
				return password;
			}
			//раз неактивен, грохнем его
			store.remove(passwordKey);
			return null;
		}
	}

	/**
	 * Сохранить пароль в хранилище.
	 * Допускается только 1 активный пароль на клиента
	 * @param oneTimePassword пароль.
	 */
	private void storePassword(OneTimePassword oneTimePassword)
	{
		if (oneTimePassword == null)
		{
			throw new IllegalArgumentException("Пароль не может быть null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			store.save(getPasswordKey(), oneTimePassword);
		}
	}

	protected String createPassword(ConfirmableObject confirmableObject) throws SecurityDbException, SecurityLogicException
	{
		OneTimePassword oneTimePassword = restorePassword(confirmableObject, getPasswordKey());
		if (oneTimePassword != null)
		{
			throw new SmsPasswordExistException((oneTimePassword.getExpireDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000);
		}
		try
		{
			String password = SmsPasswordService.generatePassword();
			storePassword(SmsPasswordService.createSmsPassword(password, confirmableObject));
			return password;
		}
		catch (SecurityDbException e)
		{
			log.error(e.getMessage(), e);
			throw new SecurityLogicException("Не удалось отправить пароль. Вам необходимо обратиться в Банк или повторить попытку позднее", e);
		}
	}

	/**
	 * инвалидировать пароль.
	 * @param confirmableObject объект для которого создавался пароль.
	 */
	protected void invalidatePassword(ConfirmableObject confirmableObject)
	{
		if (confirmableObject == null)
		{
			throw new IllegalArgumentException("Подтверждаемый объект не может быть null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			OneTimePassword password = (OneTimePassword)store.restore(getPasswordKey());
			if (password == null)
			{
				return;
			}
			//Пароль от другой сущности.
			if (!password.getEntityId().equals(confirmableObject.getId()))
			{
				return;
			}
			//Пароль от другого типа сущности.
			if (!password.getEntityType().equals(confirmableObject.getClass().getName()))
			{
				return;
			}
			store.remove(getPasswordKey());
		}
	}
}
