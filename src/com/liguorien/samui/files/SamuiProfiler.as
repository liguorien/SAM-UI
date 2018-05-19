/*
 *  SamuiProfiler.as
 *
 * Copyright 2005  Nicolas Désy.  All rights reserved.
 *
 *   This file is part of SAM-UI
 *
 *   SAM-UI is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   Foobar is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with SAM-UI; if not, write to the Free SoftwareS
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * @author Nicolas Désy - http://www.liguorien.com
 */
class SamuiProfiler {
	
	private static var _main:SamuiStack;
	private static var _current:SamuiStack;
	private static var _values:Array = new Array();
	private static var _ids:Object = new Object();
	
	/**
	 * Get ID that is mapped to the value
	 * @param value The value
	 */
	public static function getId(value:String):Number{
		if(_ids[value] == undefined){
			_ids[value] = _values.length;
			_values.push(value);			
		}
		return _ids[value];
	}
	
	/**
	 * Get value that is mapped to the ID
         *@param id The ID	
	 */
	public static function getValue(id:Number):String{
		return _values[id];
	}
	
	/**
	 * Notify the profiler that a new stack has been created
	 */
	public static function beginStack():SamuiStack{
		var st:SamuiStack = new SamuiStack(_current);		
		if(_current.isOpen()) _current = st;		
		return st;
	}

	/**
	 * Notify the profiler that current stack execution is finished
	 */
	public static function endStack():Void{
		_current.close();		
		_current = _current.getParent();		
	}


	/**
	 * Return a reference to the Main stack
	 */
	public static function getMainStack():SamuiStack{
		return _main;
	}

	
	/**
	 * begin profiler iniation
	 */
	public static function init():Object{
		_main = _current = new SamuiStack(null);
		_main.className = getId("");
		_main.methodName = getId("Main");		
		initPackage(_global,"");		
		return null;
	}
	
	
	/**
	 * Init a package
	 *@param obj The package
	 *@param packageName package name
	 */
	public static function initPackage(obj:Object, packageName:String){
		for(var i in obj){
			var item = obj[i];
			if(typeof item == "function" && i.indexOf("Samui") == -1){
				initClass(item, packageName+i, obj);				
			}else
			if(typeof item == "object"){
				initPackage(item, packageName+i+".");
			}
		}
	}
	
	
	/**
	 * Init a class
	 *@param clazz The class
	 *@param className class name
	 *@param pack class package
	 */
	public static function initClass(clazz:Function, className:String, pack:Object):Void{				
				
		var proto:Object = clazz.prototype;			
		_global.ASSetPropFlags(proto, null, 0, true);
		
		//instance method
		for(var i in proto){			
			if(typeof proto[i]=="function" && i != "__constructor__"){
				initMethod(proto, proto[i], className, i);	
			}				
		}
		
		//static method
		_global.ASSetPropFlags(clazz, null, 0, true);
		var methods:Object = clazz;			
		for(var i in methods){			
			if(typeof methods[i]=="function"){
				initMethod(methods, methods[i], className, i);	
			}				
		}
		
	//	initConstructor(pack, className);
	}
	
	
	/* TODO: find a way to profile constructor properly, it still don't work
	
	public static function initConstructor(pack:Object, className:String):Void{	
				
		var cId:Number = getId(className);
		var mId:Number = getId("__constructor__");		
		var packs:Array = className.split(".");		
		var name:String = packs[packs.length-1];		
		var original:Function = pack[name];
	
		pack[name] = function(){
			var st:SamuiStack = SamuiProfiler.beginStack();
			st.className = cId;
			st.methodName = mId;		
			var time:Number = getTimer();			
			original.apply(this,arguments);	
			st.time = (getTimer()-time);
			SamuiProfiler.endStack();		
		}		
		
		pack[name].prototype = original.prototype;		
		
	}
	
	*/
	
	
	/**
	 * Init a method
	 *@param prototype The prototype that contains the method
	 *@param method The method
	 *@param className class name
	 *@param methodName method name
	 */
	public static function initMethod(proto:Object, method:Function, className:String, methodName:String):Void{	
				
		var cId:Number = getId(className);
		var mId:Number = getId(methodName);
		
		proto[methodName] = function(){						
			var st:SamuiStack = SamuiProfiler.beginStack();
			st.className = cId;
			st.methodName = mId;			
			var time:Number = getTimer();			
			var result:Object = method.apply(this,arguments);	
			st.time = (getTimer()-time);
			SamuiProfiler.endStack();		
			return result;
		};	
	}
	
	
	/**
	 * Get stack history
	 *@return xml packet that will be send to SAM-UI 
	 */
	public static function toXML():String{
		var str:String = "<profiler><map l='"+ _values.length +"'><![CDATA[" + _values.join("|") + "]]></map><stack><![CDATA[";		
		var sts:Array = _main.getStacks();
		var l:Number = sts.length;		
		var i:Number = -1;		
		while(++i < l) if(!sts[i].isOpen()) str += sts[i].toXML();				
		return str + "]]></stack></profiler>";
	}	
}