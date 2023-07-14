<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au">
    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="initialCard" select="initialCard"/>
    
    <xsl:variable name="formData" select="/form-data"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit-auto-sub"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view-auto-sub"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="view">
        <xsl:variable name="activeCards" select="document('active-not-virtual-cards.xml')/entity-list/*"/>
        <xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>

        <receiver>
            <xsl:if test="string-length(receiverName) > 0">
                <name>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverName</xsl:with-param>
                        <xsl:with-param name="title">Наименование</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverName"/>
                    </xsl:call-template>
                </name>
            </xsl:if>
            <xsl:if test="string-length(nameService) > 0">
                <service>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">nameService</xsl:with-param>
                        <xsl:with-param name="title">Услуга</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="nameService"/>
                    </xsl:call-template>
                </service>
            </xsl:if>
            <xsl:if test="bankDetails = 'true'">
                <inn>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">receiverINN</xsl:with-param>
                        <xsl:with-param name="title">ИНН</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverINN"/>
                    </xsl:call-template>
                </inn>
                <account>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">receiverAccount</xsl:with-param>
                        <xsl:with-param name="title">Счет</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverAccount"/>
                    </xsl:call-template>
                </account>
                <bank>
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverBankName</xsl:with-param>
                            <xsl:with-param name="title">Наименование</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverBankName"/>
                        </xsl:call-template>
                    </name>
                    <bic>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverBIC</xsl:with-param>
                            <xsl:with-param name="title">БИК</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverBIC"/>
                        </xsl:call-template>
                    </bic>
                    <xsl:if test="string-length(receiverCorAccount) > 0">
                        <corAccount>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                <xsl:with-param name="title">Корсчет</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverCorAccount"/>
                            </xsl:call-template>
                        </corAccount>
                    </xsl:if>
                </bank>
            </xsl:if>
        </receiver>
        <fromResource>
            <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
            <xsl:call-template name="resourceField">
                <xsl:with-param name="name">fromResource</xsl:with-param>
                <xsl:with-param name="title">Ресурс списания</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="link" select="fromResource"/>
                <xsl:with-param name="activeCards" select="$activeCards"/>
                <xsl:with-param name="accountNumber" select="$fromAccountSelect"/>
                <xsl:with-param name="isView" select="$mode = 'view'"/>
            </xsl:call-template>
        </fromResource>
        <paymentDetails>
            <externalFields>
                <field>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">bankDetails</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="bankDetails"/>
                    </xsl:call-template>
                </field>

                <xsl:for-each select="$extendedFields">
                    <xsl:variable name="id" select="./NameBS"/>
                    <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
                    <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
                    <xsl:variable name="type" select="./Type"/>
                    <xsl:variable name="val">
                        <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                        <xsl:choose>
                            <!--если задано значение явно, юзаем его-->
                            <xsl:when test="string-length($currentValue) > 0">
                                <xsl:value-of select="$currentValue"/>
                            </xsl:when>
                            <!--иначе - инициализирующее значение-->
                            <xsl:otherwise>
                                <xsl:value-of select="./DefaultValue"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <xsl:variable name="changed" select="$formData/*[name()=$id]/@changed='true'"/>

                    <xsl:if test="not($isHideInConfirmation) and not($hidden) and not($type='calendar')">
                        <xsl:choose>
                            <xsl:when test="./Type = 'date'">
                                <field>
                                    <xsl:call-template name="dateBillingField">
                                        <xsl:with-param name="name" select="./NameBS"/>
                                        <xsl:with-param name="title" select="./NameVisible"/>
                                        <xsl:with-param name="description" select="./Description"/>
                                        <xsl:with-param name="hint" select="./Comment"/>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="./IsVisible"/>
                                        <xsl:with-param name="changed" select="$changed"/>
                                        <xsl:with-param name="value" select="$val"/>
                                    </xsl:call-template>
                                </field>
                            </xsl:when>
                            <xsl:when test="./Type = 'string'">
                                <field>
                                    <xsl:call-template name="stringBillingField">
                                        <xsl:with-param name="name" select="./NameBS"/>
                                        <xsl:with-param name="title" select="./NameVisible"/>
                                        <xsl:with-param name="description" select="./Description"/>
                                        <xsl:with-param name="hint" select="./Comment"/>
                                        <xsl:with-param name="minLength" select="./MinLength"/>
                                        <xsl:with-param name="maxLength" select="./MaxLength"/>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="./IsVisible"/>
                                        <xsl:with-param name="isSum" select="./IsSum"/>
                                        <xsl:with-param name="changed" select="$changed"/>
                                        <xsl:with-param name="value" select="$val"/>
                                    </xsl:call-template>
                                </field>
                            </xsl:when>
                            <xsl:when test="./Type = 'number'">
                                <field>
                                    <xsl:call-template name="numberBillingField">
                                        <xsl:with-param name="name" select="./NameBS"/>
                                        <xsl:with-param name="title" select="./NameVisible"/>
                                        <xsl:with-param name="description" select="./Description"/>
                                        <xsl:with-param name="hint" select="./Comment"/>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="./IsVisible"/>
                                        <xsl:with-param name="isSum" select="./IsSum"/>
                                        <xsl:with-param name="changed" select="$changed"/>
                                        <xsl:with-param name="value" select="$val"/>
                                    </xsl:call-template>
                                </field>
                            </xsl:when>
                            <xsl:when test="./Type = 'money'">
                                <field>
                                    <xsl:call-template name="moneyBillingField">
                                        <xsl:with-param name="name" select="./NameBS"/>
                                        <xsl:with-param name="title" select="./NameVisible"/>
                                        <xsl:with-param name="description" select="./Description"/>
                                        <xsl:with-param name="hint" select="./Comment"/>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="./IsVisible"/>
                                        <xsl:with-param name="isSum" select="./IsSum"/>
                                        <xsl:with-param name="changed" select="$changed"/>
                                        <xsl:with-param name="value" select="$val"/>
                                    </xsl:call-template>
                                </field>
                            </xsl:when>
                            <xsl:when test="./Type = 'list'">
                                <field>
                                    <!--поля типа list в этом платеже показываются только для просмотра, поэтому значение (если оно есть) показываем как простое строковое поле-->
                                    <xsl:call-template name="stringBillingField">
                                        <xsl:with-param name="name" select="./NameBS"/>
                                        <xsl:with-param name="title" select="./NameVisible"/>
                                        <xsl:with-param name="description" select="./Description"/>
                                        <xsl:with-param name="hint" select="./Comment"/>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="./IsVisible"/>
                                        <xsl:with-param name="isSum" select="./IsSum"/>
                                        <xsl:with-param name="changed" select="$changed"/>
                                        <xsl:with-param name="value" select="$val"/>
                                    </xsl:call-template>
                                </field>
                            </xsl:when>
                            <xsl:when test="./Type = 'set'">
                                <field>
                                    <xsl:call-template name="setBillingField">
                                        <xsl:with-param name="name" select="./NameBS"/>
                                        <xsl:with-param name="title" select="./NameVisible"/>
                                        <xsl:with-param name="description" select="./Description"/>
                                        <xsl:with-param name="hint" select="./Comment"/>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="./IsVisible"/>
                                        <xsl:with-param name="isSum" select="./IsSum"/>
                                        <xsl:with-param name="availableValues" select="./Menu/MenuItem"/>
                                        <xsl:with-param name="changed" select="$changed"/>
                                        <xsl:with-param name="checkedValues" select="$formData/*[name()=$id]/text()"/>
                                    </xsl:call-template>
                                </field>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:if>
                </xsl:for-each>
            </externalFields>
            <xsl:if test="string-length(promoCode)>0">
                <promoCode>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">promoCode</xsl:with-param>
                        <xsl:with-param name="title">Промо-код</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="promoCode"/>
                    </xsl:call-template>
                </promoCode>
            </xsl:if>
        </paymentDetails>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-auto-sub">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <xsl:variable name="autoSubType" select="autoSubType"/>

        <initialData>
            <form>EditAutoSubscriptionPayment</form>
            <EditAutoSubscriptionPayment>
                <number>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">autoSubNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер автоплатежа</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="autoSubNumber"/>
                    </xsl:call-template>
                </number>

                <xsl:call-template name="view"/>

                <autoSubDetails>
                    <startDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">autoSubStartDate</xsl:with-param>
                            <xsl:with-param name="title">Дата регистрации</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubStartDate"/>
                        </xsl:call-template>
                    </startDate>
                    <xsl:if test="string-length(autoSubUpdateDate)>0">
                        <updateDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">autoSubUpdateDate</xsl:with-param>
                                <xsl:with-param name="title">Дата изменения</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoSubUpdateDate"/>
                            </xsl:call-template>
                        </updateDate>
                    </xsl:if>
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubName</xsl:with-param>
                            <xsl:with-param name="title">Название автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubName"/>
                        </xsl:call-template>
                    </name>
                    <type>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubType</xsl:with-param>
                            <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubType"/>
                        </xsl:call-template>
                    </type>
                    <xsl:if test="$autoSubType = 'ALWAYS'">
                        <always>
                            <eventType>
                                <xsl:call-template name="autoSubEventType">
                                    <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="listValues"  select="$autoSubTypes/entity[@key='ALWAYS']/*"/>
                                    <xsl:with-param name="value" select="autoSubEventType"/>
                                </xsl:call-template>
                            </eventType>
                            <nextPayDate>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name">nextPayDateAlways</xsl:with-param>
                                    <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="nextPayDateAlways"/>
                                </xsl:call-template>
                            </nextPayDate>
                            <amount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">alwaysAmount</xsl:with-param>
                                    <xsl:with-param name="title">Сумма</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="false()"/>
                                    <xsl:with-param name="value" select="alwaysAmount"/>
                                </xsl:call-template>
                            </amount>
                        </always>
                    </xsl:if>
                    <xsl:if test="$autoSubType = 'INVOICE'">
                        <invoice>
                            <eventType>
                                <xsl:call-template name="autoSubEventType">
                                    <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="listValues" select="$autoSubTypes/entity[@key='INVOICE']/*"/>
                                    <xsl:with-param name="value" select="autoSubEventType"/>
                                </xsl:call-template>
                            </eventType>
                            <startDate>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name">nextPayDateInvoice</xsl:with-param>
                                    <xsl:with-param name="title">Ожидаемая дата оплаты счета</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="nextPayDateInvoice"/>
                                </xsl:call-template>
                            </startDate>
                            <amount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">invoiceMaxAmount</xsl:with-param>
                                    <xsl:with-param name="title">Максимальный размер платежа</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="false()"/>
                                    <xsl:with-param name="value" select="invoiceMaxAmount"/>
                                </xsl:call-template>
                            </amount>
                        </invoice>
                    </xsl:if>
                </autoSubDetails>
            </EditAutoSubscriptionPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-auto-sub">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <xsl:variable name="autoSubType" select="autoSubType"/>
        
        <document>
            <form>EditAutoSubscriptionPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <EditAutoSubscriptionDocument>
                <xsl:call-template name="view"/>
                
                <autoSubDetails>
                    <startDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">autoSubStartDate</xsl:with-param>
                            <xsl:with-param name="title">Дата регистрации</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value"  select="autoSubStartDate"/>
                        </xsl:call-template>
                    </startDate>
                    <xsl:if test="string-length(autoSubUpdateDate)>0">
                        <updateDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">autoSubUpdateDate</xsl:with-param>
                                <xsl:with-param name="title">Дата изменения</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value"  select="autoSubUpdateDate"/>
                            </xsl:call-template>
                        </updateDate>
                    </xsl:if>
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubName</xsl:with-param>
                            <xsl:with-param name="title">Название автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value"  select="autoSubName"/>
                        </xsl:call-template>
                    </name>
                    <type>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubType</xsl:with-param>
                            <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value"  select="autoSubType"/>
                        </xsl:call-template>
                    </type>
                    <xsl:if test="$autoSubType = 'ALWAYS'">
                        <always>
                            <eventType>
                                <xsl:call-template name="autoSubEventType">
                                    <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="listValues"  select="$autoSubTypes/entity[@key='ALWAYS']/*"/>
                                    <xsl:with-param name="value" select="autoSubEventType"/>
                                    <xsl:with-param name="isView" select="true()"/>
                                </xsl:call-template>
                            </eventType>
                            <nextPayDate>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name">nextPayDateAlways</xsl:with-param>
                                    <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value"  select="nextPayDateAlways"/>
                                </xsl:call-template>
                            </nextPayDate>
                            <amount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">alwaysAmount</xsl:with-param>
                                    <xsl:with-param name="title">Сумма</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value"  select="alwaysAmount"/>
                                </xsl:call-template>
                            </amount>
                        </always>
                    </xsl:if>
                    <xsl:if test="$autoSubType = 'INVOICE'">
                        <invoice>
                            <eventType>
                                <xsl:call-template name="autoSubEventType">
                                    <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="listValues"  select="$autoSubTypes/entity[@key='INVOICE']/*"/>
                                    <xsl:with-param name="value" select="autoSubEventType"/>
                                    <xsl:with-param name="isView" select="true()"/>
                                </xsl:call-template>
                            </eventType>
                            <startDate>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name">nextPayDateInvoice</xsl:with-param>
                                    <xsl:with-param name="title">Ожидаемая дата оплаты счета</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value"  select="nextPayDateInvoice"/>
                                </xsl:call-template>
                            </startDate>
                            <amount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">invoiceMaxAmount</xsl:with-param>
                                    <xsl:with-param name="title">Максимальный размер платежа</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value"  select="invoiceMaxAmount"/>
                                </xsl:call-template>
                            </amount>
                        </invoice>
                    </xsl:if>
                </autoSubDetails>
            </EditAutoSubscriptionDocument>
        </document>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="link"/>
        <xsl:param name="activeCards"/>
        <xsl:param name="isView" select="false()"/> <!--false - редактирование, true - просмотр-->

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">

                <resourceType>
                    <xsl:if test="count($activeCards)>0">
                        <availableValues>
                            <xsl:for-each select="$activeCards">
                                <xsl:variable name="selected" select="$accountNumber=./@key or $link=./field[@name='code']/text()"/>
                                <xsl:if test="not($isView) or $selected">
                                    <valueItem>
                                        <value><xsl:value-of select="./field[@name='code']/text()"/></value>
                                        <selected><xsl:value-of select="string($selected)"/></selected>
                                        <displayedValue>
                                            <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                        </displayedValue>
                                    </valueItem>
                                </xsl:if>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="autoSubEventType">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="listValues"/>
        <xsl:param name="value"/>
        <xsl:param name="isView" select="false()"/> <!--false - редактирование, true - просмотр-->

        <name><xsl:value-of select="$name"/></name>
        <title><xsl:value-of select="$title"/></title>
        <xsl:if test="string-length($description)>0">
            <description><xsl:value-of select="$description"/></description>
        </xsl:if>
        <xsl:if test="string-length($hint)>0">
            <hint><xsl:value-of select="$hint"/></hint>
        </xsl:if>
        <type>list</type>
        <required><xsl:value-of select="$required"/></required>
        <editable><xsl:value-of select="$editable"/></editable>
        <visible><xsl:value-of select="$visible"/></visible>
        <listType>
            <xsl:if test="count($listValues)>0">
                <availableValues>
                    <xsl:for-each select="$listValues">
                        <xsl:variable name="code">
                            <xsl:choose>
                                <xsl:when test="@name != ''">
                                    <xsl:value-of select="@name"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="."/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <xsl:variable name="selected" select="$code = $value"/>
                        <xsl:if test="not($isView) or $selected">
                            <valueItem>
                                <value><xsl:value-of select="$code"/></value>
                                <title><xsl:value-of select="."/></title>
                                <selected><xsl:value-of select="string($selected)"/></selected>
                            </valueItem>
                        </xsl:if>
                    </xsl:for-each>
                </availableValues>
            </xsl:if>
        </listType>
        <changed>false</changed>
    </xsl:template>

</xsl:stylesheet>