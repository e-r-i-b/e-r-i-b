package com.rssl.phizic.utils;

/**
 * @author Omeliyanchuk
 * @ created 29.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class MemoryMeasureUtils
{
	public static void printMemory(String message)
	{
		if(message!=null)
			System.out.println("----------------"+message+"----------");

		System.out.println("Total(M):" + ((Runtime.getRuntime().totalMemory())/1024)/1024);
		System.out.println("MaxMemory(M):" + ((Runtime.getRuntime().maxMemory())/1024)/1024);
		System.out.println("FreeMemory(M):" + ((Runtime.getRuntime().freeMemory())/1024)/1024);
	}
}
