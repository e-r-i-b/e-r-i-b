package com.rssl.phizic.web.gate.services.util;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public interface GateInfoService_PortType extends Remote
{
	/**
	 * @return строка, идентифицирующая внешнюю систему
	 */
	String getUID(Office office) throws RemoteException;

	/**
	 * @return true - плата списывается гейтом
	 */
	Boolean isNeedChargeOff(Office office) throws RemoteException;

	/**
	 * @return true - пользователь импортируется
	 */
	Boolean isImportEnable(Office office) throws RemoteException;

	/**
	 * @return true - после введения данных необходимо зарегистрировать(активировать пользователя) во внешней системе
	 */
	Boolean isRegistrationEnable(Office office) throws RemoteException;

	/**
	 * @return true - обязательно подписание договора перед подключением
	 */
	Boolean isAgreementSignMandatory(Office office) throws RemoteException;

	/**
	 * @return true - необходимо расторгнуть договор перед удалением активного клиента
	 */
	Boolean isNeedAgrementCancellation(Office office) throws RemoteException;

	/**
	 * @return true - доступно получение курсов валют.
	 */
	Boolean isCurrencyRateAvailable(Office office) throws RemoteException;

	/**
	 * @return true - шлюз подерживает комиссии.
	 */
	Boolean isPaymentCommissionAvailable(Office office) throws RemoteException;

	/**
	 * @return true - возможно получение рабочего календаря. (по сути, реализован ли CalendarGateService в шлюзе)
	 */
	Boolean isCalendarAvailable(Office office) throws RemoteException;		
}
