package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;

/**
 * Справочник банков
 *
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 24.10.2005
 * Time: 20:56:16
 */
public abstract class Bank extends DictionaryRecordBase implements DictionaryRecord, MultiBlockDictionaryRecord
{
	//Внешний идентификатор
    private Comparable synchKey;
	//Наименование
    private String     name;
	//Местонахождение
    private String     place;
	//Корр.счет
    private String     account;

    public Comparable getSynchKey ()
    {
        return synchKey;
    }

	public void setSynchKey ( Comparable synchKey )
    {
        this.synchKey=synchKey;
    }

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name=name;
    }

    public String getPlace ()
    {
        return place;
    }

    public void setPlace ( String place )
    {
        this.place=place;
    }

    public String getAccount ()
    {
        return account;
    }

    public void setAccount ( String account )
    {
        this.account=account;
    }

	public String getMultiBlockRecordId()
	{
		return synchKey.toString();
	}
}
