package com.rssl.phizic.logging.monitoring;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Запись для мониторинга.
 *
 * @author bogdanov
 * @ created 03.03.15
 * @ $Author$
 * @ $Revision$
 */

public interface MonitoringEntry extends Serializable
{
	/**
	 * @return приложение, в котором выполнена операция.
	 */
	public String getApplication();

	/**
	 * @param application приложение, в котором выполнена операция.
	 */
	public void setApplication(String application);

	/**
	 * @return номер блока, в котором выполнениа операция.
	 */
	public long getNodeId();

	/**
	 * @param nodeId номер блока, в котором выполнениа операция.
	 */
	public void setNodeId(long nodeId);

	/**
	 * @return платформа клиента.
	 */
	public String getPlatform();

	/**
	 * @param platform платформа клиента.
	 */
	public void setPlatform(String platform);

	/**
	 * @return дата события.
	 */
	public Calendar getStartDate();

	/**
	 * @param startDate дата события.
	 */
	public void setStartDate(Calendar startDate);

	/**
	 * @return тербанк клиента.
	 */
	public String getTb();

	/**
	 * @param tb тербанк клиента.
	 */
	public void setTb(String tb);
}
