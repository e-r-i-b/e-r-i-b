package com.rssl.phizic.business.dictionaries.pfp.targets;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class Target extends PFPDictionaryRecord
{
	private String name;         // название цели
	private Long imageId;        // идентификатор картинки
	private boolean onlyOne;     // возможность добавлять только одну цель данного типа
	private boolean laterAll;    // дата достижения цели позже остальных
	private boolean laterLoans;  // дата достижения цели позже последнего платежа по кредиту

	/**
	 * @return название цели
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - название цели
	 */
	public void setName(String name)
	{
		this.name = name;
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
	 * @return возможность добавлять только одну цель данного типа
	 */
	public boolean isOnlyOne()
	{
		return onlyOne;
	}

	/**
	 * @param onlyOne - возможность добавлять только одну цель данного типа
	 */
	public void setOnlyOne(boolean onlyOne)
	{
		this.onlyOne = onlyOne;
	}

	/**
	 * @return дата достижения цели позже остальных
	 */
	public boolean isLaterAll()
	{
		return laterAll;
	}

	/**
	 * @param laterAll - дата достижения цели позже остальных
	 */
	public void setLaterAll(boolean laterAll)
	{
		this.laterAll = laterAll;
	}

	/**
	 * @return - дата достижения цели позже последнего платежа по кредиту
	 */
	public boolean isLaterLoans()
	{
		return laterLoans;
	}

	/**
	 * @param laterLoans - дата достижения цели позже последнего платежа по кредиту
	 */
	public void setLaterLoans(boolean laterLoans)
	{
		this.laterLoans = laterLoans;
	}

	public Comparable getSynchKey()
	{
		return name;
	}

	public void updateFrom(DictionaryRecord that)
	{
		Target source = (Target) that;
		setName(source.getName());
		setImageId(source.getImageId());
	}
}
