<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/list-data/filter-data"/>
         <script type="text/javascript">
          function initTemplates() {
           }
          function clearMasks(event){
             clearInputTemplate("fromDate",DATE_TEMPLATE);
             clearInputTemplate("toDate",DATE_TEMPLATE);
           }
           function checkData() {
             if (!checkPeriod ("fromDate", "toDate", "Период с", "Период по")) return false;
             return true;
           }
           initTemplates();
           setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state"/>");
           setSelectBoxValue ("payerAccount","<xsl:value-of select="/list-data/filter-data/payerAccount"/>");
        </script>
    </xsl:template>
	
	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
		<span class="filterLabel">Статус</span>
		<select name="state">
               <option value="">Все</option>
			   <option value="I">Введен</option>
			   <option value="Z,W">На исполнении</option>
               <option value="S">Исполнен</option>
               <option value="E,D">Отказан</option>
			   <option value="V">Отозван</option>
         </select>
         <br/>
         <span class="filterLabel">Период&nbsp;c</span>
			<input name="fromDate" value="{fromDate}" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
				class="filterInput" type="text" size="10"/>
            &nbsp;по&nbsp;
			<input name="toDate" value="{toDate}" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
			       class="filterInput" type="text" size="10"/>
          <br/>
            <span class="filterLabel">Сумма&nbsp;(RUB)&nbsp;c</span>
			  <input name="minAmount" value="{minAmount}" class="filterInput" type="text" size="10"/>
              &nbsp;по&nbsp;
			  <input name="maxAmount" value="{maxAmount}" class="filterInput" type="text" size="10"/>
            <br/>
                 <span class="filterLabel">Счет списания</span>
				 <select id="payerAccount" name="payerAccount">
				 <option value="">Все</option>
				 <xsl:for-each select="document('accounts.xml')/entity-list/*">
					<option value="{./@key}">
						<xsl:value-of select="./@key"/>&nbsp;
						[<xsl:value-of select="./field[@name='type']"/>]
					 </option>
				  </xsl:for-each>
				</select>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="html" userelativepaths="yes" externalpreview="no" url="list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->