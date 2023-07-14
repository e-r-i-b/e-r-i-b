package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkFormat;
import com.rssl.phizic.common.types.FavouriteTypeLink;
import com.rssl.phizic.operations.favouritelinks.AddFavouriteLinksOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 23.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AddFavouriteLinkAction extends AsyncOperationalActionBase
{
	protected static final String FORWARD_START = "Start";
	private static final String REG_EXP = "^parameter\\(.*\\)$";

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.emptyMap();
	}

	private boolean validateURL(ActionMapping mapping, HttpServletRequest request, String url)
	{
		String contextPath = request.getContextPath();
		if (url == null || !url.startsWith(contextPath))
		{
			return false;
		}
		ModuleConfig moduleConfig = mapping.getModuleConfig();
		String actionUrl = RequestUtils.getActionMappingName(url.substring(contextPath.length()));

		return moduleConfig.findActionConfig(actionUrl) != null;
	}

	private String getParameterName(String parameterKey)
	{
		if (parameterKey.matches(REG_EXP))
			return parameterKey.substring(parameterKey.indexOf("(") + 1, parameterKey.lastIndexOf(")"));
		return null;
	}

	private Map<String, String> getRequestParameters(HttpServletRequest request)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements())
		{
			String parameterKey   = parameterNames.nextElement();
			String parameterValue = request.getParameter(parameterKey);
			String parameterName = getParameterName(parameterKey);
			if (!StringHelper.isEmpty(parameterName))
				parameters.put(parameterName, parameterValue);
		}
		return parameters;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AddFavouriteLinksOperation operation = createOperation(AddFavouriteLinksOperation.class);
		AddFavouriteLinkForm frm = (AddFavouriteLinkForm) form;

		Map<String, String> parameters = getRequestParameters(request);

		String name = frm.getName();
		FavouriteLinkFormat typeFormatLink = FavouriteLinkFormat.valueOf(frm.getTypeFormatLink());
		String pattern = frm.getPattern();
		String url = frm.getUrl();

		if (!validateURL(mapping, request, url))
		{
			frm.setMessage("Невозможно добавить ссылку.");
			return mapping.findForward(FORWARD_START);
		}

		UrlBuilder builder = new UrlBuilder();
		builder.setUrl(url);
		builder.addParameters(parameters);
		frm.setReference(builder.toString());

		String favouriteLink = typeFormatLink.toUrl(parameters);
		favouriteLink = "/" + StrutsUtils.loginContextName() + favouriteLink;

		operation.initializeNew(name,favouriteLink, pattern , FavouriteTypeLink.USER, "");
		try
		{
			operation.save();
			frm.setMessage("Операция успешно добавлена в Личное меню.");
		}
		catch (BusinessException e)
		{
			frm.setMessage(e.getMessage());
		}
		catch (BusinessLogicException e)
		{
			frm.setMessage(e.getMessage());
		}
		return mapping.findForward(FORWARD_START);
	}
}