package com.rssl.phizic.web.configure;

import com.rssl.phizic.operations.FastRefreshConfigOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн для актуализации настроек приложения.
 *
 * @author bogdanov
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class FastRefreshApplicationConfigAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.refreshCSA", "refreshCsa");
		map.put("button.refreshPhizIC", "refreshPhizIC");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		createOperation(FastRefreshConfigOperation.class);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward refreshCsa(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FastRefreshConfigOperation operation = createOperation(FastRefreshConfigOperation.class);
		operation.refreshCsaConfig();
		saveMessage(request, "Настройки ЦСА обновлены");
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward refreshPhizIC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FastRefreshConfigOperation operation = createOperation(FastRefreshConfigOperation.class);
		operation.refreshPhizIcConfig();
		saveMessage(request, "Настройки основного приложения обновлены");
		return mapping.findForward(FORWARD_START);
	}
}
