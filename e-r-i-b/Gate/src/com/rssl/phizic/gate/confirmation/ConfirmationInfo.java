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

public interface ConfirmationInfo
{
	/**
	 * @return ���������������� ������ �������������
	 */
	public ConfirmStrategyType getConfirmStrategyType();

	/**
	 * @return ��������� �������������
	 */
	public List<CardConfirmationSource> getConfirmationSources();

	/**
	 * @return �������� �� ������������� �� push
	 */
	public boolean isPushAllowed();
}
