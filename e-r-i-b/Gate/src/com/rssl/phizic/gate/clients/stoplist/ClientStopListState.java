package com.rssl.phizic.gate.clients.stoplist;

/**
 * @author Omeliyanchuk
 * @ created 28.04.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статус клиента в стоп листе.
 */
public enum ClientStopListState
{
		trusted, // абсолютно честный человек
		shady,   // содержится в предупреждающих стоп-листах
		blocked  // содержится в блокирующем стоп-листе
}
