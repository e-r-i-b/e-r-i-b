package com.rssl.phizic.test.web.webAPI;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractAction;
import com.rssl.phizic.test.wsgateclient.webAPI.wsclient.RequestConstants;
import com.rssl.phizic.test.wsgateclient.webAPI.wsclient.WebAPIRequestHelper;
import com.rssl.phizic.test.wsgateclient.webAPI.wsclient.exceptions.WebAPIException;
import com.rssl.phizic.test.wsgateclient.webAPI.wsclient.exceptions.WebAPILogicException;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн тестилки дл€ WebAPI
 * @author Jatsky
 * @ created 11.11.13
 * @ $Author$
 * @ $Revision$
 */

public class TestWebAPIAction extends TestAbstractAction
{
	public SendAbstractRequestOperation getSendRequestOperation()
	{
		return null;
	}

	public ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessLogicException, BusinessException
	{

		TestWebAPIForm form = (TestWebAPIForm) frm;
		MultiMap params = new MultiValueMap();
		for (Map.Entry<String, List<String>> entry : MapUtil.parseMultiMap(request.getParameter("params"), "=", "&").entrySet())
		{
			for (String value : entry.getValue())
			{
				if (!StringHelper.isEmpty(value))
					params.put(entry.getKey(), value);
			}
		}
		List<String> ipList = ((ArrayList<String>) params.get("ip"));

		String ip = (ipList == null) ? "" : ipList.get(0);

		String url = form.getUrl();

		if (form.getAddress().equals(RequestConstants.LOGIN_OPERATION))
		{
			List<String> tokenList = (ArrayList<String>) params.get("token");

			String token = (tokenList == null) ? "" : tokenList.get(0);

			ArrayList<String> isAuthenticationCompletedList = (ArrayList<String>) params.get("isAuthenticationCompleted");
			String isAuthenticationCompleted = (isAuthenticationCompletedList == null) ? "" : isAuthenticationCompletedList.get(0);
			try
			{
				String result = WebAPIRequestHelper.sendLoginRq(url, token, ip, isAuthenticationCompleted);
				((TestWebAPIForm) frm).setSubmit(true);
				((TestWebAPIForm) frm).setResult(result);
			}
			catch (WebAPIException e)
			{
				e.printStackTrace();
			}
			catch (WebAPILogicException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				String result = "";
				if (form.getAddress().equals(RequestConstants.LOGOFF_OPERATION))
				{
					result = WebAPIRequestHelper.sendLogoffRq(url, ip);
				}
				else if (form.getAddress().equals(RequestConstants.PRODUCT_LIST_OPERATION))
				{
					List<String> productTypes = (ArrayList<String>) params.get("productType");
					result = WebAPIRequestHelper.sendGetProductList(url, productTypes, ip);
				}
				else if (form.getAddress().equals(RequestConstants.CARD_DETAIL_OPERATION) || form.getAddress().equals(RequestConstants.ACCOUNT_DETAIL_OPERATION))
				{
					List<String> idList = ((ArrayList<String>) params.get("id"));
					String id = (idList == null) ? "" : idList.get(0);
					result = WebAPIRequestHelper.sendGetProductDetail(url, ip, id, form.getAddress());
				}
				else if (form.getAddress().equals(RequestConstants.CARD_ABSTRACT_OPERATION) || form.getAddress().equals(RequestConstants.ACCOUNT_ABSTRACT_OPERATION))
				{
					String id = ((ArrayList<String>) params.get("id") == null) ? "" : ((ArrayList<String>) params.get("id")).get(0);
					String from = (((ArrayList<String>) params.get("from")) == null) ? "" : (((ArrayList<String>) params.get("from")).get(0));
					String to = (((ArrayList<String>) params.get("to")) == null) ? "" : (((ArrayList<String>) params.get("to")).get(0));
					String count = (((ArrayList<String>) params.get("count")) == null) ? "" : (((ArrayList<String>) params.get("count")).get(0));
					result = WebAPIRequestHelper.sendGetProductAbstract(url, ip, id, from, to, count, form.getAddress());
				}
				else if (form.getAddress().equals(RequestConstants.CHECK_SESSION_OPERATION))
				{
					result = WebAPIRequestHelper.sendSimpleRequest(url, ip, RequestConstants.CHECK_SESSION_OPERATION);
				}
				else if (form.getAddress().equals(RequestConstants.MENU_OPERATION))
				{
					ArrayList list = (ArrayList) params.get("exclude");
					String[] exclude = null;

					if (list != null)
					{
						String source = (String) list.get(0);

						if (StringHelper.isNotEmpty(source))
						{
							exclude = source.split(";");
						}
					}

					result = WebAPIRequestHelper.sendMenuOperationRequest(url, ip, exclude);
				}
				else if (form.getAddress().equals(RequestConstants.FAST_PAYMENT_PANEL_OPERATION))
				{
					result = WebAPIRequestHelper.sendSimpleRequest(url, ip, RequestConstants.FAST_PAYMENT_PANEL_OPERATION);
				}
				else if (form.getAddress().equals(RequestConstants.AUTOPAYMENTS_OPERATION))
				{
					result = WebAPIRequestHelper.sendSimpleRequest(url, ip, RequestConstants.AUTOPAYMENTS_OPERATION);
				}
				else if (form.getAddress().equals(RequestConstants.ALF_GET_GRAPHIC_DATA))
				{
					result = WebAPIRequestHelper.sendSimpleParameterizedRequest(url, ip, extractParameters(params, null), RequestConstants.ALF_GET_GRAPHIC_DATA);
				}
				else if (form.getAddress().equals(RequestConstants.ALF_EDIT_OPERATION))
				{
					result = WebAPIRequestHelper.sendAlfEditRequest(url, ip, extractParameters(params, null));
				}
				else if (form.getAddress().equals(RequestConstants.ADD_ALF_OPERATION))
				{
					result = WebAPIRequestHelper.sendAddAlfOperationRequest(url, ip, extractParameters(params, null));
				}
				else if (form.getAddress().equals(RequestConstants.ALF_HISTORY_OPERATION))
				{
					Map<String, Object> parameters = extractParameters(params, Arrays.asList("selectedCardId"));

					List selectedCardId = (ArrayList) params.get("selectedCardId");
					if (selectedCardId != null)
					{
						String[] source = ((String) selectedCardId.get(0)).split(";");
						if (ArrayUtils.isNotEmpty(source))
						{
							parameters.put("selectedCardId", Arrays.asList(source));
						}
					}

					result = WebAPIRequestHelper.sendSimpleParameterizedRequest(url, ip, parameters, RequestConstants.ALF_HISTORY_OPERATION);
				}
				else if (form.getAddress().equals(RequestConstants.ALF_SERVICESTATUS_STATUS))
				{
					result = WebAPIRequestHelper.sendSimpleRequest(url, ip, RequestConstants.ALF_SERVICESTATUS_STATUS);
				}

				else if (form.getAddress().equals(RequestConstants.ALF_CONNECT))
				{
					result = WebAPIRequestHelper.sendSimpleRequest(url, ip, RequestConstants.ALF_CONNECT);
				}

				else if (form.getAddress().equals(RequestConstants.ALF_CATEGORY_LIST))
				{
					String incomeType = ((ArrayList<String>) params.get("incomeType") == null) ? "" : ((ArrayList<String>) params.get("incomeType")).get(0);
					String paginationSize = (((ArrayList<String>) params.get("paginationSize")) == null) ? "" : (((ArrayList<String>) params.get("paginationSize")).get(0));
					String paginationOffset = (((ArrayList<String>) params.get("paginationOffset")) == null) ? "" : (((ArrayList<String>) params.get("paginationOffset")).get(0));

					result = WebAPIRequestHelper.sendALFCategoryListRequest(url, ip, incomeType, paginationSize, paginationOffset);
				}

				else if (form.getAddress().equals(RequestConstants.ALF_CATEGORY_EDIT))
				{
					String incomeType = ((ArrayList<String>) params.get("id") == null) ? "" : ((ArrayList<String>) params.get("id")).get(0);
					String paginationSize = (((ArrayList<String>) params.get("name")) == null) ? "" : (((ArrayList<String>) params.get("name")).get(0));
					String paginationOffset = (((ArrayList<String>) params.get("operationType")) == null) ? "" : (((ArrayList<String>) params.get("operationType")).get(0));

					result = WebAPIRequestHelper.sendALFCategoryEditRequest(url, ip, incomeType, paginationSize, paginationOffset);
				}

				else if (form.getAddress().equals(RequestConstants.ALF_CATEGORY_FILTER))
				{
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("from", ((ArrayList) params.get("from") == null) ? "" : ((ArrayList) params.get("from")).get(0));
					parameters.put("to", ((ArrayList) params.get("to") == null) ? "" : ((ArrayList) params.get("to")).get(0));
					parameters.put("showCash", ((ArrayList) params.get("showCash") == null) ? "" : ((ArrayList) params.get("showCash")).get(0));
					parameters.put("showCashPayments", ((ArrayList) params.get("showCashPayments") == null) ? "" : ((ArrayList) params.get("showCashPayments")).get(0));
					parameters.put("showTransfers", ((ArrayList) params.get("showTransfers") == null) ? "" : ((ArrayList) params.get("showTransfers")).get(0));
					parameters.put("showCents", ((ArrayList) params.get("showCents") == null) ? "" : ((ArrayList) params.get("showCents")).get(0));

					List selectedId = (ArrayList) params.get("selectedId");
					if (selectedId != null)
					{
						String[] source = ((String) selectedId.get(0)).split(";");
						if (ArrayUtils.isNotEmpty(source))
						{
							parameters.put("selectedId", Arrays.asList(source));
						}
					}

					result = WebAPIRequestHelper.sendSimpleParameterizedRequest(url, ip, parameters, RequestConstants.ALF_CATEGORY_FILTER);
				}

				else if (form.getAddress().equals(RequestConstants.ALF_CATEGORY_BUDGETSET))
				{
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("id", ((ArrayList) params.get("id") == null) ? "" : ((ArrayList) params.get("id")).get(0));
					parameters.put("budget", ((ArrayList) params.get("budget") == null) ? "" : ((ArrayList) params.get("budget")).get(0));

					result = WebAPIRequestHelper.sendSimpleParameterizedRequest(url, ip, parameters, RequestConstants.ALF_CATEGORY_BUDGETSET);
				}
				else if (form.getAddress().equals(RequestConstants.ALF_CATEGORY_BUDGET_REMOVE))
				{
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("id", (params.get("id") == null) ? "" : ((ArrayList) params.get("id")).get(0));
					result = WebAPIRequestHelper.sendSimpleParameterizedRequest(url, ip, parameters, RequestConstants.ALF_CATEGORY_BUDGET_REMOVE);
				}
				else if (form.getAddress().equals(RequestConstants.ALF_FINANCECONTENT))
				{
					result = WebAPIRequestHelper.sendSimpleRequest(url, ip, RequestConstants.ALF_FINANCECONTENT);
				}
				else if (form.getAddress().equals(RequestConstants.MOBILE_BANK_HIDE))
				{
					result = WebAPIRequestHelper.sendSimpleRequest(url, ip, RequestConstants.MOBILE_BANK_HIDE);
				}
				else if (form.getAddress().equals(RequestConstants.ALF_EDIT_GROUP_OPERATION))
				{
					result = WebAPIRequestHelper.sendAlfEditGroupRequest(url, ip, extractParameters(params, null));
				}
				((TestWebAPIForm) frm).setSubmit(true);
				((TestWebAPIForm) frm).setResult(result);
			}
			catch (WebAPIException e)
			{
				e.printStackTrace();
			}
			catch (WebAPILogicException e)
			{
				e.printStackTrace();
			}
		}

		Cookie cookie = new Cookie(ConfigFactory.getConfig(WebAPIConfig.class).getCookieName(), WebAPITestContext.getSession());
		cookie.setPath("/");
		response.addCookie(cookie);

		return mapping.findForward(FORWARD_START);
	}

	private Map<String, Object> extractParameters(MultiMap params, List exclude)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (Object key : params.keySet())
		{
			if (exclude != null && exclude.contains(key))
			{
				continue;
			}

			parameters.put((String) key, ((ArrayList) params.get(key)).get(0));
		}

		return parameters;
	}
}
