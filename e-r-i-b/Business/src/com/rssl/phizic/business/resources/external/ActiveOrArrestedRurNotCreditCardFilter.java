package com.rssl.phizic.business.resources.external;

/**
 * ������, ����������� ��� ����� �� ��������� ����� ������ rur � ��������� ��� �������
 * @author basharin
 * @ created 05.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedRurNotCreditCardFilter extends CardFilterConjunction
{
	public ActiveOrArrestedRurNotCreditCardFilter()
	{
		addFilter(new RURCardFilter());
		addFilter(new NotCreditCardFilter());
		addFilter(new ActiveCardWithArrestedAccountFilter());
	}
}
