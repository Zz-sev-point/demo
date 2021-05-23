package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipGraphTest {
	
	@Test
	public void addVertexTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person p = new Person("abc");
		graph.addVertex(p);
		assertEquals(p, graph.people().get(graph.people().size()-1));
	}
	
	@Test
	public void addEdgeTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person p1 = new Person("a");
		Person p2 = new Person("b");
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addEdge(p1, p2);
		assertEquals(p2, p1.getFriends().get(p1.getFriends().size()-1));
	}
	
	@Test
	public void getDistanceTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person p1 = new Person("a");
		Person p2 = new Person("b");
		Person p3 = new Person("c");
		Person p4 = new Person("d");
		Person p5 = new Person("e");
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);
		graph.addVertex(p4);
		graph.addVertex(p5);
		graph.addEdge(p1, p2);
		graph.addEdge(p2, p1);
		graph.addEdge(p1, p3);
		graph.addEdge(p3, p1);
		graph.addEdge(p2, p3);
		graph.addEdge(p3, p2);
		graph.addEdge(p2, p4);
		graph.addEdge(p4, p2);
		graph.addEdge(p3, p4);
		graph.addEdge(p4, p3);
		assertEquals(1, graph.getDistance(p1, p2));
		assertEquals(1, graph.getDistance(p1, p3));
		assertEquals(2, graph.getDistance(p1, p4));
		assertEquals(-1, graph.getDistance(p1, p5));
		assertEquals(1, graph.getDistance(p2, p3));
		assertEquals(1, graph.getDistance(p2, p4));
		assertEquals(-1, graph.getDistance(p2, p5));
		assertEquals(1, graph.getDistance(p3, p4));
		assertEquals(-1, graph.getDistance(p3, p5));
		assertEquals(-1, graph.getDistance(p4, p5));
	}
}