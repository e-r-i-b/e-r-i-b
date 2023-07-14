package com.rssl.phizic.business.pfp;

import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */

/** ���� �������, ��������� ��� ����������� ��� */
public class PersonTarget
{
	private Long id;
	private Long dictionaryTarget; //id ���� �� �����������, � ������� ������� ���� �������
	private String name;           //�������� ����
	private String nameComment;    //����������� � �������� ����
	private Calendar plannedDate;  //����������� ���� �������
	private Money amount;          //��������� ����
	private Long imageId;          //id �����������
	private boolean veryLast;      //������� ������������ ���, ���� ������������ ����� ��������� � ������

	public PersonTarget()
	{
	}

	public PersonTarget(Target dictionaryTarget)
	{
		this.dictionaryTarget = dictionaryTarget.getId();
		this.name = dictionaryTarget.getName();
		this.imageId = dictionaryTarget.getImageId();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getDictionaryTarget()
	{
		return dictionaryTarget;
	}

	@Deprecated //���������� ���������� ��� ���� �� �����������, � �� ������ �������������
	public void setDictionaryTarget(Long dictionaryTarget)
	{
		this.dictionaryTarget = dictionaryTarget;
	}

	public void setDictionaryTarget(Target dictionaryTarget)
	{
		this.dictionaryTarget = dictionaryTarget.getId();
		this.name = dictionaryTarget.getName();
		this.imageId = dictionaryTarget.getImageId();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNameComment()
	{
		return nameComment;
	}

	public void setNameComment(String nameComment)
	{
		this.nameComment = nameComment;
	}

	public Calendar getPlannedDate()
	{
		return plannedDate;
	}

	public void setPlannedDate(Calendar plannedDate)
	{
		this.plannedDate = plannedDate;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	/**
	 * @return true - ���� ������������ ����� ��������� � ������
	 */
	public boolean isVeryLast()
	{
		return veryLast;
	}

	/**
	 * @param veryLast - true - ���� ������������ ����� ��������� � ������
	 */
	public void setVeryLast(boolean veryLast)
	{
		this.veryLast = veryLast;
	}
}
