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

		// 1. Данные запроса
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(Calendar.getInstance());
		request.setOperUID(operUID);
		request.setSPName(SPNameType.BP_ERIB);

		// 2. Данные заявки
		request.setApplication(makeApplication(claim));

		return request;
	}

	private ApplicationType makeApplication(ETSMLoanClaim claim)
	{
		ApplicationType application = new ApplicationType();

		// 1. Подразделение
		String departmentETSMCode = claim.getClaimDrawDepartmentETSMCode();
		if (StringHelper.isEmpty(departmentETSMCode))
			throw new IllegalArgumentException("Не указан код подразделения для оформления заявки");
		application.setUnit(departmentETSMCode);

		//Информация о кредитном инспекторе (КИ), отправившем заявку на обработку в ETSM.
		//Параметр передается только в случае отправки кредитной заявки из ЕРИБ в ETSM при инициации процедуры из CRM.
		application.setLoginKI(claim.getLoginKI());

		// 2. Канал продаж
		application.setChannel(claim.getChannel().getCode());

		// 3. Данные кредитного продукта
		application.setProduct(makeProduct(claim));

		// 4. Реквизиты заёмщика
		application.setApplicant(makeApplicant(claim));

		// 12. Признак полной заявки
		application.setCompleteAppFlag(claim.getCompleteAppFlag());

		// 13. Канал получения согласия на обработку персональных данных в БКИ
		ChannelCBRegAApproveType bkiChannelType = claim.getChannelCBRegAApprove();
		if (bkiChannelType != null)
			application.setChannelCBRegAApprove(bkiChannelType.getCode());

		// 14. Канал получения согласия на обработку персональных данных ПФР
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
	 * Формирует данные о (получаемом) кредите
	 * @param claim - заявка на кредит
	 * @return информация о кредите
	 */
	private ProductDataType makeProduct(ETSMLoanClaim claim)
	{
		ProductDataType product = new ProductDataType();

		// 1. Тип (вид) кредитного продукта
		String loanProductType = claim.getLoanProductType();
		if (StringHelper.isEmpty(loanProductType))
			throw new IllegalArgumentException("Не указан тип кредитного продукта");
		product.setType(loanProductType);

		// 2. Код кредитного продукта
		String loanProductCode = claim.getLoanProductCode();
		if (StringHelper.isEmpty(loanProductCode))
			throw new IllegalArgumentException("Не указан код кредитного продукта");
		product.setCode(loanProductCode);

		// 3. Код кредитного субпродукта
		String loanSubProductCode = claim.getLoanSubProductCode();
		if (StringHelper.isEmpty(loanSubProductCode))
			throw new IllegalArgumentException("Не указан код кредитного субпродукта");
		product.setSubProductCode(loanSubProductCode);

		// 4. Сумма кредита
		Money loanAmount = claim.getLoanAmount();
		product.setAmount(loanAmount.getDecimal());
		product.setCurrency(makeCurrency(loanAmount.getCurrency()));

		// 5. Срок кредита
		product.setPeriod(claim.getLoanPeriod());

		// 6. Способ погашения кредита - аннуитетный
		product.setPaymentType(ANNUITY_LOAN_PAYMENT);

		return product;
	}

	/**
	 * Формирует данные о заёмщике
	 * @param claim - заявка на кредит
	 * @return информация о заёмщике
	 */
	private ApplicantDataType makeApplicant(ETSMLoanClaim claim)
	{
		ApplicantDataType applicant = new ApplicantDataType();

		// 1. Тип участника сделки
		applicant.setType(claim.getApplicantType().getCode());

		// 2. Персональные данные о заёмщике
		applicant.setPersonInfo(makeApplicantPersonInfo(claim));

		// 3. Семейное положение заёмщика
		applicant.setMaritalCondition(makeApplicantMaritalCondition(claim));

		// (4) Родственники заёмщика
		applicant.setRelativeList(makeApplicantRelativeList(claim));

		// 5. Место работы заёмщика
		applicant.setEmploymentHistory(makeApplicantEmploymentHistory(claim));

		// 6. Доходы и расходы заёмщика
		applicant.setIncome(makeApplicantIncome(claim));

		// (7) Недвижимость в собственности заёмщика
		ApplicantDataType.RealEstateList realEstateList = makeApplicantRealEstateList(claim);
		applicant.setRealEstateFlag(realEstateList != null);
		applicant.setRealEstateList(realEstateList);

		// (8) Транспортные средства в собственности заёмщика
		ApplicantDataType.VehicleList vehicleList = makeApplicantVehicleList(claim);
		applicant.setVehicleFlag(vehicleList != null);
		applicant.setVehicleList(vehicleList);

		// 10. Дополнительные данные по заявке
		applicant.setAddData(makeApplicantAdditionalData(claim));

		return applicant;
	}

	private ApplicantPersonInfoType makeApplicantPersonInfo(ETSMLoanClaim claim)
	{
		ApplicantPersonInfoType personInfo = new ApplicantPersonInfoType();

		// 1. Настоящие ФИО заёмщика
		personInfo.setPersonName(makePersonName(claim.getApplicantName()));

		// (2) Предыдущие ФИО заёмщика
		PreviousNameDataType previousName = makeApplicantPreviousName(claim);
		personInfo.setNameChangedFlag(previousName != null);
		personInfo.setPreviousName(previousName);

		// (3) ИНН заёмщика
		personInfo.setINN(StringHelper.getNullIfEmpty(claim.getApplicantTaxID()));

		// 4. Пол заёмщика
		personInfo.setSex(claim.getApplicantGender().getCode());

		// 5. Дата рождения заёмщика
		Calendar birthDay = claim.getApplicantBirthDay();
		personInfo.setBirthday(birthDay);

		// 6. Место рождения заёмщика
		personInfo.setBirthPlace(claim.getApplicantBirthPlace());

		// 7. Образование заёмщика
		personInfo.setEducation(makeApplicantEducation(claim));

		// 8. Контакты заёмщика
		personInfo.setContact(makeApplicantContacts(claim));

		// 9. Гражданство заёмщика
		personInfo.setCitizenship(claim.getApplicantCitizenship().getCode());

		// 10. Паспорт заёмщика
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

		// 1. Предыдущая фамилия заёмщика
		name.setLastName(claimPreviousName.getLastName());

		// 2. Предыдущее имя заёмщика
		name.setFirstName(claimPreviousName.getFirstName());

		// (3) Предыдущее отчество заёмщика
		name.setMiddleName(StringHelper.getNullIfEmpty(claimPreviousName.getMiddleName()));

		// 4. Дата изменения ФИО
		name.setDate(claim.getApplicantNameChangeDate());

		// 5. Причина изменения
		name.setReasonCode(nameChangeReason.getCode());
		name.setReasonDesc(StringHelper.getNullIfEmpty(claim.getApplicantNameChangeDescription()));

		return name;
	}

	private EducationDataType makeApplicantEducation(ETSMLoanClaim claim)
	{
		EducationDataType education = new EducationDataType();

		// 1. Образование
		education.setStatus(claim.getApplicantEducation().getCode());

		// 2. Курс неоконченного высшего образования для
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

		// 2. Телефоны
		contacts.setPhoneList(makeApplicantPhoneList(claim));

		// 3. Адреса
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

		// 1. Адрес фактического проживания заёмщика
		Address residenceAddress = claim.getApplicantResidenceAddress();
		if (residenceAddress != null)
			addresses.add(makeAddress(residenceAddress, AddressKind.RESIDENCE));

		// (2) Адрес постоянной регистрации заёмщика
		Address fixedAddress = claim.getApplicantFixedAddress();
		if (fixedAddress != null)
			addresses.add(makeAddress(fixedAddress, AddressKind.FIXED));

		// (3) Адрес временной регистрации заёмщика
		Address temporaryAddress = claim.getApplicantTemporaryAddress();
		if (temporaryAddress != null)
			addresses.add(makeAddress(temporaryAddress, AddressKind.TEMPORARY));

		// 4. Адрес проживания совпадает с адресом постоянной регистрации
		if (residenceAddress != null && fixedAddress != null)
			addressList.setResidenceEqualFlag(ObjectUtils.equals(residenceAddress, fixedAddress));

		// 5. Срок проживания в населенном пункте на момент заполнения анкеты (лет)
		addressList.setCityResidencePeriod(BigInteger.valueOf(claim.getApplicantCityResidencePeriod()));

		// 6. Срок проживания по фактическому адресу(лет)
		addressList.setActResidencePeriod(BigInteger.valueOf(claim.getApplicantActualResidencePeriod()));

		// 7. Право проживания
		addressList.setResidenceRight(claim.getApplicantResidenceRight().getCode());

		// (8) Дата истечения временной регистрации
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

		// 1. Почтовый индекс
		address.setPostalCode(claimAddress.getPostalCode());

		// 2. Регион
		address.setRegionCode(claimAddress.getRegion().getCode());

		// (3) Район/округ
		TypeOfArea areaType = claimAddress.getAreaType();
		if (areaType != null)
			address.setAreaType(areaType.getCode());
		address.setArea(StringHelper.getNullIfEmpty(claimAddress.getAreaName()));

		// (4) Город
		TypeOfCity cityType = claimAddress.getCityType();
		if (cityType != null)
			address.setCityType(cityType.getCode());
		address.setCity(StringHelper.getNullIfEmpty(claimAddress.getCityName()));

		// (5) Населенный пункт
		TypeOfLocality localityType = claimAddress.getLocalityType();
		if (localityType != null)
			address.setSettlementType(localityType.getCode());
		address.setSettlement(StringHelper.getNullIfEmpty(claimAddress.getLocalityName()));

		// 6. Улица
		TypeOfStreet streetType = claimAddress.getStreetType();
		if (streetType != null)
			address.setStreetType(streetType.getCode());
		address.setStreet(claimAddress.getStreetName());

		// 7. Дом (владение)
		address.setHouseNum(claimAddress.getHouseNumber());

		// (8) Корпус
		address.setHouseExt(StringHelper.getNullIfEmpty(claimAddress.getBuildingNumber()));

		// (9) Строение
		address.setUnit(StringHelper.getNullIfEmpty(claimAddress.getUnitNumber()));

		// (10) Квартира/офис
		address.setUnitNum(StringHelper.getNullIfEmpty(claimAddress.getApartmentNumber()));

		return address;
	}

	private PassportType makeApplicantPassport(ETSMLoanClaim claim)
	{
		Passport claimActualPassport = claim.getApplicantPassport();
		Passport claimPreviousPassport = claim.getApplicantPreviousPassport();

		PassportType passport = new PassportType();

		// 1. Серия
		passport.setIdSeries(claimActualPassport.getSeries());

		// 2. Номер
		passport.setIdNum(claimActualPassport.getNumber());

		// 3. Кем выдан
		passport.setIssuedBy(claimActualPassport.getIssuedBy());

		// 4. Кем выдан. Код подразделения
		passport.setIssuedCode(StringHelper.getNullIfEmpty(claimActualPassport.getIssuedCode()));

		// 5. Дата выдачи
		passport.setIssueDt(claimActualPassport.getIssuedDate());

		// 6. Данные предыдущего паспорта
		passport.setPrevPassportInfoFlag(claimPreviousPassport != null);
		passport.setPrevPassport(makePrevPasport(claimPreviousPassport));

		return passport;
	}

	private PrevPassportType makePrevPasport(Passport claimPassport)
	{
		if (claimPassport == null)
			return null;

		PrevPassportType passport = new PrevPassportType();

		// 1. Серия
		passport.setIdSeries(claimPassport.getSeries());

		// 2. Номер
		passport.setIdNum(claimPassport.getNumber());

		// 3. Кем выдан
		passport.setIssuedBy(claimPassport.getIssuedBy());

		// 4. Кем выдан. Код подразделения (для предыдущего паспорта поле отсутствует)
//		passport.setIssuedCode(claimActualPassport.getIssuedCode());

		// 5. Дата выдачи
		passport.setIssueDt(claimPassport.getIssuedDate());

		return passport;
	}

	private MaritalConditionType makeApplicantMaritalCondition(ETSMLoanClaim claim)
	{
		MaritalConditionType marital = new MaritalConditionType();

		// 1. Семейное положение
		FamilyStatus familyStatus = claim.getApplicantFamilyStatus();
		if (familyStatus == null)
			return null;
		marital.setStatus(familyStatus.getCode());

		// (2) Брачный контракт
		Spouse claimSpouse = claim.getApplicantSpouse();
		if (claimSpouse != null)
			marital.setMarriageContractFlag(claimSpouse.isMarriageContract());

		// 3. Есть ли дети
		marital.setChildrenFlag(doesApplicantHaveChildren(claim));

		// (4) Данные о супруге
		if (claimSpouse != null)
			marital.setSpouseInfo(makeApplicantSpouseInfo(claimSpouse));

		return marital;
	}

	private SpouseInfoType makeApplicantSpouseInfo(Spouse claimSpouse)
	{
		SpouseInfoType spouse = new SpouseInfoType();

		// 1. Имя супруга
		spouse.setPersonName(makePersonName(claimSpouse.getName()));

		// 2. Дата рождения супруга
		spouse.setBirthday(claimSpouse.getBirthDay());

		// 3. Флаг "на иждивении"
		spouse.setDependentFlag(claimSpouse.isDependant());

		// 4. Флаг "наличие кредита в СБ"
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

		// (1) Степень родства
		FamilyRelation status = claimRelative.getStatus();
		if (status != null)
			relative.setType(status.getCode());

		// 2. ФИО
		relative.setPersonName(makePersonName(claimRelative.getName()));

		// (3) Дата рождения
		Calendar birthDay = claimRelative.getBirthDay();
		if (birthDay != null)
			relative.setBirthday(birthDay);

		// 4. Флаг "на иждивении"
		relative.setDependentFlag(claimRelative.isDependant());

		// 5. Флаг "наличие кредита в СБ"
		relative.setSBCreditFlag(makeSBCreditFlag(claimRelative.haveSBCredit()));

		// 6. Флаг "работник СБ"
		relative.setSBEmployeeFlag(claimRelative.isSBEmployee());

		// (7) Место работы. Заполняется обязательно, если SBEmployeeFlag = true
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

		// 1. Статус работника
		history.setStatus(claim.getApplicantEmploymentType().getCode());

		// (2) Информация об организации
		history.setOrgInfo(makeOrgInfo(organization));

		// (3) Дополнительная информация об организации
		history.setOrgInfoExt(makeOrgInfoExt(organization));

		// (4) Данные сотрудника СБ
		history.setSBEmployeeFlag(claim.getApplicantAsSBEmployee() != null);
		history.setSBEmployee(makeApplicantSBEmployee(claim));

		// (5) Данные наёмного сотрудника
		history.setEmployeeInfo(makeApplicantEmployeeInfo(claim));

		// (6) Описание частной практики
		history.setOwnBusinessDesc(StringHelper.getNullIfEmpty(claim.getApplicantOwnBusinessDescription()));

		return history;
	}

	private OrgInfo makeOrgInfo(Organization organization)
	{
		if (organization == null)
			return null;

		OrgInfo orgInfo = new OrgInfo();

		// 1. Организационно-правовая форма
		orgInfo.setEmployerCode(organization.getFormOfIncorporation().getCode());

		return orgInfo;
	}

	private OrgInfoExtType makeOrgInfoExt(Organization organization)
	{
		if (organization == null)
			return null;

		OrgInfoExtType orgInfo = new OrgInfoExtType();

		// 1. Полное наименование компании/ организации
		orgInfo.setFullName(organization.getFullName());

		// 2. ИНН
		orgInfo.setTaxId(organization.getTaxID());

		// 3. Вид деятельности компании
		IndustSectorType industSector = new IndustSectorType();
		industSector.setCode(organization.getKindOfActivity().getCode());
		industSector.setComment(StringHelper.getNullIfEmpty(organization.getKindOfActivityComment()));
		orgInfo.setIndustSector(industSector);

		// 4. Количество сотрудников в компании работодателе
		orgInfo.setNumEmployeesCode(organization.getNumberOfEmployees().getCode());

		return orgInfo;
	}

	private SBEmployeeType makeApplicantSBEmployee(ETSMLoanClaim claim)
	{
		SBEmployee claimSBEmployee = claim.getApplicantAsSBEmployee();
		if (claimSBEmployee == null)
			return null;

		SBEmployeeType sbEmployee = new SBEmployeeType();

		// 1. Региональный код банка
		sbEmployee.setRegionId(claimSBEmployee.getTb());

		// 2. Полное наименование подразделения
		sbEmployee.setFullName(claimSBEmployee.getDepartmentFullName());

		// 3. Тип должности (председатель или нет)
		sbEmployee.setJobType(claimSBEmployee.isTBChairman() ? "1" : "0");

		return sbEmployee;
	}

	private EmploymentHistoryType2.EmployeeInfo makeApplicantEmployeeInfo(ETSMLoanClaim claim)
	{
		Employee claimEmployee = claim.getApplicantAsEmployee();
		if (claimEmployee == null)
			return null;

		EmploymentHistoryType2.EmployeeInfo employee = new EmploymentHistoryType2.EmployeeInfo();

		// 1. Категория занимаемой должности
		employee.setJobType(claimEmployee.getPositionCategory().getCode());

		// 2. Наименование должности
		employee.setJobTitle(claimEmployee.getPositionName());

		// 3. Как долго клиент работает в компании
		employee.setExperienceCode(claimEmployee.getJobExperience().getCode());

		// 4. Количество рабочих мест за последние 3 года
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
		// 1. Среднемесячный основной доход
		income.setBasicIncome6M(claim.getApplicantBasicIncome());

		// 2. Среднемесячный дополнительный доход
		income.setAddIncome6M(claim.getApplicantAdditionalIncome());

		// 3. Среднемесячный доход семьи
		income.setFamilyIncome6M(claim.getApplicantFamilyIncome());

		// 4. Среднемесячные расходы
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

		// 1. Вид недвижимости
		realEstate.setType(claimEstate.getType().getCode());

		// 2. Адрес
		realEstate.setAddress(claimEstate.getAddress());

		// 3. Год приобретения
		realEstate.setPurchaseYear(claimEstate.getPurchaseYear());

		// 4. Площадь
		realEstate.setArea(claimEstate.getSquare());

		// 5. Единицы измерения
		realEstate.setUnits(claimEstate.getSquareUnits().getCode());

		// 6. Рыночная стоимость в долларах США
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

		// 1. Вид ТС
		vehicle.setType(claimVehicle.getType().getCode());

		// 2. Регистрационный номер ТС
		vehicle.setRegNumber(claimVehicle.getRegistationNumber());

		// 3. Год приобретения
		vehicle.setPurchaseYear(claimVehicle.getPurchaseYear());

		// 4. Марка ТС
		vehicle.setBrandName(claimVehicle.getBrandName());

		// 5. Возраст ТС в годах
		vehicle.setAgeInYears(claimVehicle.getAge());

		// 6. Рыночная стоимость в долларах США
		vehicle.setCost(claimVehicle.getCost());

		return vehicle;
	}

	private ApplicantAddDataType makeApplicantAdditionalData(ETSMLoanClaim claim)
	{
		ApplicantAddDataType data = new ApplicantAddDataType();

		// 1. Параметры выдачи кредита
		LoanIssueType loanIssueType = makeLoanIssue(claim);
		if (loanIssueType == null)
			return null;
		data.setLoanIssue(loanIssueType);

		// 2. Согласие на участие в программе добровольного страхования жизни и здоровья
		data.setInsuranceFlag(claim.getApplicantInsuranceFlag());

		// (3) Страховой номер индивидуального счета
		data.setInsuranceNumber(StringHelper.getNullIfEmpty(claim.getApplicantSNILS()));

		// 4. Согласие клиента на запрос информации банком из БКИ
		data.setCBReqApprovalFlag(claim.getApplicantBKIRequestFlag());

		// 5. Согласие клиента на предоставление банком информации в БКИ
		data.setCBSendApprovalFlag(claim.getApplicantBKIGrantFlag());

		// 6. Согласие клиента на предоставление банком информации в ПФР
		data.setPFRSendApprovalFlag(claim.getApplicantPFRGrantFlag());

		// (7) Код субъекта кредитной истории
		data.setCBCode(StringHelper.getNullIfEmpty(claim.getApplicantCreditHistoryCode()));

		// (8) Согласие клиента на получение кредитной карты ОАО "Сбербанк России" при принятии такого решения банком
		data.setCCardGetApprovalFlag(claim.getApplicantGetCreditCardFlag());

		// 9. Клиент, требующий персонального внимания
		data.setSpecialAttentionFlag(claim.getApplicantSpecialAttentionFlag());

		// (10) Есть зарплатная карта / карта, на которую зачисляется пенсия
		ApplicantAddDataType.SalaryCardList salaryCardList = makeApplicantSalaryCardList(claim);
		data.setSBSalaryCardFlag(salaryCardList != null);
		data.setSalaryCardList(salaryCardList);

		// (11) Есть зарплатный вклад / вклад, на который зачисляется пенсия
		ApplicantAddDataType.SalaryDepList salaryDepList = makeApplicantSalaryDepositList(claim);
		data.setSBSalaryDepFlag(salaryDepList != null);
		data.setSalaryDepList(salaryDepList);

		// (12) Акционер Сбербанка России
		ShareHolderType shareHolder = makeApplicantSBShareHolder(claim);
		data.setSBShareHolderFlag(shareHolder != null);
		data.setShareHolder(shareHolder);

		// 13. Дата подписания анкеты
		data.setSigningDate(claim.getSigningDate());

		// 14. Вопросы анкеты
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

		// 1. Способ выдачи
		LoanIssueMethod loanIssueMethod = claim.getLoanIssueMethod();
		if (loanIssueMethod == null)
			return null;
		loanIssue.setType(loanIssueMethod.getCode().toString());

		// (2) Номер счета вклада в Сбербанке
		loanIssue.setAcctId(StringHelper.getNullIfEmpty(claim.getLoanIssueAccount()));

		// (3) Номер банковской карты
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

		throw new IllegalArgumentException("Неожиданный код валюты: " + currency.getCode() + ". Ожидаются рубли/евро/доллары");
	}

	private String makeSBCreditFlag(Boolean haveSBCredit)
	{
		if (haveSBCredit != null)
			return haveSBCredit ? "1" : "0";
		return "2";
	}
}
