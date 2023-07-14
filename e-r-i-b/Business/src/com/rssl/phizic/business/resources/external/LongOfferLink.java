package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.mock.MockAbstractTransfer;
import com.rssl.phizic.business.longoffer.mock.MockLongOffer;
import com.rssl.phizic.business.longoffer.mock.MockLongOfferHelper;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.LongOfferService;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

public class LongOfferLink extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "long-offer";
	private static final Map<Class, String> typesMap = new HashMap<Class, String>();
	private static final String MOCK_STRING = "";
	private static final String MOCK_NAME = "Автоплатеж";

	static
	{
		typesMap.put(AccountPaymentSystemPayment.class,     "Оплата коммунальных услуг");
		typesMap.put(CardPaymentSystemPayment.class,        "Оплата коммунальных услуг");
		typesMap.put(ClientAccountsTransfer.class,          "Перевод между счетами");
		typesMap.put(AccountToCardTransfer.class,           "Перевод со счета на карту");
		typesMap.put(CardToAccountTransfer.class,           "Перевод с карты на счет");
		typesMap.put(InternalCardsTransfer.class,           "Перевод между картами");
		typesMap.put(CardJurIntraBankTransfer.class,        "Перевести деньги");
		typesMap.put(CardJurTransfer.class,                 "Перевести деньги");
		typesMap.put(AccountJurIntraBankTransfer.class,     "Перевести деньги");
		typesMap.put(AccountJurTransfer.class,              "Перевести деньги");
		typesMap.put(CardIntraBankPayment.class,            "Перевести деньги");
		typesMap.put(CardRUSPayment.class,                  "Перевести деньги");
		typesMap.put(AccountIntraBankPayment.class,         "Перевести деньги");
		typesMap.put(AccountRUSPayment.class,               "Перевести деньги");
		typesMap.put(LoanTransfer.class,                    "Погашение кредита");
	}

	private String number;            //Номер длительного порученияpe;     //Тип операции
	private String receiverAccount;
	private String receiverCard;
	private String receiverName;
	private String receiverINN;
	private String chargeOffAccount;
	private String chargeOffCard;
	private String receiverBankName;
	private String receiverBankCorAccount;

	public LongOffer getValue()
	{
		return getLongOffer();
	}

	private LongOffer getLongOffer()
	{
		try
		{
			LongOfferService longOfferService = GateSingleton.getFactory().service(LongOfferService.class);
			LongOffer longOffer = GroupResultHelper.getOneResult(longOfferService.getLongOffer(getExternalId()));
			return longOffer;
		}
		catch (InactiveExternalSystemException e)
	    {
		    return getStoredLongOffer(e);
	    }
		catch (OfflineExternalSystemException e)
		{
			return getStoredLongOffer(e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении длительного поручения №" + getNumber(), e);
			return new MockLongOffer(getExternalId());
		}
	}

	public AbstractTransfer getLongOfferInfo() throws BusinessLogicException, BusinessException
	{
		LongOffer longOffer = getLongOffer();
		if (!MockHelper.isMockObject(longOffer))
		{
			return getLongOfferInfo(longOffer);
		}
		return null;
	}

	private AbstractTransfer getLongOfferInfo(LongOffer longOffer)
	{
		try
		{
			if (MockHelper.isMockObject(longOffer))
				return createAbstractTransfer(longOffer);

			LongOfferService longOfferService = GateSingleton.getFactory().service(LongOfferService.class);
			return GroupResultHelper.getOneResult(longOfferService.getLongOfferInfo(longOffer));
		}
		catch (InactiveExternalSystemException e)
	    {
		    throw e;
	    }
		catch (Exception e)
		{
			log.error("Ошибка при получении информации о длительном поручении №" + getNumber(), e);
			return createAbstractTransfer(longOffer);
		}
	}

	public void reset() throws BusinessException, BusinessLogicException
	{
		LongOffer longOffer = getLongOffer();
		if (!MockHelper.isMockObject(longOffer))
		{
			try
			{
				GateSingleton.getFactory().service(CacheService.class).clearLongOfferCache(longOffer);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessLogicException(e);
			}
		}
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public String getName()
	{
		String name = super.getName();
		if (StringHelper.isEmpty(name))
		{
			name = getDefaultName();
			setName(name);
		}
		return name;
	}

	//возвращается название платежа
	public String getDefaultName()
	{
		LongOffer longOffer = getLongOffer();
		return MockHelper.isMockObject(longOffer) ? MOCK_NAME : typesMap.get(longOffer.getType());
	}

	//Возвращается тип исполнения платежа: каждый месяц 3-го числаб каждый год 25-го числа, по событию...
	public String getExecutionEventType()
	{
		LongOffer longOffer = getLongOffer();
		if (!MockHelper.isMockObject(longOffer))
		{
			StringBuilder executionEventType = new StringBuilder();
			ExecutionEventType eventType = longOffer.getExecutionEventType();
			// по спецификации тип может не придти
			if(eventType != null)
			{
				executionEventType.append(eventType.getDescription());
				if (eventType == ExecutionEventType.ONCE_IN_MONTH || eventType == ExecutionEventType.ONCE_IN_HALFYEAR ||
						eventType == ExecutionEventType.ONCE_IN_QUARTER || eventType == ExecutionEventType.ONCE_IN_YEAR)
					executionEventType.append(" ").append(longOffer.getPayDay().toString()).append("-го числа");
			}
			return executionEventType.toString();
		}
		return MOCK_STRING;
	}

	public void updateLinkParameters(LongOffer longOffer)
	{
		try
		{
			if (!MockHelper.isMockObject(longOffer))
			{
				Class<? extends GateDocument> paymentClass = longOffer.getType();
				AbstractTransfer longOfferInfo = getLongOfferInfo(longOffer);
				if (!MockHelper.isMockObject(longOfferInfo))
				{
					if (paymentClass==AccountPaymentSystemPayment.class)
					{
						AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) longOfferInfo;
						setReceiverName(payment.getReceiverName());
						setReceiverAccount(payment.getReceiverAccount());
						setReceiverINN(payment.getReceiverINN());
						setBankInfo(payment.getReceiverBank());
						setChargeOffAccount(payment.getChargeOffAccount());
					}
					else if (paymentClass==CardPaymentSystemPayment.class)
					{
						CardPaymentSystemPayment payment = (CardPaymentSystemPayment) longOfferInfo;
						setReceiverName(payment.getReceiverName());
						setReceiverAccount(payment.getReceiverAccount());
						setReceiverINN(payment.getReceiverINN());
						setBankInfo(payment.getReceiverBank());
						setChargeOffCard(payment.getChargeOffCard());
					}
					if (paymentClass.equals(ClientAccountsTransfer.class))
					{
						ClientAccountsTransfer payment = (ClientAccountsTransfer) longOfferInfo;
						setReceiverAccount(payment.getReceiverAccount());
						setChargeOffAccount(payment.getChargeOffAccount());
					}
					if (paymentClass.equals(CardToAccountTransfer.class))
					{
						CardToAccountTransfer payment = (CardToAccountTransfer) longOfferInfo;
						setReceiverAccount(payment.getReceiverAccount());
						setChargeOffCard(payment.getChargeOffCard());
					}
					if (paymentClass.equals(InternalCardsTransfer.class))
					{
						InternalCardsTransfer payment = (InternalCardsTransfer) longOfferInfo;
						setReceiverCard(payment.getReceiverCard());
						setChargeOffCard(payment.getChargeOffCard());
					}
					if (paymentClass.equals(AccountToCardTransfer.class))
					{
						AccountToCardTransfer payment = (AccountToCardTransfer) longOfferInfo;
						setReceiverCard(payment.getReceiverCard());
						setChargeOffAccount(payment.getChargeOffAccount());
					}
					if (paymentClass.equals(CardJurIntraBankTransfer.class))
					{
						CardJurIntraBankTransfer payment = (CardJurIntraBankTransfer) longOfferInfo;
						setChargeOffCard(payment.getChargeOffCard());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(CardJurTransfer.class))
					{
						CardJurTransfer payment = (CardJurTransfer) longOfferInfo;
						setChargeOffCard(payment.getChargeOffCard());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(AccountJurIntraBankTransfer.class))
					{
						AccountJurIntraBankTransfer payment = (AccountJurIntraBankTransfer) longOfferInfo;
						setChargeOffAccount(payment.getChargeOffAccount());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(AccountJurTransfer.class))
					{
						AccountJurTransfer payment = (AccountJurTransfer) longOfferInfo;
						setChargeOffAccount(payment.getChargeOffAccount());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(CardIntraBankPayment.class))
					{
						CardIntraBankPayment payment = (CardIntraBankPayment) longOfferInfo;
						setChargeOffCard(payment.getChargeOffCard());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(CardRUSPayment.class))
					{
						CardRUSPayment payment = (CardRUSPayment) longOfferInfo;
						setChargeOffCard(payment.getChargeOffCard());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(AccountIntraBankPayment.class))
					{
						AccountIntraBankPayment payment = (AccountIntraBankPayment) longOfferInfo;
						setChargeOffAccount(payment.getChargeOffAccount());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(AccountRUSPayment.class))
					{
						AccountRUSPayment payment = (AccountRUSPayment) longOfferInfo;
						setChargeOffAccount(payment.getChargeOffAccount());
						setReceiverName(payment.getReceiverName());
						setReceiverInfo(payment);
					}
					if (paymentClass.equals(LoanTransfer.class))
					{
						LoanTransfer payment = (LoanTransfer) longOfferInfo;
						if (StringHelper.isNotEmpty(payment.getChargeOffCard()))
							setChargeOffCard(payment.getChargeOffCard());
						if (StringHelper.isNotEmpty(payment.getAccountNumber()) && StringHelper.isEmpty(payment.getChargeOffCard()))
							setChargeOffAccount(payment.getAccountNumber());
					}
				}
				else
					log.error("Ошибка при заполнении параметров информации о длительном поручении №" + getNumber());
			}
			else
				log.error("Ошибка при заполнении параметров информации о длительном поручении №" + getNumber());
		}
		catch (Exception e)
		{
			log.error("Ошибка при заполнении параметров информации о длительном поручении №" + getNumber(), e);
		}
	}

	private void setReceiverInfo(AbstractRUSPayment payment) throws BusinessException
	{
		setReceiverAccount(payment.getReceiverAccount());
		setReceiverINN(payment.getReceiverINN());
		setBankInfo(payment.getReceiverBank());
	}

	private void setBankInfo(ResidentBank residentBank) throws BusinessException
	{
		setReceiverBankName(residentBank.getName());
		setReceiverBankCorAccount(residentBank.getAccount());
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public String getReceiverINN()
	{
		return receiverINN;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverCard()
	{
		return receiverCard;
	}

	public void setReceiverCard(String receiverCard)
	{
		this.receiverCard = receiverCard;
	}

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public String getReceiverBankName()
	{
		return receiverBankName;
	}

	public void setReceiverBankName(String receiverBankName)
	{
		this.receiverBankName = receiverBankName;
	}

	public String getReceiverBankCorAccount()
	{
		return receiverBankCorAccount;
	}

	public void setReceiverBankCorAccount(String receiverBankCorAccount)
	{
		this.receiverBankCorAccount = receiverBankCorAccount;
	}

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	@Override
	public Class getStoredResourceType()
	{
		return StoredLongOffer.class;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$longOfferName:" + this.getId();
	}

	private MockAbstractTransfer createAbstractTransfer(LongOffer longOffer)
	{
		try
		{
			MockAbstractTransfer mockAbstractTransfer = MockLongOfferHelper.getMockInfo(longOffer.getType());
			mockAbstractTransfer.setLongOffer(longOffer);
			return mockAbstractTransfer;
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	private LongOffer getStoredLongOffer(Exception e)
	{
		AbstractStoredResource storedResource = StoredResourceHelper.findStoredResource(this, e);
		if (storedResource == null)
		{
			log.error("Ошибка при получении информации по длительному поручению №" + getNumber(), e);
			return new MockLongOffer();
		}

		return (LongOffer) storedResource;
	}

	public String toString()
    {
        return "Длительное поручение №" + getNumber();
    }
}
