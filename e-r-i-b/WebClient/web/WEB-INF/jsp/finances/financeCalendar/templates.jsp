<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/private/finances/financeCalendar/listTemplates" onsubmit="return setEmptyAction(event)">
    <c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="templates" value="${form.templates}"/>
    <c:set var="extractId" value="${form.extractId}"/>
    <c:set var="templateUrl" value="${phiz:calculateActionURL(pageContext,'/private/templates/default-action.do?')}"/>
    <c:set var="createTemplateUrl" value="${phiz:calculateActionURL(pageContext, '/private/template/select-category.do')}"/>

    <tiles:insert definition="empty" flush="false">
        <tiles:put name="data" type="string">
            <div id="templateList">
                <div id="reference">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="Создание напоминания о платеже или переводе"/>
                        <tiles:put name="data">
                            <script type="text/javascript">
                                function closeTemplates()
                                {
                                    window.opener.closeListTemplates('${extractId}');
                                    window.close();
                                    return true;
                                }

                                function onChooseClick()
                                {
                                    var ids = document.getElementsByName("selectedIds");
                                    for (var i=0; i<ids.length; i++)
                                    {
                                        var id = ids.item(i);
                                        if (id.checked)
                                        {
                                            var templateUrl = document.getElementById('templateUrl' + id.value);
                                            window.opener.createTemplate(templateUrl.value + '&fromFinanceCalendar=true&extractId=${extractId}');
                                            window.close();
                                            return true;
                                        }
                                    }
                                    alert("Не задан ни один шаблон");
                                    return false;
                                }

                                function createTemplate()
                                {
                                    window.opener.createTemplate('${createTemplateUrl}?fromFinanceCalendar=true&extractId=${extractId}');
                                    window.close();
                                    return true;
                                }
                            </script>
                            <table class="templatesHeader">
                                <tr>
                                    <td>
                                        <span class="templatesHeaderImage"></span>
                                    </td>
                                    <td>
                                        Выберите из имеющихся шаблонов платеж или перевод, о котором Вы хотите себе напоминить или&nbsp;
                                        <span class="createTemplateLink" onclick="createTemplate()">создайте новый шаблон</span>
                                    </td>
                                </tr>
                            </table>
                            <div class="payment-templates">
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid" >
                                    <sl:collection id="template" model="simple-pagination" name="ListTemplatesForRemindersForm" property="templates">
                                        <sl:collectionItem title="&nbsp;" styleClass="align-center">
                                            <input type="hidden" id="templateUrl${template.id}" value="${templateUrl}&id=${template.id}&objectFormName=${template.formType.name}&stateCode=${template.state}">
                                            <html:radio name="form" property="selectedIds"  value="${template.id}" ondblclick="onChooseClick();"/>
                                        </sl:collectionItem>

                                        <sl:collectionItem title="Название шаблона" styleTitleClass="align-left" styleClass="align-left">
                                            <c:out value="${template.templateInfo.name}"/>
                                        </sl:collectionItem>

                                        <sl:collectionItem title="Сумма" styleTitleClass="align-center" styleClass="align-center">
                                            <c:if test="${template.inputSumType == 'DESTINATION'}">
                                                ${phiz:formatAmount(template.destinationAmount)}
                                            </c:if>
                                            <c:if test="${template.inputSumType == 'CHARGEOFF'}">
                                                 ${phiz:formatAmount(template.chargeOffAmount)}
                                            </c:if>
                                        </sl:collectionItem>
                                    </sl:collection>
                                </tiles:put>
                                <tiles:put name="isEmpty" value="${empty templates}"/>
                                <tiles:put name="emptyMessage">
                                    У Вас нет ни одного активного шаблона для создания напоминания.
                                </tiles:put>
                            </tiles:insert>
                            </div>
                            <div class="clear"></div>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                   <tiles:put name="commandTextKey" value="button.cancel"/>
                                   <tiles:put name="commandHelpKey" value="button.cancel"/>
                                   <tiles:put name="bundle"         value="dictionaryBundle"/>
                                   <tiles:put name="viewType"       value="buttonGrey"/>
                                   <tiles:put name="onclick"        value="closeTemplates();"/>
                                </tiles:insert>
                                <c:if test="${not empty templates}">
                                    <tiles:insert definition="clientButton" flush="false">
                                       <tiles:put name="commandTextKey" value="button.choose"/>
                                       <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                       <tiles:put name="bundle"         value="dictionaryBundle"/>
                                       <tiles:put name="onclick"        value="onChooseClick();"/>
                                    </tiles:insert>
                                </c:if>
                            </div>
                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>