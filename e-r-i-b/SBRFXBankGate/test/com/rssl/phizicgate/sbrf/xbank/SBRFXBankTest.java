package com.rssl.phizicgate.sbrf.xbank;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.test.DataSourceHelper;
import junit.framework.TestCase;

import java.io.InvalidClassException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.naming.NamingException;

/**
 * @author Gololobov
 * @ created 16.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class SBRFXBankTest extends TestCase
{
	protected void setUp() throws Exception
	{
		initConfigs();
	}

	private void initConfigs() throws InvalidClassException, NamingException
	{
		DataSourceHelper.createJUnitDataSource(new XBankJUnitDatabaseConfig());
	}

	private Account buildAccount()
	{
		TestAccount account = new TestAccount();
		account.setNumber("42307840903821049485");
		return account;
	}

	/*
	  * Тест получения расширенной выписки по счету
	 */
	public void testGetAbstract() throws GateException, GateLogicException
	{
		BankrollServiceImpl bankrollService = new BankrollServiceImpl(GateSingleton.getFactory());
		Calendar fromDate = new GregorianCalendar(2000, 1, 1);
		Calendar toDate = new GregorianCalendar(2012, 1, 1);

		Account account = buildAccount();
		//Получить расширенную выписку
		bankrollService.getAccountAbstract(account,fromDate,toDate,true);
	}
}
