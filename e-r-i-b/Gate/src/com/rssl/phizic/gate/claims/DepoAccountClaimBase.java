package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author lukina
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

public interface DepoAccountClaimBase extends SynchronizableDocument
{
	/**
	 * Внешний id счета депо
	 */
	String getDepoExternalId();

	/**
	 * Номер счета депо
	 * @return id
	 */
	String getDepoAccountNumber();
}
