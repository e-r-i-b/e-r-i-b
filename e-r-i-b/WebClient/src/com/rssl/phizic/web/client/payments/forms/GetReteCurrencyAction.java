package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 09.04.2008
 * Time: 12:16:24
 */
public class GetReteCurrencyAction extends OperationalActionBase
{
    protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();

		return map;
	}

  public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CurrencyRateService courseService = GateSingleton.getFactory().service(CurrencyRateService.class);
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
		DepartmentService departmentService = new DepartmentService();
		Office office = departmentService.findById(departmentId);
		Currency currencySell = currencyService.findByAlphabeticCode(request.getParameter("cur1"));
		Currency currencyBuy = currencyService.findByAlphabeticCode(request.getParameter("cur2"));
		BigDecimal summa = new BigDecimal(request.getParameter("sum"));
		CurrencyRate currencyRate = null;
		boolean purchase = currencySell.getExternalId().equals("0"); // покупка
		boolean type = request.getParameter("type").equals("1");

		String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
				TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
		if (purchase)
		    currencyRate = courseService.convert(currencySell, new Money(summa, currencyBuy), office, tarifPlanCodeType); // покупка валюты
		else
		    currencyRate = courseService.convert(new Money(summa, currencySell), currencyBuy, office, tarifPlanCodeType);// продажа вал.

		writeToOutput(currencyRate, purchase , type, response);

		return null;
	}

   private void writeToOutput(CurrencyRate currencyRate, boolean purchase, boolean type, HttpServletResponse response)
			throws IOException, TransformerException
    {
        BigDecimal rate = currencyRate.getFactor();
	    String str = "document.getElementById('rateForeignCurrencySale').value="+rate+";";
	    if (purchase)
	    {
		    if (type)
		       str = str + "document.getElementById('sellAmount').value="+currencyRate.getFromValue().setScale(2, BigDecimal.ROUND_HALF_UP).toString()+";";
		    else
		       str = str + "document.getElementById('buyAmount').value="
				         + currencyRate.getFromValue().setScale(2, BigDecimal.ROUND_HALF_UP).toString()+";";
		}
	    else
   	       if (type)
		      str = str + "document.getElementById('sellAmount').value="
				        + currencyRate.getFromValue().setScale(2, BigDecimal.ROUND_HALF_UP).toString()+";";
	       else
	          str = str + "document.getElementById('buyAmount').value="+currencyRate.getFromValue().setScale(2, BigDecimal.ROUND_HALF_UP).toString()+";";

	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		buffer.write(str.getBytes());

	    response.setContentLength(buffer.size());
		response.setContentType("application/x-javascript");
		ServletOutputStream stream = response.getOutputStream();
	    buffer.writeTo(stream);
			    
		stream.flush();
	    stream.close();
	}
}
