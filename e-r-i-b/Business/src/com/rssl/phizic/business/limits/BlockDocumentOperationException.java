package com.rssl.phizic.business.limits;

import static com.rssl.phizic.business.documents.strategies.limits.Constants.BLOCK_DOCUMENT_OPERATION_ERROR_MESSAGE;

/**
 * исключение о невозможности выполнения операции ввиду превышения суммы операции величины заградительного лимит
 * @author basharin
 * @ created 27.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class BlockDocumentOperationException extends BusinessDocumentLimitException
{
	public BlockDocumentOperationException(String message, Limit limit)
	{
		super(message, limit);
	}

	public String getLogMessage(Long documentId)
	{
		return String.format(BLOCK_DOCUMENT_OPERATION_ERROR_MESSAGE, documentId) + super.getLogMessage(documentId);
	}
}