package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.IMSICheckException;
import com.rssl.auth.csa.back.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizicgate.mobilebank.MobileBankIMSIHelper;
import com.rssl.phizicgate.mobilebank.MobileBankRegistrationHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class GeneratePasswordOperation extends LoginBasedOperationBase
{
	private static final JDBCActionExecutor executor = new JDBCActionExecutor("jdbc/MobileBank", com.rssl.phizic.logging.messaging.System.jdbc);

	private static final MobileBankIMSIHelper mobileBankIMSIHelper = new MobileBankIMSIHelper(executor);
	private static final MobileBankRegistrationHelper registrationHelper = new MobileBankRegistrationHelper(executor);
	;

	private Set<String> excludedPhones;
	private boolean ignoreIMSICheck;

	public GeneratePasswordOperation() {}

	public void initialize(String login, boolean ignoreIMSICheck) throws Exception
	{
		this.ignoreIMSICheck = ignoreIMSICheck;
		super.initialize(login);
	}

	public void initialize(Connector connector, boolean ignoreIMSICheck) throws Exception
	{
		this.ignoreIMSICheck = ignoreIMSICheck;
		if (!ignoreIMSICheck)
			checkIMSI(connector);
	}

	protected void checkBeforeInitialize(Connector connector) throws Exception
	{
		if (!ignoreIMSICheck)
			checkIMSI(connector);
	}

	private void checkIMSI(Connector connector) throws Exception
	{
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
	}

	public GeneratePasswordOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public Connector execute() throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session hibernateSession) throws Exception
			{
				Connector connector = getConnector();
				connector.generatePassword(excludedPhones);
				return connector;
			}
		});
	}

	public Set<String> getExcludedPhones()
	{
		return excludedPhones;
	}

	public void setExcludedPhones(Set<String> excludedPhones)
	{
		this.excludedPhones = excludedPhones;
	}
}
