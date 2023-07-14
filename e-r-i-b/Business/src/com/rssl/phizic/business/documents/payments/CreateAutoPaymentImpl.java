package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 * Заявка создания автоплатежа
 */
public class CreateAutoPaymentImpl extends AutoPaymentBase implements CreateAutoPayment
{
	/**
    * Фактичский тип документа
    */
	public Class<? extends GateDocument> getType()
	{
		return CreateAutoPayment.class;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);

		Element root = document.getDocumentElement();
		setReceiverPointCode(XmlHelper.getSimpleElementValue(root, PROVIDER_EXTERNAL_ID));

		String strReceiverId = XmlHelper.getSimpleElementValue(root, RECEIVER_INTERNAL_ID);
		setReceiverInternalId(StringHelper.isEmpty(strReceiverId) ? null : Long.parseLong(strReceiverId));
	}

}
