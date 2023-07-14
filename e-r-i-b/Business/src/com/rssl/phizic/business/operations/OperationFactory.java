package com.rssl.phizic.business.operations;

import com.rssl.phizic.business.BusinessException;

import java.security.AccessControlException;

public interface OperationFactory
{
	/**
	 * ������� ������� ��������.
	 *
	 * @param operationClass - ���������� ��� �������� (�� ����� ������ (��� ����� ��������) ����������� ����)
	 * @param serviceKey - ���� ��� ������ ������
	 * @return ��������
	 * @throws AccessControlException - ��� �������
	 * @throws BusinessException
	 */
	<T extends Operation> T create(Class<T> operationClass, String serviceKey) throws AccessControlException, BusinessException;

	/**
	 * ������� ������� ��������.
	 *
	 * @param operationKey - ���� ��������
	 * @param serviceKey   - ���� ��� ������ ������
	 * @return ��������
	 * @throws AccessControlException - ��� �������
	 * @throws BusinessException
	 */
	<T extends Operation> T create(String operationKey, String serviceKey) throws AccessControlException, BusinessException;

	/**
	 * ������� ������� �������� (������ spreaded).
	 * @param operationClass - ���������� ��� ��������
	 * @return ��������
	 * @throws AccessControlException - ��� �������
	 * @throws BusinessException
	 */
	<T extends Operation> T create(Class<T> operationClass) throws AccessControlException, BusinessException;

	/**
	 * ������� ������� �������� (������ spreaded).
	 *
	 * @param operationKey - ���� ��������
	 * @return ��������
	 * @throws AccessControlException - ��� �������
	 * @throws BusinessException
	 */
	<T extends Operation> T create(String operationKey) throws AccessControlException, BusinessException;

	/**
	 * ����� �� ������������ ���������� ��������
	 * @param operationClass - ���������� ��� �������� (�� ����� ������ (��� ����� ��������) ����������� ����)
	 * @param serviceKey - ���� ��� ������ ������
	 * @return true = ������ ����
	 * @throws BusinessException
	 */
	boolean checkAccess(Class operationClass, String serviceKey) throws BusinessException;

	/**
	 * ����� �� ������������ ���������� ��������  (������ ��� spread="true")
	 * @param operationClass - ���������� ��� ��������
	 * @return true = ������ ����
	 * @throws BusinessException
	 */
	boolean checkAccess(Class operationClass) throws BusinessException;

	/**
	 * ����� �� ������������ ���������� �������� (������ ��� spread="true")
	 *
	 * @param operationKey - ���� ��������
	 * @return true = ������ ����
	 * @throws BusinessException
	 */
	boolean checkAccess(String operationKey) throws BusinessException;

	/**
	 * ����� �� ������������ ���������� ��������
	 *
	 * @param operationKey - ���� ��������
	 * @param serviceKey - ���� ��� ������ ������
	 * @return true = ������ ����
	 * @throws BusinessException
	 */
	boolean checkAccess(String operationKey, String serviceKey) throws BusinessException;
}
