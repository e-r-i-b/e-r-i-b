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
 * ������� ����� �������� ���������� �������������
 */
public abstract class ConfirmableOperationBase extends Operation
{
	private ConfirmStrategyType confirmStrategyType;    //��� �������������
	private DisposablePassword confirmCode;             //���������� �� ���-������
	private IPasPasswordInformation confirmInformation; //���������� � ������� ������
	protected Set<String> excludedPhones;               //���������� � ������� ������

	private volatile ConfirmStrategy strategy;          //������� ��������� �������������
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
	 * ����������� (��� ���������)
	 */
	protected ConfirmableOperationBase()
	{}

	protected ConfirmableOperationBase(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * @return ��� �������������
	 */
	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * ������ ��� �������������
	 * (��� ���������, � ��������� ������� ����� initialize(com.rssl.phizic.common.types.ConfirmStrategyType))
	 * @param confirmStrategyType ��� �������������
	 */
	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	/**
	 * @return ���������� �� ���-������ (��� ���������)
	 */
	public DisposablePassword getConfirmCode()
	{
		return confirmCode;
	}

	/**
	 * ������ ���������� �� ���-������ (��� ���������)
	 * @param confirmCode ���������� �� ���-������
	 */
	public void setConfirmCode(DisposablePassword confirmCode)
	{
		this.confirmCode = confirmCode;
	}

	/**
	 * @return ���������� � ������� ������ (��� ���������)
	 */
	public IPasPasswordInformation getConfirmInformation()
	{
		return confirmInformation;
	}

	/**
	 * ������ ���������� � ������� ������(��� ���������)
	 * @param confirmInformation ���������� � ������� ������
	 */
	public void setConfirmInformation(IPasPasswordInformation confirmInformation)
	{
		this.confirmInformation = confirmInformation;
	}

	/**
	 * @return ������ �������������
	 */
	public ConfirmationInfo getConfirmationInfo() throws Exception
	{
		return getConfirmStrategy().getConfirmationInfo();	
	}

	/**
	 * @return ������������ �� �������������� ������� ������ ����������
	 */
	protected boolean useAlternativeRegistrationsMode()
	{
		return false;
	}

	/**
	 * ������������ �� �������� IMSI
	 * @return true - ������������ �������� IMSI
	 */
	protected boolean useIMSICheck()
	{
		return true;
	}

	/**
	 * @return ������ ����� �����, ��� ������� ����� ������� ��� � ����� �������������
	 */
	public abstract String getCardNumberFoSendConfirm() throws Exception;

	/**
	 * ��������� ��������� ��������  �� ������������ �������������.
	 * @throws com.rssl.auth.csa.back.exceptions.IllegalOperationStateException � ������ �������������� �������������.
	 */
	public final void checkConfirmAllowed() throws Exception
	{
		if (OperationState.NEW != getState())
			throw new IllegalOperationStateException("�������� " + getOuid() + " �� ����� ���� ������������.\n ������: " + getState());

		getConfirmStrategy().checkConfirmAllowed();
	}

	/**
	 * ��������� �������� ������������
	 * @param confirmStrategyType ��� �������������
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
			//���������� ��� �������������.
			confirmStrategy.publishCode();
		}
		catch (Exception e)
		{
			//��������� ������ - ���������� ��������
			log.error("������ �������� ���� ������������� ��� ������ " + getOuid(), e);
			refused(e);
			throw e;
		}
	}

	/**
	 * ��������� ��� ������������� ��� ��������� ��������.
	 * ��� �������� ���������� ��������� �������� ������
	 *
	 * @param confirmationCode ��� �������������.
	 * @throws com.rssl.auth.csa.back.exceptions.ConfirmationFailedException ������������� � ������ ������������� ���� ������������.
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
						setInfo("��������� ���������� ������������ �������������");
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
	 * ��������� ��������� ��������  �� ������������ ����������.
	 * �� ��������� ��� ���������� ��������� �������� � ������� CONFIRMED.
	 * @throws com.rssl.auth.csa.back.exceptions.IllegalOperationStateException � ������ �������������� ����������.
	 */
	public final void checkExecuteAllowed() throws IllegalOperationStateException
	{
		if (OperationState.CONFIRMED != getState())
		{
			throw new IllegalOperationStateException("�������� " + getOuid() + " �� ����� ���� ���������. ������� ������ ��������:" + getState());
		}
	}

	/**
	 * @return ����� ����� ������ �������������
	 */
	public static int getCongirmationLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getConfirmationTimeout();
	}

	public String getAppName() {return null;}
}
