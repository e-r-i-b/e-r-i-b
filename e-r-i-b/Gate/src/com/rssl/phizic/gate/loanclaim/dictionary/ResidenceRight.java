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
 * «апись справочника "¬ид собственности жиль€ по месту фактического проживани€"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "residence-right")
public class ResidenceRight extends AbstractDictionaryEntry
{
	/**
	 * при оформлении за€вки на кредит требуетс€ указать "комментарий".
	 * ≈сли за€витель указал, что проживает в собственной квартире, должна быть указана информаци€ о недвижимости (в комментарии)
	 */
	@XmlElement(name = "needRealtyInfo", required = true)
	private boolean needRealtyInfo;

	public boolean isNeedRealtyInfo()
	{
		return needRealtyInfo;
	}

	public void setNeedRealtyInfo(boolean needRealtyInfo)
	{
		this.needRealtyInfo = needRealtyInfo;
	}
}
