/**
 * ������ ��� ������ � �������� ������.
 * @see com.rssl.phizic.business.profile.addressbook.Contact
 */

function showDetailInfo(id)
{
    contactListManager.contacts[id].showDetailInfo();
}

function sendContactData(id)
{
    var contact = contactListManager.contacts[id];
    var a = new Array(3);
    a['id'] = contact.id;
    a['fullName'] = contact.fullName;
    a['phone'] = contact.phone;
    a['sberbankClient'] = '' + contact.sberbankClient;
    a['incognito'] = contact.incognito;
    a['cardNumber'] = contact.cardNumber;
    a['avatarPath'] = contact.avatarPath;
    window.opener.paymentReceiver.sendContactData(a, true);
    window.close();
}

function createTag(tag, attributes, data)
{
    var t = "<" + tag + " ";
    for (var atr in attributes)
    {
        t += atr + "=\"" + attributes[atr] + "\" ";
    }
    t += ">" + data + "</" + tag + ">";
    return t;
}

function showHideDeleted(show)
{
    var title = $("#deletedCntct").find(".contactTitle");
    var deletedHide = title.hasClass("hidden");
    if (deletedHide)
    {
        title.removeClass("hidden");
        $("#deletedCntctInner").show();
    }
    else if (!show)
    {
        title.addClass("hidden");
        $("#deletedCntctInner").hide();
    }
}

/**
 * ��������� ����������� ��������� �� ��������� ����.
 * @constructor
 */
var HintSetter = function(saveFunction, onChangeFunction, idPrefix, hintText, hintClass, onChangeCallBack)
{
    var self = this;

    self.saveFunction = saveFunction;
    self.onChangeFunction = onChangeFunction;
    self.idPrefix = idPrefix;
    self.hintText = hintText;

    self.hintClass = hintClass;
    self.onChangeCallBack = onChangeCallBack;

    /**
     * ��� �����.
     * @param id  �������� id
     * @param event
     */
    self.onChange = function(id, event)
    {
        if (self.onChangeFunction)
            self.onChangeFunction(id, true);
        event = event || window.event;
        var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
        if(code == 13)
        {
            if (self.saveFunction)
                self.saveFunction(id);
            return;
        }

        var el = $("#" + self.idPrefix + id);
        var newAlias = el.val();
        if (newAlias == self.hintText && el.hasClass(self.hintClass) && (code != 8 && code != 46 && code != "-500"))
        {
            el.val("");
            el.removeClass(self.hintClass);
            if (self.onChangeCallBack)
                self.onChangeCallBack(true);
        }
        else if ((el.val().length <= 1 || newAlias == self.hintText) && (code == 8 || code == 46 || code == "-500"))
        {
            el.val(self.hintText + (code != "-500" ? "_" : ""));
            el.addClass(self.hintClass);
            if (self.onChangeCallBack)
                self.onChangeCallBack(false);
        }
    }
};

