<?xml version="1.0" encoding="windows-1251"?>
<!-- шаблоны полей для УС платежей -->
<!-- подключается тэгом <xsl:import href="commonFieldTypes.atm.template.xslt"/> -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
        xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
        xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
        xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:xh="java://com.rssl.phizic.utils.xml.XmlHelper"
        exclude-result-prefixes="mu mask au xalan dh xh">

    <xsl:decimal-format name="sbrf"        decimal-separator="," grouping-separator=" "/>
    <xsl:decimal-format name="sbrf_amount" decimal-separator="." grouping-separator=" "/>

    <xsl:template name="simpleField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="type"/>
        <xsl:param name="maxLength" select="''"/>
        <xsl:param name="minLength" select="''"/>
        <xsl:param name="required" select="false()"/> <!--по умолчанию: необязательно-->
        <xsl:param name="editable" select="false()"/> <!--по умолчанию: нередактируемо-->
        <xsl:param name="visible" select="true()"/> <!--по умолчанию: видимо-->
        <xsl:param name="validators"/>
        <xsl:param name="subType" select="''"/>
        <xsl:param name="isRiskField" select="false()"/> <!--требует ли поле дополнительной проверки. По умолчанию - не требует-->
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="''"/> <!-- если пусто - смотрим в $changedFields, если нет - то, что передали-->
        <xsl:param name="valueTag"/>

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
        <xsl:choose>
            <xsl:when test="string-length($changed)>0">
                <changed><xsl:value-of select="$changed"/></changed>
            </xsl:when>
            <xsl:otherwise>
                <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:if test="string-length($validators)>0">
            <validators>
               <xsl:for-each select="$validators">
                   <validator>
                       <type><xsl:value-of select="./ValidatorType"/></type>
                       <message><xsl:value-of select="./Message"/></message>
                       <parameter><xsl:value-of select="./Parameter"/></parameter>
                   </validator>
               </xsl:for-each>
            </validators>
        </xsl:if>

        <xsl:variable name="haveSubType" select="string-length($subType)>0"/>
        <xsl:if test="$isRiskField or $haveSubType">
            <subTypes>
                <!--Признак поля, требующего дополнительной проверки-->
                <xsl:if test="$isRiskField">
                    <subType><xsl:value-of select="'risk_field'"/></subType>
                </xsl:if>
                <xsl:if test="$subType = 'phone'">
                    <subType>mobile</subType>
                </xsl:if>
                <xsl:if test="$subType = 'wallet'">
                    <subType><xsl:value-of select="$subType"/></subType>
                </xsl:if>
            </subTypes>
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
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="subType" select="''"/>
        <xsl:param name="isRiskField" select="false()"/>
        <xsl:param name="changed" select="''"/>
        <xsl:param name="value" select="''"/>

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
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="subType" select="$subType"/>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <stringType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="xh:escapeXML($value)" disable-output-escaping="yes"/></value>
                    </xsl:if>
                </stringType>
            </xsl:with-param>
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
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="''"/>
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
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="changed" select="$changed"/>
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
        <xsl:param name="maxLength" select="''"/>
        <xsl:param name="minLength" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="subType" select="''"/>
        <xsl:param name="isRiskField" select="false()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="''"/>
        <xsl:param name="value"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="maxLength" select="$maxLength"/>
            <xsl:with-param name="minLength" select="$minLength"/>
            <xsl:with-param name="type" select="'integer'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="subType" select="$subType"/>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="valueTag">
                <integerType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </integerType>
            </xsl:with-param>
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
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'date'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
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

    <xsl:template name="moneyField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'money'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="validators" select="$validators"/>
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

    <xsl:template name="numberField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="subType" select="''"/>
        <xsl:param name="changed" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'number'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="subType" select="$subType"/>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <numberType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </numberType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="linkField">
        <xsl:param name="name"/>
        <xsl:param name="title"       select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint"        select="''"/>
        <xsl:param name="required"    select="false()"/>
        <xsl:param name="editable"    select="false()"/>
        <xsl:param name="visible"     select="true()"/>
        <xsl:param name="validators"  select="''"/>
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
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="valueTag">
                <linkType>
                    <text><xsl:value-of select="$text"/></text>
                    <url><xsl:value-of  select="$url"/></url>
                </linkType>
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
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isView" select="false()"/> <!--false - редактирование, true - просмотр-->


        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
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
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="setField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="availableValues"/>
        <xsl:param name="checkedValues"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="''"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'set'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="validators" select="$validators"/>
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

    <xsl:template name="autoPaymentFloorLimitListField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="listValues"/>
        <xsl:param name="value"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="''"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <xsl:for-each select="$listValues">
                            <xsl:sort select="/text()" data-type="number"/>
                            <valueItem>
                                <value><xsl:value-of select="./text()"/></value>
                                <title><xsl:value-of select="./text()"/></title>
                                <selected><xsl:value-of select="string(./text() = $value)"/></selected>
                            </valueItem>
                        </xsl:for-each>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="dictionaryField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="dictionary"/>
        <xsl:param name="values" select="''"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isView" select="false()"/>
        <xsl:param name="changed" select="''"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <xsl:choose>
                            <xsl:when test="not($isView)">
                                <xsl:for-each select="xalan:tokenize($values, '|')">
                                    <valueItem>
                                        <value><xsl:value-of select="current()"/></value>
                                        <title><xsl:value-of select="$dictionary[@key=current()]/text()"/></title>
                                        <selected><xsl:value-of select="string(current()=$value)"/></selected>
                                    </valueItem>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <valueItem>
                                    <value><xsl:value-of select="$value"/></value>
                                    <title><xsl:value-of select="$dictionary[@key=$value]/text()"/></title>
                                    <selected>true</selected>
                                </valueItem>
                            </xsl:otherwise>
                        </xsl:choose>
                    </availableValues>
                </listType>
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
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="changed" select="$changed"/>
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

    <!-- Не Field'ы-->

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

    <xsl:template name="availableValue">
        <xsl:param name="resource"/>
        <xsl:param name="linkId"/>
        <xsl:param name="type"/>

        <xsl:if test="count($resource)>0">
            <xsl:for-each select="$resource">
                <valueItem>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <value><xsl:value-of select="$id"/></value>
                    <productValue>
                        <xsl:choose>
                            <xsl:when test="starts-with($type,'card')">
                                <cards>
                                    <xsl:call-template name="products-type-card">
                                        <xsl:with-param name="activeCard" select="current()"/>
                                    </xsl:call-template>
                                </cards>
                            </xsl:when>
                            <xsl:when test="starts-with($type,'account')">
                                <accounts>
                                    <xsl:call-template name="products-type-account">
                                        <xsl:with-param name="activeAccount" select="$resource"/>
                                    </xsl:call-template>
                                </accounts>
                            </xsl:when>
                            <xsl:when test="starts-with($type,'loan')">
                                <loans>
                                    <xsl:call-template name="products-type-loan">
                                        <xsl:with-param name="activeLoan" select="current()"/>
                                    </xsl:call-template>
                                </loans>
                            </xsl:when>
                            <xsl:when test="starts-with($type,'im')">
                                <accounts>
                                    <xsl:call-template name="products-type-im">
                                        <xsl:with-param name="activeIMA" select="$resource"/>
                                    </xsl:call-template>
                                </accounts>
                            </xsl:when>
                        </xsl:choose>
                    </productValue>
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
                </valueItem>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>

    <xsl:template name="periodTotalAmount2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='IN_DAY'">день</xsl:when>
            <xsl:when test="$code='IN_WEEK'">неделю</xsl:when>
            <xsl:when test="$code='IN_TENDAY'">декаду</xsl:when>
            <xsl:when test="$code='IN_MONTH'">месяц</xsl:when>
            <xsl:when test="$code='IN_QUARTER'">квартал</xsl:when>
            <xsl:when test="$code='IN_YEAR'">год</xsl:when>
        </xsl:choose>
    </xsl:template>

    <!--получение списка месяцев в строку-->
    <xsl:template name="monthsToString">
        <xsl:param name="value"/>
        <xsl:param name="source"/>

        <xsl:variable name="delimiter" select="'|'"/>

        <xsl:choose>
            <xsl:when test="contains($value, $delimiter)">
                <xsl:for-each select="xalan:tokenize($value, $delimiter)">
                    <xsl:value-of select="concat($source[@key = current()]/text(), ' ')"/>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat($source[@key = $value]/text(), ' ')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="products-type-card">
        <xsl:param name="activeCard"/>
        <xsl:call-template name="CardProductType">
            <xsl:with-param name="id"  select="$activeCard/field[@name='cardLinkId']"/>
            <xsl:with-param name="code"  select="$activeCard/field[@name='code']/text()"/>
            <xsl:with-param name="name"  select="$activeCard/field[@name='name']"/>
            <xsl:with-param name="description"  select="$activeCard/field[@name='description']"/>
            <xsl:with-param name="number"  select="mask:getCutCardNumber($activeCard/@key)"/>
            <xsl:with-param name="isMain"  select="$activeCard/field[@name='isMain']"/>
            <xsl:with-param name="type"  select="$activeCard/field[@name='cardType']"/>
            <xsl:with-param name="availableLimit">
                <amount>
                    <xsl:choose>
                        <xsl:when test="$activeCard/field[@name='amountDecimal'] != ''">
                            <xsl:value-of select="format-number($activeCard/field[@name='amountDecimal'], '######.00', 'sbrf_amount')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="''"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </amount>
                <currency>
                    <code><xsl:value-of select="$activeCard/field[@name='currencyCode']"/></code>
                    <name><xsl:value-of select="mu:getCurrencySign($activeCard/field[@name='currencyCode'])"/></name>
                </currency>
            </xsl:with-param>
            <xsl:with-param name="state"              select="$activeCard/field[@name='cardState']"/>
            <xsl:with-param name="mainCardNumber"     select="mask:getCutCardNumber($activeCard/field[@name='mainCardNumber'])"/>
            <xsl:with-param name="additionalCardType" select="translate($activeCard/field[@name='additionalCardTypeValue'], 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="CardProductType">
        <xsl:param name="id"/>
        <xsl:param name="code"/>
        <xsl:param name="name"/>
        <xsl:param name="description"/>
        <xsl:param name="number"/>
        <xsl:param name="isMain"/>
        <xsl:param name="type"/>
        <xsl:param name="availableLimit"/>
        <xsl:param name="state"/>
        <xsl:param name="mainCardNumber"/>
        <xsl:param name="additionalCardType"/>
        <card>
            <id><xsl:value-of select="$id"/></id>
            <code><xsl:value-of select="$code"/></code>
            <name><xsl:value-of select="$name"/></name>
            <xsl:if test="string-length($description)>0">
                <description><xsl:value-of select="$description"/></description>
            </xsl:if>
            <xsl:if test="string-length($number)>0">
                <number><xsl:value-of select="$number"/></number>
            </xsl:if>
            <xsl:if test="string-length($isMain)>0">
                <isMain><xsl:value-of select="$isMain"/></isMain>
            </xsl:if>
            <xsl:if test="string-length($type)>0">
                <type><xsl:value-of select="$type"/></type>
            </xsl:if>
            <xsl:if test="string-length($availableLimit)>0">
                <availableLimit><xsl:copy-of select="$availableLimit"/></availableLimit>
            </xsl:if>
            <state><xsl:value-of select="$state"/></state>
            <xsl:if test="string-length($mainCardNumber)>0">
                <mainCardNumber><xsl:value-of select="$mainCardNumber"/></mainCardNumber>
            </xsl:if>
            <xsl:if test="string-length($additionalCardType)>0">
                <additionalCardType><xsl:value-of select="$additionalCardType"/></additionalCardType>
            </xsl:if>
        </card>
    </xsl:template>

    <xsl:template name="products-type-account">
        <xsl:param name="activeAccount"/>
        <xsl:call-template name="AccountType">
            <xsl:with-param name="id" select="$activeAccount/field[@name='linkId']/text()"/>
            <xsl:with-param name="code" select="$activeAccount/field[@name='code']"/>
            <xsl:with-param name="name" select="$activeAccount/field[@name='name']"/>
            <xsl:with-param name="number" select="au:getFormattedAccountNumber($activeAccount/@key)"/>
            <xsl:with-param name="balance">
                <amount>
                    <xsl:choose>
                        <xsl:when test="$activeAccount/field[@name='amountDecimal'] != ''">
                            <xsl:value-of select="format-number($activeAccount/field[@name='amountDecimal'], '######.00', 'sbrf_amount')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="''"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </amount>
                <currency>
                    <code><xsl:value-of select="$activeAccount/field[@name='currencyCode']"/></code>
                    <name><xsl:value-of select="mu:getCurrencySign($activeAccount/field[@name='currencyCode'])"/></name>
                </currency>
            </xsl:with-param>
            <xsl:with-param name="state" select="$activeAccount/field[@name='state']"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="AccountType">
        <xsl:param name="id"/>
        <xsl:param name="code"/>
        <xsl:param name="name"/>
        <xsl:param name="number"/>
        <xsl:param name="balance"/>
        <xsl:param name="state"/>
        <account>
            <id><xsl:value-of select="$id"/></id>
            <code><xsl:value-of select="$code"/></code>
            <name><xsl:value-of select="$name"/></name>
            <xsl:if test="string-length($number)>0">
                <number><xsl:value-of select="$number"/></number>
            </xsl:if>
            <xsl:if test="string-length($balance)>0">
                <balance><xsl:copy-of select="$balance"/></balance>
            </xsl:if>
            <moneyBoxAvailable><xsl:value-of select="dh:isAccountForMoneyBoxMatched($id)"/></moneyBoxAvailable>
            <state><xsl:value-of select="$state"/></state>
        </account>
    </xsl:template>

    <xsl:template name="products-type-loan">
        <xsl:param name="activeLoan"/>
        <xsl:call-template name="LoanType">
            <xsl:with-param name="id"   select="$activeLoan/field[@name='loanLinkId']/text()"/>
            <xsl:with-param name="code" select="$activeLoan/field[@name='code']"/>
            <xsl:with-param name="name" select="$activeLoan/field[@name='name']"/>
            <xsl:with-param name="amount">
                <amount>
                    <xsl:choose>
                        <xsl:when test="$activeLoan/field[@name='amountDecimal'] != ''">
                            <xsl:value-of select="format-number($activeLoan/field[@name='amountDecimal'], '######.00', 'sbrf_amount')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="''"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </amount>
                <currency>
                    <code><xsl:value-of select="$activeLoan/field[@name='currencyCode']"/></code>
                    <name><xsl:value-of select="mu:getCurrencySign($activeLoan/field[@name='currencyCode'])"/></name>
                </currency>
            </xsl:with-param>
            <xsl:with-param name="nextPayAmount">
                <amount>
                    <xsl:choose>
                        <xsl:when test="$activeLoan/field[@name='nextPayAmount'] != ''">
                            <xsl:value-of select="format-number($activeLoan/field[@name='nextPayAmount'], '######.00', 'sbrf_amount')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="''"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </amount>
                <currency>
                    <code><xsl:value-of select="$activeLoan/field[@name='nextPayCurrencyCode']"/></code>
                    <name><xsl:value-of select="mu:getCurrencySign($activeLoan/field[@name='nextPayCurrencyCode'])"/></name>
                </currency>
            </xsl:with-param>
            <xsl:with-param name="nextPayDate" select="$activeLoan/field[@name='nextPayDate']"/>
            <xsl:with-param name="state"       select="$activeLoan/field[@name='state']"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="LoanType">
        <xsl:param name="id"/>
        <xsl:param name="code"/>
        <xsl:param name="name"/>
        <xsl:param name="amount"/>
        <xsl:param name="nextPayAmount"/>
        <xsl:param name="nextPayDate"/>
        <xsl:param name="state"/>
        <loan>
            <id><xsl:value-of select="$id"/></id>
            <code><xsl:value-of select="$code"/></code>
            <name><xsl:value-of select="$name"/></name>
            <xsl:if test="string-length($amount)>0">
                <amount><xsl:copy-of select="$amount"/></amount>
            </xsl:if>
            <xsl:if test="string-length($nextPayAmount)>0">
                <nextPayAmount><xsl:copy-of select="$nextPayAmount"/></nextPayAmount>
            </xsl:if>
            <xsl:if test="string-length($nextPayDate)>0">
                <nextPayDate><xsl:value-of select="$nextPayDate"/></nextPayDate>
            </xsl:if>
            <state><xsl:value-of select="$state"/></state>
        </loan>
    </xsl:template>

    <xsl:template name="products-type-im">
        <xsl:param name="activeIMA"/>
        <xsl:call-template name="IMAccountType">
            <xsl:with-param name="id" select="$activeIMA/field[@name='linkId']/text()"/>
            <xsl:with-param name="code" select="$activeIMA/field[@name='code']"/>
            <xsl:with-param name="name" select="$activeIMA/field[@name='name']"/>
            <xsl:with-param name="number" select="au:getFormattedAccountNumber($activeIMA/@key)"/>
            <xsl:with-param name="openDate" select="$activeIMA/field[@name='openDate']"/>
            <xsl:with-param name="closeDate" select="$activeIMA/field[@name='closeDate']"/>
            <xsl:with-param name="balance">
                <amount>
                    <xsl:choose>
                        <xsl:when test="$activeIMA/field[@name='amountDecimal'] != ''">
                            <xsl:value-of select="format-number($activeIMA/field[@name='amountDecimal'], '######.00', 'sbrf_amount')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="''"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </amount>
                <currency>
                    <code><xsl:value-of select="$activeIMA/field[@name='currencyCode']"/></code>
                    <name><xsl:value-of select="mu:getCurrencySign($activeIMA/field[@name='currencyCode'])"/></name>
                </currency>
            </xsl:with-param>
            <xsl:with-param name="currency" select="$activeIMA/field[@name='state']"/>
            <xsl:with-param name="agreementNumber" select="$activeIMA/field[@name='state']"/>
            <xsl:with-param name="state" select="$activeIMA/field[@name='state']"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="IMAccountType">
        <xsl:param name="id"/>
        <xsl:param name="code"/>
        <xsl:param name="name"/>
        <xsl:param name="number"/>
        <xsl:param name="openDate"/>
        <xsl:param name="closeDate"/>
        <xsl:param name="balance"/>
        <xsl:param name="currency"/>
        <xsl:param name="agreementNumber"/>
        <xsl:param name="state"/>
        <imaccount>
            <id><xsl:value-of select="$id"/></id>
            <code><xsl:value-of select="$code"/></code>
            <name><xsl:value-of select="$name"/></name>
            <xsl:if test="string-length($number)>0">
                <number><xsl:value-of select="$number"/></number>
            </xsl:if>
            <xsl:if test="string-length($openDate)>0">
                <openDate><xsl:value-of select="$openDate"/></openDate>
            </xsl:if>
            <xsl:if test="string-length($closeDate)>0">
                <closeDate><xsl:value-of select="$closeDate"/></closeDate>
            </xsl:if>
            <xsl:if test="string-length($balance)>0">
                <balance><xsl:value-of select="$balance"/></balance>
            </xsl:if>
            <xsl:if test="string-length($currency)>0">
                <currency><xsl:value-of select="$currency"/></currency>
            </xsl:if>
            <xsl:if test="string-length($agreementNumber)>0">
                <agreementNumber><xsl:value-of select="$agreementNumber"/></agreementNumber>
            </xsl:if>
           <state><xsl:value-of select="$state"/></state>
        </imaccount>
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
