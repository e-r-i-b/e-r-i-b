package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.LoginBlock;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.DateHelper;
import net.sf.cglib.core.CollectionUtils;
import net.sf.cglib.core.Transformer;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 14.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ќбертка логина
 */

public class LoginWrapper implements Login
{
	private static final Transformer LOGIN_BLOCK_TRANSFORMER = new Transformer()
	{
		public Object transform(Object value)
		{
			return new LoginBlockWrapper((LoginBlock) value);
		}
	};

	private BankLogin login;
	private List<com.rssl.phizic.gate.login.LoginBlock> blocks;
	private String userId;
	private String password;

	/**
	 * конструктор
	 * @param login оборачиваемый логин
	 */
	public LoginWrapper(BankLogin login)
	{
		this.login = login;
	}

	public Long getId()
	{
		if (login == null)
			return null;
		return login.getId();
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserId()
	{
		if (login == null)
			return userId;
		return login.getUserId();
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<com.rssl.phizic.gate.login.LoginBlock> getBlocks()
	{
		if (login == null)
			return Collections.emptyList();

		if (blocks != null)
			return blocks;

		//блокировка по длительной неактивности
		List<LoginBlock> loginBlocks = login.getBlocks();
		Calendar lastLogonDate = login.getLastLogonDate();
		Calendar today = Calendar.getInstance();
		long timeToBlock = ConfigFactory.getConfig(SecurityConfig.class).getTimeToBlock();
		if(timeToBlock > 0 && lastLogonDate != null)
		{
			if(DateHelper.diff(today, lastLogonDate)  > timeToBlock)
			{
				LoginBlock longInactivityBlock = new LoginBlock();
				longInactivityBlock.setLogin(login);
				longInactivityBlock.setBlockedFrom(today.getTime());
				longInactivityBlock.setReasonType(BlockingReasonType.longInactivity);
				longInactivityBlock.setReasonDescription(Constants.BLOCK_BY_INACTIVITY_REASON_DESCR);
				longInactivityBlock.setEmployee(null);
				loginBlocks.add(longInactivityBlock);
			}
		}

		//noinspection unchecked
		blocks = CollectionUtils.transform(loginBlocks, LOGIN_BLOCK_TRANSFORMER);
		return blocks;
	}

	public Calendar getLastSynchronizationDate()
	{
		return login.getLastSynchronizationDate();
	}

	BankLogin getLogin()
	{
		return login;
	}
}
