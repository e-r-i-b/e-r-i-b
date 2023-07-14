package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.ParameterListHandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;

/**
 *
 * Фильтруем длительные поручения.
 * За исключением платежей описанных в параметре exclusion, например автоплатежи.
 * 
 * @author egorova
 * @ created 05.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class GateDocumentIsLongOfferFilter extends ParameterListHandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		//Автоплатеж не является длительным поручением.
		if (stateObject instanceof AutoPayment)
			return false;
		
		return (stateObject instanceof AbstractLongOfferDocument) && isLongOffer(stateObject);
	}

	private boolean isLongOffer(StateObject stateObject)
	{
		return ((AbstractLongOfferDocument) stateObject).isLongOffer();
	}
}
