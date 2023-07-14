package com.rssl.phizic.business.documents.metadata;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

import java.io.Serializable;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public interface OperationOptions extends Serializable
{
	/**
	 * @return имя формы операции
	 */
	String getFormName();

	/**
	 * @return флаг возможности выполнения операции
	 * @param businessDocument
	 */
	 boolean canDo(BusinessDocument businessDocument);// throws DocumentException, DocumentLogicException;
}
