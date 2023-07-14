package com.rssl.phizic.gate.depo;

import java.util.List;

/**
 * @author mihaylov
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о ценной бумаге на счете депо
 */
public interface DepoAccountSecurity extends SecurityBase
{
	/**
	 * @return список маркеров для данной ценной бумаги
	 */
	List<SecurityMarker> getSecurityMarkers();

	/**
	 * Остаток, кол-во ценных бумаг на счете(шт.)
	 * @return remainder
	 */
	Long getRemainder();

	/**
	 * Метод хранения ценной бумаги на счете ДЕПО
	 * @return storageMethod
	 */
	DepoAccountSecurityStorageMethod getStorageMethod();
}
