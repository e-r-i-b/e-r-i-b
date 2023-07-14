package com.rssl.phizic.business.dictionaries.synchronization.information;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryEntityChangeInfo;
import com.rssl.phizic.business.dictionaries.synchronization.notification.NotificationEntity;
import com.rssl.phizic.business.dictionaries.synchronization.notification.NotificationService;
import com.rssl.phizic.business.dictionaries.synchronization.notification.SynchronizationMode;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с информацией о справочнике в блоке
 */

public class DictionaryInformationService
{
	private static final NotificationEntity CURRENT_BLOCK_SYNCHRONIZATION_PARAMETERS = new NotificationEntity(MultiBlockDictionaryRecord.class, SynchronizationMode.HARD);
	private static final String CSA_ADMIN_DB_INSTANCE_NAME = "CSAAdmin";

	private static final SimpleService service = new SimpleService();
	private static final NotificationService notificationService = new NotificationService();
	private static final SynchronizationStateService synchronizationStateService = new SynchronizationStateService();

	/**
	 * @param nodeId идентификатор блока в котором обновляется справочник
	 * @throws BusinessException
	 */
	public void saveStartSynchronization(Long nodeId) throws BusinessException
	{
		saveResult(nodeId, null, DictionaryState.PROCESS, null, CSA_ADMIN_DB_INSTANCE_NAME);
	}

	/**
	 * @param nodeId идентификатор блока в котором обновляется справочник
	 * @param lastUpdateDate актуальная дата справочников в блоке
	 * @throws BusinessException
	 */
	public void saveUpdatedResult(Long nodeId, Calendar lastUpdateDate) throws BusinessException
	{
		saveResult(nodeId, lastUpdateDate, DictionaryState.UPDATED, null, CSA_ADMIN_DB_INSTANCE_NAME);
	}

	/**
	 * @param nodeId идентификатор блока в котором обновляется справочник
	 * @param lastUpdateDate актуальная дата справочников в блоке
	 * @param errorDetail описание ошибки, произошедшей при синхронизации
	 * @throws BusinessException
	 */
	public void saveErrorResult(Long nodeId, Calendar lastUpdateDate, String errorDetail) throws BusinessException
	{
		saveResult(nodeId, lastUpdateDate, DictionaryState.ERROR, errorDetail, CSA_ADMIN_DB_INSTANCE_NAME);
	}

	/**
	 * @param nodeId идентификатор блока в котором обновляется справочник
	 * @param lastUpdateDate актуальная дата справочников в блоке
	 * @param errorDetail описание ошибки, произошедшей при синхронизации
	 * @param instance имя инстанса БД
	 * @throws BusinessException
	 */
	private void saveResult(Long nodeId, Calendar lastUpdateDate, DictionaryState state, String errorDetail, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DictionaryInformation.class).add(Expression.eq("nodeId", nodeId));
		DictionaryInformation dictionaryInformation = service.findSingle(criteria, instance);
		if (dictionaryInformation == null)
		{
			dictionaryInformation = new DictionaryInformation();
			dictionaryInformation.setNodeId(nodeId);
		}
		dictionaryInformation.setState(state);
		dictionaryInformation.setErrorDetail(errorDetail);
		if (lastUpdateDate != null)
			dictionaryInformation.setLastUpdateDate(lastUpdateDate);
		service.addOrUpdate(dictionaryInformation, instance);
	}

	/**
	 * @return информация о состоянии справочников в блоках
	 * @throws BusinessException
	 */
	public List<DictionaryInformation> getDictionaryInformation() throws BusinessException
	{
		return service.getAll(DictionaryInformation.class, CSA_ADMIN_DB_INSTANCE_NAME);
	}

	private Calendar getLastUpdatedDate() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DictionaryEntityChangeInfo.class).setProjection(Projections.max("updateDate"));
		return service.findSingle(criteria, CSA_ADMIN_DB_INSTANCE_NAME);
	}

	/**
	 * @return информация о состоянии справочников (мастер-данные)
	 * @throws BusinessException
	 */
	public DictionaryInformation getMainDictionaryInformation() throws BusinessException
	{
		DictionaryInformation information = new DictionaryInformation();
		information.setLastUpdateDate(getLastUpdatedDate());
		information.setState(DictionaryState.UPDATED);
		return information;
	}

	/**
	 * инициировать запуск обязательной синхронизации справочников
	 * @throws BusinessException
	 */
	public void sendSynchronizationNotification() throws BusinessException
	{
		notificationService.notify(MultiBlockDictionaryRecord.class, SynchronizationMode.HARD);
	}

	/**
	 * инициировать запуск обязательной синхронизации справочников в текущем блоке
	 * @throws BusinessException
	 */
	public void startCurrentBlockSynchronization() throws BusinessException
	{
		synchronizationStateService.doWait(CURRENT_BLOCK_SYNCHRONIZATION_PARAMETERS);
	}
}
