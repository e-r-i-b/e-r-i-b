package com.rssl.phizicgate.esberibgate.loanclaim.etsm;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.loanclaim.dictionary.*;
import com.rssl.phizic.gate.loanclaim.type.*;
import com.rssl.phizic.gate.payments.loan.ETSMLoanClaim;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * @author Balovtsev
 * @since 26.06.2015.
 */
public class ETSMLoanClaimRequestBuilderRelease19
{
	private static final String ANNUITY_LOAN_PAYMENT = "Annuity";

	///////////////////////////////////////////////////////////////////////////

	ChargeLoanApplicationRq makeRequest(ETSMLoanClaim claim, String operUID)
	{
		ChargeLoanApplicationRq request = new ChargeLoanApplicationRq();

		// 1. ������ �������
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(Calendar.getInstance());
		request.setOperUID(operUID);
		request.setSPName(SPNameType.BP_ERIB);

		// 2. ������ ������
		request.setApplication(makeApplication(claim));

		return request;
	}

	private ApplicationType makeApplication(ETSMLoanClaim claim)
	{
		ApplicationType application = new ApplicationType();

		// 1. �������������
		String departmentETSMCode = claim.getClaimDrawDepartmentETSMCode();
		if (StringHelper.isEmpty(departmentETSMCode))
			throw new IllegalArgumentException("�� ������ ��� ������������� ��� ���������� ������");
		application.setUnit(departmentETSMCode);

		//���������� � ��������� ���������� (��), ����������� ������ �� ��������� � ETSM.
		//�������� ���������� ������ � ������ �������� ��������� ������ �� ���� � ETSM ��� ��������� ��������� �� CRM.
		application.setLoginKI(claim.getLoginKI());

		// 2. ����� ������
		application.setChannel(claim.getChannel().getCode());

		// 3. ������ ���������� ��������
		application.setProduct(makeProduct(claim));

		// 4. ��������� �������
		application.setApplicant(makeApplicant(claim));

		// 12. ������� ������ ������
		application.setCompleteAppFlag(claim.getCompleteAppFlag());

		// 13. ����� ��������� �������� �� ��������� ������������ ������ � ���
		ChannelCBRegAApproveType bkiChannelType = claim.getChannelCBRegAApprove();
		if (bkiChannelType != null)
			application.setChannelCBRegAApprove(bkiChannelType.getCode());

		// 14. ����� ��������� �������� �� ��������� ������������ ������ ���
		ChannelPFRRegAApproveType pfrChannelType = claim.getChannelPFRRegAApprove();
		if (pfrChannelType != null)
			application.setChannelPFRRegAApprove(pfrChannelType.getCode());

		application.setTopUpList(makeTopUps(claim));

		return application;
	}

	private ApplicationType.TopUpList makeTopUps(ETSMLoanClaim claim)
	{
		Set<ClaimOfferTopUp> claimTopUps = claim.getTopUps();
		if (CollectionUtils.isEmpty(claimTopUps))
		{
			return null;
		}

		int blockCount = 0;

		List<RepayLoanType> repayLoans = new ArrayList<RepayLoanType>();
		for (ClaimOfferTopUp claimTopUp : claimTopUps)
		{
			blockCount = claimTopUp.getTopUpLoanBlockCount();

			RepayLoanType repayLoan = new RepayLoanType();
			repayLoan.setCContrIDIST(claimTopUp.getIdSource());
			repayLoan.setCContrIDDOG(claimTopUp.getIdContract());
			repayLoan.setLoanAccountNumber(claimTopUp.getLoanAccountNumber());
			repayLoan.setAgreementNumber(claimTopUp.getAgreementNumber());
			repayLoan.setTotalAmount(claimTopUp.getTotalAmount());
			repayLoan.setStartDate(claimTopUp.getStartDate());
			repayLoan.setMaturityDate(claimTopUp.getMaturityDate());
			repayLoan.setCurrency(CurrencyType.fromValue(claimTopUp.getCurrency()));
			repayLoans.add(repayLoan);
		}

		ApplicationType.TopUpList.RepayLoanList repayLoanList = new ApplicationType.TopUpList.RepayLoanList();
		repayLoanList.getRepayLoen().addAll(repayLoans);

		ApplicationType.TopUpList topUps = new ApplicationType.TopUpList();
		topUps.setRepayLoanListCount(blockCount);
		topUps.setRepayLoanList(repayLoanList);
		return topUps;
	}

