package com.rssl.phizic.gate.operations;

/**
 * ��������� �������� ��������� ������� �������� � generated ����
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface CorrelationOperation<G> extends Operation
{
	/**
	 * @return ��������
	 */
	G toGeneratedEntity() throws Exception;
}
