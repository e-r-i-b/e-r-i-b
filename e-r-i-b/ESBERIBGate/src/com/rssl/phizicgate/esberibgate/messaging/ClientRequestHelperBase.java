package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.clients.ClientProductHelper;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.types.AddressType;
import com.rssl.phizicgate.esberibgate.types.ContactType;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author egorova
 * @ created 17.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ClientRequestHelperBase extends RequestHelperBase
{
	public ClientRequestHelperBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Получение списка продуктов клиента по конкретному документу
	 * @param client - клиент, по которому запрашивается информация
	 * @param document - документ клиента
	 * @param cbCode - код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию
 	 * @param cardNumber номер карты.
 	 * @param showAllNotClosed  отображать все активные.
	 * @param bankProductTypes - типы, запрашиваемых продуктов.
	 * @return готовый запрос на получение продуктов клиента
	 * @throws com.rssl.phizic.gate.exceptions.GateException, GateLogicException
	 */
	public ProductContainer createBankAcctInqRq(Client client, ClientDocument document, String cbCode, String cardNumber, Boolean showAllNotClosed,  BankProductType ... bankProductTypes) throws GateException, GateLogicException
	{
		ProductContainer productContainer = ClientProductHelper.collectClientsProducts(client.getOffice(), bankProductTypes);
		//если внешние системы по всем продуктам заблокированы, запрос gfl не формируем
		if (CollectionUtils.isEmpty(productContainer.getProducts()))
			return productContainer;

		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctInqRq_Type bankAcctInqRq = getMainBankAcctInqRq();

		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(getPersonInfo(client, document, false, false));
		bankAcctInqRq.setCustInfo(custInfo);

		bankAcctInqRq.setAcctType(getAcctType(productContainer.getProducts()));
		bankAcctInqRq.setBankInfo(getBankInfo((Office) null, cbCode, null));

		if (showAllNotClosed != null)
		{
			bankAcctInqRq.setOnlyNotClosed(showAllNotClosed);
			bankAcctInqRq.setCardAcctId(new CardAcctId_Type());
			bankAcctInqRq.getCardAcctId().setCardNum(cardNumber);
		}

		ifxRq.setBankAcctInqRq(bankAcctInqRq);

		productContainer.setIfxRq_type(ifxRq);

		return productContainer;
	}

	/**
	 * Получение списка продуктов клиента по конкретному документу
	 * @param client - клиент, по которому запрашивается информация
	 * @param document - документ клиента
	 * @param cbCode - код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию
	 * @param bankProductTypes - типы, запрашиваемых продуктов.
	 * @return готовый запрос на получение продуктов клиента
	 * @throws com.rssl.phizic.gate.exceptions.GateException, GateLogicException
	 */
	public ProductContainer createBankAcctInqRq(Client client, ClientDocument document, String cbCode, BankProductType ... bankProductTypes) throws GateException, GateLogicException
	{
		return createBankAcctInqRq(client, document, cbCode, null, null, bankProductTypes);
	}

	/**
	 * Получение либо создание RbTbBrch только для запроса GFL в том случае, когда запрашивается информация по
	 * продуктам клиента, никогда не входившего в систему и не имеющего записи CB_CODE. Нужно при добавлении
	 * счета в админке. Новый RbTbBrch состоит из кода ТБ клиента + 6 нулей. По текущей информации шина должна
	 * маршрутизировать нормально по такому значению.
	 * @param client клиент
	 * @return найденый в базе RbTbBrch, либо сгенерированный
	 */
	public String getRbTbBrch(Client client) throws GateLogicException, GateException
	{
		if (client == null)
			return null;

		Long internalOwnerId = client.getInternalOwnerId();
		String rbTbBrch = null;
		if (internalOwnerId != null)
			rbTbBrch = getRbTbBrch(internalOwnerId);
		// BUG026576: Не посылать gfl при подключении нового клиента (генерим RbTbBrch)
		if (StringHelper.isEmpty(rbTbBrch) && client.getOffice() != null && client.getOffice().getCode() != null)
			return generateRbTbBrchId(client.getOffice());
		return rbTbBrch;
	}

	/**
	 * Получение списка продуктов клиента по конкретному документу
	 * @param client - клиент, по которому запрашивается информация
	 * @param document - документ клиента
	 * @param bankProductTypes - типы, запрашиваемых продуктов.
	 * @return готовый запрос на получение продуктов клиента
	 * @throws com.rssl.phizic.gate.exceptions.GateException, GateLogicException
	 */
	public ProductContainer createBankAcctInqRq(Client client, ClientDocument document, BankProductType ... bankProductTypes) throws GateException, GateLogicException
	{
		return createBankAcctInqRq(client, document, getRbTbBrch(client), bankProductTypes);
	}

	/**
	 * Создание запроса на изменение видимости счетов в УКО
	 * @param client - клиент, инициировавший запрос
	 * @param document - дкумент клиента
	 * @param pairs - список пар<Account,ProductPermission>
	 * @return запрос
	 */
	public IFXRq_Type createBankAcctPermissModRq(Client client, ClientDocument document, List<Pair<Object, ProductPermission>> pairs) throws GateException, GateLogicException
	{
		BankAcctPermissModRq_Type bankAcctPermissModRq = new BankAcctPermissModRq_Type();
		bankAcctPermissModRq.setRqUID(generateUUID());
		bankAcctPermissModRq.setRqTm(generateRqTm());
		bankAcctPermissModRq.setOperUID(generateOUUID());
		bankAcctPermissModRq.setSPName(SPName_Type.BP_ERIB);
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(getRbTbBrch(client.getInternalOwnerId()));
		bankAcctPermissModRq.setBankInfo(bankInfo);
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(getPersonInfo(client, document, false, false));
		bankAcctPermissModRq.setCustInfo(custInfo);

		List<DepAcctRec_Type> accounts = new ArrayList<DepAcctRec_Type>();
		for (Pair<Object, ProductPermission> pair : new ArrayList<Pair<Object, ProductPermission>>(pairs))
		{
			if (pair.getFirst() instanceof Account)
			{
				Account account = (Account) pair.getFirst();
				DepAcctRec_Type dep = new DepAcctRec_Type();

				DepAcctId_Type depAcctId = new DepAcctId_Type();
				EntityCompositeId compositeId = EntityIdHelper.getAccountCompositeId(account);
				depAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
				depAcctId.setAcctId(account.getNumber());
				depAcctId.setBankInfo(new BankInfo_Type(null, null, null, null, compositeId.getRbBrchId()));
				dep.setDepAcctId(depAcctId);
				dep.setBankAcctPermiss(getBankAcctPermissions(pair.getSecond()));

				accounts.add(dep);
				//удаляем, чтобы повторно не отсылать запрос по данному счету для других документов
				//см. ClientProductsServiceImpl#updateProductPermission
				pairs.remove(pair);
			}
			else
			{
				throw new GateException("Некорректный тип объекта. Должен быть Account");
			}
		}
		bankAcctPermissModRq.setDepAcctRec(accounts.toArray(new DepAcctRec_Type[accounts.size()]));
		
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setBankAcctPermissModRq(bankAcctPermissModRq);
		return ifxRq;
	}

	/**
	 * Создание запроса на изменение ограничения по печати и использованию одноразовых паролей по карте
	 * @param client - клиент, инициировавший запрос
	 * @param pairs - список пар<Card,OTPRestriction>
	 * @return запрос
	 */
	public IFXRq_Type createOTPRestrictionModRq(Client client, List<Pair<Object, OTPRestriction>> pairs) throws GateException, GateLogicException
	{
		OTPRestrictionModRq_Type otpRestrictionModRq = new OTPRestrictionModRq_Type();
		otpRestrictionModRq.setRqUID(generateUUID());
		otpRestrictionModRq.setRqTm(generateRqTm());
		otpRestrictionModRq.setOperUID(generateOUUID());
		otpRestrictionModRq.setSPName(SPName_Type.BP_ERIB);

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(getRbTbBrch(client.getInternalOwnerId()));
		otpRestrictionModRq.setBankInfo(bankInfo);

		List<CardAcctRec_Type> cards = new ArrayList<CardAcctRec_Type>();
		for (Pair<Object, OTPRestriction> pair : new ArrayList<Pair<Object, OTPRestriction>>(pairs))
		{
			if (pair.getFirst() instanceof Card)
			{
				Card card = (Card) pair.getFirst();
				CardAcctRec_Type cardAcc = new CardAcctRec_Type();

			    CardAcctId_Type cardAccId = new CardAcctId_Type();
				cardAccId.setCardNum(card.getNumber());
				cardAcc.setCardAcctId(cardAccId);
				OTPRestriction otpRestriction = pair.getSecond();
				OTPRestriction_Type restriction = new OTPRestriction_Type();
				restriction.setOTPGet(otpRestriction.isOTPGet());
				restriction.setOTPUse(otpRestriction.isOTPUse());
				cardAcc.setOTPRestriction(restriction);
				cards.add(cardAcc);
			}
			else
			{
				throw new GateException("Некорректный тип объекта. Должен быть Card");
			}
		}
		otpRestrictionModRq.setCardAcctRec(cards.toArray(new CardAcctRec_Type[cards.size()]));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setOTPRestrictionModRq(otpRestrictionModRq);
		return ifxRq;
	}

	private BankAcctPermiss_Type[] getBankAcctPermissions(ProductPermission permission)
	{
		BankAcctPermiss_Type[] bankAcctPermissList = new BankAcctPermiss_Type[2];

		bankAcctPermissList[0] =
				new BankAcctPermiss_Type(PermissType_Type.View, !permission.showInSbol(), SPName_Type.BP_ERIB);
		bankAcctPermissList[1] = 
				new BankAcctPermiss_Type(PermissType_Type.View, !permission.showInES(), SPName_Type.BP_ES);

		return bankAcctPermissList;
	}

	/**
	 * Заполнение информации о клиенте
	 * @param client - клиент
	 * @param document - документ клиента
	 * @param full - false - необходимо заполнить только основными данными, true - заполняем всем, чем можем.
	 * @param isForCEDBO - признак, определяющий, для какого интерфейса заполняется информаци (CEDBO или другой)
	 * @return Заполненного клиента
	 */
	protected PersonInfo_Type getPersonInfo(Client client, ClientDocument document, boolean full, boolean isForCEDBO)
	{
		//сначала заполняем короткую информацию о клиенте, которая нужно во многих сообщениях
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDate(client.getBirthDay()));

		PersonName_Type personName = new PersonName_Type();
		personName.setLastName(client.getSurName());
		personName.setFirstName(client.getFirstName());
		if (!StringHelper.isEmpty(client.getPatrName()))
			personName.setMiddleName(client.getPatrName());
		personInfo.setPersonName(personName);

		personInfo.setIdentityCard(getDocument(document, isForCEDBO));

		//Если по клиенту нужна только короткая информация, то её и возращаем, елси нет, продолжаем заполнение
		if (!full)
			return personInfo;

		personInfo.setBirthPlace(StringHelper.getEmptyIfNull(client.getBirthPlace()));
		personInfo.setTaxId(StringHelper.getEmptyIfNull(client.getINN()));
		personInfo.setCitizenship(client.getCitizenship());
		if (!StringHelper.isEmpty(client.getSex()))
			personInfo.setGender(client.getSex());
		personInfo.setResident(client.isResident());

		personInfo.setContactInfo(getContactInfo(client));
		
		return personInfo;
	}

	/*
	Заполнение контактной информации. Если не заполнена контактная информация у клиента, возвращаем null.
	*/
	private ContactInfo_Type getContactInfo(Client client)
	{
		Map<ContactType, String> contacts = new HashMap<ContactType, String>();
		addContact(contacts, ContactType.HOME_PHONE, client.getHomePhone());
		addContact(contacts, ContactType.JOB_PHONE, client.getJobPhone());
		addContact(contacts, ContactType.MOBILE_PHONE, client.getMobilePhone());
		addContact(contacts, ContactType.EMAIL, client.getEmail());

		Map<AddressType, Address> addresses = new HashMap<AddressType, Address>();
	    addAddress(addresses, AddressType.REGISTRATION, client.getLegalAddress());
		addAddress(addresses, AddressType.RESIDENCE, client.getRealAddress());


		//Поле не обязательное, поэтому. если ничего нет (ни контактов, ни адресов), то и его не заполняем.
		if (contacts.isEmpty() && addresses.isEmpty())
			return null;
		ContactInfo_Type contactInfo = new ContactInfo_Type();
		if (!contacts.isEmpty())
		{
			ContactData_Type[] contactDataArray = new ContactData_Type[contacts.size()];
			int i = 0;
			for (ContactType type : contacts.keySet())
			{
				ContactData_Type contactData = new ContactData_Type();
				contactData.setContactType(type.getDescription());
				contactData.setContactNum(contacts.get(type));
				if (type == ContactType.MOBILE_PHONE)
					contactData.setPhoneOperName(client.getMobileOperator());
				contactDataArray[i] = contactData;
				i++;
			}
			contactInfo.setContactData(contactDataArray);
		}
		if (!addresses.isEmpty())
		{
			FullAddress_Type[] addressesArray = new FullAddress_Type[addresses.size()];
			int i = 0;
			for (AddressType type : addresses.keySet())
			{
				addressesArray[i] = getAddress(type, addresses.get(type));
				i++;
			}
			contactInfo.setPostAddr(addressesArray);
		}		
		return contactInfo;
	}

	private void addAddress(Map<AddressType, Address> addresses, AddressType type, Address value)
	{
		if (value == null || isEmpty(value))
			return;
		addresses.put(type, value);
	}

	private boolean isEmpty(Address address)
	{
		return StringHelper.isEmpty(address.getUnparseableAddress()) && StringHelper.isEmpty(address.getPostalCode())
				&& StringHelper.isEmpty(address.getProvince()) && StringHelper.isEmpty(address.getDistrict())
				&& StringHelper.isEmpty(address.getCity()) && StringHelper.isEmpty(address.getSettlement())
				&& StringHelper.isEmpty(address.getStreet()) && StringHelper.isEmpty(address.getHouse())
				&& StringHelper.isEmpty(address.getBuilding()) && StringHelper.isEmpty(address.getFlat());
	}

	private void addContact(Map<ContactType, String> contacts, ContactType type, String value)
	{
		if (StringHelper.isEmpty(value))
			return;
		contacts.put(type, value);
	}

	private FullAddress_Type getAddress(AddressType type, Address clientAddress)
	{
		FullAddress_Type address = new FullAddress_Type();
		address.setAddrType(type.getType());
		//Ненормализованный адрес
		if (!StringHelper.isEmpty(clientAddress.getUnparseableAddress()))
			address.setAddr3(clientAddress.getUnparseableAddress());
		//Почтовый индекс
		if (!StringHelper.isEmpty(clientAddress.getPostalCode()))
			address.setPostalCode(clientAddress.getPostalCode());
		//Регион/Область
		if (!StringHelper.isEmpty(clientAddress.getProvince()))
			address.setRegion(clientAddress.getProvince());
		//Район
		if (!StringHelper.isEmpty(clientAddress.getDistrict()))
			address.setArea(clientAddress.getDistrict());
		//Город
		if (!StringHelper.isEmpty(clientAddress.getCity()))
			address.setCity(clientAddress.getCity());
		//Населённый пункт
		if (!StringHelper.isEmpty(clientAddress.getSettlement()))
			address.setSettlement(clientAddress.getSettlement());
		//Улица
		if (!StringHelper.isEmpty(clientAddress.getStreet()))
			address.setAddr1(clientAddress.getStreet());
		//Дом(владение)
		if (!StringHelper.isEmpty(clientAddress.getHouse()))
			address.setHouseNum(clientAddress.getHouse());
		//корпус
		if (!StringHelper.isEmpty(clientAddress.getBuilding()))
			address.setHouseExt(clientAddress.getBuilding());
		//квартира/офис
		if (!StringHelper.isEmpty(clientAddress.getFlat()))
			address.setUnitNum(clientAddress.getFlat());
		return address;
	}

}
