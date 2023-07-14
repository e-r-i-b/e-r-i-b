package com.rssl.phizic.web.common.mobile.payments.forms;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentMobileOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 01.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewMobilePaymentAction extends ViewDocumentAction
{
	private static final String SUPPORTED_FORMS = "supportedForms";
	private static final String MESSAGE = "Вы не можете просмотреть данную операцию через приложение. Для просмотра воспользуйтесь полной версией \"Сбербанк Онлайн\"";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected String buildFormHtml(ViewDocumentOperation operation, ActionForm form) throws BusinessException, BusinessLogicException
	{
		String supportedForms = currentServletContext().getInitParameter(SUPPORTED_FORMS);
		if (!supportedForms.contains(operation.getEntity().getFormName()))
		{
			throw new BusinessLogicException(MESSAGE);
		}
		return operation.buildMobileXml(getTransformInfo(), getFormInfo(form));
	}

	protected TransformInfo getTransformInfo() throws BusinessException
	{
		return new TransformInfo(getMode(), "mobile");
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		return new FormInfo(frm.getAdditionPaymentInfo());
	}

	protected String getAdditionPaymentInfo(ViewDocumentOperation operation)
	{
		return DocumentHelper.getPaymentFormInfo(operation.getEntity(), CreationType.mobile);
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessLogicException, BusinessException
	{
		super.updateFormData(operation, form);
		HttpServletRequest request = WebContext.getCurrentRequest();
		String message = HttpSessionUtils.removeSessionAttribute(request, SESSION_ADDITIONAL_MESSAGE_KEY);
		if (StringHelper.isNotEmpty(message))
			saveMessage(request, message);
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;

		ExistingSource source = null;
		try
		{
			source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());
		}
		catch (NotOwnDocumentException e)
		{
			throw new AccessException(e.getMessage());
		}
		sendAnalyzeRequestToRSA();
		ViewDocumentOperation operation;
		//Право на печать чека проверяем только для тех определенных платежей
		if (checkAccess(ViewDocumentMobileOperation.class, source.getMetadata().getName()))
			operation = createOperation(ViewDocumentMobileOperation.class, source.getMetadata().getName());
		else
			operation = (ViewDocumentOperation) super.createViewEntityOperation(form);
		operation.initialize(source);
		return operation;
	}

	private void sendAnalyzeRequestToRSA()
	{
		try
		{
			if (!StringHelper.isEmpty(RSAContext.getMobileSdkData()))
			{
				FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.VIEW_STATEMENT);
				sender.send();
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

	}
}
