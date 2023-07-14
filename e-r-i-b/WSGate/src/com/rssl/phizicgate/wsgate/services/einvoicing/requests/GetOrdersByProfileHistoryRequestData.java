package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class GetOrdersByProfileHistoryRequestData extends InvoiceRequestDataBase
{
	private List<ShopProfile> profiles;
	private Calendar dateFrom;
	private Calendar dateTo;
	private Calendar dateDelayedTo;
	private BigDecimal amountFrom;
	private BigDecimal amountTo;
	private String currency;
	private OrderState[] states;
	private Long limit;                 //ограничение на количество избираемых заказов
	private Boolean orderByDelayDate;   //нужно ли сортировать данные по дате отложенности (только если выбираем исключительно из отложенных)

	public GetOrdersByProfileHistoryRequestData(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, Long limit, Boolean orderByDelayDate, OrderState... states)
	{
		super(null);
		this.profiles = Collections.unmodifiableList(profiles);
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.dateDelayedTo = dateDelayedTo;
		this.amountFrom = amountFrom;
		this.amountTo = amountTo;
		this.currency = currency;
		this.states = states.clone();
		this.limit = limit;
		this.orderByDelayDate = orderByDelayDate;
	}

	public String getName()
	{
		return "GetOrdersByProfileHistoryRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();

		builder.openTag(PROFILES_TAG);
		for (ShopProfile profile : profiles)
			addProfileElement(profile, builder);
		builder.closeTag();

		builder.addParameter(DATE_FROM_TAG, format(dateFrom));
		builder.addParameter(DATE_TO_TAG, format(dateTo));
		builder.addParameter(DATE_DELAYED_TO_TAG, format(dateDelayedTo));
		builder.addParameter(AMOUNT_FROM_TAG, format(amountFrom));
		builder.addParameter(AMOUNT_TO_TAG, format(amountTo));
		builder.addParameter(CURRENCY_TAG, currency);
		builder.addParameterIfNotEmpty(LIMIT_TAG, limit);
		builder.addParameterIfNotEmpty(ORDER_BY_DELAYED_DATE_TAG, orderByDelayDate);

		builder.openTag(STATES_TAG);
		for (OrderState state : states)
			builder.addParameter(STATE_TAG, state.name());
		builder.closeTag();

		return builder.closeTag().toDocument();
	}
}
