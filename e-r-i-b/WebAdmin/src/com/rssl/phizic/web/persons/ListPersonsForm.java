package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 08.09.2005
 * Time: 21:20:11
 * @noinspection ReturnOfCollectionOrArrayField,AssignmentToCollectionOrArrayFieldFromParameter
 */
public class ListPersonsForm extends ListLimitActionForm
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static final String CLIENT_FIO_FIELD_NAME = "fio";
	public static final String AGREEMENT_NUMBER_FIELD_NAME = "agreementNumber";
	public static final String LOGIN_ID_FIELD_NAME = "loginId";
	public static final String DOCUMENT_NUMBER_FIELD_NAME = "documentNumber";
	public static final String DOCUMENT_SERIES_FIELD_NAME = "documentSeries";
	public static final String EMPTY_REQUIRED_FIELDS = "Пожалуйста, заполните хотя бы одно из полей фильтра: клиент, Login_ID, серия и номер, дата рождения и имя клиента в поле клиент, номер договора.";

    private boolean isSelectAll=false;
	private String blockReason;

	private String blockStartDate;
	private String blockEndDate;

    private String accessSchemeId= "simple_"+0;
    private List<SharedAccessScheme> accessSchemes=new ArrayList<SharedAccessScheme>();
    private List<Long> accessSchemesKeys=new ArrayList<Long>();
    private List<String> accessSchemesValues=new ArrayList<String>();
	//список ТБ, доступных сотруднику
	private List<Department> allowedTB = new ArrayList<Department>();

    public boolean getIsSelectAll()
    {
        return isSelectAll;
    }
    public void setIsSelectAll(boolean isSelectAll)
    {
            this.isSelectAll=isSelectAll;
    }

    public String getAccessSchemeId()
    {
        return accessSchemeId;
    }

    public void setAccessSchemeId(String accessSchemeId)
    {
        this.accessSchemeId = accessSchemeId;
    }

    public List<SharedAccessScheme> getAccessSchemes()
    {
        return accessSchemes;
    }

    public void setAccessSchemes(List<SharedAccessScheme> accessSchemes)
    {
        accessSchemesKeys = new ArrayList<Long>();
        accessSchemesValues = new ArrayList<String>();

	    for (Object accessScheme : accessSchemes)
	    {
		    AccessScheme scheme = (AccessScheme) accessScheme;
		    accessSchemesKeys.add(scheme.getId());
		    accessSchemesValues.add(scheme.getName());
	    }

        this.accessSchemes = accessSchemes;
    }

    public List<Long> getAccessSchemesKeys()
    {
        return accessSchemesKeys;
    }

    public List<String> getAccessSchemesValues()
    {
        return accessSchemesValues;
    }

    public void clearSelection() {
        selectedIds=new String[0];
        isSelectAll=false;
    }

	public String getBlockReason()
	{
		return blockReason;
	}

	public void setBlockReason(String blockReason)
	{
		this.blockReason = blockReason;
	}

	public String getBlockStartDate()
	{
		return blockStartDate;
	}

	public void setBlockStartDate(String blockStartDate)
	{
		this.blockStartDate = blockStartDate;
	}

	public String getBlockEndDate()
	{
		return blockEndDate;
	}

	public void setBlockEndDate(String blockEndDate)
	{
		this.blockEndDate = blockEndDate;
	}

	public List<Department> getAllowedTB()
	{
		return allowedTB;
	}

	public void setAllowedTB(List<Department> allowedTB)
	{
		this.allowedTB = allowedTB;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm(){

		FormBuilder formBuilder = new FormBuilder();
		DateParser dateParser = new DateParser(DATESTAMP);

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("id");
		fieldBuilder.setName("id");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setName("birthDay");
		fieldBuilder.setType("date");
		fieldBuilder.addValidators(	new DateFieldValidator(DATESTAMP, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Клиент");
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setType("string");
		FieldValidator reqFieldValidator = new RequiredFieldValidator("Пожалуйста, заполните поле клиент.");
		reqFieldValidator.setEnabledExpression(new RhinoExpression("form.birthDay != '' && form.birthDay != null"));
		fieldBuilder.addValidators(reqFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Мобильный телефон");
		fieldBuilder.setName("mobile_phone");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.addValidators(
                new RegexpFieldValidator("\\d*", "Поле Мобильный телефон должно содержать только цифры")
		);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер пин-конверта");
		fieldBuilder.setName("pinEnvelopeNumber");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Login_ID");
		fieldBuilder.setName(LOGIN_ID_FIELD_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","Login_ID может содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер документа");
		fieldBuilder.setName(DOCUMENT_NUMBER_FIELD_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
                new RegexpFieldValidator("\\d*", "Поле номер документа должно содержать только цифры"),
                new RegexpFieldValidator(".{0,16}", "Поле номер документа не должно превышать 16 цифр")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия документа");
		fieldBuilder.setName(DOCUMENT_SERIES_FIELD_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,16}", "Поле серия документа не должно превышать 16 символов"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","Поле серия документа не должно содержать спецсимволов")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип документа");
		fieldBuilder.setName("documentType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер договора");
		fieldBuilder.setName(AGREEMENT_NUMBER_FIELD_NAME);
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип договора");
		fieldBuilder.setName("creationType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тербанк");
		fieldBuilder.setName("terBank");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		RequiredMultiFieldValidator multiFieldValidatorDocNumber = new RequiredMultiFieldValidator();
		multiFieldValidatorDocNumber.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		multiFieldValidatorDocNumber.setBinding(LOGIN_ID_FIELD_NAME, LOGIN_ID_FIELD_NAME);
		multiFieldValidatorDocNumber.setBinding(AGREEMENT_NUMBER_FIELD_NAME, AGREEMENT_NUMBER_FIELD_NAME);
		multiFieldValidatorDocNumber.setBinding(DOCUMENT_NUMBER_FIELD_NAME, DOCUMENT_NUMBER_FIELD_NAME);
		multiFieldValidatorDocNumber.setMessage(EMPTY_REQUIRED_FIELDS);

		RequiredMultiFieldValidator multiFieldValidatorDocSeries = new RequiredMultiFieldValidator();
		multiFieldValidatorDocSeries.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		multiFieldValidatorDocSeries.setBinding(LOGIN_ID_FIELD_NAME, LOGIN_ID_FIELD_NAME);
		multiFieldValidatorDocSeries.setBinding(AGREEMENT_NUMBER_FIELD_NAME, AGREEMENT_NUMBER_FIELD_NAME);
		multiFieldValidatorDocSeries.setBinding(DOCUMENT_SERIES_FIELD_NAME, DOCUMENT_SERIES_FIELD_NAME);
		multiFieldValidatorDocSeries.setMessage(EMPTY_REQUIRED_FIELDS);

		formBuilder.addFormValidators(multiFieldValidatorDocNumber,multiFieldValidatorDocSeries);
		return formBuilder.build();
    }
}
