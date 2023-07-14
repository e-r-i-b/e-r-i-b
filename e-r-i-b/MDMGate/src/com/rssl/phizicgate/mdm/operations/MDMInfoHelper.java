package com.rssl.phizicgate.mdm.operations;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.mdm.business.products.*;
import com.rssl.phizicgate.mdm.business.profiles.Document;
import com.rssl.phizicgate.mdm.business.profiles.Profile;
import com.rssl.phizicgate.mdm.common.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер работы с данными мдм
 */

public final class MDMInfoHelper
{
	private MDMInfoHelper() {}

	/**
	 * обновить профиль по данным из мдм
	 * @param profile обновляемый профиль
	 * @param clientInfo источник данных для обновления
	 */
	public static void synchronizeProfile(Profile profile, ClientInfo clientInfo)
	{
		profile.setLastName(clientInfo.getLastName());
		profile.setFirstName(clientInfo.getFirstName());
		profile.setMiddleName(clientInfo.getMiddleName());
		profile.setGender(clientInfo.getGender());
		profile.setBirthday(clientInfo.getBirthday());
		profile.setBirthPlace(clientInfo.getBirthPlace());
		profile.setDocuments(addOrUpdateDocuments(profile, clientInfo.getDocuments()));
		profile.setTaxId(clientInfo.getTaxId());
		profile.setResident(clientInfo.isResident());
		profile.setEmployee(clientInfo.isEmployee());
		profile.setShareholder(clientInfo.isShareholder());
		profile.setInsider(clientInfo.isInsider());
		profile.setCitizenship(clientInfo.getCitizenship());
		profile.setLiteracy(clientInfo.isLiteracy());
	}

	/**
	 * обновить информацию о продуктах
	 * @param storedProductsInfo обновляемая информация
	 * @param mdmProductsInfo источник данных для обновления
	 * @param profileId идентификатор профиля
	 * @throws GateException
	 */
	public static void synchronizeProducts(ClientProductsSynchronizeInfo storedProductsInfo, ClientProductsInfo mdmProductsInfo, Long profileId) throws GateException
	{
		updateAccounts(storedProductsInfo, mdmProductsInfo.getAccounts(), profileId);
		updateCards(storedProductsInfo, mdmProductsInfo.getCards(), profileId);
		updateDepoAccounts(storedProductsInfo, mdmProductsInfo.getDepoAccounts(), profileId);
		updateLoans(storedProductsInfo, mdmProductsInfo.getLoans(), profileId);
		updateServices(storedProductsInfo, mdmProductsInfo.getServices(), profileId);
	}

	private static Set<Document> addOrUpdateDocuments(Profile profile, Collection<DocumentInfo> mdmDocuments)
	{
		Set<Document> documents = profile.getDocuments();
		for (DocumentInfo clientDocument : mdmDocuments)
		{
			//Если один тип документа, то надо обновлять имеющийся, а не создавать новый
			Document document = addOrUpdateDocumentByType(documents, clientDocument);
			documents.add(document);
		}
		return documents;
	}

	private static Document addOrUpdateDocumentByType(Collection<Document> documents, DocumentInfo mdmDocument)
	{
		if (documents == null || documents.isEmpty())
			return getNewDocument(mdmDocument);
		String documentType = mdmDocument.getType().toString();
		for (Document document : documents)
		{
			if (documentType.equals(document.getType().toString()))
			{
				updateDocumentFromGate(document, mdmDocument);
				return document;
			}
		}
		return getNewDocument(mdmDocument);
	}

	private static Document getNewDocument(DocumentInfo mdmDocument)
	{
		Document document = new Document();
		document.setType(mdmDocument.getType());
		updateDocumentFromGate(document, mdmDocument);
		return document;
	}

	private static void updateDocumentFromGate(Document documents, DocumentInfo mdmDocument)
	{
		documents.setNumber(mdmDocument.getNumber());
		documents.setSeries(mdmDocument.getSeries());
		documents.setIssuedDate(mdmDocument.getIssuedDate());
		documents.setIssuedBy(mdmDocument.getIssuedBy());
	}

	private static void updateAccounts(ClientProductsSynchronizeInfo storedProductsInfo, List<AccountInfo> productsInfo, Long profileId) throws GateException
	{
		List<Account> saveList = storedProductsInfo.getAccountsSaveList();
		List<Account> deleteList = storedProductsInfo.getAccountsDeleteList();
		for (AccountInfo mdmProductInfo : productsInfo)
		{
			Account product = getAccount(deleteList, mdmProductInfo);
			product.setProfileId(profileId);
			product.setStartDate(mdmProductInfo.getStartDate());
			product.setClosingDate(mdmProductInfo.getClosingDate());
			saveList.add(product);
		}
	}

