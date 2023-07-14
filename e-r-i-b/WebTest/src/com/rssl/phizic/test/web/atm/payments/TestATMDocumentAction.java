package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.test.mobile.atm.SendATMRequestOperation;
import com.rssl.phizic.web.WebContext;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.Action;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * @author serkin
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */

class TestATMDocumentAction extends Action
{
	protected int send(TestATMDocumentForm form)
	{
		try
		{
			form.setSubmit(true);

			SendATMRequestOperation operation = new SendATMRequestOperation();
			operation.setParseResponse(true);
			updateOperation(operation, form);

			operation.send();

			updateForm(form, operation);

			return operation.getResponseStatusCode();
		}
		catch (BusinessException e)
		{
			form.setResult("Error \n" + e.getMessage());
			return -1;
		}
	}

	protected void updateOperation(SendATMRequestOperation operation, TestATMDocumentForm form)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());

		String[] ignoreParamNames = { "url", "address", "cookie", "proxyUrl", "proxyPort", "documentStatus" };

		MultiMap params = new MultiValueMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements())
		{
			String paramName = (String) paramNames.nextElement();
			if (ArrayUtils.contains(ignoreParamNames, paramName))
				continue;
			for (String paramValue : request.getParameterValues(paramName))
				params.put(paramName, paramValue);
		}
		operation.setParams(params);
	}

	protected void updateForm(TestATMDocumentForm form, SendATMRequestOperation operation)
	{
		Object response = operation.getResponse();

		form.setStatus(operation.getStatus());
		form.setContentType(operation.getContentType());
		form.setCookie(operation.getCookie());
		form.setResult(operation.getResult());
		form.setResponse(response);
		form.setLastUrl(operation.getLastUrl());
		if (response != null)
		{
			form.setTransactionToken(((com.rssl.phizic.business.test.atm.generated.ResponseElement)response).getTransactionToken());
			com.rssl.phizic.business.test.atm.generated.DocumentDescriptor document2 = ((com.rssl.phizic.business.test.atm.generated.ResponseElement)response).getDocument();
			if (document2 != null)
				form.setDocument(document2);
		}
	}
}