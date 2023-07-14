package com.rssl.auth.csa.back.servises.restrictions.security;

/**
 * @author osminin
 * @ created 17.03.14
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на несовпадение логина с текущим паролем
 */
public class LoginMatchPasswordRestriction extends MatchPasswordRestriction
{
	/**
	 * ctor
	 * @param profileId идентификатор профиля
	 */
	public LoginMatchPasswordRestriction(Long profileId)
	{
		super(profileId);
	}

	@Override
	protected String getMessage()
	{
		return "Пожалуйста, укажите логин, отличающийся от Вашего пароля.";
	}
}
