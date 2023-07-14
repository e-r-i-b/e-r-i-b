package com.rssl.phizic.web.passwordcards;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.passwordcards.CreateCardsPrintingRequestOperation;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

/**
 * @author Khivin
 * @ created 26.09.2007
 */

public class CreateCardsPrintingRequestAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();

		map.put("button.download", "download");

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FilterActionForm frm = (FilterActionForm) form;
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		frm.setField("cardsCount", String.valueOf(securityConfig.getCardsCount()));
		frm.setField("keysCount", String.valueOf(securityConfig.getCardPasswords()));
		frm.setField("id", String.valueOf(0));

		return mapping.findForward(FORWARD_START);
	}

	private Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		//поля для ввода кол-ва карт и кол-ва паролей
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardsCount");
		fieldBuilder.setType("integer");
		RegexpFieldValidator regexpFieldValidatorCardsCount = new RegexpFieldValidator("\\d{1,3}");
		regexpFieldValidatorCardsCount.setMessage("Неверный формат данных в поле: количество карт, введите числовое значение в диапазоне 1...100.");

		NumericRangeValidator numericRangeValidatorCardsCount = new NumericRangeValidator();
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		numericRangeValidatorCardsCount.setMessage("Неверный формат данных в поле: количество карт, введите числовое значение в диапазоне 1...100.");

		RequiredFieldValidator cardCountRequiredFieldValidator = new RequiredFieldValidator();
		cardCountRequiredFieldValidator.setMessage( "Не задано количество карт.");

		fieldBuilder.setValidators(regexpFieldValidatorCardsCount, numericRangeValidatorCardsCount, cardCountRequiredFieldValidator);
		fieldBuilder.setDescription("Количество карт");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("keysCount");
		fieldBuilder.setType("integer");
		RegexpFieldValidator regexpFieldValidatorKeysCount = new RegexpFieldValidator("\\d{1,3}");
		regexpFieldValidatorKeysCount.setMessage("Неверный формат данных в поле: количество ключей, введите числовое значение в диапазоне 1...500.");

		NumericRangeValidator numericRangeValidatorKeysCount = new NumericRangeValidator();
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "500");
		numericRangeValidatorKeysCount.setMessage("Неверный формат данных в поле: количество ключей, введите числовое значение в диапазоне 1...500.");

		RequiredFieldValidator keyCountRequiredFieldValidator = new RequiredFieldValidator();
		keyCountRequiredFieldValidator.setMessage( "Не задано количество ключей.");

		fieldBuilder.setValidators(regexpFieldValidatorKeysCount, numericRangeValidatorKeysCount, keyCountRequiredFieldValidator);
		fieldBuilder.setDescription("Количество ключей");
		formBuilder.addField(fieldBuilder.build());

		//
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setMessage( "Не задано подразделение.");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
		fieldBuilder.setValidators(requiredFieldValidator);
		fieldBuilder.setDescription("Подразделение");
		formBuilder.addField(fieldBuilder.build());

		//Для заполнения названия департамента
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentName");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}


	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FilterActionForm frm = (FilterActionForm) form;

		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());

		Form filterForm = createForm();

		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, filterForm);

		if (formProcessor.process())
		{
			CreateCardsPrintingRequestOperation operation = createOperation(CreateCardsPrintingRequestOperation.class);

			// создадим запрос
			Document document;
			try
			{
				Map<String, Object> result = formProcessor.getResult();
				BigInteger cardsCount = (BigInteger) result.get("cardsCount");
				BigInteger keysCount = (BigInteger) result.get("keysCount");
				String departmentIdStr = (String)result.get("id");
				BigInteger departmentId = new BigInteger( departmentIdStr);

				operation.initialize(
						cardsCount.intValue(),
						keysCount.intValue(),
						departmentId.longValue()
				);

				document = operation.createRequest();

				addLogParameters(new CompositeLogParametersReader(
						new SimpleLogParametersReader("Обрабатываемая сущность", "Количество карт в запросе", cardsCount.toString()),
						new SimpleLogParametersReader("Количество ключей в карте", keysCount.toString()),
						new SimpleLogParametersReader("ID подразделения", departmentIdStr)
				));
			}
			catch (Exception e)
			{
 				ActionMessages actionMessages = new ActionMessages();
				ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.createRequest", e.getMessage());
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);
				saveErrors(request, actionMessages);

				return mapping.findForward(FORWARD_START);
			}

			writeToOutput( document, response);
			return null;
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
	}

	private void writeToOutput( Document document, HttpServletResponse response)
			throws IOException, TransformerException
	{
		Element root = document.getDocumentElement();
		// получить номер запроса, чтобы подставить в имя файла 
		String requestNumber = XmlHelper.getSimpleElementValue(root, "number");
		
		response.setContentType("application/x-file-download");
		response.setHeader("Content-disposition", "attachment; filename=" + "print_cards_request_" + requestNumber +".xml");

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "Windows-1251");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource source = new DOMSource(document);

		ServletOutputStream outputStream = response.getOutputStream();
		StreamResult result = new StreamResult(outputStream);
		transformer.transform(source, result);

		outputStream.flush();
	}
}