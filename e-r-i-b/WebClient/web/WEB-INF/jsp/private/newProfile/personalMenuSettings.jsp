<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<tiles:importAttribute/>

<html:form action="/private/favourite/list">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="newUserProfile">
<tiles:put name="data" type="string">
<script type="text/javascript">

    function resizeProductListDescription()
    {
        resizeProductDescription("sortableCards");
        resizeProductDescription("sortableAccounts");
        resizeProductDescription("sortableLoans");
        resizeProductDescription("sortableIMAccounts");
        resizeProductDescription("sortableDepoAccounts");
    }

    /**
     * Ф-я для подгонки отображения информации о продукте по ширине строки
     * @param tableId
     */
    function resizeProductDescription(tableId)
    {
        maxWidthBlock();

        var indent = 15; // отступ
        $('#mainPageSettings #' + tableId).find('div.left').each(function ()
        {
            var productNumberWith = $(this).find('span.productNumber')[0].offsetWidth;

            var productDescription = $(this).children('div')[0];
            var productNameWith = $(productDescription).find('span.linkTitle')[0].offsetWidth;

            var parentBlock = $(this).parents('.sortableProductLinks').width();
            var icon = $(this).parents('.sortableProductLinks').find('.sortIcon').width();
            var productIcon = $(this).parents('.sortableProductLinks').find('.tinyProductImg').width();
            var rightBlocks = $(this).parents('.sortableProductLinks').find('.right').width();

            // Ширина блока должна расчитываться в зависимости от контента всей плашки, 102 - все отступы внутри блока
            var productDescriptionWith = parentBlock - icon - productIcon - rightBlocks - 102;
            $(this).css('width', productDescriptionWith);

            if (($.browser.msie) && ($.browser.version == '6.0')) {
                productDescriptionWith = 345;
            }

            // Максимальная ширина для отображения названия продукта
            var productNameMaxWith = productDescriptionWith - productNumberWith - indent;

            // Если название не умещается в отведенную ширину, то добавляем осветление, устанавливаем длину для области с названием такую,
            // чтобы она вписалась четко по ширине строки
            if (productNameWith > productNameMaxWith)
            {
                productDescription.style.width = productNameMaxWith + "px";  // ширина дива с названием и блоком прозрачности
                $(this).find('.lightness')[0].style.visibility = "visible";  // включаем блок, делающий окончание текста прозрачным
            }
            // Если название короче, чем допустимое место, то укорачиваем область для названия, убираем осветление
            // (номер продукта будет рядом с названием)
            else
            {
                productDescription.style.width = productNameWith + "px";
            }
        });
    }

    function maxWidthBlock(){
        $('.openContainer .ui-sortable').each(function(index,element)
        {
            var maxWidth = 0;
            var container = $(element);
            container.find('.currency').each(function(){
                var width = $(this).css('width','');// удаляем все предыдущие значения для перерасчета
                var width = $(this).width();
                maxWidth = width > maxWidth ? width:maxWidth;
            });
            container.find('.currency').css('width', maxWidth);
        })
    }

    $(function(){
        sortableProducts("sortableDepoAccounts", "DepoAccount",         "selectedDepoAccountIds");
        sortableProducts("sortableIMAccounts",   "IMAccount",           "selectedIMAccountIds");
        sortableProducts("sortableLoans",        "Loan",                "selectedLoanIds");
        sortableProducts("sortableAccounts",     "Account",             "selectedAccountIds");
        sortableProducts("sortableCards",        "userProfileSecurity", "sortedCardIds");
        sortableProducts("sortablePFR",          "pfrLinkSelected",     "pfrLinkSelected");
        sortableProducts("sortableMenuLinks",    "securityOptions",     "sortMenuLinks",          "saveMainMenuSettings",       "saveMainMenuSettings",   "sortableMenuLinks");

    });

    function sortableProducts(tableId, selectedFieldId, inputName, buttonKey, buttonId, linkClass)
    {
        if (linkClass == undefined)
            linkClass = "sortableProductLinks";
        if (buttonId == undefined)
            buttonId = "saveSettingsView";
        if (buttonKey == undefined)
            buttonKey = "saveSettingsViewNewProfile";
        $("#"+tableId+"Show, #"+tableId+"Hide").sortable({
            connectWith: ".connectedSortable",
            tolerance: "pointer",
            containment: "#"+tableId,
            cursor: "pointer",
            cancel: ".sortableMenuLinksShowDesc",
            placeholder: "placeholder",
            axis: "y",
            update: function(event, ui){
                var linkId=$(ui.item).find("input[name="+inputName+"]").val();
                var parentDiv = ui.item.parent();
                if (parentDiv.attr("id") == tableId+'Show')
                    $("#"+selectedFieldId+linkId).attr("checked", true);
                else
                    $("#"+selectedFieldId+linkId).attr("checked", false);

                var parentsDiv = $("#" + tableId);
                if ($("#"+tableId+"Hide").find("."+linkClass).length == 0)
                {
                    parentsDiv.find(".sortableMenuLinksHideDesc").show();
                    parentsDiv.find(".sortableMenuLinksHideTitle").hide();
                }
                else
                {
                    parentsDiv.find(".sortableMenuLinksHideDesc").hide();
                    parentsDiv.find(".sortableMenuLinksHideTitle").show();
                }
                if ($("#"+tableId+"Show").find("."+linkClass).length == 0)
                {
                    parentsDiv.find(".sortableMenuLinksShowDesc").show();
                }
                else
                {
                    parentsDiv.find(".sortableMenuLinksShowDesc").hide();
                }

                var saveSettingsView = $('#'+buttonId);
                saveSettingsView.find('.buttonGreen').removeClass("disabled");
                saveSettingsView.click(function(){findCommandButton('button.'+buttonKey).click('', false);});
                resizeProductDescription(tableId);
            }
        });
    }

