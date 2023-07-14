package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Запрос на редактирование группы операций
 * @author Jatsky
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */

public class AlfEditOperationGroupData extends AlfEditOperationData
{
	public AlfEditOperationGroupData(Map<String, Object> parameters)
	{
		super(parameters);
	}

	public Document getBody()
	{
		Pattern pattern = Pattern.compile("(\\[\\d+\\])");
		Document request = createRequest();
		Element root = request.getDocumentElement();

		if (MapUtils.isNotEmpty(parameters))
		{
			List<Element> simpleElements = groupSimpleElements(request, pattern, parameters);
			for (Element element : simpleElements)
			{
				root.appendChild(element);
			}

			Map<String, List<Element>> elementsByIndex = groupElementsByIndex(request, pattern, parameters, null);
			if (MapUtils.isNotEmpty(elementsByIndex))
			{
				Element newOperations = createTag(request, "operations", null);

				for (String key : elementsByIndex.keySet())
				{
					Element newOperation = createTag(request, "operation", null);
					for (Element element : elementsByIndex.get(key))
					{
						newOperation.appendChild(element);
					}
					newOperations.appendChild(newOperation);
				}

				root.appendChild(newOperations);
			}
		}

		return request;
	}
}
