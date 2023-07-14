package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Balovtsev
 * @since 12.05.2014
 */
public class SimpleParameterizedData extends RequestDataBase
{
	private final Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * @param params дополнительные параметры запроса
	 */
	public SimpleParameterizedData(Map<String, Object> params)
	{
		this.params.putAll(params);
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element  root    = request.getDocumentElement();

		if (MapUtils.isNotEmpty(params))
		{
			for (String key : params.keySet())
			{
				Object value = params.get(key);

				if (value instanceof List)
				{
					for (Object v : (List) value)
					{
						root.appendChild(createTag(request, key, (String) v));
					}
				}
				else
				{
					root.appendChild(createTag(request, key, (String) value));
				}
			}
		}

		return request;
	}
}
