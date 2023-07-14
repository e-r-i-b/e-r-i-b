package com.rssl.auth.csa.wsclient.requests.clients;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на получение количества клиентов, ожидающих миграцию
 */

public class GetTemporaryNodeClientsCountRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "getTemporaryNodeClientsCountRq";

	private final Long nodeId;

	/**
	 * конструктор
	 * @param nodeId идентификатор блока
	 */
	public GetTemporaryNodeClientsCountRequestData(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, RequestConstants.NODE_ID_TAG, String.valueOf(nodeId)));
		return request;
	}
}