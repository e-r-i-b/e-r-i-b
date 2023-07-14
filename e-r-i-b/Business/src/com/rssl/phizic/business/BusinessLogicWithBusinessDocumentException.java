package com.rssl.phizic.business;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.forms.AfterExceptionDocumentProcessHandler;
import com.rssl.phizic.utils.ClassHelper;

/**
 * Логическое исключение с сохранение объекта, при обработке которого оно возникло.
 *
 * @author bogdanov
 * @ created 02.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class BusinessLogicWithBusinessDocumentException extends BusinessLogicException
{
	private final BusinessDocument businessDocument;
	private final String processDocumentHandlerName;

	public BusinessLogicWithBusinessDocumentException(Throwable cause, BusinessDocument businessDocument, String handlerName)
	{
		super(cause);
		this.businessDocument = businessDocument;
		this.processDocumentHandlerName = handlerName;
	}

	public BusinessLogicWithBusinessDocumentException(String message, BusinessDocument businessDocument, String handlerName)
	{
		super(message);
		this.businessDocument = businessDocument;
		this.processDocumentHandlerName = handlerName;
	}

	public BusinessLogicWithBusinessDocumentException(String message, Throwable cause, BusinessDocument businessDocument, String handlerName)
	{
		super(message, cause);
		this.businessDocument = businessDocument;
		this.processDocumentHandlerName = handlerName;
	}

	/**
	 * Обработчик.
	 *
	 * @return обработчик.
	 * @throws BusinessException
	 */
	public AfterExceptionDocumentProcessHandler getHandler() throws BusinessException
	{
		try
		{
			return ClassHelper.<AfterExceptionDocumentProcessHandler>loadClass(processDocumentHandlerName).newInstance();
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * @return документ.
	 */
	public BusinessDocument getBusinessDocument()
	{
		return businessDocument;
	}
}
