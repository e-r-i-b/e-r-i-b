<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="imagePath" value="${skinUrl}/skins/sbrf/images"/>

<tiles:insert definition="loginMain" flush="false">
    <tiles:put name="data" type="string">
        <html:form action="/logoffPromo">
            <c:set var="logoffUrl" value="${csa:calculateActionURL(pageContext, '/logoffPromo')}"/>
            <c:set var="form" value="${csa:currentForm(pageContext)}"/>
            <c:set var="promoSession" value="${form.promoSession}"/>
            <c:set var="promoSessionCreationDate" value="${promoSession.creationDate}"/>

            <tiles:put name="showLoginAndPasswordForm" value="false"/>
            <tiles:put name="showHelp" value="false"/>
            <tiles:put name="showRegistration" value="false"/>
            <tiles:put name="showSecurityBlock" value="false"/>
            <tiles:put name="loginFormHeader">

                <span class="b-ie">
                <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                </span><!-- // b-ie -->

                <div class="auth_header invoice_promo">
                    Программа продвижения
                </div>
                <div class="auth_body">

                    <div class="b-logo">
                        <img width="143" height="41" alt="" src="${skinUrl}/skins/sbrf/images/csa/loginPage/b-logo_img.png" class="logo_img">
                    </div><!-- // g-logo -->

                    <form autocomplete="off" id="loginForm" action="${logoffUrl}" class="b-form auth_form">
                        <div class="form_note">
                            <c:set var="hrs"><fmt:formatDate value="${promoSessionCreationDate.time}" pattern="HH"/></c:set>
                            <c:set var="mns"><fmt:formatDate value="${promoSessionCreationDate.time}" pattern="mm"/></c:set>
                            <c:set var="scnds"><fmt:formatDate value="${promoSessionCreationDate.time}" pattern="ss"/></c:set>
                            Рабочая смена начата в&nbsp;<fmt:formatDate value="${promoSessionCreationDate.time}" pattern="HH:mm"/>.<br>
                            Отработано
                            <span id="currentHours"></span>&nbsp;часов<br>
                            <span id="currentMinutes"></span>&nbsp;минуты.
                        </div>

                            <%--пустое поле, чтобы не было скриптовой ошибки при создании jForm--%>
                        <div onclick="" class="b-field"></div>

                        <div class="form_submit">
                            <div class="b-btn btn-large btn-red" onclick="validateAndSubmit()">
                                <div class="btn_text">Закончить смену</div>
                                <i class="btn_curve"></i>
                                <button class="btn_hidden" type="button"></button>
                            </div><!-- // b-btn -->

                            <a href="${csa:calculateActionURL(pageContext, '/index.do')}" class="form_remind aBlack">Продолжить смену</a>
                        </div>
                    </form><!-- // b-form -->
                </div>
            </tiles:put>
        </html:form>
        {scripts}
    </tiles:put>
    <tiles:put name="scripts">
        <script type="text/javascript">
            var currDate = new Date();
            var diffSeconds = (currDate.getHours() - ${hrs})*3600 + (currDate.getMinutes() - ${mns})*60 + currDate.getSeconds() - ${scnds};
            var hours = parseInt(diffSeconds/3600);
            var minutes = Math.floor(((diffSeconds - hours*3600)/60));
            document.getElementById('currentHours').innerHTML = hours.toString();
            document.getElementById('currentMinutes').innerHTML = minutes.toString();
        </script>


        <script type="text/javascript">
            function submitData()
            {
                var form = document.createElement("form");
                form.setAttribute("method", "post");
                form.setAttribute("action", "${csa:calculateActionURL(pageContext, '/logoffPromo')}");
                var hiddenField = document.createElement("input");
                hiddenField.setAttribute("type", "hidden");
                hiddenField.setAttribute("name", "operation");
                hiddenField.setAttribute("value", "button.logoffPromo");
                form.appendChild(hiddenField);
                document.body.appendChild(form);
                showOrHideAjaxPreloader(true);
                form.submit();
            }
        </script>
    </tiles:put>
</tiles:insert>