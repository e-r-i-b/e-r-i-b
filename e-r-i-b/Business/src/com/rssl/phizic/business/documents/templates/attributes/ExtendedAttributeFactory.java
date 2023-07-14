package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;

/**
 * ������� ���. ��������
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface ExtendedAttributeFactory
{
	/**
	 * ������� ���. �������
	 * @param name ���
	 * @param type ���
	 * @param value ��������
	 * @return �������
	 */
	ExtendedAttribute create(String name, Type type, Object value);

	/**
	 * ������� ���. �������
	 * @param id �������������
	 * @param name ���
	 * @param type ���
	 * @param value ��������
	 * @return �������
	 */
	ExtendedAttribute create(Long id, String name, Type type, Object value);
}
