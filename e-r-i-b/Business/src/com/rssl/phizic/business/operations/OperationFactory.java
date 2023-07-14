package com.rssl.phizic.business.operations;

import com.rssl.phizic.business.BusinessException;

import java.security.AccessControlException;

public interface OperationFactory
{
	/**
	 * Создает инстанс операции.
	 *
	 * @param operationClass - конкретный тип операции (по имени класса (без учета пакаджей) вычисляется ключ)
	 * @param serviceKey - ключ для поиска услуги
	 * @return операция
	 * @throws AccessControlException - нет доступа
	 * @throws BusinessException
	 */
	<T extends Operation> T create(Class<T> operationClass, String serviceKey) throws AccessControlException, BusinessException;

	/**
	 * Создает инстанс операции.
	 *
	 * @param operationKey - ключ операции
	 * @param serviceKey   - ключ для поиска услуги
	 * @return операция
	 * @throws AccessControlException - нет доступа
	 * @throws BusinessException
	 */
	<T extends Operation> T create(String operationKey, String serviceKey) throws AccessControlException, BusinessException;

	/**
	 * Создает инстанс операции (только spreaded).
	 * @param operationClass - конкретный тип операции
	 * @return операция
	 * @throws AccessControlException - нет доступа
	 * @throws BusinessException
	 */
	<T extends Operation> T create(Class<T> operationClass) throws AccessControlException, BusinessException;

	/**
	 * Создает инстанс операции (только spreaded).
	 *
	 * @param operationKey - ключ операции
	 * @return операция
	 * @throws AccessControlException - нет доступа
	 * @throws BusinessException
	 */
	<T extends Operation> T create(String operationKey) throws AccessControlException, BusinessException;

	/**
	 * Опрос на допустимость выполнения операции
	 * @param operationClass - конкретный тип операции (по имени класса (без учета пакаджей) вычисляется ключ)
	 * @param serviceKey - ключ для поиска услуги
	 * @return true = доступ есть
	 * @throws BusinessException
	 */
	boolean checkAccess(Class operationClass, String serviceKey) throws BusinessException;

	/**
	 * Опрос на допустимость выполнения операции  (только для spread="true")
	 * @param operationClass - конкретный тип операции
	 * @return true = доступ есть
	 * @throws BusinessException
	 */
	boolean checkAccess(Class operationClass) throws BusinessException;

	/**
	 * Опрос на допустимость выполнения операции (только для spread="true")
	 *
	 * @param operationKey - ключ операции
	 * @return true = доступ есть
	 * @throws BusinessException
	 */
	boolean checkAccess(String operationKey) throws BusinessException;

	/**
	 * Опрос на допустимость выполнения операции
	 *
	 * @param operationKey - ключ операции
	 * @param serviceKey - ключ для поиска услуги
	 * @return true = доступ есть
	 * @throws BusinessException
	 */
	boolean checkAccess(String operationKey, String serviceKey) throws BusinessException;
}
