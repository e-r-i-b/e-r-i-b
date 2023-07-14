package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.Service;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 19.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3357 $
 */

public interface SchemeOperationHelper
{
	/**
	 * @return ������ ��������� �����
	 */
	List<Service> getServices () throws BusinessException;

	/**
	 * @return ���������
	 */
	String getCategory ();

	/**
	 * @param serviceId ID ������
	 * @return ������
	 */
	Service findById ( Long serviceId ) throws BusinessException;
}
