package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.StateMachineInfo;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.xslt.FilteredEntityListSource;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.Map;
import javax.xml.transform.Templates;

/**
 * Обеспечивает метаданными
 * @author Evgrafov
 * @ created 09.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */
public interface Metadata extends Serializable
{
	/**
	 * @return кодовое обозначение метаданных (== getForm().getName())
	 */
	String getName();

	/**
	 * @return Справочники формы
	 */
	Map<String, Element> getDictionaries();

	/**
	 * @return форма для платежа
	 */
	Form getForm();

	/**
	 *
	 * @return форма для списка
	 */
	Form getFilterForm();

	/**
	 * Получить шаблон для трансформации
	 * @param templateName имя шаблона
	 * @return шаблон для трансформации
	 */
	XmlConverter createConverter(String templateName);

	/**
	 * @return Создать новый документ
	 * @throws FormException
	 */
	BusinessDocument createDocument() throws FormException;

	/**
	 * @return новый шаблон документа
	 */
	TemplateDocument createTemplate();

	/**
	 * @return источник отфильтрованного списка документов
	 */
	FilteredEntityListSource getListSource();

	/**
	 * @return загрузчик доп полей
	 */
	ExtendedMetadataLoader getExtendedMetadataLoader();

	/**
	 * @param templateName имя наблона
	 * @return XSLT шаблон
	 */
	Templates getTemplates(String templateName);

	/**
	 * @return форма общего списка
	 */
	String getListFormName();

	/**
	 * @return настройки редактирования
	 */
	OperationOptions getEditOptions();

	/**
	 * @return настройки отзыва
	 */
	OperationOptions getWithdrawOptions();

	boolean isNeedParent();

	Map<String, Object> getAdditionalAttributes();

	/**
	 * @return машина состояний
	 */
	StateMachineInfo getStateMachineInfo();

	/**
	 * Ключ идентифицирующий метаданные
	 * @param fieldSource источник данных для идентифкации расширенных метаданных.
	 * @return ключ
	 */
	String getMetadataKey(FieldValuesSource fieldSource) throws FormException;
}
