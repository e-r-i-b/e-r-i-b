package com.rssl.phizic.gate;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 22.12.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о системных настройках шлюза.
 */
public interface GateConfiguration extends Serializable
{
	/**
	 * имя пользователя, от имени которого устанавливается соединение с внешней системой
	 *
	 * @return имя пользователя, от имени которого устанавливается соединение с внешней системой
	 * */
	String getUserName();

	/**
	 * время ожидания ответа от внешней системы в миллисекундах.
	 *
	 * @return  время ожидания ответа от внешней системы в миллисекундах
	 * */
	Long getConnectionTimeout();

	/**
	 * режим работы с внешней системой: асинхронный или синхронный
	 *
	 * @return режим работы с внешней системой: асинхронный или синхронный
	 * */
	ConnectMode getConnectMode();
}