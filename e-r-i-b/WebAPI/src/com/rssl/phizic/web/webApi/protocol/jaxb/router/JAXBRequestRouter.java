package com.rssl.phizic.web.webApi.protocol.jaxb.router;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;

import javax.xml.bind.JAXBException;

/**
 * @author Balovtsev
 * @since 24.04.14
 */
public interface JAXBRequestRouter
{
	/**
	 * Метод должен обрабатывать запрос и возвращать подготовленный ответ в виде строки
	 *
	 * @param request запрос
	 * @return Ответ на запрос
	 */
	String process(Request request, String source) throws JAXBException;

	/**
	 * Очищает то что было создано/добалено в процессе работы маршрутизатора
	 */
	void clearRouteContext();
}
