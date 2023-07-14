package com.rssl.phizic.ws.esberiblistener.mdm.types;

import com.rssl.phizic.person.PersonDocumentType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author egorova
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class PassportTypeWrapper
{
	private static final Map<String, PersonDocumentType> passportType = new HashMap<String, PersonDocumentType>();

	static
	{
		passportType.put("21", PersonDocumentType.REGULAR_PASSPORT_RF);
		passportType.put("7", PersonDocumentType.MILITARY_IDCARD);  //Военный билет солдата (матроса, сержанта, старшины)
		passportType.put("26", PersonDocumentType.SEAMEN_PASSPORT);
		passportType.put("12", PersonDocumentType.RESIDENTIAL_PERMIT_RF);
		passportType.put("11", PersonDocumentType.IMMIGRANT_REGISTRATION);
		passportType.put("13", PersonDocumentType.REFUGEE_IDENTITY);
		passportType.put("22", PersonDocumentType.FOREIGN_PASSPORT_RF);
		passportType.put("39", PersonDocumentType.MIGRATORY_CARD);		
		passportType.put("1", PersonDocumentType.REGULAR_PASSPORT_USSR);
		passportType.put("2", PersonDocumentType.FOREIGN_PASSPORT_USSR);
		passportType.put("3", PersonDocumentType.BIRTH_CERTIFICATE);
		passportType.put("4", PersonDocumentType.OFFICER_IDCARD);
		passportType.put("6", PersonDocumentType.PASSPORT_MINMORFLOT);
		passportType.put("9", PersonDocumentType.DIPLOMATIC_PASSPORT_RF);
		passportType.put("10", PersonDocumentType.FOREIGN_PASSPORT);
		passportType.put("14", PersonDocumentType.TIME_IDCARD_RF);
		passportType.put("27", PersonDocumentType.RESERVE_OFFICER_IDCARD);
		passportType.put("91", PersonDocumentType.OTHER_DOCUMENT_MVD);
		passportType.put("0", PersonDocumentType.OTHER_DOCUMENT_MVD);
		passportType.put("300", PersonDocumentType.PASSPORT_WAY);
		passportType.put("5", PersonDocumentType.INQUIRY_ON_CLEARING);
		passportType.put("99", PersonDocumentType.FOREIGN_PASSPORT_OTHER);
		passportType.put("40", PersonDocumentType.FOREIGN_PASSPORT_OTHER);
		passportType.put("38", PersonDocumentType.DISPLACED_PERSON_DOCUMENT);
		passportType.put("35", PersonDocumentType.OTHER_NOT_RESIDENT);
		passportType.put("34", PersonDocumentType.TEMPORARY_PERMIT);
		passportType.put("32", PersonDocumentType.FOREIGN_PASSPORT_LEGAL);
	}

	/**
	 * Получить тип документа по типу из шлюза
	 * @param type - шлюзовой тип
	 * @return бизнесовый тип документа
	 */
	public static PersonDocumentType getPassportType(String type)
	{
		return passportType.get(type);
	}
}
