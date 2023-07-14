package com.rssl.phizic.gorod.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.test.GateTestCaseBase;

import java.util.List;

/**
 * @author Gainanov
 * @ created 24.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceTest extends GateTestCaseBase
{
	public void test()
	{
		PaymentRecipientGateServiceImpl service = new PaymentRecipientGateServiceImpl(gateFactory);
		try
		{
			List<Recipient> list = service.getPersonalRecipientList("1231231", null);
			assertNotNull(list);
			List<Field> fields = service.getPersonalRecipientFieldsValues(list.get(0), "1231321");
			assertNotNull(fields);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
