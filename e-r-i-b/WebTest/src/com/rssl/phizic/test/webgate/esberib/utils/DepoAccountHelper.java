package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author mihaylov
 * @ created 24.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хелпер для создания ответов на запросы по счетам Депо
 */
public class DepoAccountHelper extends BaseResponseHelper
{
	private static ExternalResourceService resourceService = new ExternalResourceService();
	private static BigDecimal NDS = new BigDecimal(0.18);  // ставка НДС
	private static int DEPO_ACCOUNT_COUNT = 2;// колличество счетов Депо у клиента

	/**
	 * Заполнение массива счетов Депо для GFL, если у клиента
	 * @param login - логин клиента
	 * @return массив счетов депо клиента.
	 */
	public DepoAccounts_Type getDepoAccountRecs(Login login)
	{
		DepoAccounts_Type depoRec = new DepoAccounts_Type();
		depoRec.setStatus(getDepoRecStatus());
		if(depoRec.getStatus().getStatusCode() != 0)
			return depoRec;
		List<DepoAccountLink> depoAccounts;
		try
		{
			depoAccounts = resourceService.getLinks(login,DepoAccountLink.class);
		}
		catch (BusinessException e)
		{
			depoAccounts = new ArrayList<DepoAccountLink>();
		}
		catch (BusinessLogicException e)
		{
			depoAccounts = new ArrayList<DepoAccountLink>();
		}

		List<DepoAccount_Type> result = new ArrayList<DepoAccount_Type>();

		int count = DEPO_ACCOUNT_COUNT - depoAccounts.size();
		//если у клиента < DEPO_ACCOUNT_COUNT-х счетов Депо в системе, то добавляем до DEPO_ACCOUNT_COUNT
		for(int i = 0; i<count; i++ )
		{
			String depoAccountNumber = RandomHelper.rand(10,RandomHelper.DIGITS);
			result.add(createDepoAccount(depoAccountNumber));
		}

		for(DepoAccountLink link : depoAccounts)
		{
			String depoAccountNumber = link.getAccountNumber();
			result.add(createDepoAccount(depoAccountNumber));
		}

		depoRec.setDepoAccount(result.toArray(new DepoAccount_Type[result.size()]));
		return depoRec;
	}

	private Status_Type getDepoRecStatus()
	{
		Status_Type status = new Status_Type();
		int currentHour = Calendar.getInstance().get(Calendar.HOUR);
		if(currentHour>1 && currentHour < 3)
		{
			status.setStatusCode(-800);
			status.setStatusDesc("Депозитарий СБРФ недоступен ввиду технологического перерыва");
			status.setServerStatusDesc(SERVER_STATUS_DESC);
		}
		else
			status.setStatusCode(0);
		return status;
	}

	/**
	 * Заполнение данных о счете депо
	 * @param depoAccountNumber - номер счета Депо
	 * @return данные о счете
	 */
	public DepoAccount_Type createDepoAccount(String depoAccountNumber)
	{
		DepoAccount_Type depoAccountRec = new DepoAccount_Type();
		depoAccountRec.setStatus(getStatus());
		DepAcctInfo_Type depoAccInfo = new DepAcctInfo_Type();

		depoAccInfo.setStatus(LoanStatus_Type.value2);
		depoAccountRec.setDepoAccInfo(depoAccInfo);
		depoAccountRec.setBankInfo(getMockBankInfo());

		DepoAcctId_Type depoAccId = new DepoAcctId_Type();
		depoAccId.setSystemId("Depositary");
		depoAccId.setAcctId(depoAccountNumber);
		depoAccountRec.setDepoAcctId(depoAccId);
		return  depoAccountRec;
	}

	/**
	 * Создание ответа на запрос о приеме/переводе ценных бумаг
	 * @param parameters - исходный запрос
	 * @return DepoAccTranRs
	 */
	public IFXRs_Type createDepoAccTranRs(IFXRq_Type parameters)
	{
		DepoAccTranRqType request = parameters.getDepoAccTranRq();
		IFXRs_Type response = new IFXRs_Type();

		DepoAccTranRsType depoAccTranRs = new DepoAccTranRsType();
		depoAccTranRs.setRqUID(request.getRqUID());
		depoAccTranRs.setRqTm(getRqTm());
		depoAccTranRs.setOperUID(request.getOperUID());
		Status_Type status = getStatus();
		depoAccTranRs.setStatus(status);
		if(depoAccTranRs.getStatus().getStatusCode() == 0)
		{
			depoAccTranRs.setDocNumber(generateDocNumber());
		}
		response.setDepoAccTranRs(depoAccTranRs);
		return response;
	}

