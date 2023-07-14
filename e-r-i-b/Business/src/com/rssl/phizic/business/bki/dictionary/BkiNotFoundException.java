package com.rssl.phizic.business.bki.dictionary;

import com.rssl.phizic.business.BusinessException;

/**
 * User: Moshenko
 * Date: 02.10.14
 * Time: 17:54
 * ������ �����������  ���  �� ������.
 */
public class BkiNotFoundException extends BusinessException
{
	public BkiNotFoundException(String message)
	{
		super(message);
	}

	public BkiNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public BkiNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
