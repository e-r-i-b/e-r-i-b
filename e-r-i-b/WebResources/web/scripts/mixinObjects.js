/**
 * Метод для переноса методов одного класса в другой без наследования
 *
 * @param from класс источник
 * @param to класс потребитель
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
 * Готовим примесь
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
        throw new Error('Не указан элемент верхнего уровня.');
    }

    if (!className || !(typeof className === 'string'))
    {
        throw new Error('Аргумент className является обязательным и должен иметь тип string.');
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
 * Если необходимо выполнить какое-либо действие сразу после выполнения данного метода то в классе объекта который
 * будет расширяться примесью нужно добавить метод onBind2.
 *
 * @param parent html элемент верхнего уровня в котором будет осуществляться поиск
 * @param elements объект содержащий список элементов к которым будет осуществляться привязка
 */
UIElementBinding.prototype.bind2 = function(parent, elements)
{
    if (!parent)
    {
        throw new Error('Аргумент функции bind должен быть указан.');
    }

    if (!(parent.nodeType && parent.nodeType == 1))
    {
        throw new Error('Объект можно привязать только к html элементу.');
    }

    for (var field_1 in elements)
    {
        if (elements.hasOwnProperty(field_1))
        {
            elements[field_1] = this.getElementByClassName(parent, field_1, null);
        }
    }

    /*
     * Проверка соответствия html представления объекту
     */
    for (var field_2 in elements)
    {
        if (elements.hasOwnProperty(field_2))
        {
            if (!elements[field_2])
            {
                throw new Error('HTML представление не соответствует объекту.');
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
 * Предоставляет методы для манипуляции над объектами
 * @constructor
 */
function ObjectUtils() {}

/**
 * @param to    объект в котором будет производиться замена
 * @param from  объект из которого будет производиться чтение свойств
 * @param force означает, что в разширяемом объекте будут перезаписаны все свойства указанные в объекте поставщике,
 * даже если свойства поставщика выставленны в null
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
 * IE 7 не поддерживает trim! O.o
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