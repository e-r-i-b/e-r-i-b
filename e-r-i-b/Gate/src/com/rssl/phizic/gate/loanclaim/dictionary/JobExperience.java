package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Стаж на текущем месте работы"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "experience-on-current-job")
public class JobExperience extends AbstractDictionaryEntry
{
	/**
	 *  признак, не подходит по условиям кредита (never null)
	 */
	@XmlElement(name = "loanNotAllowed", required = true)
	private boolean loanNotAllowed;

	public boolean isLoanNotAllowed()
	{
		return loanNotAllowed;
	}

	public void setLoanNotAllowed(boolean loanNotAllowed)
	{
		this.loanNotAllowed = loanNotAllowed;
	}
}
