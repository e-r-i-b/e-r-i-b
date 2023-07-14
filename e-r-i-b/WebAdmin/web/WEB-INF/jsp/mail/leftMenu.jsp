<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<script type="text/javascript">

    function subMenuClickCallBack(action, url)
    {
        <c:choose>
            <c:when test="${needSave eq 'true'}">
                if (!isDataChanged())
                {
                    if (!redirectResolved())
                        return false;
                    loadNewAction('lmi'+url,'lmInset');
                    return window.location=action;
                }

                if (!confirm("��������� ��������� � ������� �� ������ ��������?"))
                {
                    clearLoadMessage();
                    reinitField();
                    return false;
                }

                $('[name="$$forceRedirect"]').val(url);
                findNavigationButton().click(url);
                return false;
            </c:when>
            <c:otherwise>
                goTo(action);
            </c:otherwise>
        </c:choose>
    }
</script>

<tiles:insert definition="leftMenuInset" service="MailManagment">
	<tiles:put name="enabled" value="${submenu != 'MailList'}"/>
	<tiles:put name="text"    value="������ �������� �����"/>
	<tiles:put name="title"   value="������ �������� �����"/>
	<tiles:put name="forceOnclick" value="true"/>
	<tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/list')}', '/mail/list.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="MailManagment">
	<tiles:put name="enabled" value="${submenu != 'SentMailList'}"/>
	<tiles:put name="text"    value="������ ������������ �����"/>
	<tiles:put name="title"   value="������ ������������ �����"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/sentList')}', '/mail/sentList.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="MailManagment">
	<tiles:put name="enabled" value="${submenu != 'ArhMailList'}"/>
	<tiles:put name="text"    value="������ ��������� �����"/>
	<tiles:put name="title"   value="������ ��������� �����"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/archive')}', '/mail/archive.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="MailManagment">
	<tiles:put name="enabled" value="${submenu != 'MailStatistics'}"/>
	<tiles:put name="text"    value="���������� ��������� ��������� ��������"/>
	<tiles:put name="title"   value="���������� ��������� ��������� ��������"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/statistics')}', '/mail/statistics.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="MailArchiveManagment">
	<tiles:put name="enabled" value="${submenu != 'EditArchivingMail'}"/>
	<tiles:put name="text"    value="���������"/>
	<tiles:put name="title"   value="���������"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/archiving/edit')}', '/mail/archiving/edit.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="MailArchiveManagment">
	<tiles:put name="enabled" value="${submenu != 'UnArchivingMail'}"/>
	<tiles:put name="text"    value="������������"/>
	<tiles:put name="title"   value="������������"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/archiving/unarchive')}', '/mail/archiving/unarchive.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="MailMessagesManagment">
	<tiles:put name="enabled" value="${submenu != 'MessagesEdit'}"/>
	<tiles:put name="text"    value="��������� ����� ��������� � ������ ������"/>
	<tiles:put name="title"   value="��������� ����� ��������� � ������ ������"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/stmessages/edit')}', '/mail/stmessages/edit.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="SubjectManagment">
	<tiles:put name="enabled" value="${submenu != 'MailSubjects'}"/>
	<tiles:put name="text"    value="���������� ������� ���������"/>
	<tiles:put name="title"   value="���������� ������� ���������"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/subjects/list')}', '/mail/subjects/list.do')"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="ContactCenterAreaManagment">
	<tiles:put name="enabled" value="${submenu != 'ContactCenterArea'}"/>
	<tiles:put name="text"    value="���������� �������� ��"/>
	<tiles:put name="title"   value="���������� �������� ��"/>
    <tiles:put name="forceOnclick" value="true"/>
    <tiles:put name="onclick" value="subMenuClickCallBack('${phiz:calculateActionURL(pageContext, '/mail/area/list')}', '/mail/area/list.do')"/>
</tiles:insert>

