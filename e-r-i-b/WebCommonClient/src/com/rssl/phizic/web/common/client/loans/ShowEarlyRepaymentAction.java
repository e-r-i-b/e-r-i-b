package com.rssl.phizic.web.common.client.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.earlyloanrepayment.EarlyLoanRepaymentConfig;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.earlyloanrepayment.CancelEarlyLoanRepaymentOperation;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн просмотра истории заявок на досрочное погашение кредита
 * User: petuhov
 * Date: 19.05.15
 * Time: 16:51 
 */
public class ShowEarlyRepaymentAction extends OperationalActionBase
{
	private static final Log webLog = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB);
	private static final String LOAN_NAME_FIELD = "loanName";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();

		map.put("button.cancelLoanClaim", "cancelLoanClaim");

		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!PermissionUtil.impliesService("EarlyLoanRepaymentClaim"))
		{
			throw new AccessControlException("Нет доступа к операции");
		}
		ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
		Long linkId = frm.getId();

		GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
		operation.initialize(linkId);

		if(!operation.isEarlyLoanRepaymentPossible())
		{
			throw new AccessControlException("Досрочное погашение кредита невозможно");
		}

		LoanLink link = operation.getLoanLink();
		frm.setLoanLink(link);
		frm.setField(LOAN_NAME_FIELD,link.getName());
		frm.setEarlyLoanRepaymentAllowed(operation.isEarlyLoanRepaymentAllowed());
		frm.setEarlyLoanRepaymentPossible(operation.isEarlyLoanRepaymentPossible());
		frm.setEarlyRepayments(operation.getEarlyRepayments());
		frm.setEarlyRepCancelAllowed(ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).isCancelERIBAllowed());

		if(checkAccess(GetLoanAbstractOperation.class))
		{
			GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
			abstractOperation.initialize(link);
			Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>> scheduleAbstract = abstractOperation.getScheduleAbstract(-Constants.MAX_COUNT_OF_TRANSACTIONS, Constants.MAX_COUNT_OF_TRANSACTIONS, true, false);
			Map<LoanLink, ScheduleAbstract> abstractMap = scheduleAbstract.getFirst();
			frm.setScheduleAbstract(abstractMap.get(link));
		}
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward cancelLoanClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).isCancelERIBAllowed())
			throw new AccessControlException("Досрочное погашение невозможно");
		CancelEarlyLoanRepaymentOperation operation = createOperation(CancelEarlyLoanRepaymentOperation.class);
		ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
		operation.initialize(frm.getTerminationRequestId());
		try
		{
			operation.cancelEarlyLoan();
		}
		catch (BusinessException e)
		{
			webLog.error(e.getMessage());
			saveMessage(currentRequest(), "Операция не выполнена. Попробуйте позднее или обратитесь в отделение Банка");
		}
		return start(mapping, form, request, response);
	}
}
