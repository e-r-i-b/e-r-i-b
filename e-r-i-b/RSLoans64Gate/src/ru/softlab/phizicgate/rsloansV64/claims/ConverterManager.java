package ru.softlab.phizicgate.rsloansV64.claims;

import ru.softlab.phizicgate.rsloansV64.claims.parsers.ValueParser;
import ru.softlab.phizicgate.rsloansV64.claims.parsers.DummyFieldParser;
import ru.softlab.phizicgate.rsloansV64.claims.parsers.DateValueParser;
import ru.softlab.phizicgate.rsloansV64.claims.parsers.MapValueParser;

import java.util.Map;
import java.util.HashMap;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Выбор необходимого для каждого поля парсера
 */
class ConverterManager  
{
	private static final String CONFIG_FILE_NAME = "ru/softlab/phizicgate/rsloansV64/claims/loans64UserInfoFields.xml";

	private static final String FIELD_TAG = "field";
	private static final String PARAM_TAG = "param";

	private static final String NAME_ATTRIBUTE = "name";
	private static final String VALUE_ATTRIBUTE = "value";
	private static final String PARSER_ATTRIBUTE = "parser";

	private static Map<String, String> loanUserFieldsParsers = new HashMap<String, String>();

	private static Map<String, ValueParser> fieldParsers = new HashMap<String,ValueParser>();

	static
	{
		try
		{
			fieldParsers = initFields();
		}
		catch (GateException e)
		{
			e.printStackTrace();
		}
	}

	private static Map<String, ValueParser> initFields() throws GateException
	{
		Map<String, ValueParser> map = new HashMap<String, ValueParser>();
		try
		{
			Document config = XmlHelper.loadDocumentFromResource(CONFIG_FILE_NAME);
			Element rootElem = config.getDocumentElement();
			parseField(rootElem,map);
		}
		catch(Exception ex)
		{
			throw new GateException("Ошибка при разборе конфигурации RS-Loans64 loans64UserInfoFields.xml:",ex);
		}
		return map;
	}

	private static void parseField(Element element,final Map<String, ValueParser> map) throws Exception
	{
		XmlHelper.foreach(element, FIELD_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String loanIdString = element.getAttribute(NAME_ATTRIBUTE);
				String parser = element.getAttribute(PARSER_ATTRIBUTE);
				if(parser.equals("dateValueParser"))
				{
					map.put(loanIdString, new DateValueParser());
				}else if(parser.equals("mapValueParser"))
				{
					loanUserFieldsParsers = new HashMap<String, String>();
					parseParam(element);
					MapValueParser mvp = new MapValueParser(loanUserFieldsParsers);
					map.put(loanIdString,mvp);
				}else
					throw new GateException("Не удалось создать парсер "+ parser);
			}
		}
		);
	}

	private static void parseParam(Element element) throws Exception
	{		
		XmlHelper.foreach(element, PARAM_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute(NAME_ATTRIBUTE);
				String value = element.getAttribute(VALUE_ATTRIBUTE);
				loanUserFieldsParsers.put(name,value);
	        }
		}
		);
	}

	public String convert(String id, String value)
	{
		return findParser(id).parse(value);
	}

	private ValueParser findParser(String id)
	{
		ValueParser result = fieldParsers.get(id);
		return result != null? result: new DummyFieldParser();
	}
}
