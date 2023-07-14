package com.rssl.phizic.operations.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.ReceiverState;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author Kidyaev
 * @ created 30.11.2005
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditPaymentReceiverOperationBase extends PersonOperationBase implements EditEntityOperation<PaymentReceiverBase, UserRestriction>
{
	private final static MultiInstancePaymentReceiverService service = new MultiInstancePaymentReceiverService();
	private boolean isNew;
	private PaymentReceiverBase paymentReceiver;

	public void initialize(Long id, Long personId) throws BusinessException, BusinessLogicException
	{
		setPersonId(personId);
		paymentReceiver = service.findReceiver(id, getInstanceName());
		if (paymentReceiver== null){
			throw new BusinessException("Получатель не найден (id="+id+")");
		}
		isNew = false;
	}

	public void initializeNew(String kind, Long personId) throws BusinessException, BusinessLogicException
	{
		setPersonId(personId);
		PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
		ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptor(kind);
		try
		{
			createPaymentReceiver(receiverDescriptor.getClassName());
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка инициализации получателя.", e);
		}
		paymentReceiver.setKind(kind);
		paymentReceiver.setLogin(getPerson().getLogin());
		isNew = true;
	}

	public boolean isNew()
	{
		return isNew;
	}

	public PaymentReceiverBase getEntity()
	{
		return paymentReceiver;
	}

	/**
	 * 
	 * @param autoActive признак автоматического установления статуса "Активен". TODO убрать после перевода получателей на машину состояний;)
	 * @throws BusinessException
	 */
	public abstract void save(boolean autoActive) throws BusinessException;

	public void save(){}

	protected void saveInternal(boolean autoActive) throws BusinessException
	{
		if (autoActive){
			paymentReceiver.setState(ReceiverState.ACTIVE);
		}else{
			paymentReceiver.setState(ReceiverState.INITAL);
		}
		if (isNew)
		{
			service.add(paymentReceiver, getInstanceName());
		}
		else
		{
			service.update(paymentReceiver, getInstanceName());
		}
	}

	private void createPaymentReceiver(String className) throws ClassNotFoundException, IllegalAccessException
	{
		if (className == null || className.equals(""))
			throw new IllegalArgumentException("Не задано имя класса получателя");

		Class receiverClass = ClassHelper.loadClass(className);
		try
		{
			paymentReceiver = (PaymentReceiverBase) receiverClass.newInstance();
		}
		catch (InstantiationException ex)
		{
			throw new ClassNotFoundException("Ошибка получения класса получателя [" + receiverClass + "].", ex);
		}
	}
}
