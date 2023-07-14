<?xml version="1.0" encoding="windows-1251"?>
<payment-form name="AccountChangeInterestDestinationClaim" description="Дополнительное соглашение к договору о вкладе"
              detailedDescription="Заполните форму и нажмите на кнопку «Продолжить»."
              confirmDescription="Вы открыли вклад в Сбербанке России! Для того чтобы получить экземпляр «Условий по размещению денежных средств», обратитесь в ближайшее отделение банка."
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:noNamespaceSchemaLocation="../../PaymentForm.xsd">

	<implementation class="com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim"/>
	<statemachine name="PaymentStateMachine"/>

	<fields>
        <field name="accountCode"
               type="string"
               source="extra-parameters/parameter[@name='account-code']/@value"
               description="Идентификатор счета, для которого изменяется порядок уплаты процентов"/>

        <field name="documentNumber"
               description="Номер документа"
               type="string"
               source="document-number"
               signable="true"/>

        <field name="documentDate"
               description="Дата документа"
               type="date"
               source="document-date"
               signable="true"/>

        <field name="interestDestinationSource"
               description="Порядок уплаты процентов"
               source="extra-parameters/parameter[@name='interest-destination-source']/@value"
               type="string"
               signable="true"/>

        <field name="cardLink"
               description="Карта"
               type="resource"
               source="extra-parameters/parameter[@name='card']/@value">
               <validators>
                    <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"
                               enabled="form.interestDestinationSource == 'card'">
                        <message text="Укажите, пожалуйста, карту для перечисления процентов."/>
                    </validator>
               </validators>
        </field>

        <field name="interestCardNumber"
               description="Номер карты для зачисления процентов"
               type="string"
               source="extra-parameters/parameter[@name='interest-card-number']/@value"
               value="form.cardLink==null?null:form.cardLink.getNumber()"
               signable="true">
        </field>

        <field name="state"
               source="state"
               description="Статус платежа"
               type="string"/>
    </fields>

</payment-form>
