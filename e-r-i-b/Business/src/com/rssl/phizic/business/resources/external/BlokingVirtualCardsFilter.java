package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.security.PermissionUtil;

/**
 * @ author gorshkov
 * @ created 03.03.14
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ��� ��������� ���� �������� ����, � ����� �����������, ���� ������� ������.
 */
public class BlokingVirtualCardsFilter extends CardFilterConjunction
{
	public BlokingVirtualCardsFilter()
	{
		addFilter(new ActiveCardFilter());
		addFilter(new BlokingVirtualCardFilter());
	}
}

