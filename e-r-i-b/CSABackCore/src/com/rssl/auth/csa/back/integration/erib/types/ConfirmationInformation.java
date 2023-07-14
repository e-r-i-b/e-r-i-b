package com.rssl.auth.csa.back.integration.erib.types;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 *  ���������� � �������������
 */

@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class ConfirmationInformation
{
	private ConfirmStrategyType confirmStrategyType;
	private List<CardInformation> confirmationSources;
	private boolean pushAllowed;

	/**
	 * @return ���������������� ������ �������������
	 */
	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * ������ ���������������� ������ �������������
	 * @param confirmStrategyType ���������������� ������ �������������
	 */
	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	/**
	 * ������ ���������������� ������ ������������� (�� ������)
	 * @param confirmStrategyType ���������������� ������ �������������
	 */
	public void setConfirmStrategyType(String confirmStrategyType)
	{
		this.confirmStrategyType = ConfirmStrategyType.valueOf(confirmStrategyType);
	}

	/**
	 * @return ��������� �������������
	 */
	public List<CardInformation> getConfirmationSources()
	{
		return confirmationSources;
	}

	/**
	 * ������ ��������� �������������
	 * @param confirmationSources ��������� �������������
	 */
	public void setConfirmationSources(List<CardInformation> confirmationSources)
	{
		this.confirmationSources = confirmationSources;
	}

	/**
	 * @return �������� �� ������������� �� push
	 */
	public boolean isPushAllowed()
	{
		return pushAllowed;
	}

	/**
	 * ������ �������� �� ������������� �� push
	 * @param pushAllowed �������� �� ������������� �� push
	 */
	public void setPushAllowed(boolean pushAllowed)
	{
		this.pushAllowed = pushAllowed;
	}
}
