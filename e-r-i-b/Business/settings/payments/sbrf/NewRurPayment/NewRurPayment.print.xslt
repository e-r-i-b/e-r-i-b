<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                extension-element-prefixes="phizic">
    <xsl:import href="commonFieldTypes.template.xslt"/>
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/entity-list/*"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printCheck">
         <xsl:call-template name="checkForPhiz"/>
    </xsl:template>

    <xsl:template name="checkForPhiz">
         <xsl:variable name="receiverSubType" select="receiverSubType"/>
         <xsl:variable name="fromResourceType" select="fromResourceType"/>
         <xsl:variable name="isCardsTrasfer" select="$receiverSubType = 'ourCard' or $receiverSubType = 'ourPhone' or $receiverSubType = 'visaExternalCard' or $receiverSubType = 'masterCardExternalCard'"/>

         <xsl:choose>
             <xsl:when test="$isCardsTrasfer">
                 <xsl:choose>
                     <xsl:when test="contains($fromResourceType, 'Card')">
                         <xsl:call-template name="printTitle">
                             <xsl:with-param name="value">перевод с карты на карту</xsl:with-param>
                         </xsl:call-template>
                     </xsl:when>
                     <xsl:otherwise>
                         <xsl:call-template name="printTitle">
                             <xsl:with-param name="value">Перевод со счета вклада на счет карты</xsl:with-param>
                         </xsl:call-template>
                     </xsl:otherwise>
                 </xsl:choose>
             </xsl:when>
             <xsl:when test="$receiverSubType = 'externalAccount'">
                <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">БЕЗНАЛИЧНЫЙ ПЕРЕВОД СРЕДСТВ</xsl:with-param>
                </xsl:call-template>
             </xsl:when>
             <xsl:otherwise>
                 <xsl:choose>
                     <xsl:when test="contains($fromResourceType, 'Card')">
                         <xsl:call-template name="printTitle">
                             <xsl:with-param name="value">Перевод со счета карты на счет вклада</xsl:with-param>
                         </xsl:call-template>
                     </xsl:when>
                     <xsl:otherwise>
                         <xsl:call-template name="printTitle">
                             <xsl:with-param name="value">Перевод со счета вклада на счет вклада</xsl:with-param>
                         </xsl:call-template>
                     </xsl:otherwise>
                 </xsl:choose>
             </xsl:otherwise>
         </xsl:choose>

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

         <xsl:choose>
             <xsl:when test="$isCardsTrasfer">
                <xsl:call-template name="printText">
                    <xsl:with-param name="value">отправитель:</xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>

                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">№ карты</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/></xsl:with-param>
                </xsl:call-template>
                <br/>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value">получатель:</xsl:with-param>
                </xsl:call-template>
                <xsl:variable name="receiverAccountSelect"><xsl:value-of  select="receiverAccount"/></xsl:variable>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">№ карты</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($receiverAccountSelect)"/></xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value">
                        <!-- BUG039967 -->
                        <!-- <xsl:value-of select="ph:getExtraFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/> -->
                        <xsl:value-of select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/>
                        <!-- ...... -->
                    </xsl:with-param>
                </xsl:call-template>
             </xsl:when>
             <xsl:otherwise>
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value"><xsl:value-of  select="fromAccountType"/>,</xsl:with-param>
                    </xsl:call-template>
                    <xsl:choose>
                        <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                           <xsl:call-template name="printText">
                               <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint(fromAccountSelect)"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="printText">
                                <xsl:with-param name="value"><xsl:value-of  select="fromAccountSelect"/></xsl:with-param>
                             </xsl:call-template>
                        </xsl:otherwise>
                     </xsl:choose>
             </xsl:otherwise>
         </xsl:choose>
         <br/>
        <xsl:choose>
            <xsl:when test="exactAmount = 'destination-field-exact'">
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Сумма операции</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of  select="buyAmount"/>&nbsp;<xsl:value-of select="buyAmountCurrency"/></xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Сумма операции</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of  select="sellAmount"/>&nbsp;<xsl:value-of select="sellAmountCurrency"/></xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:choose>
            <xsl:when test="$postConfirmCommission">
                <xsl:call-template name="writeDownOperationsCheck">
                    <xsl:with-param name="fromResourceCurrency" select="sellAmountCurrency"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="commission!=''">
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">комиссия</xsl:with-param>
                        <xsl:with-param name="value"><xsl:value-of  select="commission"/>&nbsp;<xsl:value-of select="commissionCurrency"/></xsl:with-param>
                    </xsl:call-template>
                 </xsl:if>
            </xsl:otherwise>
        </xsl:choose>

         <br/>

         <xsl:if test="authorizeCode!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">код авторизации</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="authorizeCode"/><br/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>

         <xsl:if test="receiverAccount != '' and not($isCardsTrasfer)">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">НОМЕР СЧЕТА ПОЛУЧАТЕЛЯ</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of  select="receiverAccount"/><br/></xsl:with-param>
            </xsl:call-template>

            <xsl:if test="ground!=''">
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Назначение</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of  select="ground"/></xsl:with-param>
                </xsl:call-template>
            </xsl:if>
            <br/>
         </xsl:if>
         
         <xsl:if test="not($isCardsTrasfer)">
            <!-- реквизиты получателя-->
            <xsl:call-template name="printText">
                <xsl:with-param name="value">реквизиты получателя:</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$receiverSubType = 'externalAccount'">
            <xsl:if test="receiverBIC != ''">
                <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">БИК</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverBIC"/></xsl:with-param>
                </xsl:call-template>
            </xsl:if>
            <xsl:if test="receiverINN != ''">
               <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">ИНН</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverINN"/></xsl:with-param>
               </xsl:call-template>
            </xsl:if>
            <xsl:if test="receiverAccount != ''">
               <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">СЧЕТ</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverAccount"/></xsl:with-param>
               </xsl:call-template>
            </xsl:if>
            <xsl:if test="receiverCorAccount != ''">
               <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">КОРР.СЧЕТ</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverCorAccount"/></xsl:with-param>
               </xsl:call-template>
            </xsl:if>
         </xsl:if>
         
         <xsl:if test="not($isCardsTrasfer)">
         
            <xsl:if test="receiverName != ''">
               <xsl:call-template name="paramsCheck">
                   <xsl:with-param name="title">ФИО</xsl:with-param>
                   <xsl:with-param name="value"><xsl:value-of  select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/></xsl:with-param>
               </xsl:call-template>
            </xsl:if>
         </xsl:if>
         <br/>
         <!-- печать -->
         <xsl:call-template name="stamp"/>

         <xsl:if test="$receiverSubType = 'externalAccount'">
              <xsl:call-template name="info"/>
         </xsl:if>
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

    <xsl:template name="stamp">
        <br/>
        <div id="stamp" class="stamp">
           <xsl:value-of select="bn:getBankNameForPrintCheck()"/>
           <xsl:variable name="imagesPath" select="concat($resourceRoot, '/images/')"/>
           <xsl:choose>
                 <xsl:when test="state='DELAYED_DISPATCH'">
                    <img src="{concat($imagesPath, 'stampDelayed_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='DISPATCHED' or state='ERROR' or state ='UNKNOW'">
                     <img src="{concat($imagesPath, 'stampDispatched_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='EXECUTED'">
                     <img src="{concat($imagesPath, 'stampExecuted_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='REFUSED'">
                     <img src="{concat($imagesPath, 'stampRefused_blue.gif')}" alt=""/>
                 </xsl:when>
             </xsl:choose>
       </div>
       <br/>
    </xsl:template>

</xsl:stylesheet>
