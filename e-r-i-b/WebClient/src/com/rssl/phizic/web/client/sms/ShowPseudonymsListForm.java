package com.rssl.phizic.web.client.sms;

import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.AccountPseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.CardPseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.DepositPseudonym;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * @author eMakarov
 * @ created 20.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowPseudonymsListForm extends ListFormBase
{
	private long personId;
	private List<Pseudonym> pseudonyms;

	public long getPersonId()
	{
		return personId;
	}

	public void setPersonId(long personId)
	{
		this.personId = personId;
	}

	public List<Pseudonym> getPseudonyms()
	{
		return pseudonyms;
	}

	public void setPseudonyms(List<Pseudonym> pseudonyms)
	{
		this.pseudonyms = pseudonyms;
	}

	public List<AccountPseudonym> getAccountPseudonyms()
	{
		return getPseudonyms(AccountPseudonym.class);
	}

	public List<CardPseudonym> getCardPseudonyms()
	{
		return getPseudonyms(CardPseudonym.class);
	}

	public List<DepositPseudonym> getDepositPseudonyms()
	{
		return getPseudonyms(DepositPseudonym.class);
	}

	private <T extends Pseudonym> List<T> getPseudonyms(Class<T> clazz)
	{
		List<T> array = new ArrayList<T>();
		if (pseudonyms == null)
		{
			return array;
		}
		for (Pseudonym pseudonym : pseudonyms)
		{
			if (pseudonym.getClass().equals(clazz))
			{
				array.add((T)pseudonym);
			}
		}
		return array;
	}
}
