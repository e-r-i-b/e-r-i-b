package com.rssl.phizic.common.types.basket.fieldFormula;

/**
 * @author osminin
 * @ created 29.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Константы для связок ПУ и документов профиля
 */
public class Constants
{
	public static final String ID_SPLITTER                  = ",";

	private static final String PROVIDER_FIELD_NAME         = "providerField_";
	private static final String VALUE_FIELD_NAME            = "valueField_";
	private static final String IDENT_TYPE_FIELD_NAME       = "identTypeField_";
	private static final String LAST_FIELD_NAME             = "lastField_";
	public static final String NEW_FIELD_PREFIX             = "new_";

	/**
	 * Построить наименование для поля - параметр ПУ
	 * @param id идентификатор формулы
	 * @param prefix префикс
	 * @return наименование
	 */
	public static String getProviderFieldName(Object id, String prefix)
	{
		return prefix + PROVIDER_FIELD_NAME + id;
	}

	/**
	 * Построить наименование для поля - постфикс формулы
	 * @param id идентификатор формулы
	 * @param prefix префикс
	 * @return наименование
	 */
	public static String getLastFieldName(Object id, String prefix)
	{
		return prefix + LAST_FIELD_NAME + id;
	}

	/**
	 * Построить наименование для поля - дополнительное значение идентифитора профиля
	 * @param id идентификатор формулы
	 * @param index порядковый номер идентификатора профиля
	 * @param prefix префикс
	 * @return наименование
	 */
	public static String getValueFieldName(Object id, int index, String prefix)
	{
		return prefix + VALUE_FIELD_NAME + index + "_" + id;
	}

	/**
	 * Построить наименование для поля - идентификатор профиля
	 * @param id идентификатор формулы
	 * @param index порядковый номер идентификатора профиля
	 * @param prefix префикс
	 * @return наименование
	 */
	public static String getIdentTypeFieldName(Object id, int index, String prefix)
	{
		return prefix + IDENT_TYPE_FIELD_NAME + index + "_" + id;
	}
}
