<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils" 
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'view'"/>

    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
 		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printCheck">
         <xsl:call-template name="printTitle">
             <xsl:with-param name="value">оплата налогов</xsl:with-param>
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


        <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
        <xsl:call-template name="printText">
            <xsl:with-param name="value">Карта: <xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/></xsl:with-param>
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
            <br/>
        </xsl:if>
        <br/>
         <xsl:call-template name="paramsCheck">
             <xsl:with-param name="title">Реквизиты плательщика</xsl:with-param>
             <xsl:with-param name="value">
                 <br/>ИНДЕКС ДОКУМЕНТА:<xsl:value-of  select="RecIdentifier"/>
                 <xsl:if test="payerInn!=''">
                    <br/>ИНН ПЛАТЕЛЬЩИКА:<xsl:value-of  select="payerInn"/>
                 </xsl:if>
             </xsl:with-param>
         </xsl:call-template>
         <br/>
         <xsl:call-template name="paramsCheck">
             <xsl:with-param name="title">получатель платежа</xsl:with-param>
             <xsl:with-param name="value"><xsl:value-of  select="receiverNameOrder"/></xsl:with-param>
         </xsl:call-template>
         <br/>
         <!-- реквизиты получателя-->
         <xsl:call-template name="printText">
             <xsl:with-param name="value">реквизиты получателя:</xsl:with-param>
         </xsl:call-template>
         <xsl:if test="inn!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">ИНН</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="inn"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>
         <xsl:if test="kpp!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">КПП</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="kpp"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>
         <xsl:if test="receiverAccount!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">СЧЕТ</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="receiverAccount"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>
         <xsl:if test="receiverCorAccount!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">КОРР.СЧЕТ</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="receiverCorAccount"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>
         <xsl:if test="receiverBankCode!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">БИК</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="receiverBankCode"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>
         <xsl:if test="receiverBankName!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">Наименование банка получателя</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="receiverBankName"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>
         <xsl:if test="kbk!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">КБК</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="kbk"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>         
         <xsl:if test="okato!=''">
            <xsl:call-template name="paramsCheck">
                 <xsl:with-param name="title">ОКАТО</xsl:with-param>
                 <xsl:with-param name="value"><xsl:value-of select="okato"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>

         <!-- печать -->
         <xsl:call-template name="stampPayment"/>

         <!-- дополнительная информация -->
         <xsl:call-template name="info"/>
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

    <xsl:template name="paramsCheck">
        <xsl:param name="title"/>
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
    </xsl:template>

    <xsl:template name="stampPayment">
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


    <xsl:template name="info">
         <!-- дополнительная информация -->
         <xsl:if test="state='DISPATCHED' or state='ERROR'">
             <div class="checkSize titleAdditional">
                     платеж может быть не исполнен банком в случае указания неверных
                     реквизитов получателя, либо отзыва операции плательщиком.
             </div>
             <br/>
         </xsl:if>
    </xsl:template>

</xsl:stylesheet>