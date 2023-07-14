package com.rssl.phizic.test.web.credit;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.ETSMLoanClaimStatus;
import com.rssl.phizic.config.loanclaim.ETSMMockConfig;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ApplicationStatusType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SPNameType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SrcRqType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

/**
 * @author Nady
 * @ created 24.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ETSMLoanClaimResponseBuilderRelease19
{
	StatusLoanApplicationRq makeResponse(LoanClaimTestForm request)
	{
		// 1. Шапка ответа
		StatusLoanApplicationRq response = new StatusLoanApplicationRq();
		response.setRqUID(new RandomGUID().getStringValue());
		response.setRqTm(Calendar.getInstance());
		if (StringHelper.isNotEmpty(request.getOperUID()))
			response.setOperUID(request.getOperUID());
		response.setSPName(SPNameType.CRM);

		// 2. Исходный запрос
		response.setSrcRq(makeSourceRequestInfo(request));

		// 3. Статус
		response.setApplicationStatus(makeApplicationStatus(request));
		response.setPreapprovedFlag(request.getPeapprovedFlag());
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
		if (StringHelper.isEmpty(request.getOperUID()))
			appStatus.setApplication(makeApplication(request));
		appStatus.setReasonCode(request.getNeedVisitOfficeReason());

		return appStatus;
	}

	private ApplicationStatusType.Status makeStatus(LoanClaimTestForm form)
	{
		ApplicationStatusType.Status status = new ApplicationStatusType.Status();
		status.setStatusCode(form.getClaimStatus());
		if (form.getClaimStatus()== ETSMLoanClaimStatus.ERROR || form.getClaimStatus()==ETSMLoanClaimStatus.INVALID || form.getClaimStatus()==ETSMLoanClaimStatus.REFUSED)
			status.setError(makeError());
		else
			status.setApplicationNumber(form.getClaimNumber());
		status.setApplicationNumber(form.getApplicationNumber());
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

	private ApplicationStatusType.Application makeApplication(LoanClaimTestForm request)
	{
		ApplicationStatusType.Application application = new ApplicationStatusType.Application();
		application.setFullNameKI(request.getFioKI());
		application.setLoginKI(request.getLoginKI());
		application.setFullNameTM(StringHelper.getNullIfEmpty(request.getFioTM()));
		application.setLoginTM(StringHelper.getNullIfEmpty(request.getLoginTM()));
		application.setUnit(request.getDepartment());
		application.setChannel(request.getChannel());
		try
		{
			if (StringHelper.isNotEmpty(request.getAgreementDate()))
				application.setAppSigningDate(DateHelper.createCalendar(request.getAgreementDate(), null));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		application.setProduct(makeProduct(request));
		application.setApplicant(makeApplicant(request));
		application.setInsuranceInfo(makeInsuranceInfo(request));
		application.setType(request.getApplicationType());
		application.setAcctId(StringHelper.getNullIfEmpty(request.getAcctIdType()));
		application.setCardNum(StringHelper.getNullIfEmpty(request.getCardNum()));

		return application;
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

		if (StringHelper.isNotEmpty(request.getPreApprovedPeriod()))
			approval.setPrePeriodM(new Long(request.getPreApprovedPeriod()));
		if (StringHelper.isNotEmpty(request.getPreApprovedAmount()))
			approval.setPreAmount(new BigDecimal(request.getPreApprovedAmount()));
		if (StringHelper.isNotEmpty(request.getPreApprovedInterestRate()))
			approval.setPreInterestRate(new BigDecimal(request.getPreApprovedInterestRate()));
		return approval;
	}

	private ApplicationStatusType.Application.Product makeProduct(LoanClaimTestForm request)
	{
		ApplicationStatusType.Application.Product product = new ApplicationStatusType.Application.Product();
		if (StringHelper.isNotEmpty(request.getLoanAmount()))
			product.setAmount(new BigDecimal(request.getLoanAmount()));
		product.setType(request.getType());
		product.setCode(request.getProductCode());
		product.setSubProductCode(request.getSubProductCode());
		product.setCurrency(request.getCurrency());
		product.setPaymentType(request.getPaymentType());
		if (StringHelper.isNotEmpty(request.getLoanPeriod()))
			product.setPeriod(new Long(request.getLoanPeriod()));

		return product;
	}

	private ApplicationStatusType.Application.Applicant makeApplicant(LoanClaimTestForm request)
	{
		ApplicationStatusType.Application.Applicant applicant = new ApplicationStatusType.Application.Applicant();
		applicant.setFirstName(request.getFirstName());
		applicant.setMiddleName(StringHelper.getNullIfEmpty(request.getPatrName()));
		applicant.setLastName(request.getSurName());
		applicant.setCitizenship(request.getCitizen());
		applicant.setIdSeries(request.getDocumentSeries());
		applicant.setIdNum(request.getDocumentNumber());
		applicant.setIssueCode(request.getPassportIssueByCode());
		applicant.setIssuedBy(request.getPassportIssueBy());
		applicant.setAddress("Тридевятое королевство ");
		try
		{
			if (StringHelper.isNotEmpty(request.getPassportIssueDate()))
				applicant.setIssueDt(DateHelper.createCalendar(request.getPassportIssueDate(), null));
			if (StringHelper.isNotEmpty(request.getOldPassportIssueDate()))
				applicant.setPrevIssueDt(DateHelper.createCalendar(request.getOldPassportIssueDate(), null));
			if (StringHelper.isNotEmpty(request.getBirthDay()))
				applicant.setBirthday(DateHelper.createCalendar(request.getBirthDay(), null));
		}
		catch (ParseException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		applicant.setPrevPassportInfoFlag(request.isHasOldPassport());
		applicant.setPrevIdSeries(StringHelper.getNullIfEmpty(request.getOldDocumentSeries()));
		applicant.setPrevIdNum(StringHelper.getNullIfEmpty(request.getOldDocumentNumber()));
		applicant.setPrevIssuedBy(StringHelper.getNullIfEmpty(request.getOldPassportIssueBy()));
		return applicant;
	}

	private ApplicationStatusType.Application.InsuranceInfo makeInsuranceInfo(LoanClaimTestForm request)
	{
		ApplicationStatusType.Application.InsuranceInfo insuranceInfo = new ApplicationStatusType.Application.InsuranceInfo();
		if (StringHelper.isEmpty(request.getBusinessProcess())
				|| StringHelper.isEmpty(request.getInsuranceProgram()))
		{
			return null;
		}
		insuranceInfo.setBusinessProcess(request.getBusinessProcess());
		insuranceInfo.setInsuranceProgram(request.getInsuranceProgram());

		insuranceInfo.setIncludeInsuranceFlag(request.isIncludeInsuranceFlag());
		try
		{
			insuranceInfo.setInsurancePremium(Double.parseDouble(request.getInsurancePremium()));
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			return null;
		}
		return insuranceInfo;
	}
}
