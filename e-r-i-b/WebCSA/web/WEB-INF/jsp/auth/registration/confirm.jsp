<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:insert definition="stage" flush="false">
    <tiles:put name="title" value="Подтверждение регистрации" type="string"/>
    <tiles:put name="description" type="string">
        Пожалуйста, для подтверждения регистрации введите SMS-пароль, полученный в сообщении.
        Убедитесь, что пароль введен верно, и нажмите на кнопку «Подтвердить».
    </tiles:put>
    <tiles:put name="data" type="string">
        <html:form action="/popup/registration" onsubmit="stageForm.send('button.next'); return false">
            <c:set var="form" value="${csa:currentForm(pageContext)}"/>
            <c:set var="operationInfo" value="${form.operationInfo}"/>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">
                     Номер карты:
                </tiles:put>
                <tiles:put name="data">
                    <span class="bold">${csa:getCutCardNumber(operationInfo.cardNumber)}</span>
                </tiles:put>
            </tiles:insert>
            <hr/>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">
                    <span class="fieldTitle" id="fieldTitle">
                        Введите <span class="bold">SMS-пароль :</span>
                    </span>
                </tiles:put>
                <tiles:put name="data">
                    <input type="text" name="field(confirmPassword)" size="10"/>
                </tiles:put>
            </tiles:insert>

            <div class="buttonsArea">
                <div class="clientButton" onclick="win.close('stageForm');">
                    <div class="buttonGrey">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Отменить</span>
                        </div>
                        <div class="right-corner"></div>
                    </div>
                    <div class="clear"></div>
                </div>

                <div onclick="stageForm.send('button.next');" class="commandButton">
                    <div class="buttonGreen">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Подтвердить</span>
                        </div>
                        <div class="right-corner"></div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <script type="text/javascript">
                if(window.payInput && window.$)
                {
                    payInput.setEvents(payInput, ensureElement("stageForm"));
                    $("#stageForm input[name='field(confirmPassword)']").focus();
                }
            </script>
            <%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
        </html:form>
    </tiles:put>
</tiles:insert>