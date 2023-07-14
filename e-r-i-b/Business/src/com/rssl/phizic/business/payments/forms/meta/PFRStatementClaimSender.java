package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.documents.PFRStatementClaimSendMethod;
import com.rssl.phizic.gate.pfr.StatementStatus;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� � ���� ������ �� ������� �� ���
 */
public class PFRStatementClaimSender extends DefaultBusinessDocumentSender
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof PFRStatementClaim))
			throw new IllegalArgumentException("��������� ������ �� ������� �� ��� (PFRStatementClaim)");
		PFRStatementClaim claim = (PFRStatementClaim) document;

		log.debug("���������� ������ �� ������� ��� � ����. " +
				"����� �������� " + claim.getSendMethod() + ". " +
				"���������� ������: " + claim.isReady() + ". " +
				"CLAIM_ID=" + claim.getId());

		// 0. ����� ��������� ���������� ������ � ������� ������
		claim.setReady(null);
		claim.setReadyDescription(null);
		claim.setRefusingReason(null);

		// 1. ���������� ��������
		super.process(claim, stateMachineEvent);

		// 2. ����������� ������� ���������� �������
		examineStatementStatus(claim);
	}

	private void examineStatementStatus(PFRStatementClaim claim) throws DocumentException, DocumentLogicException
	{
		StatementStatus statementStatus = claim.isReady();
		switch (statementStatus)
		{
			case READY:
				throw new IllegalStateException("���� �� ������ ���������� ������ � ��������� READY. CLAIM_ID=" + claim.getId());

			case AVAILABLE:
				log.debug("���� �������, ��� ������� ������ � ��������� (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				break;

			case NOT_YET_AVAILABLE:
				log.debug("���� �������, ��� ������� ��� �� ������ (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				break;

			case UNAVAILABLE_DUE_INFO_REQUIRED:
				log.debug("���� �������, ��� � ������ �� ������� �� ������� ������ (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				if (StringHelper.isEmpty(claim.getReadyDescription()))
					throw new IllegalStateException("�� ������� �������� ������� ������ ������");
				// ������� ��������� ������ (� ������� �����������)
				repeatClaim(claim);
				break;

			case UNAVAILABLE_DUE_UNKNOWN_PERSON:
				log.debug("���� �������, ��� ��� ���������� ���� ��� ������� (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				if (StringHelper.isEmpty(claim.getReadyDescription()))
					throw new IllegalStateException("�� ������� �������� ������� ������ ������");
				break;

			case UNAVAILABLE_DUE_FAIL:
				log.debug("���� �������, ��� ��� ��������� ������� ��������� ���� �� ������� ������� (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				if (StringHelper.isEmpty(claim.getReadyDescription()))
					throw new IllegalStateException("�� ������� �������� ������� ������ ������");
				break;

			default:
				// ����� ������� ����� ����� �������������,
				// � ����������� ��� �� ����� �������� ���� ���� � ����
				throw new IllegalStateException("����������� ������ ������: " + statementStatus);
		}
	}

	/**
	 * ������� ��������� ������ ��� ��� (������ ��������� �����)
	 * ���� ������ ��� ���������� �� �����, ��� ��������� � �����
	 * @param claim - ������ � �������� � ��������� ADDITIONAL_INFO_REQUIRED
	 */
	private void repeatClaim(PFRStatementClaim claim) throws DocumentException, DocumentLogicException
	{
		switch (claim.getSendMethod())
		{
			case USING_PASPORT_WAY:
				log.debug("�������� ������������ ��������� ������ �� �����. CLAIM_ID=" + claim.getId());
				PFRStatementClaimHelper.prepareClaim(claim, PFRStatementClaimSendMethod.USING_SNILS);
				break;

			case USING_SNILS:
				log.debug("�� ����� ������ ��� ����������, ������ ������� �� �����. CLAIM_ID=" + claim.getId());
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				break;

			default:
				throw new IllegalStateException("����������� ������ �������� ������: " + claim.getSendMethod());
		}
	}
}
