package com.rssl.phizic.business.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentServiceTest;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.payments.forms.meta.ListFormImpl;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 05.12.2005
 * @ $Author: bogdanov $
 * @ $Revision: 53540 $
 */

@SuppressWarnings({"JavaDoc"})
public class PaymentFormServiceTest extends BusinessTestCaseBase
{
	public static String getSomePaymentFormName() throws BusinessException
	{
		PaymentFormService paymentFormService = new PaymentFormService();
		List<MetadataBean> paymentForms = paymentFormService.getAllFormsLight();
		if (paymentForms != null && !paymentForms.isEmpty())
			return paymentForms.get(0).getName();
		return null;
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void testPaymentThereAndBack() throws Exception
	{
		PersonData data = createTestClientContext();

		PaymentFormService formService = new PaymentFormService();

		String paymentFormName = PaymentFormServiceTest.getSomePaymentFormName();
		MetadataBean metadata = formService.findByName(paymentFormName);
		assertNotNull("Форма " + paymentFormName + " не найдена", metadata);

		RurPayment payment = DocumentServiceTest.createTestPayment();

		List<AccountLink> accountLinks = data.getAccounts();
		if (accountLinks.size() > 0)
		{
			Account account = accountLinks.get(0).getValue();
			payment.setChargeOffAccount(account.getNumber());
		}
		else
		{
			payment.setChargeOffAccount("счет не найден...");
		}

		payment.setFormName(metadata.getName());

		payment.setGround("MTS       9161225314");
	}

	public void testListForms() throws BusinessException
	{
		PaymentFormService formService = new PaymentFormService();

		String paymentFormName = PaymentFormServiceTest.getSomePaymentFormName();
		ListFormImpl listForm = formService.findListFormByName(paymentFormName);
		assertNotNull("Форма списка " + paymentFormName + " не найдена", listForm);

		assertNotNull(listForm);
		assertNotNull(listForm.getListTransformation());
		assertNotNull(listForm.getFilterTransformation());
	}
}
