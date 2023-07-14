package com.rssl.phizic.gate.ips;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип карточной операции
 * Запрашивает шлюз из основного приложения для построения объекта CardOperation
 */
public interface CardOperationType extends Serializable
{
	/**
	 * @return кодовый номер типа
	 */
	long getCode();

	/**
	 * @return признак "операция с наличными / с безналичными средствами"
	 */
	boolean isCash();
}
