package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author Egorova
 * @ created 25.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface LossPassbookApplicationClaim extends SynchronizableDocument
{
	/**
    * ���� ��������� ����������
    *
    * @return ���� ��� ������������� ��������
    */
   String getDepositAccount();

	/**
    * �������� �� ������
	* 1 - ������ ���������
	* 2 - ����������� �� ������ ����
    * 3 - ������ �������� ����������
	*
    * @return �������� �� ������
    */
   int getAccountAction();

	/**
    * ���� ��� ������������ �������
    *
    * @return ���� ��� ������������ �������
    */
   String getReceiverAccount();
}
