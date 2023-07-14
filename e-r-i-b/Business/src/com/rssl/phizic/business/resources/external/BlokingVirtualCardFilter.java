package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.security.PermissionUtil;

/**
 * @ author gorshkov
 * @ created 04.03.14
 * @ $Author$
 * @ $Revision$
 */
public class BlokingVirtualCardFilter extends NotVirtualCardsFilter
{
	/**
	 * ���������� true ��� false � ����������� �� ����, ����������� ��� ����� � �������� �� ������ � �� ����������.
	 * @param card ����������� �����
	 * @return true - ����� �� ����������� ��� �������� ������, false - ����� ����������� � �� �������� ������
	 */
	public boolean accept(Card card)
	{
		return PermissionUtil.impliesService("BlockingCardIncludeVirtualClaim") || super.accept(card);
	}
}
