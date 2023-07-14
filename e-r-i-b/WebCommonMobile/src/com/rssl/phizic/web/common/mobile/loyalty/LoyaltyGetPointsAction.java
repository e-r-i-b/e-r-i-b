package com.rssl.phizic.web.common.mobile.loyalty;

import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.operations.loyaltyProgram.LoyaltyProgramInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 22.08.2012
 * Time: 11:54:59
 */
public class LoyaltyGetPointsAction extends OperationalActionBase
{
	private static final String START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String,String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoyaltyGetPointsForm frm =(LoyaltyGetPointsForm)form;
		LoyaltyProgramInfoOperation operation = createOperation(LoyaltyProgramInfoOperation.class);
		LoyaltyProgramLink loyaltyProgramLink = operation.getLoyaltyProgramLink();
		if (loyaltyProgramLink != null && loyaltyProgramLink.getState() == LoyaltyProgramState.ACTIVE)
		{
			BigDecimal balance = loyaltyProgramLink.getLoyaltyProgram().getBalance();
			if (balance!=null)
				frm.setThanks(String.valueOf(balance));
		}
		return mapping.findForward(START);
	}
}
