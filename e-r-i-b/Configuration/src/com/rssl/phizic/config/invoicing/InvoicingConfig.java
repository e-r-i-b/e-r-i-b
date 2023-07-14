package com.rssl.phizic.config.invoicing;

import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author gladishev
 * @ created 28.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class InvoicingConfig extends Config
{
	public static final String ORDERS_MAX_NOTIFY_COUNT = "job.orders.notify.max.count";
	public static final String NOTIFICATIONS_SELECT_DIFF = "job.orders.notifications.select.date.diff.hours";
	public static final String CLEAR_ORDER_DAYS = "job.orders.clear.days.count";
	public static final String DELAYED_ORDERS_PERIOD = "job.orders.delayed.period";

	private Long orderNotificationCount;
	private Integer clearOrderDaysCount;
	private Integer notificationsDiffHours;
	private Pair<Integer, Integer> delayedOrdersPeriod;

	/**
	 * ctor
	 * @param reader - ридер
	 */
	public InvoicingConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return  оличество попыток выслать оповещение об изменении статуса заказа
	 */
	public Long getOrderNotificationCount()
	{
		return orderNotificationCount;
	}

	/**
	 * ¬озвращает смещение относительно текущей даты дл€ определени€
	 * периода выборки оповещений по изменению статусов заказов
	 * @return —мещение (в часах)
	 */
	public Integer getNotificationsDiffHours()
	{
		return notificationsDiffHours;
	}

	/**
	 * @return  оличество дней, старше которых удал€ем все заказы, не св€занные с клиентом
	 */
	public Integer getClearOrderDaysCount()
	{
		return clearOrderDaysCount;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		orderNotificationCount = getLongProperty(ORDERS_MAX_NOTIFY_COUNT);
		clearOrderDaysCount = getIntProperty(CLEAR_ORDER_DAYS);
		notificationsDiffHours = getIntProperty(NOTIFICATIONS_SELECT_DIFF);
		try
		{
			String[] tmp = getProperty(DELAYED_ORDERS_PERIOD).split("-");
			delayedOrdersPeriod = new Pair<Integer, Integer>(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
		}
		catch (Exception ignore)
		{
			delayedOrdersPeriod = null;
		}
	}

	/**
	 * ѕериод смещени€ (в дн€х) дл€ выборки зависших заказов
	 * @return ѕериод смещени€
	 */
	public Pair<Integer, Integer> getDelayedOrdersPeriod()
	{
		return delayedOrdersPeriod;
	}
}
