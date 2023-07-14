package com.rssl.phizic.gate.depo;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о позиции по счету ДЕПО
 */
public interface DepoAccountPosition extends Serializable
{
	/**
	 * Список разделов счета ДЕПО
	 * @return depoAccountDivision
	 */
	List<DepoAccountDivision> getDepoAccountDivisions();
}
