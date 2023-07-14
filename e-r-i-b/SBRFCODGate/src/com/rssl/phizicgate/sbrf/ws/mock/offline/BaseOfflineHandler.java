package com.rssl.phizicgate.sbrf.ws.mock.offline;

import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Random;

/**
 * @author Omeliyanchuk
 * @ created 07.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class BaseOfflineHandler implements OfflineMockHandler
{
	private static String RESULT_STRING="acknowledge_t";
	private static final String ERROR_OFFLINE_XML_PATH = "com/rssl/phizicgate/sbrf/ws/mock/xml/error_offline.xml";
	protected static final String CONFIRMATION_OFFLINE_XML_PATH = "com/rssl/phizicgate/sbrf/ws/mock/xml/confirmation_offline.xml";
	protected static final String MESSAGE_ID = "/message/messageId";
	protected static final String MESSAGE_PARENT_ID = "/message/parentId/messageId";
	protected static final String MESSAGE_PARENT_DATE = "/message/parentId/messageDate";
	private static final String ERROR_CODE = "/message/error_offline_a/code";
	private static final String ERROR_CLIENT = "ERROR_CLIENT";
	private static final String DEBIT_ROW_SUM = "/message/confirmation_offline_a/debitRow/sum";
	private static final String CREDIT_ROW_SUM = "/message/confirmation_offline_a/creditRow/sum";

	/**
	 * xpath к сумме
	 * @return
	 */
	protected String calculateDebitSum(SynchronizableDocument document, String sum) throws GateException
	{
		return sum;
	}

	protected String calculateCreditSum(SynchronizableDocument document, String sum) throws GateException
	{
		return sum;
	}

	public Document handle(Object object) throws GateException
	{
		if( object instanceof GateDocument)
		{
			try
			{
				SynchronizableDocument document = (SynchronizableDocument)object;
				if(document==null)
				{
					return null;
				}

				String parentMessageid = document.getExternalId();

				Random rnd = new Random();
				if(rnd.nextInt(5)!=4)
				{
					//todo криво, надо выносить в отдельный класс и делать более интелектуальной.
					if(AbstractTransfer.class.isAssignableFrom(document.getType()))
					{
						AbstractTransfer transfer = (AbstractTransfer)document;
						String summIn = null;
						String summOut = null;
						if(transfer.getChargeOffAmount()!=null)
						{
							summIn = transfer.getChargeOffAmount().getDecimal().toString();
						}
						if(transfer.getDestinationAmount()!=null)
						{
							summOut = transfer.getDestinationAmount().getDecimal().toString();
						}

						if(summIn ==null) summIn="10.00";
						if(summOut ==null) summOut="12.00";
						return createSuccessAnswer(parentMessageid,calculateDebitSum(transfer,summIn), calculateCreditSum(transfer,summOut));
					}
					else if(LossPassbookApplicationClaim.class.isAssignableFrom(document.getType()))
					{
						return createSuccessAnswer(parentMessageid,"", "");
					}
					else throw new GateException("Неизвестный тип платежа с id:"+document.getId());
				}
				else
				{
					return createFaultAnswer(parentMessageid);
				}
			}
			catch(Exception ex)
			{
				throw new GateException("Ошибка при подготовке вызова offline сообщения",ex);
			}
		}
		else return null;
	}

	private Document createFaultAnswer(String parentMessageId) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource(ERROR_OFFLINE_XML_PATH);
		Element root = response.getDocumentElement();
		Element messageId = XmlHelper.selectSingleNode(root, MESSAGE_ID);
		messageId.setTextContent(new RandomGUID().getStringValue());

		String[] parentMessage = parentMessageId.split("@", 2);
		Element parentId = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_ID);
		parentId.setTextContent(parentMessage[0]);
		Element parentDate = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_DATE);
		parentDate.setTextContent(parentMessage[1]);

		Random rnd = new Random();
		if(rnd.nextInt(4)!=3)
		{
			Element code = XmlHelper.selectSingleNode(root, ERROR_CODE);
			code.setTextContent(ERROR_CLIENT);
		}

		return response;
	}

	protected Document createSuccessAnswer(String parentMessageId, String debitSumReal,String creditSumReal) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource(CONFIRMATION_OFFLINE_XML_PATH);
		Element root = response.getDocumentElement();
		Element messageId = XmlHelper.selectSingleNode(root, MESSAGE_ID);
		messageId.setTextContent(new RandomGUID().getStringValue());
		Element debitSum = XmlHelper.selectSingleNode(root, DEBIT_ROW_SUM);
		debitSum.setTextContent(debitSumReal);
		Element creditSum = XmlHelper.selectSingleNode(root, CREDIT_ROW_SUM);
		creditSum.setTextContent(creditSumReal);

		String[] parentMessage = parentMessageId.split("@", 2);
		Element parentId = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_ID);
		parentId.setTextContent(parentMessage[0]);
		Element parentDate = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_DATE);
		parentDate.setTextContent(parentMessage[1]);

		return response;
	}
}
