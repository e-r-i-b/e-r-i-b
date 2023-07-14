package com.rssl.phizic.web.client.basket.invoiceSubscription;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.InitalServicePaymentDocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewAutoInvoiceSubscriptionSource;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.MaskingInfo;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.operations.basket.invoiceSubscription.CreateInvoiceSubscriptionDocumentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.CreateAutoSubscriptionActionBase;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Первый шаг создания автоподписки для корзины
 * @author niculichev
 * @ created 04.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateInvoiceSubscriptionAction extends CreateAutoSubscriptionActionBase
{
	private static final String CHOOSE_DATE_INVOICE = "chooseDateInvoice";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.next", "makeLongOffer");
		return map;
	}

	protected void updateFormAdditionalData(EditServicePaymentForm frm, EditServicePaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		CreateInvoiceSubscriptionForm form  = (CreateInvoiceSubscriptionForm) frm;
		CreateInvoiceSubscriptionDocumentOperation op = (CreateInvoiceSubscriptionDocumentOperation) operation;

		super.updateFormAdditionalData(frm, op);
		form.setAccountingEntity(op.getAccountingEntity());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return super.start(mapping, form, request, response);
	}

	protected void updateFormFields(EditServicePaymentForm frm, EditServicePaymentOperation operation, Map<String, String> documentFieldValues) throws BusinessException, BusinessLogicException
	{
		super.updateFormFields(frm, operation, documentFieldValues);
		String chooseDateInvoice = documentFieldValues.get(CHOOSE_DATE_INVOICE);
		frm.setField(CHOOSE_DATE_INVOICE, DateHelper.convertIfXmlDateFormat(chooseDateInvoice));
	}

	protected Map<String, String> prepareFieldInputValue(EditServicePaymentForm frm) throws BusinessException
	{
		CreateInvoiceSubscriptionForm form = (CreateInvoiceSubscriptionForm) frm;

		Map<String, String> params = new HashMap<String, String>();
		params.put(PaymentFieldKeys.PROVIDER_KEY, StringHelper.getEmptyIfNull(form.getRecipient()));

		Long accountingEntityId = form.getAccountingEntityId();
		if(accountingEntityId != null && accountingEntityId != 0)
			params.put(PaymentFieldKeys.ACCOUNTING_ENTITY_ID, accountingEntityId.toString());
		return params;
	}

	protected EditServicePaymentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		return createAutoPaymentOperation((EditServicePaymentForm) form, messageCollector);
	}

	protected EditServicePaymentOperation createAutoPaymentOperation(EditServicePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		CreateInvoiceSubscriptionDocumentOperation operation = createOperation("CreateInvoiceSubscriptionDocumentOperation", "CreateInvoiceSubscriptionPayment");
		operation.initialize(createSource(form, messageCollector), form.getRecipient());
		return operation;
	}

	private DocumentSource createSource(EditServicePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		CreateInvoiceSubscriptionForm frm  = (CreateInvoiceSubscriptionForm) form;
		Long autoId = frm.getAutoId();

		FieldValuesSource fieldValuesSource = new MapValuesSource(prepareFieldInputValue(form));
		// Если пришел autoId, значит создаем по рекомендованной подписке
		if (autoId != null && autoId > 0)
		{
			return new InitalServicePaymentDocumentSource(new NewAutoInvoiceSubscriptionSource(autoId), fieldValuesSource) ;
		}

		Long paymentId = form.getId();
		if (paymentId != null && paymentId > 0)
		{
			return new InitalServicePaymentDocumentSource(createExistingDocumentSource(paymentId), fieldValuesSource);
		}

		return createNewDocumentSource("CreateInvoiceSubscriptionPayment", fieldValuesSource, messageCollector);
	}

	/**
	 * @param operation операция
	 * @param valuesSource окружение значений
	 * @return Информация для маскирования полей
	 */
	protected MaskingInfo getMaskingInfo(EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException
	{
		CreateInvoiceSubscriptionDocumentOperation op = (CreateInvoiceSubscriptionDocumentOperation) operation;
		return new MaskingInfo(op.getMetadata(), valuesSource, op.getSeriesAndNumberPersonDocuments().values());
	}

	protected FieldValuesSource getUnmaskFieldValuesSource(EditDocumentOperation operation, CreatePaymentForm form, FieldValuesSource valuesSource) throws BusinessException
	{
		CreateInvoiceSubscriptionDocumentOperation op = (CreateInvoiceSubscriptionDocumentOperation) operation;
		FieldValuesSource documentFieldValuesSource = op.getDocumentFieldValuesSource();

		Map<String, String> seriesAndNumber = op.getSeriesAndNumberPersonDocuments();
		if(MapUtils.isEmpty(seriesAndNumber))
			return documentFieldValuesSource;

		FieldValuesSource seriesAndNumberDocUnmaskValueSource =
				MaskPaymentFieldUtils.buildChooseDocumentInfoValueSource(valuesSource, seriesAndNumber);

		return new CompositeFieldValuesSource(seriesAndNumberDocUnmaskValueSource, documentFieldValuesSource);
	}
}
