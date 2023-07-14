package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 06.12.2005
 * @ $Author: vagin $
 * @ $Revision: 64569 $
 */

public class ConfirmDocumentAction extends ConfirmDocumentActionBase
{
    protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.makeLongOffer", "makeLongOffer");
		return map;
	}

	protected ConfirmFormPaymentOperation getConfirmOperation(HttpServletRequest request, ConfirmPaymentByFormForm frm)
            throws BusinessException, BusinessLogicException
    {
	    Long id = frm.getId();
	    ExistingSource source = null;
	    try
	    {
		    source = new ExistingSource(id, new IsOwnDocumentValidator());
	    }
	    catch (NotOwnDocumentException e)
	    {
			throw new AccessException("У данного пользователя нет прав на просмотр платежа с id="+id, e);
	    }

	    ConfirmFormPaymentOperation operation = createConfirmOperation(source);
	    operation.initialize(source);

        return operation;
    }

	protected ConfirmFormPaymentOperation createConfirmOperation(ExistingSource source)
			throws BusinessException, BusinessLogicException
	{
		return createOperation(ConfirmFormPaymentOperation.class, getServiceName(source));
	}

	/**
	 * Возвращаем имя формы 
	 * @param source источник
	 * @return имя формы
	 */
	protected String getServiceName(DocumentSource source)
	{
		return DocumentHelper.getClientServiceName(source);
	}

	public ActionForward makeLongOffer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		EditDocumentForm frm = (EditDocumentForm) form;
		EditDocumentOperation operation = createEditOperation(request, frm);
		try
		{
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getDocument()));
			// посылаем null т.к. документ не должен ничем обновлятся
			operation.makeLongOffer(null);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return start(mapping, frm, request, response);
		}

		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(DefaultDocumentAction.createStateUrl(operation.getDocumentSate()));
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getDocument().getId()));
		return new ActionForward(urlBuilder.toString(), true);
	}

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, EditDocumentForm frm) throws BusinessException, BusinessLogicException
	{
		Long paymentId = frm.getId();
		if (paymentId == null && paymentId <= 0)
			throw new BusinessException("Не указан идентификатор платежа");

		DocumentSource source = new NewDocumentSource(paymentId, new IsOwnDocumentValidator(), CreationType.internet);
		EditDocumentOperation operation = createOperation(CreateFormPaymentOperation.class, source.getMetadata().getName());
		operation.initialize(source, null);

		return operation;
	}
}
