package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.phizic.operations.userprofile.HideTemplatesMobileOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Скрытие шаблонов из мобильного приложения
 * @author Pankin
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class HideTemplatesAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HideTemplatesForm frm = (HideTemplatesForm) form;
		HideTemplatesMobileOperation operation = createOperation(HideTemplatesMobileOperation.class);
		operation.initialize(frm.getTemplate());
		operation.hide();
		return mapping.findForward(FORWARD_START);
	}
}
