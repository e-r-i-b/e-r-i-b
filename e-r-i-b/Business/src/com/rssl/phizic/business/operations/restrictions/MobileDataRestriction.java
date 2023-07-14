package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.util.Map;

/**
 * ����������� �� ������������� ������ � mAPI
 *
 * @author khudyakov
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */
public interface MobileDataRestriction extends Restriction
{
	/**
	 * ����������� ������ mAPI
	 * @param data ������
	 * @return true - ��� �����������
	 */
	boolean accept(Map<String, Object> data) throws BusinessException, BusinessLogicException;
}
