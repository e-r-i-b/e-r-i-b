package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.longoffer.LongOfferRequestHelper;

import java.rmi.RemoteException;
import java.util.*;

/**
 * @author osminin
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 * «ашлушка, дл€ заполнени€ данными ответов
 */
public class LongOfferHelper extends BaseResponseHelper
{
	private static String[][] longOffers; // массив длительных поручений с некоторыми статическими данными
	private static final Integer NUMBER = 0; // индекс пол€ номер
	private static final Integer PMT_KIND = 1; // индекс пол€ тип операции(код вида платежа)
	private static final Integer RCPT_KIND = 2; // индекс пол€ вид получател€
	private static final Integer EXE_EVENT_TYPE = 3; // индекс пол€ код событи€ исполнени€ формы
	private static final Integer SUM_TYPE = 4; // индекс пол€ код суммы платежа
	private static final Integer DEBT_OR_CARD = 5; // списание средств с карты (0), со счета (1) или заполнены оба значени€ (2)

	private static List<AccountLink> accountLinks;
	private static List<CardLink> cardLinks;
	private static List<Office> offices;

	static
	{
		longOffers = new String[][]
			{  //    number               codePmtKind             RCPTKind                exeEventType        SumType                  Debt or Card
				{"6512465398527782170", "COMMUNAL_PAY",         "COMMUNAL_REC",         "ONCE_IN_HALFYEAR", "FIXED_SUMMA",                  "0"},
				{"6512465398527782171", "INTERNAL_PAY",         "INTERNAL_REC",         "BY_CAPITAL",       "FIXED_SUMMA",                  "1"},
				{"6512465398527782172", "INTERNAL_TO_CARD_PAY", "CARD_REC",             "ONCE_IN_MONTH",    "FIXED_SUMMA",                  "1"},
				{"6512465398527782173", "IN_BRANCH_PAY",        "ENOTHER_BRANCH_REC",   "BY_PENSION",       "FIXED_SUMMA",                  "0"},
				{"6512465398527782174", "FORM143_PAY",          "OUT_REC",              "BY_PERCENT",       "PERCENT_OF_REMAIND",           "1"},
			};
	}

	private void setDefaultSource(Login login)
	{
		ExternalResourceService resourceService = new ExternalResourceService();
		OfficeGateService officeGateService = GateSingleton.getFactory().service(OfficeGateService.class);
		try
		{
			accountLinks = resourceService.getLinks(login, AccountLink.class);
		}
		catch (BusinessException e)
		{
			accountLinks = new ArrayList<AccountLink>();
		}
		catch (BusinessLogicException e)
		{
			accountLinks = new ArrayList<AccountLink>();
		}
		try
		{
			cardLinks = resourceService.getLinks(login, CardLink.class);
		}
		catch(BusinessException e)
		{
			cardLinks = new ArrayList<CardLink>();
		}
		catch (BusinessLogicException e)
		{
			cardLinks = new ArrayList<CardLink>();
		}
		try
		{
			offices = officeGateService.getAll(0,10);
		}
		catch (Exception e)
		{
			offices = new ArrayList<Office>();
		}
	}

	/**
	 * длительные поручени€ клиента
	 */
	public SvcsAcct_Type[] getSvcsAcct(Login login)
	{
		SvcsAcct_Type[] svcsAcct = new SvcsAcct_Type[longOffers.length];
		setDefaultSource(login);
		for (int i = 0; i < longOffers.length; i++)
		{
			SvcsAcct_Type svcsAcctItem = new SvcsAcct_Type();
			svcsAcctItem.setSystemId("SystemId");
			svcsAcctItem.setSvcAcctId(getSvcAcctId(i));
			svcsAcctItem.setDtStart(getStringDate(Calendar.getInstance()));
			svcsAcctItem.setDtEnd(getEndDate());
			svcsAcctItem.setCurAmt(getRandomDecimal());
			if (i%2 == 0)
				svcsAcctItem.setAcctId(getRandomAccountNumber());
			else
				svcsAcctItem.setCardNum(getRandomCardNumber());
			svcsAcctItem.setPmtKind(longOffers[i][PMT_KIND]);
			svcsAcctItem.setRcptKind(longOffers[i][RCPT_KIND]);
			svcsAcct[i] = svcsAcctItem;
		}

		return svcsAcct;
	}