	/**
	 * ��������� ������ � (����������) �������
	 * @param claim - ������ �� ������
	 * @return ���������� � �������
	 */
	private ProductDataType makeProduct(ETSMLoanClaim claim)
	{
		ProductDataType product = new ProductDataType();

		// 1. ��� (���) ���������� ��������
		String loanProductType = claim.getLoanProductType();
		if (StringHelper.isEmpty(loanProductType))
			throw new IllegalArgumentException("�� ������ ��� ���������� ��������");
		product.setType(loanProductType);

		// 2. ��� ���������� ��������
		String loanProductCode = claim.getLoanProductCode();
		if (StringHelper.isEmpty(loanProductCode))
			throw new IllegalArgumentException("�� ������ ��� ���������� ��������");
		product.setCode(loanProductCode);

		// 3. ��� ���������� �����������
		String loanSubProductCode = claim.getLoanSubProductCode();
		if (StringHelper.isEmpty(loanSubProductCode))
			throw new IllegalArgumentException("�� ������ ��� ���������� �����������");
		product.setSubProductCode(loanSubProductCode);

		// 4. ����� �������
		Money loanAmount = claim.getLoanAmount();
		product.setAmount(loanAmount.getDecimal());
		product.setCurrency(makeCurrency(loanAmount.getCurrency()));

		// 5. ���� �������
		product.setPeriod(claim.getLoanPeriod());

		// 6. ������ ��������� ������� - �����������
		product.setPaymentType(ANNUITY_LOAN_PAYMENT);

		return product;
	}

	/**
	 * ��������� ������ � �������
	 * @param claim - ������ �� ������
	 * @return ���������� � �������
	 */
	private ApplicantDataType makeApplicant(ETSMLoanClaim claim)
	{
		ApplicantDataType applicant = new ApplicantDataType();

		// 1. ��� ��������� ������
		applicant.setType(claim.getApplicantType().getCode());

		// 2. ������������ ������ � �������
		applicant.setPersonInfo(makeApplicantPersonInfo(claim));

		// 3. �������� ��������� �������
		applicant.setMaritalCondition(makeApplicantMaritalCondition(claim));

		// (4) ������������ �������
		applicant.setRelativeList(makeApplicantRelativeList(claim));

		// 5. ����� ������ �������
		applicant.setEmploymentHistory(makeApplicantEmploymentHistory(claim));

		// 6. ������ � ������� �������
		applicant.setIncome(makeApplicantIncome(claim));

		// (7) ������������ � ������������� �������
		ApplicantDataType.RealEstateList realEstateList = makeApplicantRealEstateList(claim);
		applicant.setRealEstateFlag(realEstateList != null);
		applicant.setRealEstateList(realEstateList);

		// (8) ������������ �������� � ������������� �������
		ApplicantDataType.VehicleList vehicleList = makeApplicantVehicleList(claim);
		applicant.setVehicleFlag(vehicleList != null);
		applicant.setVehicleList(vehicleList);

		// 10. �������������� ������ �� ������
		applicant.setAddData(makeApplicantAdditionalData(claim));

		return applicant;
	}

	private ApplicantPersonInfoType makeApplicantPersonInfo(ETSMLoanClaim claim)
	{
		ApplicantPersonInfoType personInfo = new ApplicantPersonInfoType();

		// 1. ��������� ��� �������
		personInfo.setPersonName(makePersonName(claim.getApplicantName()));

		// (2) ���������� ��� �������
		PreviousNameDataType previousName = makeApplicantPreviousName(claim);
		personInfo.setNameChangedFlag(previousName != null);
		personInfo.setPreviousName(previousName);

		// (3) ��� �������
		personInfo.setINN(StringHelper.getNullIfEmpty(claim.getApplicantTaxID()));

		// 4. ��� �������
		personInfo.setSex(claim.getApplicantGender().getCode());

		// 5. ���� �������� �������
		Calendar birthDay = claim.getApplicantBirthDay();
		personInfo.setBirthday(birthDay);

		// 6. ����� �������� �������
		personInfo.setBirthPlace(claim.getApplicantBirthPlace());

		// 7. ����������� �������
		personInfo.setEducation(makeApplicantEducation(claim));

		// 8. �������� �������
		personInfo.setContact(makeApplicantContacts(claim));

		// 9. ����������� �������
		personInfo.setCitizenship(claim.getApplicantCitizenship().getCode());

		// 10. ������� �������
		personInfo.setPassport(makeApplicantPassport(claim));
		personInfo.setExtPassportExFlag(claim.getApplicantForeignPassportFlag());

		return personInfo;
	}

