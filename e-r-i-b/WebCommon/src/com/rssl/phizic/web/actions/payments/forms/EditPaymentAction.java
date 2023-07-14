package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.business.resources.external.ActiveDebitRurAccountFilter;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.loanclaim.dictionary.LoanIssueMethod;
import com.rssl.phizic.operations.ext.sbrf.payment.GetPaymentServiceInfoOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.PaymentContext;
import com.rssl.phizic.operations.payment.PaymentParameters;
import com.rssl.phizic.operations.payment.PreparePaymentOperation;
import com.rssl.phizic.web.help.HelpIdGenerator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Created by IntelliJ IDEA. User: Roshka Date: 08.11.2005 Time: 14:00:23 */

/**
 * Экшен для редактирования платежа.
 */
public class EditPaymentAction extends EditPaymentActionBase
{
	private static final LoanClaimDictionaryService loanClaimDictionaryService = new LoanClaimDictionaryService();
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.next", "next");
		map.put("button.search", "search");
		map.put("button.back.longOffer", "back");
		map.put("button.nextLongOffer", "makeLongOffer");
		map.put("button.changeConditions", "changeConditions");
		map.put("afterAccountOpening", "afterAccountOpening");
		return map;
	}

	/**
	 * Пользователь нажал "Вперёд" на одном из подготовильных шагов (1-3)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return startEditPayment(mapping, form, request);
	}

	/**
	 * Пользователь нажал "Назад" на одном из шагов 1-4
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prev(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDocumentOperation operation = getOperation(request);
		operation.saveAsInitial();

		return super.prev(mapping, form, request, response);
	}

	/**
	 * Пользователь "ходит" по страницам списка поставщика
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPaymentForm frm = (EditPaymentForm) form;

		PreparePaymentOperation operation = createOperation("PreparePaymentOperation");
		operation.initialize(getPaymentParams(frm));
		PaymentContext paymentContext = operation.getPaymentContext();

		return showPreparePaymentPage(mapping, frm, paymentContext);
	}

	private PaymentParameters getPaymentParams(EditPaymentForm frm) throws BusinessException
	{
		PaymentParameters params = new PaymentParameters();
		params.setPaymentId(frm.getId());
		params.setPaymentTemplateId(frm.getTemplate());
		params.setPaymentFormName(frm.getForm());
		params.setProviderId(frm.getRecipient());
		params.setPersonalPayment(frm.isPersonal());
		params.setCopy(frm.getCopying() != null && frm.getCopying());
		params.setFromResource(frm.getFromResource());
		params.setPaymentFields(frm.getFields());
		return params;
	}

	private ActionForward showPreparePaymentPage(ActionMapping mapping, EditPaymentForm form, PaymentContext context) throws BusinessException, BusinessLogicException
	{
		if (form.getBack())
			form.setFromResource(context.getFromResource());

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		GetPaymentServiceInfoOperation serviceInfoOperation = createOperation("GetPaymentServiceInfoOperation", "RurPayJurSB");
		serviceInfoOperation.initialize(context.getServiceId());

		ProviderFormEditor formEditor = new ProviderFormEditor(serviceInfoOperation);
		formEditor.setProviderId(context.getProviderId());
		formEditor.setKeyFields(context.getFields());
		formEditor.setRegionFilter(personData.getCurrentRegion());
		return formEditor.start(mapping, form);
	}

	/**
	 * Возвращает обратно на форму создания платежа
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPaymentForm frm = (EditPaymentForm) form;
		EditDocumentOperation operation = getOperation(request);
		BusinessDocument document = operation.getDocument();

		if(document instanceof AbstractLongOfferDocument)
		{
			AbstractLongOfferDocument longOffer = (AbstractLongOfferDocument)document;
			longOffer.setLongOffer(false);
		}
		return forwardShow(operation, frm);
	}

	/**
	 * Для расщиренной заявки на кредит возвращает обратно на форму выбора параметров кредита
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPaymentForm frm = (EditPaymentForm) form;
		EditDocumentOperation operation = getOperation(request);
		BusinessDocument document = operation.getDocument();

		if(document instanceof ExtendedLoanClaim)
		{
			operation.saveAsInitial();
		}
		return createEditForward(operation);
	}

	/**
	 * Для расширенной заявке на кредит: возвращение на форму оформления заявки после открытия нового сбер.счета
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward afterAccountOpening(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDocumentOperation operation = getOperation(request);
		saveOperation(request, operation);
		CreatePaymentForm frm = (CreatePaymentForm) form;

		BusinessDocument document = operation.getDocument();
		if (document instanceof ExtendedLoanClaim)
		{
			try
			{
				PersonContext.getPersonDataProvider().getPersonData().getAccounts(new ActiveDebitRurAccountFilter());
				String accountOpeningClaimId = request.getParameter("confirmDocumentId");
				operation.saveNewAccountInLoanClaim(Long.valueOf(accountOpeningClaimId));
			}
			catch (BusinessException e)
			{
				log.error(e);
				saveError(request, "Операция не выполнена. Попробуйте позднее или выберите другой счет для зачисления");
				return forwardShow(operation, frm);
			}
		}
		return createAfterAccountOpeningForward(operation, frm);
	}

	public ActionForward createAfterAccountOpeningForward(EditDocumentOperation operation, CreatePaymentForm frm) throws BusinessException, BusinessLogicException, DocumentException
	{
		FieldValuesSource valuesSource = createAfterAccountOpeningValuesSource(operation);
		updateForm(frm, operation, valuesSource);

		ActionForward forward = getCurrentMapping().findForward(FORWARD_SHOW_FORM + operation.getMetadata().getName());
		return forward != null ? forward : getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	private FieldValuesSource createAfterAccountOpeningValuesSource(EditDocumentOperation operation) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();
		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;
		Map<String, Object> map = new HashMap<String, Object>();
		List<LoanIssueMethod> loanIssueMethods = loanClaimDictionaryService.getLoanIssueMethodAvailable();
		String code= "";
		for (LoanIssueMethod loanIssueMethod : loanIssueMethods)
			if (loanIssueMethod.isNewProductForLoan() && loanIssueMethod.getProductForLoan().equals(LoanIssueMethod.LoanProductType.CURRENT_ACCOUNT))
				code = loanIssueMethod.getCode();
		map.put(ExtendedLoanClaim.ATTRIBUTE_LOAN_ISSUE_METHOD_CODE, code);
		map.put("receivingResourceId", extendedLoanClaim.getReceivingResourceId());
		map.put(ExtendedLoanClaim.OPENED_ACCOUNTS_COUNT, extendedLoanClaim.getOpenedAccountsCount());
		return new CompositeFieldValuesSource(new MapValuesSource(map), getRequestFieldValuesSource());
	}

	@Override
	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		String helpId = super.getHelpId(mapping, form);
		BusinessDocument document = frm.getDocument();
		if (document instanceof RurPayment)
			helpId += HelpIdGenerator.getHelpIdSuffix((RurPayment) document);

		if ((document instanceof JurPayment) && (frm instanceof EditServicePaymentForm))
		{
			EditServicePaymentForm espf = (EditServicePaymentForm) frm;
			Long recipient = espf.getRecipient();
			if (recipient != null && recipient.equals(espf.getSocialNetProviderId()))
			{
				helpId += HelpIdGenerator.SOCIAL_NET_URL_SUFFIX;
			}
		}

		return helpId;
	}
}
