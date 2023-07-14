package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.sms.banking.test.SmsBankingLoginTest;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

/**
 * @author hudyakov
 * @ created 06.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class CommandFactoryTest extends BusinessTestCaseBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final CommandFactory commandFactory = new CommandFactory();
	private static final boolean useRetailV51 = true;

	public void testHelpCommand() throws Exception
	{
		Command command = commandFactory.newInstance("? Balans");
		createTestLogin();
        command.execute();
	}

	public void testInfoCommand() throws Exception
	{
		Command command = commandFactory.newInstance("Info");
		createTestLogin();
		command.execute();
	}

	public void testRestCommand() throws Exception
	{
		Command command = commandFactory.newInstance("Balans,C4654");
		createTestLogin();
        command.execute();
	}

	public void testExtractCommand() throws Exception
	{
		Command command = commandFactory.newInstance(" Vypiska;A1515 ");
		createTestLogin();
        command.execute();
	}

	public void testPaymentCommand() throws Exception
	{
		Command command = commandFactory.newInstance("Platezh;ip 23");
		createTestLogin();
        command.execute();
	}

	public void testTransferCommand() throws Exception
	{
		Command command = commandFactory.newInstance("Perevod;A00002;A00014;45");
		createTestLogin();
        log.info(command.execute());
	}

	public void testExchangeCommand() throws Exception
	{
		Command command = commandFactory.newInstance("Obmen;A4652;A1515;33");
		createTestLogin();
        command.execute();
	}

	public void testConfirmCommand() throws Exception
	{
		Command command = commandFactory.newInstance("Perevod;A00002;A00014;45");
		createTestLogin();
		log.info(command.execute());

		Command confirmCommand = commandFactory.newInstance("4545;03059460");
		log.info(confirmCommand.execute());
	}

	public void testAsyncConfirmCommand() throws Exception
	{
		Command command = commandFactory.newInstance("Perevod;A00002;A00014;45");
		createTestLogin();
		log.info(command.execute());

		command = commandFactory.newInstance("Perevod;A00002;A00014;47");
		createTestLogin();
		log.info(command.execute());

		Command confirmCommand = commandFactory.newInstance("4545;03059460");
		log.info(confirmCommand.execute());

		confirmCommand = commandFactory.newInstance("4545;03059460");
		log.info(confirmCommand.execute());
	}
	private void createTestLogin() throws Exception
	{

/*	todo BUG013795: Убрать лишние зависимости модуля SmsBanking. 	
		if (JNDIUnitTestHelper.notInitialized())
		{
			try
			{
				JNDIUnitTestHelper.init();
			}
			catch (NamingException ne)
			{
				ne.printStackTrace();
				fail("NamingException thrown on Init : " + ne.getMessage());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				fail("Exception thrown on Init : " + e.getMessage());
			}
		}
		if (useRetailV51)
		{
			new RsV51GateInitializer().configure();
			try
			{
				new com.rssl.phizicgate.rsV51.jni.RetailJNIService().start();
			}
			catch(MBeanException ex)
			{
				throw new GateException(ex);
			}
		}
		else
		{
			new RSRetailV6r4GateInitializer().configure();
			try
			{
				new RetailJNIService().start();
			}
			catch(MBeanException ex)
			{
				throw new GateException(ex);
			}
		}
*/
		/*ActivePerson person = PhoneNumberLoginAction.loginByPhone("45");
		SmsBankingLoginTest.initializeContexts(person);
		SmsBankingLoginTest.initializeConfigs(person);*/
	}
}
