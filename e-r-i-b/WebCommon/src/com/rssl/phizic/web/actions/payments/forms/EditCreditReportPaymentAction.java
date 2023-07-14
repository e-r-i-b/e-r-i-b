package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.web.struts.forms.ActionMessagesCollector;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн редактировани€/создани€ платежа за кредитный отчет
 * @author Rtischeva
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 */
public class EditCreditReportPaymentAction extends EditServicePaymentAction
{
	@Override
	protected void updateFormAdditionalData(EditServicePaymentForm frm, EditServicePaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateFormAdditionalData(frm, operation);

		Metadata metadata = operation.getMetadata();
		frm.setHtml(buildFormHtml(operation, operation.getFieldValuesSource(), frm));
		frm.setForm(metadata.getName());
		frm.setMetadataPath(operation.getMetadataPath());
		frm.setDocument(operation.getDocument());
		//некоторые документы могут в составе своих даных содержать информацию об ошибках. показываем пользователю их, если нужно
		saveErrors(currentRequest(), operation.getDocumentErrors(new ActionMessagesCollector()).errors());

		showDisallowExternalAccountPaymentMessage(operation.getDocument());
		showRiskRecipietMessage(operation);

		//если была попытка получить отчет - сбросить флаг предложени€ обновить в текущей сессии
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData.getMustUpdateCreditReport())
				personData.setMustUpdateCreditReport(null);
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return super.next(mapping, form, request, response);
	}

	@Override
	protected String getFormName(EditPaymentForm frm)
	{
		return FormConstants.CREDIT_REPORT_PAYMENT;
	}

	@Override
	protected String getServiceName(EditServicePaymentForm frm)
	{
		return FormConstants.CREDIT_REPORT_PAYMENT;
	}
}
