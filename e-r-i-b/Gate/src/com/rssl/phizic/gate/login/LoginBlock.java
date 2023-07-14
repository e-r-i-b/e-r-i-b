package com.rssl.phizic.gate.login;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ���������� ������
 */

public interface LoginBlock
{
	/**
	 * ������ ��� ����������
	 * @param reasonType ��� ����������
	 */
	public void setReasonType(String reasonType);

	/**
	 * @return ��� ����������
	 */
	public String getReasonType();

	/**
	 * ������ ������� ����������
	 * @param reasonDescription �������
	 */
	public void setReasonDescription(String reasonDescription);

	/**
	 * @return ������� ����������
	 */
	public String getReasonDescription();

	/**
	 * ������ ������ ����������
	 * @param blockedFrom ������ ����������
	 */
	public void setBlockedFrom(Calendar blockedFrom);

	/**
	 * @return ������ ����������
	 */
	public Calendar getBlockedFrom();

	/**
	 * ������ ��������� ����������
	 * @param blockedUntil ��������� ����������
	 */
	public void setBlockedUntil(Calendar blockedUntil);

	/**
	 * @return ��������� ����������
	 */
	public Calendar getBlockedUntil();
}
