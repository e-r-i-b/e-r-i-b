package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.DepartmentServiceProvidersRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

import java.util.List;

/**
 * @author hudyakov
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 *
 * Операция печати информации по поставщику
 */

public class PrintServiceProvidersOperation extends OperationBase<DepartmentServiceProvidersRestriction> implements ViewEntityOperation<ServiceProviderBase>
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();

	private ServiceProviderBase provider;
	private Department department;
	private List<PaymentService> services;

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	/**
	 * инициализация операции
	 * @param id идентификатор поставщика
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		ServiceProviderBase temp = providerService.findById(id, getInstanceName());
	    if (temp == null)
	        throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		DepartmentServiceProvidersRestriction restriction = getRestriction();
		if (!restriction.accept(temp))
			throw new RestrictionViolationException("Поставщик услуг Code = " + temp.getCode());

		provider = temp;
		department = departmentService.findById(provider.getDepartmentId(), getInstanceName());

		services = paymentServiceService.getServicesForProvider(id, getInstanceName());
	}

	public ServiceProviderBase getEntity()
	{
		return provider;
	}

	/**
	 * @return подразделение поставщика
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * @return услуги, привязанные к поставщику
	 */
	public List<PaymentService> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}
}