	private PreviousNameDataType makeApplicantPreviousName(ETSMLoanClaim claim)
	{
		NameChangeReason nameChangeReason = claim.getApplicantNameChangeReason();
		if (nameChangeReason == null)
			return null;
		PersonName claimPreviousName = claim.getApplicantPreviousName();

		PreviousNameDataType name = new PreviousNameDataType();

		// 1. ���������� ������� �������
		name.setLastName(claimPreviousName.getLastName());

		// 2. ���������� ��� �������
		name.setFirstName(claimPreviousName.getFirstName());

		// (3) ���������� �������� �������
		name.setMiddleName(StringHelper.getNullIfEmpty(claimPreviousName.getMiddleName()));

		// 4. ���� ��������� ���
		name.setDate(claim.getApplicantNameChangeDate());

		// 5. ������� ���������
		name.setReasonCode(nameChangeReason.getCode());
		name.setReasonDesc(StringHelper.getNullIfEmpty(claim.getApplicantNameChangeDescription()));

		return name;
	}

	private EducationDataType makeApplicantEducation(ETSMLoanClaim claim)
	{
		EducationDataType education = new EducationDataType();

		// 1. �����������
		education.setStatus(claim.getApplicantEducation().getCode());

		// 2. ���� ������������� ������� ����������� ���
		Integer unfinishedYear = claim.getApplicantUnfinishedYear();
		if (unfinishedYear != null)
			education.setUnfinishedCourse(unfinishedYear.toString());

		return education;
	}

	private ContactType makeApplicantContacts(ETSMLoanClaim claim)
	{
		ContactType contacts = new ContactType();

		// (1) E-mail
		contacts.setEmailAddr(StringHelper.getNullIfEmpty(claim.getApplicantEmail()));

		// 2. ��������
		contacts.setPhoneList(makeApplicantPhoneList(claim));

		// 3. ������
		if (claim.getApplicantResidenceAddress() != null ||
				claim.getApplicantFixedAddress() != null ||
				claim.getApplicantTemporaryAddress() != null)
			contacts.setAddressList(makeApplicantAddressList(claim));

		return contacts;
	}

	private ContactType.PhoneList makeApplicantPhoneList(ETSMLoanClaim claim)
	{
		Collection<Phone> claimPhones = claim.getApplicantPhones();

		ContactType.PhoneList phoneList = new ContactType.PhoneList();
		Collection<PhoneType> phones = phoneList.getPhones();
		for (Phone claimPhone : claimPhones)
			phones.add(makePhone(claimPhone));
		return phoneList;
	}

	private PhoneType makePhone(Phone claimPhone)
	{
		PhoneType phone = new PhoneType();
		phone.setType(claimPhone.getType().getCode());
		phone.setCountryPrefix(claimPhone.getCountry());
		phone.setPrefix(claimPhone.getPrefix());
		phone.setNumber(claimPhone.getNumber());
		return phone;
	}

	private ContactType.AddressList makeApplicantAddressList(ETSMLoanClaim claim)
	{
		ContactType.AddressList addressList = new ContactType.AddressList();
		Collection<AddressType> addresses = addressList.getAddresses();

		// 1. ����� ������������ ���������� �������
		Address residenceAddress = claim.getApplicantResidenceAddress();
		if (residenceAddress != null)
			addresses.add(makeAddress(residenceAddress, AddressKind.RESIDENCE));

		// (2) ����� ���������� ����������� �������
		Address fixedAddress = claim.getApplicantFixedAddress();
		if (fixedAddress != null)
			addresses.add(makeAddress(fixedAddress, AddressKind.FIXED));

		// (3) ����� ��������� ����������� �������
		Address temporaryAddress = claim.getApplicantTemporaryAddress();
		if (temporaryAddress != null)
			addresses.add(makeAddress(temporaryAddress, AddressKind.TEMPORARY));

		// 4. ����� ���������� ��������� � ������� ���������� �����������
		if (residenceAddress != null && fixedAddress != null)
			addressList.setResidenceEqualFlag(ObjectUtils.equals(residenceAddress, fixedAddress));

		// 5. ���� ���������� � ���������� ������ �� ������ ���������� ������ (���)
		addressList.setCityResidencePeriod(BigInteger.valueOf(claim.getApplicantCityResidencePeriod()));

		// 6. ���� ���������� �� ������������ ������(���)
		addressList.setActResidencePeriod(BigInteger.valueOf(claim.getApplicantActualResidencePeriod()));

		// 7. ����� ����������
		addressList.setResidenceRight(claim.getApplicantResidenceRight().getCode());

		// (8) ���� ��������� ��������� �����������
		Calendar residenceExpiryDate = claim.getApplicantResidenceExpiryDate();
		if (residenceExpiryDate != null)
			addressList.setTempRegExpiryDt(residenceExpiryDate);

		return addressList;
	}

