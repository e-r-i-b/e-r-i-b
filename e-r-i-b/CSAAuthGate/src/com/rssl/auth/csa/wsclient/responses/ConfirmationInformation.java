package com.rssl.auth.csa.wsclient.responses;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 *  ���������� � �������������
 */

@SuppressWarnings({"ReturnOfCollectionOrArrayField"})
public class ConfirmationInformation
{
	private ConfirmStrategyType preferredConfirmType;
	private List<String> cardConfirmationSource = new ArrayList<String>();
	private boolean pushAllowed;

	/**
	 * @return ���������������� ������ �������������
	 */
	public ConfirmStrategyType getPreferredConfirmType()
	{
		return preferredConfirmType;
	}

	/**
	 * ������ ���������������� ������ ������������� (�� ������)
	 * @param preferredConfirmType ���������������� ������ �������������
	 */
	public void setPreferredConfirmType(String preferredConfirmType)
	{
		this.preferredConfirmType = ConfirmStrategyType.valueOf(preferredConfirmType);
	}

	/**
	 * @return ���������� � ������, ��� ��������� �������������      
	 */
	public List<String> getCardConfirmationSource()
	{
		return cardConfirmationSource;
	}

	/**
	 * �������� ���������� � �����, ��� ��������� �������������
	 * @param cardNumber ���������� � �����, ��� ��������� �������������
	 */
	public void addCardConfirmationSource(String cardNumber)
	{
		cardConfirmationSource.add(cardNumber);
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
