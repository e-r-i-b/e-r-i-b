package com.rssl.phizic.security;

import com.rssl.phizic.person.PersonTask;

/**
 * @author Erkin
 * @ created 12.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Задача, нуждающаяся в получении подтверждения
 */
public interface ConfirmableTask extends PersonTask
{
	/**
	 * Вызывается, когда подтверждение получено
	 */
	void confirmGranted();
}
