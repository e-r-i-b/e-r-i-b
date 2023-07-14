package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author mihaylov
 * @ created 15.10.2010
 * @ $Author$
 * @ $Revision$
 */

public interface RecallDepositaryClaim extends SynchronizableDocument
{
	/**
	 * ����� ����������� ���������(������������� ��������� � �����������)
	 * @return docNumber
	 */
	String getDocNumber();

	/**
	 * ������� ������ ���������
	 * @return revokePurpose
	 */
	String getRevokePurpose();
}
