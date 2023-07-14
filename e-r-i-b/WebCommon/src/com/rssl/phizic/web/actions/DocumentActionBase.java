package com.rssl.phizic.web.actions;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicWithBusinessDocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.forms.AfterExceptionDocumentProcessHandler;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;

/**
 * Базовый экшн для обработки документов.
 *
 * @author bogdanov
 * @ created 10.07.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class DocumentActionBase extends OperationalActionBase
{
	/**
	 * Обрабатывает документ при возникновении ошибки и выполняет переход на нужный url.
	 *
	 * @param exception исключение, хранящее документ.
	 * @return путь для редиректа.
	 */
	protected final ActionForward processDocumentInError(BusinessLogicWithBusinessDocumentException exception, HttpServletRequest request, ActionMapping mapping) throws BusinessException
	{
	    try
	    {
			ActionMessages actionErrors = new ActionMessages();
			AfterExceptionDocumentProcessHandler handler = exception.getHandler();
		    actionErrors.add(handler.getErrorMessage(), new ActionMessage(handler.getErrorMessage(), false));
			saveErrors(request.getSession(), actionErrors);
		    log.error(exception.getMessage(), exception);
			return mapping.findForward(handler.process(exception.getBusinessDocument()));
	    }
	    catch(DocumentException ex)
	    {
		    throw new BusinessException(ex);
	    }
		catch(DocumentLogicException ex)
	    {
		    saveError(request, ex);
		    return null;
	    }
	}

	protected boolean showMessageBox(BusinessDocument document)
	{
		if (document.getNotice() == null)
			return false;
		currentRequest().setAttribute("documentNotice", document.getNotice());
		document.removeNotice();
		return true;
	}
}
