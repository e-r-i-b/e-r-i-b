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
 * Операция регистрации пользователя.
 */

public class UserRegistrationOperation extends ConfirmableOperationBase
{
	private static final String CARD_NUMBER_PARAM = "cardNumber";
	private static final String CARD_ACTIVE_PARAM = "isActiveCard";
	private static final String CARD_MAIN_PARAM = "isMainCard";
	private static final String USER_ID_PARAM = "userId";

	/**
	 * Дефолтный конструктор
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
	 * провести инициализацию операции.
	 * @param cardNumber номер карты.
	 * @param confirmStrategyType тип подтверждения
	 * @throws Exception
	 */
	public void initialize(String cardNumber, ConfirmStrategyType confirmStrategyType) throws Exception
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByCardNumber(cardNumber);
		//валидной считается основная активная карта.
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
	 * @return активна ли карта регистрации
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
	 * @return является ли карта регистрации основной
	 */
	public boolean isMainCard()
	{
		return Boolean.valueOf(getParameter(CARD_MAIN_PARAM));
	}

	/**
	 * Исполнить заявку, те зарегистрировать клиента.
	 * @param login логин
	 * @param passwordValue пароль
	 * @param needNotification необходимо ли уведомление.
	 * @return коннектор
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
					//получаем если есть текущая регистрация
					List<CSAConnector> registeredConnectors = CSAConnector.findNotClosedByProfileID(getProfileId());
					if (!registeredConnectors.isEmpty())
					{
						//проверяем возможность повторной регистрации
						if (ConfigFactory.getConfig(Config.class).isDenyMultipleRegistration())
						{
							//РО: клиент, который пытается повторно зарегистрироваться, должен проходить процедуру восстановления пароля
							throw new UserAlreadyRegisteredException("Для профиля " + getProfileId() + " уже существует зарегистрированные ЦСА коннекторы");
						}
						//РО: Если клиент ранее уже получал логин и пароль с помощью процедуры удаленной регистрации, то они заменяются на новые.
						//РО: При этом для нового логина и пароля включается режим ограниченной функциональности.
						for (CSAConnector registeredConnector : registeredConnectors)
						{
							registeredConnector.close();//Удаляем коннеторы и дальше будем создавать новый.

						}
					}

					//Все подготовления выполнены. пора бы уже зарегистрировать пользователя.
					CSAConnector result = new CSAConnector(getUserId(), getCbCode(), getCardNumber(), profile, getRegistrationType());
					result.save();
					//Устанавливаем уровень безопасности
					profile.setSecurityType(getSecurityType(profile, result.getType()));
					profile.save();
					session.flush();

					Login loginEntity = Login.findBylogin(login);
					//Если же существующий логин принадлежит этому же клиенту, то система должна позволять клиенту зарегистрироваться со своим старым логином.
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
	 * проверить является ли владелец профиля profile владельцем и connector.
	 * @param profile профиль
	 * @param login логин
	 * @return да/нет
	 */
	private boolean checkOwnerLogin(Profile profile, Login login) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль должен быть задан");
		}

		if (profile == null)
		{
			throw new IllegalArgumentException("Логин должен быть задан");
		}

		Connector connector = Connector.findById(login.getConnectorId(), null);
		if (connector == null)
		{
			return false; //неизвестный коннектор никому не принадлежит.
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
				return false; //владельца логина определить не удалось. считаем, что он не принадлежит переданному профилю.
			}
			//поехали сравнивать ДP, ДУЛ И ФИО.
			return DateUtils.isSameDay(profile.getBirthdate(), userInfo.getBirthdate()) &&
					profile.getNormalizedPassport().equals(Profile.normalizePassport(userInfo.getPassport())) &&
					profile.getNormalizedFIO().equals(Profile.normalizeFIO(userInfo.getSurname(), userInfo.getFirstname(), userInfo.getPatrname()));
		}
		catch (Exception e)
		{
			log.error("Ошибка при сравнении владельца коннектора и профиля", e);
			return false;// при ошибках сравнения владельца коннектора и профиля считаем, что владельцы разные
		}
	}

	/**
	 * @return Незакрытые коннекторы профиля связанного с операцией
	 * @throws Exception
	 */
	public List<CSAConnector> getNotClosedProfileConnectors() throws Exception
	{
		if(getProfileId() == null)
			throw new IllegalStateException("В операции отсутсвует профиль");

		return CSAConnector.findNotClosedByProfileID(getProfileId());
	}

	/**
	 * @return время жизни заявки
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
