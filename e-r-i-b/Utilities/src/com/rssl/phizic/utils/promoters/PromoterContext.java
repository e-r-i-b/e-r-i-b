package com.rssl.phizic.utils.promoters;

/**
 * контекст промоутера
 * @author Pankin
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class PromoterContext
{
	private static ThreadLocal<String> shift = new ThreadLocal<String>(); // Рабочая смена
	private static ThreadLocal<String> promoterID = new ThreadLocal<String>();   //идентификатор промоутера

	/**
	 * @return идентификатор смены
	 */
	public static String getShift()
	{
		return shift.get();
	}

	/**
	 * Установить идентификатор смены
	 * @param shiftId идентификатор смены
	 */
	public static void setShift(String shiftId)
	{
		shift.set(shiftId);
	}

	/**
	 * Очистить
	 */
	public static void clear()
	{
		shift.remove();
		promoterID.remove();
	}

	/**
	 * @return идентификатор промоутера
	 */
	public static String getPromoterID()
	{
		return promoterID.get();
	}

	/**
	 * Установить текущий идентификтор промоутера
	 * @param id - текущий идентификатор промоутера
	 */
	public static void setPromoterID(String id)
	{
		promoterID.set(id);
	}
}
