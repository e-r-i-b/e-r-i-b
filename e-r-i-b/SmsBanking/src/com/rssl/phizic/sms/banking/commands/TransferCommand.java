package com.rssl.phizic.sms.banking.commands;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.NullFieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.PseudonymService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.sms.banking.security.UserSendException;
import com.rssl.phizic.sms.banking.security.WrongCommandFormatException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 10.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class TransferCommand extends PaymentCommandBase
{
	private static final DepartmentService departmentService = new DepartmentService();

	public DocumentSource getDocumentSource() throws BusinessException, WrongCommandFormatException, BusinessLogicException
	{
		return new NewDocumentSource(parameters.get("formName"), new NullFieldValuesSource(), CreationType.sms, CreationSourceType.ordinary);
	}

	public FieldValuesSource getSmsData() throws BusinessException, UserSendException, BusinessLogicException
	{
		if (message == null)
		{
		   throw new WrongCommandFormatException();
		}

		String [] parseMessage = parseString(message);

		Map<String, String> sourceMap = new HashMap<String, String>();

		for(int i = 1; i < parameters.size(); i++)
		{
			sourceMap.put(parameters.get("arg"+ i), parseMessage[i]);
		}

		sourceMap.put("type", "SALE");
		sourceMap.put("documentDate", String.format("%1$te.%1$tm.%1$tY", DateHelper.getCurrentDate()));

		PseudonymService pseudonymService = new PseudonymService();
		PersonDataProvider personDataProvider =  PersonContext.getPersonDataProvider();
		Login login = personDataProvider.getPersonData().getPerson().getLogin();


		Pseudonym fromAccountPseudonym = pseudonymService.findByPseudonymName(login, parseMessage[1]);
		Account fromAccount = ((AccountLink)fromAccountPseudonym.getLink()).getAccount();
		String fromCurrencyCode = fromAccount.getCurrency().getCode();
		sourceMap.put("sellAmountCurrency", fromCurrencyCode);


		Pseudonym toAccountPseudonym = pseudonymService.findByPseudonymName(login, parseMessage[2]);
		Account toAccount = ((AccountLink)toAccountPseudonym.getLink()).getAccount();
		if(!MockHelper.isMockObject(toAccount))
		{
			String toCurrencyCode = toAccount.getCurrency().getCode();

			BigDecimal rate = getRate(fromCurrencyCode, toCurrencyCode, "SALE").getToValue();
			BigDecimal buyAmount = rate.multiply(new BigDecimal(parseMessage[3]));
			sourceMap.put("buyAmount", buyAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			sourceMap.put("buyAmountCurrency", toCurrencyCode);
			sourceMap.put("course", rate.toString());
		}
		
		return new MapValuesSource(sourceMap);
	}

	 public static CurrencyRate getRate(String fromCurr, String toCurr, String rateType)
    {
	    try
	    {
		    Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
		    Office office = departmentService.findById(departmentId);

		    CurrencyRateService service = GateSingleton.getFactory().service(CurrencyRateService.class);
	        Currency fromCurrency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode(fromCurr);
	        Currency toCurrency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode(toCurr);

		    String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
				    TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
		    return service.convert(new Money(BigDecimal.ONE,fromCurrency), toCurrency, office,
				    tarifPlanCodeType);
	    }
	    catch (BusinessException ex)
	    {
		    return null;
	    }
	    catch (GateException e)
        {
	        return null;
        }
	    catch (GateLogicException e)
	    {
			return null;
	    }
    }
}
