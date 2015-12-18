package com.util;

public class DistanceUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(GetDistance(113.396089, 23.0537576, 113.402257, 23.050752));
	}

	/** 
     * ת��Ϊ����(rad) 
     * */  
    private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
    /** 
     * ����googleMap�е��㷨�õ�����γ��֮��ľ���,���㾫����ȸ��ͼ�ľ��뾫�Ȳ�࣬��Χ��0.2������ 
     * @param lon1 ��һ��ľ��� 
     * @param lat1 ��һ���γ�� 
     * @param lon2 �ڶ���ľ��� 
     * @param lat3 �ڶ����γ�� 
     * @return ���صľ��룬��λkm 
     * */  
    private static final  double EARTH_RADIUS = 6378137;//����뾶(��λm) 
    public static double GetDistance(double lon1,double lat1,double lon2, double lat2)  
    {  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = rad(lon1) - rad(lon2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       //s = Math.round(s * 10000) / 10000;  
       return s;  
    }
}
