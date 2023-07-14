package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Balovtsev
 * @since 25.04.14
 */
public class MenuRequestData extends RequestDataBase
{
	private final String[] exclude;

	/**
	 * @param exclude элементы визуального интерфейса которые не должны передаваться в ответе
	 */
	public MenuRequestData(String... exclude)
	{
		this.exclude = exclude;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element  root    = request.getDocumentElement();
		Element  body    = createTag(request, RequestConstants.MENU_EXCLUDE_ELEMENT_ROOT_TAG, null);

		if (ArrayUtils.isNotEmpty(exclude))
		{
			for (String value : exclude)
			{
				body.appendChild(createTag(request, RequestConstants.MENU_EXCLUDE_ELEMENT_TAG, value));
			}
		}

		root.appendChild(body);
		return request;
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
