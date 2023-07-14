package com.rssl.phizic.business.mbv;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mbv.generated.*;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import java.util.Calendar;
import javax.xml.bind.JAXBException;

/**
 * User: Moshenko
 * Date: 12.09.13
 * Time: 13:49
 */
public class MBVMessageHelper
{
    private static String versoin = "0.1";

    /**
     * @param phoneNumber номер телефона
     * @return запрос GetClientByPhoneR в виде строки
     * @throws BusinessException
     */
    public String getGetClientByPhoneRqStr(String phoneNumber) throws JAXBException {
        GetClientByPhoneRq rq = new GetClientByPhoneRq();
        rq.setRqUID(new RandomGUID().getStringValue());
        rq.setRqTm(XMLDatatypeHelper.getXMLCalendar(Calendar.getInstance()));
        rq.setPhoneNo(phoneNumber);
        rq.setVersion(versoin);
        return JAXBUtils.marshalBean(rq);

    }

    /**
     * @param clientIdentity Идентификационные данные клиента
     * @return запрос ClientAccPhRq в виде строки
     * @throws JAXBException
     */
    public String getClientAccPhRqStr(MbvClientIdentity clientIdentity) throws JAXBException
    {
        ClientAccPhRq rq = new ClientAccPhRq();
        rq.setRqUID(new RandomGUID().getStringValue());
        rq.setRqTm(XMLDatatypeHelper.getXMLCalendar(Calendar.getInstance()));
        rq.setVersion(versoin);
        rq.setClient(getClientType(clientIdentity));
        return JAXBUtils.marshalBean(rq);
    }

    /**
     * @param clientIdentity Идентификационные данные клиента
     * @return запрос BeginRq в виде строки
     * @throws JAXBException
     */
    public String getBeginRqStr(MbvClientIdentity clientIdentity) throws JAXBException
    {
        BeginRq rq = new BeginRq();
        rq.setRqUID(new RandomGUID().getStringValue());
        rq.setRqTm(XMLDatatypeHelper.getXMLCalendar(Calendar.getInstance()));
        rq.setVersion(versoin);
        rq.setClient(getClientType(clientIdentity));
        return JAXBUtils.marshalBean(rq);
    }

    /**
     * @param migrationId Идентификатор миграции клиента
     * @return запрос CommitRq в виде строки
     * @throws JAXBException
     */
    public String getCommitRqStr(String migrationId) throws JAXBException
    {
        CommitRq rq = new CommitRq();
        rq.setRqUID(new RandomGUID().getStringValue());
        rq.setRqTm(XMLDatatypeHelper.getXMLCalendar(Calendar.getInstance()));
        rq.setVersion(versoin);
	    rq.setMigID(migrationId);
        return JAXBUtils.marshalBean(rq);
    }

    /**
     * @param migrationId Идентификационные данные клиента
     * @return запрос RollbackRq в виде строки
     * @throws JAXBException
     */
    public String getRollbackRqStr(String migrationId) throws JAXBException
    {
        RollbackRq rq = new RollbackRq();
        rq.setRqUID(new RandomGUID().getStringValue());
        rq.setRqTm(XMLDatatypeHelper.getXMLCalendar(Calendar.getInstance()));
        rq.setVersion(versoin);
	    rq.setMigID(migrationId);
        return JAXBUtils.marshalBean(rq);
    }

	/**
	 * @param migrationId Идентификационные данные клиента
	 * @return запрос ReverseRq в виде строки
	 * @throws JAXBException
	 */
	public String getReverseRqStr(String migrationId) throws JAXBException
	{
		ReverseRq rq = new ReverseRq();
		rq.setRqUID(new RandomGUID().getStringValue());
		rq.setRqTm(XMLDatatypeHelper.getXMLCalendar(Calendar.getInstance()));
		rq.setVersion(versoin);
		rq.setMigID(migrationId);
		return JAXBUtils.marshalBean(rq);
	}

    /**
     * @param phoneNo Номер телефона
     * @return запрос RollbackRq в виде строки
     * @throws JAXBException
     */
    public String getDiscByPhoneStr(String phoneNo) throws JAXBException
    {
        DiscByPhoneRq rq = new DiscByPhoneRq();
        rq.setRqUID(new RandomGUID().getStringValue());
        rq.setRqTm(XMLDatatypeHelper.getXMLCalendar(Calendar.getInstance()));
        rq.setVersion(versoin);
        rq.setPhoneNo(phoneNo);
        return JAXBUtils.marshalBean(rq);
    }

    private ClientType getClientType(MbvClientIdentity clientIdentity)
    {
        ClientType clientType = new ClientType();
        clientType.setBirthday(XMLDatatypeHelper.getXMLCalendar(clientIdentity.getBirthDay()));
        ClientType.PersonName personName = new ClientType.PersonName();
        personName.setFirstName(clientIdentity.getFirstName());
        personName.setLastName(clientIdentity.getSurName());
        personName.setMiddleName(clientIdentity.getPatrName());
        clientType.setPersonName(personName);
        ClientType.IdentityCard identityCard = new ClientType.IdentityCard();
        identityCard.setIdType(PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(clientIdentity.getDocType())));
        identityCard.setIdNum(clientIdentity.getDocNumber());
        identityCard.setIdSeries(clientIdentity.getDocSeries());
        clientType.setIdentityCard(identityCard);
        return clientType;
    }
}
