<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<html:form action="/private/deposits/products/list" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="paymentMain">
        <tiles:put name="mainmenu" value="Payments"/>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                <tiles:put name="action" value="/private/payments.do"/>                                
            </tiles:insert>
            
            <%--Ссылка на категорию--%>
            <c:if test="${not empty form.category}">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name"><bean:message key="category.operations.${form.category}" bundle="paymentServicesBundle"/></tiles:put>
                    <tiles:put name="action" value="/private/payments/category.do?categoryId=${form.category}"/>
                    <tiles:put name="last" value="false"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Условия размещения средств по вкладам"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Условия размещения средств по вкладам в Сбербанке России"/>
                <tiles:put name="data" type="string">
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/iconPmntList_AccountOpeningClaim.png"/>
                        <tiles:put name="description">
                            <c:choose>
                                <c:when test="${(not empty form.listHtml or not empty form.depositProductEntities) && form.form == 'AccountOpeningClaim'}">
                                    <h3>Для того чтобы открыть новый вклад, ознакомьтесь с условиями размещения средств.
                                        После этого выберите подходящий Вам вклад, отметьте его в списке и нажмите на
                                        кнопку «Продолжить». Если Вы хотите получить дополнительную информацию по вкладу,
                                        щелкните по ссылке "Подробнее".</h3>
                                    <c:if test="${phiz:impliesService('ClientPromoCode')}">
                                        <div class="promoClaim">
                                            <tiles:insert definition="promoDiv" service="AccountOpeningClaim" flush="false">
                                                <tiles:put name="serviceId" value="AccountOpeningClaim"/>
                                                <tiles:put name="maxLength" value="${form.promoDivMaxLength}"/>
                                                <tiles:put name="listPayTitle" value="У меня есть промо-код"/>
                                            </tiles:insert>
                                        </div>
                                        <c:if test="${param.withPromo == 'true'}">
                                            <c:set var="welcomeWithPromoMessage" value="${phiz:getPromoMessage('MSG01')}"/>
                                            <script type="text/javascript">
                                                doOnLoad(function ()
                                                {
                                                    var warningsDiv = $("#warnings");
                                                    var newDiv = document.createElement('div');
                                                    var newImg = document.createElement('img');
                                                    warningsDiv.addClass('orangeNotice');
                                                    newImg.setAttribute('src', "${globalUrl}/commonSkin/images/promoGift.png");
                                                    var newSpan = document.createElement('span');
                                                    newSpan.className = 'noticeTitle';
                                                    newSpan.innerHTML = "${phiz:processBBCodeAndEscapeHtml(welcomeWithPromoMessage.title, false)}";
                                                    newDiv.appendChild(newSpan);
                                                    $(".messageContainer").before( newImg );
                                                    $(".messageContainer").before( newDiv );
                                                    warningsDiv.find(".messageContainer").html("${phiz:processBBCodeAndEscapeHtml(welcomeWithPromoMessage.text, false)}");
                                                    warningsDiv.show();
                                                });
                                            </script>
                                        </c:if>
                                    </c:if>
                                </c:when>
                                <c:when test="${not empty form.listHtml && form.form == 'AccountOpeningClaimWithClose'}">
                                    <h3><bean:message bundle="claimsBundle" key="text.accountOpeningClaimWithClose"/></h3>
                                    <c:if test="${phiz:impliesService('ClientPromoCode')}">
                                        <div class="promoClaim">
                                            <tiles:insert definition="promoDiv" service="AccountOpeningClaim" flush="false">
                                                <tiles:put name="serviceId" value="AccountOpeningClaim"/>
                                                <tiles:put name="maxLength" value="${form.promoDivMaxLength}"/>
                                                <tiles:put name="listPayTitle" value="У меня есть промо-код"/>
                                            </tiles:insert>
                                        </div>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <h3><bean:message bundle="claimsBundle" key="text.accountOpeningNotAvailable"/></h3>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>
                        <c:if test="${(not empty form.listHtml) or (not empty form.depositProductEntities)}">
                            <div id="paymentStripe">
                                <tiles:insert definition="stripe" flush="false">
                                    <tiles:put name="name" value="выбор вклада"/>
                                    <tiles:put name="current" value="true"/>
                                </tiles:insert>
                                <tiles:insert definition="stripe" flush="false">
                                    <tiles:put name="name" value="заполнение заявки"/>
                                    <tiles:put name="future" value="true"/>
                                </tiles:insert>
                                <tiles:insert definition="stripe" flush="false">
                                    <tiles:put name="name" value="подтверждение"/>
                                    <tiles:put name="future" value="true"/>
                                </tiles:insert>
                                <tiles:insert definition="stripe" flush="false">
                                    <tiles:put name="name" value="статус операции"/>
                                </tiles:insert>
                                <div class="clear"></div>
                            </div>

                            <div id="list">
                                <c:choose>
                                    <c:when test="${phiz:isUseCasNsiDictionaries()}">
                                        <tiles:insert page="/WEB-INF/jsp-sbrf/private/deposits/depositProductList.jsp" flush="false"/>
                                    </c:when>
                                    <c:otherwise>
                                        ${form.listHtml}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="clear"></div>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.back"/>
                                    <tiles:put name="commandHelpKey" value="button.back.help"/>
                                    <tiles:put name="bundle" value="claimsBundle"/>
                                    <tiles:put name="action" value="/private/payments"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.next"/>
                                    <tiles:put name="commandHelpKey" value="button.next"/>
                                    <tiles:put name="bundle" value="claimsBundle"/>
                                    <tiles:put name="onclick" value="next();"/>
                                </tiles:insert>
                            </div>
                            <div class="clear"></div>

                            <c:set var="winID" value="depositDetail"/>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="${winID}" />
                            </tiles:insert>

                            <c:set var="infoActionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/deposits/details.do?id=')}"/>
                            <script type="text/javascript">
                                 function openDepositDetalInfo(depositId, groupCode, segment)
                                    {
                                        var DEPOSIT_DETAIL_ID = '${winID}';
                                        var segment = (segment === '' || segment == null || segment == undefined) ? '' : segment;

                                        win.aditionalData(DEPOSIT_DETAIL_ID, {onOpen: function (id)
                                        {

                                            var element = document.getElementById(id);
                                            var elementId = depositId + '_' + groupCode + '_' + segment;
                                            if (element.dataWasLoaded != undefined && element.dataWasLoaded[elementId] != null ) {
                                                element.innerHTML = element.dataWasLoaded[elementId];
                                                return true;
                                            }

                                            win.loadAjaxData(DEPOSIT_DETAIL_ID, '${infoActionUrl}'+ depositId + "&group=" + groupCode + "&segment=" + segment,
                                                    function ()
                                                    {
                                                        if (element.dataWasLoaded == undefined) element.dataWasLoaded = [];
                                                        element.dataWasLoaded[elementId] = element.innerHTML;
                                                        win.open(DEPOSIT_DETAIL_ID, true);
                                                    }
                                                    );

                                            return false;
                                        }
                                        });

                                        win.open('${winID}');
                                    }

                                 <c:set var="newInfoActionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/depositProduct/details.do?id=')}"/>
                                 function openDepositDetailInfo(depositId, groupCode, segment)
                                 {
                                     var DEPOSIT_DETAIL_ID = '${winID}';
                                     var segment = (segment === '' || segment == null || segment == undefined) ? '' : segment;

                                     win.aditionalData(DEPOSIT_DETAIL_ID, {onOpen: function (id)
                                     {

                                         var element = document.getElementById(id);
                                         var elementId = depositId + '_' + groupCode + '_' + segment;
                                         if (element.dataWasLoaded != undefined && element.dataWasLoaded[elementId] != null ) {
                                             element.innerHTML = element.dataWasLoaded[elementId];
                                             return true;
                                         }

                                         win.loadAjaxData(DEPOSIT_DETAIL_ID, '${newInfoActionUrl}'+ depositId + "&group=" + groupCode + "&segment=" + segment,
                                                    function ()
                                                    {
                                                        if (element.dataWasLoaded == undefined) element.dataWasLoaded = [];
                                                        element.dataWasLoaded[elementId] = element.innerHTML;
                                                        win.open(DEPOSIT_DETAIL_ID, true);
                                                    }
                                                    );

                                            return false;
                                        }
                                        });

                                        win.open('${winID}');
                                    }

                                 <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext, '/private/payments/payment')}"/>
                                 function next()
                                 {
                                     if (checkOneSelection('depositProductId'))
                                     {
                                        var depositDescription = getRadioValue(document.getElementsByName('depositProductId'));
                                        var descriptionValues = depositDescription.split(":");
                                        var depositType = descriptionValues[0];
                                        var depositGroup = descriptionValues[1];
                                        var segmentCode = descriptionValues[2];
                                        var segment = (segmentCode === '' || segmentCode == null || segmentCode == undefined) ? '' : segmentCode;
                                        var depositId   = document.getElementById(depositType);
                                        var depositIdCode =  (depositId != null) ? "&depositId=" + depositId.value : "";
                                        window.location = '${actionUrl}' + '?form=${form.form}&depositType=' + depositType + depositIdCode + "&depositGroup=" + depositGroup + "&segment=" + segment + "&fromResource=" + "${param['fromResource']}";
                                     }
                                 }

                                 function checkOneSelection(selectionName)
                                 {
                                     checkIfOneItem(selectionName);
                                     var qnt = getSelectedQnt(selectionName);
                                     if (qnt > 1)
                                     {
                                         addError("Выберите только один вид вклада");
                                         return false;
                                     }
                                     else if (qnt == 0)
                                     {
                                         addError("Выберите один вид вклада");
                                         return false;
                                     }
                                     return true;
                                 }

                                 doOnLoad(function ()
                                 {
                                     ensureElement('list').getElementsByTagName('input')[0].checked = true;
                                 });
                            </script>
                        </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
