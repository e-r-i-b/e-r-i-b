package com.rssl.phizic.gate.confirmation;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ������������� �������� ��������
 */

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class ConfirmationInfoImpl implements ConfirmationInfo
{
	private ConfirmStrategyType confirmStrategyType;
	private List<CardConfirmationSource> confirmationSources;
	private boolean pushAllowed;

	/**
	 * �����������
	 */
	public ConfirmationInfoImpl(){}

	/**
	 * �����������
	 * @param confirmStrategyType ���������������� ������ �������������
	 * @param confirmationSources ��������� ������������� (�����)
	 */
	public ConfirmationInfoImpl(ConfirmStrategyType confirmStrategyType, List<CardConfirmationSource> confirmationSources, boolean pushAllowed)
	{
		this.confirmStrategyType = confirmStrategyType;
		this.confirmationSources = confirmationSources;
		this.pushAllowed = pushAllowed;
	}

	/**
	 * ������ ���������������� ������ �������������
	 * @param confirmStrategyType ���������������� ������ �������������
	 */
	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * ������ ��������� ������������� (�����)
	 * @param confirmationSources ��������� ������������� (�����)
	 */
	public void setConfirmationSources(List<CardConfirmationSource> confirmationSources)
	{
		this.confirmationSources = confirmationSources;
	}

	public List<CardConfirmationSource> getConfirmationSources()
	{
		return confirmationSources;
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
