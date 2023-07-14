package com.rssl.phizic.web.pin;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.operations.security.CreatePINRequestOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.*;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * @author Roshka
 * @ created 15.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class CreatePINPrintingRequestAction extends OperationalActionBase
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
		CreatePINRequestOperation operation = createOperation(CreatePINRequestOperation.class);
		return mapping.findForward(FORWARD_START);
	}

	private Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		//поле для ввода кол-ва PIN конвертов
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("count");
		RegexpFieldValidator regexpFieldValidatorCount = new RegexpFieldValidator("\\d{1,3}");
		regexpFieldValidatorCount.setMessage("Неверный формат данных в поле: количество PIN-конвертов, введите числовое значение в диапазоне 1...100.");

		NumericRangeValidator numericRangeValidatorCount = new NumericRangeValidator();
		numericRangeValidatorCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		numericRangeValidatorCount.setMessage("Неверный формат данных в поле: количество PIN-конвертов, введите числовое значение в диапазоне 1...100.");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder.setValidators(requiredFieldValidator, regexpFieldValidatorCount, numericRangeValidatorCount);
		fieldBuilder.setDescription("Количество PIN конвертов");

		formBuilder.addField(fieldBuilder.build());
		//Номер тер.банка валидатор только на него, остальные могут быть нулями
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
 		fieldBuilder.setValidators(requiredFieldValidator);
		fieldBuilder.setDescription("Подразделение");
		formBuilder.addField(fieldBuilder.build());
		//Для заполнения названия департамента
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentName");
		fieldBuilder.setDescription("Название подразделения");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePINPrintingRequestForm frm = (CreatePINPrintingRequestForm) form;

		MapValuesSource valuesSource = new MapValuesSource(frm.getFields());

		Form editForm = createForm();
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, editForm);

		if ( formProcessor.process() )
		{
			CreatePINRequestOperation operation = createOperation(CreatePINRequestOperation.class);
			String count = (String) frm.getField("count");
			String departmentId = (String) frm.getField("id");

			operation.setCount(Integer.valueOf(count));
			operation.setDepartment(Long.valueOf(departmentId));


			Document document;
			try
			{
				document = operation.createRequest();
			}
			catch (Exception e)
			{
				ActionMessages actionMessages = new ActionMessages();
				ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.createRequest", e.getMessage());
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);
				saveErrors(request, actionMessages);

				return mapping.findForward(FORWARD_START);
			}

			writeToOutput(operation.getRequestNumber(), document, response);

			frm.setField("count", count);

			addLogParameters(new CompositeLogParametersReader(
						new SimpleLogParametersReader("Обрабатываемая сущность", "Количество PIN-конвертов в запросе", count),
						new SimpleLogParametersReader("ID подразделения", departmentId)
				));
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		return null;
	}

	private void writeToOutput(String requestNumber, Document document, HttpServletResponse response)
			throws IOException, TransformerException
	{
		response.setContentType("application/x-file-download");
		response.setHeader("Content-disposition", "attachment; filename=" + "print_pin_request_" + requestNumber + ".xml");

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