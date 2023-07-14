package com.rssl.phizic.gate.documents.attribute;

/**
 * @author khudyakov
 * @ created 14.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface ExtendedAttribute
{
	/**
	 * @return ��� ��������
	 */
	String getName();

	/**
	 * @return ��� ��������
	 */
	Type getType();

	/**
	 * @return �������� ��������
	 */
	Object getValue();

	/**
	 * ���������� �������� ��������
	 * @param value ��������
	 */
	void setValue(Object value);

	/**
	 * @return ��������� ��������
	 */
	String getStringValue();

	/**
	 * @return true - �������� ���� ����������
	 */
	boolean isChanged();

	/**
	 * ���������� ���� ����� �������� ����
	 * @param changed true - �������� ���� ����������
	 */
	void setChanged(boolean changed);
}
