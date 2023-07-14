package com.rssl.phizic.gate.claims.pfr;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.pfr.StatementStatus;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Заявка на запрос выписки из ПФР (Пенсионного Фонда РФ)
 */
public interface PFRStatementClaim extends SynchronizableDocument
{
	/**
	 * Возвращает номер СНИЛС
	 * @return номер СНИЛС запрашивающего
	 */
	String getSNILS();

	/**
	 * Возвращает имя
	 * @return имя запрашивающего
	 */
	String getFirstName();

	/**
	 * Возвращает фамилию
	 * @return фамилия
	 */
	String getSurName();

	/**
	 * Возвращает отчество
	 * @return отчество
	 */
	String getPatrName();

	/**
	 * Возвращает дату рождения
	 * @return дата рождения
	 */
	Calendar getBirthDay();

	/**
	 * Возвращает тип документа
	 * @return тип документа
	 */
	ClientDocumentType getDocumentType();

	/**
	 * Возвращает номер документа
	 * @return номер документа
	 */
	String getDocNumber();

	/**
	 * Возвращает серию документа
	 * @return серия документа
	 */
	String getDocSeries();

	/**
	 * Возвращает дату выдачи документа
	 * @return дата выдачи документа
	 */
	Calendar getDocIssueDate();

	/**
	 * Возвращает "кем выдан"
	 * @return "кем выдан"
	 */
	String getDocIssueBy();

	/**
	 * Возвращает код подразделения, выдавшего документ
	 * @return код подразделения
	 */
	String getDocIssueByCode();

	/**
	 * Отвечает на вопрос "Готова ли выписка из ПФР?"
	 * @return статус готовности выписки из ПФР
	 */
	StatementStatus isReady();

	/**
	 * Установить статус выписки из ПФР
	 * @param isReady статус выписки
	 */
	void setReady(StatementStatus isReady);

	/**
	 * Возвращает описание статуса выписки из ПФР
	 * @return текстовое описание статуса выписки
	 */
	String getReadyDescription();

	void setReadyDescription(String readyDescription);
}
