package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;

/**
 * @author Gulov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Условие отображения главного пункта меню.
 */
public interface MenuLinkCondition
{
	/**
	 * Проверяет условие отображения главного пункта меню.
	 * @return true - отображать, false - нет
	 * @throws BusinessException
	 */
	boolean accept() throws BusinessException;
}