	/**
	 * Создание ответа на запрос о регистрации ценной бумаги
	 * @param parameters - исходный запрос
	 * @return DepoAccSecRegRs
	 */
	public IFXRs_Type createDepoAccSecRegRs(IFXRq_Type parameters)
	{
		DepoAccSecRegRqType request = parameters.getDepoAccSecRegRq();
		IFXRs_Type response = new IFXRs_Type();

		DepoAccSecRegRsType depoAccSecRegRs = new DepoAccSecRegRsType();
		depoAccSecRegRs.setRqUID(request.getRqUID());
		depoAccSecRegRs.setRqTm(getRqTm());
		depoAccSecRegRs.setOperUID(request.getOperUID());
		Status_Type status = getStatus();
		depoAccSecRegRs.setStatus(status);
		if(depoAccSecRegRs.getStatus().getStatusCode() == 0)
		{
			depoAccSecRegRs.setDocNumber(generateDocNumber());
		}
		response.setDepoAccSecRegRs(depoAccSecRegRs);
		return response;
	}

	/**
	 * Создание ответа на запрос о приеме/переводе ценных бумаг
	 * @param parameters - исходный запрос
	 * @return DepoAccInfoRs
	 */
	public IFXRs_Type createDepoAccInfoRs(IFXRq_Type parameters)
	{
		// данные исходного запроса
		DepoAccInfoRqType request = parameters.getDepoAccInfoRq();

		IFXRs_Type response = new IFXRs_Type();
		DepoAccInfoRsType depoAccInfoRs = new DepoAccInfoRsType();
		depoAccInfoRs.setRqUID(request.getRqUID());
		depoAccInfoRs.setRqTm(getRqTm());
		depoAccInfoRs.setOperUID(request.getOperUID());

		DepoAcctId_Type[] depoAccRecs = request.getDepoAcctId();
		List<DepoAcctRes_Type> depoAcctResList = new ArrayList<DepoAcctRes_Type>();

		for(DepoAcctId_Type depoAcc : depoAccRecs)
		{
			DepoAcctRes_Type res = new DepoAcctRes_Type();
			depoAcctResList.add(res);
			res.setDepoAcctId(depoAcc);

			DepoAcctInfo_Type depoAcctInfo = new DepoAcctInfo_Type();
			depoAcctInfo.setBankInfo(getMockBankInfo());
			res.setAcctInfo(depoAcctInfo);

			res.setStatus(getStatus());
			if(res.getStatus().getStatusCode() != 0)
				continue;

			AcctBal_Type debt = new AcctBal_Type();
			debt.setCurAmt(getRandomDecimal());
			debt.setAcctCur("RUR");
			res.setAcctBal(debt);

			String acctStatus = RandomHelper.rand(1,"0111");
			depoAcctInfo.setStatus(acctStatus);

			depoAcctInfo.setIsOperationAllowed(true);
			depoAcctInfo.setAgreemtNum("7758-2c-"+RandomHelper.rand(3,RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS));
			depoAcctInfo.setStartDt(getStringDate(getRandomDate()));

			res.setCustRec(getCustRec());
		}

		depoAccInfoRs.setDepoAcctInfoRec(depoAcctResList.toArray(new DepoAcctRes_Type[depoAccRecs.length]));
		response.setDepoAccInfoRs(depoAccInfoRs);
		return response;
	}

