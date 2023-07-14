package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.IMSICheckException;
import com.rssl.auth.csa.back.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.config.ConfigFactory;
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
 * Опреация проверки IMSI
 * @author Jatsky
 * @ created 28.01.15
 * @ $Author$
 * @ $Revision$
 */

public class CheckIMSIOperation extends LoginBasedOperationBase
{
	private static final JDBCActionExecutor executor = new JDBCActionExecutor("jdbc/MobileBank", com.rssl.phizic.logging.messaging.System.jdbc);
	private static final MobileBankIMSIHelper mobileBankIMSIHelper = new MobileBankIMSIHelper(executor);

	public void checkIMSI(String login) throws Exception
	{
		Connector connector = findAuthenticableConnecorByLogin(login);
		MobileBankRegistrationHelper registrationHelper = new MobileBankRegistrationHelper(executor);
		// проверка подключения карты идентификатора в качестве платежной к МБК. CHG054074
		boolean useMobilebankAsApp = ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp();
		if (connector.getType() == ConnectorType.TERMINAL)
		{
			if (ConfigFactory.getConfig(CardsConfig.class).isNeedAdditionalCheckMbCard())
			{
				List<MobileBankRegistration> registrations = useMobilebankAsApp ?
						GateSingleton.getFactory().service(MobileBankService.class).getRegistrations4(connector.getCardNumber(), GetRegistrationMode.BOTH) :
						registrationHelper.getRegistrations(connector.getCardNumber(), GetRegistrationMode.BOTH);
				if (registrations.isEmpty())
					throw new RestrictionException("Карта клиента, по которой получен идентификатор, не подключена к услуге «Мобильный банк».");
			}
		}

		String mbSystemId = ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId();
		Set<String> phonesByCardNumbers = useMobilebankAsApp ?
				GateSingleton.getFactory().service(MobileBankService.class).getRegPhonesByCardNumbers(Collections.singletonList(connector.getCardNumber()), GetRegistrationMode.BOTH) :
				registrationHelper.getRegPhonesByCardNumbers(Collections.singletonList(connector.getCardNumber()), GetRegistrationMode.BOTH);
		if (CollectionUtils.isEmpty(phonesByCardNumbers))
			throw new MobileBankRegistrationNotFoundException("Не найдено ни одного телефона");

		Map<String, SendMessageError> stringSendMessageErrorMap = useMobilebankAsApp ?
				GateSingleton.getFactory().service(MobileBankService.class).sendIMSICheck(phonesByCardNumbers.toArray(new String[phonesByCardNumbers.size()])) :
				mobileBankIMSIHelper.sendIMSICheck(mbSystemId, phonesByCardNumbers.toArray(new String[phonesByCardNumbers.size()]));
		;

		Set<String> errorPhones = stringSendMessageErrorMap.keySet();
		if (errorPhones.size() == phonesByCardNumbers.size())
			throw new IMSICheckException("Зарегистрирована смена SIM-карты на телефоне клиента.", errorPhones);
	}

	public CheckIMSIOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}
}
