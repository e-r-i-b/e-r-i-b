package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FormException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotResidentException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.CopyDocumentSource;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.templates.validators.CheckInactiveBillingValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.StateTemplateValidator;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.claims.DepositorFormClaim;
import com.rssl.phizic.gate.claims.SecuritiesTransferClaim;
import com.rssl.phizic.gate.claims.SecurityRegistrationClaim;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 22.09.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditPaymentActionBase extends EditDocumentActionBase
{
	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		try
		{
			Long templateId = form.getTemplate();
			Long paymentId = form.getId();
			boolean copy = form.getCopying() != null && form.getCopying();

			DocumentSource source;
			if (paymentId != null && paymentId > 0)
			{
				source = copy ?  createCopyDocumentSource(paymentId) : createExistingDocumentSource(paymentId);
			}
			else if (templateId != null && templateId > 0)
			{
				source = createCopyTemplateSource(templateId, form.isMarkReminder());
				form.setForm(source.getMetadata().getName());
			}
			else
			{
				source = createNewDocumentSource(form.getForm(), new RequestValuesSource(currentRequest()), messageCollector);
			}

			CreateFormPaymentOperation operation
					= createOperation(CreateFormPaymentOperation.class, getServiceName(source));
			operation.initialize(source, getRequestFieldValuesSource());

			return operation;
		}
		catch (FormException e)
		{
			if (e.getCause() instanceof GateLogicException || e.getCause() instanceof NotResidentException) //TODO ?
				throw new BusinessLogicException(e.getCause().getMessage(), e);
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращаем имя формы
	 * @param source источник
	 * @return имя формы
	 */
	protected String getServiceName(DocumentSource source)
	{
		return DocumentHelper.getClientServiceName(source);
	}

	/**
	 * создать источник для для нового документа.
	 * @param formName имя формы
	 * @param initalFieldValuesSource источник значений полей для инициализации документа данными отличными от источника копии.
	 * @param messageCollector хранилище ошибок, может быть null
	 * @return источник
	 */
	protected DocumentSource createNewDocumentSource(String formName, FieldValuesSource initalFieldValuesSource, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		return new NewDocumentSource(formName, initalFieldValuesSource, getNewDocumentCreationType(), CreationSourceType.ordinary, messageCollector);
	}

	/**
	 * создать источник для существющего документа.
	 * @param paymentId идентификатор существующего документа.
	 * @return источник существющего документа
	 */
	protected DocumentSource createExistingDocumentSource(Long paymentId) throws BusinessException, BusinessLogicException
	{
		return new ExistingSource(paymentId, new IsOwnDocumentValidator());
	}

	/**
	 * создать источник данных копии существующего документа
	 * @param paymentId идентификатор документа, на основе которого создается копия(из которого берутся данные)
	 * @return источник документа
	 */
	protected DocumentSource createCopyDocumentSource(Long paymentId) throws BusinessException, BusinessLogicException
	{
		DocumentSource source = new CopyDocumentSource(paymentId, new IsOwnDocumentValidator(), getNewDocumentCreationType());

		BusinessDocument sourceDocument = source.getDocument();
		if (sourceDocument.getOperationDate() != null)
		{
			// добавляем сообщение, информирующее клиента о времени подтверждения повторяемого платежа
			ActionMessages actionMessages = new ActionMessages();
			String operationDateTimeMessage = String.format(DateHelper.formatDateWithStringMonth(sourceDocument.getOperationDate())
					+ " года в " + "%1$tH:%1$tM", sourceDocument.getOperationDate());

			String message = StrutsUtils.getMessage(getKey(sourceDocument), "paymentsBundle", operationDateTimeMessage);
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			saveMessages(currentRequest(), actionMessages);

			sourceDocument.setOperationDate(null);
		}

		return source;
	}

	private String getKey(BusinessDocument sourceDocument)
	{
		String key = "message.copy.payment.warning";
		if(sourceDocument instanceof SecuritiesTransferClaim)
		{
			key = "message.copy.depotransclaim.warning";
		}
		else if(sourceDocument instanceof SecurityRegistrationClaim)
		{
			key = "message.copy.deporegclaim.warning";
		}
		else if(sourceDocument instanceof DepositorFormClaim)
		{
			key = "message.copy.depoformclaim.warning";
		}
		return key;
	}

	/**
	 * создать источник данных копии документа на основе шаблона
	 * @param templateId идентификатор шаблона, на основе которого создается копия(из которого берутся данные)
	 * @return источник документа
	 */
	protected DocumentSource createCopyTemplateSource(Long templateId, boolean reminder) throws BusinessException, BusinessLogicException
	{
		return new NewDocumentSource(templateId, getNewDocumentCreationType(), reminder, new OwnerTemplateValidator(), new StateTemplateValidator(), new CheckInactiveBillingValidator());
	}
}
