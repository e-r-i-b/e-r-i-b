package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ContactMember;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakova
 * @ created 09.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class MembersReplicaDestination extends QueryReplicaDestinationBase  implements ReplicaDestination
{
	public MembersReplicaDestination()
	{
		super("com.rssl.phizic.business.dictionaries.contact.ContactMember.getAllBySyncKey");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
	public void add(DictionaryRecord newValue) throws GateException
	{
		ContactMember contactMember = new ContactMember();
		contactMember.updateFrom(newValue);
		if (!contactMember.getId().equals("") && !contactMember.getCode().equals("") && !contactMember.getCountryId().equals(""))
		{
			super.add(contactMember);
		}
		else
		{
			addError("не удалось добавить банк с кодом "+contactMember.getCode());
		}
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		ContactMember contactMember = new ContactMember();
		contactMember.updateFrom(newValue);
		if (!contactMember.getId().equals("") && !contactMember.getCode().equals("") && !contactMember.getCountryId().equals(""))
		{
			super.update(oldValue, contactMember);
		}
		else
		{
			super.remove(oldValue);
			addError("не удалось обновить банк с кодом "+contactMember.getCode());
		}
	}
}
