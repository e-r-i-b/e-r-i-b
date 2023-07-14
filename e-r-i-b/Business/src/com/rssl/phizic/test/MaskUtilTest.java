package com.rssl.phizic.test;

import com.rssl.phizic.utils.MaskUtil;

import java.io.InvalidClassException;

/**
 * @author Barinov
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class MaskUtilTest extends BusinessTestCaseBase
{
	private void initCardsConfig() throws InvalidClassException
	{
	}

	public void testGetCutCardNumber() throws Exception
	{
		initCardsConfig();
		getCutCardNumber("639072129003750", "6390 72** ***3 750");
		getCutCardNumber("6390721290037501", "6390 72** **** 7501");
		getCutCardNumber("639072129003750123", "6390 72** **** **01 23");
	}

	private void getCutCardNumber(String number, String cutNumber)
	{
		String testCutNumber = MaskUtil.getCutCardNumberForLog(number);
		System.out.println("======================\r\n" +
				testCutNumber + "\r\n" +
				cutNumber + "\r\n"
		);
		assertTrue(cutNumber.equals(testCutNumber));
		
		testCutNumber = MaskUtil.getCutCardNumberPrint(number);
		System.out.println("======================\r\n" +
				testCutNumber + "\r\n" +
				cutNumber + "\r\n"
		);
		assertTrue(cutNumber.equals(testCutNumber));
	}
}
