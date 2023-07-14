package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkFormat;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkManager;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentForm;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.PageContext;

/** ����� ��� ������ � �������� ��������.
 * @author akrenev
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class FavouriteLinksUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String CHARACTER_ENCODING = "utf-8";
	private static final String KEY_VALUE_DELIMITER = "=";
	private static final String ENTRY_DELIMITER = "&";
	private static final FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();

	/**
	 * �������� ������ ���������� ���� ������� �� ���������� ������� ������
	 * @param pageContext �������� ��������
	 * @param favouriteLinkName ��� ������� ������
	 * @param pattern ������ ����� ������� ������
	 * @return ������
	 */
	public static String getFavouriteLinkUrl(PageContext pageContext, String favouriteLinkName, String pattern, String typeFormatLink)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		ActionForm form = (ActionForm) StrutsUtils.currentForm(pageContext);

		addNullSafeParameter(parameters, "name", favouriteLinkName);
		addNullSafeParameter(parameters, "pattern", pattern);
		addNullSafeParameter(parameters, "typeFormatLink", typeFormatLink);

		parameters.putAll(getFormParameters(form));

		return toString(parameters);
	}

	/**
	 * ������ �������� ��������� �� ������ � ������� ��� ��� ���
	 * @param pageContext �������� ��������
	 * @param typeFormat ��� ������
     * @param productId id ���������� ��������
     * @param additionalParams �������������� ��������� ������
	 * @return
	 */
	public static boolean isLinkInFavourite(PageContext pageContext, String typeFormat, String productId, String additionalParams)
	{
		if (typeFormat == null)
			return false;

		FavouriteLinkFormat format = FavouriteLinkFormat.valueOf(typeFormat);
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(productId))
			params.put("id", productId);

		if (additionalParams != null)
		{
			String[] array = additionalParams.trim().split(ENTRY_DELIMITER);
			for (String str : array)
			{
				int index = str.indexOf(KEY_VALUE_DELIMITER);
				if (index > 0 && index < str.length() - 1)
					params.put(str.substring(0, index), str.substring(index + 1));
			}
		}

		String url = format.toUrl(params);
		url = StrutsUtils.calculateActionURL(pageContext, url);

		try
		{
			return favouriteLinkManager.isLinkInFavourite(url, AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� � ���� ������: " + e);
			return false;
		}
	}

	private static Map<String, String> getFormParameters(ActionForm form)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		if (form instanceof EditPaymentForm)
		{
			EditPaymentForm frm = (EditPaymentForm) form;
			addNullSafeParameter(parameters, "parameter(template)", frm.getTemplate());
			addNullSafeParameter(parameters, "parameter(id)",       frm.getId());
			addNullSafeParameter(parameters, "parameter(service)",  frm.getService());
			addNullSafeParameter(parameters, "parameter(form)",     frm.getForm());
			if (frm.getCopying())
				addNullSafeParameter(parameters, "parameter(copy)",     frm.getCopying());
			if (frm.isPersonal())
				addNullSafeParameter(parameters, "parameter(personal)", frm.isPersonal());
		}
		return parameters;
	}

	private static void addNullSafeParameter(Map<String, String> parameters, String parameterName, Object parameterValue)
	{
		if (parameterValue == null)
		{
			return;
		}
		String value = parameterValue.toString();
		if (StringHelper.isEmpty(value))
		{
			return;
		}
		try
		{
			// ��������� - ������� ��� IE, unescapeHtml - ��-�� tiles:insert, ������� ���������� ��������� �������
			parameters.put(parameterName, URLEncoder.encode(StringEscapeUtils.unescapeHtml(value), CHARACTER_ENCODING));
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("������ ����������� ���������� �������: " + e);
		}
	}

	private static String toString(Map<String, String> parameters)
	{
		if (parameters.isEmpty())
			return "";

		String parameterString = MapUtil.format(parameters, KEY_VALUE_DELIMITER, ENTRY_DELIMITER);
		return parameterString.substring(0, parameterString.lastIndexOf(ENTRY_DELIMITER));
	}
}
