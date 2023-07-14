package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.FormException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 20.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */

public interface ExtendedMetadataLoader extends Serializable
{
	/**
	 * Загружает расширенные данные о форме
	 * Данный метод используется для первичной загрузки меты или меты по документу не содержащем в составе данных метаданные.
	 * @param metadata оригинальные метаданные
	 * @param fieldSource источник значений "характеризующий/идентифицирующий" загружаемую мету:
	 * например, для биллинговых платежей источник должен содержать иинформацию о поставщике услуг.
	 * @return расширенные данные
	 */
	Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException;

	/**
	 * загрузка меты для существующего документа.
	 * сущестующий документ может содержать часть мета информации.
	 * см например com.rssl.phizic.gate.payments.systems.PaymentSystemPayment#getExtendedFields()
	 * @param metadata оригинальные метаданные
	 * @param document документ, содержащий необходимую информацию для загрузки меты.
	 * @return расширенные метаданные
	 */
	Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException;

	/**
	 * загрузка метаданных по шаблону документа
	 * @param metadata оригинальные метаданные
	 * @param template шаблон документа
	 * @return расширенные метаданные
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException;

	/**
	 * загрузка метаданных по документу и шаблону документа
	 * @param metadata оригинальные метаданные
	 * @param document документ
	 * @param template шаблон документа
	 * @return расширенные метаданные
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException;

	/**
	 * Ключ, идентифицирующий расширенные метаданные для заданного источника данных.
	 * Если ключ одинаковый, то и load'ы должны возвращать одинаковые данные.
	 * Однако расширенные метаданные могут зависить от множества параметров(например всех полей биллингового платажа),
	 * в данном случае нецелесообразно производить кеширование или сравнение меты по ключу(формирование данного ключа может занимать больше ресурсов чем сравнение меты)
	 * В данном случае необходимо возвращать null. значение null сигранлизирует о том, что мету надо грузить всегда заново.
	 * @param fieldSource источник занчаний для формирования ключа.
	 * @return ключ или null
	 */
	String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException;
}