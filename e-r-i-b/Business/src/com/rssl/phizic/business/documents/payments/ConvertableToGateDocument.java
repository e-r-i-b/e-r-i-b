package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.business.documents.NotConvertibleToGateBusinessException;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * @author Krenev
 * @ created 16.08.2007
 * @ $Author$
 * @ $Revision$
 */
public interface ConvertableToGateDocument
{
	/**
	 * @return документ для гейта
	 */
	GateDocument asGateDocument() throws NotConvertibleToGateBusinessException;
}