	private AddressType makeAddress(Address claimAddress, AddressKind addressKind)
	{
		AddressType address = new AddressType();
		address.setManualInputFlag(true);
		address.setAddrType(addressKind.getCode());

		// 1. �������� ������
		address.setPostalCode(claimAddress.getPostalCode());

		// 2. ������
		address.setRegionCode(claimAddress.getRegion().getCode());

		// (3) �����/�����
		TypeOfArea areaType = claimAddress.getAreaType();
		if (areaType != null)
			address.setAreaType(areaType.getCode());
		address.setArea(StringHelper.getNullIfEmpty(claimAddress.getAreaName()));

		// (4) �����
		TypeOfCity cityType = claimAddress.getCityType();
		if (cityType != null)
			address.setCityType(cityType.getCode());
		address.setCity(StringHelper.getNullIfEmpty(claimAddress.getCityName()));

		// (5) ���������� �����
		TypeOfLocality localityType = claimAddress.getLocalityType();
		if (localityType != null)
			address.setSettlementType(localityType.getCode());
		address.setSettlement(StringHelper.getNullIfEmpty(claimAddress.getLocalityName()));

		// 6. �����
		TypeOfStreet streetType = claimAddress.getStreetType();
		if (streetType != null)
			address.setStreetType(streetType.getCode());
		address.setStreet(claimAddress.getStreetName());

		// 7. ��� (��������)
		address.setHouseNum(claimAddress.getHouseNumber());

		// (8) ������
		address.setHouseExt(StringHelper.getNullIfEmpty(claimAddress.getBuildingNumber()));

		// (9) ��������
		address.setUnit(StringHelper.getNullIfEmpty(claimAddress.getUnitNumber()));

		// (10) ��������/����
		address.setUnitNum(StringHelper.getNullIfEmpty(claimAddress.getApartmentNumber()));

		return address;
	}

	private PassportType makeApplicantPassport(ETSMLoanClaim claim)
	{
		Passport claimActualPassport = claim.getApplicantPassport();
		Passport claimPreviousPassport = claim.getApplicantPreviousPassport();

		PassportType passport = new PassportType();

		// 1. �����
		passport.setIdSeries(claimActualPassport.getSeries());

		// 2. �����
		passport.setIdNum(claimActualPassport.getNumber());

		// 3. ��� �����
		passport.setIssuedBy(claimActualPassport.getIssuedBy());

		// 4. ��� �����. ��� �������������
		passport.setIssuedCode(StringHelper.getNullIfEmpty(claimActualPassport.getIssuedCode()));

		// 5. ���� ������
		passport.setIssueDt(claimActualPassport.getIssuedDate());

		// 6. ������ ����������� ��������
		passport.setPrevPassportInfoFlag(claimPreviousPassport != null);
		passport.setPrevPassport(makePrevPasport(claimPreviousPassport));

		return passport;
	}

	private PrevPassportType makePrevPasport(Passport claimPassport)
	{
		if (claimPassport == null)
			return null;

		PrevPassportType passport = new PrevPassportType();

		// 1. �����
		passport.setIdSeries(claimPassport.getSeries());

		// 2. �����
		passport.setIdNum(claimPassport.getNumber());

		// 3. ��� �����
		passport.setIssuedBy(claimPassport.getIssuedBy());

		// 4. ��� �����. ��� ������������� (��� ����������� �������� ���� �����������)
//		passport.setIssuedCode(claimActualPassport.getIssuedCode());

		// 5. ���� ������
		passport.setIssueDt(claimPassport.getIssuedDate());

		return passport;
	}

	private MaritalConditionType makeApplicantMaritalCondition(ETSMLoanClaim claim)
	{
		MaritalConditionType marital = new MaritalConditionType();

		// 1. �������� ���������
		FamilyStatus familyStatus = claim.getApplicantFamilyStatus();
		if (familyStatus == null)
			return null;
		marital.setStatus(familyStatus.getCode());

		// (2) ������� ��������
		Spouse claimSpouse = claim.getApplicantSpouse();
		if (claimSpouse != null)
			marital.setMarriageContractFlag(claimSpouse.isMarriageContract());

		// 3. ���� �� ����
		marital.setChildrenFlag(doesApplicantHaveChildren(claim));

		// (4) ������ � �������
		if (claimSpouse != null)
			marital.setSpouseInfo(makeApplicantSpouseInfo(claimSpouse));

		return marital;
	}

