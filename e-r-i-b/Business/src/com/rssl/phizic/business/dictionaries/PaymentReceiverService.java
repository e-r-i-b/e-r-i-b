package com.rssl.phizic.business.dictionaries;

import java.util.*;

import com.rssl.phizic.business.*;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.auth.CommonLogin;
import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Kidyaev
 * @ created 25.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class PaymentReceiverService extends MultiInstancePaymentReceiverService
{

	public PaymentReceiverBase add(PaymentReceiverBase paymentReceiver) throws BusinessException
	{
		return super.add(paymentReceiver, null);
	}

	public List<PaymentReceiverBase> findBaseListReceiver(final Long loginId) throws BusinessException
	{
		return super.findBaseListReceiver(loginId, null);
	}

	public <T> List<T> findListReceiver(Class clazz, final Long loginId) throws BusinessException
	{
		return super.findListReceiver(clazz, loginId, null);
	}

	public <T> List<T> findListReceiver(String kind, final Long loginId) throws BusinessException
	{
		return super.findListReceiver(kind, loginId, null);
	}

	public PaymentReceiverBase findReceiver(Long paymentReceiverId) throws BusinessException
	{
		return super.findReceiver(paymentReceiverId, null);
	}

	public void remove(PaymentReceiverBase paymentReceiver) throws BusinessException
	{
		super.remove(paymentReceiver, null);
	}

	public void update(PaymentReceiverBase paymentReceiver) throws BusinessException
	{
		super.update(paymentReceiver, null);    
	}

	public void removeAll ( CommonLogin login ) throws BusinessException
	{
		super.removeAll(login, null);
	}	
}
