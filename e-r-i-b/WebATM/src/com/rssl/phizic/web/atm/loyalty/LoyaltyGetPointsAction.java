package com.rssl.phizic.web.atm.loyalty;

import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.operations.loyaltyProgram.LoyaltyProgramInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Получение информации по накопительным баллам "Спасибо" и заполнение формы LoyaltyGetPointsForm
 *
 * @author Balovtsev
 * @version 30.08.13 19:22
 * @see LoyaltyGetPointsForm
 */
public class LoyaltyGetPointsAction extends OperationalActionBase
{
	private final static String FORWARD_START = "Start";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoyaltyProgramInfoOperation operation = createOperation(LoyaltyProgramInfoOperation.class);
		LoyaltyProgramLink loyaltyProgramLink = operation.getLoyaltyProgramLink();
		if (loyaltyProgramLink != null && loyaltyProgramLink.getState() == LoyaltyProgramState.ACTIVE)
		{
			BigDecimal balance = loyaltyProgramLink.getLoyaltyProgram().getBalance();
			if (balance != null)
			{
				((LoyaltyGetPointsForm) form).setThanks(String.valueOf(balance));
			}
		}

		((LoyaltyGetPointsForm) form).setLoyaltyProgramState(loyaltyProgramLink == null ? LoyaltyProgramState.UNREGISTERED : loyaltyProgramLink.getState());

		return mapping.findForward(FORWARD_START);
	}
}
