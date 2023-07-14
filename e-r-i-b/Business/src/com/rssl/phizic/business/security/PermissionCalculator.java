package com.rssl.phizic.business.security;

/**
 * ����������� �������� ����
 *
 * @author khudyakov
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PermissionCalculator
{
	/**
	 * �������� �� ����������� ��������
	 * @param serviceKey ������
	 * @param operationKey ��������
	 * @param rigid
	 * @return true - ��������
	 */
	boolean impliesOperation(String serviceKey, String operationKey, boolean rigid);

	/**
	 * �������� �� ����������� �������
	 * @param serviceKey ������
	 * @param rigid
	 * @return true - ��������
	 */
	boolean impliesService(String serviceKey, boolean rigid);

	/**
	 * �������� �� ����������� �����
	 * @param schemeKey �����
	 * @return true - ��������
	 */
	boolean impliesAccessScheme(String schemeKey);
}
