package com.rssl.phizic.test.web.common;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class TestAbstractAction  extends LookupDispatchAction
{
	protected static final String FORWARD_START = "Start";

	///////////////////////////////////////////////////////////////////////////

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessLogicException, BusinessException
	{
		TestAbstractForm form = (TestAbstractForm) frm;
		form.setSubmit(true);

		try
		{
			SendAbstractRequestOperation operation = getSendRequestOperation();
			updateOperation(operation, form);

			operation.send();

			updateForm(form, operation);
		}
		catch (SystemException e)
		{
			form.setResult("Error \n" + e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}

	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());

		MultiMap params = new MultiValueMap();
		for(Map.Entry<String, List<String>> entry : MapUtil.parseMultiMap(request.getParameter("params"), "=", "&").entrySet())
		{
			for (String value : entry.getValue())
			{
				if(!StringHelper.isEmpty(value))
					params.put(entry.getKey(), value);
			}
		}

		operation.setParams(params);
	}

	protected void updateForm(TestAbstractForm form, SendAbstractRequestOperation operation)
	{
		form.setStatus(operation.getStatus());
		form.setContentType(operation.getContentType());
		form.setCookie(operation.getCookie());
		form.setResult(operation.getResult());
	}

	public abstract SendAbstractRequestOperation getSendRequestOperation();

}
