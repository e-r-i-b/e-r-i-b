package com.rssl.phizic.web.common.mobile.mail;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.ext.sbrf.mail.EditClientMailOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 10.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMailThemesMobileAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		ListMailThemesMobileForm frm = (ListMailThemesMobileForm) form;
		frm.setMailThemes(operation.getAllThemes());
		return mapping.findForward(FORWARD_START);
	}
}
