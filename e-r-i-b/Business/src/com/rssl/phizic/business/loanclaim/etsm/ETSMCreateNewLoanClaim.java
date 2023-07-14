package com.rssl.phizic.business.loanclaim.etsm;

import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;

import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * �������� ������� �������� ������ ���� � ETSM
 *
 * @ author: Gololobov
 * @ created: 24.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ETSMCreateNewLoanClaim
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final CRMMessageService crmMessageService = new CRMMessageService();

	private final JAXBContext jaxbContext;

	/**
	 * ctor
	 */
	public ETSMCreateNewLoanClaim()
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(ERIBSendETSMApplRq.class);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ETSM] ���� �� �������� JAXB-���������", e);
		}
	}

	/**
	 * ���������� ������� ����������� ������ �� ������
	 * @param requestXML - ������ �� TransactSM, �� �������� ������������ ����������
	 */
	public void update(String requestXML) throws BusinessException, BusinessLogicException
	{
		ERIBSendETSMApplRq request = readRequest(requestXML);

		ExtendedLoanClaim claim = findLoanClaim(request);
		//���������� ������ ������� �� �������
		initLoanClaimFromRequest(request, claim);
		//�������� � ETSM
		try
		{
			crmMessageService.initiationCreateNewLoanClaim(claim);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private ERIBSendETSMApplRq readRequest(String requestXML) throws BusinessException
	{
		try
		{
			// ��������� �� XSD-����� ����� �� ��������, �.�. ��������� - ������ ������-���������
			Reader reader = new StringReader(requestXML);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (ERIBSendETSMApplRq) unmarshaller.unmarshal(reader);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��������� ������
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	private ExtendedLoanClaim findLoanClaim(ERIBSendETSMApplRq request) throws BusinessException
	{
		String loanClaimNumber = request.getApplication().getNumber();
		GateExecutableDocument claim = businessDocumentService.findExtendedLoanClaimByUID(loanClaimNumber);
		if (claim == null)
			throw new IllegalArgumentException("�� ������� ��������� ������ � operUID=" + loanClaimNumber);

		if (!(claim instanceof ExtendedLoanClaim))
			throw new IllegalStateException("��������� ExtendedLoanClaim. docid = " + claim.getId());

		return (ExtendedLoanClaim) claim;
	}

	private void initLoanClaimFromRequest(ERIBSendETSMApplRq request, ExtendedLoanClaim claim) throws BusinessException
	{
		if (claim == null)
			return;
		String divisionId = request.getApplication().getDivisionId();
		if (StringHelper.isEmpty(divisionId))
			return;
		int regionLength = 2;
		int branchLength = 4;

		String region = divisionId.substring(0, regionLength);
		String branch = divisionId.substring(regionLength, regionLength + branchLength);
		String office = StringHelper.removeLeadingZeros(divisionId.substring(regionLength + branchLength));
		//�������������, ��� ��������� ��� �������� ������ �� �������� ������ ���� � ETSM
		claim.setClaimDrawDepartmentETSMCode(region, office, branch);
		//����� ���������� (��) � CRM, ������������� ������ � ETSM �� CRM
		claim.setLoginKI(request.getApplication().getCreatedByLogin());

		businessDocumentService.addOrUpdate(claim);
	}
}
