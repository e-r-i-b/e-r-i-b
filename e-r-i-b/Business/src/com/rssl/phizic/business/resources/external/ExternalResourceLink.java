package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.Resource;
import com.rssl.phizic.common.types.Currency;

import java.io.Serializable;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 12.10.2005 Time: 12:23:52 */
public interface ExternalResourceLink extends Resource, Serializable
{
	/**
	 * @return ������������ �������� �� ������� �������, �� ������� ��������� Link
	 */
    String getExternalId();

	/**
	 * @return ����� ���������, � �������� ��������� ������ �� ��������.
	 */
	Long getLoginId();

	/**
	 * @return �������� �� �������� �������, �� ������� ��������� Link
	 */
    Object getValue() throws BusinessException, BusinessLogicException;

	String getNumber();

	Currency getCurrency();

	String getName();

	String getDescription();

	/**
	 * @return ���������� � ������� ���� (�����������) ������ ���
	 */
	String getCode();
	/**
	 * �������� ��������� �����.
	 * �������� ��� � ��
	 */
	void reset() throws BusinessLogicException, BusinessException;

	/**
	 * ��� ��������(����, ���. ���� � �.�)
	 * @return
	 */
    ResourceType getResourceType();

	/**
	 * @return true, ���� ���� �������� ��� �������������
	 */
	Boolean getShowInSystem();

	/**
	 * @return ���������� ������
	 */
	String getPatternForFavouriteLink();

	/**
	 * 
	 * @return ���������� ����� ������� ��������
	 */
	public Class getStoredResourceType();
}
