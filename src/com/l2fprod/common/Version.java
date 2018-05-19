/* Created by JReleaseInfo AntTask from Open Source Competence Group */
/* Creation date Thu Jun 09 09:29:48 CEST 2005 */
package com.l2fprod.common;

import java.util.Date;

/**
 * This class provides information gathered from the build environment.
 * 
 * @author JReleaseInfo AntTask
 */
public class Version {


   /** buildDate (set during build process to 1118302188093L). */
   private static Date buildDate = new Date(1118302188093L);

   /**
    * Get buildDate (set during build process to Thu Jun 09 09:29:48 CEST 2005).
    * @return Date buildDate
    */
   public static final Date getBuildDate() { return buildDate; }


   /** buildTimestamp (set during build process to "06/09/2005 09:29 AM"). */
   private static String buildTimestamp = new String("06/09/2005 09:29 AM");

   /**
    * Get buildTimestamp (set during build process to "06/09/2005 09:29 AM").
    * @return String buildTimestamp
    */
   public static final String getBuildTimestamp() { return buildTimestamp; }


   /** year (set during build process to "2005"). */
   private static String year = new String("2005");

   /**
    * Get year (set during build process to "2005").
    * @return String year
    */
   public static final String getYear() { return year; }


   /** version (set during build process to "0.1"). */
   private static String version = new String("0.1");

   /**
    * Get version (set during build process to "0.1").
    * @return String version
    */
   public static final String getVersion() { return version; }


   /** project (set during build process to "l2fprod-common"). */
   private static String project = new String("l2fprod-common");

   /**
    * Get project (set during build process to "l2fprod-common").
    * @return String project
    */
   public static final String getProject() { return project; }

}
