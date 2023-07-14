package com.rssl.phizic.test.webgate.depomobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mbv.generated.*;
import com.rssl.phizic.gate.depomobilebank.MBVResultEnum;
import com.rssl.phizic.test.mbvmock.*;
import com.rssl.phizic.test.webgate.depomobilebank.generated.*;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import java.rmi.RemoteException;
import java.util.*;
import javax.xml.bind.JAXBException;

/**
 * User: Moshenko
 * Date: 05.09.13
 * Time: 13:16
 * Класс обертка для ответов MBVMigratorSoapBindingImpl
 */
public class MBVMigratorSoapWrapper
{
	private static MbvMockService service = new MbvMockService();

    private static String versoin = "0.1";

    /**
     *
     * @param rqMessageStr
     * @return
     * @throws java.rmi.RemoteException
     */
	public SendMessageResponse getClientByPhone(String rqMessageStr) throws java.rmi.RemoteException
	{
        try
        {
            SendMessageResponse rsMessage = new SendMessageResponse();
            GetClientByPhoneRs rs = new GetClientByPhoneRs();

            GetClientByPhoneRq rq = JAXBUtils.unmarshalBean(GetClientByPhoneRq.class, rqMessageStr);
            rs.setRqTm(rq.getRqTm());
            rs.setRqUID(rq.getRqUID());
            rs.setVersion(versoin);

            String phoneNo = rq.getPhoneNo();
            GetClientByPhone identityByPhone = service.getGetClientByPhone(phoneNo);
            if (identityByPhone != null)
            {
                rs.setResult(identityByPhone.getRetCode());
                rs.setResultMessage(identityByPhone.getResultMessage());
                Set<GetClientByPhoneClientIdintity> identitiSet = identityByPhone.getClientIdentity();
                if (!identitiSet.isEmpty())
                {
                    for (GetClientByPhoneClientIdintity clientIdintity : identitiSet)
                    {
                        ClientType clientType = new ClientType();
                        clientType.setBirthday(XMLDatatypeHelper.getXMLCalendar(clientIdintity.getBirthday()));
                        ClientType.PersonName personName = new ClientType.PersonName();
                        personName.setFirstName(clientIdintity.getFirstName());
                        personName.setLastName(clientIdintity.getLastName());
                        personName.setMiddleName(clientIdintity.getMiddleName());
                        clientType.setPersonName(personName);
                        ClientType.IdentityCard idCard = new ClientType.IdentityCard();
                        idCard.setIdNum(clientIdintity.getIdNum());
                        idCard.setIdSeries(clientIdintity.getIdSeries());
                        idCard.setIdType(clientIdintity.getIdType());
                        clientType.setIdentityCard(idCard);
	                    rs.getClient().add(clientType);
                    }
                }
            }
            else
                rs.setResult(MBVResultEnum.PROGRAM_ERROR.toValue());

            rsMessage.setSendMessageReturn(JAXBUtils.marshalBean(rs));
            return rsMessage;
        }
        catch (JAXBException e)
        {
            throw new RemoteException(e.getMessage());
        }
        catch (BusinessException e)
        {
            throw new RemoteException(e.getMessage());
        }
	}

