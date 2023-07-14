<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/debitcard/claims/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="cardsBundle"/>

    <tiles:insert definition="cardProductList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.card.debit.claim.list" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="DebitCardClaims"/>
        <tiles:put name="filter" type="string">
            <script type="text/javascript">
                function disableSearchInput(searchByPhone)
                {
                    ensureElementByName('filter(phone)').disabled = !searchByPhone;
                    ensureElementByName('filter(login)').disabled = searchByPhone;
                }

                doOnLoad(function()
                {
                    disableSearchInput(${form.filters.searchByPhone});
                });
            </script>

            <%--row 1--%>
            <%--Поиск по--%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.findBy"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="data">
                    <html:radio property="filter(searchByPhone)" value="true" styleId="byPhone" name="form" onchange="disableSearchInput(true)"/><label for="byPhone"><bean:message bundle="${bundle}" key="filter.findBy.phone"/></label><br/>
                    <html:radio property="filter(searchByPhone)" value="false" styleId="byLogin" name="form" onchange="disableSearchInput(false)"/><label for="byLogin"><bean:message bundle="${bundle}" key="filter.findBy.login"/></label>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"/>
            <tiles:insert definition="filterEmptytField" flush="false"/>

            <%--row 2--%>
            <%--Номер телефона--%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.phone"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="phone"/>
                <tiles:put name="size" value="15"/>
                <tiles:put name="maxlength" value="11"/>
            </tiles:insert>

            <%--Логин гостя--%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.login"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="login"/>
                <tiles:put name="size" value="37"/>
                <tiles:put name="maxlength" value="32"/>
            </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"/>

            <%--row 3--%>
            <%--Период поиска--%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.period.creation.date"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <bean:message bundle="${bundle}" key="filter.creation.date.from"/>
                    &nbsp;
                    <span>
                        <input type="text"
                               size="10" name="filter(fromDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(fromDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                    &nbsp;<bean:message bundle="${bundle}" key="filter.creation.date.to"/>&nbsp;
                    <span>
                        <input type="text"
                               size="10" name="filter(toDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(toDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="DebitCardClaims"/>
                <tiles:put name="text">
                    <bean:message key="title.card.debit.claim.list" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="claim" model="list" property="data" bundle="${bundle}">
                        <c:set var="id" value="${claim[0]}"/>
                        <c:set var="viewLink" value="/debitcard/claims/view.do&id=${id}"/>

                        <sl:collectionItem title="label.number">
                            <phiz:link action="/debitcard/claims/view">
                                <phiz:param name="id" value="${id}"/>
                                ${id}
                            </phiz:link>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.creationDate">
                            <sl:collectionItemParam id="value">
                                ${phiz:formatDateToStringOnPattern(claim[1], 'HH:mm dd.MM.yyyy')}
                            </sl:collectionItemParam>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.phone" value="${claim[2]}"/>

                        <sl:collectionItem title="label.owner.FIO">
                            <sl:collectionItemParam id="value">
                                ${claim[5]} ${claim[4]} ${fn:substring(claim[3], 0, 1)}.
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">
                    <bean:message key="message.empty.list" bundle="${bundle}"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>