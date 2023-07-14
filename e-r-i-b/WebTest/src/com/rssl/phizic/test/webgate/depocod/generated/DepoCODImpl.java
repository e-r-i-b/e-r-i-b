/**
 * DepoCODImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.depocod.generated;

import com.Ostermiller.util.Base64;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.rmi.RemoteException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class DepoCODImpl implements com.rssl.phizic.test.webgate.depocod.generated.DepoCODPortType
{
	public byte[] reserveForCredit(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] cancelReserveForCredit(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] payForCredit(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] accountRemainder(byte[] req) throws RemoteException
	{
		try
		{
			Document doc = XmlHelper.loadDocumentFromResource("com/rssl/phizic/test/webgate/depocod/xml/AccountRemainderResponse.xml");

//			XmlHelper.selectSingleNode(doc.getDocumentElement(), "//Error/Code").setTextContent("5");

			return convert(doc);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}

	public byte[] operationResult(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getAccountInfo(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createNewAccountNumber(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createForm190ForCredit(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] deleteForm190ForCredit(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getClientAccounts(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createNewAccount(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] paySumma(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] addSumma(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getAccHistory(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getClients(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getOperStatictics(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] payForCreditByItems(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getClientsWithAccounts(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] findAccNumByCardNum(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getSuccessCreditOperations(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getAccCreditsByDates(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2Acc(byte[] req) throws RemoteException
	{
		try
		{
			Document doc = XmlHelper.loadDocumentFromResource("com/rssl/phizic/test/webgate/depocod/xml/TransferResponse.xml");

//			XmlHelper.selectSingleNode(doc.getDocumentElement(), "//Error/Code").setTextContent("5");

			return convert(doc);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}

	public byte[] acc2Card(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Acc(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Card(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2AccClose(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2CardClose(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2AccOpen(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2AccOpen(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2AccCloseOpen(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2Communal(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Communal(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2Acc143(byte[] req) throws RemoteException
	{
		try
		{
			Document doc = XmlHelper.loadDocumentFromResource("com/rssl/phizic/test/webgate/depocod/xml/TransferResponse.xml");

//			XmlHelper.selectSingleNode(doc.getDocumentElement(), "//Error/Code").setTextContent("5");

			return convert(doc);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}

	public byte[] acc2Acc365(byte[] req) throws RemoteException
	{
		try
		{
			Document doc = XmlHelper.loadDocumentFromResource("com/rssl/phizic/test/webgate/depocod/xml/TransferResponse.xml");

//			XmlHelper.selectSingleNode(doc.getDocumentElement(), "//Error/Code").setTextContent("5");

			return convert(doc);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}

	public byte[] acc2Acc364Internal(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2Acc364External(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Acc143(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Acc365(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Acc364Internal(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Acc364External(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getEDBOContract(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getEDBOContractByCard(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getAccHistoryShortExtract(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getForms190ForClient(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getForm190Info(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getForm190PayExtract(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] chargeCommission(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] payingBack(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createForm190ForCommunal(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createForm190ForCharge(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createForm190For143(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createForm190For365(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] createForm190ForLoan(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] deleteForm190(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] redrawForms190ForEDBO(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] setSubscriber(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] setAccountState(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2Payment(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2Payment(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] card2CreateNewAccountNumber(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getPaymentInfo(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] operationFullResult(byte[] req) throws RemoteException
	{
		try
		{
			/**
			 * В ответе возвращаем полную строку с результатом, как при проведении операции.
			 * Если ошибка, то возвращается operationResultResponse (для этого переменной isDefault установить false).
			 */

			boolean isDefault = true;

			Document doc = XmlHelper.loadDocumentFromResource("com/rssl/phizic/test/webgate/depocod/xml/"+ (isDefault ? "TransferResponse.xml" : "OperationResultResponse.xml"));

//			XmlHelper.selectSingleNode(doc.getDocumentElement(), "//Error/Code").setTextContent("1");

			if (!isDefault)
			{
				Document request = XmlHelper.parse(req);
				String uuid = XmlHelper.getSimpleElementValue(request.getDocumentElement(), "UUID");
				XmlHelper.selectSingleNode(doc.getDocumentElement(), "//UUID").setTextContent(uuid);

//				XmlHelper.selectSingleNode(doc.getDocumentElement(), "//Result").setTextContent("false");
//				XmlHelper.selectSingleNode(doc.getDocumentElement(), "//ErrMessage").setTextContent("Описание ошибки");
			}
			return convert(doc);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}

	public byte[] accClose(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] accOpen(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] sendPaymentDraft(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] reverseAuth(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] loadCourses(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] accMaintenance(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2AccClose143(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2AccClose365(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getAccHistoryFullExtract(byte[] req) throws java.rmi.RemoteException
	{
		try
		{
			String request = new String(req);
			String filePath = "com/rssl/phizic/test/webgate/depocod/xml/GetAccHistoryFullExtractResponse.xml";
			if(request.contains("<WithCardUseInfo>true</WithCardUseInfo>"))
				filePath = "com/rssl/phizic/test/webgate/depocod/xml/GetAccHistoryFullExtractResponseWhithCardInfo.xml";
		    Document history = XmlHelper.loadDocumentFromResource(filePath);
			return convert(history);
		}
		catch (ParserConfigurationException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
		catch (SAXException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
		catch (IOException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TransformerException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public byte[] credit2Acc(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] acc2Credit(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	public byte[] getOperDay(byte[] req) throws RemoteException
	{
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	private byte[] convert(Document doc) throws TransformerException
	{
		return XmlHelper.convertDomToText(doc, "Windows-1251").getBytes();
	}
}
