package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 05.12.2006
 * @ $Author: gladishev $
 * @ $Revision: 7626 $
 */

@SuppressWarnings({"JavaDoc"})
public abstract class ViewDocumentActionBase extends ViewActionBase
{
	public static final String FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION = "ShowLoanClaimWithoutRegistration";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.edit", "edit");
		return map;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
        ViewDocumentForm      frm = (ViewDocumentForm) form;
		ViewDocumentOperation op =  (ViewDocumentOperation) operation;

        Metadata metadata = op.getMetadata();
		BusinessDocument doc = op.getEntity();

		frm.setAdditionPaymentInfo(getAdditionPaymentInfo(op));
		frm.setDocument(doc);
        frm.setHtml(buildFormHtml(op, frm));
        frm.setForm(metadata.getName());

		String formDescription = metadata.getForm().getDescription();
        frm.setTitle(formDescription);
		frm.setTemplateName(doc.getDefaultTemplateName());

		frm.setMetadata(metadata);
		frm.setMetadataPath(op.getMetadataPath());
		frm.setOwner(op.getOwner());
		frm.setDepartment(op.getDepartment());
        frm.setCanWithdraw(op.canWithdraw());
		frm.setComment(op.checkBeforeSetComment());
		frm.setArchive(op.checkBeforeSendInArchive());
		frm.setPrintDocuments(op.checkBeforePrintDocuments());
		frm.setShowDocumentState(true);

		if(doc.getState().equals(new State("ACCEPTED")) && doc.getOperationDate() != null)
			frm.setDateString(DateHelper.toStringTime(doc.getOperationDate().getTime()));
		if(doc.getState().equals(new State("EXECUTED"))&& doc.getExecutionDate() != null)
			frm.setDateString(String.format("%1$te.%1$tm.%1$tY",doc.getExecutionDate()));
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		frm.setBankBIC(bankContext.getBankBIC());
		frm.setBankName(bankContext.getBankName());
		saveInfoMessages(frm, doc);
		//TODO: Временное решение для кредитных заявок: уйти от сохранения заявки
		if (showMessageBox(op.getEntity()) && doc instanceof ExtendedLoanClaim)
		{
			try
			{
				op.save();
			}
			catch (BusinessException ignored)
			{
				op.initialize(new ExistingSource(frm.getId(), new NullDocumentValidator()));
				if (showMessageBox(op.getEntity()))
					op.save();
			}
		}
	}


	protected ActionForward forwardSuccessShow(ActionMapping mapping, ViewEntityOperation operation)
	{
		ViewDocumentOperation op = (ViewDocumentOperation) operation;

		ActionForward forward = mapping.findForward(FORWARD_START + op.getMetadata().getName());
		forward = forward != null ? forward : mapping.findForward(FORWARD_START);
		if (LoanClaimHelper.isUnregisteredClientClaim(op.getMetadata().getForm().getName()))
			forward = mapping.findForward(FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION);
		return forward;
	}

	protected String buildFormHtml(ViewDocumentOperation operation, ActionForm form) throws BusinessException, BusinessLogicException
	{
		return operation.buildFormHtml(getTransformInfo(), getFormInfo(form));
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		return new FormInfo(WebContext.getCurrentRequest().getContextPath(), currentServletContext().getInitParameter("resourcesRealPath"), getSkinUrl(form));
	}

	protected TransformInfo getTransformInfo() throws BusinessException
	{
		//TODO убрать 'endsWith("print")' - этим должна заниматься конкретная реализация
		return new TransformInfo(getMode(), getCurrentMapping().getPath().endsWith("print") ? "print" : "html");
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm)form;
		boolean isLongOffer = frm.getDocument() instanceof AbstractLongOfferDocument &&
				((AbstractLongOfferDocument) frm.getDocument()).isLongOffer();
		return mapping.getPath()+ "/"+frm.getMetadataPath() + (isLongOffer ? "/" + "LongOffer" : "");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm)form;
		ViewDocumentOperation operation = (ViewDocumentOperation) createViewEntityOperation(frm);
		if (showMessageBox(operation.getEntity()))
			operation.save();
		MachineState editState = operation.edit();

		addLogParameters(new BeanLogParemetersReader("Редактируемая сущность", operation.getEntity()));

		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(DefaultDocumentAction.createStateUrl(editState));
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getEntity().getId()));
		return new ActionForward(urlBuilder.toString(), true);
	}

	protected String getMode()
	{
		return "view";
	}

	/**
	 * Сохраняет сообщения в реквест
	 * @param doc документ.
	 */
	protected void saveInfoMessages(ViewDocumentForm form, BusinessDocument doc) throws BusinessException, BusinessLogicException
	{
		try
		{
			ApplicationConfig config = ApplicationConfig.getIt();
			//не проверяем для сотрудника
			form.setExternalAccountPaymentAllowed(true);
			//Отображает сообщение о недоступности внешних платежей со счета, если это актуально.
			Application application = config.getApplicationInfo().getApplication();
			if(application != Application.PhizIA && DocumentHelper.isPaymentDisallowedFromAccount(doc))
			{
				form.setExternalAccountPaymentAllowed(false);
				saveMessage(currentRequest(), DocumentHelper.getDisallowedExternalAccountMessage(doc));
			}

			//Сохраняет сообщение о сроках исполнения платежа/перевода.
			if(application != Application.PhizIA && config.getApplicationInfo().isNotMobileApi())
			{
				String message = ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(doc.getEventMessageKey());
				if(StringHelper.isNotEmpty(message))
					saveMessage(currentRequest(), message);
			}

			//Сообщение для исполненного iTunes
			if(DocumentHelper.isITunesProvider(doc) && doc.getState().equals(new State("EXECUTED")))
			{
				String key = "com.rssl.iccs.iqw.provider.iTunes.executed.message." + (application == Application.PhizIA? "employee": "client");
				saveMessage(currentRequest(), StrutsUtils.getMessage(key, "providerBundle"));
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	protected String getAdditionPaymentInfo(ViewDocumentOperation operation)
	{
		return DocumentHelper.getPaymentFormInfo(operation.getEntity(), CreationType.internet);
	}
}
