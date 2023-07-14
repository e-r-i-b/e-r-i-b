package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.back.exceptions.LoginRestrictionException;
import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.integration.UserInfoProvider;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.DisposableConnector;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * �������� ��������� ���������� ������ � ������
 * @author Jatsky
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

public class UserRegistrationDisposableOperation extends Operation
{

	private static final String CARD_NUMBER_PARAM = "cardNumber";
	private static final String CARD_VALID_PARAM = "isValidCard";
	private static final String USER_ID_PARAM = "userId";

	/**
	 * ��������� �����������
	 */
	public UserRegistrationDisposableOperation()
	{
	}

	/**
	 * ������������������� ��������
	 * @param cardNumber ����� �����
	 * @throws Exception
	 */
	public void initialize(String cardNumber) throws Exception
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByCardNumber(cardNumber);
		setCardValid(userInfo.isActiveCard() && userInfo.isMainCard());
		setUserId(userInfo.getUserId());
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				return null;
			}
		});
	}

	public UserRegistrationDisposableOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
		setCardNumber(identificationContext.getCardNumber());
	}

	public String getCardNumberFoSendConfirm()
	{
		return getCardNumber();
	}

	protected String getUserId()
	{
		return getParameter(USER_ID_PARAM);
	}

	private void setUserId(String userId)
	{
		addParameter(USER_ID_PARAM, userId);
	}

	protected String getCardNumber()
	{
		return getParameter(CARD_NUMBER_PARAM);
	}

	private void setCardNumber(String cardNumber)
	{
		addParameter(CARD_NUMBER_PARAM, cardNumber);
	}

	private void setCardValid(boolean cardValid)
	{
		addParameter(CARD_VALID_PARAM, cardValid);
	}

	/**
	 * @return ������� �� ����� �����������
	 */
	public boolean isCardValid()
	{
		return Boolean.valueOf(getParameter(CARD_VALID_PARAM));
	}

	protected RegistrationType getRegistrationType()
	{
		return RegistrationType.REMOTE;
	}

	/**
	 * @return ����� ����� ������
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getRegistrationTimeout();
	}

	/**
	 * ��������� ������, �� ���������������� �������.
	 * @param sendSMS ������� �������� ���
	 * @return ���������
	 * @throws Exception
	 */
	public DisposableConnector execute(final String sendSMS) throws Exception
	{
		DisposableConnector connector;
		try
		{
			connector = execute(new HibernateAction<DisposableConnector>()
			{
				public DisposableConnector run(org.hibernate.Session session) throws Exception
				{
					final boolean needSendSMS = "true".equals(sendSMS);
					Profile profile = lockProfile();
					//�������� ���� ���� ������� �����������
					List<CSAConnector> registeredCSAConnectors = CSAConnector.findNotClosedByProfileID(getProfileId());
					if (!registeredCSAConnectors.isEmpty())
					{
						//��: ���� ������ ����� ��� ������� ����� � ������ � ������� ��������� ��������� �����������, �� ��� ���������� �� �����.
						//��: ��� ���� ��� ������ ������ � ������ ���������� ����� ������������ ����������������.
						for (CSAConnector registeredConnector : registeredCSAConnectors)
						{
							registeredConnector.close();//������� ��������� � ������ ����� ��������� �����.
						}
					}

					final DisposableConnector registeredDisposableConnector = DisposableConnector.findByProfileId(getProfileId());
					//��� ������������� ���������. ���� �� ��� ���������������� ������������.
					if (registeredDisposableConnector == null)
					{
						return DisposableConnector.generateDisposableConnector(getUserId(), getCbCode(), getCardNumber(), profile, getRegistrationType(), needSendSMS);
					}
					else
					{
						return ActiveRecord.executeAtomic(new HibernateAction<DisposableConnector>()
						{

							public DisposableConnector run(org.hibernate.Session session) throws Exception
							{

								registeredDisposableConnector.setState(ConnectorState.ACTIVE);
								registeredDisposableConnector.save();
								registeredDisposableConnector.generateDisposableLogin();
								registeredDisposableConnector.generateDisposablePassword(needSendSMS);
								return registeredDisposableConnector;
							}
						});
					}
				}
			},
					PasswordRestrictionException.class, LoginRestrictionException.class);
		}
		catch (ConstraintViolationException e)
		{
			throw new LoginAlreadyRegisteredException(e);
		}

		return connector;
	}
}
