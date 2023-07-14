package com.rssl.phizicgate.mobilebank.csa;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.mobilebank.CachedJDBCAction;
import com.rssl.phizicgate.mobilebank.StandInUtils;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetClientByLoginCacheEntry;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author krenev
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * Получение информации о пользователе по логину iPAS:
 * [dbo].[mb_WWW_GetClientByLogin]
 * @AuthIdt VARCHAR(10),                                  --логин СБОЛ
 * @FirstName VARCHAR(100) OUTPUT,                        --имя
 * @FathersName VARCHAR(100) OUTPUT,                      --отчество
 * @LastName VARCHAR(100) OUTPUT,                         --фамилия
 * @RegNumber VARCHAR(20) OUTPUT,                         --паспортные данные
 * @BirthDate DATE OUTPUT,                                --дата рождения
 * @CbCode VARCHAR(20) OUTPUT,                            --тербанк в котором выдана карта (полный формат)
 * @CardNumber VARCHAR(20) OUTPUT,                        --номер карты
 * @ContrStatus INT  OUTPUT,                              --cтатус контракта
 * @AddInfoCn INT OUTPUT,                                 --признак основная=1/дополнительная карта=2
 * @strErrDescr VARCHAR(500) OUTPUT                       --описание ошибки
 */

public class GetClientByLoginJDBCAction extends CachedJDBCAction<MBUserInfo, GetClientByLoginCacheEntry>
{
	private String login;
	private static final int DATA_NOT_FOUND_RET_CODE = -100;
	private static final String MB_WWW_GET_CLIENT_BY_LOGIN = "mb_WWW_GetClientByLogin";

	private static final int AUTH_IDT_PARAMETER_INDEX = 2;
	private static final int FIRST_NAME_PARAMETER_INDEX = 3;
	private static final int FATHERS_NAME_PARAMETER_INDEX = 4;
	private static final int LAST_NAME_PARAMETER_INDEX = 5;
	private static final int REG_NUMBER_PARAMETER_INDEX = 6;
	private static final int BIRTH_DATE_PARAMETER_INDEX = 7;
	private static final int CB_CODE_PARAMETER_INDEX = 8;
	private static final int CARD_NUMBER_PARAMETER_INDEX = 9;
	private static final int CONTR_STATUS_PARAMETER_INDEX = 10;
	private static final int ADD_INFO_CN_PARAMETER_INDEX = 11;
	private static final int STR_ERR_DESCR_PARAMETER_INDEX = 12;

	/**
	 * Конструктор
	 * @param login 10цифровой логин пользователя в iPAS.
	 */
	public GetClientByLoginJDBCAction(String login)
	{
		this.login = login;
	}

	public GetClientByLoginCacheEntry doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall("{? = call " + MB_WWW_GET_CLIENT_BY_LOGIN + " " +
				"(@AuthIdt = ?, " +     //VARCHAR(10) - логин СБОЛ
				"@FirstName = ?, " +   //VARCHAR(100) OUTPUT - имя
				"@FathersName = ?, " + //VARCHAR(100) OUTPUT - отчество
				"@LastName = ?, " +    //VARCHAR(100) OUTPUT - фамилия
				"@RegNumber = ?, " +   //VARCHAR(20) OUTPUT - паспортные данные
				"@BirthDate = ?, " +   //DATE OUTPUT - дата рождения
				"@CbCode = ?, " +      //VARCHAR(20) OUTPUT - тербанк в котором выдана карта (полный формат)
				"@CardNumber = ?, " +  //VARCHAR(20) OUTPUT - номер карты
				"@ContrStatus = ?, " + //INT OUTPUT - cтатус контракта
				"@AddInfoCn = ?, " +   //INT OUTPUT - признак основная=1/дополнительная карта=2
				"@strErrDescr = ? )}"    //VARCHAR(500) OUTPUT - описание ошибки
		);

