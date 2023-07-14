package com.rssl.phizic.web.client.moneyBox;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringConfirmDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringDocumentStrategy;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.fraudMonitoring.exceptions.RequireAdditionConfirmFraudException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessageTemplateType;
import com.rssl.phizic.messaging.MessagingHelper;
import com.rssl.phizic.operations.ext.sbrf.payment.EditMoneyBoxOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditDocumentActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 * Асинхронный экшен редактирования копилки(не заявки).
 */
public class AsyncEditMoneyBoxAction extends EditDocumentActionBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.next");
		map.remove("button.prev");
		map.remove("button.saveAsDraft");
		map.remove("button.makeLongOffer");
		map.remove("button.remove");
		map.remove("button.save");
		map.remove("button.edit");
		map.remove("button.edit_template");
		map.remove("button.makeAutoTransfer");
		map.put("button.dispatch", "confirm");
		return map;
	}

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		try
		{
			DocumentSource source = new NewDocumentSource(FormConstants.EDIT_MONEY_BOX_FORM, new RequestValuesSource(currentRequest()), getNewDocumentCreationType(), CreationSourceType.ordinary, messageCollector);

			EditMoneyBoxOperation operation = createOperation(EditMoneyBoxOperation.class, DocumentHelper.getClientServiceName(source));
			operation.initialize(source, getRequestFieldValuesSource());

			return operation;
		}
		catch (FormException e)
		{
			if (e.getCause() instanceof GateLogicException)
				throw new BusinessLogicException(e.getCause().getMessage(), e);
			throw new BusinessException(e);
		}
	}

	protected void updateForm(CreatePaymentForm frm, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		super.updateForm(frm, operation,valuesSource);
		frm.setType("edit");
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		EditMoneyBoxOperation operation = getOperation(request);

		Metadata metadata = operation.getMetadata();
		FieldValuesSource valuesSource = getRequestFieldValuesSource();

		Form validateForm = metadata.getForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, validateForm, DocumentValidationStrategy.getInstance());

		if (!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			//передаем на форму ошибки валидации
			updateForm(frm, operation, getShowFormFieldValuesSource(operation));
			return mapping.findForward("Failed");
		}
		Map<String, Object> formData = processor.getResult();
		try
		{
			operation.updateDocument(formData);

			doFraudControl(operation.getDocument());

			operation.save();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward("Failed");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
			return mapping.findForward("Failed");
		}
		frm.setId(operation.getDocumentId());
		saveMessage(request, StrutsUtils.getMessage("moneyBox.edit.message.success", "moneyboxBundle"));
		return mapping.findForward("Success");
	}

	protected void doFraudControl(BusinessDocument document) throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringDocumentStrategy strategy = new FraudMonitoringConfirmDocumentStrategy(document);
			strategy.process(null, null);
		}
		catch (RequireAdditionConfirmFraudException e)
		{
			MessagingHelper.sendMessage(document, MessageTemplateType.REVIEW.name() + ".FM." + document.getClass().getName());
			throw e;
		}
		catch (ProhibitionOperationFraudException e)
		{
			MessagingHelper.sendMessage(document, MessageTemplateType.DENY.name() + ".FM." + document.getClass().getName());
			throw e;
		}
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}
}
