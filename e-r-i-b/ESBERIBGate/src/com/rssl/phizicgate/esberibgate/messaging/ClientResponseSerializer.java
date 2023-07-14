package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl;
import com.rssl.phizic.TBRenameDictionary;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.gate.clients.SimpleClientIdGenerator;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.types.AddressImpl;
import com.rssl.phizicgate.esberibgate.types.ClientImpl;
import com.rssl.phizicgate.esberibgate.types.ContactType;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;

/**
 @author Pankin
 @ created 29.09.2010
 @ $Author$
 @ $Revision$
 */
public class ClientResponseSerializer extends BaseResponseSerializer
{
	private static final Set<Long> OFFLINE_SYSTEM_STATUSES;

	static
	{
		OFFLINE_SYSTEM_STATUSES = new HashSet<Long>(3);
		OFFLINE_SYSTEM_STATUSES.add(-100L);
		OFFLINE_SYSTEM_STATUSES.add(-105L);
		OFFLINE_SYSTEM_STATUSES.add(-400L);
	}
	/*
	Карта, устанавливающая соответствие между типом контакта и полем ClientBase, в которое нужно записать значение.
	 */
	private static Map<ContactType, String> getPropertyMap()
	{
		Map<ContactType, String> map = new HashMap<ContactType, String>();
		map.put(ContactType.EMAIL, "email");
		map.put(ContactType.HOME_PHONE, "homePhone");
		map.put(ContactType.JOB_PHONE, "jobPhone");
		return map;
	}