	private SpouseInfoType makeApplicantSpouseInfo(Spouse claimSpouse)
	{
		SpouseInfoType spouse = new SpouseInfoType();

		// 1. ��� �������
		spouse.setPersonName(makePersonName(claimSpouse.getName()));

		// 2. ���� �������� �������
		spouse.setBirthday(claimSpouse.getBirthDay());

		// 3. ���� "�� ���������"
		spouse.setDependentFlag(claimSpouse.isDependant());

		// 4. ���� "������� ������� � ��"
		spouse.setSBCreditFlag(makeSBCreditFlag(claimSpouse.haveSBCredit()));

		return spouse;
	}

	private boolean doesApplicantHaveChildren(ETSMLoanClaim claim)
	{
		Collection<Relative> relatives = claim.getApplicantRelatives();
		if (relatives == null)
			return false;

		for (Relative relative : relatives)
		{
			FamilyRelation status = relative.getStatus();
			if (status != null && status.isChildren())
				return true;
		}
		return false;
	}

	private ApplicantDataType.RelativeList makeApplicantRelativeList(ETSMLoanClaim claim)
	{
		Collection<Relative> claimRelatives = claim.getApplicantRelatives();
		if (CollectionUtils.isEmpty(claimRelatives))
			return null;

		ApplicantDataType.RelativeList relativeList = new ApplicantDataType.RelativeList();
		Collection<RelativeInfoType> relatives = relativeList.getRelatives();
		for (Relative claimRelative : claimRelatives)
			relatives.add(makeRelative(claimRelative));
		return relativeList;
	}

	private RelativeInfoType makeRelative(Relative claimRelative)
	{
		RelativeInfoType relative = new RelativeInfoType();

		// (1) ������� �������
		FamilyRelation status = claimRelative.getStatus();
		if (status != null)
			relative.setType(status.getCode());

		// 2. ���
		relative.setPersonName(makePersonName(claimRelative.getName()));

		// (3) ���� ��������
		Calendar birthDay = claimRelative.getBirthDay();
		if (birthDay != null)
			relative.setBirthday(birthDay);

		// 4. ���� "�� ���������"
		relative.setDependentFlag(claimRelative.isDependant());

		// 5. ���� "������� ������� � ��"
		relative.setSBCreditFlag(makeSBCreditFlag(claimRelative.haveSBCredit()));

		// 6. ���� "�������� ��"
		relative.setSBEmployeeFlag(claimRelative.isSBEmployee());

		// (7) ����� ������. ����������� �����������, ���� SBEmployeeFlag = true
		if (claimRelative.isSBEmployee())
			relative.setName(claimRelative.getEmployeePlace());

		return relative;
	}

	private EmploymentHistoryType2 makeApplicantEmploymentHistory(ETSMLoanClaim claim)
	{
		Organization organization = claim.getApplicantOrganization();

		EmploymentHistoryType2 history = new EmploymentHistoryType2();

		if (claim.getApplicantEmploymentType() == null)
			return null;

		// 1. ������ ���������
		history.setStatus(claim.getApplicantEmploymentType().getCode());

		// (2) ���������� �� �����������
		history.setOrgInfo(makeOrgInfo(organization));

		// (3) �������������� ���������� �� �����������
		history.setOrgInfoExt(makeOrgInfoExt(organization));

		// (4) ������ ���������� ��
		history.setSBEmployeeFlag(claim.getApplicantAsSBEmployee() != null);
		history.setSBEmployee(makeApplicantSBEmployee(claim));

		// (5) ������ ������� ����������
		history.setEmployeeInfo(makeApplicantEmployeeInfo(claim));

		// (6) �������� ������� ��������
		history.setOwnBusinessDesc(StringHelper.getNullIfEmpty(claim.getApplicantOwnBusinessDescription()));

		return history;
	}

	private OrgInfo makeOrgInfo(Organization organization)
	{
		if (organization == null)
			return null;

		OrgInfo orgInfo = new OrgInfo();

		// 1. ��������������-�������� �����
		orgInfo.setEmployerCode(organization.getFormOfIncorporation().getCode());

		return orgInfo;
	}

	private OrgInfoExtType makeOrgInfoExt(Organization organization)
	{
		if (organization == null)
			return null;

		OrgInfoExtType orgInfo = new OrgInfoExtType();

		// 1. ������ ������������ ��������/ �����������
		orgInfo.setFullName(organization.getFullName());

		// 2. ���
		orgInfo.setTaxId(organization.getTaxID());

		// 3. ��� ������������ ��������
		IndustSectorType industSector = new IndustSectorType();
		industSector.setCode(organization.getKindOfActivity().getCode());
		industSector.setComment(StringHelper.getNullIfEmpty(organization.getKindOfActivityComment()));
		orgInfo.setIndustSector(industSector);

		// 4. ���������� ����������� � �������� ������������
		orgInfo.setNumEmployeesCode(organization.getNumberOfEmployees().getCode());

		return orgInfo;
	}

