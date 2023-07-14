package com.rssl.phizic.einvoicing.multiblock.requestdata;

import com.rssl.phizgate.einvoicing.Constants;
import com.rssl.phizgate.messaging.internalws.client.RequestDataBase;
import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 07.03.14
 * @ $Author$
 * @ $Revision$
 */
public class SendTicketsRequestData extends RequestDataBase
{
	private String orderUUID;
	private String ticketsInfo;

	/**
	 * ctor
	 * @param orderUUID - uuid заказа
	 * @param ticketsInfo - информация по заказу
	 */
	public SendTicketsRequestData(String orderUUID, String ticketsInfo)
	{
		this.orderUUID = orderUUID;
		this.ticketsInfo = ticketsInfo;
	}

	public String getName()
	{
		return "SendTicketsRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		builder.addParameter(Constants.ORDER_UUID_TAG, orderUUID);
		builder.addParameter(Constants.TICKETS_INFO_TAG, ticketsInfo);
		return builder.closeTag().toDocument();
	}
}
