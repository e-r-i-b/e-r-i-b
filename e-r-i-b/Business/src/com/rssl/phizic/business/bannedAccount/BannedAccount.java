package com.rssl.phizic.business.bannedAccount;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class BannedAccount extends MultiBlockDictionaryRecordBase
{
	private Long id;                //идентификатор записи
	private String accountNumber;   //номер счета
	private String bicList;         //список БИКов записаный через ","
	private AccountBanType banType; //тип запрета на счет(физику,юрику, всем)

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getBicList()
	{
		return bicList;
	}

	public void setBicList(String bicList)
	{
		this.bicList = bicList;
	}

	public AccountBanType getBanType()
	{
		return banType;
	}

	public void setBanType(AccountBanType banType)
	{
		this.banType = banType;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<String> getBICListAsList()
	{
		if(!StringHelper.isEmpty(bicList))
			return Arrays.asList(bicList.split(","));
		else
			return new ArrayList<String>();
	}
}
