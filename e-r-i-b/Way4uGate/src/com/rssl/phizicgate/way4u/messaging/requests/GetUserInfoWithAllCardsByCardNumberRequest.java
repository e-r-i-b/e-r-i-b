package com.rssl.phizicgate.way4u.messaging.requests;

import com.rssl.phizic.utils.xml.XMLWriter;

/**
 * @author sergunin
 * @ created 16.06.2014
 * @ $Author$
 * @ $Revision$
 * Запрос информации по контракту и клиенту, идентифицируемому по номеру карты
 */

public class GetUserInfoWithAllCardsByCardNumberRequest extends GetUserInfoByCardNumberRequest
{

	public GetUserInfoWithAllCardsByCardNumberRequest(String cardNumber)
	{
		super(cardNumber);
	}

	protected void buildFilter(XMLWriter writer)
	{
	}
}
