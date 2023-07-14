package com.rssl.phizic.business.dictionaries.synchronization.processors;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.SkipEntitySynchronizationException;
import com.rssl.phizic.business.dictionaries.synchronization.SynchronizeException;
import com.rssl.phizic.business.dictionaries.synchronization.information.SynchronizationStateService;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryEntityChangeInfo;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * базовый класс обработки изменений справочников
 */

public abstract class ProcessorBase<R extends MultiBlockDictionaryRecord>
{
	protected static final String CSA_ADMIN_DB_INSTANCE_NAME = "CSAAdmin";

	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final SynchronizationStateService synchronizationStateService = new SynchronizationStateService();
	private static final ImageService imageService = new ImageService();

	protected static final SimpleService simpleService = new SimpleService();

	protected abstract Class<R> getEntityClass();

	protected abstract R getNewEntity();

	protected R getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("uuid", uuid));
		return simpleService.<R>findSingle(criteria);
	}

	protected abstract void update(R source, R destination) throws BusinessException;

	/**
	 * обработать изменение
	 * @param changeInfo информация об изменении
	 * @throws SynchronizeException
	 */
	public final void process(final DictionaryEntityChangeInfo changeInfo) throws SynchronizeException, SkipEntitySynchronizationException
	{
		try
		{
			//noinspection OverlyComplexAnonymousInnerClass
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					switch (changeInfo.getChangeType())
					{
						case update:
							doUpdate(changeInfo);
							break;
						case delete:
							doRemove(changeInfo.getUid());
							break;
						default:
							throw new SynchronizeException("Неизвестный тип действия " + changeInfo.getChangeType());
					}
					synchronizationStateService.doUpdate(changeInfo.getId(), changeInfo.getUpdateDate());
					return null;
				}
			});
		}
		catch (SkipEntitySynchronizationException e)
		{
			throw e;
		}
		catch (SynchronizeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	protected void doSave(R localEntity) throws BusinessException, BusinessLogicException
	{
		simpleService.addOrUpdate(localEntity);
	}

	private void doUpdate(DictionaryEntityChangeInfo changeInfo) throws BusinessException, BusinessLogicException
	{
		String entityUuid = changeInfo.getUid();
		R localEntity = getEntity(entityUuid);
		if (localEntity == null)
			localEntity = getNewEntity();

		R source = dictionaryRecordChangeInfoService.<R>getEntity(changeInfo);
		update(source, localEntity);
		doSave(localEntity);
	}

	protected void doRemove(String uid) throws BusinessException, BusinessLogicException
	{
		R localEntity = getEntity(uid);
		if (localEntity != null)
			doRemove(localEntity);
	}

	protected void doRemove(R localEntity) throws BusinessException, BusinessLogicException
	{
		simpleService.remove(localEntity);
	}

	private Image getImage(Long id, String instanceName) throws BusinessException
	{
		return imageService.findByIdForUpdate(id, instanceName);
	}

	protected Image mergeImage(Image source, Image destination) throws BusinessException
	{
		if (source == null)
		{
			if (destination != null)
				imageService.remove(destination, null);
			return null;
		}

		if (destination == null)
			destination = new Image();

		return doMergeImage(source, destination);
	}

	protected Long mergeImage(Long sourceId, Long destinationId) throws BusinessException
	{
		if (sourceId == null)
		{
			removeImage(destinationId);
			return null;
		}

		Image source = getImage(sourceId, CSA_ADMIN_DB_INSTANCE_NAME);
		if(source == null)
			throw new SkipEntitySynchronizationException("Изображение с идентификатором " + sourceId +" не найдено в БД " + CSA_ADMIN_DB_INSTANCE_NAME);
		Image destination = destinationId == null? new Image(): getImage(destinationId, null);

		return doMergeImage(source, destination).getId();
	}

	private Image doMergeImage(Image source, Image destination) throws BusinessException
	{
		if (source.getMd5().equals(imageService.getImageHash(destination, null)) &&
				StringUtils.equals(source.getLinkURL(), destination.getLinkURL()) &&
				StringUtils.equals(source.getName(), destination.getName()) &&
				StringUtils.equals(source.getImageHelp(), destination.getImageHelp()) )
			return destination;

		byte[] sourceImgData = imageService.loadImageData(source, CSA_ADMIN_DB_INSTANCE_NAME);
		destination.setImageHelp(source.getImageHelp());
		destination.setUpdateTime(source.getUpdateTime());
		destination.setExtendImage(source.getExtendImage());
		destination.setInnerImage(source.getInnerImage());
		destination.setLinkURL(source.getLinkURL());
		destination.setName(source.getName());

		imageService.addOrUpdateImageAndImageData(destination, sourceImgData, null);
		return destination;
	}

	protected void removeImage(Long destinationId) throws BusinessException
	{
		if (destinationId != null)
			imageService.removeImageById(destinationId, null);
	}

	protected final <T extends MultiBlockDictionaryRecord> T getLocalVersionByGlobal(T source) throws BusinessException
	{
		if (source == null)
			return null;

		ProcessorBase processor = ProcessorDictionary.getProcessor(source.getClass());
		if (processor == null)
			throw new BusinessException("Не найден процессор для получения локальной копии " + source.getClass());

		//noinspection unchecked
		T result = (T) processor.getEntity(source.getMultiBlockRecordId());
		if (result == null)
			processNotFoundLocalVersion(source.getClass(), Collections.singletonList(source.getMultiBlockRecordId()));

		return result;
	}

	protected final <T extends MultiBlockDictionaryRecord> T getLocalVersionByGlobal(T source, String identityPropertyName) throws BusinessException
	{
		if (source == null)
			return null;

		DetachedCriteria criteria = DetachedCriteria.forClass(source.getClass()).add(Expression.eq(identityPropertyName, source.getMultiBlockRecordId()));
		T result = simpleService.<T>findSingle(criteria);
		if (result == null)
			processNotFoundLocalVersion(source.getClass(), Collections.singletonList(source.getMultiBlockRecordId()));

		return result;
	}

	protected final <T extends MultiBlockDictionaryRecord> List<T> getLocalVersionByGlobal(Collection<T> sourceList, String identityPropertyName) throws BusinessException
	{
		if (CollectionUtils.isEmpty(sourceList))
			return Collections.emptyList();

		Class<T> entityClass = null;
		List<String> uuidList = new ArrayList<String>();
		for (T source : sourceList)
		{
			uuidList.add(source.getMultiBlockRecordId());
			if (entityClass == null)
				//noinspection unchecked
				entityClass = (Class<T>) source.getClass();
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass).add(Expression.in(identityPropertyName, uuidList));
		List<T> result = simpleService.find(criteria);
		if (uuidList.size() == result.size())
			return result;

		List<String> localUUIDList = new ArrayList<String>();
		for (T source : result)
			localUUIDList.add(source.getMultiBlockRecordId());

		throw new BusinessException(getLocalVersionNotFoundErrorMessage(entityClass, CollectionUtils.subtract(uuidList, localUUIDList)));
	}

	private <T extends MultiBlockDictionaryRecord> void processNotFoundLocalVersion(Class<T> source, Collection ids) throws BusinessException
	{
		throw new BusinessException(getLocalVersionNotFoundErrorMessage(source, ids));
	}

	private <T extends MultiBlockDictionaryRecord> String getLocalVersionNotFoundErrorMessage(Class<T> source, Collection ids)
	{
		return "Ошибка синхронизации справочника " + getEntityClass().getSimpleName() + ":" +
				" справочник " + source.getSimpleName() + " не синхронизирован (" +
				"не найден(ы) идентификатор(ы): " + org.apache.commons.lang.StringUtils.join(ids, ", ") +
				")!";
	}

	/**
	 * Класс справочника для очистки его кеша
	 * @return
	 */
	public Class getClearCacheKey()
	{
		return getEntityClass();
	}
}
