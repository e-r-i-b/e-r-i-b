package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * @author Balovtsev
 * @since 15.05.2014
 */
public class AddAlfOperationData extends RequestDataBase
{
	private final Map<String, Object> parameters;

	public AddAlfOperationData(Map<String, Object> parameters)
	{
		this.parameters = parameters;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element  root    = request.getDocumentElement();

		if (MapUtils.isNotEmpty(parameters))
		{
			for (String key : parameters.keySet())
			{
				root.appendChild(createTag(request, key, (String) parameters.get(key)));
			}
		}

		return request;
	}
}
