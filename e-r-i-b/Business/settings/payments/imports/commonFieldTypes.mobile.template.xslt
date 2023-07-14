<?xml version="1.0" encoding="windows-1251"?>
<!-- шаблоны полей для мобильных платежей -->
<!-- подключается тэгом <xsl:import href="commonFieldTypes.mobile.template.xslt"/> -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
        xmlns:xh="java://com.rssl.phizic.utils.xml.XmlHelper"
        exclude-result-prefixes="mu xh">

    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

    <xsl:template name="simpleField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="type"/>
        <xsl:param name="isRiskField" select="false()"/> <!--требует ли поле дополнительной проверки. По умолчанию - не требует-->
        <xsl:param name="maxLength" select="''"/>
        <xsl:param name="minLength" select="''"/>
        <xsl:param name="required" select="false()"/> <!--по умолчанию: необязательно-->
        <xsl:param name="editable" select="false()"/> <!--по умолчанию: нередактируемо-->
        <xsl:param name="visible" select="true()"/> <!--по умолчанию: видимо-->
        <xsl:param name="valueTag"/>
        <xsl:param name="fieldDictType" select="''"/>
        <xsl:param name="fieldInfoType" select="''"/>
        <xsl:param name="dictTypeFieldName" select="''"/>

        <name><xsl:value-of select="$name"/></name>
        <xsl:if test="string-length($title)>0">
            <title><xsl:value-of select="$title"/></title>
        </xsl:if>
        <xsl:if test="string-length($description)>0">
            <description><xsl:value-of select="$description"/></description>
        </xsl:if>
        <xsl:if test="string-length($hint)>0">
            <hint><xsl:value-of select="$hint"/></hint>
        </xsl:if>
        <type><xsl:value-of select="$type"/></type>
        <xsl:if test="$isRiskField">
            <subType><xsl:value-of select="'risk_field'"/></subType>
        </xsl:if>
        <xsl:if test="string-length($maxLength)>0">
            <maxLength><xsl:value-of select="$maxLength"/></maxLength>
        </xsl:if>
        <xsl:if test="string-length($minLength)>0">
            <minLength><xsl:value-of select="$minLength"/></minLength>
        </xsl:if>
        <required><xsl:value-of select="string($required)"/></required>
        <editable><xsl:value-of select="string($editable)"/></editable>
        <visible><xsl:value-of select="string($visible)"/></visible>
        <xsl:copy-of select="$valueTag"/>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
        <xsl:if test="string-length($fieldDictType)>0">
            <dictFields>
                <fieldDictType><xsl:value-of select="$fieldDictType"/></fieldDictType>
                <fieldInfoType><xsl:value-of select="$fieldInfoType"/></fieldInfoType>
                <xsl:if test="string-length($dictTypeFieldName)>0">
                    <dictTypeFieldName><xsl:value-of select="$dictTypeFieldName"/></dictTypeFieldName>
                </xsl:if>
            </dictFields>
        </xsl:if>
    </xsl:template>

    <xsl:template name="stringField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="maxLength" select="''"/>
        <xsl:param name="minLength" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="value"/>
        <xsl:param name="fieldDictType" select="''"/>
        <xsl:param name="fieldInfoType" select="''"/>
        <xsl:param name="dictTypeFieldName" select="''"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'string'"/>
            <xsl:with-param name="maxLength" select="$maxLength"/>
            <xsl:with-param name="minLength" select="$minLength"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <stringType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="xh:escapeXML($value)"  disable-output-escaping="yes"/></value>
                    </xsl:if>
                </stringType>
            </xsl:with-param>
            <xsl:with-param name="fieldDictType" select="$fieldDictType"/>
            <xsl:with-param name="fieldInfoType" select="$fieldInfoType"/>
            <xsl:with-param name="dictTypeFieldName" select="$dictTypeFieldName"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="booleanField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="value"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'boolean'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <booleanType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </booleanType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="integerField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="value"/>
        <xsl:param name="fieldDictType" select="''"/>
        <xsl:param name="fieldInfoType" select="''"/>
        <xsl:param name="dictTypeFieldName" select="''"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'integer'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <integerType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </integerType>
            </xsl:with-param>
            <xsl:with-param name="fieldDictType" select="$fieldDictType"/>
            <xsl:with-param name="fieldInfoType" select="$fieldInfoType"/>
            <xsl:with-param name="dictTypeFieldName" select="$dictTypeFieldName"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="dateField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="value"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'date'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <dateType>
                    <xsl:if test="string-length($value)>0">
                        <xsl:choose>
                            <xsl:when test="contains($value, '-')">
                                <value><xsl:value-of select="concat(substring($value, 9, 2), '.', substring($value, 6, 2), '.', substring($value, 1, 4))"/></value>
                            </xsl:when>
                            <xsl:otherwise>
                                <value><xsl:value-of select="$value"/></value>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:if>
                </dateType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="moneyField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="value"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'money'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <moneyType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </moneyType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="numberField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="value"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'number'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <numberType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </numberType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="listField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="listValues"/>
        <xsl:param name="value"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <xsl:if test="count($listValues)>0">
                        <availableValues>
                            <xsl:for-each select="$listValues">
                                <valueItem>
                                    <value><xsl:value-of select="./@key"/></value>
                                    <title><xsl:value-of select="./text()"/></title>
                                    <selected><xsl:value-of select="string(./@key = $value)"/></selected>
                                </valueItem>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="linkField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="text"/>
        <xsl:param name="url"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'link'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <linkType>
                    <text><xsl:value-of select="$text"/></text>
                    <url><xsl:value-of select="$url"/></url>
                </linkType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

     <xsl:template name="resourceCheckField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="displayedValue"/>

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
                    <xsl:if test="string-length($value)>0">
                        <availableValues>
                            <valueItem>
                                <value><xsl:value-of select="$value"/></value>
                                <selected>true</selected>
                                <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                            </valueItem>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="commission">
        <xsl:param name="commissionAmount"/>
        <xsl:param name="commissionCurrency"/>

        <xsl:if test="string-length($commissionAmount)>0">
            <commission>
                <xsl:call-template name="simpleMoneyType">
                    <xsl:with-param name="amount" select="$commissionAmount"/>
                    <xsl:with-param name="currency" select="$commissionCurrency"/>
                </xsl:call-template>
            </commission>
        </xsl:if>
    </xsl:template>

    <xsl:template name="writeDownOperations">
        <xsl:param name="operations"/>
        <xsl:if test="count($operations) >0">
            <writeDownOperations>
                <xsl:for-each select="$operations">
                    <xsl:variable name="name" select="./Name"/>
                    <xsl:variable name="curAmnt" select="./CurAmount"/>
                    <xsl:variable name="turnOver" select="./TurnOver"/>
                    <writeDownOperation>
                        <operationName>
                            <xsl:value-of select="$name"/>
                        </operationName>
                        <curAmnt><xsl:value-of select="translate($curAmnt,',','.')"/></curAmnt>
                        <turnOver><xsl:value-of select="$turnOver"/></turnOver>
                    </writeDownOperation>
                </xsl:for-each>
            </writeDownOperations>
        </xsl:if>
    </xsl:template>

    <!--Простой тип Money (не Field)-->
    <xsl:template name="simpleMoneyType">
        <xsl:param name="amount"/> <!--сумма-->
        <xsl:param name="currency"/> <!--ISO-код валюты-->

        <amount><xsl:value-of select="$amount"/></amount>
        <currency>
            <code><xsl:value-of select="$currency"/></code>
            <name><xsl:value-of select="mu:getCurrencySign($currency)"/></name>
        </currency>
    </xsl:template>

    <xsl:template name="documentState">
        <xsl:param name="state"/>
        <xsl:choose>
            <xsl:when test="$state = 'SENT'">UNKNOW</xsl:when>
            <xsl:otherwise><xsl:value-of select="$state"/></xsl:otherwise>
        </xsl:choose>
    </xsl:template>

     <xsl:template name="abs">
        <xsl:param name="input"/>

          <xsl:variable name="num" select="(1-2*(number($input) &lt; 0))*number($input)" />
          <xsl:value-of select="format-number($num, '### ##0,00', 'sbrf')"/>
    </xsl:template>
</xsl:stylesheet>
