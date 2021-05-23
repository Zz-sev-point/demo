package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String name;
	private List<Person>Friends;
	
	public Person(String name) {
		this.name = name;
		Friends = new ArrayList<>();
	}
	
	public void addFriend(Person p) {
		Friends.add(p);
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Person> getFriends(){
		return this.Friends;
	}
}
