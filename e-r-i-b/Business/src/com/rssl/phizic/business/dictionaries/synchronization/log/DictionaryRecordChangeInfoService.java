package com.rssl.phizic.business.dictionaries.synchronization.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParametersConverter;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.information.SynchronizationStateEntity;
import com.rssl.phizic.business.locale.dynamic.resources.multi.block.CompositeLanguageResourceId;
import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import com.thoughtworks.xstream.converters.Converter;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author akrenev
 * @ created 17.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с данными удаленной сущности справочника
 */

public class DictionaryRecordChangeInfoService
{
	private static final String GET_CHANGED_QUERY_NAME = DictionaryEntityChangeInfo.class.getName() + ".getChanged";
	private static final SimpleService service = new SimpleService();
	private static final List<Converter> additionalConverters = new ArrayList<Converter>();

	static
	{
		additionalConverters.add(new TableViewParametersConverter());
	}


	/**
	 * получить изменения после действия lastSynchronizationInfo
	 * @param lastSynchronizationInfo информация последнего обработанного действия
	 * @return итератор изменений
	 * @throws BusinessException
	 */
	public Iterator<DictionaryEntityChangeInfo> getChanged(SynchronizationStateEntity lastSynchronizationInfo) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(MultiBlockModeDictionaryHelper.getDictionaryLogInstanceName()), GET_CHANGED_QUERY_NAME);
			if (lastSynchronizationInfo == null)
				query.setParameter("id",         -1L);
			else
				query.setParameter("id",         lastSynchronizationInfo.getLastUpdateId());
			return query.executeIterator();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	private <T extends MultiBlockDictionaryRecord> String serialize(final T record)
	{
		return XStreamSerializer.serialize(record, additionalConverters);
	}

	/**
	 * десериализовать запись справочника
	 * @param record запись лога для справочника
	 * @param <T> тип сущности
	 * @return запись справочника
	 */
	public <T extends MultiBlockDictionaryRecord> T getEntity(final DictionaryEntityChangeInfo record)
	{
		//noinspection unchecked
		return (T) XStreamSerializer.deserialize(record.getEntityData(), additionalConverters);
	}

	/**
	 * добавить в лог информацию об изменении сущности
	 * @param record сущность
	 * @param changeType тип действия
	 * @param <T>  тип сущности
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <T extends MultiBlockDictionaryRecord> void addChangesToLog(T record, ChangeType changeType) throws BusinessException
	{
		if (!MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return;

		DictionaryEntityChangeInfo dictionaryEntityChangeInfo = new DictionaryEntityChangeInfo();
		dictionaryEntityChangeInfo.setUid(record.getMultiBlockRecordId());
		dictionaryEntityChangeInfo.setDictionaryType(record.getClass().getName());
		dictionaryEntityChangeInfo.setEntityData(serialize(record));
		dictionaryEntityChangeInfo.setChangeType(changeType);
		service.add(dictionaryEntityChangeInfo, MultiBlockModeDictionaryHelper.getDictionaryLogInstanceName());
	}

	/**
	 * добавить в лог информацию об изменении сущностей
	 * @param records сущности
	 * @param changeType тип действия
	 * @param <T>  тип сущностей
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public  <T extends MultiBlockDictionaryRecord> void addChangesToLog(List<T> records, ChangeType changeType) throws BusinessException
	{
		if (!MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return;

		List<DictionaryEntityChangeInfo> dictionaryEntityChangeInfoList = new ArrayList<DictionaryEntityChangeInfo>(records.size());

		for (MultiBlockDictionaryRecord record: records)
		{
			DictionaryEntityChangeInfo dictionaryEntityChangeInfo = new DictionaryEntityChangeInfo();
			dictionaryEntityChangeInfo.setUid(record.getMultiBlockRecordId());
			dictionaryEntityChangeInfo.setDictionaryType(record.getClass().getName());
			dictionaryEntityChangeInfo.setEntityData(serialize(record));
			dictionaryEntityChangeInfo.setChangeType(changeType);
			dictionaryEntityChangeInfoList.add(dictionaryEntityChangeInfo);
		}

		service.addList(dictionaryEntityChangeInfoList, MultiBlockModeDictionaryHelper.getDictionaryLogInstanceName());
	}

	/**
	 * добавить в лог информацию об изменении сущности
	 * @param session сессия добавления записи в лог
	 * @param record сущность
	 * @param changeType тип действия
	 * @param <T>  тип сущности
	 */
	public <T extends MultiBlockDictionaryRecord> void addChangesToLog(Session session, T record, ChangeType changeType)
	{
		addChangesToLog(session, record, changeType, null);
	}

	/**
	 * добавить в лог информацию об изменении сущности
	 * Если передан старый идентификатор сущности, то в качестве UID исползуем его,
	 * что бы в блоке заменить старую сущность, а не создавать новую
	 * @param session сессия добавления записи в лог
	 * @param record сущность
	 * @param changeType тип действия
	 * @param oldRecordId старый идентификатор сущности
	 * @param <T>  тип сущности
	 */
	public <T extends MultiBlockDictionaryRecord> void addChangesToLog(Session session, T record, ChangeType changeType, String oldRecordId)
	{
		if (!MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return;

		DictionaryEntityChangeInfo dictionaryEntityChangeInfo = new DictionaryEntityChangeInfo();
		dictionaryEntityChangeInfo.setUid(StringHelper.isEmpty(oldRecordId) ? record.getMultiBlockRecordId() : oldRecordId);
		dictionaryEntityChangeInfo.setDictionaryType(record.getClass().getName());
		dictionaryEntityChangeInfo.setEntityData(serialize(record));
		dictionaryEntityChangeInfo.setChangeType(changeType);
		session.save(dictionaryEntityChangeInfo);
	}

	/**
	 * добавить в лог информацию об изменении локалезависимой сущности
	 * @param session сессия добавления записи в лог
	 * @param record сущность
	 * @param <T>  тип сущности
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <T extends MultiBlockLanguageResources> void addLocaleResourceChangesToLog(Session session, T record) throws BusinessException
	{
		if (!MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return;

		DictionaryEntityChangeInfo dictionaryEntityChangeInfo = new DictionaryEntityChangeInfo();
		dictionaryEntityChangeInfo.setUid(CompositeLanguageResourceId.format(record));
		dictionaryEntityChangeInfo.setDictionaryType(record.getClass().getName());
		dictionaryEntityChangeInfo.setEntityData(serialize(record));
		dictionaryEntityChangeInfo.setChangeType(ChangeType.update);
		session.save(dictionaryEntityChangeInfo);
	}

	/**
	 * добавить в лог информацию об изменении локалезависимой сущности
	 * @param session сессия добавления записи в лог
	 * @param uuid сквозной идентификатор сущности
	 * @param clazz класс логируемой сущности
	 * @param <T>  тип сущности
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <T extends MultiBlockLanguageResources> void removeLocaleResourceChangesToLog(Session session, String uuid, Class<T> clazz) throws BusinessException
	{
		if (!MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return;

		DictionaryEntityChangeInfo dictionaryEntityChangeInfo = new DictionaryEntityChangeInfo();
		dictionaryEntityChangeInfo.setUid(uuid);
		dictionaryEntityChangeInfo.setDictionaryType(clazz.getName());
		dictionaryEntityChangeInfo.setChangeType(ChangeType.delete);
		session.save(dictionaryEntityChangeInfo);
	}
}
