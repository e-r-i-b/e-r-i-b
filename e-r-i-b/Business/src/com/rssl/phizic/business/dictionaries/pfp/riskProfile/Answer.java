package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 * ����� �� ������ ����-�������
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
	 * @return ������������� �������� � ��
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
	 * @return ����� ������
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * ������ ����� ������
	 * @param text ����� ������
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return ������������� �������
	 */
	public Long getHolderId()
	{
		return holderId;
	}

	/**
	 * ������ ������������� �������
	 * @param holderId ������������� �������
	 */
	public void setHolderId(Long holderId)
	{
		this.holderId = holderId;
	}

	/**
	 * @return ��� ������
	 */
	public Long getWeight()
	{
		return weight;
	}

	/**
	 * ������ ��� ������
	 * @param weight ��� ������
	 */
	public void setWeight(Long weight)
	{
		this.weight = weight;
	}
}
