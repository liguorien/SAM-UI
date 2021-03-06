/*
 * MtascModel.java
 *
 * Created on June 22, 2005, 7:44 PM
 *
 * Copyright 2005  Nicolas D�sy.  All rights reserved.
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
 *   along with SAM-UI; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.liguorien.samui.project;
import com.l2fprod.common.beans.BaseBeanInfo;
import com.l2fprod.common.beans.ExtendedPropertyDescriptor;
import java.awt.Color;
import java.beans.BeanInfo;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;



/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public final class ProjectConfigModel   {
        
    public BeanInfo getBeanInfo(){
        return new ProjectConfigModelBeanInfo();
    }
    
    class ProjectConfigModelBeanInfo extends BaseBeanInfo {
        
        public ProjectConfigModelBeanInfo() {
            super(ProjectConfigModel.class);
            
            //TODO: mettre �a dans un fichier de properties pour l'internationalisation ?
            
            final String[][] meta = {
                {"projectSwf","Project","swf","Generated SWF path"},
                {"width","Project","width","SWF's width"},
                {"height","Project","height","SWF's height"},
                {"framerate","Project","framerate","SWF's framerate"},
                {"bgColor","Project","bgColor","SWF's background color"},
                {"classpaths","Project","classpath","Project classpath."},         
                {"projectMainClass","Project","main class","Project's main class. Not used if Natural Entry Point method is activated"},
                {"usingNaturalEntryPoint","Project","Use Natural Entry Point","Use a symbol as application entry point instead of using a main class."},
                {"mainSymbol","Project","Entry point ID","Linkage ID of application symbol. Only used if Natural Entry Point method is activated."},
                {"usingSamuiOutput","Project","SAM-UI output","Turn on SAM-UI output console. Use TRACE() function to output message."},
                {"samuiProfiler","Project","SAM-UI profiler","Turn on SAM-UI profiler. Output console will be activated too."},
                {"swfVersion","MTASC","version","SWF version."},
                {"exportFrame","MTASC","Export frame","Will export AS2 classes into target frame of swf."},
                {"strict","MTASC","strict","Turn on strict mode."},
                {"verbose","MTASC","verbose","Turn on verbose mode."},
                {"mx","MTASC","mx","Use mx precompiled package."},               
                {"keep","MTASC","keep","Don't remove AS2 classes from input SWF."},
                {"groupingClasses","MTASC","group","Will merge classes into one single clip (this will reduce SWF size but might cause some problems if you're using -keep or -mx)."},
                {"errorFormatting","MTASC","msvc","Use Microsoft Visual Studio errors style formating install of Java style (for file names and lines number)"},
                {"mtascHome","MTASC","path","MTASC executable path. Optional if defined in PATH environnement variable."},
                {"optionalParameters","MTASC","optional parameters","MTASC optional parameters."},
                {"swfmillHome","swfmill","path","swfmill executable path. Optional if defined in PATH environnement variable."}                
            };
            
            for(String[] data : meta){
                final ExtendedPropertyDescriptor prop = addProperty(data[0]);
                prop.setCategory(data[1]);
                prop.setDisplayName(data[2]);
                prop.setShortDescription(data[3]);
            }
        }
    }
    
    /**
     * Holds value of property project.classpath
     */
    private File _projectClasspath;
    
    /**
     * Holds value of property system.classpath
     */
    private File _systemClasspath;
    
    /**
     * Holds value of property projectHome
     */
    private File _projectMainClass;
    
    /**
     * Holds value of property mtasc.home.
     */
    private File _mtascHome;
    
    /**
     * Holds value of property swfmill.home
     */
    private File _swfmillHome;
     
    /**
     * Holds value of property mtasc.strict
     */
    private boolean _strict;
    
    /**
     * Holds value of property mtasc.verbose.
     */
    private boolean _verbose;
    
    /**
     * Holds value of property mtasc.mx
     */
    private boolean _mx;
    
    /**
     * Holds value of property mtasc.keep
     */
    private boolean _keep;
            
    /**
     * Holds value of property project.width
     */
    private int _width;
    
    /**
     * Holds value of property project.height
     */
    private int _height;
    
    /**
     * Holds value of property project.framerate
     */
    private int _framerate;
    
    /**
     * Holds value of property project.bgcolor
     */
    private Color _bgColor;
    
    /**
     * Holds value of property project.swf
     */
    private File _ouputSwf;
    
    
    /**
     * Getter for property swfmill.home.
     * @return Value of property swfmill.home.
     */
    public File getSwfmillHome(){
        return _swfmillHome;
    }
    
    /**
     * Setter for property swfmill.home
     * @param value New value of property swfmill.home
     */
    public void setSwfmillHome(File value){
        _swfmillHome = value;
    }
    
    /**
     * Getter for property mtasc.home.
     * @return Value of property mtasc.home
     */
    public File getMtascHome(){
        return _mtascHome;
    }
    
    /**
     * Setter for property mtasc.home
     * @param value New value of property mtasc.home
     */
    public void setMtascHome(File value){
        _mtascHome = value;
    }    
    
    /**
     * Getter for property project.bgcolor
     * @return Value of property project.bgcolor
     */
    public Color getBgColor(){
        return _bgColor;
    }
    
    /**
     * Setter for property project.bgcolor
     * @param value New value of property project.bgcolor
     */
    public void setBgColor(Color value){
        _bgColor = value;
    }
    
    /**
     * Getter for property project.framerate
     * @return Value of property project.framerate
     */
    public int getFramerate(){
        return _framerate;
    }
    
    /**
     * Setter for property project.framerate
     * @param value New value of property project.framerate
     */
    public void setFramerate(int value){
        _framerate = value;
    }
    
    /**
     * Getter for property project.width
     * @return Value of property project.width
     */
    public int getWidth(){
        return _width;
    }
    
    /**
     * Setter for property project.width
     * @param value New value of property project.width
     */
    public void setWidth(int value){
        _width = value;
    }
    
    /**
     * Getter for property project.width
     * @return Value of property project.width
     */
    public int getHeight(){
        return _height;
    }
    
    /**
     * Setter for property project.heigh
     * @param value New value of property project.heigh
     */
    public void setHeight(int value){
        _height = value;
    }
    
    /**
     * Getter for property project.home.
     * @return Value of property project.home.
     */
    public File getProjectMainClass(){     
        return _projectMainClass;
    }
    
    /**
     * Setter for property project.mainclass
     * @param value New value of property project.mainclass
     */
    public void setProjectMainClass(File value){     
        _projectMainClass = value;
    }
    
    
    
    /**
     * Getter for property system.classpath
     * @return Value of property system.classpath
     */
    public File getSystemClasspath(){
        return _systemClasspath;
    }
    
    /**
     * Setter for property system.classpath.
     * @param value New value of property system.classpath.
     */
    public void setSystemClasspath(File value){
        _systemClasspath = value;
    }
    
    
    
    
    /**
     * Getter for property project.classpath
     * @return Value of project.classpath
     */
    public File getProjectClasspath(){
        return _projectClasspath;
    }
    
    /**
     * Setter for property project.classpath.
     * @param value New value of property project.classpath.
     */
    public void setProjectClasspath(File value){
        _projectClasspath = value;
    }
    
    /**
     * Getter for property mtasc.strict.
     * @return Value of property mtasc.strict.
     */
    public boolean isStrict() {
        return _strict;
    }
    
    /**
     * Setter for property mtasc.strict.
     * @param verbose New value of property mtasc.strict.
     */
    public void setStrict(boolean value){
        _strict = value;
    }
    
    
    /**
     * Getter for property mtasc.verbose.
     * @return Value of property mtasc.verbose.
     */
    public boolean isVerbose() {
        return _verbose;
    }
    
    /**
     * Setter for property mtasc.verbose.
     * @param verbose New value of property mtasc.verbose.
     */
    public void setVerbose(boolean verbose) {
        _verbose = verbose;
    }
    
    /**
     * Getter for property mtasc.mx.
     * @return Value of property mtasc.mx.
     */
    public boolean isMx() {
        return _mx;
    }
    
    /**
     * Setter for property mtasc.mx.
     * @param mx New value of property mtasc.mx.
     */
    public void setMx(boolean mx) {
        _mx = mx;
    }
    
    /**
     * Getter for property mtasc.keep.
     * @return Value of property mtasc.keep.
     */
    public boolean isKeep() {
        return _keep;
    }
    
    /**
     * Setter for property mtasc.keep.
     * @param keep New value of property mtasc.keep.
     */
    public void setKeep(boolean keep) {
        _keep = keep;
    }
      
    /**
     * Holds value of property _projectSwf.
     */
    private File _projectSwf;
    
    /**
     * Getter for property projectSwf.
     * @return Value of property projectSwf.
     */
    public File getProjectSwf() {        
        return _projectSwf;
    }
    
    /**
     * Setter for property projectSwf.
     * @param projectSwf New value of property projectSwf.
     */
    public void setProjectSwf(File projectSwf) {        
        _projectSwf = projectSwf;
    }
    
    /**
     * Holds value of property _optionalParameters.
     */
    private String _optionalParameters;
    
    /**
     * Getter for property optionalParameters.
     * @return Value of property optionalParameters.
     */
    public String getOptionalParameters() {        
        return _optionalParameters;
    }
    
    /**
     * Setter for property optionalParameters.
     * @param optionalParameters New value of property optionalParameters.
     */
    public void setOptionalParameters(String optionalParameters) {        
        _optionalParameters = optionalParameters;
    }
    
    /**
     * Holds value of property _usingSamuiOutput.
     */
    private boolean _usingSamuiOutput;
    
    /**
     * Getter for property usingSamuiOutput.
     * @return Value of property usingSamuiOutput.
     */
    public boolean isUsingSamuiOutput() {        
        return _usingSamuiOutput;
    }
    
    /**
     * Setter for property usingSamuiOutput.
     * @param usingSamuiOutput New value of property usingSamuiOutput.
     */
    public void setUsingSamuiOutput(boolean usingSamuiOutput) {        
        _usingSamuiOutput = usingSamuiOutput;
    }
    
    /**
     * Holds value of property _samuiProfiler.
     */
    private boolean _samuiProfiler;
    
    /**
     * Getter for property samuiProfiler.
     * @return Value of property samuiProfiler.
     */
    public boolean isSamuiProfiler() {        
        return _samuiProfiler;
    }
    
    /**
     * Setter for property samuiProfiler.
     * @param samuiProfiler New value of property samuiProfiler.
     */
    public void setSamuiProfiler(boolean samuiProfiler) {        
        _samuiProfiler = samuiProfiler;
    }
    
    /**
     * Holds value of property _usingNaturalEntry.
     */
    private boolean _usingNaturalEntryPoint;
    
    /**
     * Getter for property usingNaturalEntry.
     * @return Value of property usingNaturalEntry.
     */
    public boolean isUsingNaturalEntryPoint()  {        
        return _usingNaturalEntryPoint;
    }
    
    /**
     * Setter for property usingNaturalEntry.
     * @param usingNaturalEntry New value of property usingNaturalEntry.
     */
    public void setUsingNaturalEntryPoint(boolean usingNaturalEntryPoint)  {        
        _usingNaturalEntryPoint = usingNaturalEntryPoint;
    }
    
    /**
     * Holds value of property _mainSymbol.
     */
    private String _mainSymbol;
    
    /**
     * Getter for property mainSymbol.
     * @return Value of property mainSymbol.
     */
    public String getMainSymbol() {        
        return _mainSymbol;
    }
    
    /**
     * Setter for property mainSymbol.
     * @param mainSymbol New value of property mainSymbol.
     */
    public void setMainSymbol(String mainSymbol) {        
        _mainSymbol = mainSymbol;
    }
    
    /**
     * Holds value of property _swfVersion.
     */
    private int _swfVersion;
    
    /**
     * Getter for property swfVersion.
     * @return Value of property swfVersion.
     */
    public int getSwfVersion() {        
        return _swfVersion;
    }
    
    /**
     * Setter for property swfVersion.
     * @param swfVersion New value of property swfVersion.
     */
    public void setSwfVersion(int swfVersion) {        
        _swfVersion = swfVersion;
    }
    
    /**
     * Holds value of property _errorFormatting.
     */
    private boolean _errorFormatting;
    
    /**
     * Getter for property errorFormatting.
     * @return Value of property errorFormatting.
     */
    public boolean isErrorFormatting() {        
        return _errorFormatting;
    }
    
    /**
     * Setter for property errorFormatting.
     * @param errorFormatting New value of property errorFormatting.
     */
    public void setErrorFormatting(boolean errorFormatting) {        
        _errorFormatting = errorFormatting;
    }
    
    /**
     * Holds value of property _exportFrame.
     */
    private int _exportFrame;
    
    /**
     * Getter for property exportFrame.
     * @return Value of property exportFrame.
     */
    public int getExportFrame() {        
        return _exportFrame;
    }
    
    /**
     * Setter for property exportFrame.
     * @param exportFrame New value of property exportFrame.
     */
    public void setExportFrame(int exportFrame) {        
        _exportFrame = exportFrame;
    }
    
    /**
     * Holds value of property _groupingClasses.
     */
    private boolean _groupingClasses;
    
    /**
     * Getter for property groupingClasses.
     * @return Value of property groupingClasses.
     */
    public boolean isGroupingClasses() {        
        return _groupingClasses;
    }
    
    /**
     * Setter for property groupingClasses.
     * @param groupingClasses New value of property groupingClasses.
     */
    public void setGroupingClasses(boolean groupingClasses) {        
        _groupingClasses = groupingClasses;
    }

    /**
     * Holds value of property _classpaths.
     */
    private ProjectClasspath _classpaths = new ProjectClasspath();

    /**
     * Getter for property classpaths.
     * @return Value of property classpaths.
     */
    public ProjectClasspath getClasspaths() {     
        return _classpaths;
    }

    /**
     * Setter for property classpaths.
     * @param classpaths New value of property classpaths.
     */
    public void setClasspaths(ProjectClasspath classpaths) {

        _classpaths = classpaths;
    }
}
