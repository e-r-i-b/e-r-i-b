package com.rssl.phizic.gate.node;

/**
 * @author osminin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Узел, блок
 */
public interface Node
{
	/**
	 * @return номер блока
	 */
	Long getNumber();

	/**
	 * @return хост листенера вэб-сервиса блока
	 */
	String getListenerHost();
}
