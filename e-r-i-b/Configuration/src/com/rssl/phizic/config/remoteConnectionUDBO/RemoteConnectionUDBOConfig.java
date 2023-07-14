package com.rssl.phizic.config.remoteConnectionUDBO;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.*;

/**
 * ������ ��� ������ � �������� ����������� ��������� ����
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class RemoteConnectionUDBOConfig extends Config
{
	public static final String ALLOWED_TB_KEY = "com.rssl.iccs.remoteConnectionUDBO.allowedTB";
	public static final String TIME_COLD_PERIOD_KEY = "com.rssl.iccs.remoteConnectionUDBO.timeColdPeriod";
	public static final String WORK_WITHOUT_UDBO_KEY = "com.rssl.iccs.remoteConnectionUDBO.workWithoutUDBO";
	public static final String CANCEL_CONNECT_UDBO_MESSAGE_TITLE_KEY = "com.rssl.iccs.remoteConnectionUDBO.cancelConnectUDBOMessage.title";
	public static final String CANCEL_CONNECT_UDBO_MESSAGE_TEXT_KEY = "com.rssl.iccs.remoteConnectionUDBO.cancelConnectUDBOMessage.text";
	public static final String CANCEL_ACCEPT_INFO_UDBO_MESSAGE_TITLE_KEY = "com.rssl.iccs.remoteConnectionUDBO.cancelAcceptInfoUDBOMessage.title";
	public static final String CANCEL_ACCEPT_INFO_UDBO_MESSAGE_TEXT_KEY = "com.rssl.iccs.remoteConnectionUDBO.cancelAcceptInfoUDBOMessage.text";
	public static final String ALL_CONNECTION_UDBO_NEED_WAIT_CONFIRM_STATE_KEY = "com.rssl.iccs.remoteConnectionUDBO.allConnectionUDBONeedWaitConfirmState";
	public static final String AGE_CLIENT_FOR_NEED_WAIT_CONFIRM_STATE_KEY = "com.rssl.iccs.remoteConnectionUDBO.ageClientForNeedWaitConfirmState";
	public static final String MONEY_CLIENT_FOR_NEED_WAIT_CONFIRM_STATE_KEY = "com.rssl.iccs.remoteConnectionUDBO.moneyClientForNeedWaitConfirmState";
	public static final String COLD_PERIOD_INFO_MESSAGE_KEY                 = "com.rssl.iccs.remoteConnectionUDBO.�oldPeriodInfo.message";
	public static final String NEED_GO_VSP_MESSAGE_KEY                      = "com.rssl.iccs.remoteConnectionUDBO.needGoVSP.message";
	public static final String ACCEPT_CONNECT_UDBO_MESSAGE_KEY               = "com.rssl.iccs.remoteConnectionUDBO.acceptConnectUDBOMessage";
	public static final String TERM_TEXT_FOR_CONNECTION_UDBO_KEY            = "com.rssl.iccs.remoteConnectionUDBO.termText";
	public static final String SMS_MESSAGE_CONNECTION_UDBO_SUCCESS_KEY      = "com.rssl.iccs.remoteConnectionUDBO.smsMessageConnectionUDBOSuccess";
	//����� �� �������� � �������� ������
	public static final String COLD_PERIOD_OPERATION_OPEN_ACCOUNT_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.openAccount";  //�������� �������
	public static final String COLD_PERIOD_OPERATION_OPEN_IMACCOUNT_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.openIMAccount";
	public static final String COLD_PERIOD_OPERATION_CARD_TO_IMACCOUNT_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.cardToIMAccount";
	public static final String COLD_PERIOD_OPERATION_CARD_TO_ACCOUNT_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.cardToAccount";
	public static final String COLD_PERIOD_OPERATION_CARD_TO_CARD_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.cardToCard";
	public static final String COLD_PERIOD_OPERATION_ACCOUNT_TO_CARD_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.accountToCard";
	public static final String COLD_PERIOD_OPERATION_IMACCOUNT_TO_CARD_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.IMAccountToCard";
	public static final String COLD_PERIOD_OPERATION_TO_OTHER_BANK_ACCOUNT_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toOtherBankAccount";
	public static final String COLD_PERIOD_OPERATION_TO_OTHER_BANK_CARD_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toOtherBankCard";
	public static final String COLD_PERIOD_OPERATION_TO_OUR_BANK_CARD_PHONE_NUMBER_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toOtherBankCardPhoneNumber";
	public static final String COLD_PERIOD_OPERATION_TO_JUR_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toJur";
	public static final String COLD_PERIOD_OPERATION_TO_DEPO_KEY = "com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toDepo";


	private static final String OPERATION_OPEN_ACCOUNT_NAME = "AccountOpeningClaim";
	private static final String OPERATION_OPEN_IMACCOUNT_NAME = "IMAOpeningClaim";
	private static final String OPERATION_TO_JUR_NAME = "JurPayment";
	private static final String OPERATION_TO_DEPO_NAME = "SecuritiesTransferClaim";

	private static final Map<String, String> serviceMap;

	private String allowedTB; //��, � ������� ��������� ����������� ����
	private int timeColdPeriod;  //����������������� � ����� ��������� �������
	private boolean workWithoutUDBO; //���������� ������ � ���� ��� ������ �� ����������� ����
	private String cancelConnectUDBOMessageTitle; //��������� ��������� ��� ������ �� ����������� ����
	private String cancelConnectUDBOMessageText; //����� ��������� ��� ������ �� ����������� ����
	private String cancelAcceptInfoUDBOMessageTitle; //��������� ��������� ��� ������ �� ������������� ���������� ��� ����������� ����
	private String cancelAcceptInfoUDBOMessageText; //����� ��������� ��� ������ �� ������������� ���������� ��� ����������� ����
	private List<String> coldPeriodBannedServiceKeys = new ArrayList<String>();    //������ �������� ����������� ��� ��������� �������
	private boolean allConnectionUDBONeedWaitConfirmState;  //������������� ���� ������ � ����
	private int ageClientForNeedWaitConfirmState;   //�������� ������������ ��� ������������� ������������� ������ �� ����������� ���� � ��: ������� �������;
	private long moneyClientForNeedWaitConfirmState;   //�������� ������������ ��� ������������� ������������� ������ �� ����������� ���� � ��: ������ �������;
	private String coldPeriodInfoMessage;   //����� ���������� ������� � ���, ��� �� �������� � �������� �������
	private String acceptConnectUdboMessage;   //����� �������� �������� � ��������� ��������
	private String termTextForConnectionUdbo;   //����� ������� �������� ���������� ����
	private String smsMessageConnectionUDBOSuccess;

	private boolean coldPeriodAvailableCardToIMAccount; //����������� �� ����� ��������� ������ �������� ����� -> ��� ����
	private boolean coldPeriodAvailableCardToAccount;   //����������� �� ����� ��������� ������ �������� ����� -> ����
	private boolean coldPeriodAvailableCardToCard;      //����������� �� ����� ��������� ������ �������� ����� -> �����
	private boolean coldPeriodAvailableAccountToCard;   //����������� �� ����� ��������� ������ �������� ���� -> �����
	private boolean coldPeriodAvailableIMAccountToCard; //����������� �� ����� ��������� ������ �������� ��� ���� -> �����
	private boolean coldPeriodAvailableToOtherBankCard; //����������� �� ����� ��������� ������ �������� �� ����� � ������ �����
	private boolean coldPeriodAvailableToOtherBankAccount; //����������� �� ����� ��������� ������ �������� �� ���� � ������ �����
	private boolean coldPeriodAvailableToOurBankCardPhoneNumber; //����������� �� ����� ��������� ������ �������� �� ����, ����� ��� �� ������ �������� � ����� �����

	static
	{
		serviceMap = new HashMap<String, String>();
		serviceMap.put(COLD_PERIOD_OPERATION_OPEN_ACCOUNT_KEY, OPERATION_OPEN_ACCOUNT_NAME);
		serviceMap.put(COLD_PERIOD_OPERATION_OPEN_IMACCOUNT_KEY, OPERATION_OPEN_IMACCOUNT_NAME);
		serviceMap.put(COLD_PERIOD_OPERATION_TO_JUR_KEY, OPERATION_TO_JUR_NAME);
		serviceMap.put(COLD_PERIOD_OPERATION_TO_DEPO_KEY, OPERATION_TO_DEPO_NAME);
	}

	/**
	 * @param reader - �����
	 */
	public RemoteConnectionUDBOConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		allowedTB = getProperty(ALLOWED_TB_KEY, "");
		timeColdPeriod = getIntProperty(TIME_COLD_PERIOD_KEY, 0);
		workWithoutUDBO = getBoolProperty(WORK_WITHOUT_UDBO_KEY, true);
		cancelConnectUDBOMessageTitle = getProperty(CANCEL_CONNECT_UDBO_MESSAGE_TITLE_KEY);
		cancelConnectUDBOMessageText = getProperty(CANCEL_CONNECT_UDBO_MESSAGE_TEXT_KEY);

		cancelAcceptInfoUDBOMessageTitle = getProperty(CANCEL_ACCEPT_INFO_UDBO_MESSAGE_TITLE_KEY);
		cancelAcceptInfoUDBOMessageText = getProperty(CANCEL_ACCEPT_INFO_UDBO_MESSAGE_TEXT_KEY);

		allConnectionUDBONeedWaitConfirmState = getBoolProperty(ALL_CONNECTION_UDBO_NEED_WAIT_CONFIRM_STATE_KEY, false);
		ageClientForNeedWaitConfirmState = getIntProperty(AGE_CLIENT_FOR_NEED_WAIT_CONFIRM_STATE_KEY, 0);
		moneyClientForNeedWaitConfirmState = getLongProperty(MONEY_CLIENT_FOR_NEED_WAIT_CONFIRM_STATE_KEY, 0);
		coldPeriodInfoMessage = getProperty(COLD_PERIOD_INFO_MESSAGE_KEY);
		acceptConnectUdboMessage = getProperty(ACCEPT_CONNECT_UDBO_MESSAGE_KEY);
		termTextForConnectionUdbo = getProperty(TERM_TEXT_FOR_CONNECTION_UDBO_KEY);
		smsMessageConnectionUDBOSuccess = getProperty(SMS_MESSAGE_CONNECTION_UDBO_SUCCESS_KEY);

		coldPeriodAvailableCardToIMAccount = getBoolProperty(COLD_PERIOD_OPERATION_CARD_TO_IMACCOUNT_KEY, false);
		coldPeriodAvailableCardToAccount = getBoolProperty(COLD_PERIOD_OPERATION_CARD_TO_ACCOUNT_KEY, false);
		coldPeriodAvailableCardToCard = getBoolProperty(COLD_PERIOD_OPERATION_CARD_TO_CARD_KEY, false);
		coldPeriodAvailableAccountToCard = getBoolProperty(COLD_PERIOD_OPERATION_ACCOUNT_TO_CARD_KEY, false);
		coldPeriodAvailableIMAccountToCard = getBoolProperty(COLD_PERIOD_OPERATION_IMACCOUNT_TO_CARD_KEY, false);
		coldPeriodAvailableToOtherBankCard = getBoolProperty(COLD_PERIOD_OPERATION_TO_OTHER_BANK_CARD_KEY, false);
		coldPeriodAvailableToOtherBankAccount = getBoolProperty(COLD_PERIOD_OPERATION_TO_OTHER_BANK_ACCOUNT_KEY, false);
		coldPeriodAvailableToOurBankCardPhoneNumber = getBoolProperty(COLD_PERIOD_OPERATION_TO_OUR_BANK_CARD_PHONE_NUMBER_KEY, false);

		coldPeriodBannedServiceKeys.clear();
		for (String key : serviceMap.keySet())
		{
			if (!getBoolProperty(key, false))
			{
				coldPeriodBannedServiceKeys.add(serviceMap.get(key));
			}
		}
	}

	/**
	 * @return ��, � ������� �������� ����������� ���������� ����
	 */
	public String getAllowedTB()
	{
		return allowedTB;
	}

	/**
	 * @return ������� ���������� ������ � ���� ��� ������ �� ���������� ����
	 */
	public boolean isWorkWithoutUDBO()
	{
		return workWithoutUDBO;
	}

	public int getTimeColdPeriod()
	{
		return timeColdPeriod;
	}

	/**
	 * @return ��������� ��������� ��� ������ �� ����������� ����
	 */
	public String getCancelConnectUDBOMessageTitle()
	{
		return cancelConnectUDBOMessageTitle;
	}

	/**
	 * @return ����� ��������� ��� ������ �� ����������� ����
	 */
	public String getCancelConnectUDBOMessageText()
	{
		return cancelConnectUDBOMessageText;
	}

	/**
	 * @return ��������� ��������� ��� ������ �� ������������� ���������� ��� ����������� ����
	 */
	public String getCancelAcceptInfoUDBOMessageTitle()
	{
		return cancelAcceptInfoUDBOMessageTitle;
	}

	/**
	 * @return ����� ��������� ��� ������ �� ������������� ���������� ��� ����������� ����
	 */
	public String getCancelAcceptInfoUDBOMessageText()
	{
		return cancelAcceptInfoUDBOMessageText;
	}

	/**
	 * @return ��������������� �������� � �������� ������
	 */
	public List<String> getColdPeriodBannedServiceKeys()
	{
		return Collections.unmodifiableList(coldPeriodBannedServiceKeys);
	}

	/**
	 * @return ��������� �������������� ������������� ��� ���� ��������� ���������� ����
	 */
	public boolean isAllConnectionUDBONeedWaitConfirmState()
	{
		return allConnectionUDBONeedWaitConfirmState;
	}

	/**
	 * @return - �� ������� ��� ��������� �������������� ������������� ��� ��������� ���������� ����
	 */
	public int getAgeClientForNeedWaitConfirmState()
	{
		return ageClientForNeedWaitConfirmState;
	}

	/**
	 * @return - ������� ������ ���� �����, ���� ��������� �������������� ������������� ��� ��������� ���������� ����
	 */
	public long getMoneyClientForNeedWaitConfirmState()
	{
		return moneyClientForNeedWaitConfirmState;
	}

	/**
	 * @return - ����� ���������� ������� � ���, ��� �� �������� � �������� �������
	 */
	public String getColdPeriodInfoMessage()
	{
		return coldPeriodInfoMessage;
	}

	/**
	 * @return - ����� �������� �������� � ��������� ��������
	 */
	public String getAcceptConnectUdboMessage()
	{
		return acceptConnectUdboMessage;
	}

	/**
	 * @return - ����� ������� ��������
	 */
	public String getTermTextForConnectionUdbo()
	{
		return termTextForConnectionUdbo;
	}

	/**
	 * @return - ����������� �� ����� ��������� ������ �������� ����� -> ��� ����
	 */
	public boolean isColdPeriodAvailableCardToIMAccount()
	{
		return coldPeriodAvailableCardToIMAccount;
	}

	/**
	 * @return - ����������� �� ����� ��������� ������ �������� ����� -> ����
	 */
	public boolean isColdPeriodAvailableCardToAccount()
	{
		return coldPeriodAvailableCardToAccount;
	}

	/**
	 * @return - ����������� �� ����� ��������� ������ �������� ����� -> �����
	 */
	public boolean isColdPeriodAvailableCardToCard()
	{
		return coldPeriodAvailableCardToCard;
	}

	/**
	 * @return - ����������� �� ����� ��������� ������ �������� ���� -> �����
	 */
	public boolean isColdPeriodAvailableAccountToCard()
	{
		return coldPeriodAvailableAccountToCard;
	}

	/**
	 * @return - ����������� �� ����� ��������� ������ �������� ��� ���� -> �����
	 */
	public boolean isColdPeriodAvailableIMAccountToCard()
	{
		return coldPeriodAvailableIMAccountToCard;
	}

	/**
	 * @return - ����������� �� ����� ��������� ������ �������� �� ���� � ������ �����
	 */
	public boolean isColdPeriodAvailableToOtherBankAccount()
	{
		return coldPeriodAvailableToOtherBankAccount;
	}


	/**
	 * @return - ����������� �� ����� ��������� ������ �������� ����� ���� ��� �� ������ �������� � ����� �����
	 */
	public boolean isColdPeriodAvailableToOurBankCardPhoneNumber()
	{
		return coldPeriodAvailableToOurBankCardPhoneNumber;
	}

	/**
	 * @return - ����������� �� ����� ��������� ������ �������� �� ����� � ������ �����
	 */
	public boolean isColdPeriodAvailableToOtherBankCard()
	{
		return coldPeriodAvailableToOtherBankCard;
	}

	/**
	 * @return - ��� ��������� �� �������� ���������� ����
	 */
	public String getSmsMessageConnectionUDBOSuccess()
	{
		return smsMessageConnectionUDBOSuccess;
	}
}
