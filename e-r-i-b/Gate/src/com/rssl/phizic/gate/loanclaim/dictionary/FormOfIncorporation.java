package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Кодов организационно-правовых форм"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "form-of-incorporation")
public class FormOfIncorporation extends AbstractDictionaryEntry
{
	@XmlElement(name = "shortName", required = true)
	private String shortName;

	///////////////////////////////////////////////////////////////////////////

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}
}
