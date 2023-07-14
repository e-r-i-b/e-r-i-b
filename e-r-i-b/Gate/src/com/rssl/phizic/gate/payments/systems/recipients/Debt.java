package com.rssl.phizic.gate.payments.systems.recipients;

import java.util.Calendar;
import java.util.List;
import java.io.Serializable;

/**
 * ƒолг клиента
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface Debt extends Serializable
{
	/**
	 * ѕолучить код задолженности.
	 * @return код задолженности.
	 */
	String getCode();

	/**
	 * ѕолучить описание задолженности
	 * @return описание задолженности
	 */
	String getDescription();

	/**
	 * »змен€ема ли задолженость
	 * @return true - при оплате долга сумма должна быть равна сумме долга. false - сумма может быть любой. 
	 */
	boolean isFixed();

	/**
	 * ѕолучить период(дату), на которую выставлена задолженность
	 * @return период оплаты.
	 */
	Calendar getPeriod();

	/**
	 * ƒата последнего погашени€
	 * @return ƒата последнего погашени€
	 */
	Calendar getLastPayDate();

	/**
	 * ѕолучить список параметров задолженности.
	 * @return список параметров задолженности.
	 */
	List<DebtRow> getRows();

	/**
	 * ѕолучение номера счета задолженности
	 * @return Ќомер счета задолженности
	 */
	String getAccountNumber();
}