	/**
	 * Ответ на получение задолженности по счету депо
	 * @param parameters запрос от ЕРИБ
	 * @return ответ от шины
	 */
	public IFXRs_Type createDepoDeptsInfoRs(IFXRq_Type parameters)
	{
		// данные исходного запроса
		DepoAccInfoRqType request = parameters.getDepoDeptsInfoRq();
		//ответ на запрос
		IFXRs_Type response = new IFXRs_Type();
		DepoDeptsInfoRsType depoDeptsInfoRs = new DepoDeptsInfoRsType();
		response.setDepoDeptsInfoRs(depoDeptsInfoRs);

		depoDeptsInfoRs.setRqUID(request.getRqUID());
		depoDeptsInfoRs.setRqTm(getRqTm());
		depoDeptsInfoRs.setOperUID(request.getOperUID());

		DepoDeptsInfoRsTypeDepoAcctDeptInfoRec depoAcctDeptInfoRec = new DepoDeptsInfoRsTypeDepoAcctDeptInfoRec();
		depoDeptsInfoRs.setDepoAcctDeptInfoRec(depoAcctDeptInfoRec);
		DepoDeptRes_Type depoDeptRes = new DepoDeptRes_Type();
		depoAcctDeptInfoRec.setDepoDeptRes(depoDeptRes);
		//выставляем информацию о счете депо из запроса
		depoDeptRes.setDepoAcctId(request.getDepoAcctId(0));


		Status_Type status = getStatus();
		depoDeptRes.setStatus(status);

		if(status.getStatusCode() != 0)
			return response;

		AcctBal_Type acctBal = new AcctBal_Type();
		acctBal.setCurAmt(getRandomDecimal());
		acctBal.setAcctCur("RUR");
		depoDeptRes.setAcctBal(acctBal);

		DepoAcctBalRec_Type[] deptRecs = new DepoAcctBalRec_Type[15];
		for(int i =0; i< deptRecs.length; i++)
		{
			DepoAcctBalRec_Type depoAcctBalRec = new DepoAcctBalRec_Type();
			depoAcctBalRec.setRecNumber(RandomHelper.rand(20,RandomHelper.DIGITS));
			BigDecimal debt = getRandomDecimal();
			depoAcctBalRec.setCurAmt(debt);
			depoAcctBalRec.setCurAmtNDS(debt.multiply(NDS));
			depoAcctBalRec.setAcctCur("RUR");
			Calendar effDate = new GregorianCalendar();
			effDate.add(Calendar.MONTH, -i);
			depoAcctBalRec.setEffDt(getStringDate(effDate));

			Calendar startDate = new GregorianCalendar();
			startDate.add(Calendar.MONTH,-i-1);
			startDate.set(Calendar.DAY_OF_MONTH,1);
			depoAcctBalRec.setStrDt(getStringDate(startDate));

			Calendar endDate = new GregorianCalendar();
			endDate.add(Calendar.MONTH,-i);
			endDate.set(Calendar.DAY_OF_MONTH,1);
			endDate.add(Calendar.DATE,-1);
			depoAcctBalRec.setEndDt(getStringDate(endDate));
			deptRecs[i] = depoAcctBalRec;
		}
		depoDeptRes.setDepoAcctBalRec(deptRecs);

		return response;
	}

