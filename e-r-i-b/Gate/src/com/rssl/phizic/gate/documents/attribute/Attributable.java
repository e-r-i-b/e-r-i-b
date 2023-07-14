package com.rssl.phizic.gate.documents.attribute;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Map;

/**
 * ��������� ��������� ���������, ������������ ���������� �������������� ����
 *
 * @author khudyakov
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface Attributable
{
	/**
	 * @return ������ �������������� ���������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	Map<String, ExtendedAttribute> getExtendedAttributes() throws GateException;
}
