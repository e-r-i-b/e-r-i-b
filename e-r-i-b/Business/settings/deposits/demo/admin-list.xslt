<?xml version="1.0" encoding="windows-1251"?>

<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		        xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">
	<xsl:import href="static://include/functions/set/set.distinct.function.xsl"/>

	<xsl:output method="html" encoding="windows-1251"/>

	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="detailsUrl" select="'details?id='"/>
    <xsl:variable name="products" select="//product"/>
    <xsl:key name="departmentId" match="//product" use="@departmentId"/>

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="count($products)>0">
                <table cellspacing="0" cellpadding="0" width="100%">
                    <xsl:apply-templates select="//product" mode="first"/>
                </table>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template match="product" mode="first">
        <xsl:if test="not(@departmentId = preceding-sibling::product/@departmentId)">
            <xsl:variable name="departmentId" select="@departmentId"/>
            <tr>
                <td>
                    <span class="infoTitle">
                        <b><xsl:value-of select="document('departments.xml')/entity-list/entity[@key = $departmentId]/field[@name = 'name']/text()"/></b>
                    </span>
                    <br/>
                </td>
            </tr>

            <tr>
                <td>
                    <table class="userTab" cellspacing="0" cellpadding="2" width="100%">
                        <tr class="titleTable">
                            <td>C���� ������</td>
                            <td>������ ������</td>
                            <td>���� ������</td>
                            <td>���������� ������ (% �������)</td>
                        </tr>
                        <xsl:apply-templates mode="second" select="key('departmentId',@departmentId)"/>
                  </table>
                </td>
            </tr>
            <tr>
                <td>
                    <hr style="margin-top:5px"/>
                    <br/>
                </td>
            </tr>
        </xsl:if>
    </xsl:template>

	<xsl:template match="product"  mode="second">
		<xsl:variable name="product" select="."/>
		<xsl:variable name="currencies" select="$product/data/element/value[@field='currency']"/>
		<tr>
			<td class="ListItem" style="background-color:#F8FBFD;" colspan="4">
				<input type="checkbox" name="selectedIds" value="{@id}"/>
				<a href="{concat($detailsUrl,@id)}">
					<xsl:value-of select="@name"/>
				</a>
			</td>
		</tr>
		<tr>
			<td class="ListItem">
				<xsl:for-each select="set:distinct($currencies)">
					<xsl:variable name="curr" select="."/>
					<xsl:variable
							name="minimumAmounts"
							select="$product/data/element[value[@field='currency']/text()=$curr]/value[@field='minimumAmount']"/>
					<xsl:for-each select="$minimumAmounts">
						<xsl:sort data-type="number"/>
						<xsl:if test="position()=1">
							��
							<xsl:value-of select="number(.)"/>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:for-each>
			</td>
			<td class="ListItem">
				<xsl:for-each select="set:distinct($currencies)">
					<xsl:value-of select="."/>
					<br/>
				</xsl:for-each>
			</td>
			<td class="ListItem">
				<xsl:for-each select="set:distinct($currencies)">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="periods" select="$elements_currency/value[@field='period']"/>
					<xsl:for-each select="$periods">
						<xsl:sort data-type="text"/>
						<xsl:if test="position()=1">
							<script type="text/javascript">
								var startElem = formatPeriod('<xsl:value-of select="."/>');
							</script>
						</xsl:if>
						<xsl:if test="position()=count($periods)">
							<xsl:choose>
								<xsl:when test="position()=1">
									 <script type="text/javascript">
										 document.write(startElem);
							         </script>
								</xsl:when>
								<xsl:otherwise>
									<script type="text/javascript">
										var endElem = formatPeriod('<xsl:value-of select="."/>');
										if (startElem == endElem)
										{
											document.write(startElem);
										}
										else
										{
										    if (startElem == termless)
										    {
												document.write(endElem + " - " + startElem);
											}
											else
											{
												document.write(startElem + " - " + endElem);
											}
										}
							         </script>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:for-each>
			</td>
			<td class="ListItem">
				<xsl:for-each select="set:distinct($currencies)">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="interestRates"
					              select="$elements_currency/value[@field='interestRate']"/>
					<xsl:for-each select="$interestRates">
						<xsl:sort data-type="number"/>
						<xsl:if test="position()=1">
							��
							<xsl:value-of select="."/>
							&#160;
						</xsl:if>
						<xsl:if test="not(position()=1) and position()=count($interestRates)">
							��
							<xsl:value-of select="."/>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:for-each>
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="products.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\products\????????\description.xml" srcSchemaRoot="product" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="table/xsl:apply&#x2D;templates" x="156" y="18"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->