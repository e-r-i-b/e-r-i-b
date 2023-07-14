package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetCardsByPhoneCacheEntry;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.sql.rowset.CachedRowSet;

/**
 * User: Moshenko
 * Date: 26.08.13
 * Time: 16:28
 * Хранимая процедура возвращает информацию о всех платёжных и информационных картах используемых в подключениях к МБК для заданного входным параметром телефона
 * Если нет подключений или ресурсов то возвращает пустой список
 */
public class GetCardNumbersByPhoneNumberJDBCAction extends GetCardNumbersByPhoneJDBCActionBase
{

	public GetCardNumbersByPhoneNumberJDBCAction(String phone)
	{
		super(phone);
	}

	@Override
	protected String getProcedureName()
	{
		return "ERMB_GetCardsByPhone";
	}

}
