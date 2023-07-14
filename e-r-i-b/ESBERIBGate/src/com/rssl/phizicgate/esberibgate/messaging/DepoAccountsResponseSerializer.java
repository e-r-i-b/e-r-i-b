package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.*;
import com.rssl.phizicgate.esberibgate.types.depo.DepoAccountStorageMethodWrapper;
import com.rssl.phizicgate.esberibgate.types.depo.RecMethodWrapper;
import com.rssl.phizicgate.esberibgate.types.depo.SecurityMarkerImpl;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.map.MultiValueMap;

import java.util.*;

/**
 * @author mihaylov
 * @ created 27.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Вспомогательный класс для разбора сообщений от шины по счетам Депо и заполнения объектов.
 */
public class DepoAccountsResponseSerializer extends BaseResponseSerializer
{

	/**
	 * Получение списка счетов Депо клиента. Разбираем ответ из GFL.
	 * @param ifxRs - ответ из шины.
	 * @param clientId - идентификатор клиента
	 * @return список счетов Депо
	 */
	public List<DepoAccount> fillDepoAccounts(IFXRs_Type ifxRs,Long clientId)
	{
		if (ifxRs == null)
			return Collections.emptyList();

		DepoAccounts_Type depoRecs = ifxRs.getBankAcctInqRs().getDepoAccounts();
		if(depoRecs == null || depoRecs.getStatus().getStatusCode() != CORRECT_MESSAGE_STATUS)
			return Collections.emptyList();
		DepoAccount_Type[] depoAccountRecs = depoRecs.getDepoAccount();
		if(depoAccountRecs == null || depoAccountRecs.length == 0)
			return Collections.emptyList();

		List<DepoAccount> depoAccounts = new ArrayList<DepoAccount>(depoAccountRecs.length);
		for(DepoAccount_Type depoAccountRec : depoAccountRecs)
		{
			try
			{
				DepoAccount depoAccount = fillDepoAccount(depoAccountRec, clientId);
				depoAccounts.add(depoAccount);								
			}
			catch (Exception e)
			{
				if (depoAccountRec != null && depoAccountRec.getDepoAcctId() != null)
					log.error("Ошибка при заполнении счета Депо №" + depoAccountRec.getDepoAcctId().getAcctId(), e);
				else
					log.error("Ошибка при заполнении счета Депо", e);
			}
		}
		return depoAccounts;
	}

	private DepoAccount fillDepoAccount(DepoAccount_Type depoAccountRec, Long clientId) throws GateLogicException, GateException
	{
		DepoAccountImpl depoAccount = new DepoAccountImpl();		
		depoAccount.setOffice(getOffice(depoAccountRec.getBankInfo()));

		depoAccount.setId(EntityIdHelper.createDepoAccountCompositeId(depoAccountRec, clientId));

		depoAccount.setAccountNumber(depoAccountRec.getDepoAcctId().getAcctId());

		if("1".equals(depoAccountRec.getDepoAccInfo().getStatus().getValue()))
			depoAccount.setState(DepoAccountState.open);
		else
			depoAccount.setState(DepoAccountState.closed);
		return depoAccount;
	}

	/**
	 * Разбор информации по счетам Депо.
	 * @param ifxRq запрос
	 * @param ifxRs - ответ из шины на запрос информации по счетам Депо
	 * @param depoAccountId - идентификаторы счетов Депо
	 * @return < идентификатор счета Депо, счет Депо>
	 */
	public GroupResult<String, DepoAccount> fillDepoAccounts(IFXRq_Type ifxRq, IFXRs_Type ifxRs, String... depoAccountId)
	{
		// считаем, что loginId одинаков для всех счетов депо из запроса, т.к. они принадлежат одному клиенту
		Long loginId = EntityIdHelper.getCommonCompositeId(depoAccountId[0]).getLoginId();
		GroupResult<String, DepoAccount> depoAccounts = new GroupResult<String,DepoAccount>();
		DepoAcctRes_Type[] depoAccountsRes = ifxRs.getDepoAccInfoRs().getDepoAcctInfoRec();
		for(DepoAcctRes_Type depoAcctRes : depoAccountsRes)
		{
			String externalId = EntityIdHelper.createDepoAccountCompositeId(depoAcctRes, loginId);
			try
			{
				DepoAccountImpl depoAccount = parseDepoAccount(ifxRq, depoAcctRes, externalId);
				depoAccounts.putResult(externalId, depoAccount);
				StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);
				storedResourcesService.updateStoredDepoAccount(loginId, depoAccount);
			}
			catch (GateLogicException e)
			{
				depoAccounts.putException(externalId, e);
			}
			catch (GateException e)
			{
				depoAccounts.putException(externalId, e);
			}
		}

