package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 * Ответ на вопрос риск-профиля
 */
public class Answer extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private String text;
	private Long holderId;
	private Long weight;

	public Answer()
	{}

	public Answer(Answer answer)
	{
		text = answer.text;
		weight = answer.getWeight();
	}

	/**
	 * @return идентификатор сущности в БД
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * for hibernate
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return текст ответа
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * задать текст ответа
	 * @param text текст ответа
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return идентификатор вопроса
	 */
	public Long getHolderId()
	{
		return holderId;
	}

	/**
	 * задать идентификатор вопроса
	 * @param holderId идентификатор вопроса
	 */
	public void setHolderId(Long holderId)
	{
		this.holderId = holderId;
	}

	/**
	 * @return вес ответа
	 */
	public Long getWeight()
	{
		return weight;
	}

	/**
	 * задать вес ответа
	 * @param weight вес ответа
	 */
	public void setWeight(Long weight)
	{
		this.weight = weight;
	}
}