	/**
	 * Ответ на получение информации о позиции по счету депо
	 * @param parameters запрос от ЕРИБ
	 * @return ответ от шины
	 */
	public IFXRs_Type createDepoAccSecInfoRs(IFXRq_Type parameters)
	{
		// данные исходного запроса
		DepoAccInfoRqType request = parameters.getDepoAccSecInfoRq();
		//ответ на запрос
		IFXRs_Type response = new IFXRs_Type();
		DepoAccSecInfoRsType depoAccSecInfoRs = new DepoAccSecInfoRsType();
		depoAccSecInfoRs.setRqUID(request.getRqUID());
		depoAccSecInfoRs.setRqTm(getRqTm());
		depoAccSecInfoRs.setOperUID(request.getOperUID());

		DepoAccSecInfoRsTypeDepoAccSecInfoRec secInfoRec = new DepoAccSecInfoRsTypeDepoAccSecInfoRec();
		secInfoRec.setDepoAcctId(request.getDepoAcctId(0));

		DepoSecurityRec_Type[] depoRecs = new DepoSecurityRec_Type[2];

		DepoSecurityRec_Type baseDepoSecurityRec = new DepoSecurityRec_Type();
		DivisionNumber_Type divisionNumber = new DivisionNumber_Type();
		divisionNumber.setType("Основной");
		divisionNumber.setNumber("L10000");
		baseDepoSecurityRec.setDivisionNumber(divisionNumber);
		depoRecs[0] = baseDepoSecurityRec;

		DepoSecurityRec_Type addDepoSecurityRec = new DepoSecurityRec_Type();
		divisionNumber = new DivisionNumber_Type();
		divisionNumber.setType("Раздел инвестора 4N8LR");
		divisionNumber.setNumber("L2049Ф");
		addDepoSecurityRec.setDivisionNumber(divisionNumber);		
		DepoSecuritySectionInfo_Type[] sectionRecs = new DepoSecuritySectionInfo_Type[8];
		sectionRecs[0]  = new DepoSecuritySectionInfo_Type(null,"E00008800001",Long.valueOf(13557), DepoStorageMethod_Type.value1);
		sectionRecs[1]  = new DepoSecuritySectionInfo_Type(null,"E00150900018",Long.valueOf(209360), DepoStorageMethod_Type.value1);
		sectionRecs[2]  = new DepoSecuritySectionInfo_Type(null,"E00149100004",Long.valueOf(7702), DepoStorageMethod_Type.value1);
		sectionRecs[3]  = new DepoSecuritySectionInfo_Type(null,"E00144600003",Long.valueOf(171321), DepoStorageMethod_Type.value1);
		sectionRecs[4]  = new DepoSecuritySectionInfo_Type(getSecurityMarkers(3,30),"E00033000001",Long.valueOf(30), DepoStorageMethod_Type.value3);
		sectionRecs[5]  = new DepoSecuritySectionInfo_Type(getSecurityMarkers(1,10),"E00001600001",Long.valueOf(10), DepoStorageMethod_Type.value3);
		sectionRecs[6]  = new DepoSecuritySectionInfo_Type(getSecurityMarkers(2,10),"E00008800001",Long.valueOf(10), DepoStorageMethod_Type.value3);
		sectionRecs[7]  = new DepoSecuritySectionInfo_Type(getSecurityMarkers(3,30),"E00033000001",Long.valueOf(30), DepoStorageMethod_Type.value2);
		addDepoSecurityRec.setSectionInfo(sectionRecs);
		
		depoRecs[1] = addDepoSecurityRec;
		secInfoRec.setDepoRec(depoRecs);

		secInfoRec.setStatus(getStatus());

		depoAccSecInfoRs.setDepoAccSecInfoRec(secInfoRec);
		response.setDepoAccSecInfoRs(depoAccSecInfoRs);

		return response;
	}

	/**
	 * Метод для заполнения маркированных ценных бумаг
	 * @param securityCount - кол-во различных маркеров для данной бумаги
	 * @param remainderSum - общее кол-во ценных бумаг
	 * @return
	 */
	private SecurityMarker_Type[] getSecurityMarkers(int securityCount, int remainderSum)
	{
		Random rand = new Random();
		int currentRemainderSum = 0;
		SecurityMarker_Type[] securityMarkers = new SecurityMarker_Type[securityCount];
		SecurityMarker_Type securityMarker;
		for(int i=0; i< securityMarkers.length-1; i++)
		{
			securityMarker = new SecurityMarker_Type();
			securityMarker.setMarkerDescription("c="+rand.nextInt(100)+", H="+rand.nextInt(100)+" 000");
			int remainder = rand.nextInt(remainderSum/securityCount);
			securityMarker.setRemainder(remainder);
			securityMarkers[i] = securityMarker;
			currentRemainderSum =+ remainder;
		}
		securityMarker = new SecurityMarker_Type();
		securityMarker.setMarkerDescription("c="+rand.nextInt(100)+", H="+rand.nextInt(100)+" 000");
		securityMarker.setRemainder(remainderSum - currentRemainderSum);
		securityMarkers[securityMarkers.length-1] = securityMarker;
		return securityMarkers;
	}

