package ru.softlab.phizicgate.rsloansV64.claims;

import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltaclaimparm;
import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltclaimparm;
import ru.softlab.phizicgate.rsloansV64.claims.parsers.FieldId;
import ru.softlab.phizicgate.rsloansV64.config.UserFieldsConfig;
import ru.softlab.phizicgate.rsloansV64.config.UserFieldsConfigSingleton;
import ru.softlab.phizicgate.rsloansV64.config.UserFieldParser;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.gate.loans.QuestionnaireAnswer;
import com.rssl.phizic.gate.exceptions.GateException;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

class ClaimParamsBuilder
{	
	private ConverterManager converterManager = new ConverterManager();

	public Ikfltaclaimparm build(LoanOpeningClaim claim, LoanOpeningClaim parent)throws GateException
	{
		Iterator<QuestionnaireAnswer> iterator = claim.getQuestionnaireIterator();
		if(iterator==null)
			return null;

		List<Ikfltclaimparm> resultList = new ArrayList<Ikfltclaimparm>();

		while(iterator.hasNext())
		{
			resultList.addAll( parseAnswer(iterator.next()) );
		}

		if (parent == null)
			resultList.addAll(parseSystemParams(claim));
		else
			resultList.addAll(parseSystemParams(parent));

		for (Ikfltclaimparm rec : resultList)
		{
			try
			{
			  System.out.println(rec.getParmid() + ":"+ rec.getParmvalue());
			}
			catch(SQLException e){}
		}

		Ikfltclaimparm[] resultArray = resultList.toArray(new Ikfltclaimparm[resultList.size()]);

		return new Ikfltaclaimparm(resultArray);
	}

	private List<Ikfltclaimparm> parseAnswer(QuestionnaireAnswer answer)throws GateException
	{
		List<Ikfltclaimparm> resultList = new ArrayList<Ikfltclaimparm>();

		FieldId fieldId = QuestionnaireAnswerIdParser.parseAnswerId( answer.getId() );

		addParam(fieldId.getSystemId(), answer.getValue(), resultList);

		addParam(fieldId.getUserId(), answer.getValue(), resultList);

		return resultList;
	}

	private List<Ikfltclaimparm> parseSystemParams(LoanOpeningClaim claim) throws GateException
	{
		final UserFieldsConfig config = UserFieldsConfigSingleton.getConfig();
		final String[] userFieldsList = config.getLoansSystemUserFields();
		List<Ikfltclaimparm> list = new ArrayList<Ikfltclaimparm>(4);
		for (String userFieldId : userFieldsList)
		{
		    String tag = config.getLoanProductSystemField(userFieldId);
			try
			{
				Class clazz = claim.getClass();
				Method method = clazz.getMethod(tag);
				Object value = method.invoke(claim);
				UserFieldParser parser = config.getLoanProductSystemFieldsParsers(userFieldId);
				if(parser != null)
				    addParam(userFieldId,(String)parser.parse(value),list);
				else if (value != null && value instanceof String)
					addParam(userFieldId,(String)value,list);
				else
					addParam(userFieldId,tag,list);
			}
			catch(NoSuchMethodException e)
			{
				addParam(userFieldId,tag,list);
			}
			catch(InvocationTargetException e)
			{
			}
			catch(IllegalAccessException e )
			{}

		}
		if (claim.getLoanAmount()!= null)
		{
		  list.add(convertValue("LCREDITSUM", claim.getLoanAmount().getDecimal().toPlainString()));
		  list.add(convertValue("LCREDITCURCODE", claim.getLoanAmount().getCurrency().getNumber().equals("643") ? "0" : claim.getLoanAmount().getCurrency().getNumber()));
		}
		if (claim.getDuration()!= null)
		  list.add(convertValue("LCREDITDURATION", Integer.toString(claim.getDuration().getMonths())));
		if (claim.getSelfAmount() != null)
		{
		  list.add(convertValue("LSELFSUM", claim.getSelfAmount().getDecimal().toPlainString()));
		  list.add(convertValue("LSELFCURCODE", claim.getSelfAmount().getCurrency().getNumber().equals("643") ? "0" : claim.getSelfAmount().getCurrency().getNumber()));
		}
		if (claim.getObjectAmount() != null)
		{
		  list.add(convertValue("SUMOBJECT", claim.getObjectAmount().getDecimal().toPlainString()));
  		  list.add(convertValue("SUMOBJECTCURCODE", claim.getObjectAmount().getCurrency().getNumber()));
		}
		if (claim.getConditionsId()!= null)
		  list.add(convertValue("CREDITTYPEID_REF", claim.getConditionsId()));

		list.add(convertValue("LCTYPEDURATION", "0"));
		list.add(convertValue("GIVINGDATE", String.format("%1$tY-%1$tm-%1$te",claim.getClientCreationDate().getTime())));

		list.add(convertValue("IKFL_FNCASH",claim.getOfficeExternalId()));//передаем номер кредитного офиса, для генерации номера заявки(см BUG009792)

		//list.add(convertValue("LPAYDURATION", claim.getLoanAmount().getCurrency().getNumber()));
		//list.add(convertValue("LTYPEDURATION", claim.getLoanAmount().getDecimal().toPlainString()));
		//list.add(convertValue("LPERCRATE", claim.getLoanAmount().getCurrency().getNumber()));
		//list.add(convertValue("LCREDITPURPOSE", claim.getLoanAmount().getCurrency().getNumber()));
		//list.add(convertValue("NOTE", claim.getLoanAmount().getCurrency().getNumber()));
		return list;
	}

	private void addParam(String loansId, String value, List<Ikfltclaimparm> resultList) throws GateException
	{
		Ikfltclaimparm param;
		param = convertValue(loansId, value);
		if(param != null)
			resultList.add(param);
	}

	private Ikfltclaimparm convertValue(String loansId, String value) throws GateException
	{
		if(loansId==null || loansId.length()==0)
			return null;

		try
		{
			return new Ikfltclaimparm(loansId, converterManager.convert(loansId, value) );
		}
		catch(SQLException ex)
		{
			throw new GateException("Не удалось создать поле кредитной заявки:" + loansId);
		}
	}


}

