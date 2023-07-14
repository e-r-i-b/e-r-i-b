package com.rssl.phizic.gorod.messaging;

/**
 * @author Omeliyanchuk
 * @ created 16.03.2011
 * @ $Author$
 * @ $Revision$
 */

public interface GorodConfigImplMBean
{
	/**
	 * Использовать ли заглушку
	 * @return true - да.
	 */
	boolean IsMock();

	/**
	 *
	 * @return хост системы город
	 */
	String getHost();

	/**
	 *
	 * @return Порт системы город
	 */
	int getPort();

	/**
	 *
	 * @return ПАН технл. пользователя
	 */
	String getPAN();

	/**
	 *
	 * @return ПИН технл. пользователя.
	 */
	String getPIN();

	/**
	 *
	 * @return
	 */
	String getPrefix();


	/**
	 *
	 * @return
	 */
	String getPostFix();

	/**
	 *
	 * @return ПАН клиентской карты
	 */
	String getClientPan();

}
