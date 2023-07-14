package com.rssl.phizic.business.services.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;

import java.util.List;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ��������� ������ ��������
 */

public class ServicesGroupService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * @return ������ ������ ����� ��������
	 * @throws BusinessException
	 */
	public List<ServicesGroup> getAll() throws BusinessException
	{
		return service.getAll(ServicesGroup.class);
	}

	/**
	 * ��������� ������
	 * @param group ������
	 * @return ����������� ������
	 * @throws BusinessException
	 */
	public ServicesGroup addOrUpdate(ServicesGroup group) throws BusinessException
	{
		return service.addOrUpdate(group);
	}

	/**
	 * ������� ������
	 * @param group ������
	 * @throws BusinessException
	 */
	public void remove(ServicesGroup group) throws BusinessException
	{
		service.remove(group);
	}
}
