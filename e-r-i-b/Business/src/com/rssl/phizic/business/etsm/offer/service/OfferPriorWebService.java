package com.rssl.phizic.business.etsm.offer.service;

import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.etsm.offer.service.generated.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.EtsmConfig;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.rpc.ServiceException;

/**
 * @author Moshenko
 * @ created 24.06.15
 * @ $Author$
 * @ $Revision$
 * Сервис для получения оферт  на кредиты из ETSM, отличных от каналов УКО(из межблочной базы)
 */

public class OfferPriorWebService
{

	private CreditETSMWebListener stub;
	/**
	 * @throws javax.xml.rpc.ServiceException
	 */
	public OfferPriorWebService()
	{
		CreditETSMWebListenerImplLocator locator = new CreditETSMWebListenerImplLocator();
		EtsmConfig etsmConfig = ConfigFactory.getConfig(EtsmConfig.class);
		try
		{
			stub = locator.getCreditETSMWebListenerPort(new URL(etsmConfig.getEtsnOfferUrl()));
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
	}
	/**
     * @param applicationNumber  Идентификатор заявки в ETSM
	 * @return  Оферта, созданная в каналах, отличных от УКО
	 */
	public List<OfferOfficePrior> getOfferOfficePrior(String applicationNumber) throws BusinessException, BusinessLogicException
	{
		QueryOfferRqType rq = new QueryOfferRqType();
		rq.setRqUID(new RandomGUID().getStringValue());
		rq.setRqTm(Calendar.getInstance());
		rq.setApplicationNumber(applicationNumber);
		QueryOfferRsType rs;
		try
		{
			rs = stub.queryOffer(rq);
		}
		catch (RemoteException e)
		{
			throw new BusinessException(e);
		}
		return getOffer(rs);
	}

	public void deleteOfferOfficePrior(String applicationNumber,Date offerDate) throws BusinessException, BusinessLogicException
	{
		DeleteOfferRqType rq = new DeleteOfferRqType();
		rq.setRqUID(new RandomGUID().getStringValue());
		rq.setRqTm(Calendar.getInstance());
		rq.setApplicationNumber(applicationNumber);
		rq.setOfferDate(DateHelper.toCalendar(offerDate));
		DeleteOfferRsType rs;
		try
		{
			rs = stub.deleteOffer(rq);
		}
		catch (RemoteException e)
		{
			throw new BusinessException(e);
		}

		StatusType statusType = rs.getStatus();
		long status = statusType.getStatusCode();
		if (status > 0)
			throw  new BusinessException(statusType.getStatusDesc());
		else if (status < 0)
			throw  new BusinessLogicException(statusType.getStatusDesc());

	}

	/**
	 *
	 * @param claim - заявка на кредит
	 * @return
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public String addOfficeLoanCLaim(OfficeLoanClaim claim) throws BusinessException
	{
		AddOfficeLoanRqType rq = new AddOfficeLoanRqType();
		rq.setRqUID(new RandomGUID().getStringValue());
		rq.setRqTm(Calendar.getInstance());
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
		officeLoanClaimType.setLoanAmount(claim.getLoanAmount().getDecimal());
		officeLoanClaimType.setLoanPeriod(BigInteger.valueOf(claim.getLoanPeriod()));
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
		officeLoanClaimType.setAcctId(claim.getAccountNumber());
		officeLoanClaimType.setCardNum(claim.getCardNumber());
		officeLoanClaimType.setTypeOfIssue(claim.getTypeOfIssue());
		if (officeLoanClaimType.isHasOldPassport())
		{
			officeLoanClaimType.setOldDocumentSeries(claim.getOldDocumentSeries());
			officeLoanClaimType.setOldDocumentNumber(claim.getOldDocumentNumber());
			officeLoanClaimType.setOldPassportIssueDate(DateHelper.toDate(claim.getOldPassportIssueDate()));
			officeLoanClaimType.setOldPassportIssueBy(claim.getOldPassportIssueBy());
		}
		officeLoanClaimType.setPreapproved(claim.isPreapproved());
		rq.setOfficeLoanClaim(officeLoanClaimType);
		AddOfficeLoanRsType rs;
		try
		{
			rs = stub.addOfficeLoan(rq);
		}
		catch (RemoteException e)
		{
			throw new BusinessException(e);
		}
		return rs.getStatus();
	}

	public List<OfficeLoanClaim> getOfficeLoanClaims(String FIO, String Doc, Calendar birthday) throws BusinessException
	{
		GetOfficeLoansByFIODulBDRq rq = new GetOfficeLoansByFIODulBDRq();
		rq.setRqUID(new RandomGUID().getStringValue());
		rq.setRqTm(Calendar.getInstance());
		rq.setFIO(FIO);
		rq.setDUL(Doc);
		rq.setBirthDay(birthday);
		try
		{
			List<OfficeLoanClaim> officeLoanClaimList = new ArrayList<OfficeLoanClaim>();
			GetOfficeLoansByFIODulBDRs response = stub.getOfficeLoansByFIODulBD(rq);
			if (response != null && response.getOfficeLoanClaims() != null)
			{
				for (OfficeLoanClaimType officeLoanClaimType : response.getOfficeLoanClaims())
				{
					OfficeLoanClaim claim = new OfficeLoanClaim();
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
					claim.setLoanApprovedPeriod(officeLoanClaimType.getLoanPeriod().longValue());
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
					if (officeLoanClaimType.isHasOldPassport())
					{
						claim.setOldDocumentSeries(officeLoanClaimType.getOldDocumentSeries());
						claim.setOldDocumentNumber(officeLoanClaimType.getOldDocumentNumber());
						claim.setOldPassportIssueDate(DateHelper.toCalendar(officeLoanClaimType.getOldPassportIssueDate()));
						claim.setOldPassportIssueBy(officeLoanClaimType.getOldPassportIssueBy());
					}
					claim.setPreapproved(officeLoanClaimType.isPreapproved());
					claim.setCreateDate(officeLoanClaimType.getCreateDate());
					officeLoanClaimList.add(claim);
				}
			}
			return officeLoanClaimList;
		}
		catch (RemoteException e)
		{
		    throw new BusinessException(e);
		}
	}

	public List<OfferOfficePrior> getOfferOfficePriorByFIODocBD(ActivePerson person) throws BusinessException
	{
		GetOfferOfficePriorByFIODulBDRq rq = new GetOfferOfficePriorByFIODulBDRq();
		rq.setRqUID(new RandomGUID().getStringValue());
		rq.setRqTm(Calendar.getInstance());
		String fio =  person.getSurName()+person.getFirstName()+person.getPatrName();
		String doc="";
		for (PersonDocument document : person.getPersonDocuments())
		{
			if (document.getDocumentType().equals(PersonDocumentType.REGULAR_PASSPORT_RF))
			{
				doc = document.getDocumentSeries() + document.getDocumentNumber();

			}
		}
		rq.setFIO(fio);
		rq.setDUL(doc);
		rq.setBirthDay(person.getBirthDay());
		GetOfferOfficePriorByFIODulBDRs rs;
		try
		{
			rs = stub.getOfferOfficePriorByFIODulBD(rq);
		}
		catch (RemoteException e)
		{
			throw new BusinessException(e);
		}
		return getOffers(rs);
	}

	public void updateOfferOfficePriorVisibleCounter(Long id) throws BusinessException
	{
		UpdateOfferOfficePriorVisibleCounterRqType rq = new UpdateOfferOfficePriorVisibleCounterRqType();
		rq.setRqUID(new RandomGUID().getStringValue());
		rq.setRqTm(Calendar.getInstance());
		rq.setOfferId(id);
		UpdateOfferOfficePriorVisibleCounterRsType rs;
		try
		{
			rs = stub.updateOfferOfficePriorVisibleCounter(rq);
			if (rs.getStatus().getStatusCode()==-1)
				throw new BusinessException(rs.getStatus().getStatusDesc());
		}
		catch (RemoteException e)
		{
			throw new BusinessException(e);
		}
	}

	private List<OfferOfficePrior> getOffer(QueryOfferRsType rs) throws BusinessException, BusinessLogicException
	{
		StatusType statusType = rs.getStatus();
		long status = statusType.getStatusCode();
		if (status > 0)
			throw  new BusinessException(statusType.getStatusDesc());
		else if (status < 0)
			throw  new BusinessLogicException(statusType.getStatusDesc());
		else
		{
			List<OfferOfficePrior> result = new ArrayList<OfferOfficePrior>();
			OfferType offerType = rs.getOffer();
			IdentityType identityType = rs.getIdentity();
			IdentityCardType identityCardType = identityType.getIdentityCard();
			for (AlternativeType alt :offerType.getAlternative())
			{
				OfferOfficePrior offer = new OfferOfficePrior();
				offer.setId(alt.getId());
				offer.setOfferDate(DateHelper.toDate(offerType.getOfferDate()));
				offer.setRegistrationAddress(identityType.getRegistration());
				//ФИО + ДР
				offer.setFirstName(identityType.getFirstName());
				offer.setLastName(identityType.getLastName());
				offer.setMiddleName(identityType.getMiddleName());
				offer.setBirthDate(DateHelper.toCalendar(identityType.getBirthday()));
				//ДУЛ
				offer.setIdNum(identityCardType.getIdNum());
				offer.setIdSeries(identityCardType.getIdSeries());
				offer.setIdType(identityCardType.getIdType());
				offer.setIdIssueBy(identityCardType.getIdIssueBy());
				offer.setIdIssueDate(DateHelper.toCalendar(identityCardType.getIdIssueDate()));
				//детали предложения
				offer.setAltAmount(alt.getAltAmount());
				offer.setAltAnnuityPayment(alt.getAltAnnuitentyPayment());
				offer.setAltInterestRate(alt.getAltInterestRate());
				offer.setAltCreditCardLimit(alt.getAltCreditCardlimit());
				offer.setAltFullLoanCost(alt.getAltFullLoanCost());
				offer.setAltPeriod(alt.getAltPeriodM().longValue());
				//данные по кредитному продукта
				offer.setProductTypeCode(offerType.getProductTypeCode());
				offer.setProductCode(offerType.getProductCode());
				offer.setSubProductCode(offerType.getSubProductCode());
				offer.setDepartment(offerType.getDepartment());
				offer.setCurrency(offerType.getCurrency().getValue());
				offer.setAccountNumber(offerType.getAccauntNumber());
				offer.setTypeOfIssue(offerType.getTypeOfIssue());
				result.add(offer);
			}
			return result;
		}
	}
	private List<OfferOfficePrior> getOffers(GetOfferOfficePriorByFIODulBDRs rs) throws BusinessException
	{
		StatusType statusType = rs.getStatus();
		long status = statusType.getStatusCode();
		if (status > 0)
			throw  new BusinessException(statusType.getStatusDesc());
		else if (status < 0)
			throw  new BusinessException(statusType.getStatusDesc());
		else
		{
			List<OfferOfficePrior> result = new ArrayList<OfferOfficePrior>();
			OfferType[] offerTypes = rs.getOfferOfficePriors();
			IdentityType identityType = rs.getIdentity();
			IdentityCardType identityCardType = identityType.getIdentityCard();
			for (OfferType offerType : offerTypes)
			{
				AlternativeType alt = offerType.getAlternative(0);
				OfferOfficePrior offer = new OfferOfficePrior();
				offer.setId(alt.getId());
				//ФИО + ДР
				offer.setFirstName(identityType.getFirstName());
				offer.setLastName(identityType.getLastName());
				offer.setMiddleName(identityType.getMiddleName());
				offer.setBirthDate(DateHelper.toCalendar(identityType.getBirthday()));
				//ДУЛ
				offer.setIdNum(identityCardType.getIdNum());
				offer.setIdSeries(identityCardType.getIdSeries());
				offer.setIdType(identityCardType.getIdType());
				offer.setIdIssueBy(identityCardType.getIdIssueBy());
				offer.setIdIssueDate(DateHelper.toCalendar(identityCardType.getIdIssueDate()));
				//детали предложения
				offer.setAltAmount(alt.getAltAmount());
				offer.setAltAnnuityPayment(alt.getAltAnnuitentyPayment());
				offer.setAltInterestRate(alt.getAltInterestRate());
				offer.setAltCreditCardLimit(alt.getAltCreditCardlimit());
				offer.setAltFullLoanCost(alt.getAltFullLoanCost());
				offer.setAltPeriod(alt.getAltPeriodM().longValue());
				//данные по кредитному продукта
				offer.setProductTypeCode(offerType.getProductTypeCode());
				offer.setProductCode(offerType.getProductCode());
				offer.setSubProductCode(offerType.getSubProductCode());
				offer.setDepartment(offerType.getDepartment());
				offer.setCurrency(offerType.getCurrency().getValue());
				offer.setAccountNumber(offerType.getAccauntNumber());
				offer.setTypeOfIssue(offerType.getTypeOfIssue());
				offer.setApplicationNumber(offerType.getApplicationNumber());
				offer.setClientCategory(offerType.getClientCategory());
				offer.setVisibilityCounter(offerType.getVisibilityCounter());
				result.add(offer);
			}
			return result;
		}
	}
}
