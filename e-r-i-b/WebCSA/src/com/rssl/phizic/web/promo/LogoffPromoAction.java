package com.rssl.phizic.web.promo;

import com.rssl.auth.csa.front.business.promo.PromoterSession;
import com.rssl.auth.csa.front.operations.promo.PromoSessionOperation;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Gololobov
 * @ created: 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LogoffPromoAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "start";
	private static final String FORWARD_LOGIN = "login";

	protected Map<String, String> getKeyMethodMap()
	{
		Map <String, String> keyMap= new HashMap<String,String>();
	    keyMap.put("button.logoffPromo", "logoffPromo");
		return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PromoSessionOperation operation = new PromoSessionOperation();
		PromoterSession promoterSession = operation.getPromoterOpenedSessionBySessionIdFromCookie(request);
		if (promoterSession == null)
		{
			return mapping.findForward(FORWARD_LOGIN);
		}
		LogoffPromoForm frm = (LogoffPromoForm) form;
		frm.setPromoSession(promoterSession);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Закрытие смены промоутера
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward logoffPromo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PromoSessionOperation operation = new PromoSessionOperation();
		PromoterSession promoterSession = operation.getPromoterOpenedSessionBySessionIdFromCookie(request);
		//Закрытие смены текущей датой
		operation.closePromoterSession(promoterSession);
		//Удаление куки с ИД промоутера
		operation.deletePromoterIdCookie(request, response);
		return mapping.findForward(FORWARD_LOGIN);
	}
}
