package com.rssl.phizic.business.smsbanking;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyServiceImpl;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.test.MockPersonDataProvider;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 13.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsBankingServiceTest extends BusinessTestCaseBase
{
	private static SmsBankingService service = new SmsBankingService();

	public void test() throws Exception
	{
		PersonContext.setPersonDataProvider(new MockPersonDataProvider(null));

//		String command = service.getSmsCommand(createTestTemplate());
//		assertNotNull(command);
	}

	private static BusinessDocument createTestTemplate() throws Exception
	{
		InternalTransfer payment = new InternalTransfer();
		payment.setAdmissionDate(DateHelper.getCurrentDate());
		payment.setDepartment((new DepartmentService()).findById(new Long(1)));
		payment.setFormName("InternalPayment");
		payment.setPayerName("Vano Bregvadze");
		CurrencyServiceImpl currencyService = GateSingleton.getFactory().service(CurrencyServiceImpl.class);
		payment.setChargeOffAmount(new Money(new BigDecimal("99.9"),
								   currencyService.findByAlphabeticCode("RUB")));

		return payment;
	}
}
