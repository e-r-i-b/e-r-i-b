package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author egorova
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class PassportTypeWrapper
{
	private static final Map<ClientDocumentType, String> passportType = new LinkedHashMap<ClientDocumentType, String>();
	private static final Map<String, ClientDocumentType> clientDocumentType = new HashMap<String, ClientDocumentType>();
	private static final String OTHER_DOCUMENT_CODE = "999";

	static
	{
		passportType.put(ClientDocumentType.REGULAR_PASSPORT_RF, "21");
		passportType.put(ClientDocumentType.MILITARY_IDCARD, "7"); //¬оенный билет солдата (матроса, сержанта, старшины)
		passportType.put(ClientDocumentType.SEAMEN_PASSPORT, "26"); 
		passportType.put(ClientDocumentType.RESIDENTIAL_PERMIT_RF, "12");
		passportType.put(ClientDocumentType.IMMIGRANT_REGISTRATION, "11");
		passportType.put(ClientDocumentType.REFUGEE_IDENTITY, "13");
		passportType.put(ClientDocumentType.FOREIGN_PASSPORT_RF, "22");
		passportType.put(ClientDocumentType.MIGRATORY_CARD, "39");
		passportType.put(ClientDocumentType.REGULAR_PASSPORT_USSR, "1");
		passportType.put(ClientDocumentType.FOREIGN_PASSPORT_USSR, "2");
		passportType.put(ClientDocumentType.BIRTH_CERTIFICATE, "3");
		passportType.put(ClientDocumentType.OFFICER_IDCARD, "4");
		passportType.put(ClientDocumentType.PASSPORT_MINMORFLOT, "6");
		passportType.put(ClientDocumentType.DIPLOMATIC_PASSPORT_RF, "9");
		passportType.put(ClientDocumentType.FOREIGN_PASSPORT, "10");
		passportType.put(ClientDocumentType.TIME_IDCARD_RF, "14");
		passportType.put(ClientDocumentType.RESERVE_OFFICER_IDCARD, "27");
		passportType.put(ClientDocumentType.OTHER_DOCUMENT_MVD, "91");
		passportType.put(ClientDocumentType.PASSPORT_WAY, "300");
		passportType.put(ClientDocumentType.INQUIRY_ON_CLEARING, "5");
		passportType.put(ClientDocumentType.FOREIGN_PASSPORT_OTHER, "99");
		passportType.put(ClientDocumentType.DISPLACED_PERSON_DOCUMENT, "38");
		passportType.put(ClientDocumentType.OTHER_NOT_RESIDENT, "35");
		passportType.put(ClientDocumentType.TEMPORARY_PERMIT, "34");
		passportType.put(ClientDocumentType.FOREIGN_PASSPORT_LEGAL, "32");

		// ƒл€ типов документов с двум€ кодами
		clientDocumentType.put("0", ClientDocumentType.OTHER_DOCUMENT_MVD);
		clientDocumentType.put("40", ClientDocumentType.FOREIGN_PASSPORT_OTHER);
	}

	/**
	 * ѕолучить цифровой код документа клиента
	 * @param clientDocumentType тип документа
	 * @return строка с кодом документа
	 */
	public static String getPassportType(ClientDocumentType clientDocumentType)
	{
		String passportCode = passportType.get(clientDocumentType);
		return StringHelper.isEmpty(passportCode) ? "91" : passportCode;
	}

	/**
	 * ѕолучить тип документа по коду
	 * @param documentType строка с кодом документа
	 * @return тип документа
	 */
	public static ClientDocumentType getClientDocumentType(String documentType)
	{
		ClientDocumentType docType = clientDocumentType.get(documentType);
		if (docType != null)
			return docType;

		for (Map.Entry<ClientDocumentType, String> entrySet : passportType.entrySet())
		{
			if (entrySet.getValue().equals(documentType))
				return entrySet.getKey();
		}
		return null;
	}

	/**
	 * ¬озвращает полный список типов документов
	 * @return полный список типов документов
	 */
	public static Map<ClientDocumentType, String> getAllTypes()
	{
		Map<ClientDocumentType, String> result = new LinkedHashMap<ClientDocumentType, String>(passportType);
		result.put(ClientDocumentType.PENSION_CERTIFICATE, OTHER_DOCUMENT_CODE);
		result.put(ClientDocumentType.OTHER, OTHER_DOCUMENT_CODE);
		return Collections.unmodifiableMap(result);
	}
}