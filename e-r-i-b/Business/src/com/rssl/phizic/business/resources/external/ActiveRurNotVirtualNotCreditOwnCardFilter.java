package com.rssl.phizic.business.resources.external;

/**
 * ��������� ���������� ����� + ���������� ��������
 * @author gladishev
 * @ created 20.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ActiveRurNotVirtualNotCreditOwnCardFilter extends ActiveNotVirtualNotCreditOwnCardFilter
{
	public ActiveRurNotVirtualNotCreditOwnCardFilter()
	{
		super();
		addFilter(new RURCardFilter());
	}
}
