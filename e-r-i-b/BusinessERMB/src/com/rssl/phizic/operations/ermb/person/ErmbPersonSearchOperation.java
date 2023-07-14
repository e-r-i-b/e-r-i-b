package com.rssl.phizic.operations.ermb.person;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.ProfileNotFoundException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.ActiveCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankProductTypeWrapper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.operations.person.search.multinode.SearchPersonMultiNodeOperation;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * User: Moshenko
 * Date: 27.05.2013
 * Time: 13:09:05
 * Поиск клиента для реализации возможности создавать профиль ЕРМБ
 */

public class ErmbPersonSearchOperation extends SearchPersonMultiNodeOperation
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final String NO_FOUND_TXT = "Клиент с таким номером телефона не найден. Возможно, он подключен к Мобильному банку по картам.";
	private static final Map<Class, BankProductType> RESOURCES = getResources();

	private static final String CLIENT = "Клиент ";
	private static final String SPACE = " ";
	private static final String NOT_FOUND = " в системе не найден.";
	private static final String NOT_UDBO = " не заключил договор УДБО. Подключение услуги Мобильный банк невозможно.";
	private static final String NOT_ACTIVE_CARD = " не имеет активных банковских карт. Подключение услуги Мобильный банк невозможно.";
	private static final ActiveCardFilter ACTIVE_CARD_FILTER = new ActiveCardFilter();

	private static Map<Class, BankProductType> getResources()
	{
		Map<Class, BankProductType> resources = new HashMap<Class, BankProductType>();
		resources.put(Account.class,    BankProductTypeWrapper.getBankProductType(Account.class));
		resources.put(Card.class,       BankProductTypeWrapper.getBankProductType(Card.class));
		resources.put(IMAccount.class,  BankProductTypeWrapper.getBankProductType(Loan.class));
		return resources;
	}

	private String getErrorMessage(String suffix)
	{
		Map<String, Object> identityData = getIdentityData();
		String surName   = (String) identityData.get("surName");
		String firstName = (String) identityData.get("firstName");
		String patrName  = (String) identityData.get("patrName");
		return CLIENT + surName + SPACE + firstName + (StringHelper.isEmpty(patrName)? "": (SPACE + patrName)) + suffix;
	}

	@Override
	protected String getNotFoundPersonMessage()
	{
		return getErrorMessage(NOT_FOUND);
	}

	@Override
	protected String getRestrictionMessage()
	{
		return getErrorMessage(NOT_UDBO);
	}

	private String getNotActiveCardMessage()
	{
		return getErrorMessage(NOT_ACTIVE_CARD);
	}

	@Override
	protected List<Class> getResourcesClasses(ActivePerson person) throws BusinessException
	{
		List<Class> resources = new ArrayList<Class>();

		for (Map.Entry<Class, BankProductType> entry : RESOURCES.entrySet())
		{
			try
			{
				if (ExternalSystemHelper.isActive(getExternalSystem(person, entry.getValue())))
					resources.add(entry.getKey());
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
		}

		return resources;
	}


	@Override
	protected void updatePerson(ActivePerson person, Map<String, Object> identityData, AuthData authData) throws BusinessException, BusinessLogicException
	{
		//подтягиваем продукты клиента
		loadProducts(person);

		//проверяем есть ли активные карты
		List<CardLink> links = externalResourceService.getLinks(person.getLogin(), CardLink.class);
		if (links != null)
		{
			ArrayList<CardLink> cardLinks = new ArrayList<CardLink>(links);
			CollectionUtils.filter(cardLinks, ACTIVE_CARD_FILTER);
			if (cardLinks.size() > 0)
				return;
		}
		throw new BusinessLogicException(getNotActiveCardMessage());
	}

	@Override
	protected void updateAdditionalData(Map<String, Object> identityData) throws BusinessException, BusinessLogicException
	{
		switch (getUserVisitingMode())
		{
			//поиск по ДУЛ
			case EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT: super.updateAdditionalData(identityData);	return;
			//поиск по банковской карте
			case EMPLOYEE_INPUT_BY_PHONE: getIdentityData(identityData); return;
		}

		throw new BusinessException("Некорректный тип входа");
	}

	private void getIdentityData(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		try
		{
			String phone = (String) data.get("phone");
			Document document = CSABackRequestHelper.sendGetUserInfoByPhoneRq(PhoneNumberFormat.P2P_NEW.translate(phone), false);
			UserInfo userInfo = CSABackResponseSerializer.getUserInfo(document);
			if (userInfo == null)
				throw new BusinessLogicException(NO_FOUND_TXT);

			data.put("surName",         userInfo.getSurname());
			data.put("firstName",       userInfo.getFirstname());
			data.put("patrName",        userInfo.getPatrname());
			data.put("birthDay",        userInfo.getBirthdate().getTime());
			data.put("documentNumber",  userInfo.getPassport());
			data.put("documentType",    ClientDocumentType.PASSPORT_WAY.name());
			data.put("region",          userInfo.getTb());
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		catch (ProfileNotFoundException e)
		{
			throw new BusinessLogicException(NO_FOUND_TXT, e);
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}
}
