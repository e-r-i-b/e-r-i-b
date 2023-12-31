package com.rssl.phizic.business.loanclaim.etsm;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.loanclaim.ETSMLoanClaimStatus;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ApplicationStatusType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq;
import org.apache.commons.lang.StringUtils;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Rtischeva
 * @ created 09.03.15
 * @ $Author$
 * @ $Revision$
 */
public class ETSMLoanClaimUpdaterRelease13
{
	private final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private final JAXBContext jaxbContext;

	/**
	 * ctor
	 */
	public ETSMLoanClaimUpdaterRelease13()
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(StatusLoanApplicationRq.class);
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
		// 1. ��������� ������
		StatusLoanApplicationRq request = readRequest(requestXML);

		// 2. ������� ������
		ExtendedLoanClaim claim = findLoanClaim(request);

		// 3. ���������� ������ ���������
		saveETSMErrors(claim, request);

		// 4. ��������� ������ ������
		if (checkClaimStatus(request.getApplicationStatus().getStatus().getStatusCode(), claim.getState().getCode()))
			throw new IllegalStateException("������ �������� ������ ��������� ������. docid=" + claim.getId() + " state=" + claim.getState());

		// 5. ��������� ������ � ������ ������
		saveETSMStatus(claim, request);
	}

	private StatusLoanApplicationRq readRequest(String requestXML) throws BusinessException
	{
		try
		{
			// ��������� �� XSD-����� ����� �� ��������, �.�. ��������� - ������ ������-���������
			Reader reader = new StringReader(requestXML);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (StatusLoanApplicationRq) unmarshaller.unmarshal(reader);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	private ExtendedLoanClaim findLoanClaim(StatusLoanApplicationRq request) throws BusinessException
	{
		GateExecutableDocument document = businessDocumentService.findByExternalId(request.getOperUID());
		if (document == null)
			throw new IllegalArgumentException("�� ������� ��������� ������ � operUID=" + request.getOperUID());

		if (!(document instanceof ExtendedLoanClaim))
			throw new IllegalStateException("��������� ExtendedLoanClaim. docid = " + document.getId());

		return (ExtendedLoanClaim) document;
	}

	private void saveETSMErrors(ExtendedLoanClaim claim, StatusLoanApplicationRq request)
	{
		ApplicationStatusType.Status etsmClaimStatus = request.getApplicationStatus().getStatus();
		ApplicationStatusType.Status.Error etsmClaimError = etsmClaimStatus.getError();

		if (etsmClaimError != null)
		{
			claim.setEtsmErrorCode(etsmClaimError.getErrorCode());
			claim.setErrorMessage(etsmClaimError.getMessage());
		}
	}

	private void saveETSMStatus(ExtendedLoanClaim claim, StatusLoanApplicationRq request) throws BusinessException, BusinessLogicException
	{
		ApplicationStatusType.Status etsmClaimStatus = request.getApplicationStatus().getStatus();
		ApplicationStatusType.Approval etsmClaimApproval = request.getApplicationStatus().getApproval();

		switch (etsmClaimStatus.getStatusCode())
		{
			// 4.A �� ������ ������ �����
			case ETSMLoanClaimStatus.INVALID:
				claim.setRefuseReason("��������� ��������� ������");
				claim.setRefusedDate(Calendar.getInstance());
				fireLoanClaimCommand(claim, new DocumentCommand(DocumentEvent.REFUSE));
				break;

			case ETSMLoanClaimStatus.REFUSED:
				claim.setRefuseReason(etsmClaimStatus.getError().getMessage());
				claim.setRefusedDate(Calendar.getInstance());
				fireLoanClaimCommand(claim, new DocumentCommand(DocumentEvent.REFUSE));
				break;

			// 4.B ������ ���������������� � TSM => ������ ������ � ���� �� ��������
			case ETSMLoanClaimStatus.REGISTERED:
				if (StringHelper.isNotEmpty(etsmClaimStatus.getApplicationNumber()))
					claim.setETSMClaimID(etsmClaimStatus.getApplicationNumber());

				businessDocumentService.addOrUpdate(claim);
				break;

			// 4.C ������ ��������
			case ETSMLoanClaimStatus.APPROVED:
				if (StringHelper.isNotEmpty(etsmClaimStatus.getApplicationNumber()))
					claim.setETSMClaimID(etsmClaimStatus.getApplicationNumber());

				Long period = etsmClaimApproval.getPeriodM();
				BigDecimal amount = etsmClaimApproval.getAmount();
				BigDecimal rate =   etsmClaimApproval.getInterestRate();
				boolean haveIssuedFields= true;
				List<String> errorIssuedFields = new ArrayList<String>();

				if (period == null)
				{
					haveIssuedFields = false;
					errorIssuedFields.add("������");
				}
				if (amount == null)
				{
					haveIssuedFields = false;
					errorIssuedFields.add("�����");
				}
				if (rate == null)
				{
					haveIssuedFields = false;
					errorIssuedFields.add("������");
				}
				if (haveIssuedFields)
				{
					claim.setApprovedPeriod(period);
					claim.setApprovedAmount(amount);
					claim.setApprovedInterestRate(rate);
				}
				else
					throw new BusinessException("����������� ������ �� ��������� �������" + StringUtils.join(errorIssuedFields, "; "));

				fireLoanClaimCommand(claim, new DocumentCommand(DocumentEvent.APPROVE));
				break;

			default:
				throw new IllegalArgumentException("����������� ������ ������ � ETSM: " + etsmClaimStatus);
		}
	}

	private void fireLoanClaimCommand(ExtendedLoanClaim claim, DocumentCommand command) throws BusinessException
	{
		try
		{
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
			executor.initialize(claim);
			executor.fireEvent(new ObjectEvent(command.getEvent(), "system"));
			businessDocumentService.addOrUpdate(claim);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	private boolean checkClaimStatus(int status, String statusCode)
	{
		switch (status){
			case ETSMLoanClaimStatus.INVALID:
				return "REFUSED".equals(statusCode);
			case ETSMLoanClaimStatus.REFUSED:
				return "REFUSED".equals(statusCode);
			case ETSMLoanClaimStatus.REGISTERED:
				return true;
			case ETSMLoanClaimStatus.APPROVED:
				return "APPROVED".equals(statusCode);
			default:
				return false;
		}

	}
}

