package com.rssl.phizicgate.esberibgate.bki;

import com.rssl.phizgate.common.credit.bki.dictionary.*;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.bki.BKIRequestType;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryRequestERIB;
import com.rssl.phizicgate.esberibgate.bki.generated.SPNameType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Построитель запроса к БКИ по данным клиента
 * @author Puzikov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

class EnquiryRequestEribBuilder
{
	private static final BkiDictionaryService dictionaryService = new BkiDictionaryService();

	/**
	 * Дата выдачи ДУЛ "по умолчанию" в формате ОКБ (BUREAU_DATE_FORMAT)
	 * Для варианта "дата выдачи не известна / не указана"
	 * ENH081992: [БКИ] Доработать запрос взаимодействия с ОКБ
	 */
	private static final String DEFAULT_DOCUMENT_ISSUE_DATE = "19000101";

	private static final String BUREAU_DATE_FORMAT  = "yyyyMMdd";   //(((19)|2\d)\d{2}((0[1-9]|1[012])(0[1-9]|1\d|2[0-8])|(0[123456789]|1[012])(29|30)|(0[13578]|1[02])31))

	EnquiryRequestERIB makeCheckRequest(Client client) throws GateException
	{
		return makeRequest(client, BKIRequestType.BKICheckCreditHistory, null);
	}

	EnquiryRequestERIB makeGetRequest(Client client, GateDocument payment) throws GateException
	{
		return makeRequest(client, BKIRequestType.BKIGetCreditHistory, payment);
	}

	private EnquiryRequestERIB makeRequest(Client client, BKIRequestType requestType, GateDocument payment) throws GateException
	{
		try
		{
			EnquiryRequestERIB request = new EnquiryRequestERIB();
			request.setRqUID(new RandomGUID().getStringValue());
			request.setOperUID(new RandomGUID().getStringValue());
			request.setRqTm(Calendar.getInstance());
			request.setRequestType(requestType.code);
			request.setSPName(SPNameType.BP_ERIB);
			request.getConsumers().add(makeConsumer(client, payment));

			return request;
		}
		catch (Exception e)
		{
			throw new GateException("Не удалось построить запрос в БКИ по клиенту", e);
		}
	}

	private EnquiryRequestERIB.Consumer makeConsumer(Client client, GateDocument payment) throws Exception
	{
		//клиент
		EnquiryRequestERIB.Consumer consumer = new EnquiryRequestERIB.Consumer();
		if (StringUtils.isEmpty(client.getFirstName()) || StringUtils.isEmpty(client.getSurName()))
			throw new GateException("Не заполнены обязательные поля: ФИО id=" + client.getInternalId());
		consumer.setName1(client.getFirstName());
		consumer.setName2(client.getPatrName());
		if  (client.getBirthDay() == null)
			throw new GateException("Не заполнены обязательные поля: ДР id=" + client.getInternalId());
		consumer.setSurname(client.getSurName());
		consumer.setSex(dictionaryService.getCodeByEsbCode(BkiSex.class, client.getSex()));
		consumer.setDateOfBirth(formatBureauDate(client.getBirthDay()));
		consumer.setPlaceOfBirth(StringUtils.defaultIfEmpty(client.getBirthPlace(), StringUtils.EMPTY));
		consumer.setNationality(dictionaryService.getCodeByEsbCode(BkiCountry.class, client.getCitizenship()));
		consumer.setDateConsentGiven(getDateConsentGiven(payment));

		//документ (сортируется как в шину)
		makeConsumerPrimaryID(consumer, client);

		//адрес
		consumer.getAddresses().add(buildAddress(client.getLegalAddress()));

		return consumer;
	}

	private void makeConsumerPrimaryID(EnquiryRequestERIB.Consumer consumer, Client client) throws Exception
	{
		List<? extends ClientDocument> clientDocuments = new ArrayList<ClientDocument>(client.getDocuments());
		if (CollectionUtils.isEmpty(clientDocuments))
			throw new GateException("У клиента нет ни одного ДУЛ. LOGIN_ID=" + client.getInternalId());
		Collections.sort(clientDocuments, new DocumentTypeComparator());
		ClientDocument document = clientDocuments.get(0);

		ClientDocumentType docType = document.getDocumentType();
		consumer.setPrimaryIDType(dictionaryService.getCodeByEsbCode(BkiPrimaryIDType.class, docType.name()));

		String docSeries = StringHelper.getEmptyIfNull(document.getDocSeries());
		String docNumber = StringHelper.getEmptyIfNull(document.getDocNumber());
		consumer.setPrimaryID(StringUtils.deleteWhitespace(docSeries + docNumber));

		if (document.getDocIssueDate() != null)
			consumer.setPrimaryIDIssueDate(formatBureauDate(document.getDocIssueDate()));
		else consumer.setPrimaryIDIssueDate(DEFAULT_DOCUMENT_ISSUE_DATE);

		if (StringHelper.isNotEmpty(document.getDocIssueBy()))
			consumer.setPrimaryIDAuthority(document.getDocIssueBy());
		else consumer.setPrimaryIDAuthority("-");
	}

	private EnquiryRequestERIB.Consumer.Address buildAddress(Address clientAddress) throws Exception
	{
		EnquiryRequestERIB.Consumer.Address address = new EnquiryRequestERIB.Consumer.Address();
		address.setAddressType(dictionaryService.getDefaultCode(BkiAddressType.class));
		if (clientAddress != null)
		{
			address.setLine1(StringUtils.defaultIfEmpty(clientAddress.getBuilding(), StringUtils.EMPTY));  //Block/Building	Номер блока	line1
			address.setLine2(StringUtils.defaultIfEmpty(clientAddress.getStreet(), StringUtils.EMPTY));    //Prospect/Lane	Название проспекта / улицы / переулка	line2
			address.setLine3(StringUtils.defaultIfEmpty(clientAddress.getCity(), StringUtils.EMPTY));      //Town/City	Населенный пункт / Поселок / Город	line3
			address.setLine4(StringUtils.defaultIfEmpty(clientAddress.getDistrict(), StringUtils.EMPTY));  //District/County/State	округ/район/штат	line4
			address.setPostcode(clientAddress.getPostalCode());
			address.setHomeTelNbr(clientAddress.getHomePhone());
		}
		else
		{
			address.setLine1(StringUtils.EMPTY);
			address.setLine2(StringUtils.EMPTY);
			address.setLine3(StringUtils.EMPTY);
			address.setLine4(StringUtils.EMPTY);
		}

		return address;
	}

	//GET - берется из подтверждения платежа
	//CHECK - не было подтверждения, отправляется текущая дата
	private String getDateConsentGiven(GateDocument payment)
	{
		Calendar date;
		if (payment == null)
		{
			date = Calendar.getInstance();
		}
		else if (payment.getClientOperationDate() == null)
		{
			date = Calendar.getInstance();
		}
		else
		{
			date = payment.getClientOperationDate();
		}
		return formatBureauDate(date);
	}

	private String formatBureauDate(Calendar date)
	{
		return new SimpleDateFormat(BUREAU_DATE_FORMAT).format(date.getTime());
	}
}