var ABContact = function ()
{
    var self = this;

    self.id = 0;
    self.phone = "";
    self.fullName = "";
    self.sberbankClient = false;
    self.incognito = false;
    self.alias = "";
    self.avatarPath = "";
    self.avatarBlock = "";
    self.cardNumber = "";
    self.category = "";
    self.trusted = false;
    self.frequencyP2P = 0;
    self.status = "";
    self.cardType = "";

    self.detailInfo = "";
    /**
     * html ������.
     * @type {string}
     */
    self.contactInfo;
    self.contactInfoHtml = "";
    self.contactDeleteInfoHtml = "";
    self.contactDetailInfoHtml = "";
    self.contactGroup = "";

    self.isShowDetailInfo = false;
    self.categoryChanged = false;
    self.clicked = 0;
    self.editAlias = false;

    self.aliasAnim = "";
    self.isShowLinkConfirmContactAndroid = false;

    self.rebuildContactInfo = function()
    {
        self.contactInfoHtml = createTag("div", {"class" : "contactLeft css3"},
                createTag("div", {"class":"contactRight"},
                        createTag("div", {"class":"contact"},
                                self.avatarBlock+
                                createTag("div", {"class": "float contactInfo fixContactWidth"},
                                    createTag("div", {"class":"fixContactWidth"}, self.fullName) +
                                    createTag("div", {"class":"alias fixContactWidth", "id":"cntAlias" + self.id}, self.alias)
                                ) +
                                (self.status == "DELETE" ? createTag("div", {"class":"restoreCnt contactInfo", "onclick":"contactListManager.restoreContact(" + self.id + ");"}, "") : "") +
                                createTag("div", {"class":"phone contactInfo"},
                                        createTag("span", {"class":"country"}, "+7&nbsp;") + self.phone
                                ) +
                                createTag("div", {"class" : "clear"}, "")
                        )
                )
        );

        return self.contactInfoHtml;
    };

    self.rebuildDeleteContactInfo = function()
    {
        self.contactDeleteInfoHtml = createTag("div", {"class" : "contactLeft deleted"},
                createTag("div", {"class" : "contactRight"},
                        createTag("div", {"class" : "contact"},
                                self.avatarBlock +
                                        createTag("div", {"class" : "float contactInfo"}, "������� ��������� � �������") +
                                        createTag("div", {"class" : "floatRight restore", "onclick" : "contactListManager.restoreContact(" + self.id + ");"}, "������������ � ������") +
                                        createTag("div", {"class" : "clear"}, "")
                        )
                )
        );

        return self.contactDeleteInfoHtml;
    };

    self.rebuildContactDetailInfo = function()
    {
        self.contactDetailInfoHtml = self.detailInfo.replace("%%---%%",
                createTag("div", {"class":"detailFIO"},
                //������������ ��������.
                createTag("div", {"class":"contactInfo"}, self.fullName) +
                //���������������� ��� ��������.
                createTag("div", {"class":"relative"},
                createTag("div",
                        {"class":"alias fixContactWidth","id":"cntAlias" + self.id,"onclick":"contactListManager.editAlias(" + self.id + ");"},
                        (self.alias ? self.alias : ADD_ALIAS) + createTag("span", {"class":"pen","id":"aliasPen"+self.id}, "") + createTag("span", {"class":"saved", "id":"aliasSaved"+self.id,"style":"display:none;"}, "")
                ))+
                    //�������������� ����������������� �����.
                    createTag("div",
                            {
                                "class":"alias alias-edit","id":"cntAliasEdit" + self.id,"style":"display:none;",
                                "onclick":"contactListManager.editAlias(" + self.id + ");"
                            },
                            createTag("input", {
                                        "type":"text","id":"cntAliasEditInput" + self.id,
                                        "name":"cntAliasEdit"+self.id,
                                        "class":(self.alias ? "" : "addNewAlias"),
                                        "value":(self.alias ? self.alias : ADD_ALIAS),
                                        "onblur":"contactListManager.onSaveAlias(" + self.id + ");",
                                        "onkeydown":"contactListManager.onChangeAlias(" + self.id + ", event);",
                                        "onkeyup":"contactListManager.resetAliasInterval(" + self.id + ");"},
                                    "")+
                                    createTag("span", {"class":"wait", "id":"aliasWait" + self.id, "style":"display:none;"}, "")
                    )
                )+
                //��������� �� ������ ����������
                createTag("div", {"class":"errorAlias", "id":"saveError"+self.id},"")+
                //��������/������ �� ����������.
                createTag("div", {"onclick":"contactListManager.changeCategory(" + self.id + ");","class":"bookmarkCntct " + (self.category == "BOOKMARK" ? "" : "inactiveBookmark")}, "") +
                //����� �������� ��� ����� ������-��������.
                createTag('div', {"class": 'addr-book-detail-phone-card'},
                    createTag("div", {"class":"phone"},
                            createTag("span", {"class":"country"}, "+7&nbsp;") + self.phone
                    ) +
                    //����� �����, ���� ����
                    (self.cardNumber ? createTag("div", {"class":"cardNumber"},
                        createTag("span", {"class":(self.cardType == '4' ? "visaCrd" : (self.cardType == '5' || self.cardType == '6' ? "masterCardCrd" : "defCrd"))}, "") +
                        self.cardNumber
                    ) : "")
                ) +
                //������� �������
                createTag("div", {"onclick":"contactListManager.deleteContact(" + self.id + ");", "class":"removeContact"}, "") +
                //���������� ���������� ��� android-���������.
                (self.isShowLinkConfirmContactAndroid ?
                        createTag("div", {"class":"trusted"},
                                createTag("div", {"class":"robot float " + (self.trusted ? "active":"")},"") +
                                createTag("div", {"class":"float addr-book-detail-trusted-text"},
                                        (
                                                self.trusted ?
                                                '�������� � Android-��������� ���������<br><a href="#" onclick="contactListManager.trusted('+ self.id +'); return false;" class="addr-book-detail-switch-a">���������</a>' :
                                                '<a href="#" onclick="contactListManager.trusted(' + self.id + '); return false;">��������� �������� ����� ��������<br /> � Android-���������</a>'
                                        )
                                ) +
                                createTag("div", {"class" : "clear"}, "")
                        )
                : "")
        );
        return self.contactDetailInfoHtml;
    };

    /**
     * �������� dom
     * @param parentElement ������������ �������.
     */
    self.create = function(parentElement, isDictionary)
    {
        if (self.status == 'HIDE')
            return;

        var tempDiv = document.createElement("div");
        tempDiv.className = 'relative';
        tempDiv.setAttribute("onclick", isDictionary ? "sendContactData(" + self.id + ")" : "showDetailInfo(" + self.id + ");");
        tempDiv.setAttribute("id", "el" + self.id);
        tempDiv.innerHTML = self.rebuildContactInfo();
        self.rebuildContactDetailInfo();
        self.contactInfo = tempDiv;
        contactListManager.addContact(self);
    };

    self.getStatus = function()
    {
        return self.status == 'ACTIVE' ? (self.category == 'NONE' ? OTHER_GROUP : BOOKMARK_GROUP) : DELETED_GROUP
    };

    self.showDetailInfo = function ()
    {
        if (!self.contactGroup.main)
            return;

        if (self.editAlias)
            return;

        if (self.clicked)
        {
            self.clicked--;
            return;
        }

        if (self.categoryChanged)
        {
            contactListManager.resetContact(self);
        }

        self.isShowDetailInfo = !self.isShowDetailInfo;
        if (self.isShowDetailInfo)
            self.clicked = 1;

        var changed = false;

        if (IE())
        {
            $("#el" + self.id)[0].innerHTML = self.isShowDetailInfo ? self.contactDetailInfoHtml : self.contactInfoHtml;
        }
        else
        {
            var animator = new Animator(200, function(p){
                if (p < 0.5)
                {
                    $("#el" + self.id).css({"opacity":(0.5-p)*2});
                    return;
                }
                else if (!changed)
                {
                    $("#el" + self.id)[0].innerHTML = self.isShowDetailInfo ? self.contactDetailInfoHtml : self.contactInfoHtml;
                    changed = true;
                }
                $("#el" + self.id).css({"opacity":(p - 0.5)*2});
            });
            animator.start();
        }
    };
};

