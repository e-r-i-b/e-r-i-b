package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.documents.AbstractCardTransfer;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 05.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface LoanTransfer extends AbstractCardTransfer
{
	/**
	 * ������� id �������
	 */
	String getLoanExternalId();

	/**
	 * ����� �������� �����
	 */
	String getAccountNumber();

	/**
	 * ����� ���������� ��������
	 */
	String getAgreementNumber();

	/**
	 * ������������� ��������
	 */
	String getIdSpacing();

	/**
	 * ���� ��������� ��������
	 */
	Calendar getSpacingDate();
}