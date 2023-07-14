package com.rssl.auth.csa.wsclient.requests;

/**
 * Запрос на выполенние операции восстановления пароля для гостя
 * @author niculichev
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FinishGuestRestorePasswordRequestData extends FinishRestorePasswordRequestData
{
	private static final String GUEST_REQUEST_NAME = "finishGuestRestorePasswordRq";

	/**
	 * ctor
	 * @param ouid идентификатор операции
	 * @param password новый пароль
	 */
	public FinishGuestRestorePasswordRequestData(String ouid, String password)
	{
		super(ouid, password);
	}

	public String getName()
	{
		return GUEST_REQUEST_NAME;
	}
}
