package com.rssl.phizic.gate.operations;

/**
 * ��������� �������� ������
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface FindEntityOperation<O> extends Operation
{
	/**
	 * @return ������� ��������
	 * @throws Exception
	 */
	O getEntity() throws Exception;
}