</script>
<tiles:insert definition="profileTemplate" flush="false">
    <tiles:put name="title" value="Настройки интерфейса"/>
    <tiles:put name="activeItem" value="interface"/>
    <tiles:put name="data">

    <div class="securityOptions">
    <tiles:insert definition="newUserProfileSecurity" flush="false">
        <tiles:put name="title" value="Главное меню"/>
        <tiles:put name="text" value="Настройте порядок расположения элементов главного меню"/>
        <tiles:put name="defaultCommandButon" value="button.saveLogin"/>
        <tiles:put name="data">
            <html:hidden property="selectedMenuLinkId"/>
            <c:set var="menuLinks" value="${form.menuLinks}"/>
            <div class="interface-items" id="sortableMenuLinks">
                <div class="sortableBlock">
                    <c:if test="${not empty menuLinks}">
                        <div  class="sortableMenuLinksMain">
                            <div class="sortIcon"></div>
                            <div class="left">Главная</div>
                        </div>
                        <div class="sortableMenuLinksMain">
                            <div class="sortIcon"></div>
                            <div class="left"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></div>
                        </div>
                        <div id="sortableMenuLinksShow" class="connectedSortable">
                            <c:set var="countShowLink" value="0"/>
                            <c:set var="countLink" value="${phiz:size(menuLinks)}"/>
                            <c:forEach var="linkInfo" items="${menuLinks}" varStatus="line">
                                 <c:if test="${linkInfo.link.use}">
                                     <c:set var="countShowLink" value="${countShowLink+1}"/>
                                    <div  class="sortableMenuLinks">
                                        <html:hidden property="sortMenuLinks" value="${linkInfo.link.id}"/>
                                        <div class="sortIcon"></div>
                                        <div class="left">
                                            <c:out value="${linkInfo.text}"/>
                                            <html:multibox property="selectedMenuLinks"
                                                           styleId="securityOptions${linkInfo.link.id}" styleClass="hideCheckbox">
                                                <bean:write name="linkInfo" property="link.id"/>
                                            </html:multibox>
                                        </div>
                                    </div>
                                 </c:if>
                            </c:forEach>
                            <div class="sortableMenuLinksShowDesc" <c:if test="${countShowLink > 0}">style="display:none"</c:if>>
                                <span>Перетащите сюда, чтобы показать</span>
                            </div>
                       </div>
                        <div class="sortableMenuLinksHide" >
                            <div class="sortableMenuLinksHideTitle" <c:if test="${countShowLink == countLink}">style="display:none"</c:if>>СКРЫТЫЕ ЭЛЕМЕНТЫ МЕНЮ</div>
                            <div id="sortableMenuLinksHide" class="connectedSortable">
                                <c:forEach var="linkInfo" items="${menuLinks}" varStatus="line">
                                     <c:if test="${!linkInfo.link.use}">
                                        <div class="sortableMenuLinks">
                                            <html:hidden property="sortMenuLinks" value="${linkInfo.link.id}"/>
                                            <div class="sortIcon"></div>
                                            <div class="left">
                                                <c:out value="${linkInfo.text}"/>
                                                <html:multibox property="selectedMenuLinks"
                                                               styleId="securityOptions${linkInfo.link.id}" styleClass="hideCheckbox">
                                                    <bean:write name="linkInfo" property="link.id"/>
                                                </html:multibox>
                                            </div>
                                        </div>
                                     </c:if>
                                </c:forEach>

                            </div>
                            <div class="sortableMenuLinksHideDesc" <c:if test="${countShowLink != countLink}">style="display:none"</c:if>>
                                Перетащите сюда, чтобы скрыть
                            </div>
                         </div>
                    </c:if>
                </div>
                <div class="clear"></div>
            </div>
            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancelChanges"/>
                    <tiles:put name="commandHelpKey" value="button.cancelChanges"/>
                    <tiles:put name="bundle" value="userprofileBundle"/>
                    <tiles:put name="action" value="/private/favourite/list.do"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.saveMainMenuSettings"/>
                    <tiles:put name="commandHelpKey" value="button.saveMainMenuSettings.help"/>
                    <tiles:put name="bundle" value="favouriteBundle"/>
                    <tiles:put name="enabled" value="false"/>
                    <tiles:put name="id" value="saveMainMenuSettings"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
    </div>
    <div class="securityOptions">
    <tiles:insert definition="newUserProfileSecurity" flush="false">
        <tiles:put name="title" value="Порядок продуктов на главной странице"/>
        <tiles:put name="text" value="Настройте порядок расположения продуктов на главной странице"/>
        <tiles:put name="style" value="id3"/>
        <tiles:put name="defaultCommandButon" value="button.saveSettingsViewNewProfile"/>
        <tiles:put name="data">
            <div id="mainPageSettings">
                <%--Карты--%>
                <c:if test="${not empty form.cards}">
                    <%@include file="mainPageProductSettings/card.jsp" %>
                </c:if>
                <%--Вклады--%>
                <c:if test="${not empty form.accounts}">
                    <%@include file="mainPageProductSettings/account.jsp" %>
                </c:if>
                <%--Кредиты--%>
                <c:if test="${not empty form.loans && phiz:impliesService('ReceiveLoansOnLogin')}">
                    <%@include file="mainPageProductSettings/loans.jsp" %>
                </c:if>
                <%--ОМС--%>
                <c:if test="${not empty form.imAccounts}">
                    <%@include file="mainPageProductSettings/imAccount.jsp" %>
                </c:if>
                <%--Счета депо--%>
                <c:set var="personInf" value="${phiz:getPersonInfo()}"/>
                <c:choose>
                    <c:when test="${!personInf.isRegisteredInDepo && personInf.creationType == 'UDBO'}">
                        <div class="profile-view-products">
                            <div class="sortableHeader"><bean:message bundle="userprofileBundle" key="title.depoAccounts"/></div>

                            <div class="profile-empty-text-depo"><bean:message bundle="commonBundle" key="text.depositary.not.include"/>&nbsp;<html:link
                                        action="/private/depo/list.do">Счета депо</html:link>.
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${not empty form.depoAccounts}">
                            <%@include file="mainPageProductSettings/depoAccount.jsp" %>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <%--Предложения банка--%>
                <div class="profile-view-products">
                    <div class="sortableHeader">
                        <bean:message bundle="userprofileBundle" key="title.bank.offer"/>
                    </div>
                    <div class="noSortable">
                        <div class="sortableProductLinks">
                            <div class="left">
                                <span class="titleBlock"><bean:message bundle="userprofileBundle" key="label.bank.offer"/></span>
                            </div>
                            <div class="right"></div>
                        </div>
                    </div>
                </div>
                <%--ПФР--%>
                <c:if test="${not empty form.pfrLink}">
                    <div id="sortablePFR">
                        <div class="sortableHeader">
                            <bean:message bundle="userprofileBundle" key="title.pfr"/>
                        </div>

                        <div id="sortablePFRShow" class="connectedSortable">
                            <c:if test="${form.pfrLink.showInMain}">
                                <div class="sortableProductLinks">
                                    <div class="tinyProductImg opacitySort">
                                        <img src="${imagePath}/products/icon_pfr32.jpg" alt=""/>
                                    </div>
                                    <div class="left opacitySort">
                                        <span class="titleBlock"><bean:message bundle="userprofileBundle" key="label.pfr"/></span>
                                        <html:checkbox name="form"  property="pfrLinkSelected" value="1" styleId="pfrLinkSelected1" styleClass="hideCheckbox"/>
                                    </div>
                                    <div class="right opacitySort"></div>
                                </div>
                            </c:if>
                            <div class="sortableMenuLinksShowDesc" <c:if test="${form.pfrLink.showInMain}">style="display:none"</c:if>>
                                <span>Перетащите сюда, чтобы показать</span>
                            </div>
                        </div>
                        <div class="sortableMenuLinksHide">
                            <div class="sortableMenuLinksHideTitle" <c:if test="${form.pfrLink.showInMain}">style="display:none"</c:if>>СКРЫТЫ НА ГЛАВНОЙ</div>
                            <div id="sortablePFRHide"  class="connectedSortable">
                                <c:if test="${!form.pfrLink.showInMain}">
                                    <div class="sortableProductLinks">
                                        <div class="tinyProductImg opacitySort">
                                            <img src="${imagePath}/products/icon_pfr32.jpg" alt=""/>
                                        </div>
                                        <div class="left opacitySort">
                                            <span class="titleBlock"><bean:message bundle="userprofileBundle" key="label.pfr"/></span>
                                            <html:checkbox name="form"  property="pfrLinkSelected" value="1" styleId="pfrLinkSelected1" styleClass="hideCheckbox"/>
                                        </div>
                                        <div class="right opacitySort"></div>
                                    </div>

                                </c:if>
                            </div>
                            <div class="sortableMenuLinksHideDesc" <c:if test="${!form.pfrLink.showInMain}">style="display:none"</c:if> >
                                Перетащите сюда, чтобы скрыть
                            </div>
                        </div>

                    </div>
                </c:if>
                <c:if test="${phiz:impliesService('LoyaltyProgramRegistrationClaim')}">
                    <div class="profile-view-products">
                        <div class="sortableHeader">
                            <bean:message bundle="userprofileBundle" key="label.loyalty"/>
                        </div>
                        <div class="noSortable">
                            <div class="sortableProductLinks">
                                <div class="tinyProductImg">
                                    <img src="${imagePath}/products/loyaltyProgramIcon32.gif" alt=""/>
                                </div>
                                <div class="left">
                                   <span class="titleBlock"><bean:message bundle="userprofileBundle" key="label.loyalty"/></span>
                                </div>
                                <div class="right"></div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="clear"></div>
                <div class="buttonsArea">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.saveSettingsViewNewProfile"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                        <tiles:put name="enabled" value="false"/>
                        <tiles:put name="id" value="saveSettingsView"/>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
    </div>
</tiles:put>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>

