package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;

/**
 * @author komarov
 * @ created 05.03.2014
 * @ $Author$
 * @ $Revision$
 */
public interface TbNumberRestriction extends Restriction
{
	/**
	 * Проверка возможности работы с подразделением по его родителю
	 * @param tb номер ТБ
	 */
	boolean accept(String tb) throws BusinessException;

}
