package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

/**
 * @author Erkin
 * @ created 17.09.2012
 * @ $Author$
 * @ $Revision$
 */
interface RegistrationForm
{
	/**
	 * ¬озвращает адрес страницы,
	 * куда нужно перейти по завершению регистрации или в случае еЄ отмены
	 * @return адрес возврата или null, если не задана
	 */
	public String getReturnURL();

	public void setReturnURL(String returnURL);
}
