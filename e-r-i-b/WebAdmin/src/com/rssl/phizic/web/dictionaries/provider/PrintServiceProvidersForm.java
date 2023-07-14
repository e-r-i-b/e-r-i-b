package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author hudyakov
 * @ created 18.01.2010
 * @ $Author$
 * @ $Revision$
 *
 * Форма печати информации по поставщику
 */

public class PrintServiceProvidersForm extends ActionFormBase
{
	private Long id;
	private ServiceProviderBase provider;
	private Department department;
	private List<PaymentService> services;

	/**
	 * @return идентификатор поставшика
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор поставшика
	 * @param id идентификатор
	 */
	@SuppressWarnings("UnusedDeclaration") // стратс обработчик запроса
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return поставщик
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public ServiceProviderBase getProvider()
	{
		return provider;
	}

	/**
	 * задать поставщика
	 * @param provider поставщик
	 */
	public void setProvider(ServiceProviderBase provider)
	{
		this.provider = provider;
	}

	/**
	 * задать подразделение поставщика
	 * @param department подразделение
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * @return подразделение поставщика
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * @return услуги, привязанные к поставщику
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public List<PaymentService> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * задать услуги, привязанные к поставщику
	 * @param services услуги
	 */
	public void setServices(List<PaymentService> services)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.services = services;
	}
}
