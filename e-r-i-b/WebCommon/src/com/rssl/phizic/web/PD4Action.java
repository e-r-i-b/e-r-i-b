package com.rssl.phizic.web;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.print.PD4PrintOperation;
import com.rssl.phizic.operations.print.CollectionLetterOperation;
import com.rssl.phizic.operations.print.PaymentOrderOperation;
import com.rssl.phizic.utils.StringUtils;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 26.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class PD4Action extends OperationalActionBase
{
    private static final String FORWARD_START   = "Start";
	private static final String FORWARD_PERIOD_FILL   = "PeriodFill";
	private static final String FORWARD_STATUS_FILL   = "StatusFill";
	private static final String FORWARD_TYPE_FILL   = "TypeFill";
	private static final String FORWARD_FUND_FILL   = "FundFill";
	private static final String FORWARD_PRINT_NALOG   = "PrintNalog";
	private static final String FORWARD_PRINT   = "Print";
	private static final String FORWARD_PRINT_LETTER   = "PrintLetterOffer";
	private static final String FORWARD_PRINT_PAY   = "PrintPayOffer";

	protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		String action = request.getParameter("action");
		if(action == null)
		{
			PD4Form frm  = (PD4Form)form;
			String  page = request.getParameter("page");

            if ( page == null )
			{
                createOperation(PD4PrintOperation.class);
            }
            else if ( page.equals("letter") )
            {
                createOperation(CollectionLetterOperation.class);
                frm.setType("letter");
            }
            else if ( page.equals("pay") )
            {
                createOperation(PaymentOrderOperation.class);
                frm.setType("pay");
			}
			else if ( page.equals("nalog") )
            {
                createOperation(PD4PrintOperation.class);
                frm.setType("nalog");
			}

			addLogParameters(new SimpleLogParametersReader("Тип печатной формы", page));

            return mapping.findForward(PD4Action.FORWARD_START);
		}

	    addLogParameters(new SimpleLogParametersReader("Тип документа", action));

		//заполнение статуса
		if(action.equals("taxstatus"))
			return mapping.findForward(PD4Action.FORWARD_STATUS_FILL);
		//заполнение типа
		if(action.equals("taxtype"))
			return mapping.findForward(PD4Action.FORWARD_TYPE_FILL);
		//заполнение основания
		if(action.equals("taxfund"))
			return mapping.findForward(PD4Action.FORWARD_FUND_FILL);
		//заполнение периода
		if(action.equals("periodfill"))
			return mapping.findForward(PD4Action.FORWARD_PERIOD_FILL);
		//печать ПД-4 с налоговыми полями
		if(action.equals("printNalog"))
        {
            createOperation(PD4PrintOperation.class);
            return mapping.findForward(PD4Action.FORWARD_PRINT_NALOG);
        }
        //печать ПД-4
		if(action.equals("print"))
        {
            createOperation(PD4PrintOperation.class);
			return mapping.findForward(PD4Action.FORWARD_PRINT);
        }
        //печать инкассового поручения
		if(action.equals("printLetter"))
		{
            createOperation(CollectionLetterOperation.class);

			PD4Form frm = (PD4Form)form;
			setSumma(frm,request);
			frm.setIsLetter(true);

			return mapping.findForward(PD4Action.FORWARD_PRINT_LETTER);
		}
		//печать платежного поручения
		if(action.equals("printPay"))
		{
            createOperation(PaymentOrderOperation.class);

			PD4Form frm = (PD4Form)form;
			setSumma(frm,request);
			frm.setIsLetter(false);

            return mapping.findForward(PD4Action.FORWARD_PRINT_PAY);
		}

		return mapping.findForward(PD4Action.FORWARD_START);
	}

	private void setSumma(PD4Form frm,HttpServletRequest request)
	{
		String summ = request.getParameter("summ");
		if(summ.length()!=0)
		{
			String moneyStr = StringUtils.sumInWords(summ,"RUB");
			frm.setSumma(moneyStr);
		}
	}


 }
