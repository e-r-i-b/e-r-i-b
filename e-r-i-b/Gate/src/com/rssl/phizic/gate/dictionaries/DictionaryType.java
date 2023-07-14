package com.rssl.phizic.gate.dictionaries;

/**
 * Тип справочника
 * @author Pankin
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public enum DictionaryType
{
	// Справочник депозитных продуктов
	DEPOSIT,

	// Справочник карт из ЦАС НСИ
	CARD,

	// Справочник ОМС из ЦАС НСИ
	IMA,

	//Справочник системы предварительной обработки операций с банковскими картами
	SPOOBK2,

	//Справочник def-кодов
	DEF_CODES,

	//Справочник mnp-телефонов
	MNP_PHONES,

	//Справочник номерных емкостей
	NUMBER_ARRAYS,

	//Справочник операторов сотовой связи
	CELL_OPERATORS,

	// Справочник общего вида
	OTHER
}
