package com.rssl.phizic.business.ant.csaadmin.mapping;

import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 10.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * Логин для ЦСА Админ
 */

@SuppressWarnings("ALL")
public class CSAAdminLogin
{
	private Long id;
	private String name;
	private String password;
	private CSAAdminAccessScheme accessScheme;
	private Long nodeId;
	private Calendar lastUpdateDate;
	private boolean deleted;
	private long wrongLoginAttempts;
	private Calendar passwordExpireDate;

	/**
	 * конструктор
	 */
	public CSAAdminLogin(){}

	/**
	 * конструктор
	 * @param name логин (он же пароль)
	 * @param accessScheme схема прав
	 */
	public CSAAdminLogin(String name, CSAAdminAccessScheme accessScheme)
	{
		this.name = name;
		CryptoService cryptoService = SecurityFactory.cryptoService();
		this.password = cryptoService.hash(name);
		this.accessScheme = accessScheme;
		this.lastUpdateDate = Calendar.getInstance();
		this.passwordExpireDate = Calendar.getInstance();
	}
}
