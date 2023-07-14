package com.rssl.phizic.web.common.mobile.dictionaries;

import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Vagin
 * @ created: 25.07.2013
 * @ $Author
 * @ $Revision
 * Экшен удаления записи из справочника доверенных получателей.
 */
public class RemoveRecentlyFilledFieldDataAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}
}
