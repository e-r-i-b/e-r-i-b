package com.rssl.phizicgate.way4u.messaging.requests;

import com.rssl.phizic.utils.xml.XMLWriter;
import com.rssl.phizicgate.way4u.messaging.Way4uRequest;

/**
 * @author krenev
 * @ created 09.10.2013
 * @ $Author$
 * @ $Revision$
 * Запрос информации по контракту и клиенту, идентифицируемому по логину
 */

public class GetUserInfoByUserIdRequest extends Way4uRequest
{
	private String userId;

	/**
	 * Конструктор запроса
	 * @param userId логин клиента
	 */
	public GetUserInfoByUserIdRequest(String userId)
	{
		this.userId = userId;
		init("Information","WAY4Appl",  "CSA", "Contract", "Inquiry", true);
	}

	protected void buildObjectForData(XMLWriter writer)
	{
		writer.startElement("ClientIDT");
			writer.writeTextElement("CustomIDT", userId);
			writer.writeTextElement("CustomCode", "AUTH_IDT");
		writer.endElement();
	}

	/**
	 *    <Filter>
	 *       <Type>Contract</Type>
	 *       <Code>ClientLogin</Code>
	 *       <Parms>LOGIN=userId;</Parms>
	 *    </Filter>
	 */
	protected void buildFilter(XMLWriter writer)
	{
		writer.startElement("Filter");
			writer.writeTextElement("Type", "Contract");
			writer.writeTextElement("Code", "ClientLogin");
			writer.writeTextElement("Parms", String.format("LOGIN=%s;", userId));
		writer.endElement();
	}
}