    /**
     *
     * @param rqMessageStr
     * @return
     * @throws java.rmi.RemoteException
     */
    public SendMessageResponse clientAccPh(String rqMessageStr) throws java.rmi.RemoteException
    {
        SendMessageResponse rsMessage = new SendMessageResponse();
        try
        {
            ClientAccPhRq rq = JAXBUtils.unmarshalBean(ClientAccPhRq.class, rqMessageStr);
            ClientAccPhRs rs = new ClientAccPhRs();
            rs.setRqTm(rq.getRqTm());
            rs.setRqUID(rq.getRqUID());
            rs.setVersion(versoin);

            ClientType client = rq.getClient();
            ClientType.PersonName pName = client.getPersonName();
            ClientType.IdentityCard doc = client.getIdentityCard();

            ClientAccPh clientAccPh =  service.getClientAccPhRq(pName.getFirstName(), pName.getLastName(), pName.getMiddleName()
                    , XMLDatatypeHelper.parseDateTime(client.getBirthday().toString()), doc.getIdType(), doc.getIdSeries(), doc.getIdNum());
            if (clientAccPh != null)
            {
                rs.setResult(clientAccPh.getRetCode());
                rs.setResultMessage(clientAccPh.getResultMessage());
                Set<ClinetAcc> accSet = clientAccPh.getAccNumbers();
                for (ClinetAcc acc:accSet)
                {
	                rs.getAcctId().add(acc.getAccNumber());
                }

                Set<ClinetPhone> phonesSet = clientAccPh.getPhones();
                for (ClinetPhone phone:phonesSet)
                {
                    PhoneType phoneType = new PhoneType();
                    phoneType.setPhoneNo(phone.getPhoneNumber());
	                Calendar lastUsageDateTime = phone.getLastUsageDateTime();
	                if (lastUsageDateTime != null)
						phoneType.setLastUsagedateTime(XMLDatatypeHelper.getXMLCalendar(lastUsageDateTime));
	                rs.getPhone().add(phoneType);
                }
            }
            else
            {
	            rs.setResult(MBVResultEnum.PARAMETER_NOT_FIND.toValue());
	            rs.setResultMessage("Действующих договоров не найдено");
            }

            rsMessage.setSendMessageReturn(JAXBUtils.marshalBean(rs));
            return rsMessage;
        }
        catch (BusinessException e)
        {
            throw new RemoteException(e.getMessage());
        } catch (JAXBException e)
        {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * @param rqMessageStr
     * @return
     * @throws java.rmi.RemoteException
     */
    public SendMessageResponse beginMigration(String rqMessageStr) throws java.rmi.RemoteException
    {
        SendMessageResponse rsMessage = new SendMessageResponse();
        try
        {
            BeginRq rq = JAXBUtils.unmarshalBean(BeginRq.class, rqMessageStr);
            BeginRs rs = new BeginRs();
            rs.setRqTm(rq.getRqTm());
            rs.setRqUID(rq.getRqUID());
            rs.setVersion(versoin);

            ClientType client = rq.getClient();
            ClientType.PersonName pName = client.getPersonName();
            ClientType.IdentityCard doc = client.getIdentityCard();

            BeginMigration beginMigration = service.getBeginMigration(pName.getFirstName(), pName.getLastName(), pName.getMiddleName()
                    , XMLDatatypeHelper.parseDateTime(client.getBirthday().toString()), doc.getIdType(), doc.getIdSeries(), doc.getIdNum());
            if (beginMigration != null)
            {
                rs.setResult(beginMigration.getRetCode());
                rs.setResultMessage(beginMigration.getResultMessage());
	            rs.setMigID(beginMigration.getMigrationId());
            }
            else
                rs.setResult(MBVResultEnum.PROGRAM_ERROR.toValue());

            rsMessage.setSendMessageReturn(JAXBUtils.marshalBean(rs));
            return rsMessage;
        }
        catch (BusinessException e)
        {
            throw new RemoteException(e.getMessage());
        } catch (JAXBException e)
        {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * @param rqMessageStr
     * @return
     * @throws java.rmi.RemoteException
     */
    public SendMessageResponse commitMigration(String rqMessageStr) throws java.rmi.RemoteException
    {
        SendMessageResponse rsMessage = new SendMessageResponse();
        try
        {
            CommitRq rq = JAXBUtils.unmarshalBean(CommitRq.class, rqMessageStr);
            CommitRs rs = new CommitRs();
            rs.setRqTm(rq.getRqTm());
            rs.setRqUID(rq.getRqUID());
            rs.setVersion(versoin);

            CommitMigration commitMigration =  service.getCommitMigration(rq.getMigID());
            if (commitMigration != null)
            {
                rs.setResult(commitMigration.getRetCode());
                rs.setResultMessage(commitMigration.getResultMessage());
                rs.setDisconnectTime(XMLDatatypeHelper.getXMLCalendar(commitMigration.getDisconnectTime()));
            }
            else
                rs.setResult(MBVResultEnum.PROGRAM_ERROR.toValue());

            rsMessage.setSendMessageReturn(JAXBUtils.marshalBean(rs));
            return rsMessage;
        }
        catch (BusinessException e)
        {
            throw new RemoteException(e.getMessage());
        } catch (JAXBException e)
        {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * @param rqMessageStr
     * @return
     * @throws java.rmi.RemoteException
     */
    public SendMessageResponse rollbackMigration(String rqMessageStr) throws java.rmi.RemoteException
    {
        SendMessageResponse rsMessage = new SendMessageResponse();
        try
        {
            RollbackRq rq = JAXBUtils.unmarshalBean(RollbackRq.class, rqMessageStr);
            RollbackRs rs = new RollbackRs();
            rs.setRqTm(rq.getRqTm());
            rs.setRqUID(rq.getRqUID());
            rs.setVersion(versoin);

            RollbackMigration rollbackMigration =  service.getRollbackMigration(rq.getMigID());
            if (rollbackMigration != null)
            {
                rs.setResult(rollbackMigration.getRetCode());
                rs.setResultMessage(rollbackMigration.getResultMessage());
            }
            else
                rs.setResult(MBVResultEnum.PROGRAM_ERROR.toValue());

            rsMessage.setSendMessageReturn(JAXBUtils.marshalBean(rs));
            return rsMessage;
        }
        catch (BusinessException e)
        {
            throw new RemoteException(e.getMessage());
        } catch (JAXBException e)
        {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * @param rqMessageStr
     * @return
     * @throws java.rmi.RemoteException
     */
	public SendMessageResponse reverseMigration(String rqMessageStr) throws java.rmi.RemoteException
	{
		SendMessageResponse rsMessage = new SendMessageResponse();
		try
		{
			ReverseRq rq = JAXBUtils.unmarshalBean(ReverseRq.class, rqMessageStr);
			ReverseRs rs = new ReverseRs();
			rs.setRqTm(rq.getRqTm());
			rs.setRqUID(rq.getRqUID());
			rs.setVersion(versoin);

			ReverseMigration rollbackMigration =  service.getReverseMigration(rq.getMigID());
			if (rollbackMigration != null)
			{
				rs.setResult(rollbackMigration.getRetCode());
				rs.setResultMessage(rollbackMigration.getResultMessage());
			}
			else
				rs.setResult(MBVResultEnum.PROGRAM_ERROR.toValue());

			rsMessage.setSendMessageReturn(JAXBUtils.marshalBean(rs));
			return rsMessage;
		}
		catch (BusinessException e)
		{
			throw new RemoteException(e.getMessage());
		} catch (JAXBException e)
		{
			throw new RemoteException(e.getMessage());
		}
	}

    /**
     * @param rqMessageStr
     * @return
     * @throws java.rmi.RemoteException
     */
    public SendMessageResponse discByPhone(String rqMessageStr) throws java.rmi.RemoteException
    {
        SendMessageResponse rsMessage = new SendMessageResponse();
        try
        {
            DiscByPhoneRq rq = JAXBUtils.unmarshalBean(DiscByPhoneRq.class, rqMessageStr);
            DiscByPhoneRs rs = new DiscByPhoneRs();
            rs.setRqTm(rq.getRqTm());
            rs.setRqUID(rq.getRqUID());
            rs.setVersion(versoin);

            String phoneNo = rq.getPhoneNo();

            DiscByPhone discByPhone =  service.getDiscByPhone(phoneNo);
            if (discByPhone != null)
            {
                rs.setResult(discByPhone.getRetCode());
                rs.setResultMessage(discByPhone.getResultMessage());
            }
            else
                rs.setResult(MBVResultEnum.PROGRAM_ERROR.toValue());

            rsMessage.setSendMessageReturn(JAXBUtils.marshalBean(rs));
            return rsMessage;
        }
        catch (BusinessException e)
        {
            throw new RemoteException(e.getMessage());
        } catch (JAXBException e)
        {
            throw new RemoteException(e.getMessage());
        }
    }
}
