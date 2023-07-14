package com.rssl.phizic.web.mapi.locale;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csamapi.operations.locale.ChangeLocaleOperation;
import com.rssl.phizic.web.auth.LookupDispatchAction;

import com.rssl.phizic.web.mapi.auth.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен изменени€ локали
 * @author komarov
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLocaleAction extends LookupDispatchAction
{
	public static final String FORWARD_ERROR = "error";
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("button.save", "save");
		return map;
	}
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(OperationalActionBase.FORWARD_START);
	}

	/**
	 * сохран€ет локаль
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeLocaleForm frm = (ChangeLocaleForm) form;
		ChangeLocaleOperation operation = new ChangeLocaleOperation();

		try
		{
			operation.initialize(frm.getLocaleId());
		}
		catch (FrontLogicException e)
		{
			saveError(request, e.getMessage());
			return mapping.findForward(FORWARD_ERROR);
		}

		operation.execute();
		return mapping.findForward(OperationalActionBase.FORWARD_START);
	}
}
