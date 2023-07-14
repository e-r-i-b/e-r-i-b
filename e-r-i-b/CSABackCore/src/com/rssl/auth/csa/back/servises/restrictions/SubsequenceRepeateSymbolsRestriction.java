package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * ����������� �� ����������� ���������� ���������� ������������� ��������
 */
public class SubsequenceRepeateSymbolsRestriction implements Restriction<String>
{
	private int maxCount;
	private String message;

	/**
	 * @param maxCount ����������� ���������� ���������� ������������� ��������
	 * @param message ��������� � ������ ������������ �����������
	 */
	public SubsequenceRepeateSymbolsRestriction(int maxCount, String message)
	{
		this.maxCount = maxCount;
		this.message = message;
	}

	public void check(String object) throws Exception
	{
		if (StringHelper.maxCountRepeateChar(object, false) > maxCount)
		{
			throw new RestrictionException(message);
		}
	}
}
