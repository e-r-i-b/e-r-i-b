package com.rssl.phizic.operations.loanreport;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bki.*;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.config.loanreport.CreditBureauConfig;
import com.rssl.phizic.config.loanreport.CreditBureauConfigStorage;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.loanreport.pdf.BkiReportBuilderHelper;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.pdf.PDFBuilderException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gulov
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditReportOperation extends OperationBase implements ViewEntityOperation
{
	private final CreditBureauConfigStorage creditBureauConfigStorage = new CreditBureauConfigStorage();

	private static final CreditProfileService profileService = new CreditProfileService();
	private static final PersonCreditReportService reportService = new PersonCreditReportService();
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final BkiReportBuilderHelper bkiReportHelper = new BkiReportBuilderHelper();

	private PersonCreditReport report;
	private PersonCreditProfile profile;
	private Money cost;
	private Long providerId;
	private CreditDetail creditDetail;
	private Person person;

	public PersonCreditReport getEntity() throws BusinessException, BusinessLogicException
	{
		return report;
	}

	public Money getCost()
	{
		return cost;
	}

	public Long getProviderId()
	{
		return providerId;
	}

	public CreditDetail getCreditDetail()
	{
		return creditDetail;
	}

	/**
	 * @return есть ли готовый ответ (ожидается ли новый - неважно)
	 */
	public boolean hasReport()
	{
		return profile.getReport() != null;
	}

	/**
	 * @return ожидается ли ответ (показывается форма ожидания, нет кнопки оплатить заново)
	 */
	public boolean isWaitingNew()
	{
		//запрос считается ожидаемым, если дата исполнения платежа за него позже даты прихода последнего ответа
		Calendar lastPayment = profile.getLastPayment();
		Calendar lastReport = profile.getLastReport();

		boolean rc = lastPayment != null && lastReport == null;                                 //первичный запрос
		rc = rc || lastPayment != null && lastReport != null && lastPayment.after(lastReport);  //вторичный запрос
		return rc;
	}

	/**
	 * @return запрос завершился ошибкой или таймаутом
	 */
	public boolean isError()
	{
		Calendar lastGetError = profile.getLastGetError();
		Calendar lastReport = profile.getLastReport();
		Calendar lastPayment = profile.getLastPayment();

		boolean rc = lastGetError != null && lastReport == null;                                                    //только ошибка
		rc = rc || lastGetError != null && lastReport != null && lastGetError.after(lastReport);                    //ошибка позже последнего ответа
		rc = rc || isWaitingNew() && lastPayment != null && CreditReportHelper.isCreditReportTimeOut(lastPayment);  //таймаут
		return rc;
	}

	public void initialize(Person person, Integer creditId) throws BusinessException
	{
		initialize(person);
		creditDetail = reportService.makeCreditDetail(profile, creditId);
	}

	public void initialize(Person person) throws BusinessException
	{
		this.person = person;
		findProfile(person);
		if (hasReport())
			report = reportService.makePersonCreditReport(profile);
		findProviderAndCost();
	}

	private void findProviderAndCost() throws BusinessException
	{
		ServiceProviderBase provider = findProvider();
		providerId = provider.getId();
		cost = getProviderAmount(provider);
	}

	private Money getProviderAmount(ServiceProviderBase provider) throws BusinessException
	{
		List<FieldDescription> fields = provider.getFieldDescriptions();
		for (FieldDescription field : fields)
			if (field.isMainSum())
			{
				String value = field.getDefaultValue();
				if (value == null)
					throw new BusinessException("не заполнено значение поля Сумма у поставщика " + provider.getName());
				return new Money(new BigDecimal(value), "RUR");
			}
		throw new BusinessException("Не найдено поле Сумма у поставщика " + provider.getName());
	}

	private void findProfile(Person person) throws BusinessException
	{
		profile = profileService.findByPerson(person);
		if (profile == null)
			throw new BusinessException("У клиента нет кредитного профиля");
		if (!profile.isConnected())
			throw new BusinessException("У клиента нет кредитной истории");
	}

	private ServiceProviderBase findProvider() throws BusinessException
	{
		CreditBureauConfig config = creditBureauConfigStorage.loadConfig();

		UUID providerUid = config.okbServiceProviderUUID;
		ServiceProviderBase provider = providerService.findByMultiBlockId(providerUid.toString());
		if (provider == null)
			throw new BusinessException("Не найден поставщик услуг для ОКБ по UID " + providerUid);
		return provider;
	}

	public PersonCreditProfile getProfile()
	{
		return profile;
	}

	/**
	 * Формирует pdf
	 * @return pdf-файл
	 * @throws com.rssl.phizic.utils.pdf.PDFBuilderException
	 * @throws BusinessException
	 */
	public byte[] createPDF() throws PDFBuilderException, BusinessException, BusinessLogicException
	{
		if (!hasReport())
			throw new BusinessLogicException("У клиента нет кредитного отчета.");

		try
		{
			return bkiReportHelper.createPDF(profile, person);
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return имя файла
	 */
	public String getFileName()
	{
		return DateHelper.formatDateToStringWithSlash(profile.getLastReport());
	}

}
