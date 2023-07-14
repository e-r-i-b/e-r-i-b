package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.GuestAuthenticationFailedException;
import com.rssl.auth.csa.back.exceptions.GuestPasswordFailedException;
import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.exceptions.MobileBankSendSmsMessageException;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.messages.MessageInfoImpl;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.operations.CSASmsResourcesOperation;
import com.rssl.auth.csa.back.servises.restrictions.operations.GuestLogonRequestCountRestriction;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import org.hibernate.Session;

import java.util.Collections;

/**
 * Операция аутентифкации гостя
 * @author niculichev
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestPhoneAuthenticationOperation extends GuestOperation
{
	private long authErrors = 0;
	private String password;

	public void initialize(final String phone) throws Exception
	{
		initialize(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				setPassword(generatePassword());
				setPhone(phone);
				// проверяем и сразу сохраняем, чтобы уменьшить вероятность неправильной проверки следующих попыток
				check();
				return null;
			}
		});

		try
		{
			publishCode(phone, getPassword());
		}
		catch (MobileBankSendSmsMessageException e)
		{
			refused(e);
			throw e;
		}
		catch (InactiveExternalSystemException e)
		{
			refused(e);
			throw e;
		}
		catch (Exception e)
		{
			refused(e);
			log.error("Ошибка отправки СМС кода на номер " + phone, e);
		}
	}

	/**
	 * Выполнить аутентификацию по указанному паролю
	 * @param password пароль
	 * @throws Exception
	 */
	public void execute(String password) throws Exception
	{
		try
		{
			authenticate(password);
		}
		catch (GuestPasswordFailedException e)
		{
			save(); // сохраняем состояния для правильной работы попыток
			throw e;
		}
	}

	private void authenticate(final String password) throws Exception
	{
		execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				if(!getPassword().equals(password))
				{
					authErrors++;
					if(authErrors > ConfigFactory.getConfig(Config.class).getGuestEntrySMSConfirmationAttempts())
					{
						// попытки вышли, меняем статус операции
						throw new GuestAuthenticationFailedException();
					}

					// позволяем еще раз попробовать
					throw new GuestPasswordFailedException();
				}
				// сохраняем профиль если не существует
				else
				{
					GuestProfile guestProfile = GuestProfile.findByPhone(getPhone());
					if(guestProfile == null)
					{
						GuestProfile.createProfile(getPhone());
					}
				}

				return null;
			}
		}, GuestPasswordFailedException.class);
	}

	@Override
	public void checkExecuteAllowed() throws IllegalOperationStateException
	{
		if (OperationState.NEW != getState())
		{
			throw new IllegalOperationStateException("Операция " + getClass().getName() + " " + getOuid() + " не может быть исполнена. Текуший статус операции:" + getState());
		}
	}

	public long getAuthErrors()
	{
		return authErrors;
	}

	private void setAuthErrors(long authErrors)
	{
		this.authErrors = authErrors;
	}

	private String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return оставшиеся попытки
	 */
	public long getLastAtempts()
	{
		return ConfigFactory.getConfig(Config.class).getGuestEntrySMSConfirmationAttempts() - authErrors;
	}

	/**
	 * @return оставшееся время для ввода пароля
	 */
	public long getConfirmTimeOut()
	{
		if(password == null)
			throw new IllegalStateException("Код подтверждения не сгенерирован");

		Long result = getOperationLifeTime() - DateHelper.diff(ActiveRecord.getCurrentDate(), getCreationDate()) / 1000;
		if (result < 1)
			return 0L;

		return result;
	}

	private void publishCode(String phone, String code) throws Exception
	{
		String name = getClass().getName();

		String text = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", code);
		String textToLog = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", CSASmsResourcesOperation.PASSWORD_MASK);
		String stubText = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".stubText");

		MessageInfo messageInfo = new MessageInfoImpl(text, textToLog, stubText);
		SendMessageRouter.getInstance().sendMessage( new SendMessageInfo(Collections.singleton(phone), messageInfo, false, null));
	}

	private String generatePassword()
	{
		int length = ConfigFactory.getConfig(Config.class).getConfirmCodeLength();
		String allowedChars = ConfigFactory.getConfig(Config.class).getConfirmCodeAllowedChars();
		return RandomHelper.rand(length, allowedChars);
	}

	private void check() throws Exception
	{
		GuestLogonRequestCountRestriction.getInstance().check(this);
	}
}