		LogThreadContext.setProcName(MB_WWW_GET_CLIENT_BY_LOGIN);
		try
		{

			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(AUTH_IDT_PARAMETER_INDEX, login);                    // AuthIdt
			cstmt.registerOutParameter(FIRST_NAME_PARAMETER_INDEX, Types.VARCHAR); // FirstName
			cstmt.registerOutParameter(FATHERS_NAME_PARAMETER_INDEX, Types.VARCHAR); // FathersName
			cstmt.registerOutParameter(LAST_NAME_PARAMETER_INDEX, Types.VARCHAR); // LastName
			cstmt.registerOutParameter(REG_NUMBER_PARAMETER_INDEX, Types.VARCHAR); // RegNumber
			cstmt.registerOutParameter(BIRTH_DATE_PARAMETER_INDEX, Types.DATE);    // BirthDate
			cstmt.registerOutParameter(CB_CODE_PARAMETER_INDEX, Types.VARCHAR); // CbCode
			cstmt.registerOutParameter(CARD_NUMBER_PARAMETER_INDEX, Types.VARCHAR); // CardNumber
			cstmt.registerOutParameter(CONTR_STATUS_PARAMETER_INDEX, Types.INTEGER); // ContrStatus
			cstmt.registerOutParameter(ADD_INFO_CN_PARAMETER_INDEX, Types.INTEGER); // AddInfoCn
			cstmt.registerOutParameter(STR_ERR_DESCR_PARAMETER_INDEX, Types.VARCHAR); // strErrDescr
			LogThreadContext.setProcName(MB_WWW_GET_CLIENT_BY_LOGIN);
			cstmt.execute();
			return processResult(cstmt);
		} finally {
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	@Override
	protected MBUserInfo processResult(GetClientByLoginCacheEntry value) throws SystemException
	{
		MBUserInfo result = new MBUserInfo();
		result.setFirstname(value.getFirstName());
		result.setPatrname(value.getFathersName());
		result.setSurname(value.getLastName());
		result.setPassport(value.getRegNumber());
		result.setBirthdate(value.getBirthDate());
		result.setCbCode(value.getCbCode());
		result.setUserId(value.getAuthIdt());
		result.setActiveCard(Utils.isCardActive(value.getContrStatus()));
		result.setMainCard(Utils.isMainCard(value.getAddInfoCn()));
		result.setCardNumber(value.getCardNumber());
		return result;
	}

	@Override
	protected GetClientByLoginCacheEntry getDbCached() throws SystemException
	{
		return cacheService.getClientByLoginCacheEntry(login);
	}

	@Override
	protected GetClientByLoginCacheEntry getAppServerCached() throws SystemException
	{
		Cache cache = CacheProvider.getCache(GetClientByLoginCacheEntry.CACHE_NAME);
		Element element = cache.get(login);
		if (element == null)
		{
			return null;
		}
		else
		{
			return (GetClientByLoginCacheEntry) element.getObjectValue();
		}
	}

	private GetClientByLoginCacheEntry processResult(CallableStatement cstmt) throws SQLException, SystemException
	{
		int rc = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(rc);
		StandInUtils.checkStandInAndThrow(rc);
		if (rc == DATA_NOT_FOUND_RET_CODE)
		{
			return null;
		}

		if (rc != 0)
		{
			throw new SystemException(cstmt.getString(STR_ERR_DESCR_PARAMETER_INDEX));
		}

		GetClientByLoginCacheEntry result = new GetClientByLoginCacheEntry();
		result.setFirstName(cstmt.getString(FIRST_NAME_PARAMETER_INDEX));
		result.setFathersName(cstmt.getString(FATHERS_NAME_PARAMETER_INDEX));
		result.setLastName(cstmt.getString(LAST_NAME_PARAMETER_INDEX));
		result.setRegNumber(cstmt.getString(REG_NUMBER_PARAMETER_INDEX));
		result.setBirthDate(DateHelper.toCalendar(cstmt.getDate(BIRTH_DATE_PARAMETER_INDEX)));
		result.setCbCode(cstmt.getString(CB_CODE_PARAMETER_INDEX));
		result.setCardNumber(cstmt.getString(CARD_NUMBER_PARAMETER_INDEX));
		result.setContrStatus(cstmt.getInt(CONTR_STATUS_PARAMETER_INDEX));
		result.setAddInfoCn(cstmt.getInt(ADD_INFO_CN_PARAMETER_INDEX));
		result.setAuthIdt(login);
		return result;
	}
}
