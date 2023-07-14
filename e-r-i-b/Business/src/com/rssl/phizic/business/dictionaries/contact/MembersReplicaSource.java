package com.rssl.phizic.business.dictionaries.contact;

import com.linuxense.javadbf.DBFException;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Map;

/**
 * @author Kosyakova
 * @ created 09.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class MembersReplicaSource extends ContactFileReplicaSource
{
	public MembersReplicaSource()
	{
		super("banks.dbf", new SynchKeyComparator());
	}

	protected void validate(DictionaryRecord dr) throws GateException
	{
		ContactMember contactMember = (ContactMember) dr;
		if (contactMember.getCountryId() == null)
			throw new GateException("Для ContactMember ID = " +
					contactMember.getCountryId() + " не указана страна");
	}

	protected DictionaryRecord map(Map row) throws DBFException, GateException
	{
		ContactMember contactMember = new ContactMember();
		contactMember.setId(convertStringToLongValue(row.get("ID")));
		contactMember.setCode(((String) row.get("PP_CODE")).trim());
		contactMember.setName(((String) row.get("NAME_RUS")).trim());
		contactMember.setPhone(((String) row.get("PHONE")).trim());
		contactMember.setAddress(((String) row.get("ADDRESS1")).trim());
		contactMember.setCity(((String) row.get("CITY_HEAD")).trim());
		contactMember.setCountryId(convertDoubleToLongValue(row.get("COUNTRY")));
		contactMember.setDeleted(convertDoubleToLongValue(row.get("DELETED")) == 1);

		return contactMember;
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
