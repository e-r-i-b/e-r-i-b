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
	 * @param reader - �����
	 */
	public InvoicingConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ���������� ������� ������� ���������� �� ��������� ������� ������
	 */
	public Long getOrderNotificationCount()
	{
		return orderNotificationCount;
	}

	/**
	 * ���������� �������� ������������ ������� ���� ��� �����������
	 * ������� ������� ���������� �� ��������� �������� �������
	 * @return �������� (� �����)
	 */
	public Integer getNotificationsDiffHours()
	{
		return notificationsDiffHours;
	}

	/**
	 * @return ���������� ����, ������ ������� ������� ��� ������, �� ��������� � ��������
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
	 * ������ �������� (� ����) ��� ������� �������� �������
	 * @return ������ ��������
	 */
	public Pair<Integer, Integer> getDelayedOrdersPeriod()
	{
		return delayedOrdersPeriod;
	}
}
