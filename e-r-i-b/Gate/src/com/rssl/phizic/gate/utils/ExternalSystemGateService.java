package com.rssl.phizic.gate.utils;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;
import java.util.List;

/**
 * Cервис внешних систем.
 *
 * @author khudyakov
 * @ created 19.10.2011
 * @ $Author$
 * @ $Revision$
 */
public interface ExternalSystemGateService extends Service
{
	/**
	 * ѕроверка на активность внешней системы. ≈сли система не активна бросаетс€ InactiveExternalSystemException
	 * с сообщением из тех переыва
	 * @param code код (uuid адаптера либо SystemId внешней системы)
	 * @throws GateException
	 */
	public void check(String code) throws GateException;

	/**
	 * ѕоказывает активна ли в данный момент внешн€€ система
	 * (если внешн€€ система не найдена б Ѕƒ, то посылка запроса разрешена)
	 * @param externalSystem внешн€€ система
	 * @return true - активна
	 */
	boolean isActive(ExternalSystem externalSystem) throws GateException;

	/**
	 * ѕроверка на активность внешней системы. ≈сли система не активна бросаетс€ InactiveExternalSystemException
	 * с сообщением из тех переыва
	 * @param externalSystem внешн€€ система
	 * @throws GateException
	 */
	public void check(ExternalSystem externalSystem) throws GateException;

	/**
	 * ¬озвращает список внешних систем по подразделению и продукту
	 * @param office подразделение
	 * @param product продукт
	 * @return список внешних систем
	 * @throws GateException
	 */
	public List<? extends ExternalSystem> findByProduct(Office office, BankProductType product) throws GateException;

	/**
	 * ¬озвращает список внешних систем по коду тербанка
	 * @param codeTB подразделение
	 * @return список внешних систем
	 * @throws GateException
	 */
	public List<? extends ExternalSystem> findByCodeTB(String codeTB) throws GateException;

	/**
	 * ¬озвращает врем€ окончани€ технологического перерыва, если есть активный. ¬ случае запрета
	 * оффлайн-платежей кидаетс€ InactiveExternalSystemException
	 * @param externalSystemUUID uuid адаптера либо SystemId внешней системы
	 * @return врем€ окончани€ технологического перерыва, либо null, если система активна.
	 * @throws GateException
	 */
	public Calendar getTechnoBreakToDateWithAllowPayments(String externalSystemUUID) throws GateException;

	/**
	 * ƒобавить запись о недоступности ћЅ 
	 * использовать общий метод
	 */
	@Deprecated
	public void addMBKOfflineEvent() throws GateException;

	/**
	 * ƒобавить запись об ошибке по выбранной внешней системе
	 * @param systemCode код внешней системы, дл€ которой необходимо добавить запись об ошибке
	 * @param systemType тип внешней системы, дл€ которой необходимо добавить запись об ошибке
	 * @throws GateException
	 */
	public void addOfflineEvent(String systemCode, AutoStopSystemType systemType) throws GateException;
}
