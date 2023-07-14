package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.pfr.PFRStatement;
import com.rssl.phizic.business.pfr.PFRStatementService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.pfr.PFRService;
import com.rssl.phizic.gate.pfr.StatementStatus;

import java.rmi.RemoteException;

/**
 * @author Erkin
 * @ created 17.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��������� ������� �� ����
 */
public class PFRStatementLoader extends BusinessDocumentHandlerBase
{
	/**
	 * �������� ������ ��� ��������� ������� ��� �� ����
	 */
	private static final PFRService pfrService = GateSingleton.getFactory().service(PFRService.class);

	/**
	 * ���������� ������ ��� ���������� ������� ��� � ����
	 */
	private static final PFRStatementService pfrStatementService = new PFRStatementService();

	///////////////////////////////////////////////////////////////////////////

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof PFRStatementClaim))
			throw new IllegalArgumentException("��������� ������ �� ������� �� ��� (PFRStatementClaim)");

		PFRStatementClaim claim = (PFRStatementClaim) document;

		// ������ ��������� �����-������
		if (claim.isReady() != StatementStatus.AVAILABLE)
			throw new IllegalStateException("������� ��� �� ������ � ���������, " +
					"�.�. ������ ��������� � ������� " + claim.isReady() + ". CLAIM_ID=" + claim.getId());

		log.debug("��������� ������� ��� �� ����. CLAIM_ID=" + claim.getId());

		// 1. ����������� XML-�������� ������� �� ����
		String statementXml = loadStatement(claim);
		if (statementXml == null) {
			log.error("���� ������� ������ ������� ���, �� ��� � � �� ������. " +
					"������=" + claim.isReady() + ", " + claim.getReadyDescription() + ". " +
					"CLAIM_ID=" + claim.getId());
			return;
		}
		log.debug("������� ��� ��������. CLAIM_ID=" + claim.getId());

		// 2. ��������� ������� � ����
		saveStatement(claim, statementXml);
		log.debug("������� ��� ���������. CLAIM_ID=" + claim.getId());

		// 3. ��������� ������ �������
		claim.setReady(StatementStatus.READY);
	}

	private String loadStatement(PFRStatementClaim claim) throws DocumentException, DocumentLogicException
	{
		try
		{
			return pfrService.getStatement(claim);
		}
		catch (RemoteException ex)
		{
			log.error("���� �� ������� �������� ������� ��� �� ����. " + ex.getMessage() + ". " +
					"CLAIM_ID="+claim.getId(), ex);
			// ������� ���� �� ������� �������� => ������ ������� � ��������� AVAILABLE
			return null;
		}
		catch (GateException ex)
		{
			log.error("�� ������� �������� ������� �� �����. CLAIM_ID=" + claim.getId());
			throw new DocumentException(ex);
		}
		catch (GateLogicException ex)
		{
			log.error("�� ������� �������� ������� �� �����. CLAIM_ID=" + claim.getId());
			throw new DocumentLogicException(ex);
		}
	}

	private void saveStatement(PFRStatementClaim claim, String statementXml) throws DocumentException
	{
		try
		{
			PFRStatement statement = new PFRStatement();
			statement.setClaimId(claim.getId());
			statement.setStatementXml(statementXml);
			pfrStatementService.add(statement);
		}
		catch (BusinessException ex)
		{
			log.error("�� ������� ��������� ������� � ��. CLAIM_ID=" + claim.getId());
			throw new DocumentException(ex);
		}
	}
}
