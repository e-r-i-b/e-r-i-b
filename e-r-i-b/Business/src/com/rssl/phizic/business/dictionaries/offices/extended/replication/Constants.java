package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Констатны для репликации
 * @author niculichev
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String SUBBRANCH_TABLE_NAME     = "SUBBRANCH";  // название таблицы в файле
	public static final String TERBANK_FIELD_NAME       = "TERBANK";    // Номер тербанка
	public static final String BRANCH_FIELD_NAME        = "BRANCH";     // Отделение
	public static final String SUBBRANCH_FIELD_NAME     = "SUBBRANCH";  // Структурное подразделение
	public static final String SUBNAME_FIELD_NAME       = "SUBNAME";    // Наименование подразделения
	public static final String SUBADDRESS_FIELD_NAME    = "SUBADDRESS"; // Адрес
	public static final String ADDRESS_FIELD_NAME       = "ADDRESS"; // Адрес
	public static final String BSNEWNUM_FIELD_NAME      = "BSNEWNUM";   // БИК
	public static final String SBIDNT_FIELD_NAME        = "SBIDNT"; // Идентификатор участника
	public static final String SUBINDEX_FIELD_NAME      = "SUBINDEX"; // Почтовый индекс
	public static final String TELEPHONE_FIELD_NAME     = "TELEPHONE"; // Телефон
	public static final String FL_OMS_FIELD_NAME        = "FL_OMS"; // // возможность выдачи драгметаллов в офисе

	public static final String RECORD_TAG_NAME = "record";
	public static final String TABLE_TAG_NAME = "table";
	public static final String FIELD_TAG_NAME = "field";

	public static final String DELIMITER = "\n";
	public static final Map<String, List<FieldValidator>> fieldValidators = new HashMap<String, List<FieldValidator>>();

	static
	{
		fieldValidators.put(TERBANK_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^[0-9]{1,4}$")));

		fieldValidators.put(BRANCH_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RegexpFieldValidator("^[0-9]{1,4}$")));

		fieldValidators.put(SUBBRANCH_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RegexpFieldValidator("^[0-9]{1,7}$")));

		fieldValidators.put(ADDRESS_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RegexpFieldValidator("^.{0,256}$")));

		fieldValidators.put(TELEPHONE_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RegexpFieldValidator("^.{0,50}$")));

		fieldValidators.put(SBIDNT_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RegexpFieldValidator("^.{0,4}$")));

		fieldValidators.put(BSNEWNUM_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RegexpFieldValidator("^.{0,26}$")));

		fieldValidators.put(FL_OMS_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RegexpFieldValidator("^(Y|N)$")));

		fieldValidators.put(SUBNAME_FIELD_NAME, Arrays.<FieldValidator>asList(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{0,256}$")));
	}
}
