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
        <script language="javascript">
            function initTemplates() {
             setInputTemplate("fromDate",DATE_TEMPLATE);
             setInputTemplate("toDate",DATE_TEMPLATE);
           }
          function clearMasks(event){
             clearInputTemplate("fromDate",DATE_TEMPLATE);
             clearInputTemplate("toDate",DATE_TEMPLATE);
           }
           function checkData() {
            if (!checkSumRange ("minAmount","maxAmount","Сумма с","Сумма по")) return false;
             if (!checkPeriod ("fromDate", "toDate", "Период с", "Период по")) return false;
             return true;
           }
           setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");
           setSelectBoxValue ("month","<xsl:value-of select="/list-data/filter-data/month"/>");
           setSelectBoxValue ("year","<xsl:value-of select="/list-data/filter-data/year"/>");
           setSelectBoxValue ("payerAccount","<xsl:value-of select="/list-data/filter-data/payerAccount"/>");
           initTemplates();
        </script>
    </xsl:template>
	
	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
      <span class="filterLabel">Статус</span>
			<select name="state" style="filterSelect">
			<option value="">Все</option>
			<option value="INITIAL,SAVED">Введен</option>
			<option value="DISPATCHED">Обрабатывается</option>
			<option value="DELAYED_DISPATCH">Ожидание обработки</option>
            <option value="EXECUTED">Исполнен</option>
            <option value="REFUSED">Отказан</option>
            <option value="RECALLED">Отозван</option>
   		   </select>
		 <br/>
         <span class="filterLabel">Номер</span>
		 <input name="number" value="{number}" class="filterInput" type="text" size="10"/>
         <br/>
        <span class="filterLabel">ФИО</span>
		<input name="fio" value="{fio}" class="filterInput" type="text" size="27"/>
        <br/>
        <span class="filterLabel">Месяц</span>
         <select name="month">
          <option value=""></option>
          <option value="1">Январь</option>
          <option value="2">Февраль</option>
          <option value="3">Март</option>
          <option value="4">Апрель</option>
          <option value="5">Май</option>
          <option value="6">Июнь</option>
          <option value="7">Июль</option>
          <option value="8">Август</option>
          <option value="9">Сентябрь</option>
          <option value="10">Октябрь</option>
          <option value="11">Ноябрь</option>
          <option value="12">Декабрь</option>
         </select>
	 	 &nbsp;год&nbsp;
		 <select name="year" onkeydown="onTabClick(event,'fromDate')">
           <option value=""></option>
          <option value="2005">2005</option>
          <option value="2006">2006</option>
          <option value="2007">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
         </select>
         <br/>
         <span class="filterLabel">Период&nbsp;c</span>
           <input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
            &nbsp;по&nbsp;
		   <input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		 <br/>
		 <span class="filterLabel">Сумма&nbsp;(RUB)&nbsp;c</span>
		 <input name="minAmount" value="{minAmount}" class="filterInput" type="text" size="10"/>
         &nbsp;по&nbsp;
	     <input name="maxAmount" value="{maxAmount}" class="filterInput" type="text" size="10"/>
        <br/>
		<span class="filterLabel">Расход</span>
			<input name="consumption" value="{consumption}" class="filterInput" type="text" size="10"/>
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
       <br/>
         <span class="filterLabel">Показать счетчик</span>
		<input name="isShowCounter" style="border:none" type="checkbox" value="true">
			<xsl:if test="isShowCounter='true'">
				<xsl:attribute name="checked"/>
			</xsl:if>
		</input>
		&nbsp;Показать тариф&nbsp;
		<input name="isShowTarif" style="border:none" type="checkbox" value="true">
			<xsl:if test="isShowTarif='true'">
				<xsl:attribute name="checked"/>
			</xsl:if>
		</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
    </xsl:template>
</xsl:stylesheet>