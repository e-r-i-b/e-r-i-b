package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.operations.payment.CreateAutoPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * —оздание автоплатежа по конкретным получател€м
 * @author niculichev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentAction extends EditPaymentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.changeConditions");
		map.remove("afterAccountOpening");
		return map;
	}
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		CreateAutoPaymentForm frm = (CreateAutoPaymentForm) form;
		CreateAutoPaymentOperation editServicePaymentOperation = createEditOperation(request, frm, messageCollector);

		updateFormAdditionalData(frm, editServicePaymentOperation);

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	protected CreateAutoPaymentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		CreateAutoPaymentForm frm = (CreateAutoPaymentForm) form;
		Long providerId = frm.getRecipient();

		CreateAutoPaymentOperation operation = createOperation("CreateAutoPaymentOperation", "CreateAutoPaymentPayment");
		operation.initialize(providerId);

		return operation;
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		CreateAutoPaymentForm frm = (CreateAutoPaymentForm) form;
		EditDocumentOperation operation = createOperation("CreateAutoPaymentOperation", "CreateAutoPaymentPayment");
		FieldValuesSource valueSource = new CompositeFieldValuesSource(getRequestFieldValuesSource(), new MapValuesSource(frm.getFields()));

		try
		{
			operation.initialize(createNewDocumentSource(FormConstants.CREATE_AUTOPAYMENT_FORM, valueSource, messageCollector));
			if (!doLongOffer(operation, valueSource))
				return createFailureForward(frm, createEditOperation(request, frm, messageCollector));
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			return createFailureForward(frm, createEditOperation(request, frm, messageCollector));
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return createFailureForward(frm, createEditOperation(request, frm, messageCollector));
		}

		return createNextStageDocumentForward(operation);

	}

	private ActionForward createFailureForward(EditPaymentForm frm, EditDocumentOperationBase operation) throws BusinessException, BusinessLogicException
	{
		updateFormAdditionalData(frm, operation);
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	private void updateFormAdditionalData(EditPaymentForm frm, EditDocumentOperationBase editDocumentOperation) throws BusinessException, BusinessLogicException
	{
		CreateAutoPaymentForm form = (CreateAutoPaymentForm) frm;
		CreateAutoPaymentOperation operation = (CreateAutoPaymentOperation) editDocumentOperation;
		form.setChargeOffResources(operation.getChargeOffResources());
		form.setProviderName(operation.getProviderName());
		form.setServiceName(operation.getServiceName());
		form.setRequisite(operation.getRequisite());
	}
}