	private static Account getAccount(List<Account> deleteList, AccountInfo mdmProductInfo)
	{
		Iterator<Account> iterator = deleteList.iterator();
		while (iterator.hasNext())
		{
			Account product = iterator.next();
			if (product.getNumber().equals(mdmProductInfo.getNumber()))
			{
				iterator.remove();
				return product;
			}
		}

		Account product = new Account();
		product.setNumber(mdmProductInfo.getNumber());
		return product;
	}

	private static void updateCards(ClientProductsSynchronizeInfo storedProductsInfo, List<CardInfo> productsInfo, Long profileId) throws GateException
	{
		List<Card> saveList = storedProductsInfo.getCardsSaveList();
		List<Card> deleteList = storedProductsInfo.getCardsDeleteList();
		for (CardInfo mdmProductInfo : productsInfo)
		{
			Card product = getCard(deleteList, mdmProductInfo);
			product.setProfileId(profileId);
			product.setStartDate(mdmProductInfo.getStartDate());
			product.setExpiredDate(mdmProductInfo.getExpiredDate());
			saveList.add(product);
		}
	}

	private static Card getCard(List<Card> deleteList, CardInfo mdmProductInfo)
	{
		Iterator<Card> iterator = deleteList.iterator();
		while (iterator.hasNext())
		{
			Card product = iterator.next();
			if (product.getNumber().equals(mdmProductInfo.getNumber()))
			{
				iterator.remove();
				return product;
			}
		}

		Card product = new Card();
		product.setNumber(mdmProductInfo.getNumber());
		return product;
	}

	private static void updateDepoAccounts(ClientProductsSynchronizeInfo storedProductsInfo, List<DepoAccountInfo> productsInfo, Long profileId) throws GateException
	{
		List<DepoAccount> saveList = storedProductsInfo.getDepoAccountsSaveList();
		List<DepoAccount> deleteList = storedProductsInfo.getDepoAccountsDeleteList();
		for (DepoAccountInfo mdmProductInfo : productsInfo)
		{
			DepoAccount product = getDepoAccount(deleteList, mdmProductInfo);
			product.setProfileId(profileId);
			saveList.add(product);
		}
	}

	private static DepoAccount getDepoAccount(List<DepoAccount> deleteList, DepoAccountInfo mdmProductInfo)
	{
		Iterator<DepoAccount> iterator = deleteList.iterator();
		while (iterator.hasNext())
		{
			DepoAccount product = iterator.next();
			if (product.getNumber().equals(mdmProductInfo.getNumber()))
			{
				iterator.remove();
				return product;
			}
		}

		DepoAccount product = new DepoAccount();
		product.setNumber(mdmProductInfo.getNumber());
		return product;
	}

	private static void updateLoans(ClientProductsSynchronizeInfo storedProductsInfo, List<LoanInfo> productsInfo, Long profileId) throws GateException
	{
		List<Loan> saveList = storedProductsInfo.getLoansSaveList();
		List<Loan> deleteList = storedProductsInfo.getLoansDeleteList();
		for (LoanInfo mdmProductInfo : productsInfo)
		{
			Loan product = getLoan(deleteList, mdmProductInfo);
			product.setProfileId(profileId);
			product.setAdditionalNumber(mdmProductInfo.getAdditionalNumber());
			product.setLegalNumber(mdmProductInfo.getLegalNumber());
			product.setLegalName(mdmProductInfo.getLegalName());
			product.setStartDate(mdmProductInfo.getStartDate());
			product.setClosingDate(mdmProductInfo.getClosingDate());
			saveList.add(product);
		}
	}

	private static Loan getLoan(List<Loan> deleteList, LoanInfo mdmProductInfo)
	{
		Iterator<Loan> iterator = deleteList.iterator();
		while (iterator.hasNext())
		{
			Loan product = iterator.next();
			if (product.getNumber().equals(mdmProductInfo.getNumber()))
			{
				iterator.remove();
				return product;
			}
		}

		Loan product = new Loan();
		product.setNumber(mdmProductInfo.getNumber());
		return product;
	}

	private static void updateServices(ClientProductsSynchronizeInfo storedProductsInfo, List<ServiceInfo> productsInfo, Long profileId) throws GateException
	{
		List<Service> saveList = storedProductsInfo.getServicesSaveList();
		List<Service> deleteList = storedProductsInfo.getServicesDeleteList();
		for (ServiceInfo mdmProductInfo : productsInfo)
		{
			Service product = getService(deleteList, mdmProductInfo);
			product.setProfileId(profileId);
			saveList.add(product);
		}
	}

	private static Service getService(List<Service> deleteList, ServiceInfo mdmProductInfo)
	{
		Iterator<Service> iterator = deleteList.iterator();
		while (iterator.hasNext())
		{
			Service product = iterator.next();
			if (product.getType().equals(mdmProductInfo.getType()))
			{
				iterator.remove();
				return product;
			}
		}

		Service product = new Service();
		product.setType(mdmProductInfo.getType());
		return product;
	}
}
