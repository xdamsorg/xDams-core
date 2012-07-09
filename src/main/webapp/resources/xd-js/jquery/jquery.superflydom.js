/**
 * jQuery Plugin SuperFlyDOM v0.9g
 *
 * Create DOM elements on the fly and automatically append or prepend them to another DOM object.
 * There are also template functions (tplAppend and tplPrepend) that can take a JSON-formatted
 * complex HTML structure, apply a dataset, and therefore add siblings way faster
 * 
 * This plugin is built off of FlyDom 3.0.8, by dohpaz
 * [http://dohpaz.mine.nu/jquery/jquery.flydom.html], who was inspired by "Oslow"
 * [http://mg.to/2006/02/27/easy-dom-creation-for-jquery-and-prototype#comment-176],
 * and since I could not get dohpaz code to work with my template (and since dohpaz
 * could not get Oslow's code to work), and neither Michael Geary's code nor Sean's
 * [http://www.pinkblack.org/itblog/?page_id=22] code were jQuery style and chainable,
 * and I wanted a ton of features anyway, while retaining a small code base, I decided
 * to rip apart, clean up (JSLint),and add features to their plugins. My hope is that
 * this version will be easier to understand, more forgiving and flexible, and maintain
 * with future versions of the fantastic framework which is jQuery.
 *
 * Note: For event attaching using the liveQuery plugin is highly recommended.
 *
 * Copyright (c) 2007 Charles Phillips [charles at doublerebel dot com]
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * @version	 $Id: jquery.superflydom.js,v 1.2 2012/06/20 10:22:58 simoneAdm Exp $
 *
 * @author	  Charles Phillips [charles at doublerebel dot com]
 * @copyright   (C) 2007. All rights reserved.
 *
 * @license	 http://www.opensource.org/licenses/mit-license.php
 * @license	 http://www.opensource.org/licenses/gpl-license.php
 *
 * @package	 jQuery Plugins
 * @subpackage  SuperFlyDOM
 *
 * @todo		 (dohpaz): Cache basic elements that are created, and if an already existing basic element is
 *			 asked to be created an additional time, use a copy of the cached element to build from.
 *			 (Charles): If dohpaz accomplishes this I will be happy to merge it with my code fork.
 * 
 */

/**
 * Create DOM elements on the fly and automatically append them to the current DOM obejct
 *
 * @uses	jQuery
 *
 * @param   string  element - The name of the DOM element to create (i.e., img, table, a, etc)
 * @param   object  attrs   - An optional object of attributes to apply to the element
 * @param   string  text    - An optional string for text node to prepend to element
 * @param   array   content - An optional array of content (or element children) to append to element
 *
 * @return  jQuery  element - The jQuery object representing the new element
 *
 * @since   FlyDom 1.0
 */

