package com.rssl.phizic.test.esberibmock;

import com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type;
import org.hibernate.util.StringHelper;

import java.math.BigDecimal;

/**
 @author: Egorovaa
 @ created: 29.02.2012
 @ $Author$
 @ $Revision$
 */
public class MockBaseResponseHelper
{
	/**
	 * –азбираем строку, содержащую данные по остаткам и лимитам и разносим информацию по ним в массив AcctBal_Type
	 * @param acctBal - строка с перечисленными парами [тип остатка] - [сумма]
	 * @return массив с остатками
	 */
	protected AcctBal_Type[] buildBalance(String acctBal)
	{
		if (!StringHelper.isEmpty(acctBal))
		{
			// делим строку на части, содержащую данные по остатку (разделены зап€тыми) вида [тип остатка] - [сумма]
			String[] strAcctBals = acctBal.replaceAll(" ", "").split(",");

			AcctBal_Type[] limits = new AcctBal_Type[strAcctBals.length];
			for (int i = 0; i < strAcctBals.length; i++)
			{
				// строку с данными по остатку делим на части
				// strAcctBal[0] - тип остатка, strAcctBal[1] - сумма
				AcctBal_Type limit = new AcctBal_Type();
				String[] strAcctBal = strAcctBals[i].split("-", 2);
				limit.setBalType(strAcctBal[0]);
				limit.setCurAmt(new BigDecimal(strAcctBal[1]));
				limits[i] = limit;
			}
			return limits;
		}
		else
			return new AcctBal_Type[0];
	}

}
