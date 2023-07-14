package com.rssl.phizic.web.loans.loanOffer.unload;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loans.loanOffer.ClaimsHoursValidator;

import java.math.BigInteger;

/**
 * User: Moshenko
 * Date: 22.06.2011
 * Time: 16:55:22
 * Форма настройки авто-выгрузки кредитных заявок
 */
public class AutoUnloadOfferForm extends EditFormBase
{
	private static final String RADIO_HOURS = "Hours";
	public static String TIMESTAMP = "HH:mm";

	private String loanProductRadio = RADIO_HOURS;        //Заявки на кредиты (Hours/Days)
	private String loanOfferRadio = RADIO_HOURS;          //Заявки на предодобренные кредиты (Hours/Days)
	private String loanCardProductRadio = RADIO_HOURS;    //Заявки на кредитные карты (Hours/Days)
	private String loanCardOfferRadio = RADIO_HOURS;      //Заявки на предодобренные кредитные карты (Hours/Days)
	private String virtualCardRadio = RADIO_HOURS;        //Заявки на виртуальные карты

	public String getLoanProductRadio()
	{
		return loanProductRadio;
	}

	public void setLoanProductRadio(String loanProductRadio)
	{
		this.loanProductRadio = loanProductRadio;
	}

	public String getLoanOfferRadio()
	{
		return loanOfferRadio;
	}

	public void setLoanOfferRadio(String loanOfferRadio)
	{
		this.loanOfferRadio = loanOfferRadio;
	}

	public String getLoanCardProductRadio()
	{
		return loanCardProductRadio;
	}

	public void setLoanCardProductRadio(String loanCardProductRadio)
	{
		this.loanCardProductRadio = loanCardProductRadio;
	}

	public String getLoanCardOfferRadio()
	{
		return loanCardOfferRadio;
	}

	public void setLoanCardOfferRadio(String loanCardOfferRadio)
	{
		this.loanCardOfferRadio = loanCardOfferRadio;
	}

	public String getVirtualCardRadio()
	{
		return virtualCardRadio;
	}

	public void setVirtualCardRadio(String virtualCardRadio)
	{
		this.virtualCardRadio = virtualCardRadio;
	}

	public static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		DateParser timeParser = new DateParser(TIMESTAMP);

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		DateFieldValidator timeFieldValidator = new DateFieldValidator(TIMESTAMP, "Дата должна быть в формате ЧЧ:ММ");

		RegexpFieldValidator dayFieldValidator = new RegexpFieldValidator("^{1}[1-7]", "Можно указать промежуток не  более 7 дней");
		RegexpFieldValidator hourFieldValidator = new RegexpFieldValidator("^{1}[1-9]*", "В значении часа нельзя указывать лидирующий ноль ");
		FieldValidator unloadClaimsHoursValidator = new ClaimsHoursValidator();

		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", new BigInteger("150"));
		lengthValidator.setMessage("Каталог выгрузки должн быть не более 150 символов");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductEnabled");
		fieldBuilder.setDescription("Заявки на кредиты");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferEnabled");
		fieldBuilder.setDescription("Заявки на предодобренные кредиты");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductEnabled");
		fieldBuilder.setDescription("Заявки на кредитные карты");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferEnabled");
		fieldBuilder.setDescription("Заявки на предодобренные кредитные карты");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardEnabled");
		fieldBuilder.setDescription("Заявки на вирутальные карты");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductRadio");
		fieldBuilder.setDescription("Заявки на кредиты");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferRadio");
		fieldBuilder.setDescription("Заявки на предодобренные кредиты");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductRadio");
		fieldBuilder.setDescription("Заявки на кредитные карты");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferRadio");
		fieldBuilder.setDescription("Заявки на предодобренные кредитные карты");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardRadio");
		fieldBuilder.setDescription("Заявки на вирутальные карты");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductFolder");
		fieldBuilder.setDescription("Заявки на кредиты: Каталог выгрузки");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferFolder");
		fieldBuilder.setDescription("Заявки на предодобренные кредиты: Каталог выгрузки");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductFolder");
		fieldBuilder.setDescription("Заявки на кредитные карты: Каталог выгрузки");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferFolder");
		fieldBuilder.setDescription("Заявки на предодобренные кредитные карты: Каталог выгрузки");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardFolder");
		fieldBuilder.setDescription("Заявки на виртуальные карты: Каталог выгрузки");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductHour");
		fieldBuilder.setDescription("Заявки на кредиты: Периодичность: часы");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductRadio=='Hours'&&form.loanProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductDay");
		fieldBuilder.setDescription("Заявки на кредиты: Периодичность: дней");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductRadio=='Days'&&form.loanProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductDayTime");
		fieldBuilder.setDescription("Заявки на кредиты: Периодичность: дней в(время)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductRadio=='Days'&&form.loanProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferHour");
		fieldBuilder.setDescription("Заявки на предодобренные кредиты: Периодичность: часы");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferRadio=='Hours'&&form.loanOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferDay");
		fieldBuilder.setDescription("Заявки на предодобренные кредиты: Периодичность: дней");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferRadio=='Days'&&form.loanOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferDayTime");
		fieldBuilder.setDescription("Заявки на предодобренные кредиты: Периодичность: дней в(время)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferRadio=='Days'&&form.loanOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductHour");
		fieldBuilder.setDescription("Заявки на кредитные карты: Периодичность: часы");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductRadio=='Hours'&&form.loanCardProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductDay");
		fieldBuilder.setDescription("Заявки на кредитные карты: Периодичность: дней");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductRadio=='Days'&&form.loanCardProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductDayTime");
		fieldBuilder.setDescription("Заявки на кредитные карты: Периодичность: дней в(время)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductRadio=='Days'&&form.loanCardProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferHour");
		fieldBuilder.setDescription("Заявки на предодобренные кредитные карты: Периодичность: часы");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferRadio=='Hours'&&form.loanCardOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferDay");
		fieldBuilder.setDescription("Заявки на предодобренные кредитные карты:  Периодичность: дней");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferRadio=='Days'&&form.loanCardOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferDayTime");
		fieldBuilder.setDescription("Заявки на предодобренные кредитные карты:  Периодичность: дней в(время)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferRadio=='Days'&&form.loanCardOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardHour");
		fieldBuilder.setDescription("Заявки на виртуальные карты: Периодичность: часы");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardRadio=='Hours'&&form.virtualCardEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardDay");
		fieldBuilder.setDescription("Заявки на виртуальные карты: Периодичность: дней");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardRadio=='Days'&&form.virtualCardEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardDayTime");
		fieldBuilder.setDescription("Заявки на виртуальные карты: Периодичность: дней в(время)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardRadio=='Days'&&form.virtualCardEnabled != false"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
