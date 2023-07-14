package com.rssl.phizic.csaadmin.listeners.mail.converters;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 *  ��������� �������� �� ������ ������������� � ������.
 */
public interface MultiNodeEntityConverter<GateType,NodeType>
{

	/**
	 * ������������� ������ � ��������� ��������������
	 * @param entity - ������
	 * @return �������� ������������� �������
	 */
	public GateType convertToGate(NodeType entity);

}
