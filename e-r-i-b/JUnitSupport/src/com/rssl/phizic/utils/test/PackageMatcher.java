package com.rssl.phizic.utils.test;

/**
 * @author Roshka
 * @ created 27.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class PackageMatcher implements TestCaseMatcher<Class<?>>
{
	private String packageNam;

	public PackageMatcher(String packageNam)
	{
		this.packageNam = packageNam;
	}

	/**
	 * Если пакет null - true
	 * @param clazz
	 * @return
	 */
	public boolean match(Class<?> clazz)
	{
		return packageNam == null ? true : clazz.getPackage().getName().contains(packageNam);
	}
}