package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.MultiNodeEmployeeData;
import org.hibernate.Filter;
import org.hibernate.Session;

/**
 * @author hudyakov
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentServiceProvidersRestriction implements ServiceProvidersRestriction
{

	public boolean accept(ServiceProviderBase provider) throws BusinessException
	{
		Long providerDepartmentId = provider.getDepartmentId();
		if (providerDepartmentId == null)
		{
			return true;
		}
		return AllowedDepartmentsUtil.isDepartmentAllowed(providerDepartmentId);
	}

	public void applyFilter(Session session)
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(!employeeData.isAllTbAccess())
		{
			Filter filter = session.enableFilter("provider_filter_by_department");
			filter.setParameter("employeeLoginId", employeeData.getLoginId());
		}
	}
}
