package com.rssl.phizic.common.types.basket.fieldFormula;

/**
 * @author osminin
 * @ created 29.07.15
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� ������ �� � ���������� �������
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
	 * ��������� ������������ ��� ���� - �������� ��
	 * @param id ������������� �������
	 * @param prefix �������
	 * @return ������������
	 */
	public static String getProviderFieldName(Object id, String prefix)
	{
		return prefix + PROVIDER_FIELD_NAME + id;
	}

	/**
	 * ��������� ������������ ��� ���� - �������� �������
	 * @param id ������������� �������
	 * @param prefix �������
	 * @return ������������
	 */
	public static String getLastFieldName(Object id, String prefix)
	{
		return prefix + LAST_FIELD_NAME + id;
	}

	/**
	 * ��������� ������������ ��� ���� - �������������� �������� ������������ �������
	 * @param id ������������� �������
	 * @param index ���������� ����� �������������� �������
	 * @param prefix �������
	 * @return ������������
	 */
	public static String getValueFieldName(Object id, int index, String prefix)
	{
		return prefix + VALUE_FIELD_NAME + index + "_" + id;
	}

	/**
	 * ��������� ������������ ��� ���� - ������������� �������
	 * @param id ������������� �������
	 * @param index ���������� ����� �������������� �������
	 * @param prefix �������
	 * @return ������������
	 */
	public static String getIdentTypeFieldName(Object id, int index, String prefix)
	{
		return prefix + IDENT_TYPE_FIELD_NAME + index + "_" + id;
	}
}
