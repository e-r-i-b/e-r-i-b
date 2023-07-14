package com.rssl.phizic.web.payments;

import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.operations.loanclaim.LoanClaimPostponeOperation;
import com.rssl.phizic.operations.loanclaim.LoanClaimRefuseClientOperation;
import com.rssl.phizic.operations.loanclaim.LoanClaimVisitOfficeOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.util.EmployeeInfoUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен работы с кредитной заявкой в сессии сотрудника
 *
 * @author Gulov
 * @ created 06.04.15
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanClaimAction extends EditPaymentAction
{
	protected static final String FORWARD_FIND_CLAIM_FORM = "ShowFindForm";
	private static final String LOAN_CLAIM_EMPLOYEE_SERVICE_NAME = "LoanClaimEmployeeService";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.changeConditions", "changeConditions");
		map.put("button.postpone", "postpone");
		map.put("button.refuseClient", "refuseClient");
		map.put("button.sendToVSP", "sendToVSP");
		return map;
	}

	@Override
	protected ActionForward createNextStageDocumentForward(EditDocumentOperation operation)
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(operation.getDocumentSate().getEmployeeForm());
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getDocument().getId()));
		return new ActionForward(StringUtils.replace(urlBuilder.toString(), "/private", "/claim"), true);
	}

	/**
	 * Для расширенной заявки на кредит возвращает обратно на форму выбора параметров кредита
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateEmployeePaymentForm frm = (CreateEmployeePaymentForm) form;
		EditDocumentOperation operation = getOperation(request);
		operation.saveAsInitial();
		return forwardShow(operation, frm);
	}

	/**
	 * Отложить заполнение заявки
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward postpone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimPostponeOperation postponeOperation = createOperation(LoanClaimPostponeOperation.class, LOAN_CLAIM_EMPLOYEE_SERVICE_NAME);
		EditDocumentOperation editOperation = getOperation(request);
		//Перевод документа в состояние "В работе TM"
		editOperation.doWaitTM();
		//Отправка сообщения в CRM о необходимости обзвона клиента
		EditLoanClaimForm frm = (EditLoanClaimForm) form;
		ExtendedLoanClaim claim =  (ExtendedLoanClaim) editOperation.getDocument();
		if (StringHelper.isEmpty(claim.getFullMobileNumber()))
		{
			claim.setMobileCountry(((EditLoanClaimForm) form).getMobileCountry());
			claim.setMobileTelecom(frm.getMobileTelecom());
			claim.setMobileNumber(frm.getMobileNumber());
		}
		postponeOperation.execute(claim, EmployeeInfoUtil.getEmployeeInfo());
		//Редирект на форму поиска заявки
		return mapping.findForward(FORWARD_FIND_CLAIM_FORM);
	}

	/**
	 * Отказ клиента от заполнения заявки
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward refuseClient(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimRefuseClientOperation operation = createOperation(LoanClaimRefuseClientOperation.class, LOAN_CLAIM_EMPLOYEE_SERVICE_NAME);
		EditDocumentOperation editOperation = getOperation(request);
		ExtendedLoanClaim claim = (ExtendedLoanClaim) editOperation.getDocument();
		//Перевод заявки в состояние "Отказан"
		operation.doRefuseClient(claim);
		//Редирект на форму поиска заявки
		return mapping.findForward(FORWARD_FIND_CLAIM_FORM);
	}

	/**
	 * Отправка клиента в ВСП
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendToVSP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDocumentOperation editOperation = getOperation(request);

		CreatePaymentForm frm = (CreatePaymentForm) form;
		ValidationStrategy strategy = getValidationStrategy(editOperation);
		Map<String, Object> formData = checkFormData(frm, editOperation, strategy);
		if (formData == null)
		{
			ActionForward forward = forwardShow(editOperation, frm);
			if (LoanClaimHelper.isUnregisteredClientClaim(frm.getForm()))
				forward = mapping.findForward(FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION);
			return forward;
		}
		save(mapping, form, request, response);

		ExtendedLoanClaim claim = (ExtendedLoanClaim) editOperation.getDocument();
		LoanClaimVisitOfficeOperation operation = createOperation(LoanClaimVisitOfficeOperation.class, LOAN_CLAIM_EMPLOYEE_SERVICE_NAME);
		//Установка подразделения для получения кредита
		claim.setClaimDrawDepartmentETSMCode(
				request.getParameter(ExtendedLoanClaim.ATTRIBUTE_RESEIVING_REGION),
				request.getParameter(ExtendedLoanClaim.ATTRIBUTE_RESEIVING_OFFICE),
				request.getParameter(ExtendedLoanClaim.ATTRIBUTE_RESEIVING_BRANCH));
		//Перевод заявки в состояние "Визит в отделение"
		operation.doVisitOffice(claim);
		//Редирект на форму поиска заявки
		return mapping.findForward(FORWARD_FIND_CLAIM_FORM);
	}
}
