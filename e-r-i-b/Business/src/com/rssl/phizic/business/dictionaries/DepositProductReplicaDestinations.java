package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.generated.DepositDictionaryElement;
import com.rssl.phizic.business.deposits.generated.DepositDictionaryProduct;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.xslt.lists.cache.event.XmlDictionaryCacheClearEvent;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.*;
import javax.xml.bind.JAXBException;

/**
 @author Pankin
 @ created 10.03.2011
 @ $Author$
 @ $Revision$
 */
@SuppressWarnings({"unchecked"})
public class DepositProductReplicaDestinations extends QueryReplicaDestinationBase
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	protected DepositProductReplicaDestinations()
	{
		super("com.rssl.phizic.business.deposits.DepositProduct.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void add(DictionaryRecord newValue) throws GateException
	{
		DepositProduct depositProduct = (DepositProduct) newValue;
		depositProduct.setLastUpdateDate(Calendar.getInstance());
		updateDepositParams(depositProduct, null);

		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try
		{
			super.add(depositProduct);
			addToLog(session,depositProduct,ChangeType.update);
			transaction.commit();
		}
		catch (Exception e)
		{
			transaction.rollback();
			throw new GateException(e);
		}
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		DepositProduct newDepositProduct = (DepositProduct) newValue;
		DepositProduct oldDepositProduct = (DepositProduct) oldValue;
		updateDepositSubTypeParams(oldDepositProduct, newDepositProduct);
		newDepositProduct.setLastUpdateDate(Calendar.getInstance());
		newDepositProduct.setAvailableOnline(((DepositProduct) oldValue).isAvailableOnline());
		newDepositProduct.setAllowedDepartments(((DepositProduct) oldValue).getAllowedDepartments());

		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try
		{
			super.update(oldValue, newValue);
			addToLog(session,oldDepositProduct,ChangeType.update);
			transaction.commit();
		}
		catch (Exception e)
		{
			transaction.rollback();
			throw new GateException(e);
		}
	}

	/**
	 * Сохранить признак доступности подвидов вклада для открытия в описании вклада; признаки капитализации и мин. баланса вклада
	 * @param oldValue - старое значение, до обновления
	 * @param newValue - новое значение
	 */
	private void updateDepositSubTypeParams(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		try
		{
			DepositProduct oldDepositProduct = (DepositProduct) oldValue;
			DepositProduct newDepositProduct = (DepositProduct) newValue;

			DepositDictionaryProduct oldProduct = JAXBUtils.unmarshalBean(DepositDictionaryProduct.class, oldDepositProduct.getDescription());
			DepositDictionaryProduct newProduct = JAXBUtils.unmarshalBean(DepositDictionaryProduct.class, newDepositProduct.getDescription());

			log.debug("[DepositProductReplicaDestinations.updateDepositSubTypeParams] newDepositProduct.getDescription().length() = " + StringHelper.getEmptyIfNull(newDepositProduct.getDescription()).length());

			updateDepositParams(newDepositProduct, newProduct);

			List<DepositDictionaryElement> oldOptions = oldProduct.getData().getOptions().getElement();
			List<DepositDictionaryElement> newOptions = newProduct.getData().getOptions().getElement();
			log.debug("[DepositProductReplicaDestinations.updateDepositSubTypeParams] newOptions.size() 0 = " + (CollectionUtils.isEmpty(newOptions) ? 0 : newOptions.size()));

			Map<BigInteger, Boolean> availToOpenMap = new HashMap<BigInteger, Boolean>();
			for (DepositDictionaryElement element : oldOptions)
			{
				availToOpenMap.put(element.getId(), element.isAvailToOpen());
			}

			for (DepositDictionaryElement element : newOptions)
			{
				Boolean availToOpenOldValue = availToOpenMap.get(element.getId());
				element.setAvailToOpen(availToOpenOldValue != null ? availToOpenOldValue : false);
			}

			List<String> wrapCdataFields = new ArrayList<String>();
			wrapCdataFields.add("text");
			log.debug("[DepositProductReplicaDestinations.updateDepositSubTypeParams] newOptions.size() 1 = " + (CollectionUtils.isEmpty(newOptions) ? 0 : newOptions.size()));
			String description = JAXBUtils.marshalBeanWithCDATA(newProduct, wrapCdataFields);
			newDepositProduct.setDescription(description);
			log.debug("[DepositProductReplicaDestinations.updateDepositSubTypeParams] description.length() = " + StringHelper.getEmptyIfNull(description).length());
		}
		catch (JAXBException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Установить в описании вклада признаки доступности капитализации и наличия минимального баланса
	 * @param depositProduct - описание вклада
	 * @param product - продукт, полученный путем разбора xml-описания вклада
	 */
	private void updateDepositParams(DepositProduct depositProduct, DepositDictionaryProduct product) throws GateException
	{
		try
		{
			if (product == null)
			{
				product = JAXBUtils.unmarshalBean(DepositDictionaryProduct.class, depositProduct.getDescription());
			}
			depositProduct.setCapitalization(product.getData().getMain().isCapitalization());
			depositProduct.setWithMinimumBalance(product.getData().getMain().isMinimumBalance());
		}
		catch (JAXBException e)
		{
			throw new GateException(e);
		}
	}

	public void remove(DictionaryRecord oldValue) throws GateException
	{
		// Не удаляем записи по депозитным продуктам.
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	private void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType)
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}
}
