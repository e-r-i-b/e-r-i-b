package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данные о сотруднике СБ
 */
@Immutable
public class SBEmployee
{
	private final String tb;

	private final String departmentFullName;

	private final boolean isTBChairman;

	/**
	 * ctor
	 * @param tb - код тербанка (never null)
	 * @param departmentFullName - полное наименование подразделения (never null)
	 * @param TBChairman - флажок "председатель тербанка"
	 */
	public SBEmployee(String tb, String departmentFullName, boolean TBChairman)
	{
		if (StringHelper.isEmpty(tb))
		    throw new IllegalArgumentException("Не указан код тербанка");
		if (StringHelper.isEmpty(departmentFullName))
			throw new IllegalArgumentException("Не указано полное наименование подразделения");

		this.tb = tb;
		this.departmentFullName = departmentFullName;
		isTBChairman = TBChairman;
	}

	/**
	 * @return код тербанка (never null)
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return полное наименование подразделения (never null)
	 */
	public String getDepartmentFullName()
	{
		return departmentFullName;
	}

	/**
	 * @return флажок "председатель тербанка"
	 */
	public boolean isTBChairman()
	{
		return isTBChairman;
	}
}
