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
 * ������ � ���������� ��
 */
@Immutable
public class SBEmployee
{
	private final String tb;

	private final String departmentFullName;

	private final boolean isTBChairman;

	/**
	 * ctor
	 * @param tb - ��� �������� (never null)
	 * @param departmentFullName - ������ ������������ ������������� (never null)
	 * @param TBChairman - ������ "������������ ��������"
	 */
	public SBEmployee(String tb, String departmentFullName, boolean TBChairman)
	{
		if (StringHelper.isEmpty(tb))
		    throw new IllegalArgumentException("�� ������ ��� ��������");
		if (StringHelper.isEmpty(departmentFullName))
			throw new IllegalArgumentException("�� ������� ������ ������������ �������������");

		this.tb = tb;
		this.departmentFullName = departmentFullName;
		isTBChairman = TBChairman;
	}

	/**
	 * @return ��� �������� (never null)
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return ������ ������������ ������������� (never null)
	 */
	public String getDepartmentFullName()
	{
		return departmentFullName;
	}

	/**
	 * @return ������ "������������ ��������"
	 */
	public boolean isTBChairman()
	{
		return isTBChairman;
	}
}
