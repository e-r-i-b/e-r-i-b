package com.rssl.phizic.business.documents.metadata;

import com.rssl.phizic.business.documents.metadata.checkers.DocumentChecker;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class DefaultOperationOptions implements OperationOptions
{
	private String formName;
	private DocumentChecker documentChecker;

	public DefaultOperationOptions(String formName, DocumentChecker documentChecker)
	{
		this.documentChecker = documentChecker;
		this.formName = formName;
	}

	/**
	 * @return имя формы операции
	 */
	public String getFormName()
	{
		return formName;
	}

	/**
	 * @return флаг возможности выполнения операции
	 * @param businessDocument
	 */
	public boolean canDo(BusinessDocument businessDocument)// throws DocumentException, DocumentLogicException;
	{
		return documentChecker.check(businessDocument);
	}
}
