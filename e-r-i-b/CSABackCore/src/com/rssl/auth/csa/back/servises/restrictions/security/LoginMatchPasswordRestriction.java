package com.rssl.auth.csa.back.servises.restrictions.security;

/**
 * @author osminin
 * @ created 17.03.14
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �� ������������ ������ � ������� �������
 */
public class LoginMatchPasswordRestriction extends MatchPasswordRestriction
{
	/**
	 * ctor
	 * @param profileId ������������� �������
	 */
	public LoginMatchPasswordRestriction(Long profileId)
	{
		super(profileId);
	}

	@Override
	protected String getMessage()
	{
		return "����������, ������� �����, ������������ �� ������ ������.";
	}
}