	private SBEmployeeType makeApplicantSBEmployee(ETSMLoanClaim claim)
	{
		SBEmployee claimSBEmployee = claim.getApplicantAsSBEmployee();
		if (claimSBEmployee == null)
			return null;

		SBEmployeeType sbEmployee = new SBEmployeeType();

		// 1. ������������ ��� �����
		sbEmployee.setRegionId(claimSBEmployee.getTb());

		// 2. ������ ������������ �������������
		sbEmployee.setFullName(claimSBEmployee.getDepartmentFullName());

		// 3. ��� ��������� (������������ ��� ���)
		sbEmployee.setJobType(claimSBEmployee.isTBChairman() ? "1" : "0");

		return sbEmployee;
	}

	private EmploymentHistoryType2.EmployeeInfo makeApplicantEmployeeInfo(ETSMLoanClaim claim)
	{
		Employee claimEmployee = claim.getApplicantAsEmployee();
		if (claimEmployee == null)
			return null;

		EmploymentHistoryType2.EmployeeInfo employee = new EmploymentHistoryType2.EmployeeInfo();

		// 1. ��������� ���������� ���������
		employee.setJobType(claimEmployee.getPositionCategory().getCode());

		// 2. ������������ ���������
		employee.setJobTitle(claimEmployee.getPositionName());

		// 3. ��� ����� ������ �������� � ��������
		employee.setExperienceCode(claimEmployee.getJobExperience().getCode());

		// 4. ���������� ������� ���� �� ��������� 3 ����
		employee.setWorkPlacesNum(BigInteger.valueOf(claimEmployee.getWorkPlacesCount()));

		return employee;
	}

	private IncomeType makeApplicantIncome(ETSMLoanClaim claim)
	{
		if (claim.getApplicantBasicIncome() == null &&
				claim.getApplicantAdditionalIncome() == null &&
				claim.getApplicantFamilyIncome() == null &&
				claim.getApplicantExpenses() == null)
			return null;
		IncomeType income = new IncomeType();
		// 1. �������������� �������� �����
		income.setBasicIncome6M(claim.getApplicantBasicIncome());

		// 2. �������������� �������������� �����
		income.setAddIncome6M(claim.getApplicantAdditionalIncome());

		// 3. �������������� ����� �����
		income.setFamilyIncome6M(claim.getApplicantFamilyIncome());

		// 4. �������������� �������
		income.setExpenses6M(claim.getApplicantExpenses());

		return income;
	}

	private ApplicantDataType.RealEstateList makeApplicantRealEstateList(ETSMLoanClaim claim)
	{
		Collection<RealEstate> claimRealEstates = claim.getApplicantRealEstates();
		if (CollectionUtils.isEmpty(claimRealEstates))
			return null;

		ApplicantDataType.RealEstateList realEstateList = new ApplicantDataType.RealEstateList();
		Collection<RealEstateType> realEstates = realEstateList.getRealEstates();
		for (RealEstate claimEstate : claimRealEstates)
			realEstates.add(makeRealEstate(claimEstate));
		return realEstateList;
	}

	private RealEstateType makeRealEstate(RealEstate claimEstate)
	{
		RealEstateType realEstate = new RealEstateType();

		// 1. ��� ������������
		realEstate.setType(claimEstate.getType().getCode());

		// 2. �����
		realEstate.setAddress(claimEstate.getAddress());

		// 3. ��� ������������
		realEstate.setPurchaseYear(claimEstate.getPurchaseYear());

		// 4. �������
		realEstate.setArea(claimEstate.getSquare());

		// 5. ������� ���������
		realEstate.setUnits(claimEstate.getSquareUnits().getCode());

		// 6. �������� ��������� � �������� ���
		realEstate.setCost(claimEstate.getCost());

		return realEstate;
	}

	private ApplicantDataType.VehicleList makeApplicantVehicleList(ETSMLoanClaim claim)
	{
		Collection<Vehicle> claimVehicles = claim.getApplicantVehicles();
		if (CollectionUtils.isEmpty(claimVehicles))
			return null;

		ApplicantDataType.VehicleList vehicleList = new ApplicantDataType.VehicleList();
		Collection<VehicleType> vehicles = vehicleList.getVehicles();
		for (Vehicle claimVehicle : claimVehicles)
			vehicles.add(makeVehicle(claimVehicle));
		return vehicleList;
	}

