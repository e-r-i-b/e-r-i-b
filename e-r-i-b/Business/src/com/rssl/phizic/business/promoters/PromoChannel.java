package com.rssl.phizic.business.promoters;

/**
 * ������ ������� � ������� (��� ���)
 *
 * @ author: Gololobov
 * @ created: 11.09.2012
 * @ $Author$
 * @ $Revision$
 */
public enum PromoChannel
{
	VSP(1),
	MVC(2),
	BAW(3);

	private int id;
	private String description;

	PromoChannel(int id)
	{
		this.id = id;

		switch (id)
		{
			case 1:
				description = "���";
			break;
			case 2:
				description = "���";
			break;
			case 3:
				description = "B@W";
			break;
		}
	}

	public int getId()
	{
		return id;
	}

	public String getDescription()
	{
		return description;
	}
}
