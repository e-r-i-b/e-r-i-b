<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/entity-list/*"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:param name="mode" select="'edit'"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

   <xsl:template match="/form-data" mode="printCheck">
        <xsl:call-template name="printTitle">
            <xsl:with-param name="value">ОПЛАТА БРОНИ <xsl:value-of select="receiverName"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">время операции (МСК)</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationTime"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">идентификатор операции</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentNumber"/></xsl:with-param>
        </xsl:call-template>

        <br/>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Номер карты</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="mask:getCutCardNumber(fromAccountSelect)"/></xsl:with-param>
        </xsl:call-template>

        <br/>
       
        <xsl:variable name="currency"><xsl:value-of select="currency"/></xsl:variable>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Сумма операции</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of select="$currency"/></xsl:with-param>
        </xsl:call-template>
        <xsl:if test="authorizeCode!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Код авторизации</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="authorizeCode"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <br/>
       
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Номер заказа</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="RecIdentifier"/></xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="airlineReservations" select="document('airlineReservation.xml')/AirlineReservation"/>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Номер брони</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="$airlineReservations/ReservId/text()"/></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(printDesc) > 0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">информация о броне</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of select="printDesc" disable-output-escaping="yes"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Реквизиты платежа</xsl:with-param>
            <xsl:with-param name="value">
                оплата брони <xsl:value-of select="receiverName"/>
            </xsl:with-param>
        </xsl:call-template>

        <br/>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Получатель платежа</xsl:with-param>
            <xsl:with-param name="value"><br/>
                    <xsl:choose>
                        <xsl:when test="string-length(nameOnBill) > 0">
                            <xsl:value-of  select="nameOnBill"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of  select="receiverName"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
        </xsl:call-template>

        <br/>

       <!-- реквизиты получателя-->
       <xsl:call-template name="printText">
           <xsl:with-param name="value">реквизиты получателя:</xsl:with-param>
       </xsl:call-template>
       <xsl:if test="receiverBankCode !=''">
           <xsl:call-template name="paramsCheck">
               <xsl:with-param name="title">БИК</xsl:with-param>
               <xsl:with-param name="value"><xsl:value-of select="receiverBankCode"/></xsl:with-param>
           </xsl:call-template>
       </xsl:if>
       <xsl:if test="receiverINN !=''">
           <xsl:call-template name="paramsCheck">
               <xsl:with-param name="title">ИНН</xsl:with-param>
               <xsl:with-param name="value"><xsl:value-of select="receiverINN"/></xsl:with-param>
           </xsl:call-template>
       </xsl:if>
       <xsl:if test="receiverAccount !=''">
           <xsl:call-template name="paramsCheck">
               <xsl:with-param name="title">СЧЕТ</xsl:with-param>
               <xsl:with-param name="value"><xsl:value-of select="receiverAccount"/></xsl:with-param>
           </xsl:call-template>
       </xsl:if>
       <xsl:if test="receiverCorAccount !=''">
           <xsl:call-template name="paramsCheck">
               <xsl:with-param name="title">КОРР.СЧЕТ</xsl:with-param>
               <xsl:with-param name="value"><xsl:value-of select="receiverCorAccount"/></xsl:with-param>
           </xsl:call-template>
       </xsl:if>

        <!-- печать -->
        <xsl:call-template name="stamp"/>

        <div  id="additionalInfo" class="checkSize titleAdditional">
            <br/>ПО ВОПРОСУ ПРЕДОСТАВЛЕНИЯ УСЛУГИ ОБРАЩАЙТЕСЬ К ПОЛУЧАТЕЛЮ ПЛАТЕЖА
            <!-- Вывод телефонного номера -->
            <xsl:if test="phoneNumber!=''">
                ПО ТЕЛЕФОНУ: <xsl:value-of select="phoneNumber"/>
            </xsl:if>
        </div>       

    </xsl:template>

    <xsl:template name="printTitle">
          <xsl:param name="value"/>
          <div class="checkSize title"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printTitleAdditional">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printText">
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="printTextForDispatched">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="paramsCheck">
          <xsl:param name="title"/>
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="stamp">
          <br/>
          <div id="stamp" class="stamp">
              <xsl:value-of select="bn:getBankNameForPrintCheck()"/>
             <xsl:variable name="imagesPath" select="concat($resourceRoot, '/images/')"/>
             <xsl:choose>
                 <xsl:when test="state='DISPATCHED' or state='ERROR' or state='DELAYED_DISPATCH'">
                     <img src="{concat($imagesPath, 'stampDispatched_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='EXECUTED'">
                     <img src="{concat($imagesPath, 'stampExecuted_blue.gif')}" alt=""/>
                 </xsl:when>
             </xsl:choose>
         </div>
         <br/>
      </xsl:template>
    
 </xsl:stylesheet>