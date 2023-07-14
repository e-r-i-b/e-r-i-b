package com.rssl.phizic.gate.templates.attributable;

import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.documents.attribute.Type;

/**
 * ��������� ������� ������ � ��������������� ������
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface Attributable
{
	/**
	 * ������� �������
	 * @param name ���
	 * @param type ���
	 * @param value ��������
	 * @return �������
	 */
	ExtendedAttribute createAttribute(String name, Type type, Object value);

	/**
	 * �������� ������� �� �����
	 * @param name ��� ��������
	 * @return �������
	 */
	ExtendedAttribute getAttribute(String name);

	/**
	 * �������� �������
	 * @param attribute �������
	 */
	void addAttribute(ExtendedAttribute attribute);

	/**
	 * ������� �������
	 * @param name ��� ��������
	 */
	void removeAttribute(String name);
}
