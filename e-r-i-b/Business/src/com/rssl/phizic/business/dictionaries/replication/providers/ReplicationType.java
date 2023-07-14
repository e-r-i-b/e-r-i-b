package com.rssl.phizic.business.dictionaries.replication.providers;

/**
 * @author khudyakov
 * @ created 19.02.2011
 * @ $Author$
 * @ $Revision$
 */
public enum ReplicationType
{
	ESB,        //загрузка шинного поставщика услуг
	TB,         //загрузка поставщика услуг
	DEFAULT,    //обычная загрузка поставщиков услуг (через механизм загрузки/выгрузки)
	REMOVE;     //репликация удаления

}
