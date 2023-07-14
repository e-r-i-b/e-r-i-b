<%--
  Created by IntelliJ IDEA.
  User: mihaylov
  Date: 29.05.2010
  Time: 14:17:41
  To change this template use File | Settings | File Templates.
--%>
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

<c:set var="form" value="${ListTemplatesForm}"/>

<html:form action="/private/templates" onsubmit="return setEmptyAction(event)">
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <div id="templateList">
                <div id="reference">
                    <c:set var="templates" value="${form.templates}"/>
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="Шаблоны платежей"/>
                        <tiles:put name="data">
                            <script type="text/javascript">

                                $(document).ready(function(){
                                    initReferenceSize();
                                });

                                function makePayment()
                                {
                                    var ids = document.getElementsByName("selectedIds");
                                    for (var i=0; i<ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            window.opener.makePaymentByTemplate(ids.item(i).value);
                                            window.close();
                                            return true;
                                        }
                                    }
                                    alert("Не задан ни один шаблон");
                                    return false;
                                }
                            </script>

                            <tiles:insert definition="formHeader" flush="false">
                                <tiles:put name="image" value="${imagePath}/icon_template.png"/>
                                <tiles:put name="alt" value="Список шаблонов"/>
                                <tiles:put name="description">
                                    Отметьте интересующий Вас шаблон в списке и нажмите на кнопку "Выбрать".
                                </tiles:put>
                            </tiles:insert>
                            <div class="payment-templates">
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid" >
                                    <sl:collection id="template" model="simple-pagination" name="ListTemplatesForm" property="templates">
                                            <sl:collectionItem title="&nbsp;" styleClass="align-center">
                                                <html:radio name="form" property="selectedIds" value="${template.id}" ondblclick="javascript:makePayment();"/>
                                            </sl:collectionItem>

                                            <sl:collectionItem title="Название шаблона" styleTitleClass="align-center" styleClass="align-center">
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
                                           <h3>
                                               Для данного платежа шаблоны не найдены.<br/>
                                               Вы можете создать шаблон платежа, и потом оплачивать его одним
                                               кликом.<br/>
                                           </h3>
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
                                   <tiles:put name="onclick"        value="window.close();"/>
                                </tiles:insert>
                                <c:if test="${not empty templates}">
                                    <tiles:insert definition="clientButton" flush="false">
                                       <tiles:put name="commandTextKey" value="button.choose"/>
                                       <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                       <tiles:put name="bundle"         value="dictionaryBundle"/>
                                       <tiles:put name="onclick"        value="makePayment();"/>
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