<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mu mask au">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>

            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <initialData>
            <form>AccountOpeningClaim</form>
            <AccountOpeningClaim>
                <xsl:call-template name="initialData"/>
            </AccountOpeningClaim>
        </initialData>
    </xsl:template>

    <xsl:template name="initialData">
        <xsl:variable name="activeAccounts" select="document('active-debit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
        <xsl:variable name="depositDescriptions" select="document(concat( 'deposit-products.xml?depositId=', depositType ) )/product/data"/>

        <documentNumber>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">documentNumber</xsl:with-param>
                <xsl:with-param name="title">Номер документа</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="documentNumber"/>
            </xsl:call-template>
        </documentNumber>

        <documentDate>
            <xsl:call-template name="dateField">
                <xsl:with-param name="name">documentDate</xsl:with-param>
                <xsl:with-param name="title">Дата документа</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="documentDate"/>
            </xsl:call-template>
        </documentDate>

        <depositName>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">depositName</xsl:with-param>
                <xsl:with-param name="title">Открыть вклад</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="$depositDescriptions/name"/>
            </xsl:call-template>
        </depositName>

        <openDate>
            <xsl:call-template name="dateField">
                <xsl:with-param name="name">openDate</xsl:with-param>
                <xsl:with-param name="title">Дата открытия</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="openDate"/>
            </xsl:call-template>
        </openDate>

        <xsl:variable name="currencies">
            <xsl:for-each select="xalan:distinct($depositDescriptions/options/element/currencyCode)">
                <xsl:element name="entity">
                    <xsl:attribute name="key"><xsl:value-of select="./text()"/></xsl:attribute>
                    <xsl:value-of select="mu:getCurrencySign(./text())"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:variable>

        <toResourceCurrency>
            <xsl:call-template name="listField">
                <xsl:with-param name="name">toResourceCurrency</xsl:with-param>
                <xsl:with-param name="title">Валюта</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="listValues" select="xalan:nodeset($currencies)/node()"/>
                <xsl:with-param name="value" select="toResourceCurrency"/>
            </xsl:call-template>
        </toResourceCurrency>

        <needInitialFee>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">needInitialFee</xsl:with-param>
                <xsl:with-param name="title">Требуется начальный взнос</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="needInitialFee"/>
            </xsl:call-template>
        </needInitialFee>

        <withMinimumBalance>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">withMinimumBalance</xsl:with-param>
                <xsl:with-param name="title">Есть ли у вклада неснижаемый остаток</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="withMinimumBalance"/>
            </xsl:call-template>
        </withMinimumBalance>

        <isPension>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">isPension</xsl:with-param>
                <xsl:with-param name="title">Является ли вклад пенсионным</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value">
                    <xsl:variable name="pensionElements" select="$depositDescriptions/options/element[pension = 'true']"/>
                    <xsl:choose>
                        <xsl:when test="count($pensionElements) &gt; 0 and isPension = 'true'">
                            <xsl:value-of select="'true'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'false'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </isPension>

        <buyAmount>
            <xsl:call-template name="moneyField">
                <xsl:with-param name="name">buyAmount</xsl:with-param>
                <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="needInitialFee = 'true'"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value">
                    <xsl:choose>
                        <xsl:when test="needInitialFee = 'true'">
                            <xsl:value-of select="buyAmount"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'0'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </buyAmount>

        <exactAmount>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">exactAmount</xsl:with-param>
                <xsl:with-param name="title">Признак, обозначающий какое из полей суммы заполнил пользователь</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="needInitialFee = 'true'"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value">
                    <xsl:choose>
                        <xsl:when test="needInitialFee = 'true'">
                            <xsl:value-of select="exactAmount"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'destination-field-exact'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </exactAmount>

        <xsl:if test="needInitialFee = 'true'">
            <minDepositBalance>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">minDepositBalance</xsl:with-param>
                    <xsl:with-param name="title">Неснижаемый остаток</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="withMinimumBalance = 'true'"/>
                    <xsl:with-param name="value" select="minDepositBalance"/>
                </xsl:call-template>
            </minDepositBalance>

            <fromResource>
                <xsl:call-template name="resourceField">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="title">Счет списания</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                </xsl:call-template>
            </fromResource>

            <course>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">course</xsl:with-param>
                    <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="course"/>
                </xsl:call-template>
            </course>
            <sellAmount>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">sellAmount</xsl:with-param>
                    <xsl:with-param name="title">Сумма списания</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="sellAmount"/>
                </xsl:call-template>
            </sellAmount>
            <period>
                <termType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">termType</xsl:with-param>
                        <xsl:with-param name="title">Тип срока</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="'period'"/>
                    </xsl:call-template>
                </termType>
                <periodDays>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">periodDays</xsl:with-param>
                        <xsl:with-param name="title">Количество дней</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="periodDays"/>
                    </xsl:call-template>
                </periodDays>
                <periodMonths>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">periodMonths</xsl:with-param>
                        <xsl:with-param name="title">Количество месяцев</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="periodMonths"/>
                    </xsl:call-template>
                </periodMonths>
                <periodYears>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">periodYears</xsl:with-param>
                        <xsl:with-param name="title">Количество лет</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="periodYears"/>
                    </xsl:call-template>
                </periodYears>
            </period>
            <closingDate>
                <xsl:call-template name="dateField">
                    <xsl:with-param name="name">closingDate</xsl:with-param>
                    <xsl:with-param name="title">Дата закрытия</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="false()"/>
                    <xsl:with-param name="value" select="closingDate"/>
                </xsl:call-template>
            </closingDate>
            <interestRate>
                <xsl:call-template name="numberField">
                    <xsl:with-param name="name">interestRate</xsl:with-param>
                    <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="interestRate"/>
                </xsl:call-template>
            </interestRate>

        </xsl:if>

        <depositId>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">depositId</xsl:with-param>
                <xsl:with-param name="title">Идентификатор депозитного продукта</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="depositId"/>
            </xsl:call-template>
        </depositId>

        <depositType>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">depositType</xsl:with-param>
                <xsl:with-param name="title">Вид вклада</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="depositType"/>
            </xsl:call-template>
        </depositType>

        <depositSubType>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">depositSubType</xsl:with-param>
                <xsl:with-param name="title">Подвид вклада</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="depositSubType"/>
            </xsl:call-template>
        </depositSubType>

        <minAdditionalFee>
            <xsl:call-template name="moneyField">
                <xsl:with-param name="name">minAdditionalFee</xsl:with-param>
                <xsl:with-param name="title">Минимальный размер дополнительного взноса</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="minAdditionalFee"/>
            </xsl:call-template>
        </minAdditionalFee>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <form>AccountOpeningClaim</form>

            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <AccountOpeningClaimDocument>

                <depositSubType>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">depositSubType</xsl:with-param>
                        <xsl:with-param name="title">Подвид вклада</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="depositSubType"/>
                    </xsl:call-template>
                </depositSubType>

                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>

                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>

                <depositName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">depositName</xsl:with-param>
                        <xsl:with-param name="title">Открыть вклад</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="depositName"/>
                    </xsl:call-template>
                </depositName>

                <openDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">openDate</xsl:with-param>
                        <xsl:with-param name="title">Дата открытия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="openDate"/>
                    </xsl:call-template>
                </openDate>

                <closingDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">closingDate</xsl:with-param>
                        <xsl:with-param name="title">Дата закрытия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="needInitialFee = 'true'"/>
                        <xsl:with-param name="value" select="closingDate"/>
                    </xsl:call-template>
                </closingDate>

                <toResourceCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">toResourceCurrency</xsl:with-param>
                        <xsl:with-param name="title">Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="toResourceCurrency"/>
                    </xsl:call-template>
                </toResourceCurrency>

                <needInitialFee>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">needInitialFee</xsl:with-param>
                        <xsl:with-param name="title">Требуется начальный взнос</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="needInitialFee"/>
                    </xsl:call-template>
                </needInitialFee>

                <withMinimumBalance>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">withMinimumBalance</xsl:with-param>
                        <xsl:with-param name="title">Есть ли у вклада неснижаемый остаток</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="withMinimumBalance"/>
                    </xsl:call-template>
                </withMinimumBalance>

                <xsl:if test="string-length(minDepositBalance) > 0">
                    <minDepositBalance>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">minDepositBalance</xsl:with-param>
                            <xsl:with-param name="title">Неснижаемый остаток</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="withMinimumBalance = 'true'"/>
                            <xsl:with-param name="value" select="minDepositBalance"/>
                        </xsl:call-template>
                    </minDepositBalance>
                </xsl:if>

                <xsl:if test="string-length(fromResource) > 0">
                    <xsl:variable name="fromDisplayedValue">
                        <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                    </xsl:variable>
                    <fromResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Счет списания</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="selectedResource" select="fromCodeAccountSelect"/>
                            <xsl:with-param name="displayedValue" select="$fromDisplayedValue"/>
                        </xsl:call-template>
                    </fromResource>
                </xsl:if>

                <xsl:if test="string-length(buyAmount) > 0">
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="buyAmount"/>
                        </xsl:call-template>
                    </buyAmount>
                </xsl:if>

                <xsl:if test="string-length(course) > 0">
                    <course>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">course</xsl:with-param>
                            <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="course"/>
                        </xsl:call-template>
                    </course>
                </xsl:if>

                <xsl:if test="string-length(sellAmount) > 0">
                    <sellAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmount"/>
                        </xsl:call-template>
                    </sellAmount>
                </xsl:if>

                <xsl:if test="string-length(exactAmount) > 0">
                    <exactAmount>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">exactAmount</xsl:with-param>
                            <xsl:with-param name="title">Признак, обозначающий какое из полей суммы заполнил пользователь</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="exactAmount"/>
                        </xsl:call-template>
                    </exactAmount>
                </xsl:if>

                <xsl:if test="string-length(interestRate) > 0">
                    <interestRate>
                        <xsl:call-template name="numberField">
                            <xsl:with-param name="name">interestRate</xsl:with-param>
                            <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="interestRate"/>
                        </xsl:call-template>
                    </interestRate>
                </xsl:if>

                <xsl:if test="string-length(minAdditionalFee) > 0">
                    <minAdditionalFee>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">minAdditionalFee</xsl:with-param>
                            <xsl:with-param name="title">Минимальный размер дополнительного взноса</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="minAdditionalFee"/>
                        </xsl:call-template>
                    </minAdditionalFee>
                </xsl:if>
            </AccountOpeningClaimDocument>
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
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>
        <xsl:param name="linkId"/>
        <xsl:param name="selectedResource"/>
        <xsl:param name="displayedValue"/>
        <xsl:variable name="value">
            <resourceType>
                <availableValues>
                    <xsl:if test="$activeAccounts != ''">
                        <xsl:call-template name="availableValue">
                            <xsl:with-param name="resource" select="$activeAccounts"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="type">account</xsl:with-param>
                            <xsl:with-param name="name" select="$name"/>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="$activeCards != ''">
                        <xsl:call-template name="availableValue">
                            <xsl:with-param name="resource" select="$activeCards"/>
                            <xsl:with-param name="linkId" select="$linkId"/>
                            <xsl:with-param name="type">card</xsl:with-param>
                            <xsl:with-param name="name" select="$name"/>
                        </xsl:call-template>
                    </xsl:if>

                    <xsl:if test="string-length($selectedResource)>0">
                        <valueItem>
                            <value><xsl:value-of select="$selectedResource"/></value>
                            <selected>true</selected>
                            <xsl:if test="string-length($displayedValue)>0">
                                <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                            </xsl:if>
                            <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                            </xsl:if>
                            <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                <currency><xsl:value-of select="toResourceCurrency"/></currency>
                            </xsl:if>
                        </valueItem>
                    </xsl:if>
                </availableValues>
            </resourceType>
        </xsl:variable>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag" select="$value"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="availableValue">
        <xsl:param name="resource"/>
        <xsl:param name="linkId"/>
        <xsl:param name="type"/>
        <xsl:param name="name"/>
        <xsl:if test="count($resource)>0">
            <xsl:for-each select="$resource">
                <valueItem>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <value><xsl:value-of select="$id"/></value>
                    <selected><xsl:value-of select="string($linkId=$id)"/></selected>
                    <displayedValue>
                        <xsl:choose>
                            <xsl:when test="$type = 'card'">
                                <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:otherwise>
                        </xsl:choose>
                    </displayedValue>
                    <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                        <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                    </xsl:if>
                    <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                        <currency><xsl:value-of select="toResourceCurrency"/></currency>
                    </xsl:if>
                </valueItem>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>