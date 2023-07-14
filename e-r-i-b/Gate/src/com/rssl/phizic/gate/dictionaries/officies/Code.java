package com.rssl.phizic.gate.dictionaries.officies;

import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.Map;

/**
 * ��� ������������� �����
 * @author Kidyaev
 * @ created 01.11.2006
 * @ $Author$
 * @ $Revision$
 */
public interface Code extends Serializable
{
	/**
	 * ���������� ���� ��� ����� � ���������� � ���������.
	 * ���������� ������ ���������� ��� ����, ��������� � ��������.
	 * @param o ������ ��� ���������
	 * @return <code>true</code> ���� ���� ������������,
     *         <code>false</code> �����.
	 */
	boolean equals(Object o);

	/**
	 * ���������� ���-��� ��� ����� ����
	 * @return ���-��� ��� ����� ����
	 */
	int hashCode();

	/**
	 * ���������� ��� ���� ���� ����� Map<��������, ��������>
	 * @return ������ ����� � �� ��������
	 */
	Map<String, String> getFields();

	/**
	 * ���������� ����. ������������� ����
	 * @return id ����
	 */

	String getId();

	/**
	 * ����������� ��� � xml ����
	 * &lt;parentTag&gt;
	 *     &lt;������������&gt;������������&lt;/������������&gt;
	 * &lt;/parentTag&gt;
	 * @param parentTag - ������������ ���
	 */
	void toXml(Element parentTag);
}
