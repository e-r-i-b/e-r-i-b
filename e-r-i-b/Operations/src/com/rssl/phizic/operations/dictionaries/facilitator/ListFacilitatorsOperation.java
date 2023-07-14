package com.rssl.phizic.operations.dictionaries.facilitator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * �������� ��� ��������� ������ �������������
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListFacilitatorsOperation extends OperationBase implements ListEntitiesOperation
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private static final String CSA_ADMIN_DB_NAME = "CSAAdmin";

	protected String getInstanceName()
	{
		return CSA_ADMIN_DB_NAME;
	}

	/**
	 * ��������� ������ ��� �� ����� �/��� ���.
	 * @param name - ���
	 * @param inn = ���
	 * @param firstResult - ������ �
	 * @param maxResult - ���������� �������
	 * @return ������ ���
	 */
	public List<FacilitatorProvider> getEndpointProviderList(String name, String inn, int firstResult, int maxResult)
	{
		ShopOrderService shopOrderService = GateSingleton.getFactory().service(ShopOrderService.class);
		try
		{
			return shopOrderService.findEndPointProviderByName(name, inn, firstResult, maxResult);
		}
		catch (GateException e)
		{
			log.error("������ ��� ��������� ������ �������� �����������", e);
			return new ArrayList<FacilitatorProvider>();
		}
		catch (GateLogicException e)
		{
			log.error("������ ��� ��������� ������ �������� �����������", e);
			return new ArrayList<FacilitatorProvider>();
		}
	}

	/**
	 * ��������� �������������� ����������-������������ � ������������ �� ��� ����
	 * @param code - ��� ���������� �����
	 * @return ������������� � ������������
	 */
	public Object[] getFacilitatorIdAndNameByCode(String code)
	{
		try
		{
			List<Object[]> resultList = new ArrayList<Object[]>();
			resultList = serviceProviderService.getFacilitatorIdAndNameByCode(code, getInstanceName());
			if (CollectionUtils.isNotEmpty(resultList))
				return resultList.get(0);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ������������ �� ����", e);
			return null;
		}
		return null;
	}
}
