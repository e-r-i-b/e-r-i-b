package com.rssl.phizic.utils;

/**
 * @author Erkin
 * @ created 03.01.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * [���������] ���������
 */
@SuppressWarnings("PublicField")
public class Priority implements Comparable<Priority>
{
	/**
	 * ������ (�������) ��������
	 */
	public int p1 = 0;

	/**
	 * ������ ��������
	 */
	public int p2 = 0;

	/**
	 * ������ ��������
	 */
	public int p3 = 0;

	/**
	 * �������� ���������
	 */
	public int p4 = 0;

	public int compareTo(Priority other)
	{
		if (this.p1 != other.p1)
			return this.p1 - other.p1;

		if (this.p2 != other.p2)
			return this.p2 - other.p2;

		if (this.p3 != other.p3)
			return this.p3 - other.p3;

		if (this.p4 != other.p4)
			return this.p4 - other.p4;

		return 0;
	}
}
