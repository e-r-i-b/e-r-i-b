package com.rssl.common.forms.doc;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 22.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 9379 $
 */

public interface DocumentSignature extends Serializable
{
	/**
	 * @return текст подписи
	 */
	String getText();

	/**
	 * @param text текст подписи
	 */
	void setText(String text);

	/**
	 * @return ID операции в которой была совершена подпись
	 */
	String getOperationId();

	/**
	 * @param operationId ID операции в которой была совершена подпись
	 */
	void setOperationId(String operationId);

	/**
	 * @return ID сессии в которой была совершена подпись
	 */
	String getSessionId();

	/**
	 * @param sessionId ID сессии в которой была совершена подпись
	 */
	void setSessionId(String sessionId);

	Calendar getCheckDate();

	void setCheckDate(Calendar checkDate);
}