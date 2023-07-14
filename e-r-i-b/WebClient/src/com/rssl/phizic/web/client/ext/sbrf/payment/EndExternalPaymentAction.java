package com.rssl.phizic.web.client.ext.sbrf.payment;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 19.01.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшен для отображения пользователю страницы об окончании оплаты внешнего платежа (ФНС/OZON)
 */
public class EndExternalPaymentAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	private static final String FORWARD_CONTINUE = "GotoAuthorizedIndex";

	///////////////////////////////////////////////////////////////////////////

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.continueVisit", "continueVisit");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Пользователь выбрал продолжение работы в системе
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward continueVisit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// Поменяем режим работы на стандартный
		AuthenticationContext.getContext().setVisitingMode(UserVisitingMode.BASIC);
		return mapping.findForward(FORWARD_CONTINUE);
	}
}
