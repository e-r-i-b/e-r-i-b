package com.rssl.phizic.notifications;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 24.01.2007
 * @ $Author$
 * @ $Revision$
 */

public interface Notification
{
	/**
	 * @return ������������� ����������
	 */
	public Long getId();
	/**
	 * @return ���� � ����� �������� ����������
	 */
	Calendar getDateCreated();

	/**
	 * @return ����������� ��� ����������
	 */
	Class<? extends Notification> getType();
}