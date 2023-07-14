package com.rssl.phizic.business.documents.exceptions;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author krenev
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class WrongDocumentTypeException extends DocumentException
{
	/**
	 * Конструктор исключения 
	 * @param document документ с неверным типом
	 * @param expected ожидаемый тип документа
	 */
	public WrongDocumentTypeException(BusinessDocument document, Class expected)
	{
		super("Неверный тип документа "+document.getClass().getName()+". Ожидается "+expected.getName());
	}
}
