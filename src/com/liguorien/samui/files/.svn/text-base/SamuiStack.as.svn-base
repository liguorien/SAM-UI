/*
 *  SamuiStack.as
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
class SamuiStack {
	
	private var _parent:SamuiStack;
	private var _stacks:Array;	
	private var _opened:Boolean;	
	public var className:Number;
	public var methodName:Number;
	public var time:Number;
	
	/**
	 *@constructor
	 *@param parent -Current stack 
	 */
	public function SamuiStack(parent:SamuiStack){			
		_stacks = [];
		_opened = true;		
		_parent = parent;
		_parent.addStack(this);
	}
	
	/*
	 * Add a new sub stack
	 *@param st The new stack
	 */
	public function addStack(st:SamuiStack):Void{
		_stacks.push(st);
	}
	
	/*
	 * Get parent stack
	 *@return The parent stack
	 */
	public function getParent():SamuiStack{
		return _parent;
	}
	
	/**
	 * Get sub stack
	 */
	public function getStacks():Array{
		return _stacks;
	}		
	
	/**
	 * Determine if the stack execution is finished 
	 *@return true if the stack is still executing
	 */
	public function isOpen():Boolean{
		return _opened;
	}
	
	/**
	 * Close current stack
	 */
	public function close():Void{
		_opened = false;
	}
	
	/**
	 * Serialize the stack
	 */
	public function toXML():String{
		var sts:Array = _stacks;
		var l:Number = sts.length;		
		var str:String = '<' + className + ',' + methodName + ',' + time;		
		var i:Number = -1;
		while(++i < l) str += sts[i].toXML();			
		return str + '>';
	}
}