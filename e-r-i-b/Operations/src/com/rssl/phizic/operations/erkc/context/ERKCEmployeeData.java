package com.rssl.phizic.operations.erkc.context;

import com.rssl.phizic.gate.csa.ProfileWithFullNodeInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author akrenev
 * @ created 11.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ����������� ��� ������ ���������� ����
 */

public class ERKCEmployeeData implements Serializable
{
	private ProfileWithFullNodeInfo csaPersonInfo;
	private Functional currentFunctional;
	private Map<String, Object> userInfo;
	private Long currentPersonId;
	private Long userLoginId;
	private Map<String, Object> additionalParameters;

	/**
	 * @return ���������� � ������� ������� (�� ���)
	 */
	public ProfileWithFullNodeInfo getCsaPersonInfo()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return csaPersonInfo;
	}

	/**
	 * ������ ���������� � ������� ������� (�� ���)
	 * @param csaPersonInfo ���������� � ������� �������
	 */
	public void setCsaPersonInfo(ProfileWithFullNodeInfo csaPersonInfo)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.csaPersonInfo = csaPersonInfo;
	}

	/**
	 * @return ������� ����������, � ������� �������� ���������
	 */
	public Functional getCurrentFunctional()
	{
		return currentFunctional;
	}

	/**
	 * ������ ������� ����������, � ������� �������� ���������
	 * @param currentFunctional ����������, � ������� �������� ���������
	 */
	public void setCurrentFunctional(Functional currentFunctional)
	{
		this.currentFunctional = currentFunctional;
	}

	/**
	 * @return ���������� � ������� (�������)
	 */
	public Map<String, Object> getUserInfo()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return userInfo;
	}

	/**
	 * ������ ���������� � ������� (�������)
	 * @param userInfo ���������� � �������
	 */
	public void setUserInfo(Map<String, Object> userInfo)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.userInfo = userInfo;
	}

	/**
	 * @return ������������� �������
	 */
	public Long getCurrentPersonId()
	{
		return currentPersonId;
	}

	/**
	 * ������ ������������� �������
	 * @param currentPersonId ������������� �������
	 */
	public void setCurrentPersonId(Long currentPersonId)
	{
		this.currentPersonId = currentPersonId;
	}

	/**
	 * @return ������������� ������ �������
	 */
	public Long getUserLoginId()
	{
		return userLoginId;
	}

	/**
	 * ������ ������������� ������ �������
	 * @param userLoginId �������������
	 */
	public void setUserLoginId(Long userLoginId)
	{
		this.userLoginId = userLoginId;
	}

	/**
	 * @return �������������� ���������
	 */
	public Map<String, Object> getAdditionalParameters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return additionalParameters;
	}

	/**
	 * ������ �������������� ���������
	 * @param additionalParameters ���������
	 */
	public void setAdditionalParameters(Map<String, Object> additionalParameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.additionalParameters = additionalParameters;
	}
}
