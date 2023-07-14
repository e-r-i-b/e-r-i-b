package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.utils.DateHelper;
import org.w3c.dom.Document;

import java.util.Calendar;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ChangeOrderStatusRequestData extends InvoiceRequestDataBase
{
	private OrderState newState;
	private Long nodeId;
	private String utrrno;
	private String paidBy;
	private Calendar delayDate;

	public ChangeOrderStatusRequestData(String orderUUID, OrderState newState, Long nodeId, String utrrno, String paidBy)
	{
		super(orderUUID);
		this.newState = newState;
		this.nodeId = nodeId;
		this.utrrno = utrrno;
		this.paidBy= paidBy;
		this.delayDate = null;
	}

	public ChangeOrderStatusRequestData(String orderUUID, OrderState newState, Long nodeId, String utrrno, String paidBy, Calendar delayDate)
	{
		super(orderUUID);
		this.newState = newState;
		this.nodeId = nodeId;
		this.utrrno = utrrno;
		this.paidBy= paidBy;
		this.delayDate = delayDate;
	}

	public String getName()
	{
		return "ChangeOrderStatusRequest";
	}

	@Override
	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		fillOrderUUID(builder);
		builder.addParameter(PAYD_BY_TAG, paidBy);
		builder.addParameter(UTRRNO_TAG, utrrno);
		builder.addParameterIfNotEmpty(STATE_TAG, newState);
		builder.addParameterIfNotEmpty(NODE_ID_TAG, nodeId);
		builder.addParameterIfNotEmpty(DELAY_DATE_TAG, DateHelper.formatDateToStringWithSlash(delayDate));
		return builder.closeTag().toDocument();
	}
}
