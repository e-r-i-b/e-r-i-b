<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/editPersonalData" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>    

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="page.title"/>
        </tiles:put>
        <tiles:put name="description">
            <bean:message bundle="pfpBundle" key="edit.description"/>
        </tiles:put>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message bundle="pfpBundle" key="index.breadcrumbsLink"/></tiles:put>
                <c:choose>
                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                        <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="action" value="/private/graphics/finance"/>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">Финансовое планирование</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data">
            <c:set var="viewModeDisabler" value="false"/>
            <c:if test="${form.fields['isViewMode']}">
                <c:set var="viewModeDisabler" value="true"/>
            </c:if>
            <html:hidden property="field(isViewMode)"/>
            <div class="pfpBlocks" onkeydown="onEnterKey(event);">
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.targets" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.riskProfile" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.financePlan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.plan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>

                <div id="personInfo" class="pfpBlock">
                    <fieldset class="fullWidthPFP">
                        <legend><bean:message bundle="pfpBundle" key="personInfo"/></legend>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Фамилия Имя Отчество</tiles:put>
                            <tiles:put name="data">
                                <input type="hidden" name="field(personFIO)" value="<bean:write name="form" property="field(personFIO)"/>">
                                <span class="bold"><bean:write name="form" property="field(personFIO)"/></span>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Возраст</tiles:put>
                            <tiles:put name="data">
                                <input type="hidden" name="field(personAge)" value="<bean:write name="form" property="field(personAge)"/>">
                                <span class="bold"><bean:write name="form" property="field(personAge)"/></span>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Семейное положение</tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="field(maritalStatus)" styleClass="select">
                                    <html:option value="MARRIED">Женат/Замужем</html:option>
                                    <html:option value="NOT_MARRIED">Холост/Не замужем</html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Дети</tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="field(childCount)" styleClass="select">
                                    <html:option value="NONE">Нет</html:option>
                                    <html:option value="ONE">1</html:option>
                                    <html:option value="TWO">2</html:option>
                                    <html:option value="THREE">3</html:option>
                                    <html:option value="FOUR">4</html:option>
                                    <html:option value="FIVE">5</html:option>
                                    <html:option value="moreThanFIVE">Более 5</html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>
                    </fieldset>
                </div>

                <div id="myTargets" class="pfpBlock">
                    <c:set var="personTargetList" value="${form.personTargetList}"/>
                    <c:set var="maxTargetCount" value="${form.maxTargetCount}"/>
                    <c:set var="showThermometer" value="false" scope="request"/>
                    <%@ include file="/WEB-INF/jsp/common/pfp/personTarget/clientTargetList.jsp"  %>
                </div>

                <div id="myResources" class="pfpBlock">
                    <fieldset class="fullWidthPFP">
                        <legend><bean:message bundle="pfpBundle" key="myResources"/></legend>
                        <fieldset>
                            <h1><bean:message bundle="pfpBundle" key="myResources.shortTermAssets"/></h1>
                            <tiles:insert definition="formRow" flush="false">
                               <tiles:put name="title"><bean:message bundle="pfpBundle" key="myResources.shortTermAssets.sbrf"/></tiles:put>
                               <tiles:put name="data">
                                   <html:text property="field(shortTermAssetsSBRF)" size="10" maxlength="13" disabled="${viewModeDisabler}" styleClass="moneyField" onkeyup="updateFields()"/><span class="bold">&nbsp;
                                   <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                               </tiles:put>
                               <tiles:put name="clazz" value="pfpFormRow"/>
                           </tiles:insert>
                            <tiles:insert definition="formRow" flush="false">
                               <tiles:put name="title"><bean:message bundle="pfpBundle" key="myResources.shortTermAssets.otherBanks"/></tiles:put>
                               <tiles:put name="data">
                                   <html:text property="field(shortTermAssetsOtherBanks)" size="10" maxlength="13" disabled="${viewModeDisabler}" styleClass="moneyField" onkeyup="updateFields()"/><span class="bold">&nbsp;
                                   <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                               </tiles:put>
                               <tiles:put name="clazz" value="pfpFormRow"/>
                           </tiles:insert>
                            <tiles:insert definition="formRow" flush="false">
                               <tiles:put name="title"><bean:message bundle="pfpBundle" key="myResources.shortTermAssets.cash"/></tiles:put>
                               <tiles:put name="data">
                                   <html:text property="field(shortTermAssetsCash)" size="10" maxlength="13" disabled="${viewModeDisabler}" styleClass="moneyField" onkeyup="updateFields()"/><span class="bold">&nbsp;
                                   <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                               </tiles:put>
                               <tiles:put name="clazz" value="pfpFormRow"/>
                           </tiles:insert>
                            <div class="pfpFormRow">
                                <div class="paymentLabel">
                                    <span class="paymentTextLabel"><bean:message bundle="pfpBundle" key="myResources.total"/></span>
                                </div>
                                <div class="paymentValue">
                                    <div class="paymentInputDiv">
                                        <span class="bold moneyField" ><c:out value="${form.fields.shortTermAssets}"/>
                                        <span class="bold"><span class="shortTermAssetsTotal"></span>
                                        <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span></span>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <h1><bean:message bundle="pfpBundle" key="myResources.mediumTermAssets"/></h1>
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title"><bean:message bundle="pfpBundle" key="myResources.mediumTermAssets.funds"/></tiles:put>
                                <tiles:put name="data">
                                    <html:text property="field(mediumTermAssetsFunds)" size="10" maxlength="13" disabled="${viewModeDisabler}" styleClass="moneyField" onkeyup="updateFields()"/><span class="bold">&nbsp;
                                    <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                                </tiles:put>
                                <tiles:put name="clazz" value="pfpFormRow"/>
                            </tiles:insert>
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title"><bean:message bundle="pfpBundle" key="myResources.mediumTermAssets.IMA"/></tiles:put>
                                <tiles:put name="data">
                                    <html:text property="field(mediumTermAssetsIMA)" size="10" maxlength="13" disabled="${viewModeDisabler}" styleClass="moneyField" onkeyup="updateFields()"/><span class="bold">&nbsp;
                                    <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                                </tiles:put>
                                <tiles:put name="clazz" value="pfpFormRow"/>
                            </tiles:insert>
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title"><bean:message bundle="pfpBundle" key="myResources.mediumTermAssets.others"/></tiles:put>
                                <tiles:put name="data">
                                    <html:text property="field(mediumTermAssetsOther)" size="10" maxlength="13" disabled="${viewModeDisabler}" styleClass="moneyField" onkeyup="updateFields()"/><span class="bold">&nbsp;
                                    <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                                </tiles:put>
                                <tiles:put name="clazz" value="pfpFormRow"/>
                            </tiles:insert>
                            <div class="pfpFormRow">
                                <div class="paymentLabel">
                                    <span class="paymentTextLabel"><bean:message bundle="pfpBundle" key="myResources.total"/></span>
                                </div>
                                <div class="paymentValue">
                                    <div class="paymentInputDiv">
                                        <span class="bold moneyField" ><c:out value="${form.fields.mediumTermAssets}"/>
                                        <span class="mediumTermAssetsTotal"></span>
                                        <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title"><bean:message bundle="pfpBundle" key="myResources.realty"/></tiles:put>
                            <tiles:put name="data">
                                <html:select property="field(apartmentCount)" styleClass="select pfpSelectWidth75" disabled="${viewModeDisabler}">
                                    <html:option value="NONE">0</html:option>
                                    <html:option value="ONE">1</html:option>
                                    <html:option value="TWO">2</html:option>
                                    <html:option value="THREE">3</html:option>
                                    <html:option value="FOUR">4</html:option>
                                    <html:option value="FIVE">5</html:option>
                                    <html:option value="moreThanFIVE">Более 5</html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>
                    </fieldset>
                </div>

                <div id="myLoans" class="pfpBlock">
                    <fieldset class="fullWidthPFP">
                        <legend><bean:message bundle="pfpBundle" key="myLoans"/></legend>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Ипотека</tiles:put>
                            <tiles:put name="data">
                                <html:select property="field(mortgageCount)" styleClass="select pfpSelectWidth75">
                                    <html:option value="NONE">Нет</html:option>
                                    <html:option value="ONE">1</html:option>
                                    <html:option value="TWO">2</html:option>
                                    <html:option value="THREE">3</html:option>
                                    <html:option value="moreThanTHREE">более 3-х</html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Потребительский кредит</tiles:put>
                            <tiles:put name="data">
                                <html:select property="field(consumerLoanCount)" styleClass="select pfpSelectWidth75">
                                    <html:option value="NONE">Нет</html:option>
                                    <html:option value="ONE">1</html:option>
                                    <html:option value="TWO">2</html:option>
                                    <html:option value="THREE">3</html:option>
                                    <html:option value="moreThanTHREE">более 3-х</html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Автокредит</tiles:put>
                            <tiles:put name="data">
                                <html:select property="field(autoLoanCount)" styleClass="select pfpSelectWidth75">
                                    <html:option value="NONE">Нет</html:option>
                                    <html:option value="ONE">1</html:option>
                                    <html:option value="TWO">2</html:option>
                                    <html:option value="THREE">3</html:option>
                                    <html:option value="moreThanTHREE">более 3-х</html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Кредитная карта</tiles:put>                            
                            <tiles:put name="data">
                                <html:select property="field(creditCardCount)" styleClass="select pfpSelectWidth75">
                                    <html:option value="NONE">Нет</html:option>
                                    <html:option value="ONE">1</html:option>
                                    <html:option value="TWO">2</html:option>
                                    <html:option value="THREE">3</html:option>
                                    <html:option value="moreThanTHREE">более 3-х</html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>
                    </fieldset>
                </div>

                <div id="myBudget" class="pfpBlock">
                    <fieldset class="fullWidthPFP">
                        <legend><bean:message bundle="pfpBundle" key="myBudget"/></legend>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Ваши ежемесячные доходы</tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(incomeMoney)" size="10" maxlength="13" onkeyup="calcFreeMoney()" disabled="${viewModeDisabler}" styleClass="moneyField"/><span class="bold">&nbsp;
                                <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                            </tiles:put>
                            <tiles:put name="description">
                                Ваши доходы за месяц с учетом всех премий и бонусов.
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Ваши ежемесячные расходы</tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(outcomeMoney)" size="10" maxlength="13" onkeyup="calcFreeMoney()" disabled="${viewModeDisabler}" styleClass="moneyField"/><span class="bold">&nbsp;
                                <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                            </tiles:put>
                            <tiles:put name="description">
                                Введите сумму Ваших расходов за месяц с учетом платежей по всем кредитам
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Ежемесячно свободных средств</tiles:put>
                            <tiles:put name="isNecessary" value="false"/>
                            <tiles:put name="data">
                                <input id="freeMoney" type="text" name="freeMoney" size="10" disabled="true" class="moneyField"/><span class="bold">&nbsp;
                                <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span></span>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>
                    </fieldset>
                </div>

                <div class="pfpButtonsBlock">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.next"/>
                        <tiles:put name="commandHelpKey" value="button.next.help"/>
                        <tiles:put name="validationFunction" value=""/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="pfpBundle"/>
                        <tiles:put name="validationFunction" value="validateForm();"/>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.back2"/>
                    <tiles:put name="commandHelpKey" value="button.back2.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action" value="/edit.do"/>
                </tiles:insert>

                <script type="text/javascript">

                    function calcFreeMoney()
                    {
                        var incomeMoney = parseFloat(getStringWithoutSpace($('input[name="field(incomeMoney)"]').val().replace(",","."))) ;
                        var outcomeMoney = parseFloat(getStringWithoutSpace($('input[name="field(outcomeMoney)"]').val().replace(",",".")));
                        var freeMoney = incomeMoney - outcomeMoney;
                        if(isNaN(freeMoney) || freeMoney < 0)
                            freeMoney = 0;
                        $('#freeMoney').setMoneyValue(freeMoney.toFixed(2));
                    }

                    function validateForm()
                    {
                        var incomeMoney = parseFloat(getStringWithoutSpace($('input[name="field(incomeMoney)"]').val().replace(",","."))) ;
                        var outcomeMoney = parseFloat(getStringWithoutSpace($('input[name="field(outcomeMoney)"]').val().replace(",",".")));
                        var freeMoney = incomeMoney - outcomeMoney;
                        if(freeMoney < 0)
                        {
                            var parentDiv = $('#freeMoney').parents("div.form-row");
                            var errorDiv  = parentDiv.find("div.errorDiv");
                            var elem = getElement('freeMoney');
                            while(elem != null && elem.className.indexOf("form-row") ==-1)
                                elem=elem.parentNode;                            
                            parentDiv.attr("class", "form-row error");                           
                            errorDiv.html("Ваши ежемесячные расходы превышают доходы.");
                            errorDiv.show();
                            elem.error = true;
                            return false;
                        }
                        return true;                        
                    }
                    function updateFields()
                    {
                        var sbrf = parseFloat(getStringWithoutSpace($('input[name="field(shortTermAssetsSBRF)"]').val().replace(",",".")));
                        var otherBanks = parseFloat(getStringWithoutSpace($('input[name="field(shortTermAssetsOtherBanks)"]').val().replace(",",".")));
                        var cash = parseFloat(getStringWithoutSpace($('input[name="field(shortTermAssetsCash)"]').val().replace(",",".")));
                        if (isNaN(sbrf))
                            sbrf = 0;
                        if (isNaN(otherBanks))
                            otherBanks = 0;
                        if (isNaN(cash))
                            cash = 0;
                        var sum = sbrf + otherBanks + cash;
                        $('.shortTermAssetsTotal').text(FloatToString(sum, 2,' '));

                        var funds = parseFloat(getStringWithoutSpace($('input[name="field(mediumTermAssetsFunds)"]').val().replace(",",".")));
                        var ima = parseFloat(getStringWithoutSpace($('input[name="field(mediumTermAssetsIMA)"]').val().replace(",",".")));
                        var other = parseFloat(getStringWithoutSpace($('input[name="field(mediumTermAssetsOther)"]').val().replace(",",".")));
                        if (isNaN(funds))
                            funds = 0;
                        if (isNaN(ima))
                            ima = 0;
                        if (isNaN(other))
                            other = 0;
                        var sumMedium = funds + ima + other;
                        $('.mediumTermAssetsTotal').text(FloatToString(sumMedium, 2,' '));
                    }
                    $(document).ready(function() {
                        calcFreeMoney();
                        $('#addPFPTargetWin').bgIframe();
                        $('#confirmRemoveTargetWin').bgIframe();
                        updateFields();
                    });
                </script>

            </div>
        </tiles:put>
    </tiles:insert>

</html:form>