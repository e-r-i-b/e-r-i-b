package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;

import java.util.List;

/**
 * @author hudyakov
 * @ created 11.09.2009
 * @ $Author$
 * @ $Revision$
 */

public class GetMultiDepartmentOfficesOperation extends GetOfficesOperation
{
	private static final AdapterService adapterService = new AdapterService();

	protected Adapter adapter;

	public void initialize(Long adapterId) throws BusinessException, BusinessLogicException
	{
		try
		{
			adapter = adapterService.getAdapterById(adapterId);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		if (adapter == null)
		{
			throw new BusinessLogicException("�� ������� ������� ������� � �������������� " + adapterId);
		}
	}

	public Adapter getAdapter()
	{
		return adapter;
	}

	public List<Office> getOffices(Office template, int firstResult, int listLimit) throws BusinessException, BusinessLogicException
	{
		try
		{
			//��� ��������� uuid �������� ������ �������� �� ���������� ������� �������
			String uuid = ExternalSystemHelper.getCode(adapter.getUUID());
			OfficeGateService service = GateManager.getInstance().getService(uuid, OfficeGateService.class);
			return service.getAll(template, firstResult, listLimit);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
