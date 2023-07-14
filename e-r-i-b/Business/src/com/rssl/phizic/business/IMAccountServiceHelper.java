package com.rssl.phizic.business;

import com.rssl.phizic.business.resources.external.IMAccountLink;

import java.util.List;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountServiceHelper
{
	/**
	 * ���� � ������ ������ ��� ������ �� ��� �� ������ �����
	 * @param number - ����� ����� ���
	 * @param imAccounLinks - ������ ������ �� ��� �������
	 * @return ������ �� ��� �������
	 */
	public static IMAccountLink getIMAccountLinkByNumber(String number, List<IMAccountLink> imAccounLinks)
	{
		for(IMAccountLink link : imAccounLinks)
		{
			if(link.getNumber().equals(number))
			{
				return link;
			}
		}
		return null;
	}
}
