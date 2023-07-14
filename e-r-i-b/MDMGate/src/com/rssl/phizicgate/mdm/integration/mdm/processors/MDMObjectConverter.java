package com.rssl.phizicgate.mdm.integration.mdm.processors;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizicgate.mdm.common.*;
import com.rssl.phizicgate.mdm.integration.mdm.generated.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author akrenev
 * @ created 20.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Конвертор объектов мдм во внутреннее представление
 */

public final class MDMObjectConverter
{
	private MDMObjectConverter() {}

	/**
	 * сконвертировать объект ответа на запрос поиска инфы во внутреннее представление
	 * @param message объект ответа на запрос поиска инфы
	 * @return внутреннее представление
	 */
	public static ClientWithProductsInfo convert(CustAgreemtInqRs message)
	{
		CustAgreemtRec custAgreemtRec = message.getCustAgreemtRec();
		CustRec custRec = custAgreemtRec.getCustRec();

		ClientInfo clientInfo = getClientInfo(custRec);
		ClientProductsInfo productsInfo = getProductsInfo(custAgreemtRec.getAgreemtRecs());
		return new ClientWithProductsInfo(clientInfo, productsInfo);
	}

	/**
	 * сконвертировать объект процессор нотификации об изменении клиента/продукта во внутреннее представление
	 * @param message объект процессор нотификации об изменении клиента/продукта
	 * @return внутреннее представление
	 */
	public static ClientWithProductsInfo convert(CustAgreemtModNf message)
	{
		ClientInfo clientInfo = getClientInfo(message.getCustInfo());
		ClientProductsInfo productsInfo = getProductsInfo(message.getAgreemtInfo());
		return new ClientWithProductsInfo(clientInfo, productsInfo);
	}

	private static ClientInfo getClientInfo(CustRec custRec)
	{
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setMdmId(custRec.getCustId().getCustPermId());
		clientInfo.setInnerId(RequestHelper.getEribIntegrationId(custRec.getCustInfo().getIntegrationInfo()));
		PersonInfo personInfo = custRec.getCustInfo().getPersonInfo();

		PersonName personName = personInfo.getPersonName();
		clientInfo.setLastName(personName.getLastName());
		clientInfo.setFirstName(personName.getFirstName());
		clientInfo.setMiddleName(personName.getMiddleName());

		clientInfo.setGender(RequestHelper.parseGender(personInfo.getGender()));
		clientInfo.setBirthday(personInfo.getBirthday());
		clientInfo.setBirthPlace(personInfo.getBirthPlace());

		clientInfo.setDocuments(getDocuments(personInfo.getIdentityCards()));

		clientInfo.setTaxId(personInfo.getTaxId());
		clientInfo.setResident(RequestHelper.parseBoolean(personInfo.getResident()));
		clientInfo.setEmployee(RequestHelper.parseBoolean(personInfo.getEmployee()));
		clientInfo.setShareholder(RequestHelper.parseBoolean(personInfo.getShareholder()));
		clientInfo.setInsider(RequestHelper.parseBoolean(personInfo.getInsider()));
		clientInfo.setCitizenship(personInfo.getCitizenShip());
		clientInfo.setLiteracy(RequestHelper.parseBoolean(personInfo.getLiteracy()));

		return clientInfo;
	}

