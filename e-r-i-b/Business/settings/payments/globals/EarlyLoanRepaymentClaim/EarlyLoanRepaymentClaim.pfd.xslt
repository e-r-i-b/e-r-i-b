<?xml version="1.0" encoding="windows-1251"?>
<payment-form name="EarlyLoanRepaymentClaim"
              description="Заявка на досрочное погашение"
              detailedDescription="Заявка на досрочное погашение"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:noNamespaceSchemaLocation="../../PaymentForm.xsd">
	<implementation class="com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl"/>
    <statemachine name="EarlyLoanRepaymentClaim"/>
    <extended-metadata-loader class="com.rssl.phizic.business.payments.forms.meta.EarlyLoanRepaymentMetadataLoader"/>

    <fields>
        <field name="documentNumber"
               description="Номер документа"
               source="document-number"
               type="string"/>

        <field name="partial"
               description="Признак частичного платежа"
               source="extra-parameters/parameter[@name='partial']/@value"
               type="string"
               inital="false"/>

        <field name="loanLinkId"
               description="Идентификатор кредитного договора"
               source="extra-parameters/parameter[@name='loanLinkId']/@value"
               type="long"/>

        <field  name="state"
                description="Статус платежа"
                type="string"
                source="state">
        </field>

        <field name="amount"
               description="Сумма"
               type="money"
               source="amount">
            <validators>
                <validator class="com.rssl.common.forms.validators.RequiredFieldValidator" enabled="form.partial == 'true'">
                    <message text="Укажите сумму платежа."/>
                </validator>
            </validators>
        </field>

        <field name="departmentId"
               source="departmentId"
               description="Офис банка"
               type="string"/>

        <field name="exactAmount"
               description="Сумма списания (зачисления)"
               type="string"
               source="exact-amount"
               value="'charge-off-field-exact'"/>

        <field  name="documentDate"
                description="Дата документа"
                type="date"
                source="document-date"
                signable="true">
            <validators>
                <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
                <validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepaymentDateValidator">
                    <message text="Для выбора даты досрочного погашения воспользуйтесь календарем."/>
                </validator>
            </validators>
        </field>

        <field  name="fromResource"
                description="Источник списания средств: счет, карта"
                type="resource"
                source="extra-parameters/parameter[@name='from-resource']/@value">

            <validators>
                <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
            </validators>
        </field>

        <field  name="fromResourceType"
                description="Тип источника списания средств: счет, карта"
                type="string"
                source="extra-parameters/parameter[@name='from-resource-type']/@value"
                value="form.fromResource==null?null:form.fromResource.getClass().getName()">
        </field>

        <field  name="fromAccountSelect"
                description="Счет/карта списания"
                type="string"
                source="payer-account"
                value="form.fromResource==null?null:form.fromResource.getNumber()"
                signable="true">
		</field>

        <field  name="fromAccountType"
                description="Тип (описание) источника списания"
                type="string"
                source="extra-parameters/parameter[@name='from-account-type']/@value"
                value="form.fromResource==null?null:form.fromResource.getValue().getDescription()"
                signable="true">
        </field>

        <field  name="fromAccountName"
                description="Наименование источника списания"
                type="string"
                source="extra-parameters/parameter[@name='from-account-name']/@value"
                value="form.fromResource==null?null:form.fromResource.getName()"
                signable="true">
        </field>

        <field name="fromResourceCurrency"
               description="Валюта"
               type="string"
               source="extra-parameters/parameter[@name='from-resource-currency']/@value"
               value="form.fromAccountSelect==null?null:form.fromResource.getCurrency().getCode()"
               signable="true">
        </field>

        <field  name="operationDate"
                description="Дата отправки платежа"
                source="operation-date-short-year"
                type="string">
        </field>

        <field  name="operationTime"
                description="Время исполнения платежа"
                source="operation-time"
                type="string">
        </field>

        <field  name="selectedResourceRest"
                description="Остаток на выбранном счету"
                source="selected-resource-rest"
                type="string">
        </field>

    </fields>

    <form-validators>
        <form-validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepaymentClosureDateValidator">
            <message text="Дата досрочного погашения не должна быть больше даты окончания кредита."/>
        </form-validator>
        <form-validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepaymentAmountValidator">
            <message text="Сумма для досрочного погашения кредита не должна быть меньше минимальной или превышать максимальную."/>
        </form-validator>
        <form-validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepWorkDayValidator">
            <message text="Дата досрочного погашения не должна попадать на выходные и праздничные дни. Пожалуйста, выберите другую дату."/>
        </form-validator>
    </form-validators>

</payment-form>