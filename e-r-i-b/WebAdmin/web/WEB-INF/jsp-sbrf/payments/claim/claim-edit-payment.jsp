<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/claim/payments/edit" onsubmit="return setEmptyAction(event);">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="stateCode" value="${frm.document.state.code}"/>
    <c:set var="actionURL" value="${phiz:calculateActionURL(pageContext, '/private/loanclaim/office/list.do')}"/>

    <tiles:insert definition="loanClaim">
        <tiles:put name="submenu" type="string" value="LoanClaimFind"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"            value="paymentFormId"/>
                <tiles:put name="name"          value="${title}"/>
                <tiles:put name="description"   value="${description}"/>
                <tiles:put name="data"          type="string">
                    <c:if test="${!(stateCode eq 'INITIAL')}">
                        <tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
                            <tiles:put name="document" beanName="frm" beanProperty="document"/>
                            <tiles:put name="description" value=""/>
                        </tiles:insert>
                    </c:if>
                    <div class="clear"></div>
                    <div class="paymentsForm">
                        ${frm.html}
                    </div>
                    <c:if test="${stateCode != 'INITIAL' && frm.document.state.code != 'INITIAL7'}">
                        <input type="hidden" id="receivingRegion" name="receivingRegion"/>
                        <input type="hidden" id="receivingOffice" name="receivingOffice"/>
                        <input type="hidden" id="receivingBranch" name="receivingBranch"/>
                    </c:if>
                    <input type="hidden" id="visitToVSP" value="false"/>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="buttons">
                    <c:if test="${stateCode != 'INITIAL'}">
                        <%--Редактировать--%>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.edit"/>
                            <tiles:put name="commandTextKey" value="button.edit"/>
                            <tiles:put name="commandHelpKey" value="button.edit.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${phiz:impliesService('LoanClaimEmployeeService')}">
                        <%--Отложить--%>
                        <c:choose>
                            <c:when test="${(frm.document.fullMobileNumber==null) and (frm.document.owner.person.mobilePhone==null or frm.document.owner.person.mobilePhone == '')}">
                                <tiles:insert definition="confirmationButton" flush="false">
                                    <tiles:put name="winId"                 value="confirmBack"/>
                                    <tiles:put name="title">Введите номер мобильного телефона:</tiles:put>
                                    <tiles:put name="message"><div>Номер:&nbsp;+<input name='mobileCountry' maxlength='4' size='4' type='text' value='7'/>&nbsp;<input name='mobileTelecom' maxlength='3'size='3' type='text'/>&nbsp;<input name='mobileNumber'  maxlength='7' size='7' type='text'/></div></tiles:put>
                                    <tiles:put name="buttonViewType"        value="buttonGreen"/>
                                    <tiles:put name="confirmCommandKey"     value="button.postpone"/>
                                    <tiles:put name="confirmCommandTextKey" value="button.postpone"/>
                                    <tiles:put name="confirmKey"            value="button.postpone"/>
                                    <tiles:put name="currentBundle"         value="paymentsBundle"/>
                                    <tiles:put name="validationFunction">confirm('<bean:message key="button.postpone.confirm.message" bundle="paymentsBundle"/>')</tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.postpone"/>
                                    <tiles:put name="commandTextKey" value="button.postpone"/>
                                    <tiles:put name="commandHelpKey" value="button.postpone.help"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="validationFunction">
                                        confirm('<bean:message key="button.postpone.confirm.message" bundle="paymentsBundle"/>');
                                    </tiles:put>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                        <%--Отказ клиента от заполнения заявки--%>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.refuseClient"/>
                            <tiles:put name="commandTextKey" value="button.refuseClient"/>
                            <tiles:put name="commandHelpKey" value="button.refuseClient.help"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="validationFunction">
                                confirm('<bean:message key="button.refuseClient.confirm.message" bundle="paymentsBundle"/>');
                            </tiles:put>
                        </tiles:insert>
                        <%--Отправить в ВСП--%>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.sendToVSP"/>
                            <tiles:put name="commandHelpKey" value="button.sendToVSP"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="onclick" value="showVSP();"/>
                        </tiles:insert>
                    </c:if>
                    <%--Сохранить--%>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"            value="button.save"/>
                        <tiles:put name="commandTextKey"        value="button.save"/>
                        <tiles:put name="commandHelpKey"        value="button.save"/>
                        <tiles:put name="bundle"                value="commonBundle"/>
                        <tiles:put name="validationFunction"    value="checkPayment()"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        doOnLoad(function()
        {
            <c:if test="${frm.document.state.code == 'INITIAL2'}">
                $("#workspace").height("1100px");
            </c:if>
        });
        function showVSP()
        {
            $('#visitToVSP').val("true");
            var windowWidth  = 820;
            var windowHeight = 900;
            var url = '${actionURL}';
            var win  =  window.open(url, 'Departments', "resizable=1, menubar=0, toolbar=0, scrollbars=1, width=" + windowWidth + ", height=" + windowHeight);
            win.moveTo((screen.width - windowWidth)/2, (screen.height - windowHeight)/2);

            return false;
        }

        function setOfficeInfo(a)
        {
            $('#receivingRegion').val(a["region"]);
            $('#receivingOffice').val(a["office"]);
            $('#receivingBranch').val(a["branch"]);

            $('#receivingDepartmentLabel').text(a["name"]);
            $('#receivingDepartmentName').val(a["name"]);

            var visitToVSP = $('#visitToVSP').val();
            var sendToVsp = createCommandButton('button.sendToVSP','');
            if (sendToVsp != undefined && visitToVSP=="true")
            {
                $('#visitToVSP').val("false");
                sendToVsp.click('', false);
            }
        }
    </script>
</html:form>