var BOOKMARK_GROUP = "bookmarkCntct";
var OTHER_GROUP = "otherCntct";
var DELETED_GROUP = "deletedCntctInner";

var ADD_ALIAS = "�������� ��������� ��� ���������";

/**
 * �������� ������� ���������.
 *
 * @param groups ������ ��������� (�����).
 * @constructor
 */
var ContactListManager = function()
{
    var self = this;

    self.inited = false;
    self.groups = [];
    self.groups[BOOKMARK_GROUP] = new ContactList(BOOKMARK_GROUP, true);
    self.groups[OTHER_GROUP] = new ContactList(OTHER_GROUP, true);
    self.groups[DELETED_GROUP] = new ContactList(DELETED_GROUP, false);

    self.contacts = [];
    self.hintSetter = new HintSetter(function(id){self.onSaveAlias(id);}, function(id){self.resetAliasInterval(id, true);}, "cntAliasEditInput", ADD_ALIAS, "addNewAlias");

    self.addContact = function(contact, async)
    {
        var cg = self.groups[contact.getStatus()];
        self.init();
        cg.addContact(contact);
        if (!async)
        {
            if (status == BOOKMARK_GROUP || (self.groups[BOOKMARK_GROUP].numOfContact > 0 && self.groups[OTHER_GROUP].numOfContact > 0))
            {
                self.groups[BOOKMARK_GROUP].showTitle(true);
                self.groups[OTHER_GROUP].showTitle(true, "������ ��������");
            }
            else if (self.groups[BOOKMARK_GROUP].numOfContact == 0 && self.groups[OTHER_GROUP].numOfContact == 0)
            {
                self.groups[BOOKMARK_GROUP].showTitle(false);
                self.groups[OTHER_GROUP].showTitle(true, "������ ���������");
            }
            else
            {
                self.groups[OTHER_GROUP].showTitle(true, "������ ���������");
            }
        }
        if (cg.numOfContact > 0)
            cg.show();
        else
            cg.hide();

        self.contacts[contact.id] = contact;
    };

    self.hideAllContacts = function()
    {
        for (var cntct in self.contacts)
            if (self.contacts[cntct].isShowDetailInfo)
                self.contacts[cntct].showDetailInfo();
    };

    /**
     * ����������, ����� ���������� �������� ������������ ��������� � �������� �����.
     * @param contact
     * @param async
     */
    self.resetContact = function(contact, async)
    {
        if (!async) {
            for (var gr in self.groups)
            {
                var group = self.groups[gr];
                if (!group.contacts)
                    continue;

                var itemIndex = -1;
                for (var i = 0; i < group.contacts.length; i++)
                {
                    if (group.contacts[i] == contact)
                    {
                        itemIndex = i;
                        break;
                    }
                }
                if (itemIndex >= 0) {
                    group.getDom().remove(contact.contactInfo);
                    group.contacts.splice(itemIndex, 1);
                    group.numOfContact--;
                    if (group.contacts.length == 0)
                    {
                        group.hide();
                    }
                }
            }
            self.groups[OTHER_GROUP].showTitle(false);
        }
        self.addContact(contact, async);
        if (!async)
            self.sort(self.sortByAlphabet);
    };

    /**
     * ���������� ������ ���������.
     * @param byAlphabet
     */
    self.sort = function(byAlphabet)
    {
        self.groups[BOOKMARK_GROUP].sort(byAlphabet);
        self.groups[OTHER_GROUP].sort(byAlphabet);
        self.groups[DELETED_GROUP].sort(byAlphabet);
        self.sortByAlphabet = byAlphabet;
    };

    self.sortByAlphabet = false;

    self.init = function()
    {
        if (!self.inited)
        {
            self.groups[BOOKMARK_GROUP].init();
            self.groups[OTHER_GROUP].init();
            self.groups[DELETED_GROUP].init();
            self.inited = true;
        }
    };

    /**
     * ����� ���������: ��������� - ������.
     * @param id
     * @returns {boolean}
     */
    self.changeCategory = function (id)
    {
        self.contacts[id].clicked = 2;
        var cat  = self.contacts[id].category == "NONE" ? "BOOKMARK" : "NONE";
        ajaxQuery("id="+id+"&category="+(self.contacts[id].category), "/PhizIC/private/userprofile/addressbook/editContactCategory.do", function(data) {
            if(data.category == cat)
            {
                self.contacts[id].category = cat;
                self.contacts[id].rebuildContactInfo();
                $("#el" + id)[0].innerHTML = self.contacts[id].rebuildContactDetailInfo();
                self.contacts[id].categoryChanged = true;
            }
            else
            {
                self.contacts[id].categoryChanged = false;
            }
        }, "json", false);

        return false;
    };

    /**
     * ������ �������������� ������.
     * @param id
     */
    self.editAlias = function(id)
    {
        var lastEdited = self.contacts[id].editAlias;
        self.contacts[id].editAlias = true;
        self.contacts[id].clicked = 2;
        if (lastEdited)
            $("#cntAliasEditInput" + id).focus();
        else
        {
            $("#cntAlias" + id).hide();
            $("#cntAliasEdit" + id).show();
            $("#cntAliasEditInput" + id).focus().select();
        }
        //����� 1400ms ����������� �������� ���������� ������.
        self.resetAliasInterval(id);
    };

    /**
     * ���������� ��������, ����� �������� ���������� �������������� �����.
     * @param id
     */
    self.resetAliasInterval = function(id, notShow)
    {
        if (self.contacts[id].aliasAnim)
        {
            clearInterval(self.contacts[id].aliasAnim);
            self.contacts[id].aliasAnim = null;
        }
        if (!notShow && !$("#cntAliasEdit"+id).hasClass("onerror")) {
            self.contacts[id].aliasAnim = setInterval(function() {
                self.onSaveAlias(id);
            }, 1400);
        }
    };

    /**
     * �� ����� ���������� ������.
     * @param id
     */
    self.onSaveAlias = function(id)
    {
        if (self.contacts[id].aliasAnim)
        {
            clearInterval(self.contacts[id].aliasAnim);
            self.contacts[id].aliasAnim = null;
        }
        $("#aliasWait"+id).show();
        $("#cntAliasEditInput" + id)[0].setAttribute("disabled","true");
        var newAlias = $("#cntAliasEditInput" + id).val();
        if (ADD_ALIAS == newAlias)
            newAlias = "";
        var aliasParam = decodeURItoWin(newAlias);
        ajaxQuery("id="+id+"&alias=" + aliasParam, "/PhizIC/private/userprofile/addressbook/editContactAlias.do", function(data) {
            //��������� ���� �� ������
            if (data.error)
            {
                $("#saveError"+id)[0].innerHTML = "�������� ������ ��� ���������� ��������";
                var animator = new Animator(2200, function(p)
                {
                    $("#saveError"+id).css({"opacity":Math.sin(Math.sqrt(1 - p)*Math.PI/2)});
                    $("#cntAliasEdit"+id).addClass("onerror");
                    self.contacts[id].editAlias = false;
                    self.editAlias(id);
                });
                animator.start();
            }
            else
            {
                var elem = $("#cntAlias" + id)[0];
                elem.innerHTML = (data.alias == "" ? ADD_ALIAS : data.alias) + elem.innerHTML.substring(elem.innerHTML.indexOf("<"));
                self.contacts[id].alias = data.alias;
                self.contacts[id].rebuildContactInfo();
                self.contacts[id].rebuildContactDetailInfo();
                var animator = new Animator(400, function(p)
                {
                    $("#aliasSaved"+id).css({"opacity":1 - p});
                },
                function()
                {
                    $("#aliasSaved"+id).hide();
                    $("#aliasPen"+id).show();
                });
                $("#aliasPen"+id).hide();
                $("#aliasSaved"+id).show();
                animator.start();
                $("#cntAlias" + id).show();
                $("#cntAliasEdit" + id).hide();
                self.contacts[id].editAlias = false;
                self.contacts[id].clicked = 0;
            }
            $("#aliasWait"+id).hide();
            $("#cntAliasEditInput" + id)[0].removeAttribute("disabled");
        }, "json", false);
    };

    /**
     * ��� ����� ������.
     * @param id
     */
    self.onChangeAlias = function(id, event)
    {
        self.hintSetter.onChange(id, event);
        $("#cntAliasEdit"+id).removeClass("onerror");
    };

    /**
     * �������� ��������
     * @param id
     */
    self.deleteContact = function(id)
    {
        self.contacts[id].clicked = 2;
        ajaxQuery("id=" + id + "&remove=1", "/PhizIC/private/userprofile/addressbook/deleteContact.do", function(data) {
            if(!data.error)
            {
                var cnt = self.contacts[id];
                cnt.status = "DELETE";
                cnt.rebuildContactInfo();
                cnt.contactInfo.innerHTML = cnt.contactInfoHtml;
                cnt.isShowDetailInfo = false;
                $("#el" + id).replaceWith("<div id=\"eldel" + cnt.id + "\">" + cnt.rebuildDeleteContactInfo() + "</div>");
                self.resetContact(cnt, true);
                showHideDeleted(true);
                var inter = setInterval(function() {
                    $("#eldel" + cnt.id).remove();
                    clearInterval(inter);
                    cnt.clicked = 0;
                    self.resetContact(cnt, false);
                }, 1600);
            }
        }, "json", false);

    };

    /**
     * �������������� ��������.
     * @param id
     */
    self.restoreContact = function(id)
    {
        ajaxQuery("id=" + id + "&remove=0", "/PhizIC/private/userprofile/addressbook/restoreContact.do", function(data) {
            if(!data.error)
            {
                var cnt = self.contacts[id];
                cnt.clicked = 0;
                $("#eldel" + cnt.id).remove();
                cnt.status = "ACTIVE";
                cnt.rebuildContactInfo();
                cnt.contactInfo.innerHTML = cnt.contactInfoHtml;
                cnt.isShowDetailInfo = false;
                self.resetContact(cnt, false);
            }
        }, "json", false);
    };

    /**
     * ��������� �������� ����������-������������.
     * @param id
     */
    self.trusted = function(id)
    {
        var cnt = self.contacts[id];
        cnt.clicked = 2;
        ajaxQuery("id=" + id + "&trusted=" + cnt.trusted, "/PhizIC/private/async/userprofile/addressbook/editContactTrust.do", function(data) {
            if (data != 'next')
            {
                var actualToken = $(data).find('input[name=org.apache.struts.taglib.html.TOKEN]').val();
                if (actualToken != undefined)
                    $('input[name=org.apache.struts.taglib.html.TOKEN]').val(actualToken);
                confirmOperation.lock = false;
                $("#oneTimePasswordWindow").html(data);
                cnt.clicked = 2;
                win.open('oneTimePasswordWindow');
            }
            else
            {
                self.contacts[id].trusted = !self.contacts[id].trusted;
                cnt.contactInfo.innerHTML = cnt.rebuildContactDetailInfo();
            }
        }, null, true);
    };

    /**
     *  ����� �� ����� ��������.
     *
     * @param text ����� ��� ������.
     * @returns {string}
     */
    self.search = function(text)
    {
        var txt = text.toLowerCase();
        var found = "";
        var inBookmark = false;
        var inOther = false;
        var inDeleted = false;
        for (var cntct in self.contacts)
        {
            if (!self.contacts[cntct].fullName)
                continue;

            if (self.contacts[cntct].fullName.toLowerCase().indexOf(txt) < 0 &&
                       (!self.contacts[cntct].alias || (self.contacts[cntct].alias &&
                       self.contacts[cntct].alias.toLowerCase().indexOf(txt) < 0)))
            {
                $("#el" + cntct).hide();
            }
            else
            {
                $("#el" + cntct).show();
                if (self.contacts[cntct].status == "DELETE")
                    inDeleted = true;
                else if (self.contacts[cntct].category == "BOOKMARK")
                    inBookmark = true;
                else
                    inOther = true;
                if (txt)
                    found += (found == "" ? "" : "|") + cntct;
            }
        }

        self.showHideGroups(inBookmark, inOther, inDeleted);

        return found;
    }

    self.showHideGroups = function(inBookmark, inOther, inDeleted)
    {
        if (!inBookmark)
            self.groups[BOOKMARK_GROUP].hide();
        else
            self.groups[BOOKMARK_GROUP].show();

        if (!inOther)
            self.groups[OTHER_GROUP].hide();
        else
            self.groups[OTHER_GROUP].show();

        if (!inDeleted)
            self.groups[DELETED_GROUP].hide();
        else
            self.groups[DELETED_GROUP].show();
    }

    self.showOnly = function(ids)
    {
        var inBookmark = false;
        var inOther = false;
        var inDeleted = false;
        for (var cntct in self.contacts)
        {
            if (ids[cntct] == "1")
            {
                $("#el" + cntct).show();
                if (self.contacts[cntct].status == "DELETE")
                    inDeleted = true;
                else if (self.contacts[cntct].category == "BOOKMARK")
                    inBookmark = true;
                else
                    inOther = true;
            }
            else
            {
                $("#el" + cntct).hide();
            }
        }

        self.showHideGroups(inBookmark, inOther, inDeleted);
    }
};

