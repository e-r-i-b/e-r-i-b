package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 18.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� �� ������ ����� ���
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public final class BKIJobReport
{
	/**
	 * ���� ���������� ������� �����
	 * ���� � �����
	 * ����� �������������
	 */
	public Calendar lastStartTime;

	/**
	 * ������ ���������� �������� ������� �����
	 * ���� � �����
	 * ����� �������������
	 */
	public Calendar lastPeriodBegin;

	/**
	 * ����� ���������� �������� ������� �����
	 * ���� � �����
	 * ����� �������������
	 */
	public Calendar lastPeriodEnd;
}
