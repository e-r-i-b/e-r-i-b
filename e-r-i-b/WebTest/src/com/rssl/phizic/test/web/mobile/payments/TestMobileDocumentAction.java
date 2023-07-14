package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.test.mobile.SendMobileRequestOperation;
import com.rssl.phizic.test.web.mobile.TestMobileForm;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.Action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * @author serkin
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class TestMobileDocumentAction extends Action
{
	protected int send(TestMobileDocumentForm form)
	{
		try
		{
			form.setSubmit(true);

			SendMobileRequestOperation operation = new SendMobileRequestOperation();
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
	
	protected void updateOperation(SendMobileRequestOperation operation, TestMobileForm form)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());
		if(StringHelper.isNotEmpty(form.getVersion()))
			operation.setVersion(form.getVersion());

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

	protected void updateForm(TestMobileDocumentForm form, SendMobileRequestOperation operation)
	{
		Object response = operation.getResponse();

		form.setStatus(operation.getStatus());
		form.setContentType(operation.getContentType());
		form.setCookie(operation.getCookie());
		form.setResult(operation.getResult());
		form.setResponse(response);
		form.setLastUrl(operation.getLastUrl());
		if (response != null) {
			try
			{
				Class responseClass = response.getClass();
				Method getTransactionToken = responseClass.getMethod("getTransactionToken");
				form.setTransactionToken((String) getTransactionToken.invoke(response));

				Method getDocument = responseClass.getMethod("getDocument");
				Object document = getDocument.invoke(response);
				if (document != null)
					form.setDocument(document);
			}
			catch (NoSuchMethodException e)
			{
				throw new RuntimeException("Некорректный тип документа", e);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException("Некорректный тип документа", e);
			}
			catch (InvocationTargetException e)
			{
				//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
				throw new RuntimeException("Ошибка выполнения метода", e.getTargetException());
			}
		}
	}
}
