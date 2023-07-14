package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.RefuseLongOfferClaim;

/**
 * @author osminin
 * @ created 01.11.2010
 * @ $Author$
 * @ $Revision$
 *
 * Фильтрация заявок на сохранение/отмену длительных поручений
 */
public class LongOfferClaimFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		if (stateObject instanceof RefuseLongOfferClaim)
			return true;
		return (stateObject instanceof AbstractLongOfferDocument) && isLongOffer(stateObject);
	}

	private boolean isLongOffer(StateObject stateObject)
	{
		return ((AbstractLongOfferDocument) stateObject).isLongOffer();
	}
}
