package com.rssl.phizicgate.mobilebank.csa;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.mobilebank.CachedJDBCAction;
import com.rssl.phizicgate.mobilebank.StandInUtils;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetClientByCardNumberCacheEntry;
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
 * ��������� ���������� � ������������ �� ������ �����:
 * [dbo].[mb_WWW_GetClientByCardNumber]
 * @CardNumber VARCHAR(20),                                 --����� �����
 * @FirstName VARCHAR(100) OUTPUT,                          --���
 * @FathersName VARCHAR(100) OUTPUT,                        --��������
 * @LastName VARCHAR(100) OUTPUT,                           --�������
 * @RegNumber VARCHAR(20) OUTPUT,                           --���������� ������
 * @BirthDate DATE OUTPUT,                                  --���� ��������
 * @CbCode VARCHAR(20) OUTPUT,                              --������� � ������� ������ ����� (������ ������)
 * @AuthIdt VARCHAR(10) OUTPUT,                             --����� ����
 * @ContrStatus INT  OUTPUT,                                --c����� ���������
 * @AddInfoCn INT OUTPUT,                                   --������� ��������=1/�������������� �����=2
 * @strErrDescr VARCHAR(500)   OUTPUT                       --�������� ������
 */

public class GetClientByCardNumberJDBCAction extends CachedJDBCAction<MBUserInfo, GetClientByCardNumberCacheEntry>
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final int DATA_NOT_FOUND_RET_CODE = -100; // ������ �� �������
	private static final int NOT_SBRF_CARD_RET_CODE = 50014; // ����� �� ���������

	private String cardNumber;
	private static final String MB_WWW_GET_CLIENT_BY_CARD_NUMBER = "mb_WWW_GetClientByCardNumber";
	private static final int CARD_NUMBER_PARAMETER_INDEX = 2;
	private static final int FIRSTNAME_PARAMETER_INDEX = 3;
	private static final int FATHERS_NAME_PARAMETER_INDEX = 4;
	private static final int LAST_NAME_PARAMETER_INDEX = 5;
	private static final int REG_NUMBER_PARAMETER_INDEX = 6;
	private static final int BIRTH_DATE_PARAMETER_INDEX = 7;
	private static final int CB_CODE_PARAMETER_INDEX = 8;
	private static final int AUTH_IDT_PARAMETER_INDEX = 9;
	private static final int CONTR_STATUS_PARAMETER_INDEX = 10;
	private static final int ADD_INFO_CN_PARAMETER_INDEX = 11;
	private static final int STR_ERR_DESCR_PARAMETER_INDEX = 12;

	/**
	 * �����������
	 * @param cardNumber ����� ����� ������������.
	 */
	public GetClientByCardNumberJDBCAction(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public GetClientByCardNumberCacheEntry doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall("{? = call " + MB_WWW_GET_CLIENT_BY_CARD_NUMBER + " " +
				"(@CardNumber = ?, " +  //VARCHAR(20)  --����� �����
				"@FirstName = ?, " +   //VARCHAR(100) OUTPUT - ���
				"@FathersName = ?, " + //VARCHAR(100) OUTPUT - ��������
				"@LastName = ?, " +    //VARCHAR(100) OUTPUT - �������
				"@RegNumber = ?, " +   //VARCHAR(20) OUTPUT - ���������� ������
				"@BirthDate = ?, " +   //DATE OUTPUT - ���� ��������
				"@CbCode = ?, " +      //VARCHAR(20) OUTPUT - ������� � ������� ������ ����� (������ ������)
				"@AuthIdt = ?, " +     //VARCHAR(10) OUTPUT - ����� ����
				"@ContrStatus = ?, " + //INT OUTPUT - c����� ���������
				"@AddInfoCn = ?, " +   //INT OUTPUT - ������� ��������=1/�������������� �����=2
				"@strErrDescr = ? )}"    //VARCHAR(500) OUTPUT - �������� ������
		);

		LogThreadContext.setProcName(MB_WWW_GET_CLIENT_BY_CARD_NUMBER);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(CARD_NUMBER_PARAMETER_INDEX, cardNumber);                    // CardNumber
			cstmt.registerOutParameter(FIRSTNAME_PARAMETER_INDEX, Types.VARCHAR); // FirstName
			cstmt.registerOutParameter(FATHERS_NAME_PARAMETER_INDEX, Types.VARCHAR); // FathersName
			cstmt.registerOutParameter(LAST_NAME_PARAMETER_INDEX, Types.VARCHAR); // LastName
			cstmt.registerOutParameter(REG_NUMBER_PARAMETER_INDEX, Types.VARCHAR); // RegNumber
			cstmt.registerOutParameter(BIRTH_DATE_PARAMETER_INDEX, Types.DATE);    // BirthDate
			cstmt.registerOutParameter(CB_CODE_PARAMETER_INDEX, Types.VARCHAR); // CbCode
			cstmt.registerOutParameter(AUTH_IDT_PARAMETER_INDEX, Types.VARCHAR); // AuthIdt
			cstmt.registerOutParameter(CONTR_STATUS_PARAMETER_INDEX, Types.INTEGER); // ContrStatus
			cstmt.registerOutParameter(ADD_INFO_CN_PARAMETER_INDEX, Types.INTEGER); // AddInfoCn
			cstmt.registerOutParameter(STR_ERR_DESCR_PARAMETER_INDEX, Types.VARCHAR); // strErrDescr
			LogThreadContext.setProcName(MB_WWW_GET_CLIENT_BY_CARD_NUMBER);
			cstmt.execute();
			return processResult(cstmt);
		}
		catch (SQLException e)
		{
			LOG.error("���� ��� ���������� ������� � ���� ������. " +
					  "�����: " + this.getClass().getName() + ". " +
					  "��� ������: " + e.getErrorCode() + ". " +
					  "���������: "  + e.getMessage(), e);
			throw e;
		}
		finally {
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	@Override
	protected MBUserInfo processResult(GetClientByCardNumberCacheEntry value) throws SystemException
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
	protected GetClientByCardNumberCacheEntry getDbCached() throws SystemException
	{
		return cacheService.getClientByCardNumberCacheEntry(cardNumber);
	}

	@Override
	protected GetClientByCardNumberCacheEntry getAppServerCached() throws SystemException
	{
		Cache cache = CacheProvider.getCache(GetClientByCardNumberCacheEntry.CACHE_NAME);
		Element element = cache.get(cardNumber);
		if (element == null)
		{
			return null;
		}
		else
		{
			return (GetClientByCardNumberCacheEntry) element.getObjectValue();
		}
	}

	private GetClientByCardNumberCacheEntry processResult(CallableStatement cstmt) throws SQLException, SystemException
	{
		int retCode = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(retCode);
		StandInUtils.checkStandInAndThrow(retCode);
		if (retCode == DATA_NOT_FOUND_RET_CODE || retCode == NOT_SBRF_CARD_RET_CODE)
		{
			return null;
		}
		if (retCode != 0)
		{
			throw new SystemException(cstmt.getString(STR_ERR_DESCR_PARAMETER_INDEX));
		}

		GetClientByCardNumberCacheEntry result = new GetClientByCardNumberCacheEntry();
		result.setFirstName(cstmt.getString(FIRSTNAME_PARAMETER_INDEX));
		result.setFathersName(cstmt.getString(FATHERS_NAME_PARAMETER_INDEX));
		result.setLastName(cstmt.getString(LAST_NAME_PARAMETER_INDEX));
		result.setRegNumber(cstmt.getString(REG_NUMBER_PARAMETER_INDEX));
		result.setBirthDate(DateHelper.toCalendar(cstmt.getDate(BIRTH_DATE_PARAMETER_INDEX)));
		result.setCbCode(cstmt.getString(CB_CODE_PARAMETER_INDEX));
		result.setAuthIdt(cstmt.getString(AUTH_IDT_PARAMETER_INDEX));
		result.setContrStatus(cstmt.getInt(CONTR_STATUS_PARAMETER_INDEX));
		result.setAddInfoCn(cstmt.getInt(ADD_INFO_CN_PARAMETER_INDEX));
		result.setCardNumber(cardNumber);
		return result;
	}
}