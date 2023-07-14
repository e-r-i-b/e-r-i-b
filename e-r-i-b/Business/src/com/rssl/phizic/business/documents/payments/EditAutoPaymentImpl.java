package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 * ������ �������������� �����������
 */
public class EditAutoPaymentImpl extends AutoPaymentBase implements EditAutoPayment
{
	/**
    * ���������� ��� ���������
    */
	public Class<? extends GateDocument> getType()
	{
		return EditAutoPayment.class;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		storeAutoPaymentData();
	}
}
