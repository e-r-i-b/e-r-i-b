<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/async/finances/categoryOperationsAbstract/list" >
    <c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="operations" value="${form.cardOperations}" scope="request"/>
    <c:set var="size" value="${fn:length(operations)}"/>
    <c:set var="searchPage" value="${form.searchPage}"/>
    <c:set var="resOnPage" value="${form.resOnPage}"/>

    <c:if test="${size > 0}">
        <table class="operationsTable deletedCategoryOperationsTable" cellpadding="0" cellspacing="0">
            <tr>
                <th><bean:message bundle="financesBundle" key="table.label.date"/></th>
                <th><bean:message bundle="financesBundle" key="table.label.description"/></th>
                <th>
                    <select id="generalCategory" class="select" style="width:150px;" onchange="selectGeneralCategory();">
                        <option value="-1"><bean:message bundle="financesBundle" key="table.label.setupAll"/></option>
                        <c:forEach items="${form.otherCategories}" var="categoryItem">
                            <c:if test="${categoryItem.id != category.id}">
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
                        <select id="operationSelect${operationDescription.id}" class="operationSelect" style="width:150px;" name="field(operationCategory${operationDescription.id})" onchange="changeOperationCategory(this, ${operationDescription.id});">
                            <option value="-1"><bean:message bundle="financesBundle" key="table.label.selectCategory"/></option>
                            <c:forEach items="${form.otherCategories}" var="categoryItem">
                                <c:if test="${categoryItem.id != form.categoryId}">
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
                    if(window.isClientApp != undefined)
                    {
                        $(document).ready( function () {
                            createNewCategoryInput(${operationDescription.id});
                            initCategoryInput(${operationDescription.id});
                        });
                    }
                </script>
            </c:forEach>
        </table>

        <script type="text/javascript">
            if(window.isClientApp != undefined)
            {
                $(document).ready(function(){
                    var newCategory = getField('generalCategory').value;
                    if (newCategory > 0)
                    {
                        $('#generalCategory').val(newCategory);
                        $(".operationSelect").val(newCategory);
                        $(".operationCategory").val(newCategory);
                    }
                    selectCore.init();
                });
            }
        </script>

        <div class="operationsListPagination">
            <html:hidden property="field(searchPage)" value="${searchPage}"/>
            <div class="align-center">
                <c:choose>
                    <c:when test="${searchPage > 0}">
                        <a class="active-arrow" href="#" onclick="setElement('field(searchPage)', ${searchPage-1}); changePage(); return false;"><div class="activePaginLeftArrow"></div></a>
                    </c:when>
                    <c:otherwise>
                        <div class="inactivePaginLeftArrow"></div>
                    </c:otherwise>
                </c:choose>
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
                <c:choose>
                    <c:when test="${resOnPage == 10}">
                        <span><div class="greenSelector"><span>10</span></div></span>
                        <a onclick="changeResOnPage(20); return false;" href="#">20</a>
                        <a onclick="changeResOnPage(50); return false;" href="#">50</a>
                    </c:when>
                    <c:when test="${resOnPage == 20}">
                        <a onclick="changeResOnPage(10); return false;" href="#">10</a>
                        <div class="greenSelector"><span>20</span></div>
                        <a onclick="changeResOnPage(50); return false;" href="#">50</a>
                    </c:when>
                    <c:when test="${resOnPage == 50}">
                        <a onclick="changeResOnPage(10); return false;" href="#">10</a>
                        <a onclick="changeResOnPage(20); return false;" href="#">20</a>
                        <div class="greenSelector"><span>50</span></div>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </c:if>
    <c:if test="${size == 0}">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="greenBold"/>
            <tiles:put name="data">
                <span class="text-dark-gray normal relative">
                    <bean:message bundle="financesBundle" key="message.empty.categoryAbstract"/>
                </span>
            </tiles:put>
        </tiles:insert>
    </c:if>
</html:form>