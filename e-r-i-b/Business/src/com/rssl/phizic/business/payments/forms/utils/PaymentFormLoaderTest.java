package com.rssl.phizic.business.payments.forms.utils;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 31.10.2005
 * Time: 17:58:58
 */
@SuppressWarnings({"JavaDoc"})
public class PaymentFormLoaderTest extends BusinessTestCaseBase
{
    private static final String GLOBAL_PATH = "Business/settings/payments/globals";
	private static final String CONFIGURATION_PATH = "Business/settings/payments/demo";

    public void manualTestPaymentFormLoader() throws Exception
    {
	    PaymentFormsLoader paymentFormsLoader = new PaymentFormsLoader();
	    paymentFormsLoader.saveFormsDataFromPath(GLOBAL_PATH, CONFIGURATION_PATH);
    }

	public void manualTestPaymentFormLoaderWithDefaulTransormers() throws Exception
	{
		PaymentFormsLoader paymentFormsLoader = new PaymentFormsLoader();
		paymentFormsLoader.saveFormsDataFromPath(CONFIGURATION_PATH+"/test/TestPayment", "");
	}

	/* just for green JUnit ;) */
    public void testStub()
    {
    }

}
