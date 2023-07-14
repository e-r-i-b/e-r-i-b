package com.rssl.phizicgate.way4u.messaging.requests;

import com.rssl.phizic.utils.xml.XMLWriter;

/**
 * @author sergunin
 * @ created 04.06.2014
 * @ $Author$
 * @ $Revision$
 * Запрос информации по контрактам и клиенту, идентифицируемому по логину
 */

public class GetUserInfoWithAllCardsByUserIdRequest extends GetUserInfoByUserIdRequest
{
	/**
	 * Конструктор запроса
	 * @param userId логин клиента
	 */
	public GetUserInfoWithAllCardsByUserIdRequest(String userId)
	{
		super(userId);
	}

	protected void buildFilter(XMLWriter writer){}
}
