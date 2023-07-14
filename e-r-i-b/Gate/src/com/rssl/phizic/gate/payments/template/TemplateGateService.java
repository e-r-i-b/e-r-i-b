package com.rssl.phizic.gate.payments.template;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * Сервис для работы с шаблонами платежей
 *
 * @author khudyakov
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateGateService extends Service
{
	/**
	 * Поиск шаблона документа по внешнему идентификатору
	 * Примечание: ЕСУШ не выполняет проверку на принадлежность шаблона клиенту
	 *
	 * @param id идентификатор
	 * @return список шаблонов
	 */
	GateTemplate findById(Long id) throws GateException;

	/**
	 * Поиск шаблонов документов
	 * Примечание:
	 * 1. ЕСУШ не выполняет проверку на принадлежность шаблона клиенту
	 * 2. На данном этапе во внешнюю систему отправляется вся история изменения профиля клиента, при этом в ЕСУШ шаблоны не перепривязываются от одного профиля к другому
	 *
	 * @param client клиент
	 * @return список шаблонов
	 */
	List<? extends GateTemplate> getAll(Client client) throws GateException, GateLogicException;

	/**
	 * Обновить шаблон(ы)
	 * Примечание: ЕСУШ не выполняет проверку на принадлежность шаблона клиенту
	 *
	 * @param templates список шаблонов
	 */
	void addOrUpdate(List<? extends GateTemplate> templates) throws GateException;

	/**
	 * Удалить шаблон
	 * Примечание: ЕСУШ не выполняет проверку на принадлежность шаблона клиенту
	 *
	 * @param templates шаблон
	 */
	void remove(List<? extends GateTemplate> templates) throws GateException;
}
