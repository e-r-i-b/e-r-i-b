<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<c:set var="form" value="${AuthenticationFormBase}"/>
<c:set var="isGlobalLoginRestriction" value="${csa:isGlobalLoginRestriction()}"/>
<c:set var="isCorrectEnter" value="${csa:isCorrectEnter()}"/>
<c:set var="isPayOrder" value="${csa:isPayOrder()}"/>
<c:set var="isAccessRegistration" value="${csa:isAccessInternalRegistration()}"/>
<c:set var="isShowRegistration" value="${!form.fields.payOrder && isAccessRegistration}"/>
<c:set var="isWebShopPaymentSession" value="${csa:isWebShopPaymentSession()}"/>
<c:set var="isUECPaymentSession" value="${csa:isUECPaymentSession()}"/>
<c:set var="needOpenChooseRegionWindow" value="${(empty sessionScope.session_region || sessionScope.$$show_default_region == 'true') && $$show_region_functionality}"/>

<c:set var="external" value="${isPayOrder=='true' or isWebShopPaymentSession=='true' or isUECPaymentSession=='true'}"/>

<c:set var="redirectForm"/>
<c:if test="${external == 'true'}">
    <c:set var="redirectForm">
        <%-- блок формы для редиректа при оплате с внешней ссылки --%>
        <form action="" method="POST" name="$$POSTRedirect" id="redirectPayOrderForm" class="hidden-form" accept-charset="utf-8" enctype="multipart/form-data">
            <%--т.к. IE 6, 7, 8 игнорирует accept-charset, приходится добавлять hidden input с utf-символом в значении--%>
            <%--взято отсюда: http://stackoverflow.com/questions/153527/setting-the-character-encoding-in-form-submit-for-internet-explorer--%>
            <input name="iehack" type="hidden" value="&#9760;"/>
            <c:set var="fields" value="${form.fields}"/>
            <c:choose>
                <c:when test="${not empty fields.PayInfo}">
                    <html:textarea name="fields" property="PayInfo"/>
                </c:when>
                <c:when test="${not empty fields.ReqId}">
                    <html:text name="fields" property="ReqId"/>
                </c:when>
                <c:when test="${not empty fields.UECPayInfo}">
                   <html:text name="fields" property="UECPayInfo"/>
                </c:when>
            </c:choose>
         </form>
    </c:set>
</c:if>

