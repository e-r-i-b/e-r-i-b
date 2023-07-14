package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.ErrorsCollector;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.operations.Operation;

import java.util.Map;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 83828 $
 */

public interface EditDocumentOperation extends Operation
{
	/**
	 * @param source объект для чтения документа и метаданных
	 * @throws BusinessException
	 */
	void initialize(DocumentSource source) throws BusinessException, BusinessLogicException;

	/**
	 * @param source объект для чтения документа и метаданных
	 * @param additionalFieldValuesSource дополнительный источник данных полей.
	 * @throws BusinessException
	 */
	void initialize(DocumentSource source, FieldValuesSource additionalFieldValuesSource) throws BusinessException, BusinessLogicException;

	/**
	 * Сохранить документ
	 * @return id сохраненного документа
	 * @throws BusinessException
	 */
	Long save() throws BusinessException, BusinessLogicException;

	/**
	 * сохранить как редактироние документа
	 * @return id сохраненного документа
	 * @throws BusinessException
	 */
	Long edit() throws BusinessException, BusinessLogicException;

	/**
	 * Сохранить черновик
	 * @return id сохраненного документа
	 * @throws BusinessException
	 */
	Long saveDraft() throws BusinessException, BusinessLogicException;

	/**
	 * Сохранить документ как шаблон
	 * @param templateName имя шаблона
	 * @return id сохраненного шаблона
	 * @throws BusinessException
	 */
	Long saveAsTemplate(String templateName) throws BusinessException, BusinessLogicException;

	/**
	 * Перевести документ в начальное состояние
	 */
	void saveAsInitial() throws BusinessException, BusinessLogicException;

	/**
	 * Перевести документ в состояние В работе TM
	 */
	public void doWaitTM() throws BusinessException, BusinessLogicException;

	/**
	 * @return метаданные
	 */
	Metadata getMetadata();

	/**
	 * @return строка идентифицирующая метаданные
	 */
	String getMetadataPath() throws BusinessException;

	/**
	 * Обновить документ введенными данными
	 * @param formDataMap введенные данные
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	void updateDocument(Map<String, Object> formDataMap) throws BusinessException, BusinessLogicException;

	/**
	 * @return Редактируемый документ.
	 */
	BusinessDocument getDocument();

	/**
	 * @return источник данных редактируемого документа.
	 */
	FieldValuesSource getFieldValuesSource()  throws BusinessException;

	/**
	 * @return Источник значений полей для валидации формы платежа (can be null)
	 */
	FieldValuesSource getValidateFormFieldValuesSource();

	/**
	 * Сделать редактируемый документ длительным поручением
	 * @param formDataMap данные для обновления документа(null если обновлять не надо)
	 */
	void makeLongOffer(Map<String, Object> formDataMap) throws BusinessException, BusinessLogicException;

	/**
	 * @return Является ли редактируемый документ длительным поручением
	 */
	boolean isLongOffer();

	/**
	 * Некоторые документы (например биллинговые платежи) в составе своих полей могут содержать информацию об ошибках
	 * Данный метод пзсволяет получить эти ошибки из документа
	 * @param errorsCollector сборщик ошибок
	 * @return сборщик ошибок с полученными по документу ошибками или пустой.
	 */
	<E> ErrorsCollector<E> getDocumentErrors(ErrorsCollector<E> errorsCollector) throws BusinessException;

	/**
	 * @return получить статус документа в терминах машины состояний. или null в случае некорректного состояния объекта.
	 */
	MachineState getDocumentSate();

	/**
	 * Строит экранную форму платежа
	 * @param fieldValuesSource - источник значений полей
	 * @param webRoot
	 * @param resourceRoot
	 * @param isDefaultShow - true - показ формы по-умолчанию, false - в остальных случаях(например, при ошибке валидации, логической ошибке и т.д.)
	 * @param skinUrl Skin URL для текущего пользователя
     * @return HTML-описание экранной формы платежа
	 */
	String buildFormHtml(FieldValuesSource fieldValuesSource, String webRoot, String resourceRoot, String skinUrl, boolean isDefaultShow) throws BusinessException;

	/**
	 * Строит описание документа для ЕРИБ API v2.x
	 * @param fieldValuesSource - источник значений полей
	 * @return XML-описание платежа
	 */
	String buildMobileXml(FieldValuesSource fieldValuesSource) throws BusinessException;

	/**
	 * Строит описание документа socialAPI
	 * @param fieldValuesSource - источник значений полей
	 * @return XML-описание платежа
	 */
	String buildSocialXml(FieldValuesSource fieldValuesSource) throws BusinessException;

	/**
	 * Строит описание документа для ATM приложения
	 * @param fieldValuesSource - источник значений полей
	 * @return XML-описание платежа
	 */
	String buildATMXml(FieldValuesSource fieldValuesSource) throws BusinessException;	

	/**
	 * @return владелец платежа (персона)
	 * @throws BusinessException
	 */
	Person getPerson() throws BusinessException;

	/**
	 * Проверка и актуализация документа
	 * @return true - актуализация произведена (требуется редирект), false - нет
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	boolean upgrade() throws BusinessLogicException, BusinessException;

	/**
	 * Сохранение данных по новому открытому счету в заявке на кредит
	 * @param accountClaimId - идентификатор заявки на открытие счета
	 * @throws BusinessException
	 */
	void saveNewAccountInLoanClaim(Long accountClaimId) throws BusinessException;
}
