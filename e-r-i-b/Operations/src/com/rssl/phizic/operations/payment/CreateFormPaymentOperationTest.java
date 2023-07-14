package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.payments.forms.PaymentFormServiceTest;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Collections;
import java.util.Map;

/**
 * @author Roshka
 * @ created 29.11.2005
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class CreateFormPaymentOperationTest extends BusinessTestCaseBase
{
	public void testCreateFormPaymentOperation() throws Exception
	{
		createTestClientContext();
		EditDocumentOperation operation = new CreateFormPaymentOperation();

		String paymentFormName = PaymentFormServiceTest.getSomePaymentFormName();
		NewDocumentSource source = new NewDocumentSource(paymentFormName, mockValuesSource(), CreationType.internet, CreationSourceType.ordinary);
		operation.initialize(source);
	}

	private FieldValuesSource mockValuesSource()
	{
		return new FieldValuesSource()
		{
			public String getValue(String name)
			{
				return null;
			}

			public Map<String, String> getAllValues()
			{
				return Collections.emptyMap();
			}

			public boolean isChanged(String name)
			{
				return false;
			}

			public boolean isEmpty()
			{
				return true;
			}

			public boolean isMasked(String name)
			{
				return false;
			}
		};
	}
}
