package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

import java.util.Collection;
import java.util.Collections;

/**
 * Ошибка проверки IMSI
 * @author Jatsky
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 */

public class IMSICheckException extends LogicException
{
	private Collection<String> errorPhones = Collections.emptySet();

	public IMSICheckException(String message, Collection<String> errorPhones)
	{
		super(message);
		this.errorPhones = errorPhones;
	}

	public Collection<String> getErrorPhones()
	{
		return errorPhones;
	}
}