		return depoAccounts;
	}

	/**
	 * Заполнение информации по счету депо
	 * @param ifxRq запрос
	 * @param depoAcctRes - запись о счете депо
	 * @param externalId - идентификатор счета депо
	 * @return счет депо
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private DepoAccountImpl parseDepoAccount(IFXRq_Type ifxRq, DepoAcctRes_Type depoAcctRes, String externalId) throws GateLogicException,GateException
	{
		Status_Type status = depoAcctRes.getStatus();
		DepoAcctId_Type depoAcctId = depoAcctRes.getDepoAcctId();
		String depoAccountNumber = depoAcctId.getAcctId();

		//если пришла ошибка по счету, то прекращаем разбор данных по счету
		if(status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			if (OFFLINE_SYSTEM_STATUSES.contains(status.getStatusCode()))
				ESBERIBExceptionStatisticHelper.throwOfflineResponse(status, DepoAcctId_Type.class, ifxRq);
			else
				ESBERIBExceptionStatisticHelper.throwErrorResponse(status, DepoAcctId_Type.class, ifxRq);
		}
		DepoAccountImpl depoAccount = new DepoAccountImpl();
		depoAccount.setAccountNumber(depoAccountNumber);
		depoAccount.setId(externalId);

		AcctBal_Type acctBal = depoAcctRes.getAcctBal();
		depoAccount.setDebt(getDebt(acctBal));

		DepoAcctInfo_Type acctInfo = depoAcctRes.getAcctInfo();
		if("1".equals(acctInfo.getStatus()))
			depoAccount.setState(DepoAccountState.open);
		else
			depoAccount.setState(DepoAccountState.closed);

		depoAccount.setOperationAllowed(acctInfo.getIsOperationAllowed());
		depoAccount.setAgreementNumber(acctInfo.getAgreemtNum());
		depoAccount.setAgreementDate(parseCalendar(acctInfo.getStartDt()));
		depoAccount.setOffice(getOffice(acctInfo.getBankInfo()));
		return depoAccount;
	}

	/**
	 * Заполнение информации о задолженности по счету Депо
	 * @param depoDeptsInfoRs - блок ответа из шини о задолженности по счету Депо
	 * @return информация о задолженности по счету депо
	 */
	public DepoDebtInfo fillDepoDebtInfo(DepoDeptsInfoRsType depoDeptsInfoRs)
	{
		DepoDebtInfoImpl depoDebtInfo = new DepoDebtInfoImpl();
		DepoDeptRes_Type depoDebtRes = depoDeptsInfoRs.getDepoAcctDeptInfoRec().getDepoDeptRes();
		Money totalDebt = getDebt(depoDebtRes.getAcctBal());
		depoDebtInfo.setTotalDebt(totalDebt);
		List<DepoDebtItem> depoDebtItems = new ArrayList<DepoDebtItem>();
		depoDebtInfo.setDebtItems(depoDebtItems);
		DepoAcctBalRec_Type[] depoDebtRec = depoDebtRes.getDepoAcctBalRec();
		if(depoDebtRec == null || depoDebtRec.length == 0)
			return depoDebtInfo;
		for(DepoAcctBalRec_Type depoDebtItemRec : depoDebtRes.getDepoAcctBalRec())
		{
			DepoDebtItemImpl debtItem = new DepoDebtItemImpl();
			debtItem.setRecNumber(depoDebtItemRec.getRecNumber());
			debtItem.setRecDate(parseCalendar(depoDebtItemRec.getEffDt()));
			debtItem.setStartDate(parseCalendar(depoDebtItemRec.getStrDt()));
			debtItem.setEndDate(parseCalendar(depoDebtItemRec.getEndDt()));

			debtItem.setAmount(createMoney(depoDebtItemRec.getCurAmt(),depoDebtItemRec.getAcctCur()));
			debtItem.setAmountNDS(createMoney(depoDebtItemRec.getCurAmtNDS(),depoDebtItemRec.getAcctCur()));
			depoDebtItems.add(debtItem);
		}
		return depoDebtInfo;
	}

	/**
	 * Разбор информации о позиции по счету Депо
	 * @param depoAccSecInfo - блок ответа из шины с позицией по счету Депо
	 * @return информация о позиции по счету Депо
	 */
	public DepoAccountPosition fillDepoAccountPosition(DepoAccSecInfoRsType depoAccSecInfo) throws GateException
	{
		MultiValueMap securityMap = new MultiValueMap();
		DepoAccountPositionImpl depoAccountPosition = new DepoAccountPositionImpl();
		List<DepoAccountDivision> depoAccountDivisions = new ArrayList<DepoAccountDivision>();
		for(DepoSecurityRec_Type depoSecRec : depoAccSecInfo.getDepoAccSecInfoRec().getDepoRec())
		{
			DepoAccountDivisionImpl division = new DepoAccountDivisionImpl();
			division.setDivisionType(depoSecRec.getDivisionNumber().getType());
			division.setDivisionNumber(depoSecRec.getDivisionNumber().getNumber());			
			division.setDepoAccountSecurities(getDepoAccountSecurityList(depoSecRec.getSectionInfo(),securityMap));
			depoAccountDivisions.add(division);									
		}
		depoAccountPosition.setDepoAccountDivisions(depoAccountDivisions);

		//заполняем данные по ценным бумагам из справочника ЦБ в системе.
		BackRefSecurityService backService = GateSingleton.getFactory().service(BackRefSecurityService.class);
		List<SecurityBase> securityBases = backService.getSecuritiesByCodes(new ArrayList(securityMap.keySet()));
		for(SecurityBase securityBase :securityBases)
		{
			List<DepoAccountSecurityImpl> securityList = (List) securityMap.get(securityBase.getInsideCode());
			for(DepoAccountSecurityImpl depoAccountSecurity : securityList)
			{
				depoAccountSecurity.setName(securityBase.getName());
				depoAccountSecurity.setNominal(securityBase.getNominal());
				depoAccountSecurity.setRegistrationNumber(securityBase.getRegistrationNumber());
			}
		}
		return depoAccountPosition;
	}

	/*Заполнение списка ценных бумаг на разделе счета депо*/
	private List<DepoAccountSecurity> getDepoAccountSecurityList(DepoSecuritySectionInfo_Type[] sectionInfo, MultiValueMap securityMap)
	{
		List<DepoAccountSecurity> securities = new ArrayList<DepoAccountSecurity>();
		if(sectionInfo == null || sectionInfo.length == 0)
				return securities;

		for(DepoSecuritySectionInfo_Type securityRec : sectionInfo)
		{
			DepoAccountSecurityImpl security = new DepoAccountSecurityImpl();
			String insideCode = securityRec.getInsideCode();
			security.setInsideCode(insideCode);
			security.setRemainder(securityRec.getRemainder());
			security.setStorageMethod(DepoAccountStorageMethodWrapper.getStorageMethod(
																securityRec.getStorageMethod().getValue()));
			securities.add(security);
			securityMap.put(insideCode,security);

			SecurityMarker_Type[] securityMarkers = securityRec.getSecurityMarker();
			if(securityMarkers != null && securityMarkers.length != 0)
			{
				List<SecurityMarker> securityMarkerList = new ArrayList<SecurityMarker>();
				for(SecurityMarker_Type marker : securityMarkers)
				{
					SecurityMarkerImpl securityMarker = new SecurityMarkerImpl();
					securityMarker.setMarker(marker.getMarkerDescription());
					securityMarker.setRemainder(marker.getRemainder());
					securityMarkerList.add(securityMarker);
				}
				security.setSecurityMarkers(securityMarkerList);
			}
		}
		return securities;
	}

	/**
	 * Разбор детальной информации по задолженностям на счете Депо
	 * @param depoAccount - счет Депо
	 * @param depoDeptRes - блоки ответа из шины о детельной информации по счету Депо
	 * @param debtItem - задолженности по счету Депо
	 * @return < задолженность по счету депо, детальная информация по задолженности>
	 */
	public GroupResult<DepoDebtItem, DepoDebtItemInfo> fillDepoDebtItemInfo(DepoAccount depoAccount, DepoDeptResZad_Type[] depoDeptRes, DepoDebtItem... debtItem)
	{
		GroupResult<DepoDebtItem, DepoDebtItemInfo> result = new GroupResult<DepoDebtItem, DepoDebtItemInfo>();
		for(DepoDeptResZad_Type depoDept : depoDeptRes)
		{
			Calendar effDate = parseCalendar(depoDept.getEffDt());
			DepoDebtItem item = findDepoDebtItem(depoDept.getRecNumber(),effDate,debtItem);
			Status_Type status = depoDept.getStatus();
			if(status.getStatusCode() != CORRECT_MESSAGE_STATUS)
			{
				ESBERIBExceptionStatisticHelper.addError(result, item, status, DepoDeptResZad_Type.class, EntityIdHelper.getCommonCompositeId(depoAccount.getId()));
				continue;
			}
			DeptRec_Type deptRec = depoDept.getDeptRec();
			DepoDebtItemInfoImpl depoDebtItemInfo = new DepoDebtItemInfoImpl();
			depoDebtItemInfo.setBankBIC(deptRec.getBIC());
			depoDebtItemInfo.setBankName(deptRec.getBankName());
			depoDebtItemInfo.setBankCorAccount(deptRec.getCorBankAccount());
			depoDebtItemInfo.setRecipientName(deptRec.getRecipientName());
			depoDebtItemInfo.setRecipientINN(deptRec.getTaxId());
			depoDebtItemInfo.setRecipientKPP(deptRec.getKPP());
			depoDebtItemInfo.setRecipientAccount(deptRec.getRecipientAccount());

			if(item != null)
				result.putResult(item,depoDebtItemInfo);
		}
		return result;
	}

	/**
	 * Заполнение информации о владельце счета депо
	 * @param ifxRs - ответ из шины о детальной информации по счету депо
	 * @param depoAccounts - счета депо
	 * @return < счет депо, информация о владельце>
	 */
	public GroupResult<DepoAccount, Client> fillDepoAccountsClients(IFXRs_Type ifxRs, DepoAccount... depoAccounts)
	{
		GroupResult<DepoAccount, Client> depoAccountClients = new GroupResult<DepoAccount, Client>();
		DepoAcctRes_Type[] depoAccountsRecs = ifxRs.getDepoAccInfoRs().getDepoAcctInfoRec();
		for(DepoAcctRes_Type depoAccountRec : depoAccountsRecs)
		{
			String systemId = depoAccountRec.getDepoAcctId().getSystemId();
			String depoAccountNumber = depoAccountRec.getDepoAcctId().getAcctId();
			DepoAccount depoAcc = findDepoAccount(systemId, depoAccountNumber, depoAccounts);
			if(depoAcc == null)
				continue;
			Status_Type status = depoAccountRec.getStatus();
			if(status.getStatusCode() != CORRECT_MESSAGE_STATUS)
			{
				ESBERIBExceptionStatisticHelper.addError(depoAccountClients, depoAcc, status, DepoAcctRes_Type.class, systemId);
				continue;
			}
			CustRec_Type custRec = depoAccountRec.getCustRec();
			ClientImpl client = fillClient(custRec.getCustInfo());
			client.setId(custRec.getCustId());

			depoAccountClients.putResult(depoAcc,client);
		}
		return depoAccountClients;
	}

	private Money getDebt(AcctBal_Type acctBal)
	{
		Currency currency = getCurrencyByString(acctBal.getAcctCur());
		return safeCreateMoney(acctBal.getCurAmt(), currency);
	}

	private DepoDebtItem findDepoDebtItem(String recNumber, Calendar recDate,DepoDebtItem... debtItem)
	{
		for(DepoDebtItem item : debtItem)
			if(item.getRecNumber().equals(recNumber) && item.getRecDate().equals(recDate))
				return item;
		return null;
	}

	private DepoAccount findDepoAccount(String systemId, String accountNumber, DepoAccount... depoAccounts)
	{
		for(DepoAccount depoAcc : depoAccounts)
		{
			EntityCompositeId depoAccId = EntityIdHelper.getCommonCompositeId(depoAcc.getId());
			if(depoAccId.getSystemId().equals(systemId) && depoAccId.getEntityId().equals(accountNumber))
				return depoAcc;
		}
		return null;
	}

	/**
	 * Анкета депонента
	 * @param ifxRs ответ из шины
	 * @param clientId - внутренний id клиента
	 * @return анкета депонета
	 */
	public DepoAccountOwnerForm getDepoAccountOwnerForm(IFXRs_Type ifxRs, Long clientId)
	{
		DepoAccountOwnerFormImpl form = new DepoAccountOwnerFormImpl();
		DepoArRsType depoArRS =  ifxRs.getDepoArRs();
		form.setDepoAccountNumber(depoArRS.getDepoAcctId().getAcctId());
		form.setLoginId(clientId);

		CustRec_Type custRec = depoArRS.getCustRec();
		CustInfo_Type custInfo = custRec.getCustInfo();
		PersonInfo_Type personInfo = custInfo.getPersonInfo();
		form.setSurName(personInfo.getPersonName().getLastName());
		form.setFirstName(personInfo.getPersonName().getFirstName());
		form.setPartName(personInfo.getPersonName().getMiddleName());
		form.setBirthday(parseCalendar(personInfo.getBirthday()));
		form.setBirthPlace(personInfo.getBirthPlace());
		form.setCitizenship(personInfo.getCitizenship());
		form.setINN(personInfo.getTaxId());
		form.setAdditionalInfo(personInfo.getAdditionalInfo());
		IdentityCard_Type identityCard = personInfo.getIdentityCard();
		form.setIdType(PassportTypeWrapper.getClientDocumentType(identityCard.getIdType()).toString());
		form.setIdSeries(identityCard.getIdSeries());
		form.setIdNum(identityCard.getIdNum());
		form.setIdIssuedBy(identityCard.getIssuedBy());
		form.setIdIssueDate(StringHelper.isEmpty(identityCard.getIssueDt()) ? null : parseCalendar(identityCard.getIssueDt()));
		form.setIdExpDate(StringHelper.isEmpty(identityCard.getExpDt()) ? null : parseCalendar(identityCard.getExpDt()));
		form.setIdIssuedCode(identityCard.getIssuedCode());
		form.setStartDate(StringHelper.isEmpty(custRec.getStartDate()) ? null : parseCalendar(custRec.getStartDate()));

		ContactInfo_Type contactInfo = personInfo.getContactInfo();

		if (contactInfo.getContactData() != null)
			updateContactData(form, contactInfo.getContactData());

		updateAddress(form, contactInfo.getPostAddr());

		Set<DepositorAccount> depositorAccounts = new HashSet<DepositorAccount>();

		for (DepoBankAccount_Type account : custRec.getBnkAccRub())
		{
			depositorAccounts.add(getRUBAccount(account));
		}
		if (custRec.getBnkAccCur() != null)
		{
			for (DepoBankAccountCur_Type account : custRec.getBnkAccCur())
			{
				depositorAccounts.add(getCurAccount(account));
			}
		}
		form.setDepositorAccounts(depositorAccounts);
		if (!StringHelper.isEmpty(custRec.getAdditionalInfo().getRecIncomeMethod()) && custRec.getAdditionalInfo().getRecIncomeMethod().equals("1"))
		{
			form.setRecIncomeMethod("Переводом на денежный счет/счета");
		}

		if (custRec.getAdditionalInfo().getRecInfoMethods() != null)
		{
			String recInfoMethod = "";
			for (DepoRecMethodr_Type method : custRec.getAdditionalInfo().getRecInfoMethods())
			{
				if (method.getMethod().intValue() != 10)
				{
					recInfoMethod = recInfoMethod + RecMethodWrapper.getRecMethod(method.getMethod().toString())+"; ";
				}
				else
				{
					recInfoMethod = recInfoMethod + RecMethodWrapper.getRecMethod(method.getMethod().toString())+method.getAgreementNumber()+" от " + method.getAgreementDate() +"; ";
				}
			}
			form.setRecInfoMethod(recInfoMethod);
		}

		if (custRec.getAdditionalInfo().getRecInstructionMethods() != null)
		{
			String recInstructionMethod = "";
			for (DepoRecMethodr_Type method : custRec.getAdditionalInfo().getRecInstructionMethods())
			{
				if (method.getMethod().intValue() != 10)
				{
					recInstructionMethod = recInstructionMethod + RecMethodWrapper.getRecMethod(method.getMethod().toString())+"; ";
				}
				else
				{
					recInstructionMethod = recInstructionMethod + RecMethodWrapper.getRecMethod(method.getMethod().toString())+method.getAgreementNumber()+" от " + method.getAgreementDate() +"; ";
				}
			}
			form.setRecInstructionMethod(recInstructionMethod);
		}

		return form;
	}



	private void updateContactData(DepoAccountOwnerFormImpl form, ContactData_Type[] contactData)
	{
		for (ContactData_Type data : contactData)
		{
			if (data.getContactType().equals("HomeTel"))
			{
				form.setHomeTel(data.getContactNum());
			}
			else if (data.getContactType().equals("WorkTel"))
			{
				form.setWorkTel(data.getContactNum());
			}
			else if (data.getContactType().equals("MobileTel"))
			{
				form.setMobileTel(data.getContactNum());
				form.setPhoneOperator(data.getPhoneOperName());
			}
			else if (data.getContactType().equals("PrivateEmail"))
			{
				form.setPrivateEmail(data.getContactNum());
			}
			else if (data.getContactType().equals("WorkEmail"))
			{
				form.setWorkEmail(data.getContactNum());
			}
			else if (data.getContactType().equals("Fax"))
			{
				form.setFax(data.getContactNum());
			}
		}
	}
	private void updateAddress(DepoAccountOwnerFormImpl form, FullAddress_Type[] address)
	{
		for (FullAddress_Type addr : address)
		{
			if (addr.getAddrType().equals("1"))
			{
				form.setRegistrationAddress(getAddress(addr));
				form.setRegAddressCountry(addr.getCountry());
			}
			else if (addr.getAddrType().equals("2"))
			{
				form.setResidenceAddress(getAddress(addr));
				form.setResAddressCountry(addr.getCountry());
			}
			else if (addr.getAddrType().equals("3"))
			{
				form.setForPensionAddress(getAddress(addr));
				form.setForPensionAddressCountry(addr.getCountry());
			}
			else if (addr.getAddrType().equals("4"))
			{
				form.setMailAddress(getAddress(addr));
				form.setMailAddressCountry(addr.getCountry());
			}
			else if (addr.getAddrType().equals("5"))
			{
				form.setWorkAddress(getAddress(addr));
				form.setWorkAddressCountry(addr.getCountry());
			}
		}
	}
	private AddressImpl getAddress(FullAddress_Type address)
	{
		AddressImpl addr = new AddressImpl();
		addr.setPostalCode(address.getPostalCode());
		addr.setProvince(address.getRegion());
		addr.setDistrict(address.getArea());
		addr.setCity(address.getCity());
		addr.setSettlement(address.getSettlement());
		addr.setStreet(address.getAddr1());
		addr.setHouse(address.getHouseNum());
		addr.setBuilding(address.getHouseExt());
		addr.setFlat(address.getUnitNum());
		return addr;
	}

	private DepositorAccountImpl getRUBAccount(DepoBankAccount_Type account )
	{
		DepositorAccountImpl acc = new DepositorAccountImpl();
		acc.setAccountNumber(account.getAccId());
		acc.setCardType(account.getCardType());
		acc.setCardId(account.getCardId());
		acc.setBankName(account.getBankName());
		acc.setBIC(account.getBIC());
		acc.setCorAccount(account.getCorAccId());
		acc.setCorBankName(account.getCorBankName());
		acc.setDestination(account.getDest());
		acc.setCurrency(getCurrencyByString("RUB"));
		return acc;
	}

	private DepositorAccountImpl getCurAccount(DepoBankAccountCur_Type account )
	{
		DepositorAccountImpl acc = new DepositorAccountImpl();
		acc.setAccountNumber(account.getAccId());
		acc.setCardType(account.getCardType());
		acc.setCardId(account.getCardId());
		acc.setBankName(account.getBankName());
		acc.setBIC(account.getBIC());
		acc.setCorAccount(account.getCorAccId());
		acc.setCorBankName(account.getCorBankName());
		acc.setDestination(account.getDest());
		acc.setCurrency(getCurrencyByString(account.getAcctCur()));
		return acc;
	}
}
