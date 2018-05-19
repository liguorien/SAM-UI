
/*
 *  SamuiProfilerOutput.as
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
class SamuiProfilerOutput extends SamuiOutput {
	
	private var _init:Object = SamuiProfiler.init();

	private function SamuiProfilerOutput(){
		super("profiler");		
	}
	
	private function onData(command:String):Void{
		if(command == "getStackHistory"){
			SamuiProfilerOutput.log("getStackHistory","Profiler","Profiler",0);
			send(SamuiProfiler.toXML());
		}	
	}		
	
	private static var _o:SamuiProfilerOutput;
	
	public static function log():Void{		
		if(_o == undefined) _o = new SamuiProfilerOutput();
		var size:Number = arguments.length;		
		_o.output("<m c='"+arguments[size-3]+"' f='"+arguments[size-2]+"' l='"+arguments[size-1]+"'><![CDATA["+arguments.splice(0, size-3).join(" ")+"]]></m>");
	}	
}