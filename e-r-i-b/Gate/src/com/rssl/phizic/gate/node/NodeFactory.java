package com.rssl.phizic.gate.node;

import com.rssl.phizic.gate.GateFactory;

/**
 * @author osminin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 *
 * �������, ��������� ���� � �����
 */
public interface NodeFactory extends GateFactory
{
	/**
	 * @return ���� ��������� ���-������� �����
	 */
	String getListenerHost();
}