( function($) {
	var el;
	if ($.elHash === undefined) { //If there is no jQuery global hash yet defined,
		var elArray, elHash = {}; //Define hash table of HTML 4.01 DOM Elements, excluding deprecated elements.
		elArray = "a|abbr|acronym|address|area|b|base|bdo|big|blockquote|body|br|button|caption|cite|code|col|colgroup|dd|del|dfn|div|dl|dt|em|fieldset|form|frame|frameset|h1|h2|h3|h4|h5|h6|head|hr|html|i|iframe|img|input|ins|kbd|label|legend|li|link|map|meta|noframes|noscript|object|ol|optgroup|option|p|param|pre|q|samp|script|select|small|span|strong|style|sub|sup|table|tbody|td|textarea|tfoot|th|thead|title|tr|tt|ul|var".split("|"); //packed hash
		for (var i = 0; i < elArray.length; i++) { elHash[elArray[i]] = true; } //Process hash
		$.elHash = elHash; //Set jQuery global hash
	}
	if ($.browser.msie) { //From Dean Edwards IE7 fixes [http://dean.edwards.name] I can't believe I have to do this, damn you IE
		Array.prototype.unshift = function() {
			var a = Array.prototype.concat.call(Array.prototype.slice.apply(arguments, [0]), this), i = a.length;
			while (i--) { this[i] = a[i]; }
			return this.length;
		}
	}
	$.fn.extend({
		createAppend: function() {
			if (arguments.length === 0) { return this; } //If nothing to process, move along!
			var ie, pEl, elH = $.elHash, arg1 = arguments[0], arg2 = arguments[1], a = 1;
			pEl = this[0] || this; //Parent element may be either jQuery Object or DOM element
			if (typeof arg1 === "number") { arg1 = String(arg1); } //Pure numbers cannot be made into textNodes, nor can you run RegExp's or .constructor against them.  I learned this the hard way.
			if (typeof arg1 === "string") { //We should have a string by now, or the input JSON was improperly formatted.
				if (arg1 in elH) { //If first argument is valid HTML Element
					ie = $.browser.msie;
					el = (ie && arg1 === 'input') ? '<input type="' + arg2.type + '" />': arg1; //Fix input for ie. REQUIRES attribute type.
					el = document.createElement(el);
					if (ie && pEl.nodeName.toLowerCase() === "table" && el.nodeName.toLowerCase() === "tr") { //Fix table/tbody for ie.
						pEl = pEl.parentNode.getElementsByTagName('tbody')[0] || pEl.appendChild(document.createElement('tbody')); // Create a new tbody
					}
					el = pEl.appendChild(el); // Add the element directly to the parentElement
					if (arg2.constructor === Object) { //if element's sibling is Attributes Object, process with jQuery
						for (attr in arg2) {
							try {
								$.attr(el, attr, arg2[attr]);	
							} catch (e) {
								//console.debug("errore "+e);
							}
							 
						}
						a++;
					}
					if (arguments.length > a) { //If element has more objects in arguments[] than we have processed
						if (arguments[a].constructor === Array){ //and the next object is an array, it is element's children
							el = $.fn.createAppend.apply($(el), arguments[a]); //recursively apply
							a++;
						}
					}
				} else if (arg1.match(/(<\S[^><]*>)|(&.+;)/g) !== null &&
						 arg1.tagName.toUpperCase() !== 'TEXTAREA') { //Check to see if TextNode is actually mixed HTML, but not textarea
						pEl.innerHTML += arg1; //Append HTML
						el = pEl; //We have no information about what could be in that HTML, so return parent
				} else { el = pEl.appendChild(document.createTextNode(arg1)); } //Otherwise just append simple TextNode
			} else { } //There should be no else.  Todo: Add error catching with throw/catch on string instead of if - Instead of hashing?
			if (arguments.length > a) { //If element has unprocessed siblings
				el = $.fn.createAppend.apply($(pEl), Array.prototype.slice.call(arguments, a)); //Process siblings (sliced arguments array) and append to parent element
				
			}
			return $(el); //We like chaining
		},
		
		createPrepend: function() {
			var al = arguments.length;
			if (al === 0) { return this; } //If nothing to process, move along!
			var elH = $.elHash, ie, pEl, hCN = this[0].hasChildNodes(), arg_l, arg = [];
			arg_l = arg.push(arguments[al-1]); //Start stacking our queue
			pEl = this[0] || this; //Parent element may be either jQuery Object or DOM element
			if (arg[0].constructor === Array) { //If element to prepend has children, add to bottom of queue
				arg_l = arg.unshift(arguments[al - 1 - arg_l]);
			}
			if (arg[0].constructor === Object) { //if element to prepend has attributes, add to queue
				arg_l = arg.unshift(arguments[al - 1 - arg_l]);
			}
			if (typeof arg[0] === "number") { arg[0] = String(arg[0]); } //Pure numbers cannot be made into textNodes, nor can you run RegExp's or .constructor against them.  I learned this the hard way.
			if (typeof arg[0] === "string") { //We should have a string by now, or the input JSON was improperly formatted.
				if (arg[0] in elH) { //If first argument is valid HTML Element
					ie = $.browser.msie;
					el = (ie && arg[0] === 'input') ? '<input type="' + arg[1].type + '" />': arg[0]; //Fix input for ie. REQUIRES attribute type.
					arg.shift();
					el = document.createElement(el);
					if (ie && pEl.nodeName.toLowerCase() === "table" && el.nodeName.toLowerCase() === "tr") { //Fix table/tbody for ie.
						pEl = pEl.parentNode.getElementsByTagName('tbody')[0] || pEl.appendChild(document.createElement('tbody')); // Create a new tbody
					}
					if (hCN) { el = pEl.insertBefore(el, pEl.firstChild); } //If parent already has child nodes, add new element before them
					else { el = pEl.appendChild(el); } // Otherwise add the element directly to the parentElement
					if (arg[0].constructor === Object) {  //if element's sibling is Attributes Object, process with jQuery
						for (attr in arg[0]) { $.attr(el, attr, arg[0][attr]); }
						arg.shift();
					}
					if (arg[0] !== undefined && arg[0].constructor === Array) { //If there are still elements in the queue, and it's an array
						el = $.fn.createPrepend.apply($(el), arg[0]); //Then recursively prepend the children
					}
				} else if (arg[0].match(/(<\S[^><]*>)|(&.+;)/g) !== null &&
						 arg[0].tagName.toUpperCase() !== 'TEXTAREA') { //Check to see if TextNode is actually mixed HTML, but not textarea
						pEl.innerHTML = arg.pop() + pEl.innerHTML; //Prepend HTML
						el = pEl; //We have no information about what could be in that HTML, so return parent
				} else {
					el = document.createTextNode(arg[0]); //Otherwise just create simple TextNode
					el = (hCN) ? pEl.insertBefore(el, pEl.firstChild) : pEl.appendChild(el); //If parent already has child nodes, add new element before them
				}
			} else { } //There should be no else.  Todo: Add error catching with throw/catch on string instead of if - Instead of hashing?
			if (al > arg_l) { //If element has unprocessed siblings
				el = $.fn.createPrepend.apply($(pEl), Array.prototype.slice.call(arguments, 0, -arg_l)); //Process siblings (sliced arguments array) and prepend to parent element
			}
			return $(el);
		},

		tplAppend: function(json, tpl) {
			return $.fn.createAppend.apply(this, tpl.call(json)); // Return ourself for chaining
		},
		
		tplPrepend: function(json, tpl) {
			return $.fn.createPrepend.apply(this, tpl.call(json)); // Return ourself for chaining
		}
	});
})(jQuery);