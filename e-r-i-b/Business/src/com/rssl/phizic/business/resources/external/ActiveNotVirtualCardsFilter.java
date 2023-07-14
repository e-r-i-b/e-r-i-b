package com.rssl.phizic.business.resources.external;

/**
 * @author Balovtsev
 * @created 26.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ��� ��������� ���� �������� ����, �������� �����������.
 */

public class ActiveNotVirtualCardsFilter extends CardFilterConjunction
{
	public ActiveNotVirtualCardsFilter()
	{
		addFilter(new ActiveCardFilter());
		addFilter(new NotVirtualCardsFilter());
	}
}
