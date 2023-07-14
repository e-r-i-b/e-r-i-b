package com.rssl.phizicgate.mock.depo;

import com.rssl.phizic.gate.depo.DepoDebtInfo;
import com.rssl.phizic.gate.depo.DepoDebtItem;
import com.rssl.phizic.common.types.Money;

import java.util.List;

/**
 * @author mihaylov
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoDebtInfoImpl implements DepoDebtInfo
{
	private Money totalDebt;
	private List<DepoDebtItem> debtItems;

	public DepoDebtInfoImpl(Money totalDebt, List<DepoDebtItem> debtItems)
	{
		this.totalDebt = totalDebt;
		this.debtItems = debtItems;
	}

	public Money getTotalDebt()
	{
		return totalDebt;
	}

	public List<DepoDebtItem> getDebtItems()
	{
		return debtItems;
	}
}
