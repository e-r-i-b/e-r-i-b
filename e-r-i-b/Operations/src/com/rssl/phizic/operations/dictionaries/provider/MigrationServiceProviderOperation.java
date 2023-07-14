package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;

/**
 * @author basharin
 * @ created 02.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class MigrationServiceProviderOperation extends EditServiceProvidersOperationBase
{
	public void initialize(Long id) throws BusinessException
	{
		ServiceProviderBase temp = providerService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("��������� ����� � id = " + id + " �� ������");

		provider = temp;
	}

	public ServiceProviderBase getEntity()
	{
		return provider;
	}

	/**
	 * ������������� ��� ����� ��������
	 */
	public void migration() throws BusinessLogicException, BusinessException
	{
		if (ServiceProviderState.MIGRATION == provider.getState())
		{
			throw new BusinessLogicException("������ ��������� ��� ��������� � ������ ��. ����������, �������� ������� ����������.");
		}
		executeTransactional(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				provider.setState(ServiceProviderState.MIGRATION);
				providerService.addOrUpdate(provider, getInstanceName());
				clearEntitiesListCache();
				return null;
			}
		});
	}

}