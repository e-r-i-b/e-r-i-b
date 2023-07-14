package com.rssl.phizicgate.rsV55.senders;

import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.documents.SystemWithdrawDocument;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizicgate.rsV55.senders.AbstractDocumentSender;
import com.rssl.phizicgate.rsV55.senders.AbstractPaymentSender;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Krenev
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class RapidaDocumentManager extends AbstractDocumentSender
{
	private static final String PARAMETER_RETAIL_SENDER_NAME = "retail-sender";
	private static final String PARAMETER_RAPIDA_SENDER_NAME = "rapida-sender";
	private static final String PARAMETER_RETAIL_OPERATION_TYPE_NAME = "retail-operation-type";
	private static final String PARAMETR_PACK_NUMBER = "pack-number";
	private static final String PARAMETER_RECEIVER_ACCOUNT = "receiver-account";
	private static final String PARAMETER_SUBOPERATION_TYPE = "retail-suboperation-type";
	private static final String PARAMETER_RECEIVER_CORRACCOUNT = "receiver-corraccount";
	private static final String PARAMETER_RECEIVER_BIK = "receiver-bic";
	private static final String PARAMETER_RECEIVER_NAME = "receiver-name";
	private static final String PARAMETER_RECEIVER_BANK_NAME = "receiver-bank-name";
	private DocumentSender retailSender;
	private DocumentSender rapidaSender;

	public void setParameters(Map<String, ?> params)
	{
		super.setParameters(params);
		initialize();
	}

	private void initialize()
	{
		try
		{
			rapidaSender = loadSender(PARAMETER_RAPIDA_SENDER_NAME);
			retailSender = loadSender(PARAMETER_RETAIL_SENDER_NAME);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		Map<String, String> retailParameters = new HashMap<String, String>();

		String retailOperationType = (String) getParameter(PARAMETER_RETAIL_OPERATION_TYPE_NAME);
		retailParameters.put(AbstractPaymentSender.PARAMETER_OPERATION_TYPE_NAME, retailOperationType);
		retailParameters.put(PARAMETER_RECEIVER_ACCOUNT,(String)getParameter(PARAMETER_RECEIVER_ACCOUNT));
		retailParameters.put(PARAMETER_SUBOPERATION_TYPE,(String)getParameter(PARAMETER_SUBOPERATION_TYPE));
		retailParameters.put(PARAMETER_RECEIVER_CORRACCOUNT,(String)getParameter(PARAMETER_RECEIVER_CORRACCOUNT));
		retailParameters.put(PARAMETER_RECEIVER_BIK,(String)getParameter(PARAMETER_RECEIVER_BIK));
		retailParameters.put(PARAMETER_RECEIVER_NAME,(String)getParameter(PARAMETER_RECEIVER_NAME));
		retailParameters.put(PARAMETER_RECEIVER_BANK_NAME,(String)getParameter(PARAMETER_RECEIVER_BANK_NAME));
		retailParameters.put(PARAMETR_PACK_NUMBER,(String)getParameter(PARAMETR_PACK_NUMBER));
		retailSender.setParameters(retailParameters);
	}

	private DocumentSender loadSender(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		String senderClassName = (String) getParameter(name);
		Class<DocumentSender> senderClass = ClassHelper.loadClass(senderClassName);
		return senderClass.newInstance();
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		rapidaSender.prepare(document);
		retailSender.send(document);
		try
		{
			rapidaSender.send(document);
		}
		catch (GateException e)
		{
			retailSender.rollback(createRevokePayment(document));
			throw e;
		}
		catch (GateLogicException e)
		{
			retailSender.rollback(createRevokePayment(document));
			throw e;
		}
		catch (Exception e)
		{
			retailSender.rollback(createRevokePayment(document));
			throw new GateException(e);
		}
	}

	private WithdrawDocument createRevokePayment(GateDocument document)
	{
		return new SystemWithdrawDocument(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//Nothing TO DO
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("не реализовано");
	}
}