	private VehicleType makeVehicle(Vehicle claimVehicle)
	{
		VehicleType vehicle = new VehicleType();

		// 1. ��� ��
		vehicle.setType(claimVehicle.getType().getCode());

		// 2. ��������������� ����� ��
		vehicle.setRegNumber(claimVehicle.getRegistationNumber());

		// 3. ��� ������������
		vehicle.setPurchaseYear(claimVehicle.getPurchaseYear());

		// 4. ����� ��
		vehicle.setBrandName(claimVehicle.getBrandName());

		// 5. ������� �� � �����
		vehicle.setAgeInYears(claimVehicle.getAge());

		// 6. �������� ��������� � �������� ���
		vehicle.setCost(claimVehicle.getCost());

		return vehicle;
	}

	private ApplicantAddDataType makeApplicantAdditionalData(ETSMLoanClaim claim)
	{
		ApplicantAddDataType data = new ApplicantAddDataType();

		// 1. ��������� ������ �������
		LoanIssueType loanIssueType = makeLoanIssue(claim);
		if (loanIssueType == null)
			return null;
		data.setLoanIssue(loanIssueType);

		// 2. �������� �� ������� � ��������� ������������� ����������� ����� � ��������
		data.setInsuranceFlag(claim.getApplicantInsuranceFlag());

		// (3) ��������� ����� ��������������� �����
		data.setInsuranceNumber(StringHelper.getNullIfEmpty(claim.getApplicantSNILS()));

		// 4. �������� ������� �� ������ ���������� ������ �� ���
		data.setCBReqApprovalFlag(claim.getApplicantBKIRequestFlag());

		// 5. �������� ������� �� �������������� ������ ���������� � ���
		data.setCBSendApprovalFlag(claim.getApplicantBKIGrantFlag());

		// 6. �������� ������� �� �������������� ������ ���������� � ���
		data.setPFRSendApprovalFlag(claim.getApplicantPFRGrantFlag());

		// (7) ��� �������� ��������� �������
		data.setCBCode(StringHelper.getNullIfEmpty(claim.getApplicantCreditHistoryCode()));

		// (8) �������� ������� �� ��������� ��������� ����� ��� "�������� ������" ��� �������� ������ ������� ������
		data.setCCardGetApprovalFlag(claim.getApplicantGetCreditCardFlag());

		// 9. ������, ��������� ������������� ��������
		data.setSpecialAttentionFlag(claim.getApplicantSpecialAttentionFlag());

		// (10) ���� ���������� ����� / �����, �� ������� ����������� ������
		ApplicantAddDataType.SalaryCardList salaryCardList = makeApplicantSalaryCardList(claim);
		data.setSBSalaryCardFlag(salaryCardList != null);
		data.setSalaryCardList(salaryCardList);

		// (11) ���� ���������� ����� / �����, �� ������� ����������� ������
		ApplicantAddDataType.SalaryDepList salaryDepList = makeApplicantSalaryDepositList(claim);
		data.setSBSalaryDepFlag(salaryDepList != null);
		data.setSalaryDepList(salaryDepList);

		// (12) �������� ��������� ������
		ShareHolderType shareHolder = makeApplicantSBShareHolder(claim);
		data.setSBShareHolderFlag(shareHolder != null);
		data.setShareHolder(shareHolder);

		// 13. ���� ���������� ������
		data.setSigningDate(claim.getSigningDate());

		// 14. ������� ������
		ApplicantAddDataType.PersonVerify personVerify = data.getPersonVerify();
		if (personVerify == null)
		{
			personVerify = new ApplicantAddDataType.PersonVerify();
			data.setPersonVerify(personVerify);
		}

		List<VerifyQuestionInfoType> verifyQuestionInfos = personVerify.getVerifyQuestionInfos();

		Collection<Question> questions = claim.getQuestions();

		for (Question question : questions)
		{
			VerifyQuestionInfoType questionInfoType = new VerifyQuestionInfoType();
			questionInfoType.setVerifyQuestionNumber(question.getId());
			questionInfoType.setVerifyQuestionText(question.getText());
			questionInfoType.setVerifyQuestionAnswer(AnswerTypeWrapper.getInstance(question.getAnswerType()).toGate(question.getAnswer()));
			verifyQuestionInfos.add(questionInfoType);
		}

		return data;
	}

