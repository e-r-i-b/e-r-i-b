package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.restrictions.*;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * ќграничение на удовлетворение парол€ дл€ доступа к ћјѕ» требовани€м безопасности.
 *
 * “ребование к формату парол€ на вход в ѕриложение:
 * Х	минимальна€ длина Ц 5 цифр;
 * Х	невозможность задани€ последовательности идущих подр€д цифр как в пр€мом, так и обратном пор€дке, т.е. вида 12345, 23456, 765432;
 * Х	не более 3х одинаковых символов, идущих подр€д.
 */

public class MAPIPasswordSecurityRestriction extends CompositeRestriction<String>
{
	private static final MAPIPasswordSecurityRestriction instance = new MAPIPasswordSecurityRestriction();

	private MAPIPasswordSecurityRestriction()
	{

		super(
				new NotEmptyStringRestriction("ѕароль не может быть пустым"),
				new RegexpRestriction("^.{5,}$", "ѕароль должен содержать не менее 5 символов"),
				new NotSubstringRestriction("01234567890", "ѕароль не может состо€ть из идущих последовательно цифр"),
				new NotSubstringRestriction("09876543210", "ѕароль не может состо€ть из идущих последовательно цифр"),
				new SubsequenceRepeateSymbolsRestriction(3, "ѕароль не должен содержать более 3 повтор€ющихс€ символов подр€д")
		);
	}

	public static MAPIPasswordSecurityRestriction getInstance()
	{
		return instance;
	}

	public void check(String object) throws Exception
	{
		try
		{
			super.check(object);
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