	/**
	 * Ответ на получение детальной информации по задолженности по счету депо
	 * @param parameters - исходный запрос
	 * @return ответ от шины
	 */
	public IFXRs_Type createDepoDeptDetInfoRs(IFXRq_Type parameters)
	{
		// данные исходного запроса
		DepoDeptDetInfoRqType request = parameters.getDepoDeptDetInfoRq();
		// ответ на запрос
		IFXRs_Type response = new IFXRs_Type();
		DepoDeptDetInfoRsType depoDeptDetInfo = new DepoDeptDetInfoRsType();
		depoDeptDetInfo.setRqUID(request.getRqUID());
		depoDeptDetInfo.setRqTm(getRqTm());
		depoDeptDetInfo.setOperUID(request.getOperUID());

		DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec depoAcctRec = new DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec();
		depoAcctRec.setDepoAcctId(request.getDepoAcctId(0));

		List<DepoDeptResZad_Type>  depoDeptRecList = new ArrayList<DepoDeptResZad_Type>();
		for(DeptId_Type deptId : request.getDeptId())
		{
			DepoDeptResZad_Type depoDeptRec = new DepoDeptResZad_Type(); //Запись о задолженности на счете депо (<DepoDeptRes>)
			depoDeptRecList.add(depoDeptRec);
			Status_Type status = getStatus();
			depoDeptRec.setStatus(status);
			if(status.getStatusCode() != 0)
				continue;

			depoDeptRec.setEffDt(deptId.getEffDt());
			depoDeptRec.setRecNumber(deptId.getRecNumber());

			DeptRec_Type deptRec = new DeptRec_Type(); //Реквизиты для оплаты задолженности (<DeptRec>)
			deptRec.setBIC("049853792");
			deptRec.setBankName("АБ АЛДАНЗОЛОТОБАНК ЗАО");
			deptRec.setCorBankAccount("30101810800000000792");
			deptRec.setRecipientName("Получатель № 8");
			deptRec.setTaxId("121212121212");
			deptRec.setKPP("234512121");
			deptRec.setRecipientAccount("42305810677456234519");
			depoDeptRec.setDeptRec(deptRec);
		}
		depoAcctRec.setDepoDeptRes(depoDeptRecList.toArray(new DepoDeptResZad_Type[depoDeptRecList.size()]));
		depoDeptDetInfo.setDepoAcctDeptInfoRec(depoAcctRec);
		response.setDepoDeptDetInfoRs(depoDeptDetInfo);
		return response;
	}

	/**
	 * Заполнение информации о владельце счета Депо
	 * @return CustRec_Type
	 */
	private CustRec_Type getCustRec()
	{
		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustId("user:987123");
		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		GregorianCalendar birthDay = new GregorianCalendar();
		birthDay.set(Calendar.YEAR, 1987);
		personInfo.setBirthday(XMLDatatypeHelper.formatDate(birthDay));
		personInfo.setBirthPlace("CCCP");
		personInfo.setTaxId("123456789012345");
		personInfo.setCitizenship("Русский");
		personInfo.setAdditionalInfo("дополнительная информация");

		PersonName_Type personName = new PersonName_Type();
		personName.setLastName("Тестов");
		personName.setFirstName("Тест");
		personInfo.setPersonName(personName);

		IdentityCard_Type identityCard = new IdentityCard_Type();
		identityCard.setIdType("21");
		identityCard.setIdSeries("1101");
		identityCard.setIdNum("123654");
		identityCard.setIssuedBy("Привет из паспортного стола");
		identityCard.setIssuedCode("000001");
		identityCard.setExpDt(getStringDate(getRandomDate()));
		personInfo.setIdentityCard(identityCard);
		custInfo.setPersonInfo(personInfo);
		custRec.setCustInfo(custInfo);

		return custRec;
	}

	/**
	 * Ответ на получение анктных данных для счета депо
	 * @param parameters - исходный запрос
	 * @return ответ от шины
	 */

