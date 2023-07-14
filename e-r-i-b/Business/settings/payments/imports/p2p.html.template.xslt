<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<!-- шаблоны полей для платежей -->
<!-- подключается тэгом <xsl:import href="commonTypes.html.template.xslt"/> -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:pru="java://com.rssl.phizic.business.profile.Utils"
                xmlns:xalan = "http://xml.apache.org/xalan">

    <xsl:import href="commonTypes.html.template.xslt" />

    <xsl:output method="html" version="1.0" indent="yes"/>

    <!--шаблон ввода сообщения получателю платежа-->
    <xsl:template name="edit-message-to-receiver-template">

        <xsl:if test="pu:impliesService('MessageWithCommentToReceiverService')">
            <xsl:call-template name="p2p-StandartRow">
                <xsl:with-param name="id">messageToReceiver</xsl:with-param>
                <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
                <xsl:with-param name="rowName">&nbsp;<div id="smsAvatarContainer" class="smsAvatarContainer"></div>
                </xsl:with-param>
                <xsl:with-param name="rowValue">
                    <script type="text/javascript">
                        function focusIn(){
                            document.getElementById("messBlock").className = "messBlock messBlockFocus";
                        }
                        function focusLost(){
                            document.getElementById("messBlock").className = "messBlock";
                        }
                    </script>
                    <div class="messBlock" id="messBlock">
                        <input id="messageToReceiver" name="messageToReceiver" type="text" value="{messageToReceiver}" maxlength="40"
                               title="Написать сообщение получателю" class="customPlaceholder" onfocus="focusIn();" onBlur="focusLost();"/>
                        <div class="messageItem"></div>
                    </div>
                </xsl:with-param>
                <xsl:with-param name="spacerClass" select="'spacer'"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <!--шаблон вывода статичного блока с сообщением получателю платежа-->
    <xsl:template name="view-message-to-receiver-template">

        <xsl:if test="string-length(messageToReceiver) > 0">
            <xsl:call-template name="p2p-StandartRow">
                <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
                <xsl:with-param name="rowName">
                    &nbsp;<div id="smsAvatarContainer" class="smsAvatarContainer"></div>
                </xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of select="messageToReceiver"/></b>&nbsp;<img src="{concat($resourceRoot, '/images/ERMB_sms.png')}" alt="sms"/>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:if test="(state = 'EXECUTED' or state = 'DISPATCHED' or state = 'UNKNOW')">
                <xsl:variable name="existMessageToReceiverStatus" select="string-length(messageToReceiverStatus) > 0"/>
                <xsl:variable name="sentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'sent'"/>
                <xsl:variable name="notSentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'not_sent'"/>

                <xsl:call-template name="p2p-StandartRow">
                    <xsl:with-param name="lineId">messageToReceiverStatusRow</xsl:with-param>
                    <xsl:with-param name="rowName">Статус SMS-сообщения</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                            <xsl:when test="$sentMessageToReceiverStatus">
                                <div id="messageToReceiverStatus" class="linear">
                                    <span class="messageToReceiverStatus"
                                          onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                          onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                        Сообщение отправлено
                                    </span>
                                </div>
                            </xsl:when>
                            <xsl:when test="$notSentMessageToReceiverStatus">
                                <div id="messageToReceiverStatus" class="linear">
                                    <span class="messageToReceiverStatus"
                                          onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                          onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                        Сообщение не отправлено
                                    </span>
                                </div>
                            </xsl:when>
                            <xsl:when test="$existMessageToReceiverStatus">
                                <xsl:value-of select="messageToReceiverStatus"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <div class="linear">
                                    сообщение будет отправлено
                                </div>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>
                <div id="messageToReceiverStatusDescription"
                     onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription','default');"
                     onmouseout="hideLayer('messageToReceiverStatusDescription');" class="layerFon stateDescription">
                    <div class="floatMessageHeader"></div>
                    <div class="layerFonBlock">
                        <xsl:choose>
                            <xsl:when test="$sentMessageToReceiverStatus">
                                Сообщение успешно отправлено получателю средств.
                            </xsl:when>
                            <xsl:when test="$notSentMessageToReceiverStatus">
                                Сообщение не удалось отправить получателю средств.
                            </xsl:when>
                        </xsl:choose>
                    </div>
                </div>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template name="p2p-StandartRow">

        <xsl:param name="id"/>
        <xsl:param name="lineId"/>                      <!--идентификатор строки-->
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
        <xsl:param name="spacerClass" select="''"/>     <!-- доп класс -->
        <xsl:param name="spacerHint"/>                  <!-- Подсказка к полю ввода -->
        <xsl:param name="readField"/>                   <!-- нередактируемые поля -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
        <xsl:param name="isAllocate" select="'false'"/>  <!-- Выделить при нажатии -->
        <xsl:param name="isNeedHint"/>                   <!-- Нужна ли подсказка к полю с сообщением об ошибке валидации -->
        <xsl:param name="textHint"/>                     <!-- Текст ошибки валидации -->

        <xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
        <!-- Определение имени инпута или селекта передаваемого в шаблон -->
        <!-- inputName - fieldName или имя поле вытащеное из rowValue -->
        <xsl:variable name="inputName">
            <xsl:choose>
                <xsl:when test="string-length($fieldName) = 0">
                    <xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
                        <xsl:value-of select="$nodeRowValue/input/@name" />
                        <xsl:if test="count($nodeRowValue/select[@name]) = 1">
                            <xsl:value-of select="$nodeRowValue/select/@name" />
                        </xsl:if>
                        <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
                            <xsl:value-of select="$nodeRowValue/textarea/@name" />
                        </xsl:if>
                    </xsl:if>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="$fieldName"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="readonly">
            <xsl:choose>
                <xsl:when test="string-length($inputName)>0">
                    <xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
                </xsl:when>
                <xsl:otherwise>
                    0
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="styleClass">
            <xsl:choose>
                <xsl:when test="$isAllocate = 'true'">
                    <xsl:value-of select="'form-row form-row-new'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'form-row-addition form-row-new'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <div class="{$styleClass}">
            <xsl:if test="string-length($lineId) > 0">
                <xsl:attribute name="id">
                    <xsl:copy-of select="$lineId"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="string-length($rowStyle) > 0">
                <xsl:attribute name="style">
                    <xsl:copy-of select="$rowStyle"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
                <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
            </xsl:if>

            <xsl:if test="$spacerClass != ''">
                <div class="{$spacerClass}"></div>
            </xsl:if>

            <xsl:if test="$spacerHint != ''">
                <div class="spacerHint">
                    <xsl:copy-of select="$spacerHint"/>
                </div>
            </xsl:if>
            <div class="paymentLabelNew">
                <span class="paymentTextLabel">
                    <xsl:if test="string-length($id) > 0">
                        <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
                    </xsl:if>
                    <xsl:if test="string-length($rowName) > 0">
                        <xsl:copy-of select="$rowName"/>
                    </xsl:if>
                </span>
                <xsl:if test="$isNeedHint">
                    <xsl:call-template name="hintTemplate">
                    <xsl:with-param name="hintText">
                        <div class="hintListText">
                            <xsl:copy-of select="$textHint"/>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
                </xsl:if>
            </div>
            <div class="paymentValue paymentValueNew">
                <div class="paymentInputDiv autoInputWidth {$readField}"><xsl:copy-of select="$rowValue"/>
                    <xsl:if test="$readonly = 0 and $mode = 'edit'">
                        <xsl:call-template name="buildDescription">
                            <xsl:with-param name="text" select="$description"/>
                        </xsl:call-template>
                    </xsl:if>
                    <script type="text/javascript">
                        $(document).ready(function(){
                            $('.errorRed').hover(function(){
                                    $(this).find('.errorDiv').addClass('autoHeight');
                                    $(this).find('.showFullText').hide();
                               }, function() {
                                    $(this).find('.errorDiv').removeClass('autoHeight');
                                    $(this).find('.showFullText').show();
                            });
                        });
                    </script>
                    <div class="errorDivBase errorDivPmnt">
                        <div class="errorRed">
                            <div class="errMsg">
                                <div class="errorDiv"></div>
                                <div class="showFullText">
                                    <div class="pointers"></div>
                                </div>
                            </div>
                            <div class="errorRedTriangle"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        </div>

        <!-- Устанавливаем события onfocus поля -->
        <xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
            <script type="text/javascript">
                if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
                    document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
            </script>
        </xsl:if>
    </xsl:template>

    <xsl:template name="hintTemplate">
        <xsl:param name="hintText"/>
        <script type="text/javascript">
            function closeHint()
            {
                $('.hintsBlock').hide();
            }
        </script>
        <div class="showHintBlock paymentHints">
            <a href="#" class="imgHintBlock newHints" onclick="return false;"></a>
            <div class="hintsBlock" style="display: none;">
                <div class="hintShadowTriangle"></div>
                <div class="workspace-box hintShadow">
                    <div class="hintShadowRT r-top">
                        <div class="hintShadowRTL r-top-left">
                            <div class="hintShadowRTR r-top-right">
                                <div class="hintShadowRTC r-top-center">
                                    <div class="closeHint" onclick="closeHint();"></div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="hintShadowRCL r-center-left">
                        <div class="hintShadowRCR r-center-right">
                            <div class="hintShadowRC r-content">
                                <xsl:copy-of select="$hintText"/>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="hintShadowRBL r-bottom-left">
                        <div class="hintShadowRBR r-bottom-right">
                            <div class="hintShadowRBC r-bottom-center"></div>
                        </div>
                     </div>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="topHint">
        <xsl:param name="topHintText"/>
        <xsl:param name="topHintValue"/>
        <xsl:param name="addHintClass"/>
        <xsl:param name="topHintId"/>
        <a href="#" class="topHint" onclick="return false;">

            <xsl:choose>
                <xsl:when test="$topHintId = ''">
                    <span><xsl:copy-of select="$topHintText"/></span>
                </xsl:when>
                <xsl:otherwise>
                    <span id="{$topHintId}Span"><xsl:copy-of select="$topHintText"/></span>
                </xsl:otherwise>
            </xsl:choose>

            <div class="topHintsBlock {$addHintClass}">
                <div class="hintShadowTriangle"></div>
                <div class="workspace-box hintShadow">
                    <div class="hintShadowRT r-top">
                        <div class="hintShadowRTL r-top-left">
                            <div class="hintShadowRTR r-top-right">
                                <div class="hintShadowRTC r-top-center"></div>
                            </div>
                        </div>
                    </div>
                    <div class="hintShadowRCL r-center-left">
                        <div class="hintShadowRCR r-center-right">
                            <div class="hintShadowRC r-content">
                                <div class="topPosition" id="{$topHintId}">
                                    <xsl:copy-of select="$topHintValue"/>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="hintShadowRBL r-bottom-left">
                        <div class="hintShadowRBR r-bottom-right">
                            <div class="hintShadowRBC r-bottom-center"></div>
                        </div>
                     </div>
                </div>
            </div>
        </a>
    </xsl:template>

    <xsl:template name="roundBorder">
        <xsl:param name="content"/>

        <div class="workspace-box bluePointer">
            <div class="bluePointerRT r-top">
                <div class="bluePointerRTL r-top-left">
                    <div class="bluePointerRTR r-top-right">
                        <div class="bluePointerRTC r-top-center">
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="bluePointerRCL r-center-left">
                <div class="bluePointerRCR r-center-right">
                    <div class="bluePointerRC r-content">
                        <div class="needPadding">
                            <xsl:copy-of select="$content"/>
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="bluePointerRBL r-bottom-left">
                <div class="bluePointerRBR r-bottom-right">
                    <div class="bluePointerRBC r-bottom-center"></div>
                </div>
             </div>
        </div>
    </xsl:template>

    <xsl:template name="errorContainer">
        <xsl:param name="sujestHintText"/>

        <div id="errorContainer" class="sujestHint">
            <div class="workspace-box roundYellow">
                <div class="roundYellowRT r-top">
                    <div class="roundYellowRTL r-top-left">
                        <div class="roundYellowRTR r-top-right">
                            <div class="roundYellowRTC r-top-center">
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="roundYellowRCL r-center-left">
                    <div class="roundYellowRCR r-center-right">
                        <div class="roundYellowRC r-content">
                            <div class="sujestHintText">
                                <xsl:copy-of select="$sujestHintText"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="roundYellowRBL r-bottom-left">
                    <div class="roundYellowRBR r-bottom-right">
                        <div class="roundYellowRBC r-bottom-center"></div>
                    </div>
                 </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="showContactTemplate">
        <xsl:param name="isView"/>
        <xsl:param name="contactName"/>
        <xsl:param name="contactPhone"/>
        <xsl:param name="contactCard"/>
        <xsl:param name="contactSberbank"/>
        <xsl:param name="avatarPath"/>

        <xsl:variable name="additClass"><xsl:if test="$isView = 'true'">float</xsl:if></xsl:variable>
        <table class="sugestTbl">
            <tr>
                <td>
                    <div class="sugestAvatar">
                        <div class="relative {$additClass}">
                            <xsl:choose>
                                <xsl:when test="$isView = 'true' and $avatarPath != ''">
                                    <img class="float avatarIcon" id="contactAvatar" src="{pru:getStaticImageUri()}/SMALL{$avatarPath}"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <img class="float avatarIcon" id="contactAvatar"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <div class="roundAvatar"></div>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="float">
                        <div id="selectedContactNameEl" class="clientName"><xsl:copy-of select="$contactName"/></div>
                        <div class="clear"></div>

                        <xsl:choose>
                            <xsl:when test="$isView = 'true'">
                                <xsl:choose>
                                    <xsl:when test="$contactCard != ''">
                                        <span class="float clientNumber contactCard" id="selectedContactCardEl"><xsl:copy-of select="$contactCard"/></span>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <span class="float clientNumber" id="selectedContactPhone">+7 <span id="selectedContactPhoneEl"><xsl:copy-of select="$contactPhone"/></span></span>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <xsl:if test="$contactSberbank = 'true'">
                                    <span id="isOurClient"></span>
                                </xsl:if>
                            </xsl:when>
                            <xsl:otherwise>
                                <span class="float clientNumber" id="selectedContactPhone">+7 <span id="selectedContactPhoneEl"> </span></span>
                                <span class="float clientNumber contactCard" id="selectedContactCardEl"> </span>
                                <span class="float clientNumber contactCard" id="NoCardEl" style="display:none;">
                                    <span class="noCard"></span> нет сохраненной карты
                                </span>
                                <span id="isOurClient"></span>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                </td>
            </tr>
        </table>
    </xsl:template>

</xsl:stylesheet>