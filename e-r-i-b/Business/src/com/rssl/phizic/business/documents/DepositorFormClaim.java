package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.documents.GateDocument;
import org.w3c.dom.Document;

/**
 * @author lukina
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepositorFormClaim extends AbstractDepoAccountClaim implements com.rssl.phizic.gate.claims.DepositorFormClaim
{

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.DepositorFormClaim.class;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
	    super.readFromDom(document, updateState, messageCollector);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
