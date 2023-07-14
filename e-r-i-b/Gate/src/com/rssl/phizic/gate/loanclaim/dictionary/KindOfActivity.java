package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Вид деятельности компании"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "types-of-company")
public class KindOfActivity extends AbstractDictionaryEntry
{
	/**
	 * при оформлении заявки на кредит требуется указать "комментарий"
	 * РО 13 (Расширенная заявка на кредит, 5.19.8.1, шаг Работа и доход):
	 * "
	 * Поле отображается, и обязательно для заполнения, если в поле «Виды деятельности» выбраны значения:
	 *  •	Оптовая / розничная торговля (уточнение)
	 *  •	Услуги (уточнение)
	 *  •	Другие отрасли (уточнение)
	 * "
	 */
	@XmlElement(name = "orgActivityDescRequired", required = true)
	private boolean orgActivityDescRequired;

	public boolean isOrgActivityDescRequired()
	{
		return orgActivityDescRequired;
	}

	public void setOrgActivityDescRequired(boolean orgActivityDescRequired)
	{
		this.orgActivityDescRequired = orgActivityDescRequired;
	}
}
