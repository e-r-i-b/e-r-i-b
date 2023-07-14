package com.rssl.phizicgate.way4u.messaging.requests;

import com.rssl.phizic.utils.xml.XMLWriter;
import com.rssl.phizicgate.way4u.messaging.Way4uRequest;

/**
 * @author krenev
 * @ created 09.10.2013
 * @ $Author$
 * @ $Revision$
 * Запрос информации по контракту и клиенту, идентифицируемому по номеру карты
 */

public class GetUserInfoByCardNumberRequest extends Way4uRequest
{
	private String cardNumber;

	/**
	 * Конструктор запроса
	 * @param cardNumber номер карты
	 */
	public GetUserInfoByCardNumberRequest(String cardNumber)
	{
		this.cardNumber = cardNumber;
		init("Information","WAY4Appl", "CSA", "Contract", "Inquiry", true);
	}

	protected void buildObjectForData(XMLWriter writer)
	{
		writer.startElement("ClientIDT");
			writer.writeTextElement("RefContractNumber", cardNumber);
		writer.endElement();
	}

	/**
	 *    <Filter>
	 *       <Type>Contract</Type>
	 *       <Code>ClientLogin</Code>
	 *       <Parms>CARD=cardNumber;</Parms>
	 *    </Filter>
	 */
	protected void buildFilter(XMLWriter writer)
	{
		writer.startElement("Filter");
			writer.writeTextElement("Type", "Contract");
			writer.writeTextElement("Code", "ClientLogin");
			writer.writeTextElement("Parms", String.format("CARD=%s;", cardNumber));
		writer.endElement();
	}
}
