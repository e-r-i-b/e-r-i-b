package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Balovtsev
 * @since 15.05.2014
 */
public class AlfEditOperationData extends RequestDataBase
{
	protected final Map<String, Object> parameters;

	public AlfEditOperationData(Map<String, Object> parameters)
	{
		this.parameters = parameters;
	}

	public Document getBody()
	{
		Pattern  pattern = Pattern.compile("(\\[\\d+\\])");
		Document request = createRequest();
		Element  root    = request.getDocumentElement();

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
				Element newOperations = createTag(request, "newOperations", null);

				for (String key : elementsByIndex.keySet())
				{
					Element newOperation = createTag(request, "newOperation", null);
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

	protected Map<String, List<Element>> groupElementsByIndex(Document request, final Pattern pattern, Map<String, Object> parameters, Map<String, List<Element>> elements)
	{
		if (elements == null)
		{
			elements = new HashMap<String, List<Element>>();
		}

		if (MapUtils.isNotEmpty(parameters))
		{
			final StringBuilder filter = new StringBuilder();

			for (String key : parameters.keySet())
			{
				if (pattern.matcher(key).find())
				{
					filter.append(key);
					break;
				}
			}

			if (StringHelper.isNotEmpty(filter.toString()))
			{
				Matcher matcher = pattern.matcher(filter.toString());

				if (matcher.find())
				{
					filter.delete(0, filter.length());
					filter.append(matcher.group());
				}

				List<Element> values = new ArrayList<Element>();
				for (String key : new HashSet<String>(parameters.keySet()))
				{
					if (key.contains(filter))
					{
						Object value = parameters.remove(key);
						values.add(createTag(request, key.replace(new String(filter.toString()), ""), (String) value));
					}
				}

				elements.put(filter.toString(), values);
			}

			return groupElementsByIndex(request, pattern, parameters, elements);
		}

		return elements;
	}

	protected List<Element> groupSimpleElements(Document request, Pattern pattern, Map<String, Object> parameters)
	{
		List<Element>    elements = new ArrayList<Element>();
		Iterator<String> iterator = parameters.keySet().iterator();

		while (iterator.hasNext())
		{
			String key = iterator.next();

			if (!pattern.matcher(key).find())
			{
				elements.add(createTag(request, key, (String) parameters.get(key)));
				iterator.remove();
			}
		}

		return elements;
	}

	@Override
	protected Element createTag(Document request, String tagName, String tagValue)
	{
		if (StringHelper.isEmpty(tagValue))
		{
			return request.createElement(tagName);
		}
		else
		{
			return super.createTag(request, tagName, tagValue);
		}
	}
}
