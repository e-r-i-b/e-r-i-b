package com.rssl.phizic.gate.schemes;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы со схемами прав сотрудников
 */

public interface AccessSchemeService extends Service
{
	/**
	 * @param id идентификатор
	 * @return список схем прав
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AccessScheme getById(Long id) throws GateException, GateLogicException;

	/**
	 * @param categories категории
	 * @return список схем прав
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<AccessScheme> getList(String[] categories) throws GateException, GateLogicException;

	/**
	 * сохранить схему
	 * @param schema сохраняемая схема
	 * @return сохраненная схема
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AccessScheme save(AccessScheme schema) throws GateException, GateLogicException;

	/**
	 * удалить схему
	 * @param schema схема
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void delete(AccessScheme schema) throws GateException, GateLogicException;
}
