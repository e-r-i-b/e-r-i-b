package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Фильтр на проверку является ли операция по документу операции по конвертации валют.
 *
 * @author bogdanov
 * @ created 05.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class ConversionOperationFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		if (!(stateObject instanceof AbstractAccountsTransfer))
			return false;

		try
		{
			AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) stateObject;
			Currency resCurrency1 = transfer.getChargeOffCurrency();
			Currency resCurrency2 = transfer.getDestinationCurrency();

			if(resCurrency1 != null)
				return !resCurrency1.compare(resCurrency2);

			if(resCurrency2 != null)
				return !resCurrency2.compare(resCurrency1);

			return false;   //!CurrencyISOComparator.compare(null,null)
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
