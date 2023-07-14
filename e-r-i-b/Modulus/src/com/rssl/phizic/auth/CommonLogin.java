package com.rssl.phizic.auth;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.common.types.ApplicationType;

import java.util.List;
import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface CommonLogin extends Serializable, ConfirmableObject, Entity
{
	String getUserId();
	String getCsaUserId();
	String getLastIpAddress();

	List<LoginBlock> getBlocks();

	/**
	 * @return количество неверных попыток логина
	 */
	public long getWrongLoginAttempts();

	// Дата последнего удачного входа
	Calendar getLastLogonDate();

	/**
	 * @return Номер карты последнего удачного входа (или null) 
	 */
	String getLastLogonCardNumber();

	/**
	 * @return Код территориального банка, к которому привязана карта, по которой был совершен последний удачный вход (или null)
	 */
	String getLastLogonCardDepartment();

	/**
	 * @return возвращает true, если у клиента есть подключение к Мобильному банку
	 * (по платёжной карте либо по информационной)
	 */
	boolean isMobileBankConnected();

    /**
     * Признак того что вход является первым
     * @return
     */
    boolean isFirstLogin();

	// Дата текущего входа в систему
	Calendar getLogonDate();

	/**
	 * @return код ОСБ банка, к которому привязана карта, по которой был совершен последний удачный вход (или null)
	 */
	String getLastLogonCardOSB();
	
	/**
	 * @return код ВСП банка, к которому привязана карта, по которой был совершен последний удачный вход (или null)
	 */
	String getLastLogonCardVSP();

	/**
	 * @return код TB банка, к которому привязана карта, по которой был совершен последний удачный вход (или null)
	 */
	String getLastLogonCardTB();

	boolean isDeleted();
}
