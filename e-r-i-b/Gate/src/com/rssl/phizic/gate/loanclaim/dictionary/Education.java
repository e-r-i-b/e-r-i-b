package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запись справочника "Образование"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "education")
public class Education extends AbstractDictionaryEntry
{
	/**
	 * При оформлении заявки на кредит требуется указать курс неоконченного высшего образования
	 */
	@XmlElement(name = "highEducationCourseRequired", required = true)
	private boolean highEducationCourseRequired;

	public boolean isHighEducationCourseRequired()
	{
		return highEducationCourseRequired;
	}

	public void setHighEducationCourseRequired(boolean highEducationCourseRequired)
	{
		this.highEducationCourseRequired = highEducationCourseRequired;
	}
}
