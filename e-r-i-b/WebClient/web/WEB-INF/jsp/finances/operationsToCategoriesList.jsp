<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/async/operations/list" >
<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="operations" value="${form.cardOperations}" scope="request"/>
<c:set var="size" value="${fn:length(operations)}"/>
<c:set var="searchPage" value="${form.searchPage}"/>
<c:set var="resOnPage" value="${form.resOnPage}"/>
<c:set var="editOperationUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/finances/category-abstract.do')}"/>
<c:set var="fromDate"><fmt:formatDate value="${form.filters['fromDate']}" pattern="dd/MM/yyyy"/></c:set>
<c:set var="toDate"><fmt:formatDate value="${form.filters['toDate']}" pattern="dd/MM/yyyy"/></c:set>
<c:set var="typeDate" value="${form.filters['typeDate']}"/>
<c:set var="categoryId" value="${form.filters['categoryId']}"/>
<c:set var="onlyAvailableCategories" value="${form.filters['onlyAvailableCategories']}"/>
<c:set var="income" value="${form.filters['income']}"/>
<c:set var="openPageDate"><fmt:formatDate value="${form.filters['openPageDate']}" pattern="dd/MM/yyyy HH:mm:ss"/></c:set>
<c:set var="canEdit" value="${phiz:impliesService('EditOperationsService')}"/>
<c:set var="selectedCardIds" value="${form.filters['selectedCardIds']}"/>

