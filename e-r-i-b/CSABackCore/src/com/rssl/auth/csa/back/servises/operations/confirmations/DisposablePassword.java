package com.rssl.auth.csa.back.servises.operations.confirmations;

import com.rssl.auth.csa.back.servises.Password;

import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * одноразовый пароль
 */

public class DisposablePassword extends Password
{
	private Long confirmErrors = 0L;

	/**
	 * конструктор (для гибернета)
	 */
	public DisposablePassword()
	{}

	/**
	 * конструктор генерящий информациб о пароле
	 * @param code пароль
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public DisposablePassword(String code) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		super(code);
	}

	/**
	 * @return количество ошибок подтверждения
	 */
	public Long getConfirmErrors()
	{
		return confirmErrors;
	}

	/**
	 * задать количество ошибок подтверждения
	 * @param confirmErrors количество ошибок подтверждения
	 */
	public void setConfirmErrors(Long confirmErrors)
	{
		this.confirmErrors = confirmErrors;
	}

	/**
	 * увеличить количество ошибок подтверждения 
	 */
	public void incErrorCount()
	{
		confirmErrors++;
	}
}
