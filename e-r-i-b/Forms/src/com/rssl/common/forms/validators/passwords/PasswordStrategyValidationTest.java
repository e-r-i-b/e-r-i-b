package com.rssl.common.forms.validators.passwords;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class PasswordStrategyValidationTest extends RSSLTestCaseBase
{
	private static final String[] WRONG_PASSWORDS_SBRF = new String[]
			{
					"1jhj3", //length
					"1jh$##*(j3", //content
					"7aa7zz777", //min amount of different symbols
					"123QQQQKHHJHJ", //wrong sequence 1
					"����uerhgjhi", //wrong sequence 2
					"sxeAGHCC UVJHHVD", //wrong sequence 3
					"���2eeeGGHJGJ", //wrong sequence 4
					"q2wyrtutyurruru", //wrong sequence 5
					"�2�3�657456fgfggf", //wrong sequence 6
					"AZSXLJKJLJJLJ", //wrong sequence  7
					"QWEQWE", //wrong sequence  8
					"QAZQAZ", //wrong sequence   9
					"�������������", //wrong sequence 10
					"���2���3���", //wrong sequence 11
					"1���2���", //wrong sequence 12
					"1QAZ2WSX3EDC", //wrong sequence 13
					"1qaz2wsx3edc4", //wrong sequence 14
					"1q2w3e4r5t6", //wrong sequence 15
					"1Q2W3E4R5T6", //wrong sequence 16
					"1�2�3�4�5�6", //wrong sequence 17
					"1�2�3�4�5�6�7", //wrong sequence 18

			};
	private static final String[] GOOD_PASSWORDS_SBRF = new String[]
			{
					"1jrom356g", //length
					"��gt891q", //content
					"q7aa7zz777", //min amount of different symbols
			};

	private static final String[] WRONG_PASSWORDS_RUSSLAV = new String[]
			{
					"1jh$##*(j3", //content
			};
	private static final String[] GOOD_PASSWORDS_RUSSLAV = new String[]
			{
					"q7aa7zz777", //min amount of different symbols
			};

	protected void setUp() throws Exception
	{
		super.setUp();
	}

	public void testPasswordValidationConfig()
	{
		PasswordValidationConfig validationConfig =
				ConfigFactory.getConfig(PasswordValidationConfig.class);

		assertNotNull(validationConfig.getStrategies());
	}

	@IncludeTest(configurations = "sbrf")
	public void testSBRFValidation() throws TemporalDocumentException
	{

		PasswordStrategyValidator pswValidator = new PasswordStrategyValidator();
		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "client");
		checkPaswords(pswValidator, WRONG_PASSWORDS_SBRF, GOOD_PASSWORDS_SBRF);

		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "employee");
		checkPaswords(pswValidator,new String[]{"5456�;;%"}, new String[]{"5456EEREssdd"});
	}

	@IncludeTest(configurations = "russlav")
	public void testRUSSLAValidation() throws TemporalDocumentException
	{
		PasswordStrategyValidator pswValidator = new PasswordStrategyValidator();
		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "employee");
		checkPaswords(pswValidator,WRONG_PASSWORDS_RUSSLAV, GOOD_PASSWORDS_RUSSLAV);

		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "employee");
		checkPaswords(pswValidator,new String[]{"5456�;;%"}, new String[]{"5456EEREssdd"});
	}

	private void checkPaswords(PasswordStrategyValidator validator, String[] wrongPasswords, String[] goodPasswords) throws TemporalDocumentException
	{
		long before = Calendar.getInstance().getTimeInMillis();
		for (String wrongPassword : wrongPasswords)
		{
			boolean condition = validator.validate(wrongPassword);

			assertFalse("..��� �� ��� �� ���, ������: " +
					wrongPassword + " �� ������ ��������� �������� " +
					validator.getMessage(), condition);
			System.out.println("bad psw: " + wrongPassword + " check result: " +
					String.valueOf(condition) + " message: " + validator.getMessage());
		}
		for (String goodPassword : goodPasswords)
		{
			boolean condition = validator.validate(goodPassword);

			assertTrue("..��� �� ��� �� ���, ������: " +
					goodPassword + " ������ ������ �������� " +
					validator.getMessage(), condition);
			System.out.println("good psw: " + goodPassword + " check result: " +
					String.valueOf(condition) + " message: " + validator.getMessage());
		}
		long after = Calendar.getInstance().getTimeInMillis();
		System.out.println("execution time: " + String.valueOf(after - before) + " ms");
	}
}


