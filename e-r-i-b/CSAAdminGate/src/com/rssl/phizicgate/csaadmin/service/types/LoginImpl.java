package com.rssl.phizicgate.csaadmin.service.types;

import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.csaadmin.service.generated.LoginBlockType;
import com.rssl.phizicgate.csaadmin.service.generated.LoginType;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 */

public class LoginImpl implements Login
{
	private LoginType login;
	private List<LoginBlock> blocks;

	/**
	 * конструктор
	 * @param login исходный логин
	 */
	public LoginImpl(LoginType login)
	{
		this.login = login;
		LoginBlockType[] loginBlocks = login.getBlocks();
		if (ArrayUtils.isEmpty(loginBlocks))
			blocks = Collections.emptyList();
		else
		{
			blocks = new ArrayList<LoginBlock>();
			for (LoginBlockType block : loginBlocks)
				blocks.add(fromGate(block));
		}
	}

	private static Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}

	private LoginBlock fromGate(LoginBlockType source)
	{
		LoginBlockImpl block = new LoginBlockImpl();
		block.setReasonType(source.getReasonType());
		block.setReasonDescription(source.getReasonDescription());
		block.setBlockedFrom(parseCalendar(source.getBlockedFrom()));
		block.setBlockedUntil(parseCalendar(source.getBlockedUntil()));
		return block;
	}

	public Long getId()
	{
		return login.getId();
	}

	public void setUserId(String userId)
	{
		login.setName(userId);
	}

	public String getUserId()
	{
		return login.getName();
	}

	public void setPassword(String password)
	{
		login.setPassword(password);
	}

	public String getPassword()
	{
		return login.getPassword();
	}

	public List<LoginBlock> getBlocks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return blocks;
	}

	public Calendar getLastSynchronizationDate()
	{
		return parseCalendar(login.getLastUpdateDate());
	}
}
