package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Семейное положение"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "family-status")
public class FamilyStatus extends AbstractDictionaryEntry
{
	/**
	 * Необходимость отоображать блок Информация о супруге
	 */
	public enum SpouseInfo
	{
		NOT,            //Отображать блок не надо
		REQUIRED,       //Необходимо полностью отображать блок
		WITHOUT_PRENUP  //Необходимо отображать блок, но без поля "Есть ли брачный контракт?"
	}
	/**
	 * при оформлении заявки на кредит требуется указать данные о супруге
	 */
	@XmlElement(name = "spouseInfoRequired", required = true)
	private SpouseInfo spouseInfoRequired;

	public SpouseInfo getSpouseInfoRequired()
	{
		return spouseInfoRequired;
	}

	public void setSpouseInfoRequired(SpouseInfo spouseInfoRequired)
	{
		this.spouseInfoRequired = spouseInfoRequired;
	}
}
