package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.ERIBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewTemplateOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ViewTemplateActionBase;
import com.rssl.phizic.web.actions.payments.forms.ViewTemplateForm;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author muhin
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 *
 * Экшен просмотра детальной информации напоминанию в АРМ Сотрудника
 */

public class ViewRemindAction extends ViewTemplateActionBase
{
	private static final String OPEN_PAYMENTS_AND_TEMPLATES = "PaymentsAndTemplates";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		return map;
	}

	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		ViewTemplateOperation operation = createOperation(ViewTemplateOperation.class, source.getMetadata().getName());
		ViewTemplateForm frm = (ViewTemplateForm) form;

		//Если пришли на форму просмотра из календаря, значит необходимо добавить в шаблон информацию о напоминании
		if (frm.isFromFinanceCalendar() && StringHelper.isNotEmpty(frm.getExtractId()))
		{
			operation.initializeFromFinanceCalendar(source, frm.getExtractId());
		}
		else
		{
			operation.initialize(source);
		}

		return operation;
	}

	protected TemplateDocumentSource createExistingTemplateSource(Long id) throws BusinessException, BusinessLogicException
	{
		return new ExistingTemplateSource(id, getTemplateValidators());
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewTemplateForm frm = (ViewTemplateForm) form;
		ViewTemplateOperation viewOperation = (ViewTemplateOperation) operation;

		super.updateFormData(operation, form);

		// обновляем признак доступности внешних платежей со счета
		updateExternalAccountPaymentAllowed(frm, viewOperation);

		saveMessages(currentRequest(), viewOperation.collectInfo());
    }

	/**
	 * Обновить признак доступности шаблонов платежей внешнему получателю со счета
	 * если шаблон платежа не досупен - сообщаем об этом пользователю
	 *
	 * @param form форма
	 * @param operation операция
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void updateExternalAccountPaymentAllowed(ViewTemplateForm form, ViewTemplateOperation operation) throws BusinessLogicException, BusinessException
	{
		TemplateDocument template = operation.getTemplate();
		boolean result = TemplateHelper.isTemplateDisallowedFromAccount(template);

		form.setExternalAccountPaymentAllowed(!result);
		if (result)
		{
			saveMessage(currentRequest(), Constants.EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE);
		}
	}

	@Override
	protected TemplateValidator[] getTemplateValidators() throws BusinessException
	{
		return new TemplateValidator[]{new ERIBTemplateValidator()};
	}

	protected ActionForward createRemoveTemplateForward()
	{
		return getCurrentMapping().findForward(OPEN_PAYMENTS_AND_TEMPLATES);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
