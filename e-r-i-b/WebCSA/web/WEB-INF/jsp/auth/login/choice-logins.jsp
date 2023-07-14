<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:insert definition="stage" flush="false">
    <tiles:put name="title" value="" type="string">
        <bean:message bundle="commonBundle" key="label.registration"/>
    </tiles:put>
    <tiles:put name="description" type="string">
        <bean:message bundle="commonBundle" key="message.login.choice"/>
    </tiles:put>
    <c:set var="form" value="${LoginForm}"/>
    <tiles:put name="data" type="string">
        <html:form action="${form.fields.payOrder?'/payOrderLogin':'/login'}" styleId="choiceLoginForm">
            <c:set var="operationInfo" value="${form.operationInfo}"/>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="Выберите идентификатор:"/>
                <tiles:put name="data">
                    <script type="text/javascript">
                        var htmlChoiseLogin = '<h2 class="popup_title"><bean:message key="message.choice.login.title" bundle="commonBundle"/></h2><div class="popup_content"><p><bean:message key="message.choice.login.description" bundle="commonBundle"/></p>' +

                                '<form class="b-form form_popup" id="formChoiceLogin" action="button.next"><fieldset class="form_fields">' +
                                    <c:forEach var="item" items="${operationInfo.connectorInfos}">
                                        '<label class="form_label">'+
                                            '<input class="form_cbx" name="field(connectorGuid)" value="${item.guid}" type="radio"/> ${item.login}'+
                                        '</label>'+
                                    </c:forEach>

                                    '</fieldset><div class="form_submit"><div class="b-btn btn-small btn-yellow"><div class="btn_text"><bean:message key="message.choice.login.button.next" bundle="commonBundle"/></div><i class="btn_curve"></i>'+
                                '<button type="button" onclick="submitChoicesLogin()" class="btn_hidden"></button></div></div></form></div>';
                        overlay.showPopup('MultiRegFix', htmlChoiseLogin);

                        function submitChoicesLogin()
                        {
                            var form = $('#formChoiceLogin')[0];
                            <c:set var="nameForm" value="${form.fields.payOrder?'/payOrderLogin':'/login'}"/>
                            form.setAttribute("action", "${csa:calculateActionURL(pageContext, nameForm)}");
                            FormSubmitter.submit(form, "button.next");
                        }
                    </script>
                </tiles:put>
            </tiles:insert>

            <div class="buttonsArea">
                <div class="clientButton" onclick="stageForm.close();">
                    <div class="buttonGrey">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Отменить</span>
                        </div>
                        <div class="right-corner"></div>
                        <div class="clear"></div>
                    </div>
                </div>

                <div class="commandButton" onclick="stageForm.send('button.next');">
                    <div class="buttonGreen">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Продолжить</span>
                        </div>
                        <div class="right-corner"></div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </html:form>
    </tiles:put>
</tiles:insert>

