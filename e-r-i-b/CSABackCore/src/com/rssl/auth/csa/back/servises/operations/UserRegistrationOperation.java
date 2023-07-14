package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.back.exceptions.LoginRestrictionException;
import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.exceptions.UserAlreadyRegisteredException;
import com.rssl.auth.csa.back.integration.UserInfoProvider;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.DisposableConnector;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * �������� ����������� ������������.
 */

public class UserRegistrationOperation extends ConfirmableOperationBase
{
	private static final String CARD_NUMBER_PARAM = "cardNumber";
	private static final String CARD_ACTIVE_PARAM = "isActiveCard";
	private static final String CARD_MAIN_PARAM = "isMainCard";
	private static final String USER_ID_PARAM = "userId";

	/**
	 * ��������� �����������
	 */
	public UserRegistrationOperation()
	{
	}

	public UserRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
		setCardNumber(identificationContext.getCardNumber());
	}

	/**
	 * �������� ������������� ��������.
	 * @param cardNumber ����� �����.
	 * @param confirmStrategyType ��� �������������
	 * @throws Exception
	 */
	public void initialize(String cardNumber, ConfirmStrategyType confirmStrategyType) throws Exception
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByCardNumber(cardNumber);
		//�������� ��������� �������� �������� �����.
		setActiveCard(userInfo.isActiveCard());
		setMainCard(userInfo.isMainCard());
		setUserId(userInfo.getUserId());

		super.initialize(confirmStrategyType);
	}

	public String getCardNumberFoSendConfirm()
	{
		return getCardNumber();
	}

	public String getUserId()
	{
		return getParameter(USER_ID_PARAM);
	}

	private void setUserId(String userId)
	{
		addParameter(USER_ID_PARAM, userId);
	}

	private String getCardNumber()
	{
		return getParameter(CARD_NUMBER_PARAM);
	}

	private void setCardNumber(String cardNumber)
	{
		addParameter(CARD_NUMBER_PARAM, cardNumber);
	}

	private void setActiveCard(boolean activeCard)
	{
		addParameter(CARD_ACTIVE_PARAM, activeCard);
	}

	/**
	 * @return ������� �� ����� �����������
	 */
	public boolean isActiveCard()
	{
		return Boolean.valueOf(getParameter(CARD_ACTIVE_PARAM));
	}

	private void setMainCard(boolean cardValid)
	{
		addParameter(CARD_MAIN_PARAM, cardValid);
	}

	/**
	 * @return �������� �� ����� ����������� ��������
	 */
	public boolean isMainCard()
	{
		return Boolean.valueOf(getParameter(CARD_MAIN_PARAM));
	}

	/**
	 * ��������� ������, �� ���������������� �������.
	 * @param login �����
	 * @param passwordValue ������
	 * @param needNotification ���������� �� �����������.
	 * @return ���������
	 * @throws Exception
	 */
	public Connector execute(final String login, final String passwordValue, boolean needNotification) throws Exception
	{
		Connector connector;
		try
		{
			connector = execute(new HibernateAction<Connector>()
			{
				public Connector run(org.hibernate.Session session) throws Exception
				{
					Profile profile = lockProfile();
					//�������� ���� ���� ������� �����������
					List<CSAConnector> registeredConnectors = CSAConnector.findNotClosedByProfileID(getProfileId());
					if (!registeredConnectors.isEmpty())
					{
						//��������� ����������� ��������� �����������
						if (ConfigFactory.getConfig(Config.class).isDenyMultipleRegistration())
						{
							//��: ������, ������� �������� �������� ������������������, ������ ��������� ��������� �������������� ������
							throw new UserAlreadyRegisteredException("��� ������� " + getProfileId() + " ��� ���������� ������������������ ��� ����������");
						}
						//��: ���� ������ ����� ��� ������� ����� � ������ � ������� ��������� ��������� �����������, �� ��� ���������� �� �����.
						//��: ��� ���� ��� ������ ������ � ������ ���������� ����� ������������ ����������������.
						for (CSAConnector registeredConnector : registeredConnectors)
						{
							registeredConnector.close();//������� ��������� � ������ ����� ��������� �����.

						}
					}

					//��� ������������� ���������. ���� �� ��� ���������������� ������������.
					CSAConnector result = new CSAConnector(getUserId(), getCbCode(), getCardNumber(), profile, getRegistrationType());
					result.save();
					//������������� ������� ������������
					profile.setSecurityType(getSecurityType(profile, result.getType()));
					profile.save();
					session.flush();

					Login loginEntity = Login.findBylogin(login);
					//���� �� ������������ ����� ����������� ����� �� �������, �� ������� ������ ��������� ������� ������������������ �� ����� ������ �������.
					if(loginEntity != null && checkOwnerLogin(profile, loginEntity))
					{
						loginEntity.changeConnector(result.getId());
					}
					else
					{
						Login.createClientLogin(login, result.getId());
					}

					result.changePassword(passwordValue);

					DisposableConnector activeDisposableConnector = DisposableConnector.findNotClosedByUserId(getUserId());
					if (activeDisposableConnector != null)
					{
						activeDisposableConnector.close();
					}

					return result;
				}
			},
					PasswordRestrictionException.class, LoginRestrictionException.class);
		}
		catch (ConstraintViolationException e)
		{
			throw new LoginAlreadyRegisteredException(e);
		}

		sendNotification(getCardNumber());

		return connector;
	}

	protected SecurityType getSecurityType(Profile profile, ConnectorType connectorType) throws GateException, GateLogicException
	{
		return SecurityTypeHelper.calcSecurityType(profile, getCardNumber(), connectorType);
	}

	protected RegistrationType getRegistrationType()
	{
		return RegistrationType.REMOTE;
	}

	/**
	 * ��������� �������� �� �������� ������� profile ���������� � connector.
	 * @param profile �������
	 * @param login �����
	 * @return ��/���
	 */
	private boolean checkOwnerLogin(Profile profile, Login login) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� ������ ���� �����");
		}

		if (profile == null)
		{
			throw new IllegalArgumentException("����� ������ ���� �����");
		}

		Connector connector = Connector.findById(login.getConnectorId(), null);
		if (connector == null)
		{
			return false; //����������� ��������� ������ �� �����������.
		}
		if (connector.getProfile().getId().equals(profile.getId()))
		{
			return true;
		}

		try
		{
			CSAUserInfo userInfo = connector.isMigrated() ? SyncUtil.getUserInfoByUserId(connector.getUserId()) : SyncUtil.getUserInfoByCardNumber(connector.getCardNumber());
			if (userInfo == null)
			{
				return false; //��������� ������ ���������� �� �������. �������, ��� �� �� ����������� ����������� �������.
			}
			//������� ���������� �P, ��� � ���.
			return DateUtils.isSameDay(profile.getBirthdate(), userInfo.getBirthdate()) &&
					profile.getNormalizedPassport().equals(Profile.normalizePassport(userInfo.getPassport())) &&
					profile.getNormalizedFIO().equals(Profile.normalizeFIO(userInfo.getSurname(), userInfo.getFirstname(), userInfo.getPatrname()));
		}
		catch (Exception e)
		{
			log.error("������ ��� ��������� ��������� ���������� � �������", e);
			return false;// ��� ������� ��������� ��������� ���������� � ������� �������, ��� ��������� ������
		}
	}

	/**
	 * @return ���������� ���������� ������� ���������� � ���������
	 * @throws Exception
	 */
	public List<CSAConnector> getNotClosedProfileConnectors() throws Exception
	{
		if(getProfileId() == null)
			throw new IllegalStateException("� �������� ���������� �������");

		return CSAConnector.findNotClosedByProfileID(getProfileId());
	}

	/**
	 * @return ����� ����� ������
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getRegistrationTimeout();
	}

	@Override
	protected boolean useAlternativeRegistrationsMode()
	{
		return true;
	}
}
