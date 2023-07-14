package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankService;

/**
 * Генератор пароля, использующий ХП для ЕРМБ-клиентов
 * @author Rtischeva
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */
public class ERMBPasswordGenerator implements PasswordGenerator
{
	private String cardNumber;

	private String phoneNumber;

	public ERMBPasswordGenerator(String cardNumber, String phoneNumber)
	{
		this.cardNumber = cardNumber;
		this.phoneNumber = phoneNumber;
	}

	public void generatePassword() throws Exception
	{
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp())
			GateSingleton.getFactory().service(MobileBankService.class).generatePasswordForErmbClient(cardNumber, phoneNumber);
		else
			com.rssl.auth.csa.back.integration.mobilebank.MobileBankService.getInstance().generatePasswordForErmbClient(cardNumber, phoneNumber);
	}
}
