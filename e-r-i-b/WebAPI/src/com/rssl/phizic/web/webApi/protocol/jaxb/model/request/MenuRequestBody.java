package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.Excluded;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Для запроса содержания визуального интерфейса (операция "menucontainer.content")
 *
 * @author Balovtsev
 * @since 24.04.14
 */
@XmlRootElement(name = "body")
public class MenuRequestBody extends SimpleRequestBody
{
	private List<Excluded> excludedVisualElements;

	/**
	 * @return список исключаемых элементов
	 */
	@XmlElementWrapper(name = "excludedVisualElements", required = false)
	@XmlElement(name="exclude", required = true)
	public List<Excluded> getExcludedVisualElements()
	{
		return excludedVisualElements;
	}

	/**
	 * @param excludedVisualElements список исключаемых элементов
	 */
	public void setExcludedVisualElements(List<Excluded> excludedVisualElements)
	{
		this.excludedVisualElements = excludedVisualElements;
	}
}