&nbsp;
<c:choose>
    <c:when test="${size > 0 || searchPage > 0}">
        <div style="margin-bottom: 10px;">
            <table class="<c:if test="${canEdit}">edit </c:if>operationsTable" cellpadding="0" cellspacing="0">
                <tr>
                    <th><bean:message bundle="financesBundle" key="label.list.operations.date"/></th>
                    <th><bean:message bundle="financesBundle" key="label.list.operations.name"/></th>
                    <th><bean:message bundle="financesBundle" key="label.list.operations.category"/></th>
                    <th><bean:message bundle="financesBundle" key="label.list.operations.cardNumber"/></th>
                    <th class="align-right"><bean:message bundle="financesBundle" key="label.list.operations.amount"/></th>
                </tr>
                <c:choose>
                    <c:when test="${size > 0}">
                        <c:forEach items="${operations}" var="operationDescription" end="${resOnPage - 1}">
                            <c:set var="editFunction" value="editOperation.setListOperationWithCategoriesData(this, '${fromDate}','${toDate}', '${typeDate}', ${searchPage}, ${resOnPage}, '${openPageDate}', '${categoryId}', '${selectedCardIds}', '${onlyAvailableCategories}'); editOperation.open(${operationDescription.id});"/>
                            <tr class="operationTr" <c:if test="${canEdit}"> onclick="if(!redirectResolved()) return false; ${editFunction} return false;"</c:if>>
                                <td class="date">
                                    <fmt:formatDate value="${operationDescription.date}" pattern="dd.MM.yyyy"/>
                                </td>
                                <td class="description">
                                    <c:out value="${operationDescription.title}"/>
                                    <c:if test="${not empty operationDescription.businessDocumentId}">
                                        <img src="${commonImagePath}/grayClock.gif" onclick="if(!redirectResolved()) return false; financesUtils.goToViewPayment('${operationDescription.businessDocumentId}'); cancelBubbling(event);">
                                    </c:if>
                                </td>
                                <td class="categoryName">
                                    <c:out value="${operationDescription.category.name}"/>
                                </td>
                                <td class="cardNumber">
                                    <c:choose>
                                        <c:when test="${not empty operationDescription.card}">
                                            <c:out value="${phiz:getCutCardNumber(operationDescription.card.number)}"/>
                                        </c:when>
                                        <c:otherwise>
                                            Наличные
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="operationSumm align-right">
                                    <c:if test="${operationDescription.nationalAmount.decimal > 0}">+</c:if>${phiz:formatAmount(operationDescription.nationalAmount)}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5" class="align-center">На данной странице нет записей. Пожалуйста, перейдите на предыдущую страницу.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>

            <c:if test="${size > 10 && searchPage == 0 || size >= 0 && searchPage > 0}">
                <div class="operationsListPagination">
                    <div class="align-center">
                        <c:choose>
                            <c:when test="${searchPage > 0}">
                                <a class="active-arrow" href="#" onclick="if(!redirectResolved()) return false; changePage(this, 'last'); return false;"><div class="activePaginLeftArrow"></div></a>
                            </c:when>
                            <c:otherwise>
                                <div class="inactivePaginLeftArrow"></div>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${size > resOnPage}">
                                <a class="active-arrow" href="#" onclick="if(!redirectResolved()) return false; changePage(this, 'next'); return false;"><div class="activePaginRightArrow"></div></a>
                            </c:when>
                            <c:otherwise>
                                <div class="inactivePaginRightArrow"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="operationsListPaginSize">
                        показывать по:
                        <input type="hidden" value="${resOnPage}" name="resOnPage">
                        <c:choose>
                            <c:when test="${resOnPage == 10}">
                                <span><div class="greenSelector"><span>10</span></div></span>
                                <span class="proportion"><a onclick="if(!redirectResolved()) return false; changeResOnPage(this, 20); return false;" href="#">20</a></span>
                                <span class="proportion"><a onclick="if(!redirectResolved()) return false; changeResOnPage(this, 50); return false;" href="#">50</a></span>
                            </c:when>
                            <c:when test="${resOnPage == 20}">
                                <span class="proportion"><a onclick="if(!redirectResolved()) return false; changeResOnPage(this, 10); return false;" href="#">10</a></span>
                                <div class="greenSelector"><span>20</span></div>
                                <span class="proportion"><a onclick="if(!redirectResolved()) return false; changeResOnPage(this, 50); return false;" href="#">50</a></span>
                            </c:when>
                            <c:when test="${resOnPage == 50}">
                                <span class="proportion"><a onclick="if(!redirectResolved()) return false; changeResOnPage(this, 10); return false;" href="#">10</a></span>
                                <span class="proportion"><a onclick="if(!redirectResolved()) return false; changeResOnPage(this, 20); return false;" href="#">20</a></span>
                                <div class="greenSelector"><span>50</span></div>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>
        <script type="text/javascript">
            function changePage(elem, paginationType)
            {
                var id = $(elem).closest(".operationsListBlock").attr("id");
                var params = {
                    resOnPage: ${resOnPage},
                    fromDate: '${fromDate}',
                    toDate: '${toDate}',
                    typeDate: '${typeDate}',
                    category: '${categoryId}',
                    onlyAvailableCategories: '${onlyAvailableCategories}',
                    selectedCardIds: '${selectedCardIds}',
                    openPageDate: '${openPageDate}',
                    income: '${income}'
                };
                financesUtils.changePage(id, params, paginationType, ${searchPage});
            }

            function changeResOnPage(elem, num)
            {
                setElement('resOnPage', num);

                var id = $(elem).closest(".operationsListBlock").attr("id");
                var showOtherAccounts = getElementValue('filter(showOtherAccounts)');
                var params = {
                    resOnPage: num,
                    fromDate: '${fromDate}',
                    toDate: '${toDate}',
                    typeDate: '${typeDate}',
                    category: '${categoryId}',
                    onlyAvailableCategories: '${onlyAvailableCategories}',
                    selectedCardIds: '${selectedCardIds}',
                    openPageDate: '${openPageDate}',
                    income: '${income}'
                };
                financesUtils.changeResOnPage(id, params);
            }

            if(window.isClientApp != undefined)
            {
                editOperation.url = '${editOperationUrl}';
                editOperation.operations = [];
                <c:forEach items="${operations}" var="operationDescription" end="${resOnPage - 1}">
                editOperation.operations.push({
                    id: ${operationDescription.id},
                    date: "<fmt:formatDate value="${operationDescription.date}" pattern="dd.MM.yyyy"/>",
                    title: "${fn:escapeXml(operationDescription.title)}",
                    categoryId: ${operationDescription.categoryId},
                    <c:choose>
                    <c:when test="${not empty operationDescription.cardAmount}">
                    sum: ${operationDescription.cardAmount.decimal},
                    sumText: "${phiz:formatAmount(operationDescription.cardAmount)}",
                    currency: "${phiz:getCurrencySign(operationDescription.cardAmount.currency.code)}",
                    cardInfo: "${phiz:getCutCardNumberPrint(operationDescription.card.number)}",
                    </c:when>
                    <c:otherwise>
                    sum: ${operationDescription.nationalAmount.decimal},
                    sumText: "${phiz:formatAmount(operationDescription.nationalAmount)}",
                    currency: "${phiz:getCurrencySign(operationDescription.nationalAmount.currency.code)}",
                    cardInfo: "Наличные",
                    </c:otherwise>
                    </c:choose>
                    categories: [
                        <c:forEach var="category" items="${operationDescription.availableCategories}" varStatus="i">
                        <c:if test="${i.index>0}">,</c:if>
                        { id: ${category.id}, title: "${phiz:escapeForJS(category.name, true)}" }
                        </c:forEach>
                    ],
                    businessDocumentId: "${operationDescription.businessDocumentId}"
                });
                </c:forEach>
                <c:if test="${canEdit}">
                $('.operationsTable .operationTr')
                        .bind('mouseenter',
                        function ()
                        {
                            $(this).addClass("over");
                        })
                        .bind('mouseleave',
                        function ()
                        {
                            $(this).removeClass("over");
                        });
                </c:if>

                $('.operationsTable .categoryName').breakWords();
            }
        </script>
    </c:when>

    <c:otherwise>
        <div class="emptyText">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="greenBold"/>
                <tiles:put name="data">
                        <span class="text-dark-gray normal relative">
                            <bean:message bundle="financesBundle" key="message.finance.operations.emptyMessage"/>
                        </span>
                </tiles:put>
            </tiles:insert>
        </div>
    </c:otherwise>
</c:choose>
</html:form>