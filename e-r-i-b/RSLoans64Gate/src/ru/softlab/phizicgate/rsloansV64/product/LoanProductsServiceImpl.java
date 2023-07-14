package ru.softlab.phizicgate.rsloansV64.product;

import com.rssl.phizic.gate.loans.LoanProductsService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

import ru.softlab.phizicgate.rsloansV64.connection.GateRSLoans64Executor;
import ru.softlab.phizicgate.rsloansV64.connection.LoansConnectionAction;
import ru.softlab.phizicgate.rsloansV64.config.UserFieldsConfig;
import ru.softlab.phizicgate.rsloansV64.config.UserFieldsConfigSingleton;
import ru.softlab.phizicgate.rsloansV64.config.UserFieldParser;
import ru.softlab.phizicgate.rsloansV64.jpub.*;

import javax.xml.parsers.DocumentBuilder;

/**
 * @author Omeliyanchuk
 * @ created 03.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductsServiceImpl extends AbstractService implements LoanProductsService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final int ALL_KINDS = 2;
	private static final int RESULT = 1;

	private static final String LOAN_PRODUCT_TAG = "loan-product";
	private static final String LOAN_CONDITIONS_TAG = "conditions";
	private static final String LOAN_CONDITION_TAG = "condition";
	private static final String LOAN_STATIC_TAG = "static";
	private static final String LOAN_DYNAMIC_TAG = "dynamic";
	private static final String LOAN_VALUE_TAG = "value";
	private static final String LOAN_TYPES_TAG = "loan-types";
	private static final String LOAN_TYPE_TAG = "loan-type";
	
	private static final String ID_TAG = "id";
	private static final String NAME_TAG = "name";
	private static final String CURRENCY_TAG = "currency";
	private static final String DURAUTION_TAG = "duration";


	private static final String ATTRIBUTE_NAME_TAG = "name";

	private static final String CONDITION_ID_FIELD = "conditionId";
	private static final String CURRENCY_FIELD = "currency";
	private static final String LOAN_TYPE_NAME_FIELD = "name";
	private static final String RATE_FIELD = "rate";
	private static final String PENALTY_PER_DAY_FIELD = "penaltyPerDay";

	private static final String LOAN_PRODUCT_REQUEST = "loanProductRequest";
	private static final String LOAN_INFO_REQUEST = "loanInfoRequest";



	public LoanProductsServiceImpl(GateFactory factory)
    {
        super(factory);
    }
   /**
    * Получение информации о всех кредитных продуктах.
    * Возвращенный документ содержит информацию, необходимую для создания кредитного продукта.
    * Формат возвращенного xml документа определяется реализацией.
    *
    *
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   public Document getLoansInfo() throws GateException
   {
	   log.trace("Получение списка видов кредита RS-Loans");
	   try
	   {
		    final UserFieldsConfig config = UserFieldsConfigSingleton.getConfig();
			final BigDecimal[] userFieldsList = config.getLoansInfoUserFields();

		    return GateRSLoans64Executor.getInstance(true).execute(new LoansConnectionAction<Document>()
				{
					public Document run(Connection connection) throws Exception
					{   //todo переделать процедуру на переменное число пользовательских полей
						CallableStatement cst = connection.prepareCall("{ ? = call IKFL.GetListTypeCrd(null,?,?,?,?,null,null) }");
						cst.registerOutParameter(RESULT, Types.ARRAY, "IKFLTATYPECRDLST");
						cst.setInt(2, ALL_KINDS);
						cst.setBigDecimal(3, userFieldsList[0]);   // возможные сроки кредита
						cst.setBigDecimal(4, userFieldsList[1]);   // доступные офисы
						cst.setBigDecimal(5, userFieldsList[2]);   // количество поручителей
						cst.execute();

						Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
						typeMap.put("IKFLTTYPECRDLST", Ikflttypecrdlst.class);
						Object[] tatypecrdlstArray = (Object[]) cst.getArray(RESULT).getArray(typeMap);

						return buildLoansInfoDocument(tatypecrdlstArray, config, userFieldsList);
					}
				});
	   }
	   catch(Exception ex)
	   {
		   throw new GateException("Ошибка при получении информации по видам кредитов:",ex);
	   }
   }

	private Document buildLoansInfoDocument(Object[] tatypecrdlstArray, final UserFieldsConfig config, final BigDecimal[] userFieldsList) throws SQLException, GateException
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document loanTypeDocument = documentBuilder.newDocument();
		Element typesElement = loanTypeDocument.createElement(LOAN_TYPES_TAG);
		loanTypeDocument.appendChild(typesElement);

		for (Object o : tatypecrdlstArray)
		{
			Ikflttypecrdlst ttypecrdlst = (Ikflttypecrdlst)o;
			Element loanElement = loanTypeDocument.createElement(LOAN_TYPE_TAG);
			typesElement.appendChild(loanElement);

			addTextField(loanTypeDocument, loanElement, ID_TAG, ttypecrdlst.getCredittypeid().toPlainString());

			addTextField(loanTypeDocument, loanElement, NAME_TAG, ttypecrdlst.getCredittypename().trim());

			String currency = ttypecrdlst.getCurcodeiso().trim();
			if ("RUR".equals(currency)){
				currency="RUB";
			}
			addTextField(loanTypeDocument, loanElement, CURRENCY_TAG, currency);

			checkPeriodType(ttypecrdlst.getTypeduration());

			UserFieldParser<List<String>> parser = config.getLoanInfoUserFieldsParser(userFieldsList[0]);
			Set<String> durations = new TreeSet<String>();
			durations.addAll(parser.parse(ttypecrdlst.getDuration().toPlainString()));
			durations.addAll(parser.parse(ttypecrdlst.getUserfield1()));
			for (String duration : durations)
			{
				addTextField(loanTypeDocument, loanElement, config.getLoanInfoUserField(userFieldsList[0]), duration);
			}

			UserFieldParser<List<String>> officeParser = config.getLoanInfoUserFieldsParser(userFieldsList[1]);
			List<String> officies = officeParser.parse(ttypecrdlst.getUserfield2());
			for (String office : officies)
			{
				addTextField(loanTypeDocument, loanElement, config.getLoanInfoUserField(userFieldsList[1]), office);
			}

			addTextField(loanTypeDocument, loanElement, config.getLoanInfoUserField(userFieldsList[2]), ttypecrdlst.getUserfield3());
		}
		validate(loanTypeDocument);
		return loanTypeDocument;
	}

	private void validate(Document document) throws GateException
	{
		DocumentValidator validator = new DocumentValidator();
		validator.validate(document);
	}

	/**
    * Получение информации о кредитном продукте.
    * Формат документа определяется реализацией.
    *
    * @param conditions Список ID условий (Domain: ExternalID)
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   public Document getLoanProduct(List<String> conditions) throws GateException
   {
	   log.trace("Получение детальной информации по видам кредита RS-Loans");

	   int i = 0;
	   final BigDecimal[] creditTypeIds= new BigDecimal[conditions.size()];
	   for (String condition : conditions)
	   {
		   creditTypeIds[i] = new BigDecimal(condition);
		   i++;
	   }

	   UserFieldsConfig config = UserFieldsConfigSingleton.getConfig();

	   final BigDecimal[] userFieldsList = config.getLoanProductUserFields();

	   try
	   {
		   return GateRSLoans64Executor.getInstance(true).execute(new LoansConnectionAction<Document>()
				{
					public Document run(Connection connection) throws Exception
					{
						CallableStatement cst = connection.prepareCall("{ ? = call IKFL.GetCrdTypeInfo(?,?) }");
						cst.registerOutParameter(RESULT, Types.ARRAY, "IKFLTACRDTYPE");
						cst.setObject(2,new Ikfltanumber(creditTypeIds));
						cst.setObject(3,new Ikfltanumber(userFieldsList));
						cst.execute();

						Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
						typeMap.put("IKFLTACRDTYPE", Ikfltacrdtype.class);
						typeMap.put("IKFLTCRDTYPE", Ikfltcrdtype.class);
						typeMap.put("IKFLTAUSERFIELD", Ikfltauserfield.class);
						typeMap.put("IKFLTUSERFIELD", Ikfltuserfield.class);
						return buildLoanProductDocument((Object[]) cst.getArray(RESULT).getArray(typeMap));
					}
				});
	   }
		catch(Exception ex)
		{
			throw new GateException("Ошибка при получении детальной информации по видам кредитов:",ex);
		}
   }

	private Document buildLoanProductDocument(Object[] tatypecrdlstArray) throws GateException
	{
		/*todo реализовать алгоритм определения отличающихся у видов кредита параметров
		  Т.е. единые для всех видов параметры  выносим в static, остальные в dynamic
		   например, в видах кредитов loans разные сроки и в зависимости от них, разные ставки,
		   но валюта одинаковая, тогда формируем xml
		   <loan-product>
		        <conditions>
		            <static>
		                <value name="currency">RUB</value>
		            </static>
		            <dynamic>
		                <condition>
		                    <value name="conditionId">123</value>
		                    <value name="duration">M:12</value>
		                    <value name="rate">11</value>
		                </condition>
		                    <value name="conditionId">234</value>
		                    <value name="duration">M:24</value>
		                    <value name="rate">10</value>
		                <condition>
		                </condition>
		            </dynamic>
		        </conditions>
		   </loan-product>

		   Сейчас все пишем в dynamic.
		 */
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document loanProductDocument = documentBuilder.newDocument();
		Element rootElement = loanProductDocument.createElement(LOAN_PRODUCT_TAG);
		loanProductDocument.appendChild(rootElement);

		Element conditionsElement = loanProductDocument.createElement(LOAN_CONDITIONS_TAG);
		rootElement.appendChild(conditionsElement);

		Element dynamicElement = loanProductDocument.createElement(LOAN_DYNAMIC_TAG);
		conditionsElement.appendChild(dynamicElement);

		for (Object o : tatypecrdlstArray)
		{
			Ikfltcrdtype loanType = (Ikfltcrdtype)o;
			addCreditTypeInfo(loanProductDocument, dynamicElement, loanType, LOAN_PRODUCT_REQUEST);
		}

		validate(loanProductDocument);
		
		return loanProductDocument;
	}

	/**
	 * заполняет информацию о кредитном продукте
	 * @throws GateException
	 */
	private void addCreditTypeInfo(Document document, Element root, Ikfltcrdtype loanType, String requestType) throws GateException
	{
		Element conditionElement = document.createElement(LOAN_CONDITION_TAG);
		root.appendChild(conditionElement);

		try
		{
			BigDecimal loanTypeId = loanType.getCredittypeid();
			if(loanTypeId != null)
				addValueField(document,conditionElement,CONDITION_ID_FIELD, loanTypeId.toPlainString());

			addValueField(document,conditionElement, LOAN_TYPE_NAME_FIELD, loanType.getCredittypename());

			String currency = loanType.getCurcodeiso();
			if ("RUR".equals(currency)){
				currency="RUB";
			}
			addValueField(document,conditionElement, CURRENCY_FIELD, currency);

			Double rate = loanType.getMainrate();
			if(rate != null)
				addValueField(document,conditionElement, RATE_FIELD, Double.toString(rate));

			Double penaltyPerDay = loanType.getPenaltyperday();
			if(penaltyPerDay != null)
				addValueField(document,conditionElement, PENALTY_PER_DAY_FIELD, Double.toString(penaltyPerDay));

			checkPeriodType(loanType.getTypeduration());

			addValueField(document,conditionElement, PENALTY_PER_DAY_FIELD, Double.toString(penaltyPerDay));

			final UserFieldsConfig config = UserFieldsConfigSingleton.getConfig();
			final BigDecimal[] userFieldsList = config.getLoansInfoUserFields();
			UserFieldParser<List<String>> parser = config.getLoanInfoUserFieldsParser(userFieldsList[0]);

			List<String> durations =parser.parse(loanType.getDuration().toPlainString());
			for (String duration : durations)
			{
				addValueField(document, conditionElement, config.getLoanInfoUserField(userFieldsList[0]), duration);
			}

			addAdditionFields(document, conditionElement, loanType.getAdditionfields(), requestType);
		}
		catch(SQLException ex)
		{
			throw new GateException("Ошибка при получении информации по кредитному продукту", ex);
		}
	}

	/**
	 * создает список <value>,для каждого доп. поля
	 * @throws GateException
	 */
	private void addAdditionFields(Document document, Element root, Ikfltauserfield fields, String requestType) throws GateException
	{
		UserFieldsConfig config = UserFieldsConfigSingleton.getConfig();

		if(fields != null)
		{
			try
			{
				Ikfltuserfield[] fieldArray = fields.getArray();

				for (Ikfltuserfield tuserfield : fieldArray)
				{
					List<String> values = null;
					String tag = null;

					BigDecimal userFieldId = tuserfield.getUserfieldid();
					String value = tuserfield.getUserfieldvalue();

					if(requestType.equals(LOAN_PRODUCT_REQUEST))
					{
						tag = config.getLoanProductUserField(userFieldId);
						UserFieldParser<List<String>> parser = config.getLoanProductUserFieldsParser(userFieldId);
						if(parser !=null)
						{
							values = parser.parse(value);
						}
					}

					if(values==null)
					{
						addValueField(document,root,tag,value);
					}
					else
					{
						for (String val : values)
						{
							addValueField(document,root,tag,val);
						}
					}
				}
			}
			catch(SQLException ex)
			{
				throw new GateException("Ошибка при получении пользовательских полей",ex);
			}

		}
	}

	/**
	 * проверяет, что тип периода - месяцы
	 * @throws GateException если не месяцы
	 */
	private void checkPeriodType(BigDecimal type) throws GateException
	{
		//поддерживаем только 0 - месяцы
		if(type.intValue()!=0)
		{
			throw new GateException("Неизвестное значение типа периода кредита:" +type.intValue());
		}
	}

	/**
	 * Создает элемент <fieldName>fieldValue</fieldName>
	 */
	private void addTextField(Document loanTypeDocument, Element loanElement, String fieldName,String fieldValue)
	{
		Element element = loanTypeDocument.createElement(fieldName);
		element.setTextContent(fieldValue);
		loanElement.appendChild(element);
	}

	/**
	 * создает элемент <value name="fieldName">fieldValue</value>
	 */
	private void addValueField(Document document, Element root, String fieldName,String fieldValue)
	{
		if(fieldValue ==null)return;

		Element element = document.createElement(LOAN_VALUE_TAG);
		element.setAttribute(ATTRIBUTE_NAME_TAG, fieldName);
		element.setTextContent(fieldValue.trim());
		root.appendChild(element);
	}
}
