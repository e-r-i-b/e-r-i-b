package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.person.PersonDocumentType;

/**
 * User: moshenko
 * Date: 01.02.2013
 * Time: 12:00:58
 * Хелпер для работы со справочником СПООБК
 */
public class SpoobkDocumentHelper
{

	/**
	 *
	 * 121 - Паспорт гражданина РФ
	 * 122 - Загранпаспорт гражданина РФ
	 * 194 - Иные документы предусмотренные федеральным законодательством
	 * 192 - Документ, выданный иностр.гос-вом и признанный в РФ
	 *
	 *
	 * @param doc документ
	 * @return код документа по СПООБК
	 */
	public static String getSpoobkDocumentNumber(PersonDocumentType doc) throws NotFoundException
	{
		if(doc == PersonDocumentType.REGULAR_PASSPORT_RF)
			return "121";
		else if (doc == PersonDocumentType.FOREIGN_PASSPORT_RF)
			return "122";
		else if (doc == PersonDocumentType.FOREIGN_PASSPORT_LEGAL)
			return "192";
		else if (doc == PersonDocumentType.OTHER_NOT_RESIDENT)
			return "194";
		else
			throw new NotFoundException("Документ клиента не соответствует ни одному из кодов СПООБК: " + doc);
	}
}
