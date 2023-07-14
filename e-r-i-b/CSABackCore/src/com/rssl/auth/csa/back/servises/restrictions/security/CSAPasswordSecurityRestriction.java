package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.restrictions.*;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на удовлетворение пароля требованиям безопасности.
 *
 *	длина не менее 8 символов;
 *	должен содержать минимум 1 цифру;
 *	не должен содержать более 3х одинаковых символов подряд;
 *	может содержать элементы пунктуации из списка – « – ! @ # $ % ^ & * ( ) _ - + : ; , .»
 */

public class CSAPasswordSecurityRestriction implements Restriction<String>
{
	private static final Restriction<String> statelessRestriction = new CompositeRestriction<String>(
			new NotEmptyStringRestriction("Пароль не может быть пустым."),
			new RegexpRestriction("^[\\d\\-\\.\\+\\(\\):;,!#$@%^&*_a-zA-Z]{8,30}$", "Пароль содержит недопустимые символы."),
			new SubsequenceRepeateSymbolsRestriction(3, "Пароль не должен содержать более 3 повторяющихся символов подряд.")
	);
	private Restriction<String> restriction;

	private CSAPasswordSecurityRestriction(Restriction<String> restriction)
	{
		this.restriction = restriction;
	}

	/**
	 * @return инстанс, не завязанный на состояние (БД) и проверяющий ограничения только по значению.
	 */
	public static CSAPasswordSecurityRestriction getInstance()
	{
		return new CSAPasswordSecurityRestriction(statelessRestriction);
	}

	/**
	 * Возвращяет инстанс, проверяющий ограничения в контексте конкретного гостевого профиля.
	 * Может использоваться БД для некоторых ограничений. Например, проверки на повторяемость пароля за диапазон
	 * @param profile гостевой профиль, в контексте которого происходият проверки
	 * @return инстанс
	 */
	public static Restriction<String> getInstance(GuestProfile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Коннектор не може тбыть null");
		}
		return new CSAPasswordSecurityRestriction(new CompositeRestriction<String>
				(
						new NotEquilsRestriction<String>(profile.getLogin().toUpperCase(), "Пароль не может совпадать с логином"),
						statelessRestriction,
						new GuestPasswordHistoryRestriction(profile, 3)
				));
	}

	/**
	 * Возвращяет нстанс, проверяющий ограничения в контексте конкретного коннектора.
	 * Может использоваться БД для некоторых ограничений. Например, проверки на повторяемость пароля за диапазон
	 * @param connector коннектор, в контексте которого происходият проверки
	 * @return инстанс
	 */
	public static Restriction<String> getInstance(CSAConnector connector) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("Коннектор не може тбыть null");
		}
		return new CSAPasswordSecurityRestriction(new CompositeRestriction<String>
				(
						new NotEquilsRestriction<String>(connector.getLogin().toUpperCase(), "Пароль не может совпадать с логином"),
						statelessRestriction,
						new PasswordHistoryRestriction(connector.getProfile(), 3)
				));
	}

	/**
	 * Возвращяет инстанс, проверяющий ограничения в контексте конкретного профиля.
	 * Может использоваться БД для некоторых ограничений. Например, проверки на повторяемость пароля за диапазон
	 * @param profile профиль, в контексте которого происходият проверки
	 * @return инстанс
	 */
	public static Restriction<String> getInstance(Profile profile)
	{
		return new CSAPasswordSecurityRestriction(new CompositeRestriction<String>
				(
						statelessRestriction,
						new PasswordHistoryRestriction(profile, 3)
				));
	}

	public void check(String object) throws Exception
	{
		try
		{
			restriction.check(object.toUpperCase());
		}
		catch (PasswordRestrictionException e)
		{
			throw e;
		}
		catch (RestrictionException e)
		{
			throw new PasswordRestrictionException(e);
		}
	}
}