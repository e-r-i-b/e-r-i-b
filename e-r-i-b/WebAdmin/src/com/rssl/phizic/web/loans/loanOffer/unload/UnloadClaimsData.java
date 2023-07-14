package com.rssl.phizic.web.loans.loanOffer.unload;

import com.rssl.phizic.operations.loanOffer.ProductKind;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Используется как параметр типа в action-е UnloadOfferAction.
 * Хранит собственно данные, а также доп. параметры, необходимые action-у.
 * @author Dorzhinov
 * @ created 25.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class UnloadClaimsData implements Serializable
{
	private final ProductKind kind; //Вид выгружаемых заявок
	private final Set<Long> unloadedIds; //id выгруженных заявок
	private final List<String> data; //собственно данные

	public UnloadClaimsData(ProductKind kind, Set<Long> unloadedIds, List<String> data)
	{
		this.kind = kind;
		this.unloadedIds = unloadedIds;
		this.data = data;
	}

	public ProductKind getKind()
	{
		return kind;
	}

	public Set<Long> getUnloadedIds()
	{
		return unloadedIds;
	}

	public List<String> getData()
	{
		return data;
	}
}