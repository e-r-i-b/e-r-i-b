package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.test.BusinessTestCaseBase;
import org.xml.sax.InputSource;

import java.io.FileInputStream;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: kosyakov $
 * @ $Revision: 3400 $
 */

public class PaymentListFormParserTest extends BusinessTestCaseBase
{
	private static final String FORM_PATH = "Business/settings/payments/russlav/InternalPayment/InternalPayment.list-pfd.xml";

	public void testPaymentListFormParser() throws Exception
	{
		ListFormImpl listForm = getTestListForm();

		assertNotNull(listForm);
		assertNotNull(listForm.getFilterForm());
		assertNotNull(listForm.getListSource());
		assertNotNull(listForm.getTitle());

	}

	/**
	 * @return тестовая форма
	 */
	public static ListFormImpl getTestListForm() throws Exception
	{
		PaymentListFormParser parser = new PaymentListFormParser();
		FileInputStream stream = null;
		ListFormImpl listForm = new ListFormImpl();

		try
		{
			stream = new FileInputStream(FORM_PATH);
			parser.parse(listForm, new InputSource(stream));
		}
		finally
		{
			if(stream != null)
				stream.close();
		}
		return listForm;
	}
}
