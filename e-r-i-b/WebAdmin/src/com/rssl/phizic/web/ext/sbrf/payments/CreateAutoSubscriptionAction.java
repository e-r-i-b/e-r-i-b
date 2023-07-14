package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.InitalServicePaymentDocumentSource;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.CreateAutoSubscriptionActionBase;
import com.rssl.phizic.web.actions.payments.forms.CreateAutoSubscriptionPaymentForm;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Первый шаг создания шинного автоплатежа(автоподписки)
 * @author niculichev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoSubscriptionAction extends CreateAutoSubscriptionActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		CreateAutoSubscriptionPaymentForm frm = (CreateAutoSubscriptionPaymentForm) form;
		frm.setOperationUID(OperationContext.getCurrentOperUID());

		CreateESBAutoPayOperation operation = createEditOperation(request, frm, messageCollector);

		//заносим на форму информацию о полях и поставщиках(биллинговых услугах)
		updateFormAdditionalData(frm, operation);

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	protected CreateESBAutoPayOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		return createAutoPaymentOperation((EditServicePaymentForm) form, messageCollector);
	}

	protected CreateESBAutoPayOperation createAutoPaymentOperation(EditServicePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		Long paymentId = form.getId();
		CreateESBAutoPayOperation operation = createOperation("CreateESBAutoPayOperation", "CreateEmployeeAutoPayment");

		DocumentSource source = null;
		if(paymentId != null && paymentId > 0L)
		{
			source = new InitalServicePaymentDocumentSource(createExistingDocumentSource(paymentId), new MapValuesSource(prepareFieldInputValue(form)));
		}
		else
		{
			source = createNewDocumentSource(getFormName(form), new MapValuesSource(prepareFieldInputValue(form)), messageCollector);
		}

		operation.initialize(source, form.getRecipient(), ObjectEvent.EMPLOYEE_EVENT_TYPE);

		return operation;
	}

	protected void updateFormAdditionalData(EditServicePaymentForm form, EditServicePaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		CreateAutoSubscriptionPaymentForm frm = (CreateAutoSubscriptionPaymentForm) form;
		CreateESBAutoPayOperation op = (CreateESBAutoPayOperation) operation;

		frm.setActivePerson(op.getPerson());
		frm.setFields(operation.getDocumentFieldValues());

		super.updateFormAdditionalData(form, operation);
	}	

	protected ActionForward createNextStageDocumentForward(EditDocumentOperation operation)
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(operation.getDocumentSate().getEmployeeForm());
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getDocument().getId()));
		return new ActionForward(urlBuilder.toString(), true);
	}

	protected DocumentSource createExistingDocumentSource(Long paymentId) throws BusinessException, BusinessLogicException
	{
		return new ExistingSource(paymentId, new ChecksOwnersPaymentValidator());
	}

	protected boolean isAutoSubscriptionPay(EditServicePaymentForm form) throws BusinessException, BusinessLogicException
	{
		return true;
	}

	protected void saveAutoPaymentMessages(HttpServletRequest request, EditDocumentOperation operation) 
	{
		saveStateMachineEventMessages(request, operation, false);
	}
}
