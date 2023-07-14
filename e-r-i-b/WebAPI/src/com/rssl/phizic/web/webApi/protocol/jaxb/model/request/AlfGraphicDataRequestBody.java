package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Balovtsev
 * @since 16.05.2014
 */
public class AlfGraphicDataRequestBody extends SimpleRequestBody
{
	private Boolean showKopeck;
	private Boolean showWithOverdraft;

	/**
	 * Необязательный элемент
	 * @return Признак отображения копеек в сумме остатка
	 */
	@XmlElement(name = "showKopeck", required = false)
	public Boolean getShowKopeck()
	{
		return showKopeck == null ? false : showKopeck;
	}

	/**
	 * Необязательный элемент
	 * @return Признак отображения для кредитных карт и карт
	 * с разрешенным овердрафтом суммы с учетом или без учета
	 * кредитных средств
	 */
	@XmlElement(name = "showWithOverdraft", required = false)
	public Boolean getShowWithOverdraft()
	{
		return showWithOverdraft == null ? false : showWithOverdraft;
	}

	public void setShowKopeck(Boolean showKopeck)
	{
		this.showKopeck = showKopeck;
	}

	public void setShowWithOverdraft(Boolean showWithOverdraft)
	{
		this.showWithOverdraft = showWithOverdraft;
	}
}
