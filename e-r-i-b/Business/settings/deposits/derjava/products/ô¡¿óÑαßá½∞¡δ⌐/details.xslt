<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:set="http://exslt.org/sets"
  exclude-result-prefixes="set">

<xsl:import href="static://include/functions/set/set.distinct.function.xsl"/>

<xsl:output method="html" encoding="windows-1251" indent="yes"/>

<xsl:template match="/">
  <xsl:variable name="data" select="/product/data/element"/>
  <xsl:variable name="curs" select="/product/dictionaries/entity-list/entity[../@name='currencies']"/>
  <xsl:variable name="periodDict" select="/product/dictionaries/entity-list/entity[../@name='paymentPeriods']"/>
  <xsl:variable name="flagDict" select="/product/dictionaries/entity-list/entity[../@name='flag']"/>
  <xsl:for-each select="$curs">
    <span class="workspaceTitle" height="40px"><xsl:value-of select="text()"/></span>
    <xsl:variable name="cur" select="@key"/>
    <xsl:variable name="table" select="$data[value[@field='currency']/text()=$cur]"/>
    <xsl:variable name="rows" select="$table/value[@field='period']"/>
    <xsl:variable name="cols" select="$table/value[@field='minimumAmount']"/>
    <xsl:variable name="paymentPeriod" select="$table/value[@field='paymentPeriod']"/>

    <table class="userTab" cellspacing="0" cellpadding="2" width="50%">
    <tr class="titleTable">
      <td>Период\Сумма</td>
    <xsl:for-each select="set:distinct($cols)">
	<xsl:sort select="text()" data-type="number"/>
      <td>
         &#160;<xsl:value-of select="text()"/>
      </td>
    </xsl:for-each>
    </tr>
    <xsl:for-each select="set:distinct($rows)">
    <xsl:sort select="text()" data-type="text"/>
    <xsl:variable name="row" select="text()"/>
    <xsl:element name="tr">
        <xsl:attribute name="class">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
      <td class="ListItem">
        &#160;<script type="text/javascript">document.write(formatPeriod('<xsl:value-of select="$row"/>'))</script>
      </td>
      <xsl:for-each select="set:distinct($cols)">
      <xsl:sort select="text()" data-type="number"/>
      <xsl:variable name="col" select="text()"/>
      <td class="ListItem" align="center">
         <xsl:variable name="procent" select="$table/value[@field='interestRate'][../value[@field='period']/text()=$row][../value[@field='minimumAmount']/text()=$col]"/>
         <xsl:choose>
             <xsl:when test="string-length($procent) = 0">
                  -
             </xsl:when>
             <xsl:otherwise>
                  <xsl:value-of select="$procent"/>%
             </xsl:otherwise>
         </xsl:choose>
      </td>
      </xsl:for-each>
    </xsl:element>
    </xsl:for-each>
    </table>
    <table>
    <tr>
        <td><span class="infoTitle">Возможность пополнения вклада:</span></td>
        <td><xsl:value-of select="$flagDict[@key=$table/value[@field='renewal']]/text()"/></td>
    </tr>
    <tr>
        <td><span class="infoTitle">Пролонгация:</span></td>
        <td><xsl:value-of select="$flagDict[@key=$table/value[@field='prolongation']]/text()"/></td>
    </tr>
    <tr>
        <td><span class="infoTitle">Капитализация процентов:</span></td>
        <td><xsl:value-of select="$flagDict[@key=$table/value[@field='capitalization']]/text()"/></td>
    </tr>
    <tr>
        <td> <span class="infoTitle">Способ выплаты процентов:</span></td>
        <td><xsl:value-of select="$periodDict[@key=$paymentPeriod]/text()"/></td>
    </tr>
    <tr><td colspan="2">&#160;</td></tr>
    </table>
  </xsl:for-each>
</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="description" userelativepaths="yes" externalpreview="no" url="description.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->