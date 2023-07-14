package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.gate.loanclaim.type.Spouse;

import java.math.BigDecimal;

/**
 * ���������, ��� ����� ��������� ������ � ���. ������ ������ (��� �� ������) ������ ������
 *
 * @author EgorovaA
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLoanClaimTotalIncomeHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("�������� ��� ������ id=" + ((BusinessDocument) document).getId() + " (��������� ExtendedLoanClaim)");
		}

		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;

		BigDecimal basicIncome = loanClaim.getApplicantBasicIncome();
		BigDecimal additionalIncome = loanClaim.getApplicantAdditionalIncome();
		BigDecimal income = basicIncome.add(additionalIncome);
		BigDecimal familyIncome = loanClaim.getApplicantFamilyIncome();

		// A. CHG070592
		// ���� � ������� ���� ������ (��) � �� (���) ��������� �� �� ���������"
		// ������ �����������  ��������� ��������
		// "����� �����" > "���. �����" + "�������������� �����
		Spouse spouse = loanClaim.getApplicantSpouse();
		if (spouse != null && !spouse.isDependant())
		{
			if ( !(familyIncome.compareTo(income) > 0) )
				throw new DocumentLogicException("�������������� ����� ����� ������ ���� ������ ����� ��������������� ��������� ������ � ��������������� ��������������� ������. ����������, ��������� ���������� �����");
		}

		// B. CHG071765
		// ���� ������� ������\�� ������� ��� �������� ��������� ������� ������/������� � ��� ����/���� ���������� ���� ��� ���������, ��
		// "����� �����" ="���. �����" + "�������������� �����
		else
		{
			if ( !(familyIncome.compareTo(income) == 0) )
				throw new DocumentLogicException("�������������� ����� ����� ������ ���� ����� ����� ��������������� ��������� ������ � ��������������� ��������������� ������. ����������, ��������� ���������� �����");
		}
	}
}
