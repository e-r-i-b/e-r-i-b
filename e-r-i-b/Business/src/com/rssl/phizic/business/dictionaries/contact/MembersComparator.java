package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.dictionaries.ContactMember;

/**
 * @author Kosyakova
 * @ created 09.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class MembersComparator extends AbstractCompatator
{
	public int compare(Object o1, Object o2)
	{
		ContactMember member1 = (ContactMember) o1;
		com.rssl.phizic.gate.dictionaries.ContactMember member2 = (ContactMember) o2;

		if(!isObjectsEquals(member1.getSynchKey(),member2.getSynchKey()))
			return -1;

		if(!isObjectsEquals(member1.getCode(), member2.getCode()))
			return -1;

		if(!isObjectsEquals(member1.getName(), member2.getName()))
			return -1;

		if(!isObjectsEquals(member1.getCity(), member2.getCity()))
			return -1;

		if(!isObjectsEquals(member1.getAddress(), member2.getAddress()))
			return -1;

		if(!isObjectsEquals(member1.getPhone(), member2.getPhone()))
			return -1;

		if(!isObjectsEquals(member1.getCountryId(),member2.getCountryId()))
			return -1;

		return 0;
	}
}
