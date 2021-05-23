/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle     the turtle context
	 * @param sideLength length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		for (int i = 0; i < 4; i++) {
			turtle.forward(sideLength);
			turtle.turn(90);
		}
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		return ((double) 180 - (double) 360 / sides);
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		double exterior = (double) 180 - angle;
		return (int) Math.ceil(360 / exterior);
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle     the turtle context
	 * @param sides      number of sides of the polygon to draw
	 * @param sideLength length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		double angle = (double) 180 - calculateRegularPolygonAngle(sides);
		for (int i = 0; i < sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(angle);
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the Bearing towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentBearing. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentBearing current direction as clockwise from north
	 * @param currentX       current location x-coordinate
	 * @param currentY       current location y-coordinate
	 * @param targetX        target point x-coordinate
	 * @param targetY        target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
			int targetY) {
		double angle = (double) 90 - Math.atan2(targetY - currentY, targetX - currentX) * 180.0 / Math.PI;
		angle = angle - currentBearing;
		return angle < 0 ? (angle + 360) : angle;
	}

	/**
	 * Given a sequence of points, calculate the Bearing adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateBearingToPoint() to implement this function.
	 * 
	 * @param xCoords list of x-coordinates (must be same length as yCoords)
	 * @param yCoords list of y-coordinates (must be same length as xCoords)
	 * @return list of Bearing adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
		List<Double> angles = new ArrayList<>();
		int len = xCoords.size(), currentX = xCoords.get(0), currentY = yCoords.get(0), targetX, targetY;
		double currentbearing = 0.0, angle;
		if (len < 2)
			return angles;
		for (int i = 1; i < len; i++) {
			targetX = xCoords.get(i);
			targetY = yCoords.get(i);
			angle = calculateBearingToPoint(currentbearing, currentX, currentY, targetX, targetY);
			angles.add(angle);
			currentbearing += angle;
			currentbearing = currentbearing % 360;
			currentX = xCoords.get(i);
			currentY = yCoords.get(i);
		}
		return angles;
	}

	/**
	 * Given a set of points, compute the convex hull, the smallest convex set that
	 * contains all the points in a set of input points. The gift-wrapping algorithm
	 * is one simple approach to this problem, and there are other algorithms too.
	 * 
	 * @param points a set of points with xCoords and yCoords. It might be empty,
	 *               contain only 1 point, two points or more.
	 * @return minimal subset of the input points that form the vertices of the
	 *         perimeter of the convex hull
	 */
	public static Set<Point> convexHull(Set<Point> points) {
		int size = points.size();
		if (size < 3)
			return points;
		Set<Point> convexHull = new HashSet<Point>();
		Point[] p = points.toArray(new Point[size]);
		Point start = new Point(Double.MAX_VALUE, Double.MAX_VALUE), current, next;
		double currentbearing = 0.0, angle = Double.MAX_VALUE, nextang;
		// 找到最左或者最左下的那个点
		for (int i = 0; i < size; i++) {
			if (p[i].x() < start.x() || (p[i].x() == start.x() && p[i].y() < start.y()))
				start = p[i];
		}
		current = start;
		next = start;
		do {
			convexHull.add(next);
			current = next;
			for (int i = 0; i < size; i++) {
				if (p[i] != current) {
					nextang = calculateBearingToPoint(currentbearing, (int) current.x(), (int) current.y(),
							(int) p[i].x(), (int) p[i].y());
					if (nextang < angle) {
						angle = nextang;
						next = p[i];
					} else if (nextang == angle) {
						double dis1 = Math.pow(current.x() - next.x(), 2) + Math.pow(current.y() - next.y(), 2);
						double dis2 = Math.pow(current.x() - p[i].x(), 2) + Math.pow(current.y() - p[i].y(), 2);
						if (dis2 > dis1)
							next = p[i];
					}
				}
			}
			currentbearing += angle;
			angle = Double.MAX_VALUE;
		} while (next != start);
		return convexHull;
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {
		for (int i = 0; i < 350; i += 7) {
			turtle.color(PenColor.BLUE);
			turtle.forward(i);
			turtle.turn(46);
			turtle.color(PenColor.CYAN);
			turtle.forward(i + 1);
			turtle.turn(46);
			turtle.color(PenColor.GREEN);
			turtle.forward(i + 2);
			turtle.turn(46);
			turtle.color(PenColor.YELLOW);
			turtle.forward(i + 3);
			turtle.turn(46);
			turtle.color(PenColor.ORANGE);
			turtle.forward(i + 4);
			turtle.turn(46);
			turtle.color(PenColor.RED);
			turtle.forward(i + 5);
			turtle.turn(46);
			turtle.color(PenColor.PINK);
			turtle.forward(i + 6);
			turtle.turn(46);
			turtle.color(PenColor.MAGENTA);
			turtle.forward(i + 6);
			turtle.turn(46);
		}
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

//        drawSquare(turtle, 40);
        drawRegularPolygon(turtle, 6, 40);
//		 draw the window
//		drawPersonalArt(turtle);
		turtle.draw();
	}

}
