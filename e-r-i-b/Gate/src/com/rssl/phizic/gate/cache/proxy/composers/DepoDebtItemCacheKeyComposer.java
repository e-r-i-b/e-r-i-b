package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoDebtItem;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoDebtItemCacheKeyComposer extends AbstractCacheKeyComposer
{
	private static final String SEPARATOR = "|";
	public Serializable getKey(Object[] args, String params)
	{
		StringBuilder builder = new StringBuilder();
		int paramNum = getOneParam(params);
		DepoDebtItem depoDebt = (DepoDebtItem)args[paramNum];
		DepoAccount  depoAccount = (DepoAccount) args[0];
		builder.append(depoDebt.getRecNumber());
		builder.append(SEPARATOR);
		builder.append(depoAccount.getId());
		return builder.toString();
	}
}
