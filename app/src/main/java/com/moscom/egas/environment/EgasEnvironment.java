package com.moscom.egas.environment;

public class EgasEnvironment {
    private static String className = EgasEnvironment.class.getSimpleName();
    private static  String JSON_SEVER = "http://192.168.42.170:8080/egas/json";
    //private static  String JSON_SEVER = "http://192.168.1.110:8080/egas/json";
    //private static  String JSON_SEVER = "http://192.168.233.75:8080/egas/json";
    private static  String JSON_REQUESTS = "json";

    public static  String getJsonServer() throws Exception{ 			return JSON_SEVER; }
    public static  String getJsonRequests() throws Exception{ 			return JSON_REQUESTS; }

}
