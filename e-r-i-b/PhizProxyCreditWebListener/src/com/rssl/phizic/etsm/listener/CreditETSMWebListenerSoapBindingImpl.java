/**
 * CreditETSMWebListenerSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.etsm.listener;

import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.etsm.offer.OfferPriorService;
import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.etsm.listener.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import org.apache.axis.types.UnsignedInt;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CreditETSMWebListenerSoapBindingImpl implements com.rssl.phizic.etsm.listener.generated.CreditETSMWebListener
{
	private static final OfferPriorService OFFER_PRIOR_SERVICE = new OfferPriorService();

	//время жизни оферты в днях
	private final static int OFFER_LIFE_DAYS = 31;

	public QueryOfferRsType queryOffer(QueryOfferRqType parameters) throws RemoteException
	{
		String appNumber = parameters.getApplicationNumber();
		List<OfferOfficePrior> offerList = Collections.emptyList();
		try
		{
			Date lifeDate = DateHelper.add(new Date(), 0, 0, OFFER_LIFE_DAYS);
			offerList = OFFER_PRIOR_SERVICE.getOfficeOffers(appNumber, lifeDate, "ACTIVE");
		}
		catch (BusinessException e)
		{
			return getRs(1, e.getMessage());
		}

		if (offerList.isEmpty())
			return getRs(-1, "По applicationNumber:" + appNumber + " оферт не найдено.");

		QueryOfferRsType result = getRs(0,"");
		//Данные по клиенту извлекаем из первой альтернотивной оферте, потому что они одинаковы для всех оферт с одним  appNumber
		OfferOfficePrior o = offerList.get(0);
		IdentityType identity = new IdentityType();
		//ФИО + ДР
		identity.setFirstName(o.getFirstName());
		identity.setLastName(o.getLastName());
		identity.setMiddleName(o.getMiddleName());
		identity.setBirthday(DateHelper.toDate(o.getBirthDate()));
		identity.setRegistration(o.getRegistrationAddress());
		//ДУЛ
		IdentityCardType identityCardType = new IdentityCardType();
		identityCardType.setIdNum(o.getIdNum());
		identityCardType.setIdSeries(o.getIdSeries());
		identityCardType.setIdType(o.getIdType());
		identityCardType.setIdIssueBy(o.getIdIssueBy());
		identityCardType.setIdIssueDate(DateHelper.toDate(o.getIdIssueDate()));

		identity.setIdentityCard(identityCardType);
		result.setIdentity(identity);
		OfferType offerType = new OfferType();
		//данные по кредитному продукту
		offerType.setDepartment(o.getDepartment());
		offerType.setProductTypeCode(o.getProductTypeCode());
		offerType.setProductCode(o.getProductCode());
		offerType.setSubProductCode(o.getSubProductCode());
		OfferTypeCurrency currency = new OfferTypeCurrency(o.getCurrency());
		offerType.setCurrency(currency);
		offerType.setTypeOfIssue(o.getTypeOfIssue());
		offerType.setAccauntNumber(o.getAccountNumber());
		//данные по кредиту  .
		AlternativeType[] alternativeArr = new  AlternativeType[offerList.size()];
		for (int i = 0 ; i < offerList.size(); i++)
		{
			OfferOfficePrior offer = offerList.get(i);
			AlternativeType alternativeType = new AlternativeType();
			alternativeType.setId(offer.getId());
			alternativeType.setAltAmount(offer.getAltAmount());;
			alternativeType.setAltAnnuitentyPayment(offer.getAltAnnuityPayment());;
			alternativeType.setAltFullLoanCost(offer.getAltFullLoanCost());;
			alternativeType.setAltCreditCardlimit(offer.getAltCreditCardLimit());;
			alternativeType.setAltInterestRate(offer.getAltInterestRate());
			alternativeType.setAltPeriodM(new UnsignedInt(offer.getAltPeriod()));
			alternativeArr[i] =  alternativeType;
		}
		offerType.setAlternative(alternativeArr);
		offerType.setApplicationNumber(appNumber);
		offerType.setOfferStatus(o.getState());
		offerType.setOfferDate(DateHelper.toCalendar(o.getOfferDate()));
		offerType.setClientCategory(o.getClientCategory());
		result.setOffer(offerType);
		return result;
	}

	public DeleteOfferRsType deleteOffer(DeleteOfferRqType parameters) throws RemoteException
	{
		String appNumber = parameters.getApplicationNumber();
		Calendar offerDate = parameters.getOfferDate();
		int statusCode = 0;
		String statusDesc = "";
		try
		{
			OFFER_PRIOR_SERVICE.deleteOffers(appNumber, DateHelper.toDate(offerDate));

		}
		catch (BusinessException e)
		{
			statusCode = 1;
			statusDesc = e.getMessage();
		}

		DeleteOfferRsType rs = new DeleteOfferRsType();
		StatusType status = new StatusType();
		rs.setRqUID(new RandomGUID().getStringValue());
		rs.setRqTm(Calendar.getInstance());

		status.setStatusCode(statusCode);
		status.setStatusDesc(statusDesc);
		rs.setStatus(status);
		return rs;
	}

	public AddOfficeLoanRsType addOfficeLoan(AddOfficeLoanRqType parameters) throws RemoteException
	{
		OfficeLoanClaim claim = new OfficeLoanClaim();
		OfficeLoanClaimType officeLoanClaimType = parameters.getOfficeLoanClaim();
		claim.setApplicationNumber(officeLoanClaimType.getApplicationNumber());
		claim.setState(officeLoanClaimType.getState().intValue());
		claim.setFioKI(officeLoanClaimType.getFioKI());
		claim.setLoginKI(officeLoanClaimType.getLoginKI());
		claim.setFioTM(officeLoanClaimType.getFioTM());
		claim.setLoginTM(officeLoanClaimType.getLoginTM());
		claim.setChannel(officeLoanClaimType.getChannel());
		claim.setAgreementDate(DateHelper.toCalendar(officeLoanClaimType.getAgreementDate()));
		claim.setDepartment(officeLoanClaimType.getDepartment());
		claim.setNeedVisitOfficeReason(officeLoanClaimType.getNeedVisitOfficeReason());
		claim.setType(officeLoanClaimType.getType());
		claim.setProductCode(officeLoanClaimType.getProductCode());
		claim.setSubProductCode(officeLoanClaimType.getSubProductCode());
		claim.setLoanApprovedAmount(officeLoanClaimType.getLoanAmount());
		claim.setLoanApprovedPeriod(officeLoanClaimType.getLoanPeriod().intValue());
		claim.setLoanApprovedRate(officeLoanClaimType.getLoanRate());
		claim.setProductPeriod(officeLoanClaimType.getProductPeriod().intValue());
		claim.setProductAmount(officeLoanClaimType.getProductAmount());
		claim.setCurrency(officeLoanClaimType.getCurrency());
		claim.setPaymentType(officeLoanClaimType.getPaymentType());
		claim.setFirstName(officeLoanClaimType.getFirstName());
		claim.setSurName(officeLoanClaimType.getSurName());
		claim.setPatrName(officeLoanClaimType.getPatrName());
		claim.setBirthDay(DateHelper.toCalendar(officeLoanClaimType.getBirthDay()));
		claim.setCitizen(officeLoanClaimType.getCitizen());
		claim.setDocumentSeries(officeLoanClaimType.getDocumentSeries());
		claim.setDocumentNumber(officeLoanClaimType.getDocumentNumber());
		claim.setPassportIssueDate(DateHelper.toCalendar(officeLoanClaimType.getPassportIssueDate()));
		claim.setPassportIssueByCode(officeLoanClaimType.getPassportIssueByCode());
		claim.setPassportIssueBy(officeLoanClaimType.getPassportIssueBy());
		claim.setHasOldPassport(officeLoanClaimType.isHasOldPassport());
		claim.setTypeOfIssue(officeLoanClaimType.getTypeOfIssue());
		claim.setAccountNumber(officeLoanClaimType.getAcctId());
		claim.setCardNumber(officeLoanClaimType.getCardNum());
		if (claim.isHasOldPassport())
		{
			claim.setOldDocumentSeries(officeLoanClaimType.getOldDocumentSeries());
			claim.setOldDocumentNumber(officeLoanClaimType.getOldDocumentNumber());
			claim.setOldPassportIssueDate(DateHelper.toCalendar(officeLoanClaimType.getOldPassportIssueDate()));
			claim.setOldPassportIssueBy(officeLoanClaimType.getOldPassportIssueBy());
		}
		claim.setPreapproved(officeLoanClaimType.isPreapproved());
		String result = OFFER_PRIOR_SERVICE.addOfficeLoanClaim(claim);
		return new AddOfficeLoanRsType(new RandomGUID().getStringValue(), Calendar.getInstance(), result);
	}

	public GetOfficeLoansByFIODulBDRs getOfficeLoansByFIODulBD(GetOfficeLoansByFIODulBDRq parameters) throws RemoteException
	{
		GetOfficeLoansByFIODulBDRs loansByFIODulBDRs = new GetOfficeLoansByFIODulBDRs();
		loansByFIODulBDRs.setRqUID(new RandomGUID().getStringValue());
		loansByFIODulBDRs.setRqTm(Calendar.getInstance());
		try
		{
			List<OfficeLoanClaim> officeLoanClaimList = OFFER_PRIOR_SERVICE.findOfficeLoanClaimsByFIODulBD(parameters.getFIO(), parameters.getDUL(), parameters.getBirthDay());
			int i=0;
			if (officeLoanClaimList != null)
			{
				OfficeLoanClaimType[] officeLoanClaimTypes = new OfficeLoanClaimType[officeLoanClaimList.size()];
				loansByFIODulBDRs.setOfficeLoanClaims(officeLoanClaimTypes);
			}
			for (OfficeLoanClaim claim : officeLoanClaimList)
			{
				OfficeLoanClaimType officeLoanClaimType = new OfficeLoanClaimType();

				officeLoanClaimType.setState(BigInteger.valueOf(claim.getState()));

				officeLoanClaimType.setApplicationNumber(claim.getApplicationNumber());
				officeLoanClaimType.setFioKI(claim.getFioKI());
				officeLoanClaimType.setLoginKI(claim.getLoginKI());
				officeLoanClaimType.setFioTM(claim.getFioTM());
				officeLoanClaimType.setLoginTM(claim.getLoginTM());
				officeLoanClaimType.setChannel(claim.getChannel());
				officeLoanClaimType.setAgreementDate(DateHelper.toDate(claim.getAgreementDate()));
				officeLoanClaimType.setDepartment(claim.getDepartment());
				officeLoanClaimType.setNeedVisitOfficeReason(claim.getNeedVisitOfficeReason());
				officeLoanClaimType.setType(claim.getType());
				officeLoanClaimType.setProductCode(claim.getProductCode());
				officeLoanClaimType.setSubProductCode(claim.getSubProductCode());
				officeLoanClaimType.setLoanAmount(claim.getLoanApprovedAmount());
				officeLoanClaimType.setLoanPeriod(BigInteger.valueOf(claim.getLoanApprovedPeriod()));
				officeLoanClaimType.setLoanRate(claim.getLoanRate());
				officeLoanClaimType.setProductAmount(claim.getProductAmount());
				officeLoanClaimType.setProductPeriod(BigInteger.valueOf(claim.getProductPeriod()));
				officeLoanClaimType.setCurrency(claim.getCurrency());
				officeLoanClaimType.setPaymentType(claim.getPaymentType());
				officeLoanClaimType.setFirstName(claim.getFirstName());
				officeLoanClaimType.setSurName(claim.getSurName());
				officeLoanClaimType.setPatrName(claim.getPatrName());
				officeLoanClaimType.setBirthDay(DateHelper.toDate(claim.getBirthDay()));
				officeLoanClaimType.setCitizen(claim.getCitizen());
				officeLoanClaimType.setDocumentSeries(claim.getDocumentSeries());
				officeLoanClaimType.setDocumentNumber(claim.getDocumentNumber());
				officeLoanClaimType.setPassportIssueDate(DateHelper.toDate(claim.getPassportIssueDate()));
				officeLoanClaimType.setPassportIssueByCode(claim.getPassportIssueByCode());
				officeLoanClaimType.setPassportIssueBy(claim.getPassportIssueBy());
				officeLoanClaimType.setHasOldPassport(claim.isHasOldPassport());
				officeLoanClaimType.setTypeOfIssue(claim.getTypeOfIssue());
				officeLoanClaimType.setAcctId(claim.getAccountNumber());
				officeLoanClaimType.setCardNum(claim.getCardNumber());
				if (officeLoanClaimType.isHasOldPassport())
				{
					officeLoanClaimType.setOldDocumentSeries(claim.getOldDocumentSeries());
					officeLoanClaimType.setOldDocumentNumber(claim.getOldDocumentNumber());
					officeLoanClaimType.setOldPassportIssueDate(DateHelper.toDate(claim.getOldPassportIssueDate()));
					officeLoanClaimType.setOldPassportIssueBy(claim.getOldPassportIssueBy());
				}
				officeLoanClaimType.setPreapproved(claim.isPreapproved());
				loansByFIODulBDRs.setOfficeLoanClaims(i, officeLoanClaimType);
				i++;
			}
		}
		catch (BusinessException e)
		{

		}
		return loansByFIODulBDRs;
	}

	public GetOfferOfficePriorByFIODulBDRs getOfferOfficePriorByFIODulBD(GetOfferOfficePriorByFIODulBDRq parameters) throws RemoteException
	{
		String FIO = parameters.getFIO();
		String Doc = parameters.getDUL();
		Calendar birthDay = parameters.getBirthDay();
		List<OfferOfficePrior> offerList = Collections.emptyList();
		try
		{
			offerList = OFFER_PRIOR_SERVICE.findOfferOfficePriorsByFIODulBD(FIO, Doc, birthDay);
		}
		catch (BusinessException e)
		{
			return getOfferOfficePriorByFIODulBDRsRs(1, e.getMessage());
		}

		if (offerList.isEmpty())
			return getOfferOfficePriorByFIODulBDRsRs(-1, "По ФИО:" + FIO + " ДУЛ: " + Doc + " ДР:" + DateHelper.formatDateYYYYMMDD(birthDay) + " оферт не найдено.");

		GetOfferOfficePriorByFIODulBDRs result = getOfferOfficePriorByFIODulBDRsRs(0,"");
		//Данные по клиенту извлекаем из первой альтернотивной оферте, потому что они одинаковы для всех оферт с одним  appNumber
		OfferOfficePrior o = offerList.get(0);
		IdentityType identity = new IdentityType();
		//ФИО + ДР
		identity.setFirstName(o.getFirstName());
		identity.setLastName(o.getLastName());
		identity.setMiddleName(o.getMiddleName());
		identity.setBirthday(DateHelper.toDate(o.getBirthDate()));
		identity.setRegistration(o.getRegistrationAddress());
		//ДУЛ
		IdentityCardType identityCardType = new IdentityCardType();
		identityCardType.setIdNum(o.getIdNum());
		identityCardType.setIdSeries(o.getIdSeries());
		identityCardType.setIdType(o.getIdType());
		identityCardType.setIdIssueBy(o.getIdIssueBy());
		identityCardType.setIdIssueDate(DateHelper.toDate(o.getIdIssueDate()));

		identity.setIdentityCard(identityCardType);
		result.setIdentity(identity);
		int i=0;
		OfferType[] offerTypes = new OfferType[offerList.size()];
		result.setOfferOfficePriors(offerTypes);
		for (OfferOfficePrior offer : offerList)
		{
			OfferType offerType = new OfferType();
			//данные по кредитному продукту
			offerType.setDepartment(o.getDepartment());
			offerType.setProductTypeCode(o.getProductTypeCode());
			offerType.setProductCode(o.getProductCode());
			offerType.setSubProductCode(o.getSubProductCode());
			OfferTypeCurrency currency = new OfferTypeCurrency(o.getCurrency());
			offerType.setCurrency(currency);
			offerType.setTypeOfIssue(o.getTypeOfIssue());
			offerType.setAccauntNumber(o.getAccountNumber());

			//данные по кредиту  .
			AlternativeType[] alternativeArr = new  AlternativeType[1];
			AlternativeType alternativeType = new AlternativeType();
			alternativeType.setId(offer.getId());
			alternativeType.setAltAmount(offer.getAltAmount());;
			alternativeType.setAltAnnuitentyPayment(offer.getAltAnnuityPayment());;
			alternativeType.setAltFullLoanCost(offer.getAltFullLoanCost());;
			alternativeType.setAltCreditCardlimit(offer.getAltCreditCardLimit());;
			alternativeType.setAltInterestRate(offer.getAltInterestRate());
			alternativeType.setAltPeriodM(new UnsignedInt(offer.getAltPeriod()));
			alternativeArr[0] =  alternativeType;

			offerType.setAlternative(alternativeArr);
			offerType.setApplicationNumber(offer.getApplicationNumber());
			offerType.setOfferStatus(offer.getState());
			offerType.setOfferDate(DateHelper.toCalendar(offer.getOfferDate()));
			offerType.setClientCategory(offer.getClientCategory());
			offerType.setVisibilityCounter(offer.getVisibilityCounter());
			result.setOfferOfficePriors(i, offerType);
			i++;
		}
		return result;
	}

	public UpdateOfferOfficePriorVisibleCounterRsType updateOfferOfficePriorVisibleCounter(UpdateOfferOfficePriorVisibleCounterRqType parameters) throws RemoteException
	{
		try
		{
			OFFER_PRIOR_SERVICE.updateOfferOfficePriorVisibleCounter(parameters.getOfferId());
			return getUpdateOfferOfficePriorVisibleCounterRsType(0,"");
		}
		catch (BusinessException e)
		{
			return getUpdateOfferOfficePriorVisibleCounterRsType(-1, "Не удалось обновить счетчик заявки с id = "+ parameters.getOfferId());
		}
	}

	private QueryOfferRsType getRs(long statusCode,String setStatusDesc)
	{
		QueryOfferRsType rs = new QueryOfferRsType();
		StatusType status = new StatusType();
		rs.setRqUID(new RandomGUID().getStringValue());
		rs.setRqTm(Calendar.getInstance());

		status.setStatusCode(statusCode);
		status.setStatusDesc(setStatusDesc);
		rs.setStatus(status);
		return rs;
	}
	private GetOfferOfficePriorByFIODulBDRs getOfferOfficePriorByFIODulBDRsRs(long statusCode,String setStatusDesc)
	{
		GetOfferOfficePriorByFIODulBDRs rs = new GetOfferOfficePriorByFIODulBDRs();
		StatusType status = new StatusType();
		rs.setRqUID(new RandomGUID().getStringValue());
		rs.setRqTm(Calendar.getInstance());

		status.setStatusCode(statusCode);
		status.setStatusDesc(setStatusDesc);
		rs.setStatus(status);
		return rs;
	}

	private UpdateOfferOfficePriorVisibleCounterRsType getUpdateOfferOfficePriorVisibleCounterRsType(long statusCode,String setStatusDesc)
	{
		UpdateOfferOfficePriorVisibleCounterRsType rs = new UpdateOfferOfficePriorVisibleCounterRsType();
		StatusType status = new StatusType();
		rs.setRqUID(new RandomGUID().getStringValue());
		rs.setRqTm(Calendar.getInstance());

		status.setStatusCode(statusCode);
		status.setStatusDesc(setStatusDesc);
		rs.setStatus(status);
		return rs;
	}
}