	private String getEndDate()
	{
		Date date = Calendar.getInstance().getTime();
		Calendar endDate = DateHelper.toCalendar(DateHelper.add(date,1,1,1));
		return getStringDate(endDate);
	}

	private SvcsAcct_TypeSvcAcctId getSvcAcctId(int i)
	{
		SvcsAcct_TypeSvcAcctId svcAcctId = new SvcsAcct_TypeSvcAcctId();
		svcAcctId.setSvcAcctNum(Long.parseLong(longOffers[i][NUMBER]));
		svcAcctId.setSvcType(i%2 != 0);

		BankInfo_Type bankInfo = getRandomBankInfo();

		svcAcctId.setBankInfo(bankInfo);
		return svcAcctId;
	}

	//получить поручение по номеру из массива
	private String[] getLongOffer(String number)
	{
		for (String[] str: longOffers)
		{
			if (str[NUMBER].equals(number))
				return str;
		}
		return null;
	}

	/**
	 * ответ на запрос создани€ длительного поручени€ на перевод денежных средств с вклада физическому лицу
	 * @param parameters запрос
	 * @return ответ
	 */
	public IFXRs_Type createSvcAddRq(IFXRq_Type parameters)
	{
		SvcAddRq_Type svcAddRq = parameters.getSvcAddRq();
		SvcAddRs_Type svcAddRs = new SvcAddRs_Type();

		svcAddRs.setRqUID(LongOfferRequestHelper.generateUUID());
		svcAddRs.setRqTm(LongOfferRequestHelper.generateRqTm());
		svcAddRs.setOperUID(LongOfferRequestHelper.generateOUUID());

		SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
		svcAcctId.setSvcAcctNum(getRandomlong());
		svcAcctId.setBankInfo(svcAddRq.getBankInfo());

		svcAddRs.setSvcAcctId(svcAcctId);
		svcAddRs.setStatus(getStatus());

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setSvcAddRs(svcAddRs);
		return ifxRs;
	}

	/**
	 * ответ на запрос дополнительной информации по длительным поручени€м
	 */
	public IFXRs_Type createSvcAcctAudRq(IFXRq_Type parameters) throws RemoteException
	{
		SvcAcctAudRq_Type svcAcctAudRq = parameters.getSvcAcctAudRq();
		SvcAcctAudRs_Type svcAcctAudRs = new SvcAcctAudRs_Type();

		svcAcctAudRs.setRqUID(LongOfferRequestHelper.generateUUID());
		svcAcctAudRs.setRqTm(LongOfferRequestHelper.generateRqTm());
		svcAcctAudRs.setOperUID(svcAcctAudRq.getOperUID());
		svcAcctAudRs.setStatus(getStatus());
		
		SvcAcctAudRq_TypeSvcAcct[] svcAccts = svcAcctAudRq.getSvcAcct();
		int svcAcctCount = svcAccts.length;
		SvcActInfo_Type[] svcActInfo = new SvcActInfo_Type[svcAcctCount];

		for (int i = 0; i < svcAcctCount; i++)
		{
			SvcActInfo_Type svcActInfoItem = new SvcActInfo_Type();
			Status_Type status = getStatus();
			svcActInfoItem.setStatus(status);
			if (status.getStatusCode() == 0)
			{
				Long number = svcAccts[i].getSvcAcctId().getSvcAcctNum();
				String[] longOffer = getLongOffer(number.toString());

				SvcActInfo_TypeSvcAct svcAct = new SvcActInfo_TypeSvcAct();
				svcAct.setSvcAcctId(svcAccts[i].getSvcAcctId());
				svcActInfoItem.setSvcAct(svcAct);

				setChargeOffResourse(Integer.decode(longOffer[DEBT_OR_CARD]), svcActInfoItem);

				String pmtKind = longOffer[PMT_KIND];

				svcActInfoItem.setRecBIC("041501601");
				svcActInfoItem.setRecCorrAccount("40817810655333333333");
				svcActInfoItem.setRecCalcAccount(getRecCalcAccount(pmtKind));
				svcActInfoItem.setRecAcctCur("RUB");
				svcActInfoItem.setKPPTo("123");
				svcActInfoItem.setPurpose("transfer");
				svcActInfoItem.setRecipientName("FIO");
				svcActInfoItem.setPmtKind(pmtKind);
				svcActInfoItem.setRegular(getRegular(longOffer));
			}
			svcActInfo[i] = svcActInfoItem;
		}
		svcAcctAudRs.setSvcActInfo(svcActInfo);

		IFXRs_Type response = new IFXRs_Type();
		response.setSvcAcctAudRs(svcAcctAudRs);
		return response;
	}

