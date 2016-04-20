package cn.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class JSONUtil {
	
	/***
	 * ͨ��URL��ַ��ȡjson�ַ���
	 * http://apis.juhe.cn/ip/ip2addr?ip=www.baidu.com&key=appkey0003201
	 * @param 
	 * @return
	 */
	public static String fromUrlToJson(String purl){
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(purl);
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputline = null;
			while((inputline = in.readLine()) != null){
				sb.append(inputline);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * ��json�����ַ����õ�java����
	 * @param jsonStr {"age":20,"date":null,"name":"����"}
	 * @param beanClass
	 * @return
	 */
	public static Object toBean(String jsonStr,Class beanClass){
		return JSONObject.toBean(JSONObject.fromObject(jsonStr), beanClass);
	}
	
	/**
	 * ��json�����ַ����õ�java����,������������
	 * @param jsonStr  {"age":20,"animalList":[{"animalColor":"red","animalName":"dog"},
	 * {"animalColor":"black","animalName":"cat"}],"date":null,"name":"����"}
	 * @param beanClass
	 * @param classMap
	 * @return
	 */
	public static Object toBean(String jsonStr,Class beanClass,Map classMap){
		return JSONObject.toBean(JSONObject.fromObject(jsonStr), beanClass, classMap);
	}
	
	/**
	 * ��һ��json�����ȡ java���󼯺�
	 * @param jsonStr [{"age":10,"name":"����"},{"age":10,"name":"����"},{"age":10,"name":"����"}]
	 * @param beanClass 
	 * @return 
	 */
	public static Collection fromJsonStringToArray(String jsonStr,Class beanClass){
		return JSONArray.toCollection(JSONArray.fromObject(jsonStr), beanClass);
	}
	
	/**
	 * ���������л���JSON����
	 * @param object
	 * @return string
	 */
	public static String serializer(Object object){
		return JSONSerializer.toJSON(object).toString();
	}
	
	 /**
     * ���������л���JSON����
     * @param object Object
     * @param excludes ����Ҫת��������
     * @return String
     */
	public static String serializer(Object object,String[] excludes){
		JsonConfig config = new JsonConfig();
		config.setExcludes(excludes);
		return JSONSerializer.toJSON(object, config).toString();
	}
	

	
	
	public static void main(String[] args) throws Exception {
		List<User> listuser = new ArrayList<User>();
		listuser.add(new User("����",10));
		listuser.add(new User("����",10));
		listuser.add(new User("����",10));
		
		String aa = JSONSerializer.toJSON(listuser).toString();
		System.out.println(aa);
		
		JSONObject jsonObject =JSONObject.fromObject("{ \"users\":" + aa +"}");
		JSONArray array = jsonObject.getJSONArray("users");
		Object[] arrObj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject user = (JSONObject)array.get(i);
			String name = (String)user.get("name");
//			System.out.println(name);
			arrObj[i] = JSONObject.toBean(user,User.class);
		}
		
		List<User> list2 = (List)JSONArray.toCollection(JSONArray.fromObject(aa), User.class);
		System.out.println(list2);
		for (User user : list2) {
			System.out.println(user.getName() + ",," + user.getAge());
		}
		
		String aa2 = JSONSerializer.toJSON(new User("����", 20)).toString();
		System.out.println(aa2);
		User u = (User)JSONObject.toBean(JSONObject.fromObject(aa2), User.class);
		System.out.println(u.getName());
		
		User userAndAddress = new User("����",30);
		userAndAddress.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"));
		userAndAddress.setAddress(new Address("������", "100005"));
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		
		String stru = JSONSerializer.toJSON(userAndAddress,config).toString();
		System.out.println(stru);
		User user = (User)toBean(stru, User.class);
		System.out.println(user.getDate());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(user.getDate()));
		System.out.println(user.getName() + ","  +","+ user.getAddress().getAddressName());
		
		User user2 = new User("����",20);
		user2.setAddress(new Address("������", "200005"));
		List list = new ArrayList();
		list.add(new Animal("dog", "red"));
		list.add(new Animal("cat", "black"));
		user2.setAnimalList(list);
//		String struser2 = JSONSerializer.toJSON(user2).toString();
		JsonConfig config2 = new JsonConfig();
		config2.setExcludes(new String[]{"address"});
		String struser2 = JSONSerializer.toJSON(user2,config2).toString();
		System.out.println(struser2);
		Map map = new HashMap();
		map.put("animalList", Animal.class);
		User user3 = (User)toBean(struser2, User.class,map);
		List list3 = user3.getAnimalList();
		for (Object object : list3) {
			String color = ((Animal)object).getAnimalColor();
			System.out.println(color);
		}
		
		
		
	}
	

	
	
	
	
	
}
