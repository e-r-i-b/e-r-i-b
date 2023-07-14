package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.depo.DepoDebtInfo;
import com.rssl.phizic.gate.depo.DepoDebtItem;
import com.rssl.phizic.common.types.Money;

import java.util.List;

/**
 * @author mihaylov
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoDebtInfoImpl implements DepoDebtInfo
{
	private Money totalDebt;
	private List<DepoDebtItem> debtItems;

	public Money getTotalDebt()
	{
		return totalDebt;
	}

	public void setTotalDebt(Money totalDebt)
	{
		this.totalDebt = totalDebt;
	}

	public List<DepoDebtItem> getDebtItems()
	{
		return debtItems;
	}

	public void setDebtItems(List<DepoDebtItem> debtItems)
	{
		this.debtItems = debtItems;
	}
}
