package com.rssl.phizic.web.common;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Пустой экшн, который просто возвращает форвард с именем "show". Создан чтобы не плодить
 * кучу экшенов с различными названиями и таким функцционалом.
 * @author tisov
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */

public class EmptyAction extends LookupDispatchAction
{
	public static final String SHOW_FORWARD_NAME = "show";

	@Override protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(SHOW_FORWARD_NAME);
	}
}
