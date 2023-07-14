package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Kidyaev
 * @ created 29.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class RemovePaymentReceiverOperation extends PersonOperationBase implements RemoveEntityOperation<PaymentReceiverBase, UserRestriction>
{
	private final static MultiInstancePaymentReceiverService service = new MultiInstancePaymentReceiverService();
	private PaymentReceiverBase paymentReceiver;

	public void initialize(Long id) throws BusinessException
	{
		this.paymentReceiver = service.findReceiver(id, getInstanceName());
	}

	@Transactional
	public void remove() throws BusinessException
	{
		service.remove(getEntity(), getInstanceName());
	}

	public PaymentReceiverBase getEntity()
	{
		return paymentReceiver;
	}
}
