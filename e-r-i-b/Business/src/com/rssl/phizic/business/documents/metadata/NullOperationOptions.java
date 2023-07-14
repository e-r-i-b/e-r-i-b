package com.rssl.phizic.business.documents.metadata;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class NullOperationOptions implements OperationOptions
{
	private static NullOperationOptions nullOperationOptionsInstance = new NullOperationOptions ();
	private NullOperationOptions(){
	}

	public static NullOperationOptions getInstance(){
		return nullOperationOptionsInstance;
	}
	/**
	 * @return имя формы операции
	 */
	public String getFormName()
	{
		return null;
	}

	/**
	 * @return флаг возможности выполнения операции
	 * @param businessDocument
	 */
	public boolean canDo(BusinessDocument businessDocument)// throws DocumentException, DocumentLogicException;
	{
		 return false;
	}
}
