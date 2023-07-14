package com.rssl.phizic.web.ext.sbrf.documents;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.IllegalEventException;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.common.payments.BusinessDocumentsStatesHelper;
import org.apache.struts.action.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 24.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class DocumentRefuseAction extends OperationalActionBase
{
	private static final String FORWARD_LIST = "List";
	private static final Map<String, List<String>> refusedStatesForMashines = new HashMap<String, List<String>>();

	static
	{
		refusedStatesForMashines.put("BillingPaymentStateMachine", Arrays.asList("DISPATCHED", "ERROR", "UNKNOW", "SENT",
						"DELAYED_DISPATCH", "OFFLINE_DELAYED", "BILLING_CONFIRM_TIMEOUT", "BILLING_GATE_CONFIRM_TIMEOUT", "ABS_RECALL_TIMEOUT", "ABS_GATE_RECALL_TIMEOUT"));
		refusedStatesForMashines.put("PaymentStateMachine", Arrays.asList("DELAYED_DISPATCH", "OFFLINE_DELAYED", "DISPATCHED", "UNKNOW", "ERROR"));
		refusedStatesForMashines.put("RurPayment", Arrays.asList("DELAYED_DISPATCH", "OFFLINE_DELAYED", "DISPATCHED", "UNKNOW", "ERROR"));
		refusedStatesForMashines.put("NewRurPayment", Arrays.asList("DELAYED_DISPATCH", "OFFLINE_DELAYED", "DISPATCHED", "UNKNOW", "ERROR"));
		refusedStatesForMashines.put("CreateInvoiceSubscriptionPayment", Arrays.asList("DISPATCHED", "ERROR", "UNKNOW", "OFFLINE_DELAYED"));
		refusedStatesForMashines.put("CreateAutoPaymentStateMachine", Arrays.asList("ERROR", "UNKNOW"));
		refusedStatesForMashines.put("EditOrRefuseAutoPaymentStateMachine", Arrays.asList("ERROR"));
		refusedStatesForMashines.put("SecurityRegistrationClaim", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("DepositorFormClaim", Arrays.asList("ERROR"));
		refusedStatesForMashines.put("RecallDepositaryClaim", Arrays.asList("ERROR"));
		refusedStatesForMashines.put("SecuritiesTransferClaim", Arrays.asList("DISPATCHED", "ERROR", "OFFLINE_DELAYED"));
		refusedStatesForMashines.put("EditAutoSubscriptionPayment", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("DelayAutoSubscriptionPayment", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("RecoveryAutoSubscriptionPayment", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("CloseAutoSubscriptionPayment", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("DelayP2PAutoTransferClaim", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("RecoveryP2PAutoTransferClaim", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("CloseP2PAutoTransferClaim", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("CreateP2PAutoTransferClaim", Arrays.asList("DISPATCHED", "ERROR"));
		refusedStatesForMashines.put("EditP2PAutoTransferClaim", Arrays.asList("DISPATCHED", "ERROR", "OFFLINE_DELAYED"));
		refusedStatesForMashines.put("RefundGoodsClaim", Arrays.asList("DISPATCHED", "UNKNOW"));
		refusedStatesForMashines.put("RollbackOrderClaim", Arrays.asList("DISPATCHED", "UNKNOW"));
	}

	protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.refuse", "refuse");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    DocumentRefuseForm frm = (DocumentRefuseForm) form;
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
	    DocumentSource source = new ExistingSource(frm.getId(), new NullDocumentValidator());
		operation.initialize(source);
	    try
		{
			checkDocumentType(operation.getEntity());
		}
		catch (BusinessLogicException e)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
			return mapping.findForward(FORWARD_LIST);
		}

	    return mapping.findForward(FORWARD_START);
    }

	public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DocumentRefuseForm frm = (DocumentRefuseForm) form;
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);

		ActionMessages msgs = new ActionMessages();

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), DocumentRefuseForm.REFUSE_FORM);
		if(processor.process())
		{
			try
			{
				DocumentSource source = new ExistingSource(frm.getId(), new NullDocumentValidator());
				operation.initialize(source);
				checkDocumentType(operation.getEntity());
				State state = operation.getEntity().getState();
				operation.refuse((String) frm.getField("reason"));

				//лог.
				addLogParameters(new BeanLogParemetersReader("Первоначальный статус", state));
				addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", operation.getEntity()));
			}
			catch(IllegalEventException e)
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("wrong.document.state.exception.default", true));
				saveErrors(currentRequest(), msgs);
				return mapping.findForward(FORWARD_START);
			}

			catch (BusinessLogicException e)
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveErrors(currentRequest(), msgs);
				return mapping.findForward(FORWARD_START);
			}

			return mapping.findForward(FORWARD_LIST);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}
	}

	private void checkDocumentType(BusinessDocument document) throws BusinessLogicException
	{
		List<String> refusedStates = refusedStatesForMashines.get(document.getStateMachineInfo().getName());
		if (refusedStates == null)
			throw new BusinessLogicException("Вы не можете отказать данный тип платежа.");

		if (!refusedStates.contains(document.getState().getCode()))
		{
			StringBuilder states = new StringBuilder();
			for (String state : refusedStates)
				states.append(state).append(", ");

			String parseStates = BusinessDocumentsStatesHelper.parseStates("auditBundle", document, states.substring(0, states.length()-2)); 
			throw new BusinessLogicException("Вы можете отказать данный тип платежа только в состояни" +
					(refusedStates.size() > 1 ? "ях " : "и ") + parseStates + ".");
		}
	}
}