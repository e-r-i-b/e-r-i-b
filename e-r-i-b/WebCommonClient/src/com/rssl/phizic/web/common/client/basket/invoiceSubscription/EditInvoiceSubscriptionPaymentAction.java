package com.rssl.phizic.web.common.client.basket.invoiceSubscription;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewCreateInvoiceSubscriptionSource;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.MaskingInfo;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.basket.invoiceSubscription.EditInvoiceSubscriptionDocumentOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentAction;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Ёкшн редактирование за€вки на создание подписки получени€ инвойсов
 * @author niculichev
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class EditInvoiceSubscriptionPaymentAction extends EditPaymentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.remove", "remove");
		map.put("button.save", "save");
		map.put("button.prev", "prev");
		return map;
	}

	protected ActionForward findBackForward(ActionMapping mapping, BusinessDocument document)
	{
		return mapping.findForward(FORWARD_BACK);
	}

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		DocumentSource source = createExistingDocumentSource(form.getId());

		EditInvoiceSubscriptionDocumentOperation operation= createOperation(EditInvoiceSubscriptionDocumentOperation.class, "CreateInvoiceSubscriptionPayment");
		operation.initialize(source, getRequestFieldValuesSource());

		return operation;
	}

	protected void updateForm(CreatePaymentForm frm, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		super.updateForm(frm, operation, valuesSource);

		EditInvoiceSubscriptionPaymentForm form = (EditInvoiceSubscriptionPaymentForm) frm;
		EditInvoiceSubscriptionDocumentOperation op = (EditInvoiceSubscriptionDocumentOperation) operation;
		form.setAccountingEntity(op.getAccountingEntity());
	}

	protected FieldValuesSource getUnmaskFieldValuesSource(EditDocumentOperation operation, CreatePaymentForm form, FieldValuesSource valuesSource) throws BusinessException
	{
		CreateFormPaymentOperation op = (CreateFormPaymentOperation) operation;
		FieldValuesSource documentFieldValuesSource = op.getDocumentFieldValuesSource();

		Map<String, String> seriesAndNumber = op.getPersonDocumentsMaskInfo();
		if(MapUtils.isEmpty(seriesAndNumber))
			return documentFieldValuesSource;

		FieldValuesSource seriesAndNumberDocUnmaskValueSource =
				MaskPaymentFieldUtils.buildChooseDocumentInfoValueSource(valuesSource, seriesAndNumber);

		return new CompositeFieldValuesSource(seriesAndNumberDocUnmaskValueSource, documentFieldValuesSource);
	}

	protected MaskingInfo getMaskingInfo(EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException
	{
		CreateFormPaymentOperation op = (CreateFormPaymentOperation) operation;
		return new MaskingInfo(op.getMetadata(), valuesSource, op.getPersonDocumentsMaskInfo().values());
	}

	protected String createRedirectUrl(EditInvoiceSubscriptionPaymentForm frm) throws BusinessLogicException, BusinessException
	{
		EditDocumentOperation operation = createInvoiceSubscriptionOperation(frm);
		if (operation != null)
		{
			UrlBuilder urlBuilder = new UrlBuilder();
			urlBuilder.setUrl(DefaultDocumentAction.createStateUrl(operation.getDocumentSate()));
			urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getDocument().getId()));
			return urlBuilder.toString();
		}
		return null;
	}

	protected EditDocumentOperation createInvoiceSubscriptionOperation(EditInvoiceSubscriptionPaymentForm frm) throws BusinessException, BusinessLogicException
	{
		NewCreateInvoiceSubscriptionSource source = new NewCreateInvoiceSubscriptionSource(frm.getId(), getCreationType());
		CreateFormPaymentOperation operation = createOperation(EditInvoiceSubscriptionDocumentOperation.class, FormConstants.CREATE_INVOICE_SUBSCRIPTION_PAYMENT);
		operation.initialize(source);
		addLogParameters(new BeanLogParemetersReader("ќбрабатываема€ сущность", operation.getDocument()));
		// посылаем null т.к. документ не должен ничем обновл€тс€
		operation.makeLongOffer(null);

		return operation;
	}

	protected CreationType getCreationType()
	{
		return CreationType.internet;
	}
}
