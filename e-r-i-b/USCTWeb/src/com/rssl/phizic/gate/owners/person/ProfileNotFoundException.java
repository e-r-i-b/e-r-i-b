package com.rssl.phizic.gate.owners.person;

/**
 * Исключение, бросаемое в случае отсутствия клиента в БД ЕСУШ
 *
 * @author khudyakov
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ProfileNotFoundException extends Exception
{
	private static final String ERROR_MESSAGE = "клиент не найден в БД ЕСУШ";

	public ProfileNotFoundException()
	{
		super(ERROR_MESSAGE);
	}
}