	private static ClientInfo getClientInfo(CustInfo custInfo)
	{
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setMdmId(RequestHelper.getMDMIntegrationId(custInfo.getIntegrationInfo()));
		clientInfo.setInnerId(RequestHelper.getEribIntegrationId(custInfo.getIntegrationInfo()));
		PersonInfo personInfo = custInfo.getPersonInfo();

		PersonName personName = personInfo.getPersonName();
		clientInfo.setLastName(personName.getLastName());
		clientInfo.setFirstName(personName.getFirstName());
		clientInfo.setMiddleName(personName.getMiddleName());

		clientInfo.setGender(RequestHelper.parseGender(personInfo.getGender()));
		clientInfo.setBirthday(personInfo.getBirthday());
		clientInfo.setBirthPlace(personInfo.getBirthPlace());

		clientInfo.setDocuments(getDocuments(personInfo.getIdentityCards()));

		clientInfo.setTaxId(personInfo.getTaxId());
		clientInfo.setResident(RequestHelper.parseBoolean(personInfo.getResident()));
		clientInfo.setEmployee(RequestHelper.parseBoolean(personInfo.getEmployee()));
		clientInfo.setShareholder(RequestHelper.parseBoolean(personInfo.getShareholder()));
		clientInfo.setInsider(RequestHelper.parseBoolean(personInfo.getInsider()));
		clientInfo.setCitizenship(personInfo.getCitizenShip());
		clientInfo.setLiteracy(RequestHelper.parseBoolean(personInfo.getLiteracy()));
		return clientInfo;
	}

	private static Set<DocumentInfo> getDocuments(List<IdentityCard> identityCards)
	{
		Set<DocumentInfo> documents = new HashSet<DocumentInfo>();
		for (IdentityCard identityCard : identityCards)
		{
			DocumentInfo documentInfo = new DocumentInfo();
			ClientDocumentType documentType = PassportTypeWrapper.getClientDocumentType(identityCard.getIdType());
			documentInfo.setType(documentType == null ? ClientDocumentType.OTHER : documentType);
			documentInfo.setSeries(identityCard.getIdSeries());
			documentInfo.setNumber(identityCard.getIdNum());
			documentInfo.setIssuedBy(identityCard.getIssuedBy());
			documentInfo.setIssuedDate(identityCard.getIssueDt());
			documents.add(documentInfo);
		}
		return documents;
	}

	private static ClientProductsInfo getProductsInfo(List<AgreemtRec> agreemtRecs)
	{
		ClientProductsInfo productsInfo = new ClientProductsInfo();
		productsInfo.setAccounts(new ArrayList<AccountInfo>());
		productsInfo.setCards(new ArrayList<com.rssl.phizicgate.mdm.common.CardInfo>());
		productsInfo.setDepoAccounts(new ArrayList<DepoAccountInfo>());
		productsInfo.setLoans(new ArrayList<com.rssl.phizicgate.mdm.common.LoanInfo>());
		productsInfo.setServices(new ArrayList<com.rssl.phizicgate.mdm.common.ServiceInfo>());

		for (AgreemtRec agreemtRec : agreemtRecs)
			updateProductsInfo(productsInfo, agreemtRec);

		return productsInfo;
	}

	private static ClientProductsInfo getProductsInfo(AgreemtInfo agreemtInfo)
	{
		ClientProductsInfo productsInfo = new ClientProductsInfo();
		productsInfo.setAccounts(new ArrayList<AccountInfo>());
		productsInfo.setCards(new ArrayList<com.rssl.phizicgate.mdm.common.CardInfo>());
		productsInfo.setDepoAccounts(new ArrayList<DepoAccountInfo>());
		productsInfo.setLoans(new ArrayList<com.rssl.phizicgate.mdm.common.LoanInfo>());
		productsInfo.setServices(new ArrayList<com.rssl.phizicgate.mdm.common.ServiceInfo>());

		updateProductsInfo(productsInfo, agreemtInfo);

		return productsInfo;
	}

	private static void updateProductsInfo(ClientProductsInfo productsInfo, AgreemtRec agreemtRec)
	{
		for (com.rssl.phizicgate.mdm.integration.mdm.generated.LoanInfo loanInfo : agreemtRec.getLoanInfos())
			productsInfo.getLoans().add(getLoanInfo(loanInfo));
		for (com.rssl.phizicgate.mdm.integration.mdm.generated.CardInfo cardInfo : agreemtRec.getCardInfos())
			productsInfo.getCards().add(getCardInfo(cardInfo));
		for (DepoInfo depoInfo : agreemtRec.getDepoInfos())
			productsInfo.getDepoAccounts().add(getDepoAccountInfo(depoInfo));
		for (com.rssl.phizicgate.mdm.integration.mdm.generated.ServiceInfo serviceInfo : agreemtRec.getServiceInfos())
			productsInfo.getServices().add(getServiceInfo(serviceInfo));
		for (DepInfo depInfo : agreemtRec.getDepInfos())
			productsInfo.getAccounts().add(getAccountInfo(depInfo));
	}