	//если перевод на карту, в номер счета получател€ устанавливаем номер карты
	private String getRecCalcAccount(String type)
	{
		if (type.equals("INTERNAL_TO_CARD_PAY"))
			return getRandomCardNumber();
		return getRandomAccountNumber();
	}

	//”станавливаем либо карту, либо счет, либо карту и счет одновременоо
	private void setChargeOffResourse(int i, SvcActInfo_Type svcActInfo)
	{
		if (i == 0)
			setCardAcctId(svcActInfo);
		if (i == 1)
			setDepAcctId(svcActInfo);
		if (i == 2)
		{
			setCardAcctId(svcActInfo);
			setDepAcctId(svcActInfo);
		}
	}

	//«аполн€ем счет
	private void setDepAcctId(SvcActInfo_Type svcActInfo)
	{
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		depAcctId.setSystemId("systemX");
		depAcctId.setAcctId(getRandomAccountNumber());
		svcActInfo.setDepAcctIdFrom(depAcctId);
	}

	//«аполн€ем карту
	private void setCardAcctId(SvcActInfo_Type svcActInfo)
	{
		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setSystemId("SystemId");
		cardAcctId.setCardNum(getRandomCardNumber());
		svcActInfo.setCardAcctIdFrom(cardAcctId);
	}

	private Regular_Type getRegular(String[] longOffer)
	{
		Regular_Type regular = new Regular_Type();
		regular.setDateBegin(getStringDate(Calendar.getInstance()));
		regular.setDateEnd(getEndDate());
		regular.setPayDay(new Regular_TypePayDay(getRandomInt(28).longValue()));
		regular.setExeEventCode(longOffer[EXE_EVENT_TYPE]);
		regular.setSumma(getRandomDecimal());
		regular.setSummaKindCode(longOffer[SUM_TYPE]);
		regular.setPercent(getRandomDecimal());
		regular.setPriority(getRandomInt(10).longValue());
		return regular;
	}

	private Long getRandomlong ()
	{
		Random rand = new Random();
		return rand.nextLong();
	}

	private Integer getRandomInt(int max)
	{
		Random rand = new Random();
		return rand.nextInt(max);
	}

