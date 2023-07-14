package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.FormException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepWorkDayValidator;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.earlyloanrepayment.EarlyLoanRepaymentConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;

/**
 * ћетадаталоадер дл€ за€вок на досрочное погашение кредита<br/>
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 26.05.15
 * Time: 13:58
 */
public class EarlyLoanRepaymentMetadataLoader implements ExtendedMetadataLoader
{
	private static final String PAYMENT_SCHEDULE_DICTIONARY_NAME = "paymentSchedule.xml";
	private static final String LOAN_INFO_DICTIONARY_NAME = "loanInfo.xml";

	private static final String DEFAULT_DATE = "defaultDate";
	private static final String DEFAULT_PAYMENT = "defaultPayment";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final CalendarService calendarService = new CalendarService();

	private static BigDecimal HUNDRED = BigDecimal.valueOf(100);

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		return buildExtendedMetadata(metadata, BooleanUtils.toBoolean(fieldSource.getValue("partial")), Long.parseLong(fieldSource.getValue("loanLinkId")));
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		EarlyLoanRepaymentClaimImpl claim = (EarlyLoanRepaymentClaimImpl) document;
		if(claim.getState().getCode().equals("INITIAL"))
		{
			return buildExtendedMetadata(metadata, claim.isPartial(), claim.getLoanLinkId());
		}
		else
		{
			return metadata;
		}
	}

	/**
	 * ѕостроить метадату по данным
	 * @param metadata исходна€ метадата
	 * @param partial признак частичного погашени€
	 * @param loanLinkId линк на кредит
	 * @return нова€ метадата
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private Metadata buildExtendedMetadata(Metadata metadata,boolean partial,Long loanLinkId) throws BusinessException, BusinessLogicException
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);
		newMetadata.setExtendedForm(metadata.getForm());

		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("info");
		Loan briefLoan = PersonContext.getPersonDataProvider().getPersonData().getLoan(loanLinkId).getLoan();
		Loan detailedLoan = GateSingleton.getFactory().service(LoansService.class).getLoan(briefLoan.getId()).getResult(briefLoan.getId());
		if(detailedLoan==null)
			throw new TemporalBusinessException("Ќе удалось получить детальные данные по кредиту");
		try
		{
			if(partial)
			{
				// частичное погашение кредита
				builder.createEntityTag(DEFAULT_DATE, new SimpleDateFormat(DATE_FORMAT).format(getFirstAcceptableDate(briefLoan).getTime()));
				builder.createEntityTag("minPayment", getMinimalPayment(briefLoan));
				builder.createEntityTag("maxPayment", getFullPayment(detailedLoan));
			}
			else
			{
				// полное погашение кредита
				ScheduleAbstract schedule = GateSingleton.getFactory().service(LoansService.class).getSchedule(0L,null,briefLoan).getResult(briefLoan);
				newMetadata.addDictionary(PAYMENT_SCHEDULE_DICTIONARY_NAME, buildDictionaryFromXmlField(buildPaymentSchedule(schedule)));
				builder.createEntityTag(DEFAULT_DATE, new SimpleDateFormat(DATE_FORMAT).format(getClosestPayment(briefLoan,schedule)));

				builder.createEntityTag(DEFAULT_PAYMENT, briefLoan.getRecPayment().getDecimal().toString());
				builder.createEntityTag("fullPayment", getFullPayment(detailedLoan));
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		builder.createEntityTag("loanCurrency",getLoanCurrency(briefLoan));
		builder.createEntityTag("loanTb", getLoanTb(briefLoan));
		builder.createEntityTag("loanTermEnd", getLoanTermEnd(briefLoan));
		builder.closeEntityTag("info");
		newMetadata.addDictionary(LOAN_INFO_DICTIONARY_NAME, buildDictionaryFromXmlField(builder.toString()));

		return newMetadata;
	}

	/**
	 * Ќайти первую дату, допустимую по настройке досрочного погашени€ и не €вл€ющуюс€ выходным днем
	 * @param loan кредит дл€ определени€ “Ѕ относительно календар€ которого определ€ютс€ выходные дни
	 * @return найденна€ дата
	 * @throws BusinessException
	 */
	private Calendar getFirstAcceptableDate(Loan loan) throws Exception
	{
		Calendar calendar = DateHelper.getCurrentDate();
		calendar.add(Calendar.DAY_OF_MONTH, ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMinDate());
		String tb = loan.getOffice().getCode().getFields().get("region");

		while(!calendarService.getIsWorkDayByTB(calendar,tb))
		{
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return calendar;
	}

	/**
	 * Ќайти приемлемый платЄж по графику
	 * @param from начало допустимого интервала
	 * @param to конец допустимого интервала
	 * @param schedule график
	 * @return дата допустимого платежа или null
	 */
	private Calendar findAcceptableScheduleItem(Calendar from,Calendar to,ScheduleAbstract schedule, Loan loan) throws Exception
	{
		String tb = loan.getOffice().getCode().getFields().get("region");
		if(schedule.getYearPayments()!=null)
		{
			for(LoanYearPayment year : schedule.getYearPayments())
			{
				for(LoanMonthPayment month : year.getMonths())
				{
					Calendar itemDate = month.getScheduleItem().getDate();
					if(!itemDate.before(from) && (to==null || !itemDate.after(to)) && loan.getTermEnd().after(itemDate) && calendarService.getIsWorkDayByTB(itemDate,tb))
					{
						return month.getScheduleItem().getDate();
					}
				}
			}
		}
		return null;
	}

	/**
	 * ѕолучить ближайший платеж
 	 * @param loan кредит
	 * @return ближайший платеж
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private Date getClosestPayment(Loan loan,ScheduleAbstract schedule) throws Exception
	{
		Calendar minDate = DateHelper.getCurrentDate();
		minDate.add(Calendar.DAY_OF_YEAR, ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMinDate());

		int maxDateSetting = ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMaxDate();
		Calendar maxDate = DateHelper.getCurrentDate();
		if(maxDateSetting==0)
		{
			maxDate = null;
		}
		else
		{
			maxDate.add(Calendar.DAY_OF_YEAR, maxDateSetting);
		}

		Calendar date = loan.getClosestPaymentDate();
		if(!date.before(minDate) && (maxDate==null || !date.after(maxDate)) && calendarService.getIsWorkDayByTB(date,loan.getOffice().getCode().getFields().get("region")))
		{
			return date.getTime();
		}
		date = findAcceptableScheduleItem(minDate, maxDate, schedule, loan);
		if(date!=null)
		{
			return date.getTime();
		}
		else
		{
			return getFirstAcceptableDate(loan).getTime();
		}
	}

	/**
	 * ѕостроить справочник по графику платежей
	 * @param schedule график платежей
	 * @return график платежей в xml
	 */
	private String buildPaymentSchedule(ScheduleAbstract schedule) throws BusinessLogicException, BusinessException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("schedule");
		if(schedule.getYearPayments()!=null)
		{
			for(LoanYearPayment year : schedule.getYearPayments())
			{
				for(LoanMonthPayment month : year.getMonths())
				{
				    ScheduleItem payment = month.getScheduleItem();
					builder.openEntityTag("payment");
					builder.createEntityTag("date", DateHelper.toXMLDateFormat(payment.getDate().getTime()));
					builder.createEntityTag("balance", payment.getRemainDebt().getDecimal().toString());
					builder.createEntityTag("amount", payment.getTotalPaymentAmount().getDecimal().toString());
					builder.closeEntityTag("payment");
				}
			}
		}
		builder.closeEntityTag("schedule");
		return builder.toString();
	}

	/**
	 * ѕолучить валюту кредита
	 * @param loan кредит
	 * @return код валюты
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private String getLoanCurrency(Loan loan) throws BusinessLogicException, BusinessException
	{
		return loan.getLoanAmount().getCurrency().getCode();
	}

	private String getLoanTb(Loan loan) throws BusinessLogicException, BusinessException
	{
		return loan.getOffice().getCode().getFields().get("region");
	}

	private String getLoanTermEnd(Long loanLinkId) throws BusinessLogicException, BusinessException
	{
		return DateHelper.getDateFormat(PersonContext.getPersonDataProvider().getPersonData().getLoan(loanLinkId).getLoan().getTermEnd().getTime());
	}

	private String getMinimalPayment(Long loanLinkId) throws BusinessLogicException, BusinessException
	{
		return PersonContext.getPersonDataProvider().getPersonData().getLoan(loanLinkId).getLoan().getNextPaymentAmount().getDecimal().divide(HUNDRED).multiply(BigDecimal.valueOf(ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMinAmount())).toString();
	}

	/**
	 * ѕолучить дату окончани€ кредита
	 * @param loan кредит
	 * @return дата окончани€
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private String getLoanTermEnd(Loan loan) throws BusinessLogicException, BusinessException
	{
		return DateHelper.getDateFormat(loan.getTermEnd().getTime());
	}
	/**
	 * –ассчитать минимальный платеж по кредиту
	 * @param loan кредит
	 * @return минимальный платеж
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private String getMinimalPayment(Loan loan) throws BusinessLogicException, BusinessException	{
		return loan.getRecPayment().getDecimal().divide(HUNDRED).multiply(BigDecimal.valueOf(ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMinAmount()))
				.round(new MathContext(10, RoundingMode.CEILING)).toString();
	}

	/**
	 * ѕолучить полное погашение кредита
 	 * @param loan кредит
	 * @return сумма полного погашени€
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private String getFullPayment(Loan loan) throws BusinessLogicException, BusinessException
	{
		return loan.getFullRepaymentAmount().getDecimal().toString();
	}

	private Element buildDictionaryFromXmlField(String xmlField) throws BusinessException
	{
		try
		{
			Document document = XmlHelper.parse(xmlField);
			return document.getDocumentElement();
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	// useless methods

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

}
