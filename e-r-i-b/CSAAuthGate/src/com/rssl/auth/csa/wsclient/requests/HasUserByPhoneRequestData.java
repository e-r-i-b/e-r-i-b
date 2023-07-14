package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.List;

/**
 * Список номеров ЕРМБ по текущему списку номеров.
 *
 * @author bogdanov
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 */

public class HasUserByPhoneRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "HasUserByPhoneRq";
	private final String phones;

	public HasUserByPhoneRequestData(Collection<String> phones)
	{
		StringBuilder sb = new StringBuilder(12);
		for (String ph : phones)
		{
			if (sb.length() != 0)
				sb.append("|");
			sb.append(ph);
		}
		this.phones = sb.toString();
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, RequestConstants.PHONES_TAG, phones));
		return request;
	}
}
