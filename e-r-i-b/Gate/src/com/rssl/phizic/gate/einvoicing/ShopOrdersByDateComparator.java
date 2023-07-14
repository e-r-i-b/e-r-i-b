package com.rssl.phizic.gate.einvoicing;

import java.util.Comparator;

/**
 * @author tisov
 * @ created 27.05.14
 * @ $Author$
 * @ $Revision$
 * компаратор дл€ сравнени€ интернет-заказов по их дате (заказы с более поздней датой считаем бќльшими)
 */

public class ShopOrdersByDateComparator implements Comparator<ShopOrder>
{
	public int compare(ShopOrder o1, ShopOrder o2)
	{
		return o1.getDate().compareTo(o2.getDate());
	}
}
