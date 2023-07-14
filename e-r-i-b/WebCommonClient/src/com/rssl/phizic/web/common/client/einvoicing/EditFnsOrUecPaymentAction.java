package com.rssl.phizic.web.common.client.einvoicing;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExternalDocumentSource;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Создание платежа ФНС или УЭК
 *
 * @author bogdanov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 */

public class EditFnsOrUecPaymentAction extends EditEInvoicingPaymentAction
{
	protected ActionForward getStartActionForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	protected EditServicePaymentOperation createOperationInner(String formName) throws BusinessException
	{
		return createOperation("EditFnsOrUecPaymentOperation", formName);
	}

	protected DocumentSource createNewDocumentSource(EditServicePaymentForm form) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource fieldValuesSource = new MapValuesSource(prepareFieldInputValue(form));
		return new ExternalDocumentSource(form.getOrderId(), fieldValuesSource, getNewDocumentCreationType(), CreationSourceType.ordinary);
	}
}
