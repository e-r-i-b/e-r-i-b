package com.rssl.phizic.business.persons;

import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;

import java.util.*;

/**
 * Компаратор для сортировки ДУЛов клиента для отображения в профиле
 * @author lukina
 * @ created 21.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class PersonDocumentProfileTypeComparator implements Comparator<PersonDocument>
{
	private static final PersonDocumentType[] typeList = {
			PersonDocumentType.REGULAR_PASSPORT_RF,
			PersonDocumentType.PENSION_CERTIFICATE,
			PersonDocumentType.FOREIGN_PASSPORT,
			PersonDocumentType.OTHER,
			PersonDocumentType.OTHER_DOCUMENT_MVD,
			PersonDocumentType.FOREIGN_PASSPORT_RF,
			PersonDocumentType.RESIDENTIAL_PERMIT_RF,
			PersonDocumentType.MILITARY_IDCARD,
			PersonDocumentType.TIME_IDCARD_RF,
			PersonDocumentType.RESERVE_OFFICER_IDCARD,
			PersonDocumentType.REGULAR_PASSPORT_USSR,
			PersonDocumentType.OFFICER_IDCARD,
			PersonDocumentType.SEAMEN_PASSPORT,
			PersonDocumentType.TEMPORARY_PERMIT,
			PersonDocumentType.MIGRATORY_CARD,
			PersonDocumentType.BIRTH_CERTIFICATE,
			PersonDocumentType.REFUGEE_IDENTITY,
			PersonDocumentType.IMMIGRANT_REGISTRATION,
			PersonDocumentType.FOREIGN_PASSPORT_OTHER,
			PersonDocumentType.FOREIGN_PASSPORT_OTHER,
			PersonDocumentType.PASSPORT_MINMORFLOT,
			PersonDocumentType.OTHER_NOT_RESIDENT,
			PersonDocumentType.DIPLOMATIC_PASSPORT_RF,
			PersonDocumentType.FOREIGN_PASSPORT_LEGAL,
			PersonDocumentType.INQUIRY_ON_CLEARING,
			PersonDocumentType.FOREIGN_PASSPORT_USSR
	};

	private static final Map<PersonDocumentType, Integer> typePriorities = new HashMap<PersonDocumentType, Integer>();

	static
	{
		for (int i=0;i<typeList.length;i++)
			typePriorities.put(typeList[i],i);
	}

	public int compare(PersonDocument document1, PersonDocument document2)
	{
		PersonDocumentType type1 = document1.getDocumentType();
		PersonDocumentType type2 = document2.getDocumentType();
		if (type1 == type2)
			return (int) (document2.getId() - document1.getId());
		if (!typePriorities.containsKey(type1) && !typePriorities.containsKey(type2))
			return 0;
		if (typePriorities.containsKey(type1) && !typePriorities.containsKey(type2))
			return -1;
		if (!typePriorities.containsKey(type1) && typePriorities.containsKey(type2))
			return 1;

		return typePriorities.get(type1) - typePriorities.get(type2);
	}
}
