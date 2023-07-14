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
 * ����� ������ ���������� �� ����������
 */

public class PrintServiceProvidersForm extends ActionFormBase
{
	private Long id;
	private ServiceProviderBase provider;
	private Department department;
	private List<PaymentService> services;

	/**
	 * @return ������������� ����������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ����������
	 * @param id �������������
	 */
	@SuppressWarnings("UnusedDeclaration") // ������ ���������� �������
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public ServiceProviderBase getProvider()
	{
		return provider;
	}

	/**
	 * ������ ����������
	 * @param provider ���������
	 */
	public void setProvider(ServiceProviderBase provider)
	{
		this.provider = provider;
	}

	/**
	 * ������ ������������� ����������
	 * @param department �������������
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * @return ������������� ����������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * @return ������, ����������� � ����������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public List<PaymentService> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * ������ ������, ����������� � ����������
	 * @param services ������
	 */
	public void setServices(List<PaymentService> services)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.services = services;
	}
}
