package com.rssl.phizic.web.configure.basketidents;

import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма просмотра идентфикаторов корзины
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketIdentTypesForDepForm extends ActionFormBase
{
	private Long id;
	private String depName;
	private List<Pair<BasketIndetifierType, String>> identifiers;

	public String getDepName()
	{
		return depName;
	}

	public void setDepName(String depName)
	{
		this.depName = depName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<Pair<BasketIndetifierType, String>> getIdentifiers()
	{
		return identifiers;
	}

	public void setIdentifiers(List<Pair<BasketIndetifierType, String>> identifiers)
	{
		this.identifiers = identifiers;
	}
}
