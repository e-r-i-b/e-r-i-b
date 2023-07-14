package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ������ �� ��������� ������������ ��������� �� ����������� �����
 * @author komarov
 * @ created 15.04.2014
 * @ $Author$
 * @ $Revision$
 */
public interface GetPrivateOperationScanClaim extends SynchronizableDocument
{
	/**
	 * @return ��� �������
	 */
	String   getFIO();

	/**
	 * @return ����� �����
	 */
	String   getAccountNumber();

	/**
	 * @return ����� � ������
	 */
	BigDecimal getAmount();

	/**
	 * @return ���� ��������
	 */
	Calendar getSendOperationDate();

	/**
	 * @return ��� �����������
	 */
	Long  getAuthorisationCode();

	/**
	 * @return ����� ����������� �����
	 */
	String   getEMail();

	/**
	 * @return ��������� �������� ��� ���
	 */
	Boolean   isDebit();

}
