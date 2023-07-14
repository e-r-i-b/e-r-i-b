package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

/**
 * @author Erkin
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 *  онфиг по бановскому продукту определЄнного вида (счЄт, карта и т.п.)
 */
@ThreadSafe
public interface BankrollConfig 
{
	/**
	 * @return true - продукты данного вида используетс€ (в канале)
	 */
	boolean isProductUsed();

	/**
	 * @return промежуток времени (секунды),
	 * в течении которого список продуктов данного вида считаетс€ актуальным.
	 * ѕо истечении времени выполн€етс€ обновление списка продуктов.
	 */
	long getProductListLifetime();
}
