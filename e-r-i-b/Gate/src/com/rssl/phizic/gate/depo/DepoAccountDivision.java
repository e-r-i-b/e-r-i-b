package com.rssl.phizic.gate.depo;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о разделе счета ДЕПО
 */
public interface DepoAccountDivision extends Serializable
{
	/**
	 * @return тип раздела счета ДЕПО
	 */
	String getDivisionType();

	/**
	 * @return номер раздела счета ДЕПО
	 */
	String getDivisionNumber();

	/**	 
	 * @return Ценные бумаги раздела
	 */
	List<DepoAccountSecurity> getDepoAccountSecurities();
}
