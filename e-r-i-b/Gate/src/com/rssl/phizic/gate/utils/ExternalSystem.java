package com.rssl.phizic.gate.utils;

/**
 * Абстакция внешней системы
 *
 * @author khudyakov
 * @ created 12.03.2012
 * @ $Author$
 * @ $Revision$
 */
public interface ExternalSystem
{
	/**
	 * @return название
	 */
	String getName();

	/**
	 * @return идентификатор внешней системы(в случае прямой интеграции uuid адаптера, в случае шины systemId)
	 */
	String getUUID();

}
