package com.rssl.phizic.business.etsm.offer;

import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateService;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.loanclaim.type.Address;
import com.rssl.phizic.gate.loanclaim.type.Passport;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DateSpanFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Moshenko
 * @ created 27.07.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferAgrimentHelper
{
	private static SimpleService simpleService = new SimpleService();
	private static CreditOfferTemplateService templateService = new CreditOfferTemplateService("credit-offer-template-cache-name");
	private static final String DELIMITER = " ";
	private static final String COMMA = ",";

	public static String getEribOfferAgreementText(OfferEribPrior offer) throws BusinessException
	{
		ExtendedLoanClaim cliam = simpleService.findById(ExtendedLoanClaim.class, offer.getClaimId());
		if (cliam == null)
			throw new BusinessException("�� ������� ����� ��������� ������ � id:" + offer.getClaimId());

		return getEribOfferAgreementText(offer, cliam);
	}

	/* @param offer ������ ����
	 * @param claim ������ �� ������
	 * @return����������������� ����� ����������.
	 * @throws BusinessException
	 */
	public static String getEribOfferAgreementText(OfferEribPrior offer, ExtendedLoanClaim claim) throws BusinessException
	{
		CreditOfferTemplate offertTemplate = templateService.getActiveTemplate();
		if (offertTemplate == null)
			throw new BusinessException("�� ������� ���������� �������� ������ ������");

		// ��� �������
		String clientFIO = StringHelper.getEmptyIfNull(claim.getOwnerLastName()) + " " +
				StringHelper.getEmptyIfNull(claim.getOwnerFirstName()) + " " +
				StringHelper.getEmptyIfNull(claim.getOwnerMiddleName());

		// ��� ��������. ������� �� ������� �������
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		ActivePerson person = personData.getPerson();
		String borrowerFIO = StringHelper.getEmptyIfNull(person.getSurName()) + " " +
				StringHelper.getEmptyIfNull(person.getFirstName()) + " " +
				StringHelper.getEmptyIfNull(person.getPatrName());

		// ������ � ��������� �������
		Passport passport = claim.getApplicantPassport();
		Calendar docIssueDate = passport.getIssuedDate();
		String docIssue = docIssueDate == null ? "" : DateHelper.formatDateWithMonthString(docIssueDate);

		// ���������� ������
		BigDecimal interestRate = offer.getAltInterestRate();
		String interestRateText = interestRate == null ? "" :
				interestRate.toString() + "% (" + StringUtils.sumInWords(interestRate.toString(), "") + " ���������)";

		// ���� ������� � �������
		Long period = offer.getAltPeriod();
		String durationText = period == null ? "" : DateSpanFormat.format(new DateSpan(0, period.intValue(), 0));

		// ������ ��������� �������
		BigDecimal fullLoanCost = offer.getAltFullLoanCost();
		String fullLoanCostTxt = fullLoanCost == null ? "" :
				fullLoanCost.toString() + " ���. (" + StringUtils.sumInWords(fullLoanCost.toString(), "RUB") + "), ���������� �����";

		// �������������� ����� ������� � ������;
		BigDecimal altAmount = offer.getAltAmount();
		String altAmountTxt = altAmount == null ? "" :
				altAmount.toString() + " ���. (" + StringUtils.sumInWords(altAmount.toString(), "RUB") + "), ���������� �����";

		// ���� ��� ������ �������, ��������� �������� ��� ���������� ������
		String accountNumber = AccountsUtil.getFormattedAccountNumber(claim.getLoanIssueAccount());

		// �������������� ����������� ������ (������� ��������� �������)
		BigDecimal altAnnuityPayment = offer.getAltAnnuityPayment();
		String altAnnuityPaymentTxt = altAnnuityPayment == null ? "" :
				"���������� ������������ ��������� � ������� " + altAnnuityPayment.toString() + " ���.";

		Map<String, String> params = new HashMap<String, String>();
		params.put(CreditOfferTemplate.ATTRIBUTE_FIO, clientFIO);
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_SERIES, StringHelper.getEmptyIfNull(passport.getSeries()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_NUMBER, StringHelper.getEmptyIfNull(passport.getNumber()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_BY, StringHelper.getEmptyIfNull(passport.getIssuedBy()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_DATE, docIssue);
		params.put(CreditOfferTemplate.ATTRIBUTE_LOAN_COST, fullLoanCostTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_AMOUNT, altAmountTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_PERIOD, durationText);
		params.put(CreditOfferTemplate.ATTRIBUTE_INTEREST_RATE, interestRateText);
		params.put(CreditOfferTemplate.ATTRIBUTE_ANNUITY_PAYMENT, altAnnuityPaymentTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_ISSUE_ACCOUNT, accountNumber);
		params.put(CreditOfferTemplate.ATTRIBUTE_BORROWER, borrowerFIO);
		params.put(CreditOfferTemplate.ATTRIBUTE_REGISTRATION, getRegistrationStr(claim));

		return XmlHelper.getWithHtmlTag(offertTemplate.getText(), params);
	}

	/**
	 * @param claim ����������� ������
	 * @return ����� ����������
	 */
	public static String getRegistrationStr(ExtendedLoanClaim claim)
	{
		Address address = claim.getApplicantResidenceAddress();
		StringBuilder sb = new StringBuilder();
		//������
		if (address.getRegion() != null)
			sb.append(address.getRegion().getName()).append(DELIMITER);
		//�����/�����
		if (address.getAreaType() != null)
			sb.append(address.getAreaType().getName()).append(DELIMITER);
		if (address.getAreaName() != null)
			sb.append(address.getAreaName()).append(DELIMITER).append(COMMA);
		//������
		if (address.getCityType() != null)
			sb.append(address.getCityType().getName()).append(DELIMITER);
		if (address.getCityName() != null)
			sb.append(address.getCityName()).append(DELIMITER).append(COMMA);
		//��������� �����
		if (address.getLocalityType() != null)
			sb.append(address.getLocalityType().getName()).append(DELIMITER);
		if (address.getLocalityName() != null)
			sb.append(address.getLocalityName()).append(DELIMITER).append(COMMA);
		//������
		if (address.getStreetType() != null)
			sb.append(address.getStreetType().getName()).append(DELIMITER);
		if (address.getStreetName() != null)
			sb.append(address.getStreetName()).append(DELIMITER).append(COMMA);
		//���
		if (address.getHouseNumber() != null)
			sb.append(address.getHouseNumber()).append(DELIMITER).append(COMMA);
		//������
		if (address.getBuildingNumber() != null)
			sb.append(address.getBuildingNumber()).append(DELIMITER).append(COMMA);
		//��������
		if (address.getApartmentNumber() != null)

			sb.append("��.").append(address.getApartmentNumber());
		return sb.toString();
	}

	/**
	 * @param offer ������ �� ����
	 * @return ����������������� ����� ����������.
	 * @throws BusinessException
	 */
	public static String getOfficeOfferAgreementText(OfferOfficePrior offer) throws BusinessException
	{
		CreditOfferTemplate offertTemplate = templateService.getActiveTemplate();
		if (offertTemplate == null)
			throw new BusinessException("�� ������� ���������� �������� ������ ������");

		// ��� �������
		String clientFIO = StringHelper.getEmptyIfNull(offer.getLastName()) + " " +
				StringHelper.getEmptyIfNull(offer.getFirstName()) + " " +
				StringHelper.getEmptyIfNull(offer.getMiddleName());

		// ������ � ��������� �������
		Calendar docIssueDate = offer.getIdIssueDate();
		String docIssue = docIssueDate == null ? "" : DateHelper.formatDateWithMonthString(docIssueDate);

		// ���������� ������
		BigDecimal interestRate = offer.getAltInterestRate();
		String interestRateText = interestRate == null ? "" :
				interestRate.toString() + "% (" + StringUtils.sumInWords(interestRate.toString(), "") + " ���������)";

		// ���� ������� � �������
		Long period = offer.getAltPeriod();
		String durationText = period == null ? "" : DateSpanFormat.format(new DateSpan(0, period.intValue(), 0));

		// ������ ��������� �������
		BigDecimal fullLoanCost = offer.getAltFullLoanCost();
		String fullLoanCostTxt = fullLoanCost == null ? "" :
				fullLoanCost.toString() + " ���. (" + StringUtils.sumInWords(fullLoanCost.toString(), "RUB") + "), ���������� �����";

		// �������������� ����� ������� � ������;
		BigDecimal altAmount = offer.getAltAmount();
		String altAmountTxt = altAmount == null ? "" :
				altAmount.toString() + " ���. (" + StringUtils.sumInWords(altAmount.toString(), "RUB") + "), ���������� �����";

		// ���� ��� ������ �������, ��������� �������� ��� ���������� ������
		String accountNumber = AccountsUtil.getFormattedAccountNumber(offer.getAccountNumber());

		// �������������� ����������� ������ (������� ��������� �������)
		BigDecimal altAnnuityPayment = offer.getAltAnnuityPayment();
		String altAnnuityPaymentTxt = altAnnuityPayment == null ? "" :
				"���������� ������������ ��������� � ������� " + altAnnuityPayment.toString() + " ���.";

		Map<String, String> params = new HashMap<String, String>();
		params.put(CreditOfferTemplate.ATTRIBUTE_FIO, clientFIO);
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_SERIES, StringHelper.getEmptyIfNull(offer.getIdSeries()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_NUMBER, StringHelper.getEmptyIfNull(offer.getIdNum()));
 		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_BY, StringHelper.getEmptyIfNull(offer.getIdIssueBy()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_DATE, docIssue);
		params.put(CreditOfferTemplate.ATTRIBUTE_LOAN_COST, fullLoanCostTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_AMOUNT, altAmountTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_PERIOD, durationText);
		params.put(CreditOfferTemplate.ATTRIBUTE_INTEREST_RATE, interestRateText);
		params.put(CreditOfferTemplate.ATTRIBUTE_ANNUITY_PAYMENT, altAnnuityPaymentTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_ISSUE_ACCOUNT, accountNumber);
		params.put(CreditOfferTemplate.ATTRIBUTE_BORROWER, clientFIO);
		params.put(CreditOfferTemplate.ATTRIBUTE_REGISTRATION, offer.getRegistrationAddress());

		return XmlHelper.getWithHtmlTag(offertTemplate.getText(), params);
	}

	/**
	 * @param offer �������������� ������
	 * @return ����������������� ����� ����������.
	 * @throws BusinessException
	 */
	public static String getConfirmedOfferAgreementText(OfferConfirmed offer) throws BusinessException
	{
		CreditOfferTemplate offertTemplate = templateService.find(offer.getTemplateId());
		if (offertTemplate == null)
			throw new BusinessException("�� ������� ���������� ������ ������");

		ExtendedLoanClaim claim = simpleService.findById(ExtendedLoanClaim.class, offer.getClaimId());
		if (claim == null)
			throw new BusinessException("�� ������� ����� ��������� ������ � id:" + offer.getClaimId());

		// ��� �������
		String clientFIO = StringHelper.getEmptyIfNull(offer.getLastName()) + " " +
				StringHelper.getEmptyIfNull(offer.getFirstName()) + " " +
				StringHelper.getEmptyIfNull(offer.getMiddleName());

		// ���� ������ ���������
		Calendar docIssueDate = offer.getIssueDt();
		String docIssue = docIssueDate == null ? "" : DateHelper.formatDateWithMonthString(docIssueDate);

		// ���������� ������
		BigDecimal interestRate = offer.getAltInterestRate();
		String interestRateText = interestRate == null ? "" :
				interestRate.toString() + "% (" + StringUtils.sumInWords(interestRate.toString(), "") + " ���������)";

		// ���� ������� � �������
		Long period = offer.getAltPeriod();
		String durationText = period == null ? "" : DateSpanFormat.format(new DateSpan(0, period.intValue(), 0));

		// ������ ��������� �������
		BigDecimal fullLoanCost = offer.getAltFullLoanCost();
		String fullLoanCostTxt = fullLoanCost == null ? "" :
				fullLoanCost.toString() + " ���. (" + StringUtils.sumInWords(fullLoanCost.toString(), "RUB") + "), ���������� �����";

		// �������������� ����� ������� � ������;
		BigDecimal altAmount = offer.getAltAmount();
		String altAmountTxt = altAmount == null ? "" :
				altAmount.toString() + " ���. (" + StringUtils.sumInWords(altAmount.toString(), "RUB") + "), ���������� �����";

		// ���� ��� ������ �������, ��������� �������� ��� ���������� ������
		String accountNumber = AccountsUtil.getFormattedAccountNumber(offer.getAccountNumber());

		// �������������� ����������� ������ (������� ��������� �������)
		BigDecimal altAnnuityPayment = offer.getAltAnnuityPayment();
		String altAnnuityPaymentTxt = altAnnuityPayment == null ? "" :
				"���������� ������������ ��������� � ������� " + altAnnuityPayment.toString() + " ���.";

		Map<String, String> params = new HashMap<String, String>();
		params.put(CreditOfferTemplate.ATTRIBUTE_FIO, clientFIO);
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_SERIES, StringHelper.getEmptyIfNull(offer.getIdSeries()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_NUMBER, StringHelper.getEmptyIfNull(offer.getIdNum()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_BY, StringHelper.getEmptyIfNull(offer.getIssuedBy()));
		params.put(CreditOfferTemplate.ATTRIBUTE_PASSPORT_ISSUED_DATE, docIssue);
		params.put(CreditOfferTemplate.ATTRIBUTE_LOAN_COST, fullLoanCostTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_AMOUNT, altAmountTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_PERIOD, durationText);
		params.put(CreditOfferTemplate.ATTRIBUTE_INTEREST_RATE, interestRateText);
		params.put(CreditOfferTemplate.ATTRIBUTE_ANNUITY_PAYMENT, altAnnuityPaymentTxt);
		params.put(CreditOfferTemplate.ATTRIBUTE_ISSUE_ACCOUNT, accountNumber);
		params.put(CreditOfferTemplate.ATTRIBUTE_BORROWER, StringHelper.getEmptyIfNull(offer.getBorrower()));
		params.put(CreditOfferTemplate.ATTRIBUTE_REGISTRATION, StringHelper.getEmptyIfNull(offer.getRegistrationAddress()));

		return XmlHelper.getWithHtmlTag(offertTemplate.getText(), params);
	}


}
