package com.rssl.phizic.business.payments.forms;

import com.rssl.phizic.test.BusinessTestCaseBase;

import java.io.FileInputStream;

/**
 * @author Evgrafov
 * @ created 21.11.2006
 * @ $Author: krenev $
 * @ $Revision: 22135 $
 */

@SuppressWarnings({"JavaDoc"})
public class FormParserTest extends BusinessTestCaseBase
{
	private static final String PATH = "Business/settings/payments/Test";

	public void test() throws Exception
	{
		FileInputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(PATH + "/ElectricPayment/ElectricPayment.pfd.xml");
			FormParser parser = new FormParser();
			parser.parse(inputStream);

			assertNotNull(parser.getWithdrawOptions());
			assertNotNull(parser.getEditOptions());
			assertNotNull(parser.getDocumentClassName());
			assertFalse(parser.getDictionaries().isEmpty());
		}
		finally
		{
			if (inputStream != null)
				inputStream.close();
		}
	}
}