package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.exceptions.LoginRestrictionException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.restrictions.*;
import com.rssl.auth.csa.back.servises.Connector;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * ќграничение на удовлетворение логина требовани€м безопасности.
 *
 * »дентификатор должен состо€ть из букв русского или латинского алфавита и может содержать цифры, например: ivan12.
 * –егистр букв (большие или маленькие) не имеет значени€. ƒлина идентификатора должна быть не менее 5-ти символов.
 * »дентификатор не должен содержать более 3х одинаковых символов подр€д и может содержать следующие символы: У@Ф, У_Ф, У-У, У.Ф
 * “ребовани€ к логину:
 * Х	длина не менее 5 символов;
 * Х	не должен содержать более 3х одинаковых символов подр€д;
 * Х	может содержать элементы пунктуации из списка Ц Ђ Ц @ _ -  .ї
 * Х	не может состо€ть из 10 цифр.
 * Х	не может состо€ть из буквы УZФ и 10 цифр.
 * Х	не должен содержать более 3 символов, расположенных подр€д на клавиатуре, например, qwer. TODO реализовать
 */
public class LoginSecurityRestriction implements Restriction<String>
{
	private static final Restriction<String> statelessRestriction = new CompositeRestriction<String>(
			new NotEmptyStringRestriction("Ћогин не может быть пустым"),
			new NotIpasLoginRestriction("Ћогин не может состо€ть из 10 цифр"),
			new NotDisposableLoginRestriction("Ћогин не может состо€ть из буквы УZФ и 10 цифр."),
			new RegexpRestriction("^[\\d\\-\\.@_a-zA-Z]{5,32}$", "Ћогин должен состо€ть из букв латинского алфавита и может содержать следующие символы: У@Ф, У_Ф, У-У, У.Ф. ƒлина логина должна быть не менее 5-ти символов."),
			new SubsequenceRepeateSymbolsRestriction(3, "Ћогин не может содержать более 3 одинаковых символов подр€д")
	);
	private Restriction<String> restriction;

	private LoginSecurityRestriction(Restriction<String> restriction)
	{
		this.restriction = restriction;
	}

	/**
	 * @return инстанс, не зав€занный на состо€ние (Ѕƒ) и провер€ющий ограничени€ только по значению.
	 */
	public static Restriction<String> getInstance()
	{
		return new LoginSecurityRestriction(statelessRestriction);
	}

	/**
	 * ¬озвращ€ет нстанс, провер€ющий ограничени€ в контексте конкретного коннектора.
	 * ћожет использоватьс€ Ѕƒ дл€ некоторых ограничений. Ќапример, проверки на зан€тость логина
	 * @param connector коннектор, в контексте которого происходи€т проверки
	 * @return инстанс
	 */
	public static Restriction<String> getInstance(Connector connector)
	{
		if (connector == null)
		{
			throw new IllegalArgumentException(" оннектор не может быть null");
		}
		return getInstance();
	}

	public void check(String object) throws Exception
	{
		try
		{
			restriction.check(object);
		}
		catch (LoginRestrictionException e)
		{
			throw e;
		}
		catch (RestrictionException e)
		{
			throw new LoginRestrictionException(e);
		}
	}
}
