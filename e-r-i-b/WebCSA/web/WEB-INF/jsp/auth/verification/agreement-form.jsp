<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:insert definition="loginMain" flush="false">
    <tiles:put name="showRegistration" value="false"/>
    <tiles:put name="showSecurityBlock" value="false"/>
    <tiles:put name="additionalContent">
        <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/roundBorder.css"/>
        <c:if test="${csa:isModernCSS()}">
            <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/modern.css"/>
        </c:if>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/style.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/rou.css"/>
        <html:form action="/businessEnvironment/verify">
        <div class="workspace-box shadow">
            <div class="shadowRT r-top">
                <div class="shadowRTL r-top-left">
                    <div class="shadowRTR r-top-right">
                        <div class="shadowRTC r-top-center"></div>
                    </div>
                </div>
            </div>
            <div class="shadowRCL r-center-left">
                <div class="shadowRCR r-center-right">
                    <div class="shadowRC r-content">
                    <c:set var="operationInfo" value="${BusinessEnvironmentForm.operationInfo}"/>
                <c:set var="preferredConfirmType" value="${operationInfo.preferredConfirmType}"/>
                <c:set var="pushAllowed" value="${operationInfo.pushAllowed}"/>
                <%-- окно с текстом соглашения --%>
                <tiles:insert definition="window" flush="false">
                    <tiles:put name="id" value="agreement"/>
                    <tiles:put name="data">
                        <div class="message">
                            <h2><bean:message bundle='businessEnvironmentBundle' key='form.agreement.detail.message.title'/></h2>
                            <bean:message bundle='businessEnvironmentBundle' key='form.agreement.detail.message'/>
                        </div>
                        <div class="buttonsArea">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="id" value="cancel"/>
                                <tiles:put name="type" value="Grey"/>
                                <tiles:put name="onclick" value="win.close('agreement');"/>
                                <tiles:put name="title"><bean:message bundle='businessEnvironmentBundle' key='form.agreement.detail.button.cancel'/></tiles:put>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </tiles:insert>
                <%-- окно с выбором карты --%>
                <tiles:insert definition="window" flush="false">
                    <tiles:put name="id" value="cards"/>
                    <tiles:put name="closeCallback" value="onCardsListCloseCallback"/>
                    <tiles:put name="data">
                        <div class="oneTimePasswordWindow">
                            <h2><bean:message bundle='businessEnvironmentBundle' key='form.confirm.title'/></h2>
                        </div>
                        <div>
                            <bean:message bundle='businessEnvironmentBundle' key='form.confirm.select.card.messages'/>
                        </div>

                        <div id="cardSelectorError" style="display: none">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="red"/>
                                <tiles:put name="data"><div class="infoMessage"><div class="messageContainer"></div></div></tiles:put>
                            </tiles:insert>
                        </div>

                        <c:set var="cards" value="${operationInfo.cardConfirmationSource}"/>
                        <c:set var="cardId" value=""/>
                        <c:forEach var="card" items="${cards}" varStatus="iteratorState">
                            <c:set var="imgCode" value="default"/>

                            <c:set var="cardNumber">${csa:getCutCardNumber(card.value)}</c:set>
                            </br>
                            <div class="pruductImg">
                                <img src="${skinUrl}/skins/sbrf/images/cards_type/icon_cards_default64.gif" border="0"/>
                            </div>
                            <div class="productTitle paddingTop24">
                                <div class="buttonGrey textDecorationUnderline" onclick="initializeCardConfirm('${card.key}');">
                                    <b><c:out value="${cardNumber}"/></b>
                                </div>
                            </div>
                            <div class="clear"></div>
                            <c:if test="${iteratorState.first and iteratorState.last}">
                                <c:set var="cardId" value="${card.key}"/>
                            </c:if>
                            <c:if test="${not iteratorState.last}">
                                <div class="productDivider"></div>
                            </c:if>
                        </c:forEach>
                    </tiles:put>
                </tiles:insert>
                <%-- окно подтверждения --%>
                <tiles:insert definition="window" flush="false">
                    <tiles:put name="id" value="confirm"/>
                    <tiles:put name="data">
                        <div id="confirmData">
                        </div>
                    </tiles:put>
                </tiles:insert>
                <script type="text/javascript">
                    function openAgreement()
                    {
                        win.open("agreement");
                        $('#acceptAgreement').removeAttr("disabled");
                    }

                    function validateAgreementForm()
                    {
                        if ($('#acceptAgreement').is(":checked"))
                            return true;

                        displayError("<bean:message bundle='businessEnvironmentBundle' key='form.agreement.message.not.accept'/>");
                        return false;
                    }

                    function openConfirmVerifyWindow(data)
                    {
                        $('#confirmData').html(data);
                        win.open('confirm');
                    }
                    function onCardsListCloseCallback()
                    {
                        $(win.active).find("#cardSelectorError").hide();
                        return true;
                    }

                    function processCardConfirmInitResponce(data)
                    {
                        var errorText = $(data).find("#confirmationError").html();
                        if (errorText == null)
                        {
                            win.close('cards');
                            return openConfirmVerifyWindow(data);
                        }
                        var errorContainer = $(win.active).find("#cardSelectorError");
                        errorContainer.find(".messageContainer").html('<div class = "itemDiv">' + errorText + '</div>');
                        errorContainer.show();
                        return true;
                    }

                    function initializeCardConfirm(cardKey)
                    {
                        $('[name=cardId]').val(cardKey);
                        getButton('button.initializeVerify.card').click();
                    }

                    function tryCardConfirm()
                    {
                        if (validateAgreementForm())
                        <c:choose>
                            <c:when test="${not empty cardId}">
                                initializeCardConfirm('${cardId}');
                            </c:when>
                            <c:otherwise>
                                win.open('cards');
                            </c:otherwise>
                        </c:choose>
                    }

                    function changeButtonEnabled()
                    {
                        var enabled = $('#acceptAgreement').is(":checked");
                        getButton("button.initializeVerify.sms").setEnabled(enabled);
                        getButton("button.try.initializeVerify.card").setEnabled(enabled);
                        <c:if test="${pushAllowed}">
                            getButton("button.initializeVerify.push").setEnabled(enabled);
                        </c:if>
                    }

                    $(document).ready(function(){
                        getButton("button.initializeVerify.sms").setEnabled(false);
                        getButton("button.try.initializeVerify.card").setEnabled(false);
                        <c:if test="${pushAllowed}">
                            getButton("button.initializeVerify.push").setEnabled(false);
                        </c:if>
                    });
                </script>

                            <h2>
                                <img src="${skinUrl}/skins/commonSkin/images/csa/icon_businessEnvironment.gif">
                                <span><bean:message bundle='businessEnvironmentBundle' key='form.agreement.title'/></span>
                            </h2>
                            <p><bean:message bundle='businessEnvironmentBundle' key='form.agreement.message.first' arg0='${csa:getBusinessEnvironmentUserURL()}'/></p>
                            <p><bean:message bundle='businessEnvironmentBundle' key='form.agreement.message.second' arg0='openAgreement()'/></p>
                            <div>
                                <input type="hidden" name="cardId"/>
                                <input type="checkbox" id="acceptAgreement" name="field(agreementAccept)" tabindex="1" value="true" disabled="disabled" onclick="changeButtonEnabled()"/>
                                <label for="acceptAgreement"><bean:message bundle='businessEnvironmentBundle' key='form.agreement.accept'/></label>
                            </div>

                            <div class="buttonsArea">
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="id" value="button.try.initializeVerify.card"/>
                                    <tiles:put name="title"><bean:message bundle='businessEnvironmentBundle' key='form.agreement.button.confirmByCard'/></tiles:put>
                                    <tiles:put name="validationFunction" value="validateAgreementForm"/>
                                    <c:choose>
                                        <c:when test="${preferredConfirmType eq 'card'}">
                                            <tiles:put name="type" value="Green"/>
                                            <tiles:put name="isDefault" value="true"/>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:put name="type" value="Grey"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <tiles:put name="onclick" value="tryCardConfirm()"/>
                                    <tiles:put name="tabindex" value="2"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="id" value="button.initializeVerify.card"/>
                                    <tiles:put name="type" value="Hidden"/>
                                    <tiles:put name="useAjax" value="true"/>
                                    <tiles:put name="afterAjax" value="processCardConfirmInitResponce"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="id" value="button.initializeVerify.sms"/>
                                    <tiles:put name="title"><bean:message bundle='businessEnvironmentBundle' key='form.agreement.button.confirmBySMS'/></tiles:put>
                                    <tiles:put name="validationFunction" value="validateAgreementForm"/>
                                    <c:choose>
                                        <c:when test="${preferredConfirmType eq 'sms'}">
                                            <tiles:put name="type" value="Green"/>
                                            <tiles:put name="isDefault" value="true"/>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:put name="type" value="Grey"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <tiles:put name="useAjax" value="true"/>
                                    <tiles:put name="afterAjax" value="openConfirmVerifyWindow"/>
                                    <tiles:put name="tabindex" value="3"/>
                                </tiles:insert>
                                <c:if test="${pushAllowed}">
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="id" value="button.initializeVerify.push"/>
                                        <tiles:put name="title"><bean:message bundle='businessEnvironmentBundle' key='form.agreement.button.confirmByPush'/></tiles:put>
                                        <tiles:put name="validationFunction" value="validateAgreementForm"/>
                                        <c:choose>
                                            <c:when test="${preferredConfirmType eq 'push'}">
                                                <tiles:put name="type" value="Green"/>
                                                <tiles:put name="isDefault" value="true"/>
                                            </c:when>
                                            <c:otherwise>
                                                <tiles:put name="type" value="Grey"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <tiles:put name="useAjax" value="true"/>
                                        <tiles:put name="afterAjax" value="openConfirmVerifyWindow"/>
                                        <tiles:put name="tabindex" value="4"/>
                                    </tiles:insert>
                                </c:if>
                            </div>

                    </div>
                </div>
            </div>
            <div class="shadowRBL r-bottom-left">
                <div class="shadowRBR r-bottom-right">
                    <div class="shadowRBC r-bottom-center"></div>
                </div>
            </div>
        </div>
        </html:form>
    </tiles:put>
</tiles:insert>