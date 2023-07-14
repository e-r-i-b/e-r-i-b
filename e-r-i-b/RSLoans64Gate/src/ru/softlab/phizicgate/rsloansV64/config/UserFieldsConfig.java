package ru.softlab.phizicgate.rsloansV64.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.gate.exceptions.GateException;


/**
 * @author Omeliyanchuk
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class UserFieldsConfig
{
	private static final String LOANS_CONFIG_FILE_NAME = "ru/softlab/phizicgate/rsloansV64/config/loans64UserFields.xml";

	private static final String REQUEST_TAG = "request";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String GET_LOAN_PRODUCT_REQUEST = "getLoanProduct";
	private static final String FIELD_TAG = "field";
	private static final String LOANS_ID_TAG = "loansId";
	private static final String TAG_TAG = "tag";
	private static final String VALUE_TAG = "value";
	private static final String PARSER_TAG = "parser";

	private Map<BigDecimal,String> loanProductUserFieldsMap = new HashMap<BigDecimal,String>();
	private Map<BigDecimal,String> loanInfoUserFieldsMap = new HashMap<BigDecimal,String>();
	private Map<String,String> loanProductSystemFieldsMap = new HashMap<String,String>();
	private Map<BigDecimal, UserFieldParser> loanProductUserFieldsParsers = new HashMap<BigDecimal, UserFieldParser>();
	private Map<BigDecimal, UserFieldParser> loanInfoUserFieldsParsers = new HashMap<BigDecimal, UserFieldParser>();
	private Map<String, UserFieldParser> loanProductSystemFieldsParsers = new HashMap<String, UserFieldParser>();

	UserFieldsConfig()
	{
	}

	public void init() throws GateException
	{
		try
		{
			Document config = XmlHelper.loadDocumentFromResource(LOANS_CONFIG_FILE_NAME);
			Element rootElem = config.getDocumentElement();
			parseConfig(rootElem);
		}
		catch(Exception ex)
		{
			throw new GateException("Ошибка при разборе конфигурации RS-Loans64 loans64UserFields.xml:",ex);
		}
	}

	public BigDecimal[] getLoanProductUserFields()
	{
		return null;
	}

	public BigDecimal[] getLoansInfoUserFields()
	{
		return null;
	}

	public String[] getLoansSystemUserFields()
	{
		return null;
	}

	public String getLoanProductUserField(BigDecimal userField)
	{
		return loanProductUserFieldsMap.get(userField);
	}

	public String getLoanInfoUserField(BigDecimal userField)
	{
		return loanInfoUserFieldsMap.get(userField);
	}

	public String getLoanProductSystemField(String userField)
	{
		return loanProductSystemFieldsMap.get(userField);
	}

	public UserFieldParser getLoanProductUserFieldsParser(BigDecimal userField)
	{
		return loanProductUserFieldsParsers.get(userField);
	}

	public UserFieldParser getLoanInfoUserFieldsParser(BigDecimal userField)
	{
		return loanInfoUserFieldsParsers.get(userField);
	}

	public UserFieldParser getLoanProductSystemFieldsParsers(String userField)
	{
		return loanProductSystemFieldsParsers.get(userField);
	}

	private void parseConfig(Element root) throws Exception
	{
			XmlHelper.foreach(root, REQUEST_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String requestName = element.getAttribute(NAME_ATTRIBUTE);
					if(requestName!= null)
					{
						if(requestName.equals(GET_LOAN_PRODUCT_REQUEST))
						{
							parseLoansProducts(element);
						}
						if(requestName.equals("getLoansInfo"))
						{
							parseLoansInfo(element);
						}
						if(requestName.equals("getLoansSystemInfo"))
						{
							parseLoansSystemInfo(element);
						}
					}
				}
			}
			);
	}

	private void parseLoansInfo(Element element) throws Exception
	{
		parseField(element,loanInfoUserFieldsMap,loanInfoUserFieldsParsers);
	}

	private void parseLoansProducts(Element element) throws Exception
	{
		parseField(element, loanProductUserFieldsMap, loanProductUserFieldsParsers);
	}

	private void parseLoansSystemInfo(Element element) throws Exception
	{
		parseField2(element,loanProductSystemFieldsMap,loanProductSystemFieldsParsers);
	}

	private void parseField(Element element,final Map<BigDecimal, String> fields,final Map<BigDecimal, UserFieldParser> parsers) throws Exception
	{

		XmlHelper.foreach(element, FIELD_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String loanIdString = element.getAttribute(LOANS_ID_TAG);
				String tag = element.getAttribute(TAG_TAG);
				if (tag == null || tag.length() == 0)
				    tag = element.getAttribute(VALUE_TAG);
				String parserString = XmlHelper.getSimpleElementValue(element,PARSER_TAG);

				if(loanIdString == null)
					throw new GateException("Не установлен идентификатор поля в RS_LOANS");
				if(tag==null)
					throw new GateException("Не установлено имя поля в ИКФЛ");

				BigDecimal loanId = new BigDecimal(loanIdString);
				fields.put(loanId, tag);

				if(parserString != null)
				{
					try
					{
						Class<? extends UserFieldParser> parser = ClassHelper.loadClass(parserString);
						parsers.put(loanId,parser.newInstance());
					}
					catch(ClassNotFoundException ex)
					{
						throw new GateException("Не удалось загрузить парсер:" + parserString +" для поля с id:" + loanIdString,ex);
					}
					catch(Exception ex)
					{
						throw new GateException("Не удалось создать парсер:" + parserString +" для поля с id:" + loanIdString,ex);
					}
				}
			}
		}
		);
	}

	private void parseField2(Element element,final Map<String, String> fields,final Map<String, UserFieldParser> parsers) throws Exception
	{

		XmlHelper.foreach(element, FIELD_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String loanIdString = element.getAttribute(LOANS_ID_TAG);
				String tag = element.getAttribute(TAG_TAG);
				if (tag == null || tag.length() == 0)
				    tag = element.getAttribute(VALUE_TAG);
				String parserString = XmlHelper.getSimpleElementValue(element,PARSER_TAG);

				if(loanIdString == null)
					throw new GateException("Не установлен идентификатор поля в RS_LOANS");
				if(tag==null)
					throw new GateException("Не установлено имя поля в ИКФЛ");

				fields.put(loanIdString, tag);

				if(parserString != null)
				{
					try
					{
						Class<? extends UserFieldParser> parser = ClassHelper.loadClass(parserString);
						parsers.put(loanIdString,parser.newInstance());
					}
					catch(ClassNotFoundException ex)
					{
						throw new GateException("Не удалось загрузить парсер:" + parserString +" для поля с id:" + loanIdString,ex);
					}
					catch(Exception ex)
					{
						throw new GateException("Не удалось создать парсер:" + parserString +" для поля с id:" + loanIdString,ex);
					}
				}
			}
		}
		);
	}

}
