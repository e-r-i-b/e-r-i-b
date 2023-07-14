package com.rssl.phizic.business.persons.clients;

import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientState;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.*;

/**
 * @author Erkin
 * @ created 16.10.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация Client, про которого известны только ФИО ДУЛ ДР и ТБ.
 * Назначение: вызов GFL по ФИО ДУЛ ДР ТБ
 */
@Immutable
public class FakeClient implements Client
{
	private static final Long FAKE_ID = -1L;

	private final String surName;

	private final String firstName;

	private final String patrName;

	private final ClientDocument passport;

	private final Calendar birthDay;

	private final Office office;

	/**
	 * Конструктор по данным клиента из ЦСА
	 * @param clientCSAInfo - инфо по клиенту из ЦСА
	 */
	public FakeClient(UserInfo clientCSAInfo)
	{
		surName     = clientCSAInfo.getSurname();
		firstName   = clientCSAInfo.getFirstname();
		patrName    = clientCSAInfo.getPatrname();

		passport    = makePassportWay(clientCSAInfo.getPassport());

		birthDay    = clientCSAInfo.getBirthdate();

		office      = makeOffice(clientCSAInfo.getTb());
	}

	/**
	 * Конструктор по данным клиента из МБК
	 * @param clientMBKInfo - инфо по клиенту из МБК
	 */
	public FakeClient(com.rssl.phizic.gate.mobilebank.UserInfo clientMBKInfo)
	{
		surName     = clientMBKInfo.getSurname();
		firstName   = clientMBKInfo.getFirstname();
		patrName    = clientMBKInfo.getPatrname();

		passport    = makePassportWay(clientMBKInfo.getPassport());

		birthDay    = clientMBKInfo.getBirthdate();

		office      = makeOffice(LoginHelper.getTBFromCB_CODE(clientMBKInfo.getCbCode()));
	}

	private ClientDocumentImpl makePassportWay(String seriesAndNumber)
	{
		ClientDocumentImpl passportWay = new ClientDocumentImpl();
		passportWay.setDocumentType(ClientDocumentType.PASSPORT_WAY);
		passportWay.setDocSeries(seriesAndNumber);
		return passportWay;
	}

	private Office makeOffice(String terBank)
	{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("region", terBank);
		fields.put("branch", "0000");
		fields.put("office", "00");

		return new OfficeImpl(new CodeImpl(fields));
	}

	public String getId()
	{
		throw new UnsupportedOperationException();
	}

	public Long getInternalId()
	{
		// ClientProductsService требует ИД клиента (для формирования ExternalID)
		// Возвраем идентифкатор несуществующей записи
		return FAKE_ID;
	}

	public Long getInternalOwnerId()
	{
		// ClientProductsService требует ИД клиента (для формирования ExternalID)
		// Возвраем идентифкатор несуществующей записи
		return FAKE_ID;
	}

	public String getDisplayId()
	{
		throw new UnsupportedOperationException();
	}

	public String getShortName()
	{
		throw new UnsupportedOperationException();
	}

	public String getFullName()
	{
		throw new UnsupportedOperationException();
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public String getBirthPlace()
	{
		throw new UnsupportedOperationException();
	}

	public String getEmail()
	{
		throw new UnsupportedOperationException();
	}

	public String getHomePhone()
	{
		throw new UnsupportedOperationException();
	}

	public String getJobPhone()
	{
		throw new UnsupportedOperationException();
	}

	public String getMobilePhone()
	{
		throw new UnsupportedOperationException();
	}

	public String getMobileOperator()
	{
		throw new UnsupportedOperationException();
	}

	public String getSex()
	{
		throw new UnsupportedOperationException();
	}

	public String getINN()
	{
		throw new UnsupportedOperationException();
	}

	public boolean isResident()
	{
		throw new UnsupportedOperationException();
	}

	public Address getLegalAddress()
	{
		throw new UnsupportedOperationException();
	}

	public Address getRealAddress()
	{
		throw new UnsupportedOperationException();
	}

	public List<? extends ClientDocument> getDocuments()
	{
		// Collections.singletonList не подойдёт, т.к. он возвращает немодифицируемый список,
		// а некоторые разработчики любят посортировать прям то, что возвращает этот метод
		return Arrays.asList(passport);
	}

	public String getCitizenship()
	{
		throw new UnsupportedOperationException();
	}

	public Office getOffice()
	{
		return office;
	}

	public String getAgreementNumber()
	{
		throw new UnsupportedOperationException();
	}

	public Calendar getInsertionDate()
	{
		throw new UnsupportedOperationException();
	}

	public Calendar getCancellationDate()
	{
		throw new UnsupportedOperationException();
	}

	public ClientState getState()
	{
		throw new UnsupportedOperationException();
	}

	public boolean isUDBO()
	{
		throw new UnsupportedOperationException();
	}

	public SegmentCodeType getSegmentCodeType()
	{
		throw new UnsupportedOperationException();
	}

	public String getTarifPlanCodeType()
	{
		throw new UnsupportedOperationException();
	}

	public Calendar getTarifPlanConnectionDate()
	{
		throw new UnsupportedOperationException();
	}

	public String getManagerId()
	{
		throw new UnsupportedOperationException();
	}

	public String getManagerTB()
	{
		throw new UnsupportedOperationException();
	}

	public String getManagerOSB()
	{
		throw new UnsupportedOperationException();
	}

	public String getManagerVSP()
	{
		throw new UnsupportedOperationException();
	}

	public String getGender()
	{
		throw new UnsupportedOperationException();
	}
}
