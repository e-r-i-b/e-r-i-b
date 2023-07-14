package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.util.Map;

/**
 * Ограничение на использование данных в mAPI
 *
 * @author khudyakov
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */
public interface MobileDataRestriction extends Restriction
{
	/**
	 * Ограничение версии mAPI
	 * @param data версия
	 * @return true - нет ограничения
	 */
	boolean accept(Map<String, Object> data) throws BusinessException, BusinessLogicException;
}
