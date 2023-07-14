package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.SecuritiesTransferClaim;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.gate.depo.*;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * ������� ��������� ���������� ������ ����� �� �����.
 * @author komarov
 * @ created 21.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ValidateSecurityCountHandler extends BusinessDocumentHandlerBase
{
	private static final String MESSAGE= "��� ���������� ��������� �� ��������� ������� ������ ����� ���� ����������� ����������� ���������� ������ �����. ��������� ���������� ������ ����� ��� �������� ���������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof SecuritiesTransferClaim))
			throw new DocumentException("�������� ��� �������. ��������� SecuritiesTransferClaim");

		SecuritiesTransferClaim payment = (SecuritiesTransferClaim) document;
		
		//���� ������������ ���� ������ �����, �� �������� ���������� ����� �� ����� ������� �� ������������.
		if(TransferOperation.RECEPTION == payment.getOperType())
		{
			return;
		}
		
		String divisionNumber     = payment.getDivisionNumber();
		String divisionType       = payment.getDivisionType();
		Long   securityCount      = payment.getSecurityCount();
		String registrationNumber = payment.getRegistrationNumber();

		DepoAccountLink depoAccLink = payment.getDepoLink();
		if (depoAccLink == null)
			throw new DocumentException("�� ������ ����-��-���� ����");

		try
		{
			DepoAccountPosition position = depoAccLink.getDepoAccountPositionInfo();
			List<DepoAccountDivision> accountDivisions = position.getDepoAccountDivisions();
			for(DepoAccountDivision accountDivision : accountDivisions)
			{
				if(accountDivision.getDivisionNumber().equals(divisionNumber) &&
				   accountDivision.getDivisionType().equals(divisionType))
				{
					List<DepoAccountSecurity> list = accountDivision.getDepoAccountSecurities();
					for(DepoAccountSecurity security : list)
					{
						if( registrationNumber.equals(security.getRegistrationNumber()) &&
							security.getStorageMethod() == DepoAccountSecurityStorageMethod.open &&
							security.getRemainder().compareTo(securityCount) < 0 )
						{
							  throw new DocumentLogicException(MESSAGE);
						}
					}
				}
			}
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
