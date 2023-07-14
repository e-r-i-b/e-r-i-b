package com.rssl.phizic.logging.operations.context;

/** ������ ������� � ������� operUID'�
 * @author akrenev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public interface PossibleAddingOperationUIDObject
{
	/**
	 * ������ operUID ��� �������
	 * @param operUID ���������� operUID
	 */
	void setOperationUID(String operUID);

	/**
	 * @return operUID �������
	 */
	String getOperationUID();
}
