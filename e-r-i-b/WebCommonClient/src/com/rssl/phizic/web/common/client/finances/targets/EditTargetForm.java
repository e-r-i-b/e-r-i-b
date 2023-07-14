package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.payments.forms.validators.StartDateDistanceValidator;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * @author lepihina
 * @ created 23.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditTargetForm extends EditFormBase
{
	private static final long MAX_PERIOD = 100 * 365 + 24; // 100 ���
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final Form EDIT_FORM = createForm();
	private List<CardLink> moneyBoxChargeOffResources = new ArrayList<CardLink>();
	private Map<String, Boolean> moneyBoxChargeOffResourcesMBConnect = new HashMap<String, Boolean>();

	private String targetType;
	//���� �� � ������������ ������ ���������� ������ ��� ��������� ����� ������� ��� ���
	private boolean haveTariffTemplate;

	/**
	 * ���������� ��� ����������� ����
	 * @return ��� ����
	 */
	public String getTargetType()
	{
		return targetType;
	}

	public void setTargetType(String targetType)
	{
		this.targetType = targetType;
	}

	public boolean isHaveTariffTemplate()
	{
		return haveTariffTemplate;
	}

	public void setHaveTariffTemplate(boolean haveTariffTemplate)
	{
		this.haveTariffTemplate = haveTariffTemplate;
	}

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATE_FORMAT);

		MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
		moneyFieldValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999999999.99");
		moneyFieldValidator.setMessage("�������� � ���� ��������� ���� ������ ���� � ��������� 0,00 - 9 999 999 999 999 999,99 ���");

		DatePeriodFieldValidator datePeriodFieldValidator = new DatePeriodFieldValidator(DATE_FORMAT);
		datePeriodFieldValidator.setParameter(DatePeriodFieldValidator.BEFORE_DAY, "1");
		datePeriodFieldValidator.setParameter(DatePeriodFieldValidator.AFTER_DAY, Long.valueOf(MAX_PERIOD).toString());
		datePeriodFieldValidator.setMessage("����������, ������� ���� � ��������� �� 1 ��� �� 100 ��� ������������ ������� ����.");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����");
		fieldBuilder.setName("dictionaryTarget");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("targetName");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.dictionaryTarget=='OTHER'"));
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{1,35}", "�������� ���� ������ ���� �� ����� 35 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� � ��������");
		fieldBuilder.setName("targetNameComment");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,100}", "����������� � �������� ���� ������ ���� �� ����� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ���� ������������ ����");
		fieldBuilder.setName("targetPlanedDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				requiredFieldValidator,
				datePeriodFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����");
		fieldBuilder.setName("targetAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				moneyFieldValidator,
				requiredFieldValidator
		);
		fb.addField(fieldBuilder.build());

		addMoneyBoxFields(fb);

		return fb.build();
	}

	private static void addMoneyBoxFields(FormBuilder fb)
	{
		RhinoExpression enabledMoneyBoxExpression = new RhinoExpression("form.createMoneyBox");
		MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
		moneyFieldValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999999999.99");
		moneyFieldValidator.setMessage("�������� � ���� ����� ������ ���� � ��������� 0,00 - 9 999 999 999 999 999,99 ���");
		DateParser dateParser = new DateParser(DATE_FORMAT);
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �������");
		fieldBuilder.setName("createMoneyBox");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����������");
		fieldBuilder.setName("moneyBoxSumType");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(enabledMoneyBoxExpression);
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������");
		fieldBuilder.setName("longOfferEventType");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(enabledMoneyBoxExpression);
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("moneyBoxName");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(enabledMoneyBoxExpression);
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ���������� ��������");
		fieldBuilder.setName("longOfferStartDate");
		fieldBuilder.setType("string");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.createMoneyBox && form.moneyBoxSumType =='FIXED_SUMMA'"));
		fieldBuilder.addValidators(requiredFieldValidator, new DateFieldValidator(DATE_FORMAT), new DateNotInPastValidator(DATE_FORMAT));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("moneyBoxSellAmount");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(enabledMoneyBoxExpression);
		RequiredFieldValidator reqSumValidator = new RequiredFieldValidator();
		reqSumValidator.setEnabledExpression(new RhinoExpression("form.createMoneyBox && form.moneyBoxSumType =='FIXED_SUMMA'"));
		fieldBuilder.addValidators(reqSumValidator, moneyFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������");
		fieldBuilder.setName("moneyBoxFromResource");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(enabledMoneyBoxExpression);
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("moneyBoxPercent");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.createMoneyBox && form.moneyBoxSumType !='FIXED_SUMMA'"));
		RequiredFieldValidator reqPercentValidator = new RequiredFieldValidator();
		reqPercentValidator.setEnabledExpression(new RhinoExpression("form.createMoneyBox =='true' && form.moneyBoxSumType !='FIXED_SUMMA'"));
		fieldBuilder.addValidators();
		fb.addField(fieldBuilder.build());

		StartDateDistanceValidator distanceValidator = new StartDateDistanceValidator();
		distanceValidator.setBinding("startDate", "longOfferStartDate");
		distanceValidator.setBinding("eventType", "longOfferEventType");
		distanceValidator.setEnabledExpression(new RhinoExpression("form.createMoneyBox && form.moneyBoxSumType =='FIXED_SUMMA'"));

		fb.addFormValidators(distanceValidator);
	}

	public List<CardLink> getMoneyBoxChargeOffResources()
	{
		return Collections.unmodifiableList(moneyBoxChargeOffResources);
	}

	public void setMoneyBoxChargeOffResources(List<CardLink> moneyBoxChargeOffResources)
	{
		this.moneyBoxChargeOffResources = moneyBoxChargeOffResources;
	}

	public Map<String, Boolean> getMoneyBoxChargeOffResourcesMBConnect()
	{
		return Collections.unmodifiableMap(moneyBoxChargeOffResourcesMBConnect);
	}

	public void setMoneyBoxChargeOffResourcesMBConnect(Map<String, Boolean> map)
	{
		moneyBoxChargeOffResourcesMBConnect = map;
	}
}
