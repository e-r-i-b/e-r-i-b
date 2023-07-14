package com.rssl.phizic.business.resources.external;

/**
 * ������, ����������� ��� ����� ������� ��� ���������� � �� ����������
 * @author basharin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedNotVirtualCardFilter extends CardFilterConjunction
{
	public ActiveOrArrestedNotVirtualCardFilter()
	{
		addFilter(new NotVirtualCardsFilter());
		addFilter(new ActiveCardWithArrestedAccountFilter());
	}
}
