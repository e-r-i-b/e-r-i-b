package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;

/**
 * Проверка возможности работы с автоплатежом
 * @author basharin
 * @ created 16.01.2012
 * @ $Author$
 * @ $Revision$
 */

public interface AutoPaymentRestriction  extends Restriction
{
	/**
	* Проверка возможности работы с автоплатежом
	* @param link автоплатеж
	*/
	boolean accept(AutoPaymentLink link) throws BusinessException;
}
