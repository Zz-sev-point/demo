package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class FriendshipGraph {
	private List<Person> people = new ArrayList<Person>();
	private Set<String> names = new HashSet<String>();

	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		//should print 1
		System.out.println(graph.getDistance(rachel, ben));
		//should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		//should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		//should print -1
	}
	
	public int getDistance(Person p1, Person p2) {
		if (p1 == p2)
			return 0;
		Map<Person, Integer> dis = new HashMap<>();
		Queue<Person> q = new LinkedList<>();
		q.offer(p1);
		dis.put(p1, 0);
		while (!q.isEmpty()) {
			Person p = q.poll();
			List<Person> Friends = p.getFriends();
			for (Person i : Friends) {
				if (!dis.containsKey(i)) {
					if (i == p2)
						return dis.get(p) + 1;
					q.offer(i);
					dis.put(i, dis.get(p) + 1);
				}
			}
		}
		return -1;
	}

	public void addVertex(Person p) {
		if (names.contains(p.getName())) {
			System.out.println("The name has existed!");
			System.exit(0);
		}
		people.add(p);
		names.add(p.getName());
	}

	public void addEdge(Person p1, Person p2) {
		if(people.contains(p1)&&people.contains(p2))
			p1.addFriend(p2);
		else {
			System.out.println("Can't find the person/people!");
			System.exit(0);
		}
	}

	public List<Person> people() {
		return people;
	}

	public Set<String> names() {
		return names;
	}
}
