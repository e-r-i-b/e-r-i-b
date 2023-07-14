package com.rssl.phizic.gate.einvoicing;

import java.util.Calendar;

/**
 * ����������� � ����������� �������� ��� ������.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopNotification
{
	/**
	* @return ������� ������������� ������ � ����
	*/
	public String getUuid();

	/**
	* @return ���������� ������������� ������/��������� ������/�������� � ����������
	*/
	public String getExternalId();

	/**
	* @return ���������� ��� �������� SVFE
	*/
	public String getUtrrno();

	/**
	* @return ������ ���������
	*/
	public String getState();

	/**
	* @return ������ ����������
	*/
	public NotificationStatus getNotifStatus();

	/**
	 * ���������� ������ ����������
	* @param status ������ ����������
	*/
	public void setNotifStatus(NotificationStatus status);

	/**
	* @return ����� ����������
	*/
	public Calendar getNotifTime();

	/**
	 * ���������� ����� ����������
	 * @param notifTime - ����� ����������
	 */
	public void setNotifTime(Calendar notifTime);

	/**
	* @return ���������� ������� ����������
	*/
	public Long getNotifCount();

	/**
	 * ���������� ���������� ������� ����������
	* @param count ���������� ������� ����������
	*/
	public void setNotifCount(Long count);

	/**
	* @return ����� ������ (��� ����������, ������������� �������)
	*/
	public String getNotifStatusDescription();

	/**
	 * ���������� ����� ������
	 * @param notifStatusDescription - ����� ������
	 */
	public void setNotifStatusDescription(String notifStatusDescription);

	/**
	* @return ��� ����������
	*/
	public String getReceiverCode();

	/**
	 * @return ���� �������� ����������
	 */
	public Calendar getDate();
}
