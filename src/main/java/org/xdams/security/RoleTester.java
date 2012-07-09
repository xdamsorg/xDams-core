package org.xdams.security;

public class RoleTester {
   public static final String ROLE_ADMIN  = "ROLE_ADMIN";
   public static final String ROLE_GOD    = "ROLE_GOD";
   public static final String ROLE_USER   = "ROLE_USER";
   public static final String ROLE_READER = "ROLE_READER";
   
   public static boolean testEditing(String role){
	   if(role.equals(ROLE_ADMIN)){
		   return true;
	   }else if(role.equals(ROLE_GOD)){
		   return true;
	   }else if(role.equals(ROLE_USER)){
		   return true;
	   }
	   return false;
   }
   public static boolean testAdminTools(String role){
	   if(role.equals(ROLE_ADMIN)){
		   return true;
	   }else if(role.equals(ROLE_GOD)){
		   return true;
	   }
	   return false;
   }
   public static boolean testArchive(String role){
	   if(role.equals(ROLE_ADMIN)){
		   return true;
	   }else if(role.equals(ROLE_GOD)){
		   return true;
	   }
	   return false;
   }
   public static boolean testGod(String role){
	   if(role.equals(ROLE_GOD)){
		   return true;
	   }
	   return false;
   }
}
