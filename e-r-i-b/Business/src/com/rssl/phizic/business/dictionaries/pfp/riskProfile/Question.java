package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.pfp.common.MarkDeletedRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 * вопрос риск-профиля
 */
public class Question extends PFPDictionaryRecord implements MarkDeletedRecord
{
	private SegmentCodeType segment;
	private String text;
	private List<Answer> answers;
	private boolean deleted = false;

	/**
	 * дефолтный конструктор
	 */
	public Question()
	{}

	/**
	 * копирующий конструктор
	 * @param question источник данных
	 */
	public Question(Question question)
	{
		segment = question.segment;
		text = question.text;
		deleted = question.deleted;
		answers = new ArrayList<Answer>();
		if (CollectionUtils.isEmpty(question.answers))
			return;

		for (Answer answer: question.answers)
			answers.add(new Answer(answer));
	}

	/**
	 * @return сегмент клиентов, которому предназначен вопрос
	 */
	public SegmentCodeType getSegment()
	{
		return segment;
	}

	/**
	 * задать сегмент клиентов, которому предназначен вопрос
	 * @param segment сегмент
	 */
	public void setSegment(SegmentCodeType segment)
	{
		this.segment = segment;
	}

	/**
	 * @return текст вопроса
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * установить текст вопроса
	 * @param text текст вопроса
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return возможные ответы
	 */
	public List<Answer> getAnswers()
	{
		if (answers == null)
			answers = new ArrayList<Answer>();
		
		return answers;
	}

	/**
	 * задать список возможных ответов
	 * @param answers возможные ответы
	 */
	public void setAnswers(List<Answer> answers)
	{
		this.answers = answers;
	}

	public boolean isDeleted()
	{
		return deleted;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * добавить возможный ответ
	 * @param addedAnswer возможный ответ
	 */
	public void addAnswer(Answer addedAnswer)
	{
		addedAnswer.setHolderId(getId());
		getAnswers().add(addedAnswer);
	}

	/**
	 * удаление всех возможных ответов
	 */
	public void clearAnswers()
	{
		if (answers == null)
		{
			answers = new ArrayList<Answer>();
			return;
		}

		answers.clear();
	}

	/**
	 * задать возможные ответы
	 * @param answers возможные ответы
	 */
	public void updateAnswers(List<Answer> answers)
	{
		clearAnswers();
		this.answers.addAll(answers);
	}

	public Comparable getSynchKey()
	{
		return StringHelper.getEmptyIfNull(segment) + String.valueOf(isDeleted()) + text;
	}

	public void updateFrom(DictionaryRecord that)
	{
		Question source = (Question) that;
		setText(source.getText());
		setDeleted(source.isDeleted());
		updateAnswers(source.getAnswers());
	}
}
