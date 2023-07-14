package com.rssl.phizic.business.dictionaries.jbt;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;

/**
 * @author hudyakov
 * @ created 21.06.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class JBTFetcherBase implements JBTFetcher
{
	private static final DepartmentService departmentService = new DepartmentService();

	protected static final String ATTR_DELIMITER = "|";
	protected static final String ATTR_VALUES_DELIMITER = "@";
	protected static String DATESTAMP = "%1$te/%1$tm/%1$tY";
	protected static String ZERO = "0";

	protected SBRFOfficeAdapter getSBRFOfficeAdapterForProvider(ServiceProviderBase provider) throws BusinessException
	{
		Long departmentId = provider.getDepartmentId();
		Department department = departmentService.findById(departmentId);
		return new SBRFOfficeAdapter(department);
	}

	protected static final ServiceProviderService serviceProviderService = new ServiceProviderService();
}
