<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
<html:form action="/private/finances/categoryOperationsAbstract" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="category" value="${form.category}"/>
    <c:set var="operations" value="${form.data}" scope="request"/>
    <c:set var="size" value="${fn:length(operations)}"/>
    <c:set var="searchPage" value="0"/>
    <c:set var="resOnPage" value="10"/>
    <tiles:insert definition="financePlot">

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function selectGeneralCategory()
                {
                    var newCategory = $('#generalCategory').val();
                    setElement('field(generalCategory)', newCategory);
                    $(".operationSelect").val(newCategory);
                    $(".operationCategory").val(newCategory);
                }

                function changeOperationCategory(select, operationId)
                {
                    $('#generalCategory').val(-1);
                    setElement('field(generalCategory)', -1);

                    $("#categoryInput"+operationId).val($(select).val());
                }

                function initCategoryInput(operationId)
                {
                    var id = "categoryInput"+operationId;
                    var categoryId = $("#"+id).val();
                    $("#operationSelect"+operationId).val(categoryId);
                }

                function createNewCategoryInput(operationId)
                {
                    var id = "categoryInput"+operationId;
                    if ($("#"+id).length == 0)
                    {
                        var input = document.createElement("input");
                        input.setAttribute("type", "hidden");
                        input.className = "operationCategory";
                        input.setAttribute("id", id);
                        input.setAttribute("name", "newCategory("+operationId+")");
                        input.setAttribute("value",-1);
                        $("#newOperationCategories").append(input);
                    }
                }

                function checkEmptyNewCategory()
                {
                    var selectList = $(".operationSelect");
                    for(var i = 0; i < selectList.size(); i++)
                        if (parseInt(selectList[i].value) == -1)
                        {
                            win.open('errorAllCategoriesChanged');
                            return false;
                        }
                    return true;
                }
            </script>

            <%-- ¬сплывающее окно добавлени€ категории --%>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="errorAllCategoriesChanged"/>
                <tiles:put name="data">
                    <jsp:include page="/WEB-INF/jsp/finances/window/changeAllCategories.jsp"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="roundBorderWithoutTop" flush="false">
                <tiles:put name="top">
                    <c:set var="selectedTab" value="operationCategories"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="financeContainer" flush="false">
                        <tiles:put name="title"><bean:message key="categoryOperationsAbstract.containerTitle" bundle="financesBundle"/> &laquo;${category.name}&raquo;</tiles:put>
                        <tiles:put name="showFavourite" value="false"/>
                        <tiles:put name="infoText">
                            <bean:message key="categoryOperationsAbstract.infoText" bundle="financesBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div id="newOperationCategories">
                                <input type="hidden" name="field(generalCategory)" value="0"/>
                            </div>

                            <c:if test="${not empty form.data}">
                                <div style="margin-bottom: 10px;" id="operationsListBlock">
                                    <table id="deletedCategoryOperationsTable" class="operationsTable deletedCategoryOperationsTable" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <th><bean:message bundle="financesBundle" key="table.label.date"/></th>
                                            <th><bean:message bundle="financesBundle" key="table.label.description"/></th>
                                            <th>
                                                <select id="generalCategory" class="select" style="width:150px;" onchange="selectGeneralCategory();">
                                                    <option value="-1"><bean:message bundle="financesBundle" key="table.label.setupAll"/></option>
                                                    <c:forEach items="${form.otherCategories}" var="categoryItem">
                                                        <c:if test="${categoryItem.id != form.category.id}">
                                                            <option value="${categoryItem.id}">${categoryItem.name}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </th>
                                            <th><bean:message bundle="financesBundle" key="table.label.account"/></th>
                                            <th class="align-right"><bean:message bundle="financesBundle" key="table.label.amount"/></th>
                                        </tr>

                                        <c:forEach items="${operations}" var="operationDescription" end="${resOnPage - 1}">
                                            <tr id="operation">
                                                <td class="date">
                                                    <fmt:formatDate value="${operationDescription.date.time}" pattern="dd.MM.yyyy"/>
                                                </td>
                                                <td class="description">
                                                    <c:out value="${operationDescription.description}"/>
                                                </td>
                                                <td class="categoryName">
                                                    <select id="operationSelect${operationDescription.id}" class="operationSelect" style="width:150px;" onchange="changeOperationCategory(this, ${operationDescription.id});">
                                                        <option value="-1"><bean:message bundle="financesBundle" key="table.label.selectCategory"/></option>
                                                        <c:forEach items="${form.otherCategories}" var="categoryItem">
                                                            <c:if test="${categoryItem.id != category.id}">
                                                                <option value="${categoryItem.id}">${categoryItem.name}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td class="cardNumber">
                                                    <c:out value="${phiz:getCutCardNumber(operationDescription.cardNumber)}"/>
                                                </td>
                                                <td class="operationSumm align-right">
                                                    <c:if test="${operationDescription.nationalAmount > 0}">+</c:if>${operationDescription.nationalAmount}
                                                </td>
                                            </tr>

                                            <script type="text/javascript">
                                                $(document).ready( function () {
                                                    createNewCategoryInput(${operationDescription.id});
                                                    initCategoryInput(${operationDescription.id});
                                                });
                                            </script>
                                        </c:forEach>
                                    </table>

                                    <c:if test="${size > resOnPage && searchPage == 0 || size > 0 && searchPage > 0}">
                                        <script type="text/javascript">
                                            function changeResOnPage(num)
                                            {
                                                setElement('field(resOnPage)', num);
                                                setElement('field(searchPage)', 0);
                                                changePage();
                                            }

                                            <%-- ‘ункци€ пейджинга страницы --%>
                                            function changePage()
                                            {
                                                if (!checkEmptyNewCategory())
                                                    return;

                                                var params = "&operation=button.getOperations";
                                                params += "&categoryId=${category.id}";
                                                params += "&resOnPage="+getField('resOnPage').value;
                                                params += "&searchPage="+getField('searchPage').value;

                                                var actionURL = "${phiz:calculateActionURL(pageContext,"/private/async/finances/categoryOperationsAbstract/list.do")}";
                                                ajaxQuery (params, actionURL, function(data){operationsAjaxResult(data);});
                                            }

                                            function operationsAjaxResult(data)
                                            {
                                                data = trim(data);
                                                //если вернулась пуста€ строка, то веро€тнее всего произошл тайм аут сессии, перезагружаем страницу
                                                if (data == '')
                                                {
                                                    window.location.reload();
                                                }
                                                else
                                                {
                                                    $('#operationsListBlock').html(data);
                                                }
                                            }
                                        </script>

                                        <div class="operationsListPagination">
                                            <html:hidden property="field(searchPage)" value="${searchPage}"/>
                                            <div class="align-center">
                                                <div class="inactivePaginLeftArrow"></div>
                                                <c:choose>
                                                    <c:when test="${size > resOnPage}">
                                                        <a class="active-arrow" href="#" onclick="setElement('field(searchPage)', ${searchPage+1}); changePage(); return false;"><div class="activePaginRightArrow"></div></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="inactivePaginRightArrow"></div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                            <div class="operationsListPaginSize">
                                                показывать по:
                                                <html:hidden property="field(resOnPage)" value="${resOnPage}"/>
                                                <span><div class="greenSelector"><span>10</span></div></span>
                                                <a onclick="changeResOnPage(20); return false;" href="#">20</a>
                                                <a onclick="changeResOnPage(50); return false;" href="#">50</a>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>

                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="button.close.help"/>
                                        <tiles:put name="bundle" value="financesBundle"/>
                                        <tiles:put name="viewType" value="simpleLink"/>
                                        <c:choose>
                                            <c:when test="${phiz:impliesService('UseWebAPIService')}">
                                                <tiles:put name="redirectURL" value="${phiz:getWebAPIUrl('operation.categories')}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <tiles:put name="action" value="/private/finances/operationCategories.do"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </tiles:insert>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey" value="button.next"/>
                                        <tiles:put name="commandHelpKey" value="button.next.help"/>
                                        <tiles:put name="bundle" value="financesBundle"/>
                                        <tiles:put name="validationFunction" value="checkEmptyNewCategory();"/>
                                    </tiles:insert>
                                </div>
                            </c:if>

                            <c:if test="${empty form.data}">
                                <tiles:insert definition="roundBorderLight" flush="false">
                                    <tiles:put name="color" value="greenBold"/>
                                    <tiles:put name="data">
                                        <span class="text-dark-gray normal relative">
                                            <bean:message bundle="financesBundle" key="message.empty.categoryAbstract"/>
                                        </span>
                                    </tiles:put>
                                </tiles:insert>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>