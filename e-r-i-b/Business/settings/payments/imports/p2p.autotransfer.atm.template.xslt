<?xml version="1.0" encoding="windows-1251"?>
<!-- шаблоны билинговых полей для мобильных платежей -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                exclude-result-prefixes="xalan">

    <xsl:import href="commonFieldTypes.atm.template.xslt"/>

    <xsl:template name="receiver-type-list">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description"   select="''"/>
        <xsl:param name="hint"          select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="validators"    select="''"/>
        <xsl:param name="value"         select="''"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name"         select="$name"/>
            <xsl:with-param name="title"        select="$title"/>
            <xsl:with-param name="description"  select="$description"/>
            <xsl:with-param name="hint"         select="$hint"/>
            <xsl:with-param name="type"         select="'list'"/>
            <xsl:with-param name="required"     select="$required"/>
            <xsl:with-param name="editable"     select="$editable"/>
            <xsl:with-param name="visible"      select="$visible"/>
            <xsl:with-param name="validators"   select="$validators"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <xsl:if test="$value = '' or $value = 'ph' or $value = 'several'">
                            <xsl:call-template name="list-print-value-title">
                                <xsl:with-param name="key"      select="'ph'"/>
                                <xsl:with-param name="title"    select="'Частному лицу'"/>
                                <xsl:with-param name="currentValue"><xsl:value-of select="receiverType"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:if test="$value = '' or $value = 'ph' or $value = 'several'">
                            <xsl:call-template name="list-print-value-title">
                                <xsl:with-param name="key"      select="'several'"/>
                                <xsl:with-param name="title"    select="'Между моими картами'"/>
                                <xsl:with-param name="currentValue"><xsl:value-of select="receiverType"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="receiver-sub-type-list">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description"   select="''"/>
        <xsl:param name="hint"          select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="validators"    select="''"/>
        <xsl:param name="value"         select="''"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name"         select="$name"/>
            <xsl:with-param name="title"        select="$title"/>
            <xsl:with-param name="description"  select="$description"/>
            <xsl:with-param name="hint"         select="$hint"/>
            <xsl:with-param name="type"         select="'list'"/>
            <xsl:with-param name="required"     select="$required"/>
            <xsl:with-param name="editable"     select="$editable"/>
            <xsl:with-param name="visible"      select="$visible"/>
            <xsl:with-param name="validators"   select="$validators"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <xsl:if test="$value = '' or $value = 'severalCard' or $value = 'ourCard' or $value = 'ourPhone'">
                            <xsl:call-template name="list-print-value-title">
                                <xsl:with-param name="key"      select="'severalCard'"/>
                                <xsl:with-param name="title"    select="'Между моими картами'"/>
                                <xsl:with-param name="currentValue"><xsl:value-of select="receiverSubType"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:if test="$value = '' or $value = 'severalCard' or $value = 'ourCard' or $value = 'ourPhone'">
                            <xsl:call-template name="list-print-value-title">
                                <xsl:with-param name="key"      select="'ourCard'"/>
                                <xsl:with-param name="title"    select="'На карту Сбербанка'"/>
                                <xsl:with-param name="currentValue"><xsl:value-of select="receiverSubType"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>

                        <xsl:if test="$value = '' or $value = 'severalCard' or $value = 'ourCard' or $value = 'ourPhone'">
                            <xsl:call-template name="list-print-value-title">
                                <xsl:with-param name="key"      select="'ourPhone'"/>
                                <xsl:with-param name="title"    select="'На карту Сбербанка по номеру телефона'"/>
                                <xsl:with-param name="currentValue"><xsl:value-of select="receiverSubType"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="auto-sub-event-type-list">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>

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
            <availableValues>
                <xsl:call-template name="list-print-value-title">
                    <xsl:with-param name="key">ONCE_IN_MONTH</xsl:with-param>
                    <xsl:with-param name="title">Раз в месяц</xsl:with-param>
                    <xsl:with-param name="currentValue"><xsl:value-of select="longOfferEventType"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="list-print-value-title">
                    <xsl:with-param name="key">ONCE_IN_WEEK</xsl:with-param>
                    <xsl:with-param name="title">Раз в неделю</xsl:with-param>
                    <xsl:with-param name="currentValue"><xsl:value-of select="longOfferEventType"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="list-print-value-title">
                    <xsl:with-param name="key">ONCE_IN_QUARTER</xsl:with-param>
                    <xsl:with-param name="title">Раз в квартал</xsl:with-param>
                    <xsl:with-param name="currentValue"><xsl:value-of select="longOfferEventType"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="list-print-value-title">
                    <xsl:with-param name="key">ONCE_IN_YEAR</xsl:with-param>
                    <xsl:with-param name="title">Раз в год</xsl:with-param>
                    <xsl:with-param name="currentValue"><xsl:value-of select="longOfferEventType"/></xsl:with-param>
                </xsl:call-template>
            </availableValues>
        </listType>
    </xsl:template>

    <xsl:template name="list-print-value-title">
        <xsl:param name="key"/>
        <xsl:param name="title"/>
        <xsl:param name="currentValue"/>

        <valueItem>
            <value><xsl:value-of select="$key"/></value>
            <title><xsl:value-of select="$title"/></title>
            <selected>
                <xsl:value-of select="string($currentValue != '' and contains($key, $currentValue))"/>
            </selected>
        </valueItem>
    </xsl:template>
</xsl:stylesheet>