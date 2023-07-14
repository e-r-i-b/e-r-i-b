package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн формы ввода уточн€ющих данных клиента
 */
public class GuestPersonDataAction extends LoginStageActionSupport
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> methods = new HashMap<String, String>();
		methods.put("next", "next");
		return methods;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GuestPersonDataForm frm = (GuestPersonDataForm) form;
		AuthenticationContext context = getAuthenticationContext();
		frm.setPhone(((GuestLogin) context.getLogin()).getAuthPhone());
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GuestPersonDataForm frm = (GuestPersonDataForm) form;
		if ("next".equals(frm.getOperation()))
		{
			MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
			FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, GuestPersonDataForm.FORM);
			try
			{
				if (formProcessor.process())
				{
					Map<String, String> map = AuthenticationContext.getContext().getAuthenticationParameters();
					map.put("surName", frm.getField("surName"));
					map.put("firstName", frm.getField("firstName"));
					map.put("patrName", frm.getField("patrName"));
					map.put("birthday", frm.getField("birthday"));
					map.put("document", frm.getField("document"));
					completeStage();
					return mapping.findForward(FORWARD_SHOW_FORM);
				}
				else
				{
					saveErrors(request, formProcessor.getErrors());
					return start(mapping, form, request, response);
				}
			}
			catch (BusinessLogicException e)
			{
				saveError(request,e.getMessage());
				return start(mapping, form, request, response);
			}
		}
		return super.executeLogin(mapping, form, request, response);
	}
}
