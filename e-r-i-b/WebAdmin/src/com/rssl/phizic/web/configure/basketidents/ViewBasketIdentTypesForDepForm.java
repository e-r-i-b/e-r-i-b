package com.rssl.phizic.web.configure.basketidents;

import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма просмотра идентфикаторов корзины для подразделений
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewBasketIdentTypesForDepForm extends ActionFormBase
{
	private List<Pair<Integer, String>> identifiers;

	public List<Pair<Integer, String>> getIdentifiers()
	{
		return identifiers;
	}

	public void setIdentifiers(List<Pair<Integer, String>> identifiers)
	{
		this.identifiers = identifiers;
	}
}
