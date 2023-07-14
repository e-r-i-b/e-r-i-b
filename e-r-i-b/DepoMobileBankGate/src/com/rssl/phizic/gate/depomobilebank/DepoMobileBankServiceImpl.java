package com.rssl.phizic.gate.depomobilebank;

import com.rssl.phizic.business.mbv.MBVMessageHelper;
import com.rssl.phizic.business.mbv.generated.*;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigratorServiceLocator;
import com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigratorSoapBindingStub;
import com.rssl.phizic.gate.depomobilebank.ws.generated.SendMessage;
import com.rssl.phizic.gate.depomobilebank.ws.generated.SendMessageResponse;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.mbv.ClentPhone;
import com.rssl.phizic.gate.mbv.ClientAccPh;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankConfig;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankService;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.commons.lang.StringUtils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;

/**
 * User: Moshenko
 * Date: 03.09.13
 * Time: 15:57
 */
public class DepoMobileBankServiceImpl extends AbstractService implements DepoMobileBankService
{
    private MBVMessageHelper messageHelper = new MBVMessageHelper();

	public DepoMobileBankServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public List<MbvClientIdentity> checkPhoneOwn(String phoneNumber) throws GateException
	{
		if (StringUtils.isBlank(phoneNumber))
			throw new IllegalArgumentException("Аргумент 'phone' не может быть пустым");

		phoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber);

