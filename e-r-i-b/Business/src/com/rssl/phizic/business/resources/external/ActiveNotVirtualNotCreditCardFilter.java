package com.rssl.phizic.business.resources.external;

/**
 * @author Balovtsev
 * @created 26.01.2011
 * @ $Author$
 * @ $Revision$
 * 
 * ������ ����������� ����� ���������� �������� ����� � �������� ��������, �������� ��������,
 * ����� ����� �������� ��������� ��� �����������.
 */

public class ActiveNotVirtualNotCreditCardFilter extends ActiveNotVirtualCardsFilter
{

	public ActiveNotVirtualNotCreditCardFilter()
	{
		super();
		addFilter(new NotCreditCardFilter());
	}
}
