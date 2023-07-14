<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/finances/operationCategories" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="isHintsRead" value="${form.readAllHints}"/>
    <tiles:insert definition="financePlot">

        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <c:set var="selectedTab" value="operationCategories"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>

                    <div id="financeContainer">
                        <tiles:insert definition="financeContainer" flush="false">
                            <tiles:put name="showFavourite" value="false"/>
                            <tiles:put name="infoText">
                                <c:set var="actualDate"><bean:write name="form" property="lastModified.time" format="dd.MM.yyyy" ignore="true"/></c:set>
                                <c:if test="${not empty form.lastModified}">
                                    <c:set var="isOldData" value="${phiz:hourToCurrentDate(form.lastModified) > 24}"/>
                                    <c:if test="${isOldData}">
                                        <br/>
                                        <tiles:insert definition="roundBorderLight" flush="false">
                                            <tiles:put name="color" value="infMesOrange"/>
                                            <tiles:put name="data">
                                                <div class="infoMessage">
                                                    <div class="messageUpdate">
                                                        <bean:message key="message.operationCategories" bundle="financesBundle" arg0="${actualDate}"/>
                                                        &nbsp;
                                                        <tiles:insert definition="clientButton" flush="false">
                                                              <tiles:put name="commandTextKey" value="button.update"/>
                                                              <tiles:put name="commandHelpKey" value="button.update.help"/>
                                                              <tiles:put name="bundle" value="financesBundle"/>
                                                              <tiles:put name="viewType" value="buttonGrayNew"/>
                                                              <tiles:put name="onclick" value="reload();cancelBubbling(event);"/>
                                                          </tiles:insert>
                                                    </div>
                                                </div>
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>
                            </tiles:put>
                            <tiles:put name="data">
                                <%--Фильтр--%>
                                <tiles:insert definition="filterDate" flush="false">
                                    <tiles:put name="name" value="Date"/>
                                    <tiles:put name="buttonKey" value="button.filter"/>
                                    <tiles:put name="buttonBundle" value="newsBundle"/>
                                    <tiles:put name="validationFunction" value="checkFilterDates();"/>
                                </tiles:insert>

                                <div class="hightLevelPosition">
                                    <c:set var="node" value="${form.node}" scope="request"/>
                                    <tiles:insert page="/WEB-INF/jsp/common/layout/customSelect.jsp" flush="false">
                                        <tiles:put name="customSelectId" value="1"/>
                                        <tiles:put name="customSelectName" value="selectedId"/>
                                        <tiles:put name="customSelectValue" value="Все карты и наличные"/>
                                        <tiles:put name="customSelectOnclick" value="findCommandButton('button.filter').click('', false);"/>
                                    </tiles:insert>
                                    <div class="clear"></div>
                                </div>
                                <div class="filterCheckboxesInLine">
                                    <div class="filterCheckbox">
                                        <html:checkbox property="filter(showTransfer)" onclick="if(!redirectResolved()) return false;findCommandButton('button.filter').click('', false);"/>Показать переводы
                                    </div>
                                    <div class="filterCheckbox">
                                        <html:checkbox property="filter(showCash)" onclick="if(!redirectResolved()) return false;findCommandButton('button.filter').click('', false);"/>Показать снятие наличных
                                    </div>
                                    <div class="filterCheckbox">
                                        <html:checkbox property="filter(showCents)" styleId="ShowCents"/>Показать копейки
                                    </div>
                                    <div class="clear"></div>
                                </div>

                                <c:set var="openPageDate" scope="request"><fmt:formatDate value="${form.openPageDate.time}" pattern="dd/MM/yyyy HH:mm:ss"/></c:set>
                                <c:set var="selectedCardIds" value="" scope="request"/>
                                <c:forEach items="${form.node.selectedIds}" var="cardId" varStatus="i">
                                    <c:set var="selectedCardIds" value="${selectedCardIds}${cardId};" scope="request"/>
                                </c:forEach>

                                <input type="hidden" id="lastShowCategory" value="outcome">
                                <div class="operationsCaregories">
                                    <c:set var="totalAmount" value="0"/>
                                    <c:set var="otherCategoriesLabel" value="Остальные категории списаний"/>
                                    <%@ include file="/WEB-INF/jsp/finances/categoryOperationsDiagram.jsp" %>

                                    <c:if test="${!isHintsRead}">
                                        <div id="beginUseBudget">
                                            <span onclick="if(!redirectResolved()) return false;beginUseBudget();">Начните управлять своими расходами</span>
                                            <div class="clear"></div>
                                        </div>
                                    </c:if>

                                    <div id="costManagementBlock" class="operationsCaregories">
                                        <c:if test="${not empty form.outcomeOperations}">
                                            <tiles:insert definition="picInfoBlock" flush="false">
                                                <tiles:put name="title" value="Управление расходами"/>
                                                <tiles:put name="text">
                                                    <bean:message bundle="financesBundle" key="text.finance.outcome.control.description"/>
                                                </tiles:put>
                                                <tiles:put name="style" value="cost"/>
                                                <tiles:put name="onClick" value="if(!redirectResolved()) return false;showOrHide('cost'); delHighlighting(this);"/>
                                                <tiles:put name="aditionalData">
                                                    <c:set var="frm" value="${form}"/>
                                                    <c:set var="operations" value="${form.outcomeOperations}"/>
                                                    <c:set var="outcomeCategories" value="${form.outcomeCategories}"/>
                                                    <%@ include file="/WEB-INF/jsp/finances/costManagement.jsp" %>
                                                </tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                    </div>

                                    <c:if test="${phiz:impliesService('EditCategoriesService')}">
                                        <tiles:insert definition="picInfoBlock" flush="false">
                                            <tiles:put name="title" value="Управление категориями"/>
                                            <tiles:put name="text">
                                                В данном меню Вы можете создать новую расходную  категорию. Для изменения категории по операции, воспользуйтесь выпиской по категории.
                                            </tiles:put>
                                            <tiles:put name="style" value="category"/>
                                            <tiles:put name="onClick" value="if(!redirectResolved()) return false;showOrHide('category');"/>
                                            <tiles:put name="aditionalData">
                                                <c:set var="outcomeCategories" value="${form.outcomeCategories}"/>
                                                <%@ include file="/WEB-INF/jsp/finances/categories.jsp" %>
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <%-- Подключаем  скрипт для АЛФ--%>
    <script type="text/javascript" src="${globalUrl}/scripts/finances/financesUtils.js"></script>
    <%-- Подключаем  скрипт с для создания подсказок на странице--%>
    <script type="text/javascript" src="${globalUrl}/scripts/hint.js"></script>
    
    <%@ include file="/WEB-INF/jsp/finances/editOperationWindow.jsp"%>

    <script type="text/javascript">
        var hints = new Array();
        $(document).ready(function()
            {
                $("#categoryList").hide();

                <c:choose>
                    <c:when test="${not isHintsRead}">
                        var firstBudgetScale = $('#budgetScalesBlock .budgetScale:first');
                        pushHint(1, $('#budgetScaleTotal .dribbleHint'), 'Сумма расходов по всем категориям.', nextElementHint);
                        pushHint(2, $(firstBudgetScale).find('.thermometerLeftData span'), 'Категории ваших расходов.', function(){nextElementHint();});
                        pushHint(3, $(firstBudgetScale).find('.editBudgetData span'), 'Средние расходы по категории за последние 6 месяцев.', function(){nextElementHint();});
                        pushHint(4, $(firstBudgetScale).find('.dribbleHint'), 'Ваши текущие расходы по данной категории.', function(){nextElementHint();});
                        pushHint(5, $(firstBudgetScale).find('.editBudgetData span'), 'При наведении можно редактировать или удалять категорию.', function(){nextElementHint();});
                        pushHint(6, $('#newCategoryButtons .addNewCategoryButton:first'), 'Добавление новой категории.', function(){nextElementHint();});

                        $('#costManagementBlock').hide();
                    </c:when>
                    <c:otherwise>
                        $("#costList").hide();
                    </c:otherwise>
                </c:choose>

                financesUtils.setShowTransfers(getElementValue("filter(showTransfer)"));
            }
        );

        function beginUseBudget()
        {
            $('#beginUseBudget').hide();
            $('#costManagementBlock').show();
            blockWorkspaceContent('blockDiv', $('#financeContainer'));
            showHint();
        }

        function showOrHide(name)
        {
            $("#"+name+"List").toggle();
            $("."+name+"Container").find("h1").toggleClass("closeCategory");
            if(typeof costManagment != "undefined" && $("#costList").is(':visible'))
            {
               costManagment.redrawBudgetList();
            }
        }

        <%-- Убираем подсветку у основного блока --%>
        function delHighlighting(elem)
        {
            $(elem).closest('.forProductBorder').toggleClass("showLight");
            $(elem).closest('.forProductBorder').toggleClass("productTitleHover");
        }

        <c:if test="${not isHintsRead}">
            <%-- Добавить подсказку --%>
            function pushHint(id, element, data, closeCallback)
            {
                var hint = {
                    id: id,
                    element: element,
                    data: data,
                    closeCallback: closeCallback
                };

                hints.push(hint);
            }

            <%-- Отобразать текущую подсказку --%>
            function showHint()
            {
                var hint = hints[0];
                var isCreate = hintUtils.createElementHint(hint.id, hint.element, hint.data, true, '', hint.closeCallback);

                <%-- Подсказка не создалась --%>
                if (!isCreate)
                {
                    nextElementHint();
                }
            }

            function allHintsRead()
            {
                var actionURL = document.webRoot + "/private/async/budget/save.do";
                ajaxQuery ("operation=button.allHintsRead", actionURL, function(){}, null, false);
            }

            <%-- Удаляет прочитанную подсказку из списка, отображает следующую подсказку. --%>
            function nextElementHint()
            {
                hintUtils.removeHint(hints.shift().id);

                if (hints.length == 0)
                {
                    allHintsRead();
                    hideBlockDiv('blockDiv');
                    $('.costContainer').closest('.forProductBorder').toggleClass("showLight");
                }
                else
                    showHint();
            }

            function blockWorkspaceContent(id, content)
            {
                $('<div />', {id: id, className: 'blockDiv'}).appendTo("body");
                positionedBlockDiv(id, content);

                $(window).resize(function(){
                    positionedBlockDiv(id, content);
                });
            }

            function positionedBlockDiv(id, content)
            {
                var block = $('#'+id);
                block.css("width", content.outerWidth(true)+32);
                block.css("height", content.outerHeight(true));
                block.css("left", content.offset().left-16);
                block.css("top", content.offset().top);
            }

            function hideBlockDiv(id)
            {
                $('#'+id).hide();
            }
        </c:if>

        <c:set var="currDate" value="${phiz:currentDate().time}"/>

        function checkFilterDates()
        {
            removeAllErrors();
            var typeDate = $('input[name=filter(typeDate)]').val();
            var maxDate = parseDate('<fmt:formatDate value="${currDate}" pattern="dd/MM/yyyy"/>');
            var minDate = new Date(maxDate.getYear()-1,maxDate.getMonth()-1,1);

            if (typeDate == 'period')
            {
                var fromDateStr = $('input[name=filter(fromDate)]').val();
                var toDateStr = $('input[name=filter(toDate)]').val();

                var fromDate = parseDate(fromDateStr, "Введите дату в поле Период в формате ДД/ММ/ГГГГ.");
                if (fromDate == null)
                    return false;

                var toDate = parseDate(toDateStr, "Введите дату в поле Период в формате ДД/ММ/ГГГГ.");
                if (toDate == null)
                    return false;

                if (fromDate > toDate)
                {
                    addError("Конечная дата должна быть больше начальной!");
                    return false;
                }

                if (toDate > maxDate || fromDate < minDate)
                {
                    addError("Вы можете просмотреть движение средств только за последний год.");
                    return false;
                }
            }
            else
            {
                var onDateStr = $('input[name=filter(onDate)]').val();

                var onDate = parseDate(onDateStr, "Введите дату в поле Месяц в формате ДД/ММ/ГГГГ.");
                if (onDate == null)
                    return false;

                if (onDate > maxDate || onDate < minDate)
                {
                    addError("Вы можете просмотреть движение средств только за последний год.");
                    return false;
                }
            }
            return true;
        }

        function parseDate(dateStr, msg)
        {
            var dateYear = dateStr.slice(6);
            var dateMonth = dateStr.slice(3,5)-1;
            var dateDay = dateStr.slice(0,2);
            var date = new Date(dateYear, dateMonth, dateDay);

            if (date.getFullYear() != dateYear || date.getMonth() != dateMonth || date.getDate() != dateDay)
            {
                addError(msg);
                return null;
            }

            return date;
        }
    </script>
</html:form>