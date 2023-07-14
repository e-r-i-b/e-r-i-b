package com.rssl.phizic.web.client.basket.invoiceSubscription;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.operations.basket.invoiceSubscription.EditInvoiceSubscriptionClaimOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditDocumentActionBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 13.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования подписки(услуги)
 */
public class EditInvoiceSubscriptionAction extends EditDocumentActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.next");
		map.remove("button.prev");
		map.remove("button.saveAsDraft");
		map.remove("button.makeLongOffer");
		map.remove("button.remove");
		map.remove("button.edit");
		map.remove("button.edit_template");
		map.remove("button.makeAutoTransfer");
		return map;
	}

	@Override
	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditInvoiceSubscriptionClaimOperation operation = createOperation(EditInvoiceSubscriptionClaimOperation.class, FormConstants.EDIT_INVOICE_SUBSCRIPTION_CLAIM);
		operation.initialize(frm.getId());
		return operation;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm)form;
		EditInvoiceSubscriptionClaimOperation operation = getOperation(request);
		Form validateForm = operation.getMetadata().getForm();
		FieldValuesSource valuesSource = operation.getFieldValuesSource();
		FieldValuesSource requestSource = getValidateFormFieldValuesSource(frm, operation);

		ValidationStrategy strategy = getValidationStrategy(operation);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new CompositeFieldValuesSource(requestSource, valuesSource), validateForm, strategy);
		if (!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			return forwardShow(operation, frm);
		}
		Map<String, Object> formData = processor.getResult();
		operation.updateDocument(formData);
		try{
			operation.save();
		}
		catch (BusinessException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return forwardShow(operation, frm);
		}
		resetOperation(request);
		return mapping.findForward(FORWARD_SHOW_LIST);
	}
}