		try
		{
            SendMessage rq = new SendMessage();
            rq.setMessage(messageHelper.getGetClientByPhoneRqStr(phoneNumber));
            SendMessageResponse rs = getWebServiceStub().sendMessage(rq);
            GetClientByPhoneRs clientByPhoneRs = JAXBUtils.unmarshalBean(GetClientByPhoneRs.class, rs.getSendMessageReturn());

            List<MbvClientIdentity> result = new LinkedList<MbvClientIdentity>();
            List<ClientType> clientType = clientByPhoneRs.getClient();

            for (ClientType client : clientType)
            {
                MbvClientIdentity clientIdentity = new MbvClientIdentity();
                clientIdentity.setBirthDay(XMLDatatypeHelper.parseDateTime(client.getBirthday().toString()));
                ClientType.PersonName personName = client.getPersonName();
                clientIdentity.setFirstName(personName.getFirstName());
                clientIdentity.setSurName(personName.getLastName());
                clientIdentity.setPatrName(personName.getMiddleName());
                ClientType.IdentityCard identityCard = client.getIdentityCard();
                clientIdentity.setDocType(PersonDocumentType.valueOf(PassportTypeWrapper.getClientDocumentType(identityCard.getIdType())));
                clientIdentity.setDocNumber(identityCard.getIdNum());
                clientIdentity.setDocSeries(identityCard.getIdSeries());
                result.add(clientIdentity);
            }
            return result;

		}
		catch (RemoteException e)
		{
            throw new GateException(e);
		}
        catch (JAXBException e)
        {
            throw new GateException(e);
        }
	}

    public ClientAccPh getClientAccPh(MbvClientIdentity clientIdentity) throws GateException
    {
	    PersonDocumentType docType = clientIdentity.getDocType();
	    //не производим поиск для паспорта  WAY, его в MBV  нет (BUG063066)
	    if (docType == PersonDocumentType.PASSPORT_WAY)
		    throw new IllegalArgumentException("Недопустимо получать список вкладов и телефонов (ClientAccPhRq) по паспорту way ");

        try
        {
            SendMessage rq = new SendMessage();
            rq.setMessage(messageHelper.getClientAccPhRqStr(clientIdentity));
            SendMessageResponse rs = getWebServiceStub().sendMessage(rq);
            ClientAccPhRs clientAccPhRs =  JAXBUtils.unmarshalBean(ClientAccPhRs.class, rs.getSendMessageReturn());
	        checkAndThrowResultCode(clientAccPhRs.getResult(), clientAccPhRs.getResultMessage());
	        ClientAccPh mbvClientAccPh = new MBVClientAccPh();
            mbvClientAccPh.setAccList(clientAccPhRs.getAcctId());
            List<PhoneType> xmlPhoneList =  clientAccPhRs.getPhone();
            List<ClentPhone> phoneList = new ArrayList<ClentPhone>();
            for (PhoneType phoneType :xmlPhoneList)
            {
                MBVClentPhone phone = new MBVClentPhone();
                phone.setPhoneNumber(phoneType.getPhoneNo());
                XMLGregorianCalendar lastUpdTime = phoneType.getLastUsagedateTime();
                if (lastUpdTime != null)
                    phone.setLastUsageDateTime(XMLDatatypeHelper.parseDateTime(lastUpdTime.toString()));
                phoneList.add(phone);
            }
            mbvClientAccPh.setPhoneList(phoneList);
            return mbvClientAccPh;
        }
        catch (JAXBException e)
        {
            throw new GateException(e);
        }
        catch (RemoteException e)
        {
            throw new GateException(e);
        }
    }

	private void checkAndThrowResultCode(String rc, String description) throws GateException
	{
		MBVResultEnum returnCode = MBVResultEnum.fromValue(rc);
		switch (returnCode)
		{
			case SUCCESS:
			case PARAMETER_NOT_FIND:
			case OBJECT_READY:
				return;
			case NO_CONNECTION:
			case PROGRAM_ERROR:
			default:
				throw new GateException(String.format("Не удалось вызвать метод МБВ. Код ошибки: %s. Описание ошибки: %s", rc, StringUtils.defaultIfEmpty(description, "-")));
		}
	}

    public UUID beginMigration(MbvClientIdentity clientIdentity) throws GateException
    {
        try
        {
            SendMessage rq = new SendMessage();
            rq.setMessage(messageHelper.getBeginRqStr(clientIdentity));
            SendMessageResponse rs = getWebServiceStub().sendMessage(rq);
            BeginRs beginRs =  JAXBUtils.unmarshalBean(BeginRs.class, rs.getSendMessageReturn());

			if (MBVResultEnum.SUCCESS.toValue().equals(beginRs.getResult()))
	        {
		        return UUID.fromValue(beginRs.getMigID());
	        }
			else if (MBVResultEnum.PARAMETER_NOT_FIND.toValue().equals(beginRs.getResult()))
			{
				//не найдено подключение МБВ
				return null;
			}
	        else
	        {

	            throw new GateException("Запуск процедуры миграции клиента из МБВ не удался. Код результата:" +
	                                    beginRs.getResult()+". Дополнительная информация о результате операции: " +
	                                    beginRs.getResultMessage());
	        }
        }
        catch (JAXBException e)
        {
            throw new GateException(e);
        }
        catch (RemoteException e)
        {
            throw new GateException(e);
        }
    }

    public void commitMigration(UUID migrationId) throws GateException
    {
	    if (migrationId == null)
		    throw new IllegalArgumentException("Необходимо указать id миграции");
        try
        {
            SendMessage rq = new SendMessage();
            rq.setMessage(messageHelper.getCommitRqStr(migrationId.toString()));
            SendMessageResponse rs = getWebServiceStub().sendMessage(rq);
            CommitRs commitRs =  JAXBUtils.unmarshalBean(CommitRs.class, rs.getSendMessageReturn());
            if (!StringHelper.equals(commitRs.getResult(), MBVResultEnum.SUCCESS.toValue()))
            {

                throw new GateException("Запуск процедуры авершения транзакции миграции клиента из МБВ не удался. Код результата:" +
                        commitRs.getResult()+". Дополнительная информация о результате операции: " +
                        commitRs.getResultMessage());
            }
        }
        catch (JAXBException e)
        {
            throw new GateException(e);
        }
        catch (RemoteException e)
        {
            throw new GateException(e);
        }
    }



    public void rollbackMigration(UUID migrationId) throws GateException
    {
	    if (migrationId == null)
		    throw new IllegalArgumentException("Необходимо указать id миграции");
        try
        {
            SendMessage rq = new SendMessage();
            rq.setMessage(messageHelper.getRollbackRqStr(migrationId.toString()));
            SendMessageResponse rs = getWebServiceStub().sendMessage(rq);
            RollbackRs rollbackRs =  JAXBUtils.unmarshalBean(RollbackRs.class, rs.getSendMessageReturn());
            if (!StringHelper.equals(rollbackRs.getResult(), MBVResultEnum.SUCCESS.toValue()))
            {

                throw new GateException("Запуск процедуры отмены старта транзакции миграции  из МБВ не удался. Код результата:" +
                        rollbackRs.getResult()+". Дополнительная информация о результате операции: " +
                        rollbackRs.getResultMessage());
            }
        }
        catch (JAXBException e)
        {
            throw new GateException(e);
        }
        catch (RemoteException e)
        {
            throw new GateException(e);
        }
    }

	public void reverseMigration(UUID migrationId) throws GateException
	{
		if (migrationId == null)
			throw new IllegalArgumentException("Необходимо указать id миграции");
		try
		{
			SendMessage rq = new SendMessage();
			rq.setMessage(messageHelper.getReverseRqStr(migrationId.toString()));
			SendMessageResponse rs = getWebServiceStub().sendMessage(rq);
			ReverseRs reverseRs =  JAXBUtils.unmarshalBean(ReverseRs.class, rs.getSendMessageReturn());
			if (!StringHelper.equals(reverseRs.getResult(), MBVResultEnum.SUCCESS.toValue()))
			{

				throw new GateException("Запуск процедуры отката миграции  из МБВ не удался. Код результата:" +
						reverseRs.getResult()+". Дополнительная информация о результате операции: " +
						reverseRs.getResultMessage());
			}
		}
		catch (JAXBException e)
		{
			throw new GateException(e);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

    public void discByPhone(String phoneNumber) throws GateException
    {
        if (StringUtils.isBlank(phoneNumber))
            throw new IllegalArgumentException("Аргумент 'phone' не может быть пустым");

        phoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber);
        try
        {
            SendMessage rq = new SendMessage();
            rq.setMessage(messageHelper.getDiscByPhoneStr(phoneNumber));
            SendMessageResponse rs = getWebServiceStub().sendMessage(rq);
            DiscByPhoneRs rollbackRs =  JAXBUtils.unmarshalBean(DiscByPhoneRs.class, rs.getSendMessageReturn());
            if (!StringHelper.equals(rollbackRs.getResult(), MBVResultEnum.SUCCESS.toValue()))
            {

                throw new GateException("Запуск процедуры отмены старта транзакции миграции  из МБВ не удался. Код результата:" +
                        rollbackRs.getResult()+". Дополнительная информация о результате операции: " +
                        rollbackRs.getResultMessage());
            }
        }
        catch (JAXBException e)
        {
            throw new GateException(e);
        }
        catch (RemoteException e)
        {
            throw new GateException(e);
        }

    }

    private MBVMigratorSoapBindingStub getWebServiceStub()
	{
		String url = null;
		try
		{
			DepoMobileBankConfig config = ConfigFactory.getConfig(DepoMobileBankConfig.class);
            MBVMigratorServiceLocator locator = new MBVMigratorServiceLocator();
			url = config.getEndpointUrl();
			locator.setMBVMigratorEndpointAddress(url);

			return (MBVMigratorSoapBindingStub)locator.getMBVMigrator();
		}
		catch (ServiceException e)
		{
			throw new ConfigurationException("Не удалось подключиться к веб-сервису " + url, e);
		}
	}
}