	private static void updateProductsInfo(ClientProductsInfo productsInfo, AgreemtInfo agreemtInfo)
	{
		for (com.rssl.phizicgate.mdm.integration.mdm.generated.LoanInfo loanInfo : agreemtInfo.getLoanInfos())
			productsInfo.getLoans().add(getLoanInfo(loanInfo));
		for (com.rssl.phizicgate.mdm.integration.mdm.generated.CardInfo cardInfo : agreemtInfo.getCardInfos())
			productsInfo.getCards().add(getCardInfo(cardInfo));
		for (DepoInfo depoInfo : agreemtInfo.getDepoInfos())
			productsInfo.getDepoAccounts().add(getDepoAccountInfo(depoInfo));
		for (com.rssl.phizicgate.mdm.integration.mdm.generated.ServiceInfo serviceInfo : agreemtInfo.getServiceInfos())
			productsInfo.getServices().add(getServiceInfo(serviceInfo));
		for (DepInfo depInfo : agreemtInfo.getDepInfos())
			productsInfo.getAccounts().add(getAccountInfo(depInfo));
	}

	private static AccountInfo getAccountInfo(DepInfo mdmInfo)
	{
		AccountInfo productInfo = new AccountInfo();
		productInfo.setNumber(mdmInfo.getAcctId());
		productInfo.setStartDate(mdmInfo.getStartDt());
		productInfo.setClosingDate(mdmInfo.getClosingDt());
		return productInfo;
	}

	private static com.rssl.phizicgate.mdm.common.ServiceInfo getServiceInfo(com.rssl.phizicgate.mdm.integration.mdm.generated.ServiceInfo mdmInfo)
	{
		com.rssl.phizicgate.mdm.common.ServiceInfo productInfo = new com.rssl.phizicgate.mdm.common.ServiceInfo();
		productInfo.setType(mdmInfo.getProdType());
		productInfo.setStartDate(mdmInfo.getStartDt());
		productInfo.setEndDate(mdmInfo.getEndDt());
		return productInfo;
	}

	private static DepoAccountInfo getDepoAccountInfo(DepoInfo mdmInfo)
	{
		DepoAccountInfo productInfo = new DepoAccountInfo();
		productInfo.setNumber(mdmInfo.getAcctId());
		return productInfo;
	}

	private static com.rssl.phizicgate.mdm.common.CardInfo getCardInfo(com.rssl.phizicgate.mdm.integration.mdm.generated.CardInfo mdmInfo)
	{
		com.rssl.phizicgate.mdm.common.CardInfo productInfo = new com.rssl.phizicgate.mdm.common.CardInfo();
		productInfo.setNumber(mdmInfo.getCardNum());
		productInfo.setStartDate(mdmInfo.getStartDt());
		productInfo.setExpiredDate(mdmInfo.getExpDt());
		return productInfo;
	}

	private static com.rssl.phizicgate.mdm.common.LoanInfo getLoanInfo(com.rssl.phizicgate.mdm.integration.mdm.generated.LoanInfo mdmInfo)
	{
		com.rssl.phizicgate.mdm.common.LoanInfo productInfo = new com.rssl.phizicgate.mdm.common.LoanInfo();
		productInfo.setNumber(mdmInfo.getAgreemtNum());
		productInfo.setAdditionalNumber(mdmInfo.getSupplAgreemtNum());
		productInfo.setLegalNumber(mdmInfo.getLegalAgreemtNum());
		productInfo.setLegalName(mdmInfo.getLegalName());
		productInfo.setStartDate(mdmInfo.getStartDt());
		productInfo.setClosingDate(mdmInfo.getClosingDt());
		return productInfo;
	}
}
