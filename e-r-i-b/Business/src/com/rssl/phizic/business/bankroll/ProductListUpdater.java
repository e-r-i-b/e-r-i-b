package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.common.types.annotation.NonThreadSafe;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Актуализатор списка продуктов
 */
@NonThreadSafe
interface ProductListUpdater
{
	void updateProductList(boolean forceUpdate);
}
