package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.utils.ClassHelper;
import org.hibernate.criterion.DetachedCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 10.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstancePaymentReceiverService
{
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final Object DICTIONARY_LOCKER = new Object();
	private static volatile PaymentPersonalReceiversDictionary dictionary;

	/**
	 * создание нового получателя
	 * @param paymentReceiver объект получатель
	 * @return добавленный получатель
	 * @throws com.rssl.phizic.business.BusinessException ошибка при работе с бд
	 */
	public PaymentReceiverBase add ( PaymentReceiverBase paymentReceiver, String instanceName ) throws BusinessException
	{
		return simpleService.add(paymentReceiver, instanceName);
	}

	/**
	 * Обновление данных по получателю.
	 * @param paymentReceiver получатель
	 * @throws BusinessException ошибка при работе с БД
	 */
	public void update ( PaymentReceiverBase paymentReceiver, String instanceName ) throws BusinessException
	{
		simpleService.update(paymentReceiver, instanceName);
	}

	/**
	 * удаление получателя
	 * @param paymentReceiver
	 * @throws BusinessException ошибка при работе с БД
	 */
	public void remove ( PaymentReceiverBase paymentReceiver, String instanceName ) throws BusinessException
	{
		simpleService.remove(paymentReceiver, instanceName);
	}

	/**
	 * Удалить всех получателей
	 * @param login пользователь
	 * @param instanceName имя экземпляра Бд
	 * @throws BusinessException
	 */
	public void removeAll ( CommonLogin login, String instanceName ) throws BusinessException
	{
		List<PaymentReceiverBase> receiversList = getAll(login, instanceName);
		for (PaymentReceiverBase paymentReceiverBase : receiversList)
		{
			simpleService.remove(paymentReceiverBase, instanceName);
		}
	}	

	/**
	 * ищем получателя по идентификатору
	 * @param paymentReceiverId идентификатор получателя
	 * @return
	 * @throws BusinessException
	 */
	public PaymentReceiverBase findReceiver(Long paymentReceiverId, String instanceName) throws BusinessException
	{
			return simpleService.findById(PaymentReceiverBase.class, paymentReceiverId, instanceName);
	}

	/**
	 * получение  всех получателей
	 * @param loginId
	 * @return
	 * @throws BusinessException
	 */
	public List<PaymentReceiverBase> findBaseListReceiver(final Long loginId, String instanceName) throws BusinessException
	{
		return simpleService.find(DetachedCriteria.forClass(PaymentReceiverBase.class).add(org.hibernate.criterion.Expression.eq("login.id", loginId)), instanceName);
	}

	/**
	 * получение списка получателей клиента по kind
	 * @param kind вид получателей
	 * @param loginId  идентификатор логина клиента
	 * @return  список получателей
	 * @throws BusinessException ошибка при работе с БД
	 */
	public <T> List<T>  findListReceiver(String kind, final Long loginId, String instanceName) throws BusinessException
	{
		ReceiverDescriptor receiver = getDictionary().getReceiverDescriptor(kind);
		try
		{
			Class T = ClassHelper.loadClass(receiver.getClassName());
			return simpleService.find(DetachedCriteria.forClass(PaymentReceiverBase.class).add(org.hibernate.criterion.Expression.eq("login.id", loginId)).add(org.hibernate.criterion.Expression.eq("kind", kind)), instanceName);
		}
		catch (ClassNotFoundException e)
		{
			throw new BusinessException("Не найден класс получателя [kind = " + kind + "]."+e);
		}
	}

	private <T> PaymentPersonalReceiversDictionary getDictionary() throws BusinessException
	{
		PaymentPersonalReceiversDictionary localDictionary = dictionary;
		if(localDictionary == null)
		{
			synchronized (DICTIONARY_LOCKER)
			{
				if (dictionary == null)
					dictionary = new PaymentPersonalReceiversDictionary();

				localDictionary = dictionary;
			}
		}
		return localDictionary;
	}

	/**
	 * Получение всех получателей клиента определенного класса
	 * @param clazz класс получателя
	 * @param loginId идентификатор логина клиента
	 * @return список получателей
	 * @throws BusinessException ошибка при работе с БД
	 */
	public <T> List<T>  findListReceiver(Class clazz, final Long loginId, String instanceName) throws BusinessException
	{
		return simpleService.find(DetachedCriteria.forClass(clazz).add(org.hibernate.criterion.Expression.eq("login.id", loginId)), instanceName);
	}

	/**
	 * скопировать всех получатель в другую БД
	 * @param login пользователь
	 * @param fromInstanceName имя Бд источника
	 * @param toInstanceName имя Бд назначения
	 * @throws BusinessException
	 */
	public void replicate(CommonLogin login, String fromInstanceName, String toInstanceName) throws BusinessException
	{
		List<PaymentReceiverBase> receiversList = getAll(login,fromInstanceName);
		List<PaymentReceiverBase> receiversListTo = getAll(login,toInstanceName);
		for (PaymentReceiverBase paymentReceiverBase : receiversList)
		{
			simpleService.replicate(paymentReceiverBase,toInstanceName);
			for (PaymentReceiverBase receiverBase : receiversListTo)
			{
				if(receiverBase.getId().equals(paymentReceiverBase.getId()))
				{
					receiversListTo.remove(receiverBase);
					break;
				}
			}
		}

		for (PaymentReceiverBase receiverBase : receiversListTo)
		{
			simpleService.remove(receiverBase,toInstanceName);
		}
	}

	private List<PaymentReceiverBase> getAll(CommonLogin login, String instanceName) throws BusinessException
	{
		List<PaymentReceiverBase> receiversList = new ArrayList<PaymentReceiverBase>();

		List<String> kinds = getAvaibleReceiversKinds();
		if(kinds!=null && kinds.size()!=0)
		{
			for (String kind : kinds)
			{
				List<PaymentReceiverBase> list = findListReceiver(kind, login.getId(),instanceName);
				receiversList.addAll(list);
			}
		}
		return receiversList;
	}

	/**
	 * Получение всех доступных в данной конфигурации видов получателей.
	 * @return список kind
	 */
	public List<String> getAvaibleReceiversKinds() throws BusinessException
	{
		return getDictionary().getAvaibleReceiverKinds();
	}
}
