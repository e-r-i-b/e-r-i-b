package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.*;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.messages.MessageInfoImpl;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.OperationState;
import com.rssl.auth.csa.back.servises.operations.CSASmsResourcesOperation;
import com.rssl.auth.csa.back.servises.restrictions.operations.OperationRestrictionProvider;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import org.hibernate.LockMode;
import org.hibernate.Session;

import java.util.Collections;

/**
 * @author niculichev
 * @ created 15.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestConfirmableOperation extends GuestOperation
{
	private long authErrors = 0;
	private String password;

	/**
	 * ѕроверить код подтверждени€ дл€ указанной операции.
	 * при проверке происходит изменение счетчика ошибок
	 *
	 * @param confirmationCode код подтверждени€.
	 * @throws com.rssl.auth.csa.back.exceptions.GuestConfirmationFailedException выбрасываетс€ в случае некорректного кода подтврежени€.
	 */
	public final void checkConfirmCode(final String confirmationCode) throws Exception
	{
		Boolean result = executeIsolated(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				session.refresh(GuestConfirmableOperation.this, LockMode.UPGRADE_NOWAIT);
				boolean success = getPassword().equals(confirmationCode);
				if (!success)
				{
					authErrors++;
					if(authErrors > getConfirmationAttemptsCount())
					{
						setState(OperationState.REFUSED);
						setInfo("ѕревышено количество некорректных подтверждений");
					}
				}
				else
				{
					setState(OperationState.CONFIRMED);
				}
				session.update(GuestConfirmableOperation.this);
				return success;
			}
		});

		if (!result)
			throw new GuestConfirmationFailedException(this);

		try
		{
			OperationRestrictionProvider.getInstance().checkPostConfirmation(this);
		}
		catch (RestrictionException e)
		{
			refused(e);
			throw e;
		}
	}

	public void checkExecuteAllowed() throws IllegalOperationStateException
	{
		if (OperationState.CONFIRMED != getState())
		{
			throw new IllegalOperationStateException("ќпераци€ " + getClass().getName() + " " + getOuid() + " не может быть исполнена. “екуший статус операции:" + getState());
		}
	}

	protected void publishCode(String phone, String code) throws Exception
	{
		String name = getClass().getName();

		String text = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", code);
		String textToLog = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", CSASmsResourcesOperation.PASSWORD_MASK);
		String stubText = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".stubText");

		MessageInfo messageInfo = new MessageInfoImpl(text, textToLog, stubText);
		SendMessageRouter.getInstance().sendMessage(new SendMessageInfo(Collections.singleton(phone), messageInfo, true, null));
	}

	protected void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public long getAuthErrors()
	{
		return authErrors;
	}

	private void setAuthErrors(long authErrors)
	{
		this.authErrors = authErrors;
	}

	/**
	 * @return оставшиес€ попытки
	 */
	public long getLastAttempts()
	{
		return ConfigFactory.getConfig(Config.class).getConfirmationAttemptsCount() - authErrors;
	}

	/**
	 * @return оставшеес€ врем€ дл€ ввода парол€
	 */
	public long getConfirmTimeOut()
	{
		if(password == null)
			throw new IllegalStateException(" од подтверждени€ не сгенерирован");

		Long result = getOperationLifeTime() - DateHelper.diff(ActiveRecord.getCurrentDate(), getCreationDate()) / 1000;
		if (result < 1)
			return 0L;

		return result;
	}

	/**
	 * @return врем€ жизни гостевой за€вки подтверждени€
	 */
	public static int getConfirmationLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getConfirmationTimeout();
	}

	/**
	 * @return количество попыток подтверждени€ гостевой за€вки
	 */
	public static int getConfirmationAttemptsCount()
	{
		return ConfigFactory.getConfig(Config.class).getConfirmationAttemptsCount();
	}

	/**
	 * @return сгенерированный одноразовый пароль
	 */
	protected String generatePassword()
	{
		int length = ConfigFactory.getConfig(Config.class).getConfirmCodeLength();
		String allowedChars = ConfigFactory.getConfig(Config.class).getConfirmCodeAllowedChars();
		return RandomHelper.rand(length, allowedChars);
	}
}
