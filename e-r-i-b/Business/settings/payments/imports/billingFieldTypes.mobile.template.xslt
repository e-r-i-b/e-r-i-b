<?xml version="1.0" encoding="windows-1251"?>
<!-- шаблоны билинговых полей для мобильных платежей -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:xh="java://com.rssl.phizic.utils.xml.XmlHelper"
        exclude-result-prefixes="xalan xh">

    <xsl:template name="simpleBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="type"/>
        <xsl:param name="isRiskField" select="false()"/> <!--требует ли поле дополнительной проверки. По умолчанию - не требует-->
        <xsl:param name="maxLength" select="''"/>
        <xsl:param name="minLength" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="valueTag"/>
        <xsl:param name="fieldDictType" select="''"/>
        <xsl:param name="fieldInfoType" select="''"/>
        <xsl:param name="dictTypeFieldName" select="''"/>

        <name><xsl:value-of select="$name"/></name>
        <xsl:if test="string-length($title) > 0">
            <title>
                <xsl:call-template name="escapeString">
                    <xsl:with-param name="string" select="$title"/>
                </xsl:call-template>
            </title>
        </xsl:if>
        <xsl:if test="string-length($description) > 0">
            <description>
                <xsl:call-template name="escapeString">
                    <xsl:with-param name="string" select="$description"/>
                </xsl:call-template>
            </description>
        </xsl:if>
        <xsl:if test="string-length($hint) > 0">
            <hint>
                <xsl:call-template name="escapeString">
                    <xsl:with-param name="string" select="$hint"/>
                </xsl:call-template>
            </hint>
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
        <isSum><xsl:value-of select="string($isSum)"/></isSum>
        <xsl:copy-of select="$valueTag"/>
        <changed><xsl:value-of select="string($changed)"/></changed>
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

    <xsl:template name="stringBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="maxLength" select="''"/>
        <xsl:param name="minLength" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="fieldDictType" select="''"/>
        <xsl:param name="fieldInfoType" select="''"/>
        <xsl:param name="dictTypeFieldName" select="''"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
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
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <stringType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="xh:escapeXML($value)" disable-output-escaping="yes"/></value>
                    </xsl:if>
                </stringType>
            </xsl:with-param>
            <xsl:with-param name="fieldDictType" select="$fieldDictType"/>
            <xsl:with-param name="fieldInfoType" select="$fieldInfoType"/>
            <xsl:with-param name="dictTypeFieldName" select="$dictTypeFieldName"/>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="integerBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="fieldDictType" select="''"/>
        <xsl:param name="fieldInfoType" select="''"/>
        <xsl:param name="dictTypeFieldName" select="''"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'integer'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
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
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="dateBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'date'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="changed" select="$changed"/>
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
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="calendarBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'calendar'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <calendarType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </calendarType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="moneyBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'money'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <moneyType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </moneyType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="numberBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="fieldDictType" select="''"/>
        <xsl:param name="fieldInfoType" select="''"/>
        <xsl:param name="dictTypeFieldName" select="''"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'number'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <numberType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </numberType>
            </xsl:with-param>
            <xsl:with-param name="fieldDictType" select="$fieldDictType"/>
            <xsl:with-param name="fieldInfoType" select="$fieldInfoType"/>
            <xsl:with-param name="dictTypeFieldName" select="$dictTypeFieldName"/>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="linkBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'link'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <linkType>
                    <text><xsl:value-of select="substring-before($value,'|')"/></text>
                    <url><xsl:value-of select="substring-after($value,'|')"/></url>
                </linkType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="setBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="availableValues"/>
        <xsl:param name="checkedValues"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'set'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <setType>
                    <xsl:if test="count($availableValues)>0">
                        <availableValues>
                            <xsl:for-each select="$availableValues">
                                <xsl:variable name="checkBoxValue" select="./Value"/>
                                <xsl:variable name="code">
                                    <xsl:choose>
                                        <xsl:when test="./Id != ''">
                                            <xsl:value-of select="./Id"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="./Value"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>
                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <title><xsl:value-of select="./Value"/></title>
                                    <selected>
                                        <xsl:choose>
                                            <xsl:when test="contains($checkedValues, '@')">
                                                <xsl:variable name="checked">
                                                    <xsl:for-each select="xalan:tokenize($checkedValues, '@')">
                                                        <xsl:if test="current() = $checkBoxValue">
                                                            1
                                                        </xsl:if>
                                                    </xsl:for-each>
                                                </xsl:variable>
                                                <xsl:value-of select="string(string-length($checked)>0)"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="string($checkedValues = $checkBoxValue)"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </selected>
                                </valueItem>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </setType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="listBillingField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="listValues"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleBillingField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <xsl:if test="count($listValues)>0">
                        <availableValues>
                            <xsl:for-each select="$listValues">
                                <xsl:variable name="code">
                                    <xsl:choose>
                                        <xsl:when test="./Id != ''">
                                            <xsl:value-of select="./Id"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="./Value"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>
                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <title><xsl:value-of select="./Value"/></title>
                                    <selected><xsl:value-of select="string($code = $value)"/></selected>
                                </valueItem>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </listType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="escapeString">
            <xsl:param name="string" select="''" />
            <xsl:choose>
                <!--Маскирование кавычки """-->
                <xsl:when test="contains($string, '&quot;')">
                    <xsl:call-template name="escapeString">
                        <xsl:with-param name="string" select="substring-before($string, '&quot;')" />
                    </xsl:call-template>
                    <xsl:value-of select="'&amp;'" disable-output-escaping="yes"/>
                    <xsl:text>quot;</xsl:text>
                    <xsl:call-template name="escapeString">
                        <xsl:with-param name="string" select="substring-after($string, '&quot;')" />
                    </xsl:call-template>
                </xsl:when>
                <!--Маскирование апострофа "'"-->
                <xsl:when test="contains($string, &quot;'&quot;)">
                    <xsl:call-template name="escapeString">
                        <xsl:with-param name="string" select="substring-before($string, &quot;'&quot;)" />
                    </xsl:call-template>
                    <xsl:value-of select="'&amp;'" disable-output-escaping="yes"/>
                    <xsl:text>apos;</xsl:text>
                    <xsl:call-template name="escapeString">
                        <xsl:with-param name="string" select="substring-after($string, &quot;'&quot;)" />
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$string" />
                </xsl:otherwise>
            </xsl:choose>
        </xsl:template>
</xsl:stylesheet>
