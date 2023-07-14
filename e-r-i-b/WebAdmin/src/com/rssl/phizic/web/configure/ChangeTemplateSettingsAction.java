package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bogdanov
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ChangeTemplateSettingsAction extends EditPropertiesActionBase
{
	private static final String FORWARD_RELOAD = "Reload";

	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("ChangeTemplateSettingsOperation");
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.revoke", "cancel");
		return map;
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_RELOAD);
	}
}
