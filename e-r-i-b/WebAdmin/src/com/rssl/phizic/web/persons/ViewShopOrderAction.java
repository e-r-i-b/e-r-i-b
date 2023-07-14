package com.rssl.phizic.web.persons;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.EmployeeExternalShopDocumentSource;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.ViewShopOrderOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Ёкшн просмотра интернет-заказа в корзине
 * @author niculichev
 * @ created 21.08.15
 * @ $Author$
 * @ $Revision$
 */
public class ViewShopOrderAction extends ViewActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewShopOrderForm frm = (ViewShopOrderForm) form;

		FieldValuesSource valuesSource = new MapValuesSource(
				Collections.singletonMap(PaymentFieldKeys.ORDER_ID_KEY, StringHelper.getEmptyIfNull(frm.getOrderId())));

		DocumentSource source = new EmployeeExternalShopDocumentSource(frm.getPersonId(), frm.getOrderId(), valuesSource);
		ViewDocumentOperation operation = createOperation("ViewShopOrderOperation", "PersonManagement");
		operation.initialize(source);

		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewShopOrderForm frm = (ViewShopOrderForm) form;
		ViewShopOrderOperation op = (ViewShopOrderOperation) operation;

		frm.setHtml(buildFormHtml(op, frm));
		frm.setFormName(op.getMetadata().getName());
		frm.setProviderName(op.getReceiverName());
	}

	protected String buildFormHtml(ViewDocumentOperation operation, ActionForm form) throws BusinessException, BusinessLogicException
	{
		return operation.buildFormHtml(new TransformInfo("view", "html"),
				new FormInfo(currentRequest().getContextPath(), currentServletContext().getInitParameter("resourcesRealPath"), getSkinUrl(form)));
	}

	protected boolean isAjax()
	{
		return true;
	}
}
