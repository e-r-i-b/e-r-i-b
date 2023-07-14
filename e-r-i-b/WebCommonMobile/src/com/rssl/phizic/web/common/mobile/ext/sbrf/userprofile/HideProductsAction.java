package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.userprofile.HideProductMobileOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Скрытие продуктов в мобильном приложении
 * @author Pankin
 * @ created 26.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class HideProductsAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(1);
		keyMethodMap.put("hide", "start");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HideProductsForm frm = (HideProductsForm) form;
		HideProductMobileOperation operation = createOperation(HideProductMobileOperation.class);
		operation.initialize(frm.getResource());
		operation.hide();
		return mapping.findForward(FORWARD_START);
	}
}
