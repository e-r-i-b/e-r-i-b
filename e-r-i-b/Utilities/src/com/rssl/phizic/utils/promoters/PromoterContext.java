package com.rssl.phizic.utils.promoters;

/**
 * �������� ����������
 * @author Pankin
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class PromoterContext
{
	private static ThreadLocal<String> shift = new ThreadLocal<String>(); // ������� �����
	private static ThreadLocal<String> promoterID = new ThreadLocal<String>();   //������������� ����������

	/**
	 * @return ������������� �����
	 */
	public static String getShift()
	{
		return shift.get();
	}

	/**
	 * ���������� ������������� �����
	 * @param shiftId ������������� �����
	 */
	public static void setShift(String shiftId)
	{
		shift.set(shiftId);
	}

	/**
	 * ��������
	 */
	public static void clear()
	{
		shift.remove();
		promoterID.remove();
	}

	/**
	 * @return ������������� ����������
	 */
	public static String getPromoterID()
	{
		return promoterID.get();
	}

	/**
	 * ���������� ������� ������������ ����������
	 * @param id - ������� ������������� ����������
	 */
	public static void setPromoterID(String id)
	{
		promoterID.set(id);
	}
}