	public IFXRs_Type createDepoArRs(IFXRq_Type parameters)
	{
		DepoAccInfoRqType request = parameters.getDepoArRq();
		IFXRs_Type response = new IFXRs_Type();
		DepoArRsType depoArRs = new DepoArRsType();
		depoArRs.setRqUID(request.getRqUID());
		depoArRs.setRqTm(getRqTm());
		depoArRs.setOperUID(request.getOperUID());

		depoArRs.setDepoAcctId(request.getDepoAcctId(0));

		Status_Type status = getStatus();
		depoArRs.setStatus(status);
		if(depoArRs.getStatus().getStatusCode() == 0)
		{
			//номер документа
			depoArRs.setDocNumber(generateDocNumber());

			CustRec_Type custRec = new CustRec_Type();
			custRec.setCustId("1258796666");

//			информация о потребителе
			CustInfo_Type custInfo = new CustInfo_Type();
			PersonInfo_Type personInfo = new PersonInfo_Type();
			personInfo.setBirthday(getStringDate(getRandomDate()));
			personInfo.setBirthPlace("г. Москва");
			personInfo.setTaxId("352524112233");
			personInfo.setCitizenship("РФ");
			personInfo.setAdditionalInfo("дополнительная информация");

			PersonName_Type personName = new PersonName_Type();
			personName.setFirstName("Сергей");
			personName.setLastName("Петров");
			personName.setMiddleName("Иванович");
			personInfo.setPersonName(personName);

			IdentityCard_Type identityCard = new IdentityCard_Type();
			identityCard.setIdType(PassportTypeWrapper.getPassportType(ClientDocumentType.REGULAR_PASSPORT_RF));
			identityCard.setIdSeries("1900");
			identityCard.setIdNum("543000");
			identityCard.setIssuedBy("УВД г.Гори Тифлисской губернии Российской империи");
			identityCard.setIssuedCode("1236-88");
			identityCard.setIssueDt(getStringDate(getRandomDate()));
			identityCard.setExpDt(getStringDate(getRandomDate()));
			personInfo.setIdentityCard(identityCard);

			ContactInfo_Type contactInfo = new ContactInfo_Type();

			ContactData_Type contactData = new ContactData_Type();
			contactData.setContactType("HomeTel");
			contactData.setContactNum("1112256");
			contactInfo.setContactData(new ContactData_Type[] {contactData});

			FullAddress_Type addr = new FullAddress_Type();
			addr.setCountry("Россия");
			addr.setAddrType("1");
			addr.setPostalCode("160001");
			addr.setArea("Сокольский район");
			addr.setCity("г. Гори");
			addr.setAddr1("Тихвирский пер.");
			addr.setHouseNum("10");
			addr.setUnitNum("10");

			FullAddress_Type postAddr = new FullAddress_Type();
			postAddr.setCountry("Россия");
			postAddr.setAddrType("3");
			postAddr.setPostalCode("120658");
			postAddr.setRegion("республика Адыгея");
			postAddr.setCity("г. Майкоп");
			postAddr.setAddr1("ул. Мира");
			postAddr.setHouseNum("11");
			postAddr.setUnitNum("89");

			contactInfo.setPostAddr(new FullAddress_Type[] {addr, postAddr});

			personInfo.setContactInfo(contactInfo);
			custInfo.setPersonInfo(personInfo);
			custRec.setCustInfo(custInfo);

//			Информация о рублевом счете
			DepoBankAccount_Type depoBankAccount = new DepoBankAccount_Type();
			depoBankAccount.setAccId("4081781025123456789012");
			depoBankAccount.setCardType("Visa Electron");
			depoBankAccount.setCardId("1253699258999");
			depoBankAccount.setBankName("Сбербанк");
			depoBankAccount.setBIC("12398745632145");
			depoBankAccount.setCorAccId("258741369789654123");
			depoBankAccount.setCorBankName("Сбербанк");
			depoBankAccount.setDest("Для всех денежных переводов");
			custRec.setBnkAccRub(new DepoBankAccount_Type[] {depoBankAccount});

			//информация о валютном счете
            DepoBankAccountCur_Type accountCur = new DepoBankAccountCur_Type();
			accountCur.setAcctCur("USD");
			accountCur.setAccId("40817840000000000");
			accountCur.setCardType("Visa Classic");
			accountCur.setCardId("1253699258999");
			accountCur.setBankName("ВТБ24");
			accountCur.setBIC("12398745632145");
			accountCur.setCorAccId("258741369789654123");
			accountCur.setCorBankName("Сбербанк");
			accountCur.setDest("Для всех денежных переводов");
			custRec.setBnkAccCur(new DepoBankAccountCur_Type[] {accountCur});

			DepoBankAccountAdditionalInfo_Type additionalInfo = new DepoBankAccountAdditionalInfo_Type();
			additionalInfo.setRecIncomeMethod("1");

			DepoRecMethodr_Type depoRecMethod =  new  DepoRecMethodr_Type();
			depoRecMethod.setMethod(new BigInteger("14"));
			additionalInfo.setRecInstructionMethods(new DepoRecMethodr_Type[] {depoRecMethod});

			DepoRecMethodr_Type depoRecMethodr = new  DepoRecMethodr_Type();
			depoRecMethodr.setMethod(new BigInteger("10"));
			depoRecMethodr.setAgreementDate(getStringDate(getRandomDate()));
			depoRecMethodr.setAgreementNumber("12345566666");
			DepoRecMethodr_Type depoRecMethodr1 = new  DepoRecMethodr_Type();
			depoRecMethodr1.setMethod(new BigInteger("1"));

			additionalInfo.setRecInfoMethods(new DepoRecMethodr_Type[] {depoRecMethodr, depoRecMethodr1});

			custRec.setAdditionalInfo(additionalInfo);
			custRec.setStartDate(getStringDate(getRandomDate()));
			depoArRs.setCustRec(custRec);
		}
		response.setDepoArRs(depoArRs);
		return response;
	}

