package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Map;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������� �������� ��� �������������
 */

public interface ConfirmableOperationInfo
{
	/**
	 * @return ����� ��������������
	 */
	public String getAuthToken();

	/**
	 * ������ ���������������� ������ �������������
	 * @param preferredConfirmType ���������������� ������ �������������
	 */
	public void setPreferredConfirmType(ConfirmStrategyType preferredConfirmType);

	/**
	 * @return ���������������� ������ �������������
	 */
	public ConfirmStrategyType getPreferredConfirmType();

	/**
	 * ������ ���������� � ������, ��� ��������� �������������
	 * @param cardConfirmationSource ���������� � ������, ��� ��������� �������������
	 */
	public void setCardConfirmationSource(Map<String, String> cardConfirmationSource);

	/**
	 * @return ���������� � ������, ��� ��������� �������������
	 */
	public Map<String, String> getCardConfirmationSource();

	/**
	 * @return �������� �� ������������� �� push
	 */
	public boolean isPushAllowed();

	/**
	 * ������ �������� �� ������������� �� push
	 * @param pushAllowed �������� �� ������������� �� push
	 */
	public void setPushAllowed(boolean pushAllowed);

}
