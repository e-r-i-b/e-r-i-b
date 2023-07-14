package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Вид недвижимости в собственности"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "type-of-realty")
public class TypeOfRealty extends AbstractDictionaryEntry
{
	/**
	 * Если при оформлении кредитной заявки заявитель указал, что проживает в собственной квартире
	 * (значение из справочника ResidenceRight needRealtyInfo == true), то должна быть указана информация о квартире (поле residence типа недвижимости д.б. true).
	 * см. РО 13 (Расширенная заявка на кредит, 5.19.8.1, шаг Собственность и долги)
	 */
	@XmlElement(name = "residence", required = true)
	private boolean residence;

	public boolean isResidence()
	{
		return residence;
	}

	public void setResidence(boolean residence)
	{
		this.residence = residence;
	}
}
