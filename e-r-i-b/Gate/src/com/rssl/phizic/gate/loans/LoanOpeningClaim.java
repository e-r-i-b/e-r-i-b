package com.rssl.phizic.gate.loans;

import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;

import java.util.Iterator;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ��������� �������
 */
public interface LoanOpeningClaim extends SynchronizableDocument
{
   /**
    * ����� �������������� �������
    *
    * @return �����
    */
   Money getLoanAmount();
   /**
    * ����������� �������� ��������.
    * ����� ���� null.
    *
    * @return �����
    */
   Money getSelfAmount();
   /**
    * ��������� ������������� ������������� (������).
    * ����� ���� null.
    *
    * @return �����
    */
   Money getObjectAmount();
   /**
    * ���� �������������� �������
    *
    * @return ����
    */
   DateSpan getDuration();
   /**
    * ������� ID ������������ ������� �������
    * Domain: ExternalID
    *
    * @return id
    */
   String getConditionsId();
   /**
    * ������� ID ������������� ����� � ������� ��������������� ������
    * Domain: ExternalID
    *
    * @return ������� ID
    */
   String getOfficeExternalId();
   /**
    * �������� ������� �� ������� ������
    *
    * @return ��������
    */
   Iterator<QuestionnaireAnswer> getQuestionnaireIterator();
   /**
    * �������� �� ������� �����������.
    * ���� ����������� ���, �� �������� �� ������ ������.
    *
    * @return ��������
    */
   Iterator<LoanOpeningClaim> getGuarantorClaimsIterator();
   /**
    * ����� ������������� �������
    *
    * @return �����
    */
   Money getApprovedAmount();
   /**
    * ���������� ������������ �����. ����������� ������
    *
    * @param amount �����
    */
   void setApprovedAmount(Money amount);
   /**
    * ���� ������������� �������
    *
    * @return ����
    */
   DateSpan getApprovedDuration();
   /**
    * ���������� ������������ ����. ����������� ������
    *
    * @param dateSpan ����
    */
   void setApprovedDuration(DateSpan dateSpan);

	/**
	 * ����� ������ ��� ����������� ������������
	 * @return
	 */
	String getClaimNumber();

	/**
	 *  ���������� ����� ������ ��� ����������� ������������
 	 * @param claimNumber ����� ������ � Loans
	 */
	void setClaimNumber(String claimNumber);
}
