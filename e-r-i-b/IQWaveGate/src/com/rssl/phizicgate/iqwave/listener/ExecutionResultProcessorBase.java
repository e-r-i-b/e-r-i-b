package com.rssl.phizicgate.iqwave.listener;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringHandler;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.messaging.MessageInfoContainer;
import com.rssl.phizgate.common.payments.PaymentCompositeId;
import com.rssl.phizicgate.manager.services.persistent.documents.UpdateDocumentServiceImpl;
import com.rssl.phizicgate.wsgateclient.services.types.GateDocumentImpl;
import org.w3c.dom.Document;

import java.util.HashMap;
import javax.xml.transform.TransformerException;

/**
 * ������� ����� ��� ������������ ��������� .
 * @author niculichev
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class ExecutionResultProcessorBase implements MesssageProcessor
{
	/**
	 * ���������� ���������
	 * @param infoContainer ��������� ��� ���������
	 * @return ��������� ���������
	 */
	public String process(MessageInfoContainer infoContainer) throws GateException, GateLogicException
	{
		try
		{
			if (infoContainer.getErrorCode() == null)
			{
				processExecute(infoContainer);
				MonitoringHandler.getInstance().processOk(infoContainer.getMessageTag());
			}
			else
			{
				processReject(infoContainer);
				MonitoringHandler.getInstance().processResponce(infoContainer.getMessageTag(),infoContainer.getErrorCode());
			}

			return XmlHelper.convertDomToText(MessageHelper.fillHeader(Constants.OFFLINE_TICKET, infoContainer, Constants.NO_ERROR, null));
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		finally
		{
			UpdateDocumentServiceImpl.resetPersister();
		}
	}

	/**
	 * ��������� ��������� � �������
	 * @param infoContainer ��������� ���������� � ����������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected void processReject(MessageInfoContainer infoContainer) throws GateLogicException, GateException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		SynchronizableDocument payment = getPayment(updateDocumentService, infoContainer);
		updateLogThreadContext(payment);
		updateDocumentService.update(payment, MessageHelper.error2State(infoContainer));
	}

	/**
	 * ��������� ��������� ��� ������
	 * @param infoContainer ��������� ���������� � ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected void processExecute(MessageInfoContainer infoContainer) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		SynchronizableDocument payment = getPayment(updateDocumentService, infoContainer);
		updateLogThreadContext(payment);
		fillPaymentData(payment, infoContainer.getBodyContent());
		//�������� ������ �������.
		updateDocumentService.update(payment);
		//�������� ���������
		updateDocumentService.update(payment, new DocumentCommand(DocumentEvent.EXECUTE, new HashMap<String, Object>()));
	}

	private SynchronizableDocument getPayment(UpdateDocumentService updateDocumentService, MessageInfoContainer infoContainer) throws GateException, GateLogicException
	{
		PaymentCompositeId compositeId = new PaymentCompositeId(infoContainer.getParentMessageId(), XMLDatatypeHelper.formatDate(infoContainer.getParentMessageDate()));
		SynchronizableDocument synchronizableDocument = updateDocumentService.find(compositeId.toString());

		if (synchronizableDocument == null)
		{
			//����� �������� �� ������ ������� ���������������
			synchronizableDocument = updateDocumentService.find(infoContainer.getParentMessageId());
		}

		if (synchronizableDocument == null)
		{
			throw new GateException("�� ������ ����������� �������� " + compositeId.toString());
		}

		return synchronizableDocument;
	}

	/**
	 * �������� ������� � ���������� �������, ������� ������ � ���������
	 * @param payment  ������
	 * @param bodyContent ���������� � ���������
	 * @throws GateException
	 */
	public void fillPaymentData(SynchronizableDocument payment, Document bodyContent) throws GateException
	{
		//nothing
	}

	/**
	 * ��������� �������� � ����� �����������.
	 * @param payment ������
	 * @throws GateException
	 */
	protected void updateLogThreadContext(SynchronizableDocument payment)  throws GateException
	{

		LogThreadContext.setLoginId(payment.getInternalOwnerId());
		if (payment instanceof GateDocumentImpl)
		{
			GateDocumentImpl gateDocument= (GateDocumentImpl)payment;
			String fio = gateDocument.getPayerName();
			if (!StringHelper.isEmpty(fio))
			{
				String[] fioArr = fio.split(" ");
				switch(fioArr.length)
				{
					case 1:
						LogThreadContext.setSurName(fioArr[0]);
						break;
					case 2:
						LogThreadContext.setSurName(fioArr[0]);
						LogThreadContext.setFirstName(fioArr[1]);
						break;
					case 3:
						LogThreadContext.setSurName(fioArr[0]);;
						LogThreadContext.setFirstName(fioArr[1]);
						LogThreadContext.setPatrName(fioArr[2]);;
					default:
					// do nothing
				}
			}
			OperationContextUtil.synchronizeObjectAndOperationContext(gateDocument);
		}
	}
}
