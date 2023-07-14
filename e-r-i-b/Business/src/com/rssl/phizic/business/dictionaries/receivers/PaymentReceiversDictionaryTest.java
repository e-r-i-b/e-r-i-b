package com.rssl.phizic.business.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.receivers.generated.PaymentDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;

import java.util.List;

/**
 * @author Kidyaev
 * @ created 28.12.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
@ExcludeTest(configurations="sbrf")
public class PaymentReceiversDictionaryTest extends BusinessTestCaseBase
{
	public void testContactReceiversDictionary() throws BusinessException
	{
		PaymentReceiversDictionary dictionary = new PaymentReceiversDictionary();
		assertNotNull(dictionary);

		List<PaymentDescriptor> pds = dictionary.getPaymentDescriptors();

		for (PaymentDescriptor pd : pds)
		{
			List<ReceiverDescriptor> rds = dictionary.getReceiverDescriptors(pd.getAppointment());
			assertNotNull(rds);

			for (ReceiverDescriptor rd : rds)
			{
				ReceiverDescriptor rd1 = dictionary.getReceiverDescriptor(pd.getAppointment(), rd.getKey());
				assertSame(rd, rd1);
			}
		}
	}
}
