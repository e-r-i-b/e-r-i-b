package com.rssl.auth.csa.back.servises;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public enum LoginType
{
	ERKC_REG,   //логин создан при регистрации клиента в ЕРКЦ
	SELF_REG,   //логин создан при самостоятельной регистрации
	TERMINAL,   //логин не создавался и получен из iPas.
	AUTO,       //логин создан автоматически при первом входе по новому ipas логину
}