	private LoanIssueType makeLoanIssue(ETSMLoanClaim claim)
	{
		LoanIssueType loanIssue = new LoanIssueType();

		// 1. ������ ������
		LoanIssueMethod loanIssueMethod = claim.getLoanIssueMethod();
		if (loanIssueMethod == null)
			return null;
		loanIssue.setType(loanIssueMethod.getCode().toString());

		// (2) ����� ����� ������ � ���������
		loanIssue.setAcctId(StringHelper.getNullIfEmpty(claim.getLoanIssueAccount()));

		// (3) ����� ���������� �����
		loanIssue.setCardNum(StringHelper.getNullIfEmpty(claim.getLoanIssueCard()));

		return loanIssue;
	}

	private ApplicantAddDataType.SalaryCardList makeApplicantSalaryCardList(ETSMLoanClaim claim)
	{
		Collection<String> salaryCards = claim.getApplicantSalaryCards();
		Collection<String> pensionCards = claim.getApplicantPensionCards();
		if (CollectionUtils.isEmpty(salaryCards) && CollectionUtils.isEmpty(pensionCards))
			return null;

		ApplicantAddDataType.SalaryCardList cardList = new ApplicantAddDataType.SalaryCardList();
		Collection<SalaryCardType> cards = cardList.getSalaryCards();

		if (salaryCards != null)
		{
			for (String cardNumber : salaryCards)
				cards.add(makeSalaryCard(CardKind.SALARY, cardNumber));
		}

		if (pensionCards != null)
		{
			for (String cardNumber : pensionCards)
				cards.add(makeSalaryCard(CardKind.PENSION, cardNumber));
		}

		return cardList;
	}

	private SalaryCardType makeSalaryCard(CardKind cardKind, String cardNumber)
	{
		SalaryCardType card = new SalaryCardType();
		card.setType(cardKind.getCode());
		card.setCardNum(cardNumber);
		return card;
	}

	private ApplicantAddDataType.SalaryDepList makeApplicantSalaryDepositList(ETSMLoanClaim claim)
	{
		Collection<String> salaryDeposits = claim.getApplicantSalaryDeposits();
		Collection<String> pensionDeposits = claim.getApplicantPensionDeposits();
		if (CollectionUtils.isEmpty(salaryDeposits) && CollectionUtils.isEmpty(pensionDeposits))
			return null;

		ApplicantAddDataType.SalaryDepList depositList = new ApplicantAddDataType.SalaryDepList();
		Collection<SalaryDepType> deposits = depositList.getSalaryDeps();

		if (salaryDeposits != null)
		{
			for (String depositNumber : salaryDeposits)
				deposits.add(makeSalaryDeposit(DepositKind.SALARY, depositNumber));
		}

		if (pensionDeposits != null)
		{
			for (String depositNumber : pensionDeposits)
				deposits.add(makeSalaryDeposit(DepositKind.PENSION, depositNumber));
		}

		return depositList;
	}

	private SalaryDepType makeSalaryDeposit(DepositKind depositKind, String depositNumber)
	{
		SalaryDepType deposit = new SalaryDepType();
		deposit.setType(depositKind.getCode());
		deposit.setAcctId(depositNumber);
		return deposit;
	}

	private ShareHolderType makeApplicantSBShareHolder(ETSMLoanClaim claim)
	{
		int commonSharesCount = claim.getApplicantSBCommonSharesCount();
		int preferenceSharesCount = claim.getApplicantSBPreferenceSharesCount();
		if (commonSharesCount == 0 && preferenceSharesCount == 0)
			return null;

		ShareHolderType shareHolder = new ShareHolderType();
		shareHolder.setCommonShareAmount(commonSharesCount);
		shareHolder.setPreferenceShareAmount(preferenceSharesCount);
		return shareHolder;
	}

	///////////////////////////////////////////////////////////////////////////

	private PersonNameType makePersonName(PersonName claimPersonName)
	{
		PersonNameType name = new PersonNameType();
		name.setLastName(claimPersonName.getLastName());
		name.setFirstName(claimPersonName.getFirstName());
		name.setMiddleName(StringHelper.getNullIfEmpty(claimPersonName.getMiddleName()));
		return name;
	}

	private String makeCurrency(Currency currency)
	{
		if (CurrencyUtils.isRUR(currency))
			return "RUB";

		if (CurrencyUtils.isEUR(currency))
			return "EUR";

		if (CurrencyUtils.isUSD(currency))
			return "USD";

		throw new IllegalArgumentException("����������� ��� ������: " + currency.getCode() + ". ��������� �����/����/�������");
	}

	private String makeSBCreditFlag(Boolean haveSBCredit)
	{
		if (haveSBCredit != null)
			return haveSBCredit ? "1" : "0";
		return "2";
	}
}
