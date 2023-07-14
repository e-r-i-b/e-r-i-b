package com.rssl.phizic.einvoicing;

import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.shoplistener.generated.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;

/**
 * Построение xml с хранением детальной информации по заказу (список товаров, информация о броне авиабилетов).
 *
 * @author bogdanov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopDetailInfoBuilder
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	/**
	 * Создание xml с информацией о товарах. Обновление таблицы в базе данных.
	 * fields
	 *   field
	 *     description
	 *     name
	 *     count
	 *     price
	 *       amount
	 *       currency
	 *
	 * @param items список товаров.
	 */
	public static String fillShopGoodsInfo(ItemType[] items) throws GateException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("fields");
		for (ItemType field : items)
		{
			if (field.getCount().doubleValue() == 0)
			{
				log.warn("Количество единиц товара с наименованием = '" + field.getName() + "' равно нулю");
				continue;
			}
			if (field.getPrice().doubleValue() == 0)
			{
				log.warn("Стоимость товара с наименованием = '" + field.getName() + "' равно нулю");
				continue;
			}
			builder.openEntityTag("field");
			if (StringHelper.isNotEmpty(field.getName()))
				builder.createEntityTag("name", field.getName());
			if (StringHelper.isNotEmpty(field.getItemDesc()))
				builder.createEntityTag("description", field.getItemDesc());
			if (field.getCount() != null)
				builder.createEntityTag("count", field.getCount().toString());
			if (field.getPrice() != null)
			{
				builder.openEntityTag("price");
				builder.createEntityTag("amount", field.getPrice().toString());
				builder.createEntityTag("currency", field.getPriceCur().getValue());
				builder.closeEntityTag("price");
			}
			builder.closeEntityTag("field");
		}
		builder.closeEntityTag("fields");
		return builder.toString();
	}

	/**
	 * Создание xml информации о броне авиабилетов. Обновление таблицы в базе данных.
	 * AirlineReservation
	 *   ReservId
	 *   ReservExpiration
	 *   PassengersList
	 *     Passenger
	 *       FirstName
	 *       LastName
	 *       Type
	 *   RoutesList
	 *     Route
	 *       AirlineName
	 *       Departure
	 *         Airport
	 *         Location
	 *         Terminal
	 *         DateTime
	 *         Flight
	 *       Arrival
	 *         Airport
	 *         Location
	 *         Terminal
	 *         DateTime
	 *         Flight
	 * @param reserv информация о броне.
	 */
	public static String fillAirlineReservation(AirlineReservationType reserv) throws GateException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("AirlineReservation")
				.createEntityTag("ReservId", reserv.getReservId());
        if (StringHelper.isNotEmpty(reserv.getReservExpiration()))
		    builder.createEntityTag("ReservExpiration", reserv.getReservExpiration());

		builder.openEntityTag("PassengersList");
		for (PassengerType passenger : reserv.getPassengersList()) {
			builder.openEntityTag("Passenger")
					.createEntityTag("FirstName", passenger.getFirstName())
					.createEntityTag("LastName", passenger.getLastName())
					.createEntityTag("Type", passenger.getType())
					.closeEntityTag("Passenger");
		}
		builder.closeEntityTag("PassengersList");

		builder.openEntityTag("RoutesList");
		for (RouteType route : reserv.getRoutesList()) {
			builder.openEntityTag("Route")
					.createEntityTag("AirlineName", route.getAirlineName());

			DepartureType departure = route.getDeparture();
			builder.openEntityTag("Departure")
					.createEntityTag("Airport", departure.getAirport());
            if (StringHelper.isNotEmpty(departure.getLocation()))
			    builder.createEntityTag("Location", departure.getLocation());
            if (StringHelper.isNotEmpty(departure.getTerminal()))
			    builder.createEntityTag("Terminal", departure.getTerminal());
			builder.createEntityTag("DateTime", departure.getDateTime())
					.createEntityTag("Flight", departure.getFlight())
					.closeEntityTag("Departure");

			ArrivalType arrival = route.getArrival();
			builder.openEntityTag("Arrival")
					.createEntityTag("Airport", arrival.getAirport());
            if (StringHelper.isNotEmpty(arrival.getLocation()))
			    builder.createEntityTag("Location", arrival.getLocation());
            if (StringHelper.isNotEmpty(arrival.getTerminal()))
			    builder.createEntityTag("Terminal", arrival.getTerminal());
			builder.createEntityTag("DateTime", arrival.getDateTime())
					.createEntityTag("Flight", arrival.getFlight())
					.closeEntityTag("Arrival");

			builder.closeEntityTag("Route");
		}
		builder.closeEntityTag("RoutesList")
				.closeEntityTag("AirlineReservation");

		return builder.toString();
	}

	public static void updateOrder(final String orderUuid, final String orderDetailInfo) throws GateException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.einvoicing.ShopOrders.setOrderInfo");
			executorQuery.setParameter("uuid", orderUuid).setParameter("detailInfo", orderDetailInfo).executeUpdate();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
