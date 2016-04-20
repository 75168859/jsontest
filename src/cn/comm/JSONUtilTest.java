package cn.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class JSONUtilTest {
	
	@Test
	public void toBean(){
		try {
			User user = new User("张三",12);
			user.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"));
			user.setAddress(new Address("海淀", "10004"));
			String str = JSONUtil.serializer(user);
			System.out.println(str);
			User u = (User)JSONUtil.toBean(str, User.class);
			System.out.println(u.getName() + "," + u.getDate() + "," + u.getAddress().getAddressName());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void toBean2(){
		try {
			User user = new User("张三",12);
			user.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"));
			user.setAddress(new Address("海淀", "10004"));
			List list = new ArrayList(); 
			list.add(new Animal("dog","red")); 
			list.add(new Animal("cat", "black"));
			user.setAnimalList(list);
			String str = JSONUtil.serializer(user);
			System.out.println(str);
			
			Map map = new HashMap();
			map.put("animalList", Animal.class);
			User u = (User)JSONUtil.toBean(str, User.class,map);
			List list2 = u.getAnimalList();
			for (Object object : list2) {
				 String color = ((Animal)object).getAnimalColor();
				 System.out.println(color);
			}
			System.out.println(u.getName() + "," + u.getDate() + "," + u.getAddress().getAddressName());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void fromJsonStringToArray(){
		List<User> listuser = new ArrayList<User>();
		listuser.add(new User("张三", 10));
		listuser.add(new User("张三", 10));
		listuser.add(new User("张三", 10));
		String aa = JSONUtil.serializer(listuser);
		System.out.println(aa);
		List<User> list = (List)JSONUtil.fromJsonStringToArray(aa, User.class);
		for (User u : list) {
			
		}
	}
	
	@Test
	public void fromJsonStringToArray2(){
		List<User> listuser = new ArrayList<User>();
		User u1 = new User("张三", 10);
		List list1 = new ArrayList(); 
		list1.add(new Animal("dog","red")); 
		list1.add(new Animal("cat", "black"));
		u1.setAnimalList(list1);
		
		User u2 = new User("张三", 10);
		User u3 = new User("张三", 10);
		listuser.add(u1);
		listuser.add(u2);
		listuser.add(u3);
		String aa = JSONUtil.serializer(listuser);
		System.out.println(aa);
		
		Map map = new HashMap();
		map.put("animalList", Animal.class);
		List<User> list = (List)JSONUtil.fromJsonStringToArray(aa, User.class,map);
		for (User u : list) {
			List<Animal> ll = u.getAnimalList();
			for (Animal animal : ll) {
				System.out.println(animal.getAnimalName());
			}
		}
		
	}
	
	@Test
	public void getMapFromJson(){
		User u1 = new User("张三", 10);
		String jsonStr = JSONUtil.serializer(u1);
		Map map = JSONUtil.getMapFromJson(jsonStr);
		System.out.println(map);
		
	}
	
	
}
