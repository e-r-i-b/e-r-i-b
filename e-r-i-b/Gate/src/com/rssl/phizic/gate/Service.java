package com.rssl.phizic.gate;

/**
 * Сервис предосавляемый гейтом.
 * Реализация долна быть потокобезопасна.
 * @author Evgrafov
 * @ created 19.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2027 $
 */
public interface Service
{
	/**
	 * @return фабрика, создавшая сервис
	 */
	GateFactory getFactory();
}
