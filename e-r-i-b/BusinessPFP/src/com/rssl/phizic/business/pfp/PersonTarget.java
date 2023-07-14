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

/** ÷ель клиента, указанна€ при прохождении ѕ‘ѕ */
public class PersonTarget
{
	private Long id;
	private Long dictionaryTarget; //id цели из справочника, с которой св€зана цель клиента
	private String name;           //название цели
	private String nameComment;    //комментарий к названию цели
	private Calendar plannedDate;  //планируема€ дата покупки
	private Money amount;          //стоимость цели
	private Long imageId;          //id изображени€
	private boolean veryLast;      //признак показывающий что, цель отображаетс€ самой последней в списке

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

	@Deprecated //Ќеобходимо передавать всю цель из справочника, а не только идентификатор
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
	 * @return true - цель отображаетс€ самой последней в списке
	 */
	public boolean isVeryLast()
	{
		return veryLast;
	}

	/**
	 * @param veryLast - true - цель отображаетс€ самой последней в списке
	 */
	public void setVeryLast(boolean veryLast)
	{
		this.veryLast = veryLast;
	}
}
