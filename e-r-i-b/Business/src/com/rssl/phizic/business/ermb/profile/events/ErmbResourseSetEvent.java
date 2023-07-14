package com.rssl.phizic.business.ermb.profile.events;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * @author EgorovaA
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сущность для передачи наблюдателю возможно измененных списков линков клиента
 */
public class ErmbResourseSetEvent extends ErmbResourseEvent
{
	private Map<Class, List<Long>> oldResourses = new HashMap<Class, List<Long>>();
	private Map<Class, List<Long>> newResourses = new HashMap<Class, List<Long>>();

	public ErmbResourseSetEvent(Long personId) throws BusinessException
	{
		super(personId);
	}

	public Map<Class, List<Long>> getOldResourses()
	{
		return oldResourses;
	}

	public Map<Class, List<Long>> getNewResourses()
	{
		return newResourses;
	}

	public void setOldData(List<CardLink> cardLinks, List<AccountLink> accountLinks, List<LoanLink> loanLinks)
	{
		oldResourses.put(CardLink.class, copyResourse(cardLinks));
		oldResourses.put(AccountLink.class, copyResourse(accountLinks));
		oldResourses.put(LoanLink.class, copyResourse(loanLinks));
	}

	public void setNewData(List<CardLink> cardLinks, List<AccountLink> accountLinks, List<LoanLink> loanLinks)
	{
		newResourses.put(CardLink.class, copyResourse(cardLinks));
		newResourses.put(AccountLink.class, copyResourse(accountLinks));
		newResourses.put(LoanLink.class, copyResourse(loanLinks));
	}

	private List<Long> copyResourse(List<? extends ErmbProductLink> list)
	{
		if (list == null)
			return new ArrayList<Long>();

		List<Long> idsList = new ArrayList<Long>(list.size());
		for (ErmbProductLink link : list)
		{
			idsList.add(link.getId());
		}
		return idsList; 
	}

	public void setOldData(Map<Class<? extends ExternalResourceLink>, List<? extends ExternalResourceLink>> links)
	{
		List<CardLink> cards = (List<CardLink>) links.get(CardLink.class);
		List<AccountLink> accounts = (List<AccountLink>) links.get(AccountLink.class);
		List<LoanLink> loans = (List<LoanLink>) links.get(LoanLink.class);

		setOldData(cards, accounts, loans);
	}

	public void setNewData(Map<Class<? extends ExternalResourceLink>, List<? extends ExternalResourceLink>> links)
	{
		List<CardLink> cards = (List<CardLink>) links.get(CardLink.class);
		List<AccountLink> accounts = (List<AccountLink>) links.get(AccountLink.class);
		List<LoanLink> loans = (List<LoanLink>) links.get(LoanLink.class);

		setNewData(cards, accounts, loans);
	}
}
