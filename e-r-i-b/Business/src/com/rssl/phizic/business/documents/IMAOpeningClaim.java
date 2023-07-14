package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.IMAOpeningFromCardClaim;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.PermissionUtil;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

/**
 * @author Mescheryakova
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningClaim  extends ExchangeCurrencyTransferBase  implements com.rssl.phizic.gate.claims.IMAOpeningClaim, IMAOpeningFromCardClaim
{
	private static final String OFFICE_NAME = "office-name";
	private static final String OFFICE_ADDRESS = "office-address";
	private static final String OPENING_DATE = "opening-date";
	private static final String COURSE_SELL = "course-sell";
	private static final String REGION = "office-region";
	private static final String BRANCH = "office-branch";
	private static final String OFFICE = "office";
	private static final String IMA_ID = "imaId";
	private static final String IMA_NAME = "buyIMAProduct";
	private static final String DEFAULT_LOCALE_IMA_NAME = "defaultLocaleImaName";
	private static final String IMA_TYPE = "ima-type";
	private static final String IMA_SUB_TYPE = "ima-sub-type";
	private static final String IMA_AMOUNT = "value-of-exact-amount";
	private static final String IMA_CURRENCY = "buyCurrency";
	private static final String PFP_ID = "pfpId";
	private static final String PFP_PORTFOLIO_ID = "pfpPortfolioId";
	private static final DepartmentService DEPARTMENT_SERVICE = new DepartmentService();

	protected boolean needRates() throws DocumentException
	{
		return isConvertion();
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);

		if (!PersonContext.isAvailable())
			return;
		try
		{
			String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
					TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();

			if (TariffPlanHelper.isUnknownTariffPlan(tarifPlanCodeType))
				return;

			//Установка обычного курса для отображения клиенту выгоды
			CurrencyRateService rateService = GateSingleton.getFactory().service(CurrencyRateService.class);
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

			Currency nationalCurrency = currencyService.getNationalCurrency();
			Currency currency = currencyService.findByNumericCode(getIMACurrency());
			Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
			Department department = DEPARTMENT_SERVICE.findById(departmentId);

			CurrencyRate buy = rateService.getRate(currency, nationalCurrency, CurrencyRateType.SALE_REMOTE,
									department, TariffPlanHelper.getUnknownTariffPlanCode());

			setNullSaveAttributeStringValue(STANDART_CONVERTION_RATE_ATTRIBUTE_NAME, String.valueOf(buy.getFactor()));
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
    * Фактичский тип документа
    */
	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		if (chargeOffResourceType == ResourceType.NULL)
		{
			return null;
		}

		if (chargeOffResourceType == ResourceType.CARD)
		{
			return IMAOpeningFromCardClaim.class;
		}
		if (chargeOffResourceType == ResourceType.ACCOUNT)
		{
			return com.rssl.phizic.gate.claims.IMAOpeningClaim.class;
		}

		throw new IllegalStateException("Невозмжно определить тип документа");
	}

	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия. Дату берем из линка (обновляется из GFL при входе)
				setChargeOffCardExpireDate(getChargeOffCardExpireDate(chargeOffResourceType, messageCollector));
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
		}
	}

	public ResourceType getDestinationResourceType()
	{
		return ResourceType.IM_ACCOUNT;
	}

	protected Money roundAmount(BigDecimal decimal, Currency currency)
	{
		return MoneyUtil.roundDownForCurrency(decimal, currency);
	}

	public String getOfficeName()
	{
		return getNullSaveAttributeStringValue(OFFICE_NAME);
	}

	public String getOfficeAddress()
	{
		return getNullSaveAttributeStringValue(OFFICE_ADDRESS);	
	}

	public void setOpeningDate(Calendar openingDate)
	{
		setNullSaveAttributeStringValue(OPENING_DATE, getDateFormat().format(openingDate.getTime()));
	}

	public Calendar getOpeningDate()
	{
		String openingDate =  getNullSaveAttributeStringValue(OPENING_DATE);
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDateFormat().parse(openingDate));
			return calendar;
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	public BigDecimal getCourse()
	{
		return getConvertionRate();
	}

	public BigDecimal getCourseSale()
	{
		return BigDecimal.valueOf(Double.valueOf(getNullSaveAttributeStringValue(COURSE_SELL)));
	}

	public String getOfficeRegion()
	{
		return getNullSaveAttributeStringValue(REGION);
	}

	public String getOfficeBranch()
	{
		return getNullSaveAttributeStringValue(BRANCH);
	}

	public String getOfficeVSP()
	{
		return getNullSaveAttributeStringValue(OFFICE);
	}

	public Long getIMAProductID()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(IMA_ID));
	}

	public String getIMAProductName()
	{
		return getNullSaveAttributeStringValue(IMA_NAME);
	}

	/**
	 * @return название ОМС для базовой локали
	 */
	public String getDefaultLocaleIMAProductName()
	{
		return getNullSaveAttributeStringValue(DEFAULT_LOCALE_IMA_NAME);
	}

	public Long getIMAProductType()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(IMA_TYPE));
	}

	public Long getIMAProductSubType()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(IMA_SUB_TYPE));
	}

	public BigDecimal getIMAAmount()
	{
		return (BigDecimal) getNullSaveAttributeValue(IMA_AMOUNT);
	}

	public String getIMACurrency()
	{
		return getNullSaveAttributeStringValue(IMA_CURRENCY);
	}


	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}

	public String getOperUId()
	{
		return getNullSaveAttributeStringValue(OPER_UID);
	}

	/**
	 * Установка идентификатора операции
	 * @param operUid
	 */
	public void setOperUId(String operUid)
	{
		setNullSaveAttributeStringValue(OPER_UID, operUid);
	}

	public Calendar getOperTime()
	{
		return getNullSaveAttributeCalendarValue(OPER_TIME);
	}

	/**
	 * Установка даты и времени передачи сообщения
	 * @param operTime
	 */
	public void setOperTime(Calendar operTime)
	{
		setNullSaveAttributeCalendarValue(OPER_TIME, operTime);
	}

	/**
	 * @return идентификатор ПФП
	 */
	public Long getPfpId()
	{
		return (Long)getNullSaveAttributeValue(PFP_ID);
	}

	/**
	 * @return идентификатор портфеля из ПФП
	 */
	public Long getPfpPortfolioId()
	{
		return (Long)getNullSaveAttributeValue(PFP_PORTFOLIO_ID);
	}
}
