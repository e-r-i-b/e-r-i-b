package com.rssl.phizic.logging.monitoring;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ������ ��� �����������.
 *
 * @author bogdanov
 * @ created 03.03.15
 * @ $Author$
 * @ $Revision$
 */

public interface MonitoringEntry extends Serializable
{
	/**
	 * @return ����������, � ������� ��������� ��������.
	 */
	public String getApplication();

	/**
	 * @param application ����������, � ������� ��������� ��������.
	 */
	public void setApplication(String application);

	/**
	 * @return ����� �����, � ������� ���������� ��������.
	 */
	public long getNodeId();

	/**
	 * @param nodeId ����� �����, � ������� ���������� ��������.
	 */
	public void setNodeId(long nodeId);

	/**
	 * @return ��������� �������.
	 */
	public String getPlatform();

	/**
	 * @param platform ��������� �������.
	 */
	public void setPlatform(String platform);

	/**
	 * @return ���� �������.
	 */
	public Calendar getStartDate();

	/**
	 * @param startDate ���� �������.
	 */
	public void setStartDate(Calendar startDate);

	/**
	 * @return ������� �������.
	 */
	public String getTb();

	/**
	 * @param tb ������� �������.
	 */
	public void setTb(String tb);
}
