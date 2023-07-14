package com.rssl.phizic.web.client.payments;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ext.sbrf.payments.forms.MemOrder;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.action.ActionMapping;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Omeliyanchuk
 * @ created 28.07.2006
 * @ $Author$
 * @ $Revision$
 */
public class PrintMemForm extends ActionFormBase
{
	private Long         id;

	private Person person;
	private MemOrder order;

	public void setOrder(MemOrder order)
	{
		this.order = order;
	}

	public String getpayerBIC()
	{
		Bank bank = order.getPayerBank();
		if(bank != null)
		{
			if(bank instanceof ResidentBank)
			{
				ResidentBank rbank = (ResidentBank)bank;
				return rbank.getBIC();
			}
		}
		return "";
	}

	public String getpayerBankAccount()
	{
		Bank bank = order.getPayerBank();
		if(bank != null)
		{
			if(bank instanceof ResidentBank)
			{
				ResidentBank rbank = (ResidentBank)bank;
				return rbank.getAccount();
			}
		}
		return "";
	}

	public String getpayerBankName()
	{
		Bank bank = order.getPayerBank();
		if(bank != null)
			return bank.getName();

		return "";
	}

	public String getreceiverBankAccount()
	{
		Bank bank = order.getReciverBank();
		if(bank != null)
		{
			if(bank instanceof ResidentBank)
			{
				ResidentBank rbank = (ResidentBank)bank;
				return rbank.getAccount();
			}
		}
		return "";
	}

	public String getreceiverBankName()
	{
		Bank bank = order.getReciverBank();
		if(bank != null)
			return bank.getName();

		return "";
	}

	public String getreceiverINN()
	{
		return order.getreceiverINN();
	}

	public String getreceiverKPP()
	{
		return order.getreceiverKPP();
	}

	public String getreceiverBIC()
	{
		Bank bank = order.getReciverBank();
		if(bank != null)
		{
			if(bank instanceof ResidentBank)
			{
				ResidentBank rbank = (ResidentBank)bank;
				return rbank.getBIC();
			}
		}
		return "";
	}

	public String getBIC()
	{
		return getreceiverBIC();
	}

	public String getDay()
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd", dfs);
		return sdf.format(order.getPayment().getDateCreated());
	}
	public String getMonthString()
	{
		Calendar calendar =  order.getPayment().getDateCreated();
		return DateHelper.toFormDate( calendar );
	}
	public String getMonth()
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		SimpleDateFormat sdf = new SimpleDateFormat("MM", dfs);
		return sdf.format(order.getPayment().getDateCreated());

	}
	public String getYear()
	{
		Calendar calendar =  order.getPayment().getDateCreated();
		return ((Integer)calendar.get(Calendar.YEAR)).toString();
	}

	public String getSumma()
	{
		//Money money = order.getPayment().getChargeOffAmount();
		//return StringUtils.sumInWords(money.getDecimal().toPlainString(), money.getCurrency().getCode());
		return "";
	}

	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Person getPerson()
	{
		return person;
	}
	public void setPerson(Person person)
	{
		this.person = person;
	}
	public BusinessDocument getPayment()
	{
		return order.getPayment();
	}

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		id = null;
		order = null;
	}
}
