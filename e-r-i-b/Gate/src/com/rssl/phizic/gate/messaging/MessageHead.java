package com.rssl.phizic.gate.messaging;

import java.util.Calendar;

/**
 * ��������� �������
 * @author niculichev
 * @ created 19.06.2012
 * @ $Author$
 * @ $Revision$
 */
public interface MessageHead
{
	/**
	 * @return ������������ ���������
	 */
	public String getMessageId();

	/**
	 * @return ���� ���������
	 */
	public Calendar getMessageDate();

	/**
	 * @return ����������� ���������
	 */
	public String getFromAbonent();

	/**
	 * @return ������������ ��������� c �������� �������
	 */
	public String getParentMessageId();

	/**
	 * @return ���� ��������� c �������� �������
	 */
	public String getParentMessageDate();

	/**
	 * @return ����������� ��������� c �������� �������
	 */
	public String getParentFromAbonent();
}
