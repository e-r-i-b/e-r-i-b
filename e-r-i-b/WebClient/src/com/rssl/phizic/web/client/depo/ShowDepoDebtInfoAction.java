package com.rssl.phizic.web.client.depo;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.operations.depo.GetDepoDebtInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoDebtInfoAction extends OperationalActionBase
{
	public static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
        map.put("button.filter", "filter");
        return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowDepoDebtInfoForm frm = (ShowDepoDebtInfoForm) form;
	    Long linkId = frm.getId();

	    GetDepoDebtInfoOperation operation = createOperation(GetDepoDebtInfoOperation.class);
	    operation.initialize(linkId);

	    DepoAccountLink link = operation.getDepoAccountLink();
		if(frm.isRefresh())
			link.refresh();

		frm.setDepoAccountLink(link);
		try
		{
			frm.setDepoDebtInfo(operation.getDepoDebtInfo());

		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при получении задолженности по счету депо №" + link.getAccountNumber(), e);
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveMessages(request, msgs);
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping,form, request,  response);
	}
}
