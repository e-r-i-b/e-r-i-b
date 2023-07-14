<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/finances/targets/editTarget" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
    <%--Доступно ли клиенту просмотр доп. соглашения по вкладу цели--%>
    <c:set var="needShowTarifPlan" value="${tarifPlanCodeType != '0' && form.haveTariffTemplate}"/>
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
                            <div class="editAccountTargetBlock">
                                <div class="titleAccTarget"><bean:message key="editTarget.fields.title" bundle="financesBundle"/></div>

                                <c:set var="targetType" value="${form.targetType}"/>
                                <tiles:insert definition="formRow" flush="false">
                                    <tiles:put name="title"><img class="orangeBorder" src="${commonImagePath}/account_targets/${targetType}.png"></tiles:put>
                                    <tiles:put name="data">
                                        <html:hidden name="form" property="field(dictionaryTarget)"/>
                                        <c:choose>
                                            <c:when test="${targetType == 'OTHER'}">
                                                <html:text name="form" property="field(targetName)" size="25" maxlength="35"/>
                                            </c:when>
                                            <c:otherwise>
                                                <html:hidden name="form" property="field(targetName)"/>
                                                <span class="bold"><bean:write name="form" property="field(targetName)"/></span>
                                            </c:otherwise>
                                        </c:choose>
                                        <br/><br/>
                                        <html:text name="form" property="field(targetNameComment)" styleClass="customPlaceholder" title="введите комментарий" size="25" maxlength="100"/>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="formRow" flush="false">
                                    <tiles:put name="title"><bean:message key="target.amount.title" bundle="financesBundle"/>:</tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <html:text name="form" property="field(targetAmount)" styleClass="moneyField" maxlength="19"/>
                                        <bean:message key="currency.rub" bundle="financesBundle"/>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="formRow" flush="false">
                                    <tiles:put name="title"><bean:message key="target.planedDate.title" bundle="financesBundle"/>:</tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <input type="text" name="field(targetPlanedDate)" class="date-pick dp-applied" size="10"
                                                           value="<bean:write name="org.apache.struts.taglib.html.BEAN" property="field(targetPlanedDate)" format="dd/MM/yyyy"/>"/>
                                    </tiles:put>
                                </tiles:insert>

                                <%@ include file="/WEB-INF/jsp-sbrf/moneyBox/template/money-box-fields-edit.jsp"%>

                                <div class="depositInfo">
                                    <span><bean:message key="editTarget.accounts.title" bundle="financesBundle"/></span>
                                </div>

                            </div>
                            <%--Просмотр условий вклада--%>
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="data">
                                    <tiles:put name="title">&nbsp;</tiles:put>
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="read.depositInfo"/>
                                        <tiles:put name="commandHelpKey" value="read.depositInfo.help"/>
                                        <tiles:put name="bundle" value="financesBundle"/>
                                        <tiles:put name="viewType" value="blueGrayLink"/>
                                        <tiles:put name="onclick" value="printDepositInfo();"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>

                            <%--По аналогии с открытием вклада из вкладов, отображаем ссылку только если у клиента тарифный план
                                отличный от обычного и есть процентные ставки у открываемого вклада для тарифного плана клиента--%>
                            <c:if test="${needShowTarifPlan}">
                                <%--Просмотр дополнительного соглашения ко вкладу--%>
                                <tiles:insert definition="formRow" flush="false">
                                    <tiles:put name="data">
                                        <tiles:put name="title">&nbsp;</tiles:put>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="read.depositTarifInfo"/>
                                            <tiles:put name="commandHelpKey" value="read.depositTarifInfo.help"/>
                                            <tiles:put name="bundle" value="financesBundle"/>
                                            <tiles:put name="viewType" value="blueGrayLink"/>
                                            <tiles:put name="onclick" value="printDepositInfo('tariffTerms');"/>
                                        </tiles:insert>
                                    </tiles:put>
                                </tiles:insert>
                            </c:if>

                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title">&nbsp;</tiles:put>
                                <tiles:put name="data">
                                    <input id="agreeForAll" type="checkbox" name="agreeForAll" value="" style="vertical-align: middle;" disabled="true">
                                    <span class="bold"><bean:message key="agree.depositInfo.text" bundle="financesBundle"/></span>
                                    <div class="payments-legend">
                                        <c:choose>
                                            <c:when test="${needShowTarifPlan}">
                                                <bean:message key="agree.depositInfo.hintWithTariff" bundle="financesBundle"/>
                                            </c:when>
                                            <c:otherwise>
                                                <bean:message key="agree.depositInfo.hint" bundle="financesBundle"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </tiles:put>
                            </tiles:insert>

                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/finances/targets/viewDepositTerms')}"/>
                            <script type="text/javascript">
                                function printDepositInfo(tariffTerms)
                                {
                                    var url = '${url}';
                                    if (tariffTerms != undefined)
                                        url = url + '?termsType='+tariffTerms;

                                    window.open(url, 'depositInfo', 'menubar=1,toolbar=0,scrollbars=1');
                                    var agreeForAll = ensureElement('agreeForAll');
                                    <c:choose>
                                        <c:when test="${needShowTarifPlan}">
                                            <!--две ссылки-->
                                            if(!isEmpty(tariffTerms))
                                            {
                                                <!--посмотрел первое условие-->
                                                window.userReadFirstConfirmMessage = true;
                                            }
                                            else
                                            {
                                                <!--посмотрел второе условие-->
                                                window.userReadSecondConfirmMessage = true;
                                            }
                                            if(window.userReadSecondConfirmMessage && window.userReadFirstConfirmMessage)
                                            {
                                                window.userReadSecondConfirmMessage = false;
                                                window.userReadFirstConfirmMessage = false;
                                                if (agreeForAll != null)
                                                {
                                                    agreeForAll.disabled = false;
                                                }
                                            }
                                        </c:when>
                                        <c:otherwise>
                                            <!--одна ссылка-->
                                            if (agreeForAll != null)
                                            {
                                                agreeForAll.disabled = false;
                                            }
                                        </c:otherwise>
                                    </c:choose>
                                }

                                function checkTargetName()
                                {
                                    var name = getElement('field(targetName)').value;
                                    var ERROR_MESSAGE = "Введите название цели."
                                    if (name == null || name =='' || name.length > 30)
                                       {
                                        addMessage(ERROR_MESSAGE);
                                        window.scrollTo(0,0);
                                    }
                                    else
                                        removeMessage(ERROR_MESSAGE);
                                    return (name != null && name != '' && name.length < 30);
                                }

                                function checkTargetAmount()
                                {
                                    var amount = getElement('field(targetAmount)').value;
                                    amount = amount.replace(/\s/g, '');
                                    var ERROR_MESSAGE = "Введите значение в поле <bean:message key="target.amount.title" bundle="financesBundle"/>.";
                                    var BIGSUM_MESSAGE = "Значение в поле <bean:message key="target.amount.title" bundle="financesBundle"/>"
                                                         + " должно быть в диапазоне 0,00 - 9 999 999 999 999 999,99 руб.";
                                    if( amount > 9999999999999999.99)
                                    {
                                        addMessage(BIGSUM_MESSAGE);
                                        window.scrollTo(0,0);
                                    }
                                    else
                                        removeMessage(BIGSUM_MESSAGE);
                                    if (amount == null || amount =='' )
                                    {
                                        addMessage(ERROR_MESSAGE);
                                        window.scrollTo(0,0);
                                    }
                                    else
                                        removeMessage(ERROR_MESSAGE);
                                    return (amount != null && amount != '' && amount <= 9999999999999999.99);
                                }

                                function checkTargetPlanedDate()
                                {
                                    var date = getElement('field(targetPlanedDate)').value;
                                    var ERROR_MESSAGE = "Введите корректную дату в поле <bean:message key="target.planedDate.title" bundle="financesBundle"/>."
                                    if (date == null || date =='')
                                       {
                                        addMessage(ERROR_MESSAGE);
                                        window.scrollTo(0,0);
                                    }
                                    else
                                        removeMessage(ERROR_MESSAGE);
                                    return (date != null && date !='');
                                }

                                function checkClientAgreesCondition()
                                {
                                    var checked = getElement('agreeForAll').checked;
                                    <c:choose>
                                        <c:when test="${needShowTarifPlan}">
                                            var AGREEMENT_MESSAGE = "Для того чтобы подтвердить заявку, ознакомьтесь с " +
                                                                "условиями вклада и с текстом дополнительного соглашения, щелкнув по ссылкам «просмотр условий Договора о вкладе» и «Просмотр дополнительного соглашения». Если Вы " +
                                                                "с условиями согласны, установите флажок в поле «с условиями Договора о вкладе согласен» и нажмите на кнопку «Создать цель».";
                                        </c:when>
                                        <c:otherwise>
                                            var AGREEMENT_MESSAGE = "Для того чтобы подтвердить заявку, ознакомьтесь с " +
                                                                    "условиями вклада, щелкнув по ссылке «просмотр условий Договора о вкладе». Если Вы " +
                                                                    "с условиями согласны, установите флажок в поле «с условиями Договора о вкладе согласен» и нажмите на кнопку «Создать цель».";
                                        </c:otherwise>
                                    </c:choose>

                                    if (!checked)
                                    {
                                        addMessage(AGREEMENT_MESSAGE);
                                        window.scrollTo(0,0);
                                    }
                                    else
                                        removeMessage(AGREEMENT_MESSAGE);
                                    return checked;
                                }
                                function checkFields()
                                {
                                    payInput.fieldClearError('field(targetPlanedDate)');
                                    removeAllErrors();
                                    return checkTargetName()&&
                                    checkTargetAmount() &&
                                    checkTargetPlanedDate()&&
                                    checkClientAgreesCondition();
                                }
                            </script>

                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="edit.operation.button.cancel"/>
                                    <tiles:put name="commandHelpKey" value="edit.operation.button.cancel.help"/>
                                    <tiles:put name="bundle" value="financesBundle"/>
                                    <tiles:put name="viewType" value="simpleLink"/>
                                    <tiles:put name="action" value="/private/finances/targets/targetsList.do"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.save"/>
                                    <tiles:put name="commandTextKey" value="button.create.target"/>
                                    <tiles:put name="commandHelpKey" value="button.create.target.help"/>
                                    <tiles:put name="bundle" value="financesBundle"/>
                                    <tiles:put name="validationFunction">checkFields();</tiles:put>
                                </tiles:insert>
                            </div>

                            <div>
                                <%-- назад к выбору цели --%>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.goto.select.target"/>
                                    <tiles:put name="commandHelpKey" value="button.goto.select.target"/>
                                    <tiles:put name="bundle" value="financesBundle"/>
                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                    <tiles:put name="action" value="/private/finances/targets/selectTarget.do"/>
                                </tiles:insert>
                            </div>

                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>