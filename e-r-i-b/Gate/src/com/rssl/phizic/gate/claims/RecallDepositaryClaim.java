package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author mihaylov
 * @ created 15.10.2010
 * @ $Author$
 * @ $Revision$
 */

public interface RecallDepositaryClaim extends SynchronizableDocument
{
	/**
	 * Номер отзываемого документа(идентификатор документа в Депозитарии)
	 * @return docNumber
	 */
	String getDocNumber();

	/**
	 * Причина отзыва документа
	 * @return revokePurpose
	 */
	String getRevokePurpose();
}
