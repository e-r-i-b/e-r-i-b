package com.rssl.phizicgate.way4u.messaging;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLElementAttributes;
import com.rssl.phizic.utils.xml.XMLMessageWriter;
import com.rssl.phizic.utils.xml.XMLWriter;

/**
 * @author krenev
 * @ created 09.10.2013
 * @ $Author$
 * @ $Revision$
 * Абстракция - запрос в way4u.
 */

public abstract class Way4uRequest
{
	private String id;
	private String body;

	protected void init(String msgType, String scheme, String app, String objectType, String actionType, boolean needResultDtls)
	{
		id = new RandomGUID().getStringValue();
		body = buildMessage(msgType, scheme, app, objectType, actionType, needResultDtls);
	}

	/**
	 * @return идентифкатор запроса
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return тип запроса. По умолчанию возвращается имя класса запроса.
	 */
	public String getType()
	{
		return getClass().getSimpleName();
	}

	/**
	 * @return строковое представление запроса
	 */
	public String asString()
	{
		return body;
	}

	private String buildMessage(String msgType, String scheme, String app, String objectType, String actionType, boolean needResultDtls)
	{
		XMLMessageWriter writer = new XMLMessageWriter("UTF-8");
		writer.startDocument();
			writer.startElement("UFXMsg", new XMLElementAttributes()
					.add("direction", "Rq")
					.add("msg_type", msgType)
					.add("scheme", scheme)
					.add("version", "2.0")
			);
				writer.writeTextElement("MsgId", getId());
				writer.startElement("Source", new XMLElementAttributes("app", app)).endElement();
				writer.startElement("MsgData").startElement(msgType);
					writer.writeTextElement("RegNumber", getId());
					writer.writeTextElement("ObjectType", objectType);
					writer.writeTextElement("ActionType", actionType);
					if (needResultDtls)
						buildResultDtls(writer);
					buildObjectFor(writer);
				writer.endElement().endElement();
			writer.endElement();
		writer.endDocument();
		return writer.toString();
	}

	protected void buildObjectFor(XMLWriter writer)
	{
		writer.startElement("ObjectFor");
			buildObjectForData(writer);
		writer.endElement();
	}

	/**
	 * Построить часть запроса, находящуюся внутри тега /UFXMsg/MsgData/Information/ObjectFor
	 * @param writer
	 */
	protected abstract void buildObjectForData(XMLWriter writer);


	/**
	 * Построить часть запроса c фильтрами
	 * /UFXMsg/MsgData/Information/ResultDtls/Filter
	 * @param writer XML-писатель
	 */
	protected abstract void buildFilter(XMLWriter writer);

	/**
	 * <ResultDtls>
	 *    <Parm>
	 *       <ParmCode>Client</ParmCode>
	 *       <Value>Y</Value>
	 *    </Parm>
	 *    <Parm>
	 *       <ParmCode>Contract</ParmCode>
	 *       <Value>Y</Value>
	 *    </Parm>
	 *    <Parm>
	 *       <ParmCode>ExtraRs</ParmCode>
	 *       <Value>SBRFContrCustCardLogin</Value>
	 *    </Parm>
	 *    <Filter>
	 *        ....
	 *    </Filter>
	 * </ResultDtls>
	 */
	private void buildResultDtls(XMLWriter writer)
	{
		writer.startElement("ResultDtls");
		buildParm(writer, "Client", "Y");
		buildParm(writer, "Contract", "Y");
		buildParm(writer, "ExtraRs", "SBRFContrCustCardLogin");
		buildFilter(writer);
		writer.endElement();
	}

	/**
	 * <Parm>
	 *    <ParmCode>parmCode</ParmCode>
	 *    <Value>value</Value>
	 * </Parm>
	 */
	private void buildParm(XMLWriter writer, String parmCode, String value)
	{
		writer.startElement("Parm");
			writer.writeTextElement("ParmCode", parmCode);
			writer.writeTextElement("Value", value);
		writer.endElement();
	}
}
