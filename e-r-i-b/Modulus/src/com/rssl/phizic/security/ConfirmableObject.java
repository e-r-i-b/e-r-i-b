package com.rssl.phizic.security;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

import java.util.Map;

/**
 * @author Krenev
 * @ created 27.11.2008
 * @ $Author$
 * @ $Revision$
 */
public interface ConfirmableObject
{
	/**
	 * @return ������������� ��������������� �������
	 */
	public Long getId();

	/**
	 * @return �������
	 * @throws SecurityException
	 * @throws SecurityLogicException
	 */
	public byte[] getSignableObject() throws SecurityException, SecurityLogicException;

	/**
	 * ���������� ��� ��������� �������������
	 * @param confirmStrategyType ��� ���������
	 */
	void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType);
}
