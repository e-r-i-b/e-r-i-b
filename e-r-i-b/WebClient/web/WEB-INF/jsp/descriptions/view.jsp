<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${ExtendedDescriptionDataForm}"/>
<tiles:insert definition="empty">
    <tiles:put name="data" type="string">
        <div id="reference">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <div class="description-data">
                        <div class="content">
                            ${form.content}
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>    