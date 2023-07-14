package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.loans.loan.LoanSettingsEditOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nady
 * @ created 08.06.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн редактирования настроек для кредитов
 */
public class LoanSettingsEditAction extends OperationalActionBase
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
		LoanSettingsEditForm frm = (LoanSettingsEditForm) form;
		LoanSettingsEditOperation operation = createOperation(LoanSettingsEditOperation.class);
		operation.initialize();

		updateForm(frm, operation);

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(LoanSettingsEditForm frm, LoanSettingsEditOperation operation) throws BusinessException
	{
		frm.setGetCreditAtLogon(operation.isGetCreditAtLogon());
	}

	public final ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanSettingsEditForm frm = (LoanSettingsEditForm) form;
		LoanSettingsEditOperation operation = createOperation(LoanSettingsEditOperation.class);
		operation.save(frm.isGetCreditAtLogon());
		return start(mapping, form, request, response);
	}
}
