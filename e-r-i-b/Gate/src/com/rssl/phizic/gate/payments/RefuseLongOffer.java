package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author gladishev
 * @ created 12.10.2010
 * @ $Author$
 * @ $Revision$
 */
public interface RefuseLongOffer extends SynchronizableDocument
{
	String getLongOfferExternalId();
}