	/**
	 * Создание ответа на сообщение об отзыве документа
	 * @param parameters - исходный запрос
	 * @return DepoRevokeDocRs
	 */
	public IFXRs_Type createDepoRevokeDocRs(IFXRq_Type parameters)
	{
		DepoRevokeDocRqType request = parameters.getDepoRevokeDocRq();
		IFXRs_Type response = new IFXRs_Type();

		DepoRevokeDocRsType depoRevokeDocRs = new DepoRevokeDocRsType();
		depoRevokeDocRs.setRqUID(request.getRqUID());
		depoRevokeDocRs.setRqTm(getRqTm());
		depoRevokeDocRs.setOperUID(request.getOperUID());
		depoRevokeDocRs.setStatus(getStatus());

		response.setDepoRevokeDocRs(depoRevokeDocRs);
		return response;
	}

	/**
	 * Создание ответа на сообщение об приеме документа
	 * @param parameters - исходный запрос
	 * @return DepoRevokeDocRs
	 */
	public IFXRs_Type createMessageRecvRs(IFXRq_Type parameters)
	{
		MessageRecvRqType request = parameters.getMessageRecvRq();
		IFXRs_Type response = new IFXRs_Type();

		MessageRecvRsType messageRecvRs = new MessageRecvRsType();
		messageRecvRs.setRqUID(request.getRqUID());
		messageRecvRs.setRqTm(getRqTm());
		messageRecvRs.setOperUID(request.getOperUID());
		messageRecvRs.setStatus(getStatus());
		messageRecvRs.setDocNumber(request.getDocNumber());
		messageRecvRs.setBankInfo(getMockBankInfo());
		response.setMessageRecvRs(messageRecvRs);
		return response;
	}
	/**
	 * Создание ответа на сообщение об приеме документа
	 * @param parameters - исходный запрос
	 * @return DepoRevokeDocRs
	 */
	public IFXRs_Type createDepoClientRegRs(IFXRq_Type parameters)
	{
		DepoClientRegRqType request = parameters.getDepoClientRegRq();
		IFXRs_Type response = new IFXRs_Type();

		DepoClientRegRsType depoClientRegRs = new DepoClientRegRsType();
		depoClientRegRs.setRqUID(request.getRqUID());
		depoClientRegRs.setRqTm(getRqTm());
		depoClientRegRs.setOperUID(request.getOperUID());
		depoClientRegRs.setStatus(getStatus());
		depoClientRegRs.setIsExist(true);//существует ли клиент в депозитарии
		response.setDepoClientRegRs(depoClientRegRs);
		return response;
	}

	private String generateDocNumber()
	{
		return RandomHelper.rand(10, RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS);
	}
}
