package com.rssl.phizic.web.common;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 18.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class FilterAction  extends AsyncOperationalActionBase
{

	private static String FORWARD_START = "Start";
	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FilterForm frm = (FilterForm) form;
		String url = frm.getUrl();
		ModuleConfig moduleConfig = mapping.getModuleConfig();
		ActionConfig config = moduleConfig.findActionConfig(url);
		String className = config.getType();
		Class classManager = ClassHelper.loadClass(className);

		String formName = moduleConfig.findFormBeanConfig(config.getName()).getType();
		ListFormBase newForm = (ListFormBase) ClassHelper.loadClass(formName).newInstance();
		ListActionBase action = (ListActionBase) classManager.newInstance();

		Map<String, Object> params = action.getDefaultParameters(newForm);
		frm.setFilters(params);
		return mapping.findForward(FORWARD_START);
	}
}
