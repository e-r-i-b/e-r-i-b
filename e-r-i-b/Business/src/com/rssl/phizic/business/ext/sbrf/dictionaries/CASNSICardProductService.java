package com.rssl.phizic.business.ext.sbrf.dictionaries;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Mescheryakova
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � �������, ������������ �� ����������� ��� ���
 */

public class CASNSICardProductService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������� ��� ��������� ���������� �������� � ��
	 * @param obj   - �������� ��� ����������
	 * @return      - ����������� ��������
	 * @throws BusinessException
	 */
	public CASNSICardProduct addOrUpdate(final CASNSICardProduct obj) throws BusinessException
	{
		return simpleService.addOrUpdate(obj, null);
	}

	/**
	 * ����� ����� �� ����������� ��� ��� �� id
	 * @throws BusinessException
	 */
	public CASNSICardProduct findById(long id) throws BusinessException
	{
		return simpleService.findById(CASNSICardProduct.class, id);
	}
}
