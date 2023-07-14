package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankService;

/**
 * @author krenev
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 * Генератор пароля с использованием хранимки МБ
 */
public class MBPasswordGenerator implements PasswordGenerator
{
	private String cardNumber;

	public MBPasswordGenerator(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public void generatePassword() throws Exception
	{
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp())
			GateSingleton.getFactory().service(MobileBankService.class).generatePassword(cardNumber);
		else
			com.rssl.auth.csa.back.integration.mobilebank.MobileBankService.getInstance().generatePassword(cardNumber);
	}
}
