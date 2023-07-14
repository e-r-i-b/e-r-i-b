package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.IMSICheckException;
import com.rssl.auth.csa.back.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.auth.csa.back.servises.connectors.PasswordBasedConnector;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.mobilebank.MobileBankIMSIHelper;
import com.rssl.phizicgate.mobilebank.MobileBankRegistrationHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;

import java.util.*;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author krenev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RestorePasswordOperation extends ConfirmableOperationBase
{
	private static final String LOGIN_PARAMETER_NAME = "login";
	private static final String GUID_PARAM = "connector";
	private static final JDBCActionExecutor executor = new JDBCActionExecutor("jdbc/MobileBank", System.jdbc);

	private static final MobileBankIMSIHelper mobileBankIMSIHelper = new MobileBankIMSIHelper(executor);
	private static final MobileBankRegistrationHelper registrationHelper = new MobileBankRegistrationHelper(executor);

	public RestorePasswordOperation() {}

	public RestorePasswordOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public String getCardNumberFoSendConfirm() throws Exception
	{
		return getConnector().getCardNumber();
	}

	private void setConnectorUID(String guid)
	{
		addParameter(GUID_PARAM, guid);
	}

	private String getConnectorUID()
	{
		return getParameter(GUID_PARAM);
	}

	/**
	 * провести инициализацию операции.
	 * @param login логин пользователя.
	 * @param confirmStrategyType тип подтверждения
	 * @throws Exception
	 */
	public void initialize(String login, ConfirmStrategyType confirmStrategyType) throws Exception
	{
		setLogin(login);
		Connector connector = findAuthenticableConnecorByLogin(login);

		// проверка подключения карты идентификатора в качестве платежной к МБК. CHG054074
		boolean useMobilebankAsApp = ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp();
		if (connector.getType() == ConnectorType.TERMINAL)
		{
			if (ConfigFactory.getConfig(CardsConfig.class).isNeedAdditionalCheckMbCard())
			{
				List<MobileBankRegistration> registrations = useMobilebankAsApp ?
						GateSingleton.getFactory().service(MobileBankService.class).getRegistrations4(connector.getCardNumber(), GetRegistrationMode.SOLID) :
						registrationHelper.getRegistrations(connector.getCardNumber(), GetRegistrationMode.SOLID);
				if (registrations.isEmpty())
					throw new RestrictionException("Уважаемый клиент! Для восстановления пароля в Сбербанк Онлайн Ваша карта, по которой получен идентификатор, должна быть подключена к услуге «Мобильный банк».");
			}
		}

		// Дополнительная проверка ИМСИ для НЕ ЕРМБ клиентов
		if (!ERMBConnector.isExistNotClosedByProfileId(getProfileId()))
		{
			Set<String> phonesByCardNumbers = useMobilebankAsApp ?
					GateSingleton.getFactory().service(MobileBankService.class).getRegPhonesByCardNumbers(Collections.singletonList(connector.getCardNumber()), GetRegistrationMode.BOTH) :
					registrationHelper.getRegPhonesByCardNumbers(Collections.singletonList(connector.getCardNumber()), GetRegistrationMode.BOTH);
			if (CollectionUtils.isEmpty(phonesByCardNumbers))
				throw new MobileBankRegistrationNotFoundException("Не найдено ни одного телефона");

			String mbSystemId = ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId();
			Map<String, SendMessageError> stringSendMessageErrorMap = useMobilebankAsApp ?
					GateSingleton.getFactory().service(MobileBankService.class).sendIMSICheck(phonesByCardNumbers.toArray(new String[phonesByCardNumbers.size()])) :
					mobileBankIMSIHelper.sendIMSICheck(mbSystemId, phonesByCardNumbers.toArray(new String[phonesByCardNumbers.size()]));
			;

			Set<String> errorPhones = stringSendMessageErrorMap.keySet();
			if (errorPhones.size() == phonesByCardNumbers.size())
				throw new IMSICheckException("Зарегистрирована смена SIM-карты на Вашем телефоне. Для восстановления пароля доступа в систему обратитесь в контактный центр банка", errorPhones);

			this.excludedPhones = errorPhones;
		}

		setConnectorUID(connector.getGuid());
		super.initialize(confirmStrategyType);
	}

	public Connector execute(final String password) throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session hibernateSession) throws Exception
			{
				Connector result = findConnectorByGuid(getConnectorUID(), LockMode.UPGRADE_NOWAIT);
				if (StringHelper.isEmpty(password))
				{
					result.generatePassword(excludedPhones);
				}
				else
				{
					if (!(result instanceof PasswordBasedConnector))
					{
						throw new UnsupportedOperationException("Коннектор" + result.getGuid() + " не поддерживает установку произвольного пароля.");
					}
					((PasswordBasedConnector) result).changePassword(password);
				}
				return result;
			}
		},
				PasswordRestrictionException.class);
	}

	/**
	 * Получить коннектор, для которого происходит восстановление пароля
	 * @return коннектор, для которого происходит восстановление пароля
	 */
	public Connector getConnector() throws Exception
	{
		return findConnectorByGuid(getConnectorUID(), null);
	}

	/**
	 * @return время жизни заявки
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getRestorePasswordTimeout();
	}

	@Override
	protected boolean useAlternativeRegistrationsMode()
	{
		return true;
	}

	/**
	 * Положить в операции данные об устройстве клиента
	 * @param rsaData - данные
	 */
	public void setRSAData(Map<String, String> rsaData)
	{
		addParameterWithReplacingDelimiter(DEVICE_TOKEN_FSO_PARAMETER_NAME, rsaData.get(DEVICE_TOKEN_FSO_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, rsaData.get(DEVICE_TOKEN_COOKIE_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(DEVICE_PRINT_PARAMETER_NAME, rsaData.get(DEVICE_PRINT_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(DOM_ELEMENTS_PARAMETER_NAME, rsaData.get(DOM_ELEMENTS_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(JS_EVENTS_PARAMETER_NAME, rsaData.get(JS_EVENTS_PARAMETER_NAME));

		addParameterWithReplacingDelimiter(PAGE_ID_PARAMETER_NAME, rsaData.get(PAGE_ID_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(HTTP_ACCEPT_HEADER_NAME, rsaData.get(HTTP_ACCEPT_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_ACCEPT_CHARS_HEADER_NAME, rsaData.get(HTTP_ACCEPT_CHARS_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, rsaData.get(HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_REFERRER_HEADER_NAME, rsaData.get(HTTP_REFERRER_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_ACCEPT_ENCODING_HEADER_NAME, rsaData.get(HTTP_ACCEPT_ENCODING_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_USER_AGENT_HEADER_NAME, rsaData.get(HTTP_USER_AGENT_HEADER_NAME));
	}

	/**
	 * Получить данные об устройстве клиента из параметров операции
	 * @return - данные
	 */
	public Map<String, String> getRSAData()
	{
		Map<String, String> result = new TreeMap<String, String>();

		result.put(DEVICE_TOKEN_FSO_PARAMETER_NAME, getParameterWithReplacingDelimiter(DEVICE_TOKEN_FSO_PARAMETER_NAME));
		result.put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, getParameterWithReplacingDelimiter(DEVICE_TOKEN_COOKIE_PARAMETER_NAME));
		result.put(DEVICE_PRINT_PARAMETER_NAME, getParameterWithReplacingDelimiter(DEVICE_PRINT_PARAMETER_NAME));
		result.put(DOM_ELEMENTS_PARAMETER_NAME, getParameterWithReplacingDelimiter(DOM_ELEMENTS_PARAMETER_NAME));
		result.put(JS_EVENTS_PARAMETER_NAME, getParameterWithReplacingDelimiter(JS_EVENTS_PARAMETER_NAME));

		result.put(PAGE_ID_PARAMETER_NAME, getParameterWithReplacingDelimiter(PAGE_ID_PARAMETER_NAME));
		result.put(HTTP_USER_AGENT_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_USER_AGENT_HEADER_NAME));
		result.put(HTTP_ACCEPT_ENCODING_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_ENCODING_HEADER_NAME));
		result.put(HTTP_REFERRER_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_REFERRER_HEADER_NAME));
		result.put(HTTP_ACCEPT_CHARS_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_CHARS_HEADER_NAME));
		result.put(HTTP_ACCEPT_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_HEADER_NAME));
		result.put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_LANGUAGE_HEADER_NAME));


		return result;
	}

	/**
	 * Положить введённый пользователем логин в параметры операции
	 * @param login - логин
	 */
	private void setLogin(String login)
	{
		addParameter(LOGIN_PARAMETER_NAME, login);
	}

	/**
	 * @return логин введённый пользователем на первом шаге операции восстановления
	 */
	public String getLogin()
	{
		return getParameter(LOGIN_PARAMETER_NAME);
	}

}
