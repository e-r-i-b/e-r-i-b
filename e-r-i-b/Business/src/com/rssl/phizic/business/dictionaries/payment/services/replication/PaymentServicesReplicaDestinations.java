package com.rssl.phizic.business.dictionaries.payment.services.replication;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.business.dictionaries.payment.services.MultiInstancePaymentServiceService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.locale.PaymentServiceResources;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.BeanHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @author khudyakov
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServicesReplicaDestinations extends QueryReplicaDestinationBase<PaymentService>
{
	private static final MultiInstancePaymentServiceService paymentServiceService = new MultiInstancePaymentServiceService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final LanguageResourcesBaseService<PaymentServiceResources> paymentServiceResourcesService = new LanguageResourcesBaseService<PaymentServiceResources>(PaymentServiceResources.class);

	public PaymentServicesReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.payment.services.PaymentService.getAll");
	}

	public void initialize(GateFactory factory) throws GateException {}

	public void add(PaymentService newValue) throws GateException
	{
		String synchKey  = newValue.getSynchKey().toString();
		Transaction tx = null;

		try
		{
			PaymentService existingPaymentService = findBySynchKey(synchKey);
			if (existingPaymentService != null)
			{
				update(existingPaymentService, newValue);
			}
			else
			{
				if (updateParent(newValue))
				{
					Session session = getSession();
					tx = session.beginTransaction();
					getSession().save(newValue);
					addToLog(session, newValue, ChangeType.update);
					tx.commit();
				}
			}
		}
		catch (Exception e)
		{
			if(tx != null)
				tx.rollback();

			log.error(String.format("При добавлении группы услуг synchKey = %s произошла ошибка.", synchKey));
			throw new GateException(e);
		}
	}

	public void remove(PaymentService oldValue) throws GateException
	{
		Transaction tx = null;
		try
		{
			if (getCountOfProviders(oldValue.getId()) > 0)
			{
				log.info(String.format("Удаление  группы услуг synchKey = %s невозможно, к ней привязаны поставщики.", oldValue.getSynchKey()));
				return;
			}

			if(paymentServiceService.hasChild(oldValue.getId(), getInstanceName()))
			{
				log.info(String.format("Удаление  группы услуг synchKey = %s невозможно, так как имеются подчиненные группы услуг.", oldValue.getSynchKey()));
				return;
			}

			Session session = getSession();
			tx = session.beginTransaction();
			super.remove(oldValue);
			addToLog(session, oldValue, ChangeType.delete);
			paymentServiceResourcesService.removeResources(session,oldValue.getMultiBlockRecordId());
			tx.commit();
		}
		catch (Exception e)
		{
			if(tx != null)
				tx.rollback();

			log.error(String.format("При удалении группы услуг synchKey = %s произошла ошибка", oldValue.getSynchKey()));
			throw new GateException(e);
		}
	}

	private Integer getCountOfProviders(Long servieId)
	{
		Query query = getSession().getNamedQuery(PaymentService.class.getName() + ".getCountOfProvider").setParameter("id", servieId);
		return Integer.decode(query.uniqueResult().toString());
	}

	public void update(PaymentService oldValue, PaymentService newValue) throws GateException
	{
		Transaction tx = null;
		try
		{
			newValue.setId(oldValue.getId());
			newValue.setPopular(oldValue.isPopular());
			newValue.setPriority(oldValue.getPriority());
			newValue.setShowInAtmApi(oldValue.getShowInAtmApi());
			newValue.setShowInMApi(oldValue.getShowInMApi());
			newValue.setShowInSystem(oldValue.getShowInSystem());
			newValue.setParentServices(oldValue.getParentServices());

			if(newValue.getDefaultImage() == null)
				newValue.setImageId(oldValue.getImageId());

			updateParent(newValue);

			BeanHelper.copyPropertiesFull(oldValue, newValue);

			Session session = getSession();
			tx = session.beginTransaction();
			session.update(oldValue);
			addToLog(session, oldValue, ChangeType.update);
			tx.commit();
		}
		catch (Exception e)
		{
			if(tx != null)
				tx.rollback();

			log.error(String.format("При обновлении группы услуг synchKey = %s произошла ошибка.", oldValue.getSynchKey()), e);
			throw new GateException(e);
		}
	}

	private boolean updateParent(PaymentService paymentService)
	{
		String synchKey = paymentService.getSynchKey().toString();
		int index = synchKey.lastIndexOf(".");
		String parentSynchKey = synchKey;
		if (index > 0)
			parentSynchKey = synchKey.substring(0, index);

		//верхнему уровню иерархии родитель не нужен
		if (!synchKey.equals(parentSynchKey))
		{
			//ищем родителя для постороения иерархии
			PaymentService parent = findBySynchKey(parentSynchKey);

			if (parent != null)
			{
				if (getCountOfProviders(parent.getId()) > 0)
				{
					log.info(String.format("Добавление группы услуг synchKey = %s невозможно, так как к родительской услуге привязаны поставщики.", synchKey));
					return false;
				}
				List<PaymentService> list = paymentService.getParentServices();
				if (!list.contains(parent))
				{
					list.add(parent);
					paymentService.setParentServices(list);
				}
			}
		}
		else
			paymentService.setCategory(true);
		//если у услуги не указана картинка, добавляем стандартную
		if (paymentService.getDefaultImage() == null)
		{
			paymentService.setDefaultImage("/payment_service/tovary_uslugi.jpg");
		}
		return true;
	}

	private PaymentService findBySynchKey(String synchKey)
	{
		Query query = getSession().getNamedQuery(PaymentService.class.getName() + ".findBySynchKey").setParameter("synchKey", synchKey);
		return (PaymentService) query.uniqueResult();
	}

	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	private <T> void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType)
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}
}
