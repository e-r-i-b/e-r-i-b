package com.rssl.phizic.gate.claims.pfr;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.pfr.StatementStatus;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ������ ������� �� ��� (����������� ����� ��)
 */
public interface PFRStatementClaim extends SynchronizableDocument
{
	/**
	 * ���������� ����� �����
	 * @return ����� ����� ��������������
	 */
	String getSNILS();

	/**
	 * ���������� ���
	 * @return ��� ��������������
	 */
	String getFirstName();

	/**
	 * ���������� �������
	 * @return �������
	 */
	String getSurName();

	/**
	 * ���������� ��������
	 * @return ��������
	 */
	String getPatrName();

	/**
	 * ���������� ���� ��������
	 * @return ���� ��������
	 */
	Calendar getBirthDay();

	/**
	 * ���������� ��� ���������
	 * @return ��� ���������
	 */
	ClientDocumentType getDocumentType();

	/**
	 * ���������� ����� ���������
	 * @return ����� ���������
	 */
	String getDocNumber();

	/**
	 * ���������� ����� ���������
	 * @return ����� ���������
	 */
	String getDocSeries();

	/**
	 * ���������� ���� ������ ���������
	 * @return ���� ������ ���������
	 */
	Calendar getDocIssueDate();

	/**
	 * ���������� "��� �����"
	 * @return "��� �����"
	 */
	String getDocIssueBy();

	/**
	 * ���������� ��� �������������, ��������� ��������
	 * @return ��� �������������
	 */
	String getDocIssueByCode();

	/**
	 * �������� �� ������ "������ �� ������� �� ���?"
	 * @return ������ ���������� ������� �� ���
	 */
	StatementStatus isReady();

	/**
	 * ���������� ������ ������� �� ���
	 * @param isReady ������ �������
	 */
	void setReady(StatementStatus isReady);

	/**
	 * ���������� �������� ������� ������� �� ���
	 * @return ��������� �������� ������� �������
	 */
	String getReadyDescription();

	void setReadyDescription(String readyDescription);
}