/**
 * ������ ���������.
 */

var ContactList = function(groupName, main)
{
    var self = this;
    self.groupName = groupName;
    self.main = main;
    self.numOfContact = 0;
    self.contacts = [];

    self.thiS = "";

    self.getDom = function()
    {
        if (self.thiS == "")
            self.thiS = $(document).find("#" + self.groupName);
        return self.thiS;
    };

    self.addContact = function (contact)
    {
        self.contacts[self.numOfContact++] = contact;
        self.getDom()[0].appendChild(contact.contactInfo);
        contact.contactGroup = self;
    };

    self.sort = function (byAlphabet)
    {
        self.contacts.sort(function(a, b) {
            if (byAlphabet)
            {
                var aa = a.fullName.toLowerCase();
                var bb = b.fullName.toLowerCase();
                if (aa > bb)
                    return 1;
                if (aa < bb)
                    return -1;
                return 0;
            }
            else
            {
                return b.frequencyP2P - a.frequencyP2P;
            }
        });

        for (var i = 0; i < self.contacts.length; i++)
        {
            try
            {
                self.getDom()[0].removeChild(self.contacts[i].contactInfo);
            }
            catch (e)
            {}
        }

        for (i = 0; i < self.contacts.length; i++)
        {
            self.getDom()[0].appendChild(self.contacts[i].contactInfo);
        }
    };

    self.show = function()
    {
        if (self.groupName == DELETED_GROUP)
            $("#deletedCntct").show();
        else
            self.getDom().show();
    };

    self.hide = function()
    {
        if (self.groupName == DELETED_GROUP)
            $("#deletedCntct").hide();
        else
            self.getDom().hide();
    };

    self.showTitle = function(show, name)
    {
        if (!main)
            return;
        var ct = (self.groupName == DELETED_GROUP ? $("#deletedCntct") : self.thiS).find(".whiteBack");
        if (show)
        {
            ct.show();
            if (name)
                ct.html(name);
        }
        else
            ct.hide();
    };

    self.init = function()
    {
        self.hide();
        self.showTitle(false);
    };
};

var contactListManager = new ContactListManager();
