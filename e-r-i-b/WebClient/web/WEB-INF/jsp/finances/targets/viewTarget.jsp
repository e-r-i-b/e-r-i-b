<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/finances/targets/viewTarget" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="tarifPlanCode">
        <bean:write name="form" property="field(depositTariffPlanCode)"/>
    </c:set>
    <tiles:insert definition="financePlot">

        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderWithoutTop" flush="false">
                <tiles:put name="top">
                    <c:set var="selectedTab" value="myTargets"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="financeContainer" flush="false">
                        <tiles:put name="showFavourite" value="false"/>
                        <tiles:put name="data">
                            <div id="payment">
                                <div id="paymentForm">
                                    <div class="editAccountTargetBlock">
                                        <div class="titleAccTarget"><bean:message key="editTarget.fields.title" bundle="financesBundle"/></div>

                                        <c:set var="targetType" value="${form.fields['dictionaryTarget']}"/>
                                        <tiles:insert definition="formRow" flush="false">
                                            <tiles:put name="title"><img class="orangeBorder" src="${commonImagePath}/account_targets/${targetType}.png"/></tiles:put>
                                            <tiles:put name="data">
                                                <html:hidden name="form" property="field(dictionaryTarget)"/>
                                                <span class="bold"><bean:write name="form" property="field(targetName)"/></span>
                                                <br/><br/>
                                                <span class="inputPlaceholder"><bean:write name="form" property="field(targetNameComment)"/></span>
                                            </tiles:put>
                                        </tiles:insert>

                                        <tiles:insert definition="formRow" flush="false">
                                            <tiles:put name="title"><bean:message key="target.amount.title" bundle="financesBundle"/>:</tiles:put>
                                            <tiles:put name="data">
                                                <span class="summ">
                                                    <bean:write name="form" property="field(targetAmount)"/>
                                                    <bean:message key="currency.rub" bundle="financesBundle"/>
                                                </span>
                                            </tiles:put>
                                        </tiles:insert>

                                        <tiles:insert definition="formRow" flush="false">
                                            <tiles:put name="title"><bean:message key="target.planedDate.title" bundle="financesBundle"/>:</tiles:put>
                                            <tiles:put name="data">
                                                <span class="bold"><bean:write name="org.apache.struts.taglib.html.BEAN" property="field(targetPlanedDate)" format="dd/MM/yyyy"/></span>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>

                                    <c:if test="${phiz:impliesService('CreateMoneyBoxPayment')}">
                                        <%@ include file="/WEB-INF/jsp-sbrf/moneyBox/template/money-box-fields-view.jsp"%>
                                    </c:if>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="data">
                                            <tiles:put name="title">&nbsp;</tiles:put>
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="read.depositInfo"/>
                                                <tiles:put name="commandHelpKey" value="read.depositInfo.help"/>
                                                <tiles:put name="bundle" value="financesBundle"/>
                                                <tiles:put name="viewType" value="blueGrayLink"/>
                                                <tiles:put name="onclick" value="viewDepositTerms();"/>
                                            </tiles:insert>
                                        </tiles:put>
                                    </tiles:insert>

                                    <%--Просмотр дополнительного соглашения ко вкладу--%>
                                    <c:if test="${tarifPlanCode != 0}">
                                        <tiles:insert definition="formRow" flush="false">
                                            <tiles:put name="data">
                                                <tiles:put name="title">&nbsp;</tiles:put>
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="read.depositTarifInfo"/>
                                                    <tiles:put name="commandHelpKey" value="read.depositTarifInfo.help"/>
                                                    <tiles:put name="bundle" value="financesBundle"/>
                                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                                    <tiles:put name="onclick" value="viewDepositTerms('tariffTerms');"/>
                                                </tiles:insert>
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title">&nbsp;</tiles:put>
                                        <tiles:put name="data">
                                            <input id="agreeForAll" type="checkbox" name="agreeForAll" value="" style="vertical-align: middle;" disabled="true" checked="checked">
                                            <span class="bold"><bean:message key="agree.depositInfo.text" bundle="financesBundle"/></span>
                                        </tiles:put>
                                    </tiles:insert>

                                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/finances/targets/viewDepositTerms')}"/>
                                    <script type="text/javascript">
                                        function removeMessageByButtonName(buttonName)
                                        {

                                        }

                                        function checkClientAgreesCondition(buttonName)
                                        {
                                            return true;
                                        }

                                        function viewDepositTerms(tariffTerms)
                                        {
                                            var url = '${url}';
                                            if (tariffTerms != undefined)
                                                url = url + '?termsType='+tariffTerms;
                                            window.open(url, 'depositInfo', 'menubar=1,toolbar=0,scrollbars=1');
                                        }
                                    </script>
                                </div>

                                <div class="backToEditTarget backToServiceBottom">
                                    <%-- назад к выбору цели --%>
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.back.to.target.edit"/>
                                        <tiles:put name="commandHelpKey" value="button.back.to.target.edit.help"/>
                                        <tiles:put name="bundle" value="financesBundle"/>
                                        <tiles:put name="action" value="/private/finances/targets/editTarget?id=${form.targetId}"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                        <tiles:put name="image"       value="backIcon.png"/>
                                        <tiles:put name="imageHover"     value="backIconHover.png"/>
                                        <tiles:put name="imagePosition"  value="left"/>
                                    </tiles:insert>
                                </div>
                                <div class="clear"></div>

                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="edit.operation.button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="edit.operation.button.cancel.help"/>
                                        <tiles:put name="bundle" value="financesBundle"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                        <tiles:put name="action" value="/private/finances/targets/targetsList"/>
                                    </tiles:insert>

                                    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
                                    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>

                                    <span class="clientButton chooseConfirmStrategy">
                                        <tiles:insert definition="confirmButtons" flush="false">
                                            <tiles:put name="ajaxUrl" value="/private/async/payments/confirm"/>
                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                            <tiles:put name="message">
                                                <bean:message key="card.payments.security.param.message" bundle="securityBundle" arg0="${documentType}"/>
                                            </tiles:put>
                                            <tiles:put name="formName" value="${form.metadata.form.name}"/>
                                            <tiles:put name="mode" value="${phiz:getUserVisitingMode()}"/>
                                            <tiles:put name="stateObject" value="document"/>
                                        </tiles:insert>
                                    </span>
                                </div>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>