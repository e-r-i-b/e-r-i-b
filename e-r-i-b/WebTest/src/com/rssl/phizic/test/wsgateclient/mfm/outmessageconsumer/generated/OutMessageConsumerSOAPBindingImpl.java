/**
 * OutMessageConsumerSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.test.MFMMockHibernateExecutor;
import com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.OutcomeMessage;
import org.hibernate.Session;

public class OutMessageConsumerSOAPBindingImpl implements com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumer{

	private static final String RESULT_CODE_SUCCESS = "ok";
	private static final String RESULT_CODE_ERROR = "error-system";
    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[] consumeOutMessage(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault {

	    OutMessage outMessage = parameters.getConsumeOutMessageArg().getOutMessage(0);
	    final OutcomeMessage message = new OutcomeMessage();
	    message.setGuid(outMessage.getOutMessageId());
	    message.setAddress(outMessage.getAddress());
	    message.setPriority(outMessage.getPriority());
	    message.setText(outMessage.getContent());
	    try
	    {
		    MFMMockHibernateExecutor.getInstance(true).execute(new HibernateAction<Void>()
		    {
			    public Void run(Session session) throws Exception
			    {
				    session.save(message);
				    return null;
			    }
		    });
	    }
	    catch (Exception e)
	    {
		    throw new OutMessageConsumerFault(RESULT_CODE_ERROR);
	    }
	    ConsumeOutMessageResult result = new ConsumeOutMessageResult(outMessage.getOutMessageId(), RESULT_CODE_SUCCESS);
	    ConsumeOutMessageResult[] results = {result};
	    return results;
    }

    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[] findOutMessage(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault {
        return null;
    }

}
