package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;

/**
 * ��������� ������� ������ � ��������������� ������
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface Attributable extends StateObject
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
