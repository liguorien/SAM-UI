/*
 * ProjectConfig.java
 *
 * Created on June 22, 2005, 7:45 PM
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
 *   along with SAM-UI; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.liguorien.samui.project;
import com.l2fprod.common.propertysheet.PropertySheetTableModel;
import com.liguorien.samui.ExceptionLogger;
import com.liguorien.samui.MainUI;
import com.liguorien.samui.library.Library;
import com.liguorien.samui.library.LibraryModel;
import com.liguorien.samui.output.AntThread;
import com.liguorien.samui.ui.SWFViewer;
import com.liguorien.utils.IOUtil;
import com.liguorien.utils.XmlUtil;
import com.sun.org.apache.xpath.internal.XPathAPI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public final class ProjectConfig implements PropertyChangeListener, Runnable {
    
    private final static String FILES_PATH = "/com/liguorien/samui/files/";
    private final static String BUILD_PATH = "build.xml";
    private final static String PROPERTIES_PATH = "build.properties";
    private final static String LIBRARY_PATH = "library.xml";
    private final static String LIBRARY_FOLDER_PATH = "library-folder.xml";
    
    private final static String[] AS_FILES = {"SamuiOutput.as","SamuiProfiler.as","SamuiProfilerOutput.as","SamuiStack.as"};
    
    
    private ProjectConfigModel _projectModel;
    private ProjectConfigView _view;
    private boolean _modelChanged = false;
    private File _currentProject = null;
    private String projectPath = null;
    private Library _library = null;
    private Document _swfmll;
    private StringBuilder _linkedClasses = null;
    private SWFViewer _swf = null;
    
    
    private final static boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().indexOf("windows") > -1;
    
    private final static ProjectConfig _instance = new ProjectConfig();
    public final static ProjectConfig getInstance(){
        return _instance;
    }
    
    /** Creates a new instance of MtascController */
    private ProjectConfig() {
        _projectModel = new ProjectConfigModel();
    }
    
    /**
     *
     * @return
     */
    public Library getLibrary(){
        if(_library == null){
            _library = new Library();
        }
        return _library;
    }
    
    /**
     *
     * @return
     */
    public ProjectConfigView getView(){
        if(_view == null){
            _view = new ProjectConfigView(_projectModel);
            ((PropertySheetTableModel)_view.getTable().getModel()).addPropertyChangeListener(this);
        }
        return _view;
    }
    
    /**
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt){
        _modelChanged = true;
    }
    
    /**
     * Create a new project, create default required files.
     * @param folder New project folder
     */
    public synchronized void initProject(File folder){
        try{
            projectPath = folder.getAbsolutePath() + File.separatorChar;
            
            if(!folder.exists()){
                folder.mkdirs();
            }
            
            new File(projectPath + "src").mkdir();
            new File(projectPath + "images").mkdir();
            
            final String[][] files = {
                {FILES_PATH + BUILD_PATH, projectPath + BUILD_PATH},
                {FILES_PATH + PROPERTIES_PATH, projectPath + PROPERTIES_PATH},
                {FILES_PATH + LIBRARY_FOLDER_PATH, projectPath + LIBRARY_FOLDER_PATH}
            };
            
            for(String[] file : files){
                IOUtil.copy( getClass().getResourceAsStream(file[0]), new FileOutputStream(new File(file[1])) );
            }
            
            for(String file : AS_FILES){
                IOUtil.copy( getClass().getResourceAsStream(FILES_PATH+file), new FileOutputStream(new File(projectPath+"src"+File.separatorChar+file)) );
            }
            
            openProject(folder);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    public void run(){
        
        MainUI ui = MainUI.getInstance();
        ui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        try{
            
            projectPath = _currentProject.getAbsolutePath() + File.separatorChar;
            
            final Properties props = new Properties();
            
            props.load(new BufferedInputStream(new FileInputStream(new File(projectPath + PROPERTIES_PATH))));
            
            _projectModel.setWidth(Integer.parseInt(props.getProperty("project.width")));
            _projectModel.setHeight(Integer.parseInt(props.getProperty("project.height")));
            _projectModel.setFramerate(Integer.parseInt(props.getProperty("project.framerate")));
            _projectModel.setBgColor(Color.decode("0x" + props.getProperty("project.bgcolor")));
            // _projectModel.setProjectClasspath(getFile(props.getProperty("project.classpath")));
            // _projectModel.setSystemClasspath(getFile(props.getProperty("system.classpath")));
            
            _projectModel.setProjectSwf(getFile(props.getProperty("project.swf")));
            _projectModel.setUsingNaturalEntryPoint(Boolean.valueOf(props.getProperty("project.natural")));
            _projectModel.setMainSymbol(props.getProperty("project.mainsymbol"));
            _projectModel.setStrict(Boolean.valueOf(props.getProperty("mtasc.strict")));
            _projectModel.setVerbose(Boolean.valueOf(props.getProperty("mtasc.verbose")));
            _projectModel.setMx(Boolean.valueOf(props.getProperty("mtasc.mx")));
            _projectModel.setKeep(Boolean.valueOf(props.getProperty("mtasc.keep")));
            _projectModel.setGroupingClasses(Boolean.valueOf(props.getProperty("mtasc.group")));
            _projectModel.setSwfVersion(Integer.parseInt(props.getProperty("mtasc.version")));
            _projectModel.setExportFrame(Integer.parseInt(props.getProperty("mtasc.export")));
            _projectModel.setErrorFormatting(Boolean.valueOf(props.getProperty("mtasc.msvc")));
            _projectModel.setMtascHome(getFile(props.getProperty("mtasc.home")));
            _projectModel.setSwfmillHome(getFile(props.getProperty("swfmill.home")));
            _projectModel.setOptionalParameters(props.getProperty("mtasc.optional",""));
            _projectModel.setUsingSamuiOutput(Boolean.valueOf(props.getProperty("samui.output")));
            _projectModel.setSamuiProfiler(Boolean.valueOf(props.getProperty("samui.profiler")));
            
            
            final ProjectClasspath classpath = new ProjectClasspath();
            final String pathStr = props.getProperty("project.classpath", "");
            
            if(pathStr != null && !"".equals(pathStr)){
                final String[] paths = pathStr.split(";");
                for(String path : paths){
                    classpath.add(getFile(path));
                }
            }
            
            
            _projectModel.setClasspaths(classpath);
            _projectModel.setProjectMainClass(null);
            
            final String mainClass = props.getProperty("project.mainclass", "");
           
            if(!"".equals(mainClass)){
                for(File path : classpath){
                    File file = new File(path, mainClass);
                    if(file.exists()){
                        _projectModel.setProjectMainClass(file);
                        break;
                    }
                }
            }
            
            
            ui.initComponents();
            
            _view.readFromObject(_projectModel);
            
            final Document doc = XmlUtil.parse(new File(projectPath + BUILD_PATH));
            final NodeList list = XPathAPI.selectNodeList(doc, "/project/target");
            final int size = list.getLength();
            final JComboBox combo = ui.getToolBar().getTargetCombo();
            
            combo.removeAllItems();
            
            for(int i=0; i<size; i++){
                combo.addItem(XmlUtil.getAttribute(list.item(i), "name"));
            }
            
            final Document dom = XmlUtil.parse(new File(projectPath + LIBRARY_FOLDER_PATH));
            
            final LibraryModel libModel = getLibrary().getModel();
            libModel.readDOM(dom);
            
        }catch(Exception ex){      
            ExceptionLogger.log(ex);
        }finally{
            ui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    /**
     *
     * @param folder
     */
    public synchronized void openProject(File folder){
        _currentProject = folder;
        EventQueue.invokeLater(this);
    }
    
    public synchronized void runSWF(){
        new Thread(new Runnable(){
            public void run(){
                try{
                    
                    if(_swf == null){
                        _swf = new SWFViewer(_projectModel.getProjectSwf());
                        MainUI.getInstance().getMainPanel().addTab(_swf);
                        _swf.requestFocus();
                    }else{
                        _swf.requestFocus();
                        _swf.setFile(_projectModel.getProjectSwf());
                    }
                    
                    MainUI.getInstance().requestFocus();
                    
                }catch(Throwable ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    
    /**
     *
     * @param target
     */
    public synchronized void runAntTarget(final String target){
        runAntTarget(target, false);
    }
    
    /**
     *
     * @param target
     * @param runSWF
     */
    public synchronized void runAntTarget(final String target, boolean runSWF){
        saveProject();
        
        _userWantToRunSWF = runSWF;
        
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try{
                    
                    ProcessBuilder pb = null;
                    
                    if(IS_WINDOWS){
                        String[] params = new String[]{"cmd.exe", "/C", "\"\"%JAVA_HOME%/bin/java\"\"", "-cp", "%ANT_HOME%/lib/ant-launcher.jar", "-Dant.home=%ANT_HOME%",  "org.apache.tools.ant.launch.Launcher", "-buildfile", projectPath+"build.xml", target};
                        pb = new ProcessBuilder(params);
                        System.out.println(Arrays.toString(params));
                    }else{
                        pb = new ProcessBuilder(new String[]{ "$JAVA_HOME/bin/java", "-cp", "$ANT_HOME/lib/ant-launcher.jar", "-Dant.home=$ANT_HOME",  "org.apache.tools.ant.launch.Launcher", "-buildfile", projectPath+"build.xml", target});
                    }
                    
                    //System.out.println();
                    
                    final Process ant = pb.start();
                    
                    final AntThread thread = new AntThread(ant, ProjectConfig.this);
                    
                    thread.start();
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                    ExceptionLogger.log(ex);
                }
            }
        });
        
    }
    
    
    private boolean _userWantToRunSWF;
    
    /**
     *
     * @return
     */
    public boolean isUserWantToRunSWF(){
        return _userWantToRunSWF;
    }
    
    /**
     *
     * @param runSWF
     */
    public synchronized void compileProject(boolean runSWF){
        runAntTarget("compile", runSWF);
    }
    
    
    
    public synchronized void saveProject(){
        
        _view.writeToObject(_projectModel);
        
        final Properties props = new Properties();
        
        props.setProperty("project.swf",getPath( _projectModel.getProjectSwf()));
        props.setProperty("project.width", String.valueOf(_projectModel.getWidth()));
        props.setProperty("project.height", String.valueOf(_projectModel.getHeight()));
        props.setProperty("project.framerate", String.valueOf(_projectModel.getFramerate()));
        props.setProperty("project.bgcolor",  Integer.toHexString(_projectModel.getBgColor().getRGB()).substring(2));
        
        props.setProperty("project.natural", String.valueOf(_projectModel.isUsingNaturalEntryPoint()));
        props.setProperty("project.mainsymbol", _projectModel.getMainSymbol());
        
        props.setProperty("mtasc.export", String.valueOf( _projectModel.getExportFrame()));
        props.setProperty("mtasc.version",String.valueOf( _projectModel.getSwfVersion()));
        props.setProperty("mtasc.msvc",String.valueOf( _projectModel.isErrorFormatting()));
        props.setProperty("mtasc.group",String.valueOf( _projectModel.isGroupingClasses()));
        
        
        
        props.setProperty("samui.output",String.valueOf( _projectModel.isUsingSamuiOutput()));
        props.setProperty("samui.profiler",String.valueOf( _projectModel.isSamuiProfiler()));
        props.setProperty("mtasc.strict", String.valueOf(_projectModel.isStrict()));
        props.setProperty("mtasc.verbose", String.valueOf(_projectModel.isVerbose()));
        props.setProperty("mtasc.mx", String.valueOf(_projectModel.isMx()));
        props.setProperty("mtasc.keep", String.valueOf(_projectModel.isKeep()));
        props.setProperty("mtasc.home", getPath(_projectModel.getMtascHome()));
        props.setProperty("mtasc.optional", _projectModel.getOptionalParameters());
        
        props.setProperty("swfmill.home", getPath(_projectModel.getSwfmillHome()));
        
        
        final ProjectClasspath paths = _projectModel.getClasspaths();
        
        final StringBuilder mtascClasspath = new StringBuilder();
        for(File path : paths){
            mtascClasspath.append(" -cp ").append(getPath(path));
        }
        props.setProperty("mtasc.classpath", mtascClasspath.toString());
        
        
        final StringBuilder projectClasspath = new StringBuilder();
        for(File path : paths){
            projectClasspath.append(';').append(getPath(path));
        }
        if(projectClasspath.length() > 0){
            projectClasspath.deleteCharAt(0);
        }
        props.setProperty("project.classpath", projectClasspath.toString());
        
        
        
        final File mainClass = _projectModel.getProjectMainClass();
        
        if(mainClass != null){
            
            File usedPath = null;
            
            int index = -1;
            
            for(File path : paths){
                index = mainClass.getAbsolutePath().indexOf(path.getAbsolutePath());
                if(index == 0) {
                    usedPath = path;
                    break;
                }
            }
            
            if(index == 0){
                props.setProperty("project.mainclass", mainClass.getAbsolutePath().substring(index + usedPath.getAbsolutePath().length() + 1).replaceAll("\\\\", "/"));
            }else{
                props.setProperty("project.mainclass", getPath(mainClass));
            }
            
        }
        
        generateSwfml(props.getProperty("project.mainclass"));
        
        props.setProperty("linked.classes", _linkedClasses.toString());
        
        
        
        try{
            props.store(new FileOutputStream(new File(projectPath + PROPERTIES_PATH)), "Ant configuration \r\n#Generated by SAM-UI");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
        final LibraryModel libModel = _library.getModel();
        
        if(libModel.hasChange()){
            libModel.markChanges();
            XmlUtil.save(libModel.getDOM(),  new File(projectPath + LIBRARY_FOLDER_PATH));
        }
        
        _modelChanged = false;
    }
    
    
    
    private final static Pattern _quotePattern = Pattern.compile("\"");
    
    private synchronized void generateSwfml(String mainClass){
        try{
            
            final StringBuilder buffer = new StringBuilder();
            
            buffer
                    .append("<movie width='").append(_projectModel.getWidth())
                    .append("' height='").append(_projectModel.getHeight())
                    .append("' framerate='" + _projectModel.getFramerate())
                    .append("'><background color='#").append(Integer.toHexString(_projectModel.getBgColor().getRGB()).substring(2))
                    .append("'/><frame><clip import=\"classes.swf\" /><library></library>");
            
            
            if(_projectModel.isUsingNaturalEntryPoint()){
                buffer.append("<place id=\"").append(_projectModel.getMainSymbol()).append("\" name=\"app\" x=\"0\" y=\"0\" depth=\"1000\" />");
            }else{
                buffer.append("<call object=\"").append(mainClass.replaceAll("\"", "").replaceAll("\\.as", "").replaceAll("[/\\\\]",".")).append("\" method=\"main\" />");
            }
            
            buffer.append("</frame></movie>");
            
            _swfmll = XmlUtil.parse(buffer.toString());
            
            _linkedClasses = new StringBuilder();
            
            final Node library = XPathAPI.selectSingleNode(_swfmll, "/movie/frame/library");
            final NodeList list = XPathAPI.selectNodeList(getLibrary().getModel().getDOM(), "//item");
            final int size = list.getLength();
            
            for(int i=0; i<size; i++){
                final Node item = XmlUtil.addNode(library, "clip");
                final Node symbol = list.item(i);
                
                XmlUtil.setAttribute(item, "id", XmlUtil.getAttribute(symbol, "linkage"));
                
                //optional symbol properties
                final String[][] props = {{"file","import"} , {"class","class"}};
                for(String[] prop : props){
                    final String value = XmlUtil.getAttribute(symbol, prop[0]);
                    if(value != null && value.length() > 0){
                        XmlUtil.setAttribute(item, prop[1], value);
                        if("class".equals(prop[1])){
                            _linkedClasses.append(value.replaceAll("\\.","/")).append(".as ");
                        }
                    }
                }
            }
            
            XmlUtil.save(_swfmll,  new File(projectPath + LIBRARY_PATH));
            
        }catch(Exception ex){
            ExceptionLogger.log(ex);
        }
    }
    
    
    /**
     *
     * @param prop
     * @return
     */
    public File getFile(String prop){
        try{
            if(prop == null || "".equals(prop.trim())) {
                return null;
            }
            
            prop = _quotePattern.matcher(prop).replaceAll("");
            
            final File file = new File(prop);
            if(file.isAbsolute()){
                return file;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new File(projectPath + prop);
    }
    
    
    /**
     *
     * @param file
     * @return
     */
    public String getPath(File file){
        return getPath(file,true);
    }
    
    
    /**
     *
     * @param file
     * @param withQuote
     * @return
     */
    public String getPath(File file, boolean withQuote){
        
        if(file == null) return "";
        
        String path = file.getAbsolutePath();
        
        if(IS_WINDOWS){
            path = path.replaceAll("\\\\", "/");
        }
        
        final int index = path.indexOf(projectPath);
        
        if(index == 0){
            if(withQuote){
                return '"' + path.substring(index + projectPath.length()) + '"';
            }
            return path.substring(index + projectPath.length());
        }
        if(withQuote){
            return '"' + path + '"';
        }
        return path;
    }
}
