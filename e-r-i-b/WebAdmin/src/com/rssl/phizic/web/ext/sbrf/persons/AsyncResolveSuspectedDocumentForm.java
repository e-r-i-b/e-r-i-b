package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tisov
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 * Форма утверждения вердикта фрод-документу
 */
public class AsyncResolveSuspectedDocumentForm extends ActionFormBase
{
	private String verdict;
	private String verdictText;
	private long documentId;
	private long person;

	/**
	 * Вердикт для документа
	 * @return
	 */
	public String getVerdict()
	{
		return verdict;
	}

	/**
	 * Установить вердикт для документа
	 * @param verdict
	 */
	public void setVerdict(String verdict)
	{
		this.verdict = verdict;
	}

	/**
	 * Пояснение сотрудника к вердикту
	 * @return
	 */
	public String getVerdictText()
	{
		return verdictText;
	}

	/**
	 * Установить пояснение сотрудника к вердикту
	 * @param verdictText
	 */
	public void setVerdictText(String verdictText)
	{
		this.verdictText = verdictText;
	}

	/**
	 * Ид документа, по которому выносится вердикт
	 * @return
	 */
	public long getDocumentId()
	{
		return documentId;
	}

	/**
	 * Установить ид документа
	 * @param documentId
	 */
	public void setDocumentId(long documentId)
	{
		this.documentId = documentId;
	}

	/**
	 * Идентификатор клиента
	 * @return
	 */
	public long getPerson()
	{
		return person;
	}

	/**
	 * Идентификатор клиента
	 * @param person
	 */
	public void setPerson(long person)
	{
		this.person = person;
	}
}
