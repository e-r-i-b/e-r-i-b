package com.rssl.phizic.operations.documents.templates;

import com.rssl.common.forms.ErrorsCollector;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.operations.EditEntityOperation;

import java.util.Calendar;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 08.03.14
 * @ $Author$
 * @ $Revision$
 */
public interface EditTemplateDocumentOperation extends EditEntityOperation
{
	/**
	 * Инициализация
	 * @param source ресурс
	 */
	void initialize(TemplateDocumentSource source) throws BusinessException, BusinessLogicException;

	/**
	 * Инициализация
	 * @param recipientId идентификатор поставщика услуг
	 */
	void initialize(Long recipientId) throws BusinessException, BusinessLogicException;

	/**
	 * @return шаблон
	 */
	TemplateDocument getTemplate();
	/**
	 * @return метаданные
	 */
	Metadata getMetadata();

	/**
	 * @return StateMachineExecutor
	 */
	StateMachineExecutor getExecutor();

	/**
	 * Обновить данными с формы
	 * @param formData данные
	 */
	void update(Map<String, Object> formData) throws BusinessLogicException, BusinessException;

	/**
	 * Редактировать шаблон
	 */
	void edit() throws BusinessException, BusinessLogicException;

	/**
	 * Изменить название шаблона
	 * @param name название
	 */
	void changeName(String name) throws BusinessException, BusinessLogicException;

	/**
	 * Обновить шаблон данными о напоминании
	 * @param reminderInfo информации о напоминании
	 * @param residualDate дата оставшегося выставленного счета
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	void changeReminderInfo(ReminderInfo reminderInfo, Calendar residualDate) throws BusinessLogicException, BusinessException;

	/**
	 * Создать шаблон в один шаг
	 */
	void saveQuicklyCreatedTemplate() throws BusinessException, BusinessLogicException;

	/**
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @param source источник данных
	 * @return html
	 */
	String buildFormHtml(TransformInfo transformInfo, FormInfo formInfo, FieldValuesSource source) throws BusinessException;

	/**
	 * @return строка идентифицирующая метаданные
	 * @throws BusinessException
	 */
	String getMetadataPath() throws BusinessException;

	/**
	 * Получить ошибки редактирования
	 * @param errorsCollector errorsCollector
	 * @param <E>
	 * @return ошибки
	 * @throws BusinessException
	 */
	public <E> ErrorsCollector<E> getTemplateErrors(ErrorsCollector<E> errorsCollector) throws BusinessException;
}