<tiles:insert definition="loginMain" flush="false">
    <c:choose>
        <c:when test="${!isGlobalLoginRestriction}">
            <%-- если доступен вход --%>
            <c:choose>
                <c:when test="${isPayOrder && isCorrectEnter && isUECPaymentSession}">
                    <tiles:put name="loginFormHeader">
                        ${redirectForm}
                        <span class="b-ie">
                        <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                        </span><!-- // b-ie -->

                        <div class="auth_header invoice_uecard">
                            <span class="auth_invoice">
                                <img src="${skinUrl}/skins/sbrf/images/csa/loginPage/tmp/invoice_uecard-pic.png" alt="Универсальная электронная карта"/>
                            </span>
                        </div>
                    </tiles:put>
                    <tiles:put name="showLoginAndPasswordForm" value="true"/>
                    <tiles:put name="customFooterLogo">
                        <div class="b-invoice-logo">
                            <img class="invoice-logo_pic" src="${skinUrl}/skins/sbrf/images/csa/loginPage/tmp/footer-ue-pic.png" alt=""/>
                        </div><!-- // b-invoice-logo -->
                    </tiles:put>
                </c:when>
                <c:when test="${isPayOrder && isCorrectEnter && !isUECPaymentSession}">
                    <tiles:put name="loginFormHeader">
                        ${redirectForm}
                        <span class="b-ie">
                        <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                        </span><!-- // b-ie -->

                        <div class="auth_header invoice_unknown">
                            <span class="b-ie">
                                <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                            </span><!-- // b-ie -->
                            <div class="relative-ie">Войдите, чтобы оплатить выставленный счет</div>
                        </div>
                    </tiles:put>
                    <tiles:put name="showLoginAndPasswordForm" value="true"/>
                    <tiles:put name="showHelp" value="${!isWebShopPaymentSession}"/>
                </c:when>
                <c:when test="${isPayOrder && !isCorrectEnter}">
                    <tiles:put name="loginFormHeader">
                        ${redirectForm}
                        <span class="b-ie">
                        <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                        </span><!-- // b-ie -->
                        <div class="auth_header invoice_disabled">
                            <span class="b-ie">
                            <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                            </span><!-- // b-ie -->
                            <div class="relative-ie">
                                <h4>Вход закрыт</h4>
                                <p>Эта ссылка предназначена только для для оплаты выставленных счетов. Перейдите на&nbsp;обычную <a href="${csa:calculateActionURL(pageContext, '/index')}">страницу входа</a> a или вернитесь на&nbsp;сайт магазина</p>
                            </div>
                        </div>
                        <div class="auth_body">
                            <div class="auth_redirect relative-ie">
                                <a class="auth_link aBlack" href="${csa:calculateActionURL(pageContext, '/index')}">Войти в&nbsp;Сбербанк Онлайн c&nbsp;логином и&nbsp;паролем</a>
                            </div>
                        </div>
                    </tiles:put>
                </c:when>
                <c:when test="${!isPayOrder}">
                    <tiles:put name="loginFormHeader">
                        <span class="b-ie">
                            <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i
                        class="t"></i><i class="b"></i>
                        </span><!-- // b-ie -->
                    </tiles:put>
                    <tiles:put name="showLoginAndPasswordForm" value="true"/>
                    <c:choose>
                        <c:when test="${form.isPromoCookie}">
                            <tiles:put name="showLinks" value="false"/>
                            <tiles:put name="showLogoffPromoLink" value="true"/>
                        </c:when>
                        <c:otherwise>
                            <tiles:put name="showLinks" value="true"/>
                            <tiles:put name="customFooterLogo">
                                <a class="b-apps" href="http://www.sberbank.ru/moscow/ru/person/dist_services/inner_apps/" target="_blank">
                                    <span class="apps_text">Мобильные приложения</span>
                                </a><!-- // b-apps -->
                            </tiles:put>
                        </c:otherwise>
                    </c:choose>

                </c:when>
            </c:choose>
        </c:when>
        <c:otherwise>
            <%-- если стоит глобальная блокировка входа --%>
            <tiles:put name="loginFormHeader">
                <span class="b-ie">
                <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                </span><!-- // b-ie -->
                <div class="auth_header invoice_disabled">
                    <span class="b-ie">
                    <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                    </span><!-- // b-ie -->
                    <div class="relative-ie">
                        <h4>Вход закрыт</h4>
                        <p><bean:message key="message.global.bloking.login" bundle="commonBundle"/></p>
                    </div>
                </div>
                <div class="auth_body">
                    <div class="auth_redirect relative-ie">
                        <a href="${csa:calculateActionURL(pageContext, '/index')}" class="auth_link aBlack">Войти в Сбербанк Онлайн c логином и паролем</a>
                    </div>
                </div>
            </tiles:put>

        </c:otherwise>
    </c:choose>
    <tiles:put name="showHelp" value="false"/>
    <tiles:put name="formAction"    value="${csa:calculateActionURL(pageContext, external?'/payOrderLogin.do':'/login.do')}"/>
    <tiles:put name="showRegistration" value="${isShowRegistration}"/>
    <tiles:put name="scripts">
        <c:if test="${phiz:isScriptsRSAActive()}">
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

            <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
            <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>
        </c:if>
        <script type="text/javascript">
            function submitData()
            {
                <c:if test="${phiz:isScriptsRSAActive()}">
                    <%-- формирование основных данных для ФМ --%>
                    new RSAObject().toHiddenParameters();
                </c:if>

                authForm.hidePassword();
                authForm.submit($('#buttonSubmit')[0], 'button.begin');
            }

            function setElementValue(name, value)
            {
                var frm = $("#loginForm")[0];
                var field = getElementFromCollection(frm.elements, name);
                if (field == null)
                {
                    addField('hidden', name, value);
                }
                else
                {
                    field.value = value;
                }
            }
        </script>
        <c:if test="${phiz:needKasperskyScript()}">
            <script type="text/javascript" src="https://af.kaspersky-labs.com/sb/kljs"></script>
        </c:if>
    </tiles:put>
</tiles:insert>