	/**
	 * Разбор ответа шины, заполнение информации по клиенту
	 * @param response - ответ
	 * @param rbTbBranchId
	 * @param isCardRequest - запрос делался по номеру карты(true) или по ФИО+ДУЛ(false)
	 * @return клиент
	 * @throws GateLogicException
	 */
	public Client fillClient(IFXRs_Type response, String rbTbBranchId, boolean isCardRequest) throws GateLogicException, GateException
	{
		CustInqRs_Type custInqRs = response.getCustInqRs();
		Status_Type status = custInqRs.getStatus();

		// если в ЦОД не найдена информация о клиенте (либо данные ЦСА и CEDBO не совпадают, либо клиент
		// входит по кредитной карте)
		if (status.getStatusCode() == CLIENT_NOT_FOUND)
		{
			return null;
		}

		// Если в ЦОД клиент найден по ДУЛ, но какие-то данные не совпадают (ошибка -424), возвращаем null в
		// случае запроса по ФИО+ДУЛ или пустого не УДБО клиента в случае запроса по номеру карты
		if (status.getStatusCode() == DIFFERENT_CLIENT_DATA)
		{
			return isCardRequest ? new ClientImpl() : null;
		}

		//Если из шины пришёл статус, что шина не поддерживается,
		// значит можем вернуть оттуда только информацию по картам, значит возращаем пустого - карточного клиента
		if (status.getStatusCode() == ESB_REALLY_NOT_SUPPORTED)
		{
			log.error("Запрашивался CEDBO на шине по клиенту, подразделение которого не переведено на Базовый продукт.");
			return new ClientImpl();
		}

		// Если получаем ошибку на запрос CEDBO, считаем клиента карточным
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			if (OFFLINE_SYSTEM_STATUSES.contains(status.getStatusCode()))
			{
				ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
				Office clientOffice = new ExtendedOfficeGateImpl();
				clientOffice.setCode(new ExtendedCodeGateImpl(rbTbBranchId.substring(0, 2), null, null));
				List<? extends ExternalSystem> externalSystems = externalSystemGateService.findByProduct(clientOffice, BankProductType.Deposit);
				throw new OfflineExternalSystemException(externalSystems);
			}
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". " + status.getStatusDesc());
			return new ClientImpl();
		}
		
		//Если вернулся только признак УДБО false, возвращаем пустого клиента.
		if(custInqRs.getCustRec() == null && custInqRs.getSPDefField() != null)
			return new ClientImpl();

		CustInfo_Type custInfo = custInqRs.getCustRec().getCustInfo();
		ClientImpl client = fillClient(custInfo);

		client.setOffice(getOffice(custInfo.getBankInfo()));

		PersonInfo_Type personInfo = custInfo.getPersonInfo();
		if (personInfo.getContactInfo() != null)
			addContactInfo(client, personInfo.getContactInfo());

		//Информация о заключении клиентом УДБО
		addSPDefField(client, custInqRs);

		//Информация о тарифном плане и сегменте клиента
		addTarifPlanInfo(client,custInqRs.getCustRec().getTarifPlanInfo());

		return client;
	}

	private void addContactInfo(ClientImpl client, ContactInfo_Type contactInfo)
	{
		FullAddress_Type postAddress[] = contactInfo.getPostAddr();
		//В рамках этого запроса по спецификации возвращается только один адрес - адрес регистрации
		if (postAddress != null && postAddress.length != 0)
		{
			AddressImpl address = new AddressImpl(postAddress[0].getAddr3());
			client.setLegalAddress(address);
		}

		ContactData_Type contactData[] = contactInfo.getContactData();
		if (contactData == null || contactData.length == 0)
			return;

		for (ContactData_Type contact : contactData)
		{
			try
			{
				ContactType contactType = ContactType.fromDescription(contact.getContactType());
				if (contactType == ContactType.MOBILE_PHONE)
				{
					client.setMobilePhone(PhoneNumberUtil.getNormalizePhoneNumberString(contact.getContactNum()));
					client.setMobileOperator(contact.getPhoneOperName());
					if (client.getMobilePhone().length() < 10)
						log.warn("Внимание! Мобильный телефон клиента содержит менее 10 цифр. Клиент - " +
								client.getFullName());
				}
				String propertyName = getPropertyMap().get(contactType);
				if (propertyName == null)
					continue;
				BeanUtils.setProperty(client, propertyName, contact.getContactNum());
			}
			catch (Exception e)
			{
				log.error("Ошибка при записи контакта типа \"" + contact.getContactType() + "\"", e);
			}
		}
	}

	private String getFullName(PersonName_Type personName)
	{
		StringBuffer buf = new StringBuffer();

		if (personName.getLastName() != null)
		{
			buf.append(personName.getLastName());
		}

		if (personName.getFirstName() != null)
		{
			if (buf.length() > 0)
				buf.append(" ");
			buf.append(personName.getFirstName());
		}

		if (personName.getMiddleName() != null)
		{
			if (buf.length() > 0)
				buf.append(" ");
			buf.append(personName.getMiddleName());
		}

		return buf.toString();
	}
	//Сделано по запросу BUG026519: Заменить код 02 на 44 
	// Еще одни гвозди, деваться опять некуда. Согласовано с Хоботовым Андреем
	protected Office getOffice(BankInfo_Type bankInfo) throws GateLogicException, GateException
	{
		if (bankInfo == null)
			return null;

		//CHG034816: делаем обратное преобразование от подчинённого(02) к основному(44).
		String codeRegion = bankInfo.getRegionId();
		String tbCode = ConfigFactory.getConfig(TBRenameDictionary.class).getOldTbBySynonym(codeRegion);
		if (!StringHelper.isEmpty(tbCode))
			bankInfo.setRegionId(tbCode.substring(0,2));
		
		return super.getOffice(bankInfo);
	}

	/**
	 * Информация о тарифном плане и сегменте
	 * @param client - клиент
	 * @param tarifPlanInfo - информация по тарифному плану и сегменту клиента
	 */
	private void addTarifPlanInfo(ClientImpl client, TarifPlanInfo_Type tarifPlanInfo)
	{
		if (tarifPlanInfo != null)//Информация возвращается только для клиентов ЦА и МБ
		{
			if (StringHelper.isNotEmpty(tarifPlanInfo.getCode()))
				client.setTarifPlanCodeType(tarifPlanInfo.getCode());
			if (StringHelper.isNotEmpty(tarifPlanInfo.getSegmentCode()))
				client.setSegmentCodeType(SegmentCodeType.fromValue(Long.valueOf(tarifPlanInfo.getSegmentCode())));
			client.setTarifPlanConnectionDate(parseCalendar(tarifPlanInfo.getConnectionDate()));
			client.setManagerId(tarifPlanInfo.getManagerId());
			client.setManagerTB(tarifPlanInfo.getManagerTb());
			client.setManagerOSB(tarifPlanInfo.getManagerOsb());
			client.setManagerVSP(tarifPlanInfo.getManagerFilial());
		}
	}

	/**
	 * Информация о заключенном клиентом УДБО
	 * @param client - клиент
	 * @param custInqRs
	 */
	private void addSPDefField(ClientImpl client, CustInqRs_Type custInqRs)
	{
		CustRec_Type custRec = custInqRs.getCustRec();
		if (custInqRs != null && custRec != null)
		{
			SPDefField_Type spDefField = custInqRs.getSPDefField();

			//Дата расторжения договора УДБО, если нет, то не проставится
			if (spDefField.getState() != null && spDefField.getState().getValue().equals(State_Type.CLOSED_CONTRACT.getValue()))
				client.setCancellationDate(parseCalendar(spDefField.getFieldDt1()));
			client.setUDBO(spDefField.getFieldValue());

			if (client.isUDBO())
			{
				CustInfo_Type custInfo = custRec.getCustInfo();
				PersonInfo_Type personInfo = custInfo.getPersonInfo();
				PersonName_Type personName = personInfo.getPersonName();

				client.setId(SimpleClientIdGenerator.generateClientId(personName.getLastName(), personName.getFirstName(),
						personName.getMiddleName(), personInfo.getBirthday(), personInfo.getIdentityCard().getIdNum(),
						custInfo.getBankInfo().getRbBrchId()));
				client.setAgreementNumber(spDefField.getFieldNum());
				client.setInsertionDate(parseCalendar(spDefField.getFieldDt()));
			}
		}
	}

	/**
	 * Сериализация запроса CAD.
	 *
	 * @param custAddRs ответ CAD.
	 * @return строка ответа.
	 */
	public String getCustAddrRs(CustAddRs_Type custAddRs)
	{
		if (custAddRs.getStatus().getStatusCode() == 0)
			return null;

		return custAddRs.getStatus().getStatusCode() + "!" + custAddRs.getStatus().getStatusDesc() + "!" + custAddRs.getStatus().getServerStatusDesc();
	}
}
