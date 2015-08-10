package com.newbrain.utils;




public class Constant {
	
	
	/**请求成功*/
	public static final String REQUEST_SUCCESS = "200";
	
	/**activity之间跳转传值的flag*/
	public static final String FLAG_ACTIVITY_RESULT = "result";
	
	
	public static final String FLAG_index1 = "result";
	
	public static final String FLAG_ACTIVITY_INDEX_FRIST = "first";
	
	public static final String FLAG_ACTIVITY_INDEX_SECOND = "second";
	
	
//	public static final String BASE_URL = "http://218.244.138.142:8890/";
	
	public static final String BASE_URL = "http://192.168.0.109:8080/";
	
	
	/**请求失败*/
	public static final int FLAG_HTTP_ERROR = 400;
	
	/**天气预报的key*/
	public static final String KEY_WEATHER = "3c50629d25fcd90323508531422bed95";
	
	/**天气预报flag*/
	public static final int FLAG_WEATHER_JUHE = 1001;
	
	/**天气预报*/
	public static final String  METHOD_WEATHER_JUHE= "http://v.juhe.cn/weather/index";
	
	/**天气储存起来*/
	public static final String WEATHER_CURRENT_TEMP = "weather_current_temp";
	
	public static final String WEATHER_WEATHER = "weather_weather";
	
	public static final String WEATHER_TEMPERATURE = "weather_temperature";
	
	public static final String WEATHER_DATE = "weather_date";
	
	public static final String WEATHER_FA = "weather_fa";
	
	public static final String WEATHER_FB = "weather_fb";
	
	
	
	/**附近好友*/
	public static final String METHOD_NEARBY_CF = BASE_URL+"weiCar/interf/nearbyFriendList";
	/**附近好友*/
	public static final int FLAG_NEARBY_CF  = 7001;
	
	
	
	/**获取所有的车辆*/
	public static final String METHOD_GET_ALL_CAR = BASE_URL+"weiCar/interf/car/carList";
	/**获取所有的车辆*/
	public static final int FLAG_GET_ALL_CAR = 6001;
	
	
	/**好友列表*/
	public static final String METHOD_GET_FRIEND_LIST = BASE_URL+"weiCar/interf/friends/friendList";
	
	/**好友列表*/
	public static final int FLAG_GET_FRIEND_LIST = 5001;
	
	
	/**违章查询的key*/
	public static final String KEY_ILLEGALQUERY = "3c32fe56dc63be76ff886e47aecd87e7";
	
	/**得到违章查询的省市区 */
	public static final String METHOD_GETPAC = "http://v.juhe.cn/wz/citys";
	
	/**请求省市区*/
	public static final int FLAG_GETPAC = 2001;
	
	
	/**请求违章查询结果*/
	public static final String METHOD_ILLEGALQUERY = "http://v.juhe.cn/wz/query";
	
	
	/**违章查询接口*/
	public static final int Flag_ILLEGALQUERY = 2002;
	
	
	/**登陆接口*/
	public static final String METHOD_LOGIN = BASE_URL+"weiCar/interf/customer/login";
	
	/**登陆flag*/
	public static final int FLAG_LOGIN = 3002;
	
	
	/**注册接口*/
	public static final String METHOD_REGISTER = BASE_URL+"weiCar/interf/customer/register";
	/**注册flag*/
	public static final int FLAG_REGISTER = 3003;
	
	
	/**添加车辆*/
	public static final int  FLAG_ADD_CAR = 4001;
			
	public static final String METHOD_ADD_CAR = BASE_URL+"weiCar/interf/car/addCar";
	
	/**存放精度和纬度*/
	public static final String LOCATION_LATITUDE = "LOCATION_LATITUDE";
	
	public static final String LOCATION_LONGITUDE = "LOCATION_LONGITUDE";
	
	
	
	
	
}