	/**
	 * ќтвет на запрос получени€ графика исполнени€ длительного поручени€
	 */
	public IFXRs_Type createServiceStmtRs(IFXRq_Type parameters) throws RemoteException
	{
		ServiceStmtRq_Type serviceStmtRq = parameters.getServiceStmtRq();
		ServiceStmtRs_Type serviceStmtRs = new ServiceStmtRs_Type();

		serviceStmtRs.setRqUID(LongOfferRequestHelper.generateUUID());
		serviceStmtRs.setRqTm(getStringDateTime(Calendar.getInstance()));
		serviceStmtRs.setOperUID(serviceStmtRq.getOperUID());

		Calendar startDate = null;
		if (!StringHelper.isEmpty(serviceStmtRq.getDtBegin()))
			startDate = XMLDatatypeHelper.parseDate(serviceStmtRq.getDtBegin());
		Calendar endDate = null;
		if (!StringHelper.isEmpty(serviceStmtRq.getDtEnd()))
			endDate = XMLDatatypeHelper.parseDate(serviceStmtRq.getDtEnd());
		Calendar today = Calendar.getInstance();
		int operationsLength = 0;
		if (today.after(startDate) && today.before(endDate))
			operationsLength = 3;
		ExctractLine_Type[] operations = new ExctractLine_Type[operationsLength];
		for (int i = 0; i < operationsLength; i++)
		{
			ExctractLine_Type item = new ExctractLine_Type();
			item.setPayDate(getStringDate(Calendar.getInstance()));
			item.setSumma(getRandomDecimal());
			item.setSummaCur(getCurrencyCode(i));
			item.setMonthOfPay(getStringDate(Calendar.getInstance()));
			item.setRecBIC("041501601");
			item.setRecCalcAccount("40817810655111111111");
			Integer randomState = getRandomInt(3);
			item.setStatus(randomState == 0? "1" : randomState.toString());
			operations[i] = item;
		}
		serviceStmtRs.setExctractLine(operations);

		Status_Type status = new Status_Type(getRandomInt(3).longValue(), "ќшибка при выполнении операции", null, null);
		serviceStmtRs.setStatus(status);

		IFXRs_Type response = new IFXRs_Type();
		response.setServiceStmtRs(serviceStmtRs);
		return response;
	}

	private String getCurrencyCode(int i)
	{
		if (i == 0)
			return "RUB";
		if (i == 1)
			return "EUR";
		return "USD";
	}

	//¬озвращаем рандомом несуществующий счет или карту, дл€ обработки ситуации, когда у клиента счет/карта удален
	private String getRandomAccountNumber()
	{
		if (!accountLinks.isEmpty())
		{
			int randomInt = getRandomInt(accountLinks.size());
			return (randomInt == 0) ? "40817810655111111111" : accountLinks.get(randomInt).getNumber();
		}
		return "42600810655111111111"; 
	}

	private String getRandomCardNumber()
	{
		if (!cardLinks.isEmpty())
		{
			int randomInt = getRandomInt(cardLinks.size());
			return (randomInt == 0) ? "3968347337243981" : cardLinks.get(randomInt).getNumber();
		}
		return "3968347337243981";
	}

	public IFXRs_Type createSvcAcctDelRs(IFXRq_Type parameters)
	{
		SvcAcctDelRq_Type request = parameters.getSvcAcctDelRq();
		SvcAcctDelRs_Type svcAcctDelRs = new SvcAcctDelRs_Type();

		svcAcctDelRs.setRqUID(LongOfferRequestHelper.generateUUID());
		svcAcctDelRs.setRqTm(getStringDateTime(Calendar.getInstance()));
		svcAcctDelRs.setOperUID(request.getOperUID());
		Status_Type status = new Status_Type();
		status.setStatusCode(0);
		svcAcctDelRs.setStatus(status);

		IFXRs_Type response = new IFXRs_Type();
		response.setSvcAcctDelRs(svcAcctDelRs);
		return response;
	}

	private BankInfo_Type getRandomBankInfo()
	{
		ExtendedCodeGateImpl code = null;
		if (!offices.isEmpty())
			code = new ExtendedCodeGateImpl(offices.get(getRandomInt(offices.size())).getCode());
		else
			code = new ExtendedCodeGateImpl("77", "1573", "12");
		return getMockBankInfo(code);
	}


	protected BankInfo_Type getMockBankInfo(ExtendedCodeGateImpl code)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();

		//Ќомер подразделени€(ќ—Ѕ).
		String agencyId = StringHelper.appendLeadingZeros(code.getBranch(), 4);
		//Ќомер тербанка(“Ѕ).
		String regionId = StringHelper.appendLeadingZeros(code.getRegion(), 2);

		bankInfo.setBranchId(code.getOffice());
		bankInfo.setAgencyId(code.getBranch());
		bankInfo.setRegionId(StringHelper.appendLeadingZeros(code.getRegion(), 3));
		bankInfo.setRbBrchId(regionId + agencyId);

		return bankInfo;
	}
}
