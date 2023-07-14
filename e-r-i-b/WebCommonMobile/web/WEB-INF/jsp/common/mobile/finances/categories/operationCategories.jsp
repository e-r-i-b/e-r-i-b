<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/finances/operationCategories">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="financesStructure" flush="false">
        <c:if test="${not form.hasErrors}">
        <tiles:put name="data">
            <%--фильтр по картам--%>
            <c:if test="${not empty form.element}">
                <cardFilter>
                    <tiles:insert page="treeNode.jsp" flush="false">
                        <tiles:put name="element" beanName="form" beanProperty="element"/>
                    </tiles:insert>
                </cardFilter>
            </c:if>
            <%--список категорий--%>
            <sl:collection id="categoryAbstract" property="outcomeOperations" model="xml-list" title="categories">
                <%--@elvariable id="categoryAbstract" type="com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract"--%>
                <sl:collectionItem title="category">
                    <id>${categoryAbstract.id}</id>
                    <name><c:out value="${categoryAbstract.name}"/></name>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="nationalSum"/>
                        <tiles:put name="decimal" value="${categoryAbstract.categorySum}"/>
                        <tiles:put name="currencyCode" value="RUB"/>
                    </tiles:insert>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="visibleSum"/>
                        <tiles:put name="decimal" value="${categoryAbstract.visibleSum}"/>
                        <tiles:put name="currencyCode" value="RUB"/>
                    </tiles:insert>
                    <c:if test="${categoryAbstract.budgetSum != null && (!categoryAbstract.avgBudget || categoryAbstract.budgetSum != 0)}">
                        <budget>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="budgetSum"/>
                                <tiles:put name="decimal" value="${categoryAbstract.budgetSum}"/>
                                <tiles:put name="currencyCode" value="RUB"/>
                            </tiles:insert>
                            <isAvg>${categoryAbstract.avgBudget}</isAvg>
                        </budget>
                    </c:if>

                    <incomeType>${categoryAbstract.incomeType ? 'income' : 'outcome'}</incomeType>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
        </c:if>
    </tiles:insert>
</html:form>