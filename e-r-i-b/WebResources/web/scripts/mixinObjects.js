/**
 * ����� ��� �������� ������� ������ ������ � ������ ��� ������������
 *
 * @param from ����� ��������
 * @param to ����� �����������
 */
function mixin(from, to)
{
    for (var property in from)
    {
        if (typeof from[property] == 'function')
        {
            to[property] = from[property];
        }
    }
}

/**
 * ������� �������
 * @constructor
 */
function UIElementBinding() {}

/**
 * @param parent
 * @param {String} className
 * @param {Node} child
 */
UIElementBinding.prototype.getElementByClassName = function(parent, className, child)
{
    if (!parent)
    {
        throw new Error('�� ������ ������� �������� ������.');
    }

    if (!className || !(typeof className === 'string'))
    {
        throw new Error('�������� className �������� ������������ � ������ ����� ��� string.');
    }

    var rootElement = (child || parent);

    if (rootElement.className)
    {
        var names = rootElement.className.split(' ');

        for (var i = 0; i < names.length; i++)
        {
            if (names[i] == className)
            {
                return rootElement;
            }
        }
    }

    var element = null;

    if (rootElement.hasChildNodes())
    {
        var nodes = rootElement.childNodes;

        for (var j=0; j<nodes.length; j++)
        {
            element = this.getElementByClassName(parent, className, nodes[j]);

            if (element)
            {
                break;
            }
        }
    }

    return element;
};

/**
 * ���� ���������� ��������� �����-���� �������� ����� ����� ���������� ������� ������ �� � ������ ������� �������
 * ����� ����������� �������� ����� �������� ����� onBind2.
 *
 * @param parent html ������� �������� ������ � ������� ����� �������������� �����
 * @param elements ������ ���������� ������ ��������� � ������� ����� �������������� ��������
 */
UIElementBinding.prototype.bind2 = function(parent, elements)
{
    if (!parent)
    {
        throw new Error('�������� ������� bind ������ ���� ������.');
    }

    if (!(parent.nodeType && parent.nodeType == 1))
    {
        throw new Error('������ ����� ��������� ������ � html ��������.');
    }

    for (var field_1 in elements)
    {
        if (elements.hasOwnProperty(field_1))
        {
            elements[field_1] = this.getElementByClassName(parent, field_1, null);
        }
    }

    /*
     * �������� ������������ html ������������� �������
     */
    for (var field_2 in elements)
    {
        if (elements.hasOwnProperty(field_2))
        {
            if (!elements[field_2])
            {
                throw new Error('HTML ������������� �� ������������� �������.');
            }
        }
    }

    if (this['onBind2'] !== undefined)
    {
        this['onBind2'](parent);
    }

    return this;
};

/**
 * ������������� ������ ��� ����������� ��� ���������
 * @constructor
 */
function ObjectUtils() {}

/**
 * @param to    ������ � ������� ����� ������������� ������
 * @param from  ������ �� �������� ����� ������������� ������ �������
 * @param force ��������, ��� � ����������� ������� ����� ������������ ��� �������� ��������� � ������� ����������,
 * ���� ���� �������� ���������� ����������� � null
 */
ObjectUtils.prototype.reWriteObject = function(to, from, force)
{
    to   = (!to   || to   === undefined) ? {} : to;
    from = (!from || from === undefined) ? {} : from;

    for (var property in from)
    {
        if (from.hasOwnProperty(property))
        {
            var value = from[property];

            if (value === undefined || (!value && !force))
            {
                continue;
            }

            if (typeof value == 'object')
            {
                if (value instanceof Array)
                {
                    to[property] = value;
                }
                else
                {
                    this.reWriteObject(to[property], from[property], force);
                }
            }
            else
            {
                to[property] = from[property];
            }
        }
    }

    return to;
};

/**
 * IE 7 �� ������������ trim! O.o
 * @constructor
 */
ObjectUtils.prototype.StringTrimSupport = function ()
{
    if (typeof String.prototype.trim !== 'function')
    {
        String.prototype.trim = function()
        {
            return this.replace(/^\s+|\s+$/g, '');
        };
    }
};