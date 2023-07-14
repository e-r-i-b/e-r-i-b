package com.rssl.phizic.business.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.MethodMatcher;
import com.rssl.phizic.business.resources.ResourceType;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

public abstract class OperationsConfig
{
	protected OperationsConfig(){}

	/**
	 * @return ������ ���� ����� ��������
	 */
	public abstract List<ResourceType> getResourceTypes();

	/**
	 * ����� ��� ������� �� ����� ������
	 * @param className ��� ������ �������
	 * @return ��� �������
	 * @throws BusinessException ���� ������ �� ������
	 */
	public abstract ResourceType findResourceType(String className) throws BusinessException;

	/**
	 * ����� ��� ������� �� ����� ������
	 * @param clazz ����� �������
	 * @return ��� �������
	 * @throws BusinessException ���� ������ �� ������
	 */
	public abstract ResourceType findResourceType(Class clazz) throws BusinessException;

	/**
	 * @return ������ ���� ��������
	 */
	public abstract List<OperationDescriptor> getOperationDescriptors();

	/**
	 * ����� �������� �� �� �����
	 * @param key ���� ��������
	 * @return ��������
	 * @throws BusinessException �������� �� �������
	 */
	public abstract OperationDescriptor findOperationByKey(String key) throws BusinessException;

	/**
	 * @return ������ ���� �����
	 */
	public abstract List<Service> getServices();

	/**
	 * ����� ������ �� �����
	 * @param key ���� ������
	 * @return ������
	 * @throws BusinessException ������ �� �������
	 */
	public abstract Service findService(String key) throws BusinessException;

	/**
	 * ������ �������� � ������
	 * @param service ������
	 * @return ������
	 */
	public abstract List<ServiceOperationDescriptor> getServiceOperationDescriptors(Service service);

	/**
	 * ������ ����������� �� ������
	 * @param descriptor �������� �������� ��� �������� ������ �����������
	 * @return ������
	 */
	public abstract List<MethodMatcher> getRemoveMethodMatchers(OperationDescriptor descriptor);
}

