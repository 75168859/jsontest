package cn.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class JSONUtil {

	/***
	 * 通过URL地址获取json字符串
	 * http://apis.juhe.cn/ip/ip2addr?ip=www.baidu.com&key=appkey0003201
	 * 
	 * @param
	 * @return
	 */
	public static String fromUrlToJson(String purl) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(purl);
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputline = null;
			while ((inputline = in.readLine()) != null) {
				sb.append(inputline);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 从json对象字符串得到java对象
	 * 
	 * @param jsonStr
	 *            {"age":20,"date":"2015-01-01","name":"李四"}
	 * @param beanClass
	 * @return
	 */
	public static Object toBean(String jsonStr, Class beanClass) {
		setDataFormat2JAVA();
		return JSONObject.toBean(JSONObject.fromObject(jsonStr), beanClass);
	}

	/**
	 * 从json对象字符串得到java对象,包括集合属性
	 * 
	 * @param jsonStr
	 *            {"age":20,"animalList":[{"animalColor":"red","animalName":
	 *            "dog"},
	 *            {"animalColor":"black","animalName":"cat"}],"date":"2015-01-01"
	 *            ,"name":"赵六"}
	 * @param beanClass
	 * @param classMap
	 * @return
	 */
	public static Object toBean(String jsonStr, Class beanClass, Map classMap) {
		setDataFormat2JAVA();
		return JSONObject.toBean(JSONObject.fromObject(jsonStr), beanClass,
				classMap);
	}

	/**
	 * 从一个json数组获取 java对象集合
	 * 
	 * @param jsonStr
	 *            [{"age":10,"name":"张三"},{"age":10,"name":"张三"},{"age":10,
	 *            "name":"张三"}]
	 * @param beanClass
	 * @return
	 */
	public static Collection<?> fromJsonStringToArray(String jsonStr,
			Class<?> beanClass) {
		setDataFormat2JAVA();
		return JSONArray.toCollection(JSONArray.fromObject(jsonStr), beanClass);
	}

	/**
	 * 从一个json数组获取 java对象集合
	 * 
	 * @param jsonStr
	 *            [{"address":null,"age":10,"animalList":[{"animalColor":"red",
	 *            "animalName"
	 *            :"dog"},{"animalColor":"black","animalName":"cat"}]
	 *            ,"date":null
	 *            ,"name":"张三"},{"address":null,"age":10,"animalList"
	 *            :[],"date":null
	 *            ,"name":"张三"},{"address":null,"age":10,"animalList"
	 *            :[],"date":null,"name":"张三"}]
	 * @param beanClass
	 * @return
	 */
	public static List fromJsonStringToArray(String jsonStr, Class beanClass,
			Map classMap) {
		setDataFormat2JAVA();
		List list = new ArrayList();
		JSONArray array = JSONArray.fromObject(jsonStr);
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, beanClass, classMap));
		}
		return list;
	}

	/**
	 * 根据json串获取一个 java map
	 * 
	 * @param jsonStr
	 *            {animalList=[], address=null, name=张三, age=10, date=null}
	 * @return
	 */
	public static Map getMapFromJson(String jsonStr) {
		setDataFormat2JAVA();
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Map map = new HashMap();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	/**
	 * 将对象序列化成JSON串
	 * 
	 * @param object
	 * @return string
	 */
	public static String serializer(Object object) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor());
		return JSONSerializer.toJSON(object, config).toString();
	}

	/**
	 * 将对象序列化成JSON串
	 * 
	 * @param object
	 *            Object
	 * @param excludes
	 *            不需要转换的属性
	 * @return String
	 */
	public static String serializer(Object object, String[] excludes) {
		JsonConfig config = new JsonConfig();
		config.setExcludes(excludes);
		config.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor());
		return JSONSerializer.toJSON(object, config).toString();
	}

	/**
	 * json日期字符串转换成java date 类型时需要调用此方法
	 */
	private static void setDataFormat2JAVA() {
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd",
						"yyyy-MM-dd HH:mm:ss" }));
	}

	public static void main(String[] args) throws Exception {
		List<User> listuser = new ArrayList<User>();
		listuser.add(new User("张三", 10));
		listuser.add(new User("张三", 10));
		listuser.add(new User("张三", 10));

		String aa = JSONSerializer.toJSON(listuser).toString();
		System.out.println(aa);

		JSONObject jsonObject = JSONObject
				.fromObject("{ \"users\":" + aa + "}");
		JSONArray array = jsonObject.getJSONArray("users");
		Object[] arrObj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject user = (JSONObject) array.get(i);
			String name = (String) user.get("name");
			// System.out.println(name);
			arrObj[i] = JSONObject.toBean(user, User.class);
		}

		List<User> list2 = (List) JSONArray.toCollection(
				JSONArray.fromObject(aa), User.class);
		System.out.println(list2);
		for (User user : list2) {
			System.out.println(user.getName() + ",," + user.getAge());
		}

		String aa2 = JSONSerializer.toJSON(new User("李四", 20)).toString();
		System.out.println(aa2);
		User u = (User) JSONObject.toBean(JSONObject.fromObject(aa2),
				User.class);
		System.out.println(u.getName());

		User userAndAddress = new User("王五", 30);
		userAndAddress.setDate(new SimpleDateFormat("yyyy-MM-dd")
				.parse("2015-01-01"));
		userAndAddress.setAddress(new Address("海淀区", "100005"));

		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor());

		String stru = JSONSerializer.toJSON(userAndAddress, config).toString();
		System.out.println(stru);
		User user = (User) toBean(stru, User.class);
		// JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new
		// String[]{"yyyy-MM-dd"}));
		// User user =
		// (User)JSONObject.toBean(JSONObject.fromObject(stru,config),
		// User.class);
		System.out.println(user.getDate());

		/*
		 * System.out.println(new
		 * SimpleDateFormat("yyyy-MM-dd").format(user.getDate()));
		 * System.out.println(user.getName() + "," +","+
		 * user.getAddress().getAddressName());
		 */

		/*
		 * User user2 = new User("赵六",20); user2.setAddress(new Address("海淀区",
		 * "200005")); List list = new ArrayList(); list.add(new Animal("dog",
		 * "red")); list.add(new Animal("cat", "black"));
		 * user2.setAnimalList(list); // String struser2 =
		 * JSONSerializer.toJSON(user2).toString(); JsonConfig config2 = new
		 * JsonConfig(); config2.setExcludes(new String[]{"address"}); String
		 * struser2 = JSONSerializer.toJSON(user2,config2).toString();
		 * System.out.println(struser2);
		 */
		// Map map = new HashMap();
		// map.put("animalList", Animal.class);
		// User user3 = (User)toBean(struser2, User.class,map);
		// List list3 = user3.getAnimalList();
		// for (Object object : list3) {
		// String color = ((Animal)object).getAnimalColor();
		// System.out.println(color);
		// }

		// Date d = new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01");
		// System.out.println(d);

	}

}
