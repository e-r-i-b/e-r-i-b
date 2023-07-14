package com.rssl.phizic.business.longoffer;

import com.rssl.phizic.business.resources.external.LongOfferLink;

import java.util.List;

public class LongOfferServiceHelper
{
	public static LongOfferLink findLongOfferLinkByNumber(List<LongOfferLink> longOfferLinks, String number)
	{
		for (LongOfferLink longOfferLink: longOfferLinks)
		{
			if (longOfferLink.getNumber().equals(number))
				return longOfferLink;
		}
		return null;
	}

	/**
	 * �������� ���� �� ��������������
	 * @param longOfferLinks ������ ������
	 * @param externalId �������������
	 * @return ���� ����������� ���������
	 */
	public static LongOfferLink findLongOfferLinkByEternalId(List<LongOfferLink> longOfferLinks, String externalId)
	{
		for (LongOfferLink longOfferLink: longOfferLinks)
			if (longOfferLink.getExternalId().equals(externalId))
				return longOfferLink;
		return null;
	}
}
