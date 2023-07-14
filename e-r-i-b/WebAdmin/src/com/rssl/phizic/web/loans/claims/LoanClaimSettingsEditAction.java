package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.loanclaim.LoanClaimSettingsEditOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен просмотра и редактировани€ настроек за€вки на кредит
 * @author Rtischeva
 * @ created 06.02.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimSettingsEditAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.save", "save");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimSettingsEditForm frm = (LoanClaimSettingsEditForm) form;
		LoanClaimSettingsEditOperation operation = createOperation(LoanClaimSettingsEditOperation.class);
		operation.initialize();

		updateForm(frm, operation);

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(LoanClaimSettingsEditForm frm, LoanClaimSettingsEditOperation operation) throws BusinessException
	{
		frm.setCallAvailable(operation.isCallAvailable());
		frm.setLockOperationDebit(operation.isLockOperationDebit());
		frm.setMaxEURSumUnlockRestriction(operation.getMaxEURSumUnlockRestriction());
		frm.setMaxRUBSumUnlockRestriction(operation.getMaxRUBSumUnlockRestriction());
		frm.setMaxUSDSumUnlockRestriction(operation.getMaxUSDSumUnlockRestriction());
		frm.setMinEURSumDebitOperationERKC(operation.getMinEURSumDebitOperationERKC());
		frm.setMinRUBSumDebitOperationERKC(operation.getMinRUBSumDebitOperationERKC());
		frm.setMinUSDSumDebitOperationERKC(operation.getMinUSDSumDebitOperationERKC());
		frm.setNeedConfirmDebitOperationERKC(operation.isNeedConfirmDebitOperationERKC());
		frm.setPeriodLockedOperationDebit(operation.getPeriodLockedOperationDebit());
	}

	public final ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimSettingsEditForm frm = (LoanClaimSettingsEditForm) form;
		LoanClaimSettingsEditOperation operation = createOperation(LoanClaimSettingsEditOperation.class);
		operation.initialize();
		operation.setCallAvailable(frm.isCallAvailable());
		operation.setLockOperationDebit(frm.isLockOperationDebit());
		operation.setMaxEURSumUnlockRestriction(frm.getMaxEURSumUnlockRestriction());
		operation.setMaxRUBSumUnlockRestriction(frm.getMaxRUBSumUnlockRestriction());
		operation.setMaxUSDSumUnlockRestriction(frm.getMaxUSDSumUnlockRestriction());
		if (frm.isNeedConfirmDebitOperationERKC())
		{
			operation.setMinRUBSumDebitOperationERKC(frm.getMinRUBSumDebitOperationERKC());
			operation.setMinUSDSumDebitOperationERKC(frm.getMinUSDSumDebitOperationERKC());
			operation.setMinEURSumDebitOperationERKC(frm.getMinEURSumDebitOperationERKC());
		}
		operation.setNeedConfirmDebitOperationERKC(frm.isNeedConfirmDebitOperationERKC());
		operation.setPeriodLockedOperationDebit(frm.getPeriodLockedOperationDebit());
		operation.save();
		return start(mapping, form, request, response);
	}
}
