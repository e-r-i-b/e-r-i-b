package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.ConfirmationFailedException;
import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.integration.ipas.IPasPasswordInformation;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationState;
import com.rssl.auth.csa.back.servises.operations.confirmations.*;
import com.rssl.auth.csa.back.servises.restrictions.operations.OperationRestrictionProvider;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.config.ConfigFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;

import java.util.Set;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 * Базовый класс операций требуеющих подтверждения
 */
public abstract class ConfirmableOperationBase extends Operation
{
	private ConfirmStrategyType confirmStrategyType;    //тип подтверждения
	private DisposablePassword confirmCode;             //информация об смс-пароле
	private IPasPasswordInformation confirmInformation; //информация о чековом пароле
	protected Set<String> excludedPhones;               //информация о чековом пароле

	private volatile ConfirmStrategy strategy;          //текушая стратегия подтверждения
	private final Object LOCKER = new Object();

	private ConfirmStrategy getConfirmStrategy() throws Exception
	{
		if (strategy != null)
			return strategy;

		synchronized (LOCKER)
		{
			if (strategy != null)
				return strategy;

			switch (confirmStrategyType)
			{
				case sms:
					strategy = new SMSConfirmStrategy(getOuid(), getClass(), getCardNumberFoSendConfirm(),	useAlternativeRegistrationsMode(), useIMSICheck(), confirmCode, getAppName());
					break;
				case push:
					strategy = new PushConfirmStrategy(getProfile(), getOuid(), getClass(), getCardNumberFoSendConfirm(),	useAlternativeRegistrationsMode(), useIMSICheck(), confirmCode, getAppName());
					break;
				case card:
					strategy = new IPasConfirmStrategy(getOuid(), getCardNumberFoSendConfirm(), confirmInformation);
					break;
				case none:
					strategy = new NoConfirmStrategy();
			}

			return strategy;
		}
	}

	private void setConfirmCodeInfo(Object password)
	{
		switch (confirmStrategyType)
		{
			case sms: confirmCode = (DisposablePassword) password; break;
			case card: confirmInformation = (IPasPasswordInformation) password; break;
			case push: confirmCode = (DisposablePassword) password; break;
		}
	}

	/**
	 * конструктор (для гибернета)
	 */
	protected ConfirmableOperationBase()
	{}

	protected ConfirmableOperationBase(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * @return тип подтверждения
	 */
	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * задать тип подтверждения
	 * (для гибернета, в остальных случаях через initialize(com.rssl.phizic.common.types.ConfirmStrategyType))
	 * @param confirmStrategyType тип подтверждения
	 */
	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	/**
	 * @return информация об смс-пароле (для гибернета)
	 */
	public DisposablePassword getConfirmCode()
	{
		return confirmCode;
	}

	/**
	 * задать информацию об смс-пароле (для гибернета)
	 * @param confirmCode информация об смс-пароле
	 */
	public void setConfirmCode(DisposablePassword confirmCode)
	{
		this.confirmCode = confirmCode;
	}

	/**
	 * @return информация о чековом пароле (для гибернета)
	 */
	public IPasPasswordInformation getConfirmInformation()
	{
		return confirmInformation;
	}

	/**
	 * задать информацию о чековом пароле(для гибернета)
	 * @param confirmInformation информация о чековом пароле
	 */
	public void setConfirmInformation(IPasPasswordInformation confirmInformation)
	{
		this.confirmInformation = confirmInformation;
	}

	/**
	 * @return детали подтверждения
	 */
	public ConfirmationInfo getConfirmationInfo() throws Exception
	{
		return getConfirmStrategy().getConfirmationInfo();	
	}

	/**
	 * @return использовать ли альтернативные способы поиска получателя
	 */
	protected boolean useAlternativeRegistrationsMode()
	{
		return false;
	}

	/**
	 * использовать ли проверку IMSI
	 * @return true - использовать проверку IMSI
	 */
	protected boolean useIMSICheck()
	{
		return true;
	}

	/**
	 * @return полный номер карты, для которой будет выслана смс с кодом подтвреждения
	 */
	public abstract String getCardNumberFoSendConfirm() throws Exception;

	/**
	 * проверить состояние операции  на допустимость подтверждения.
	 * @throws com.rssl.auth.csa.back.exceptions.IllegalOperationStateException в случае недопустимости подтверждения.
	 */
	public final void checkConfirmAllowed() throws Exception
	{
		if (OperationState.NEW != getState())
			throw new IllegalOperationStateException("Операция " + getOuid() + " не может быть подтверждена.\n Статус: " + getState());

		getConfirmStrategy().checkConfirmAllowed();
	}

	/**
	 * инициация процесса подтвержденя
	 * @param confirmStrategyType тип подтверждения
	 */
	public final void initialize(final ConfirmStrategyType confirmStrategyType) throws Exception
	{
		this.confirmStrategyType = confirmStrategyType;
		final ConfirmStrategy confirmStrategy = getConfirmStrategy();
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				confirmStrategy.initialize();
				if (confirmStrategyType == ConfirmStrategyType.sms)
					((SMSConfirmStrategy) confirmStrategy).setExcludePhones(excludedPhones);
				setConfirmCodeInfo(confirmStrategy.getConfirmCodeInfo());
				return null;
			}
		});
		try
		{
			//Отправляем код подтверждения.
			confirmStrategy.publishCode();
		}
		catch (Exception e)
		{
			//Произошла ошибка - отказываем операцию
			log.error("Ошибка отправки кода подтверждения для заявки " + getOuid(), e);
			refused(e);
			throw e;
		}
	}

	/**
	 * Проверить код подтверждения для указанной операции.
	 * при проверке происходит изменение счетчика ошибок
	 *
	 * @param confirmationCode код подтверждения.
	 * @throws com.rssl.auth.csa.back.exceptions.ConfirmationFailedException выбрасывается в случае некорректного кода подтврежения.
	 */
	public final void checkConfirmCode(final String confirmationCode) throws Exception
	{
		Boolean result = executeIsolated(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				session.refresh(ConfirmableOperationBase.this, LockMode.UPGRADE_NOWAIT);
				checkConfirmAllowed();
				ConfirmStrategy confirmStrategy = getConfirmStrategy();
				boolean success = confirmStrategy.check(confirmationCode);
				if (!success)
				{
					if (confirmStrategy.isFailed())
					{
						setState(OperationState.REFUSED);
						setInfo("Превышено количество некорректных подтверждений");
					}
				}
				else
				{
					setState(OperationState.CONFIRMED);
				}
				session.update(ConfirmableOperationBase.this);
				return success;
			}
		});
		if (!result)
		{
			throw new ConfirmationFailedException(this);
		}
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

	/**
	 * проверить состояние операции  на допустимости исполнения.
	 * По умолчанию для исполнения допустимы операции в статусе CONFIRMED.
	 * @throws com.rssl.auth.csa.back.exceptions.IllegalOperationStateException в случае недопустимости исполнения.
	 */
	public final void checkExecuteAllowed() throws IllegalOperationStateException
	{
		if (OperationState.CONFIRMED != getState())
		{
			throw new IllegalOperationStateException("Операция " + getOuid() + " не может быть исполнени. Текуший статус операции:" + getState());
		}
	}

	/**
	 * @return время жизни заявки подтверждения
	 */
	public static int getCongirmationLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getConfirmationTimeout();
	}

	public String getAppName() {return null;}
}
