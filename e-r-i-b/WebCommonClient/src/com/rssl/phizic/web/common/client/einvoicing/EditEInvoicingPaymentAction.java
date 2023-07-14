package com.rssl.phizic.web.common.client.einvoicing;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.InvoiceMessage;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExternalShopDocumentSource;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.context.ClientInvoiceCounterHelper;
import com.rssl.phizic.operations.einvoicing.UpdateShopOrderOperation;
import com.rssl.phizic.operations.payment.billing.EditInternetShopPaymentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentAction;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 23.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Первый шаг редактирования платежа, созданного по технологии оплаты e-invoicing
 */
public class EditEInvoicingPaymentAction extends EditServicePaymentAction
{

	private static final String SUCCESS_FORWARD_NAME = "Success";
	private static final String FAILED_FORWARD_NAME  = "Failed";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.delay", "delay");
		map.put("button.delete", "delete");
		map.remove("button.changeConditions");
		map.remove("afterAccountOpening");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward = getStartActionForward(mapping, form, request, response);
		if (forward != null)
			return forward;

		ActionForward startForward = super.start(mapping, form, request, response);
		ActionMessages errors = getErrors(request);
		if (errors.isEmpty())
			return startForward;
		else
		{
			saveSessionErrors(request, errors);
			return mapping.findForward(FORWARD_BACK);
		}
	}

	protected ActionForward getStartActionForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditServicePaymentOperation editServicePaymentOperation = createEditOperation(request, (EditServicePaymentForm) form, new MessageCollector());
		BusinessDocument document = editServicePaymentOperation.getDocument();
		if (document != null && document.getId() != null)
		{
			ActionForward forward = mapping.findForward("ShowDocument");
			return new ActionForward(forward.getPath() + "&id=" + document.getId() + "&objectFormName=" + document.getFormName() + "&stateCode=" + document.getState().getCode(), true);
		}

		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return super.next(mapping, form, request, response);
	}

	@Override
	protected EditServicePaymentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentForm frm = (EditServicePaymentForm) form;

		if (StringHelper.isNotEmpty(form.getOrderId()))
		{
			DocumentSource source = createNewDocumentSource(frm);
			EditServicePaymentOperation operation = createOperationInner(source.getDocument().getFormName());
			operation.initialize(source, (Long)null);

			if(operation instanceof EditInternetShopPaymentOperation
					&& AuthenticationContext.getContext().getVisitingMode() == UserVisitingMode.BASIC)
			{
				ClientInvoiceCounterHelper.updateShopOrdersStoredData(form.getOrderId());
			}

			return operation;
		}

		EditServicePaymentOperation operation = super.createEditOperation(request, form, messageCollector);
		return operation;
	}

	protected EditServicePaymentOperation createOperationInner(String formName) throws BusinessException
	{
		return createOperation("EditInternetShopPaymentOperation", formName);
	}

	protected DocumentSource createNewDocumentSource(EditServicePaymentForm form) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource fieldValuesSource = new MapValuesSource(prepareFieldInputValue(form));
		return new ExternalShopDocumentSource(form.getOrderId(), fieldValuesSource, getNewDocumentCreationType(), CreationSourceType.ordinary);
	}

	protected Map<String, String> prepareFieldInputValue(EditServicePaymentForm frm)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(PaymentFieldKeys.ORDER_ID_KEY, StringHelper.getEmptyIfNull(frm.getOrderId()));
		params.put(PaymentFieldKeys.DOCUMENT_NUMBER_KEY, StringHelper.getEmptyIfNull(frm.getDocumentNumber()));
		return params;
	}

	protected void updateFormAdditionalData(EditServicePaymentForm frm, EditServicePaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(frm, operation, operation.getFieldValuesSource());
		super.updateFormAdditionalData(frm, operation);
	}

	public ActionForward delay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			EditServicePaymentForm frm = (EditServicePaymentForm) form;

			String delayDateString = (String) frm.getField("chooseDelayDateOrder");
			DateParser parser = new DateParser();
			Calendar delayDate = Calendar.getInstance();
			delayDate.setTime(parser.parse(delayDateString));

			UpdateShopOrderOperation operation = createOperation(UpdateShopOrderOperation.class);
			operation.initialize(frm.getOrderId());
			operation.delay(delayDate);

			if(frm.isNeedSaveMessage())
			{
				InvoiceMessage.saveMessage(operation.getShopOrder(), InvoiceMessage.Type.delay);
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, "Во время выполнения операции произошла ошибка. Пожалуйста повторите попытку позднее.");
			return mapping.findForward(FAILED_FORWARD_NAME);
		}
		return mapping.findForward(SUCCESS_FORWARD_NAME);
	}
}
