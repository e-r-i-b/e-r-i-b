package com.rssl.phizic.business.extendedattributes;

/**
 * @author Roshka
 * @ created 08.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface Attributable
{
    /**
     * �������� ������� �� �����.
     * @param name ��� ��������
     * @return
     */
    ExtendedAttribute getAttribute(String name);

    /**
     * �������� �������
     * @param attr
     */
    void addAttribute(ExtendedAttribute attr);

    /**
     * ������� �������
     * @param name
     */
    void removeAttribute(String name);

	/**
	 * ������� ������� (�� ���������)
	 * @param type ���
	 * @param name ���
	 * @param value ��������
	 * @return ����� �������
	 */
	ExtendedAttribute createAttribute(String type, String name, Object value);

	/**
	 * ������� ������� (�� ���������)
	 * @param type ���
	 * @param name ���
	 * @param value ��������� ��������
	 * @return ����� �������
	 */
	ExtendedAttribute createAttribute(String type, String name, String value);

}
