<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/card/recommendation/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fields" value="${form.fields}"/>
    <c:set var="maxStepCount" value="${form.maxStepCount}"/>
    <tiles:insert definition="editPFPCardRecommendation">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.description"/>
                </tiles:put>
                <tiles:put name="data">

                    <fieldset>
                        <legend><bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.steps"/></legend>

                        <c:forEach var="step" begin="0" end="${maxStepCount-1}">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.steps.name" arg0="${step + 1}"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <c:set var="currentStepNameFieldName" value="stepName${step}"/>
                                    <input type="text" name="fields(${currentStepNameFieldName})" value="${fields[currentStepNameFieldName]}" maxlength="58" size="58"/>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.steps.description"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <c:set var="currentStepDescriptionFieldName" value="stepDescription${step}"/>
                                    <textarea id="currentStepDescriptionFieldName${currentStepDescriptionFieldName}" name="fields(${currentStepDescriptionFieldName})" cols="75" rows="2"><c:out value="${fields[currentStepDescriptionFieldName]}"/></textarea>
                                    <div class="clear"></div>
                                    <tiles:insert definition="bbCodeButtons" flush="false">
                                        <tiles:put name="textAreaId" value="currentStepDescriptionFieldName${currentStepDescriptionFieldName}"/>
                                        <tiles:put name="showFio" value="true"/>
                                        <tiles:put name="showBold" value="true"/>
                                        <tiles:put name="showItalics" value="true"/>
                                        <tiles:put name="showUnderline" value="true"/>
                                        <tiles:put name="showColor" value="true"/>
                                        <tiles:put name="showHyperlink" value="true"/>
                                        <tiles:put name="showTextAlign" value="true"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </c:forEach>
                    </fieldset>

                    <fieldset>
                        <legend><bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.account.income"/>:
                            </tiles:put>
                            <tiles:put name="data">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.account.income.from"/>
                                <html:text property="fields(accountIncomeFrom)" size="3"/>
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.account.income.to"/>
                                <html:text property="fields(accountIncomeTo)" size="3"/>
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.account.income.unit"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.account.income.default"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="fields(accountIncomeDefault)" size="3"/>
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.account.income.unit"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.account.description"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:textarea property="fields(accountDescription)" styleId="accountDescription" cols="56" rows="2"/>
                                <div class="clear"></div>
                                <tiles:insert definition="bbCodeButtons" flush="false">
                                    <tiles:put name="textAreaId" value="accountDescription"/>
                                    <tiles:put name="showBold" value="true"/>
                                    <tiles:put name="showItalics" value="true"/>
                                    <tiles:put name="showUnderline" value="true"/>
                                    <tiles:put name="showColor" value="true"/>
                                    <tiles:put name="showPercentAccount" value="true"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.thanks.income"/>:
                            </tiles:put>
                            <tiles:put name="data">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.thanks.income.from"/>
                                <html:text property="fields(thanksIncomeFrom)" size="3"/>
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.thanks.income.to"/>
                                <html:text property="fields(thanksIncomeTo)" size="3"/>
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.thanks.income.unit"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.thanks.income.default"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="fields(thanksIncomeDefault)" size="3"/>
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.thanks.income.default.unit"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.efficacy.thanks.description"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:textarea property="fields(thanksDescription)" styleId="thanksDescription" cols="56" rows="2"/>
                                <div class="clear"></div>
                                <tiles:insert definition="bbCodeButtons" flush="false">
                                    <tiles:put name="textAreaId" value="thanksDescription"/>
                                    <tiles:put name="showBold" value="true"/>
                                    <tiles:put name="showItalics" value="true"/>
                                    <tiles:put name="showUnderline" value="true"/>
                                    <tiles:put name="showColor" value="true"/>
                                    <tiles:put name="showPercentThanks" value="true"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.recommendation"/>:
                            </tiles:put>
                            <tiles:put name="data">
                                <html:textarea property="fields(recommendation)" styleId="recommendation" cols="56" rows="2"/>
                                <div class="clear"></div>
                                <tiles:insert definition="bbCodeButtons" flush="false">
                                    <tiles:put name="textAreaId" value="recommendation"/>
                                    <tiles:put name="showFio" value="true"/>
                                    <tiles:put name="showBold" value="true"/>
                                    <tiles:put name="showItalics" value="true"/>
                                    <tiles:put name="showUnderline" value="true"/>
                                    <tiles:put name="showColor" value="true"/>
                                    <tiles:put name="showHyperlink" value="true"/>
                                    <tiles:put name="showTextAlign" value="true"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.cards"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">

                            <script type="text/javascript">
                                function openCardsDictionaries()
                                {
                                    window.open("${phiz:calculateActionURL(pageContext, '/pfp/products/card/list')}?action=dictionary",
                                            'cardDictionary',
                                            "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                                }

                                function setCard(data)
                                {
                                    $(".hidableElement").show();
                                    var existingCard = $("#cardProduct" + data['id']);
                                    if (existingCard.length == 1)
                                        return '<bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.cards.table.message.exist"/>';

                                    var line =  '<tr id="cardProduct' + data['id'] + '" class="ListLine0">' +
                                            '<td class="listItem">' +
                                            '<input type="checkbox" class="checkboxMargin" name="selectedIds" value="' + data['id'] + '"/>&nbsp;' +
                                            '<input type="hidden" checked name="cardProductIds" value="' + data['id'] + '"/>' +
                                            '</td>' +
                                            '<td>' +
                                            data['name'] +
                                            '<input type="hidden" value="' + data['name'] +'" name="fields(cardProductNameFor' + data['id'] + ')"/>' +
                                            '</td>' +
                                            '</tr>';
                                    $("#cardProducts > tbody").append(line);
                                    return null;
                                }

                                function setCards(data)
                                {
                                    for(var i = 0; i < data.length; ++i)
                                    {
                                        var message = setCard(data[i]);
                                        if (message != null)
                                            return message;

                                    }
                                    return null;
                                }

                                function removeCard()
                                {
                                    checkIfOneItem("selectedIds");
                                    if (!checkSelection("selectedIds", '<bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.cards.table.message.checkSelection"/>'))
                                        return false;

                                    if (!confirm('<bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.cards.table.message.removeAgreement"/>'))
                                        return false;

                                    $("#cardProducts [name=selectedIds]:checked").each(function(){$(this).parent().parent().remove();});
                                    if ($("[name=selectedIds]").size() == 0)
                                        $(".hidableElement").hide();
                                    return true;
                                }
                            </script>
                            <c:set var="buttonDisplayStyle" value=""/>
                            <c:if test="${empty form.cardProductIds}">
                                <c:set var="buttonDisplayStyle" value=" display:none;"/>
                            </c:if>
                            <%--Кнопки--%>
                            <tiles:insert definition="clientButton" flush="false" operation="EditProductTypeParametersOperation">
                                <tiles:put name="commandTextKey"         value="form.edit.fields.cards.add"/>
                                <tiles:put name="commandHelpKey"     value="form.edit.fields.cards.add"/>
                                <tiles:put name="bundle"             value="pfpCardRecommendationBundle"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                                <tiles:put name="onclick" value="openCardsDictionaries();"/>
                            </tiles:insert>

                            <div class="hidableElement" style="${buttonDisplayStyle}">
                                <tiles:insert definition="clientButton" flush="false" operation="EditProductTypeParametersOperation">
                                    <tiles:put name="commandTextKey"         value="form.edit.fields.cards.remove"/>
                                    <tiles:put name="commandHelpKey"     value="form.edit.fields.cards.remove"/>
                                    <tiles:put name="bundle"             value="pfpCardRecommendationBundle"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                    <tiles:put name="onclick" value="removeCard();"/>
                                </tiles:insert>
                            </div>
                            <div class="clear"></div>

                            <%--Таблица--%>
                            <div class="smallDynamicGrid hidableElement" style="${buttonDisplayStyle}">
                                <tiles:insert definition="tableTemplate" flush="false">
                                    <tiles:put name="id" value="cardProducts"/>
                                    <tiles:put name="text"><bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.cards.table.title"/></tiles:put>
                                    <tiles:put name="head">
                                        <th width="20px">
                                            <input type="checkbox" onclick="switchSelection(this,'selectedIds');">
                                        </th>
                                        <th>
                                            <bean:message bundle="pfpCardRecommendationBundle" key="form.edit.fields.cards.table.name"/>
                                        </th>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <logic:iterate id="productId" name="EditUseCreditCardRecommendationForm" property="cardProductIds">
                                            <c:set var="fieldName" value="cardProductNameFor${productId}"/>
                                            <tr id="cardProduct${productId}" class="ListLine0">
                                                <td class="listItem">
                                                    <input type="checkbox" class="checkboxMargin" value="${productId}" name="selectedIds">
                                                    <input type="hidden" value="${productId}" name="cardProductIds" style="display: none;">
                                                </td>
                                                <td>
                                                    ${form.fields[fieldName]}
                                                    <html:hidden property="fields(${fieldName})" style="display: none;"/>
                                                </td>
                                            </tr>
                                        </logic:iterate>
                                        <%--Нужно чтобы не отображалось сообщения emptyMessage--%>
                                        <div></div>
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListProductTypeParametersOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpCardRecommendationBundle"/>
                        <tiles:put name="action"            value="/pfp/products/card/recommendation/edit"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditProductTypeParametersOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpCardRecommendationBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>