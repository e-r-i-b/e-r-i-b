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
	 * @return список всех типов ресурсов
	 */
	public abstract List<ResourceType> getResourceTypes();

	/**
	 * Ќайти тип ресурса по имени класса
	 * @param className им€ класса ресурса
	 * @return тип ресурса
	 * @throws BusinessException если ресурс не найден
	 */
	public abstract ResourceType findResourceType(String className) throws BusinessException;

	/**
	 * Ќайти тип ресурса по имени класса
	 * @param clazz класс ресурса
	 * @return тип ресурса
	 * @throws BusinessException если ресурс не найден
	 */
	public abstract ResourceType findResourceType(Class clazz) throws BusinessException;

	/**
	 * @return список всех операций
	 */
	public abstract List<OperationDescriptor> getOperationDescriptors();

	/**
	 * Ќайти операцию по ее ключу
	 * @param key ключ операции
	 * @return операци€
	 * @throws BusinessException операци€ не найдена
	 */
	public abstract OperationDescriptor findOperationByKey(String key) throws BusinessException;

	/**
	 * @return —писок всех услуг
	 */
	public abstract List<Service> getServices();

	/**
	 * Ќайти услугу по ключу
	 * @param key ключ услуги
	 * @return услуга
	 * @throws BusinessException услуга не найдена
	 */
	public abstract Service findService(String key) throws BusinessException;

	/**
	 * —писок операций в услуге
	 * @param service услуга
	 * @return список
	 */
	public abstract List<ServiceOperationDescriptor> getServiceOperationDescriptors(Service service);

	/**
	 * —писок ограничений на методы
	 * @param descriptor описание операции дл€ которого ищутс€ ограничени€
	 * @return список
	 */
	public abstract List<MethodMatcher> getRemoveMethodMatchers(OperationDescriptor descriptor);
}

