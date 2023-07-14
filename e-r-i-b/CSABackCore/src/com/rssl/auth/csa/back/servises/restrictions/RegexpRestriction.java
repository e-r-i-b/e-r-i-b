package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.auth.csa.back.exceptions.RestrictionException;

import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * ����������� �� �������������� ����������� ���������
 */
public class RegexpRestriction implements Restriction<String>
{
	private String message;
	private Pattern pattern;

	/**
	 * @param regexp ���������� ��������� ��� ��������
	 * @param message ��������� � ������ ������������ �����������
	 */
	public RegexpRestriction(String regexp, String message)
	{
		pattern = Pattern.compile(regexp);
		this.message = message;
	}

	public void check(String object) throws RestrictionException
	{
		if (!pattern.matcher(object).matches())
		{
			throw new RestrictionException(message);
		}
	}
}
