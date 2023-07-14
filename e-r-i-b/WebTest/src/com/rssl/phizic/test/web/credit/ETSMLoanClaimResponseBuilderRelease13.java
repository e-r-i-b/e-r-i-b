package com.rssl.phizic.test.web.credit;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.ETSMLoanClaimStatus;
import com.rssl.phizic.config.loanclaim.ETSMMockConfig;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ApplicationStatusType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.SPNameType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.SrcRqType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 25.02.2014
 * @ $Author$
 * @ $Revision$
 */
class ETSMLoanClaimResponseBuilderRelease13
{
	StatusLoanApplicationRq makeResponse(LoanClaimTestForm request)
	{
		// 1. Шапка ответа
		StatusLoanApplicationRq response = new StatusLoanApplicationRq();
		response.setRqUID(new RandomGUID().getStringValue());
		response.setRqTm(Calendar.getInstance());
		response.setOperUID(request.getOperUID());
		response.setSPName(SPNameType.CRM);

		// 2. Исходный запрос
		response.setSrcRq(makeSourceRequestInfo(request));

		// 3. Статус
		response.setApplicationStatus(makeApplicationStatus(request));

		return response;
	}

	private SrcRqType makeSourceRequestInfo(LoanClaimTestForm request)
	{
		SrcRqType srcRqType = new SrcRqType();
		RandomGUID rqUID = new RandomGUID();

		srcRqType.setRqUID(rqUID.getStringValue());
		srcRqType.setRqTm(Calendar.getInstance());
		return srcRqType;
	}

	private ApplicationStatusType makeApplicationStatus(LoanClaimTestForm request)
	{
		ApplicationStatusType appStatus = new ApplicationStatusType();
		appStatus.setStatus(makeStatus(request));
		appStatus.setApproval(makeApproval(request));
		return appStatus;
	}

	private ApplicationStatusType.Status makeStatus(LoanClaimTestForm form)
	{
		ApplicationStatusType.Status status = new ApplicationStatusType.Status();
		status.setStatusCode(form.getClaimStatus());
		if (form.getClaimStatus()==ETSMLoanClaimStatus.ERROR || form.getClaimStatus()==ETSMLoanClaimStatus.INVALID || form.getClaimStatus()==ETSMLoanClaimStatus.REFUSED)
			status.setError(makeError());
		else
			status.setApplicationNumber(form.getClaimNumber());

		return status;
	}

	private ApplicationStatusType.Status.Error makeError()
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		ETSMMockConfig etsmMockConfig = loanClaimConfig.getETSMMockConfig();

		ApplicationStatusType.Status.Error error = new ApplicationStatusType.Status.Error();
		error.setErrorCode(etsmMockConfig.getLoanClaimErrorCode());
		error.setMessage(etsmMockConfig.getLoanClaimErrorMessage());
		return error;
	}

	private ApplicationStatusType.Approval makeApproval(LoanClaimTestForm request)
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		ETSMMockConfig etsmMockConfig = loanClaimConfig.getETSMMockConfig();

		if (etsmMockConfig.getLoanClaimStatusCode() != ETSMLoanClaimStatus.APPROVED)
			return null;

		ApplicationStatusType.Approval approval = new ApplicationStatusType.Approval();
		approval.setPeriodM(new Long(request.getApprovedPeriod()));
		approval.setAmount(new BigDecimal(request.getApprovedAmount()));
		approval.setInterestRate(new BigDecimal(request.getApprovedInterestRate()));
		return approval;
	}
}
