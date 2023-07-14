package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * —правочник валют счетов и карт
 * @author Gololobov
 * @ created 29.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountCardCurrencyListSource extends CurrencyListSourceBase
{
	public AccountCardCurrencyListSource(EntityListDefinition definition, String nationCurrency) throws GateException, GateLogicException
	{
		super(definition, nationCurrency);
	}

	public AccountCardCurrencyListSource(EntityListDefinition definition, Map parameters) throws GateException, GateLogicException
	{
		super(definition, parameters);
	}

	/**
	 * ѕродукты клиента по которым будет строитьс€ справочник валют.
	 * @param params - параметры справочника
	 * @return ClientCurrencyProductBundle - содержит списки продуктов, которые необходимы дл€ построени€ справочника валют
	 * @throws BusinessException
	 */
	protected ClientCurrencyProductBundle buildCurrencyProducts(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		ClientCurrencyProductBundle clientCurrencyProductBundle = new ClientCurrencyProductBundle();
		//—чета
		clientCurrencyProductBundle.setAccounts(getAccounts());
		// арты
		clientCurrencyProductBundle.setCards(getCards());

		return clientCurrencyProductBundle;
	}
}
