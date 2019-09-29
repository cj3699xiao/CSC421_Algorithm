package csc421;

import java.util.*;

import algs13.Stack;
import stdlib.StdIn;
import java.awt.*;

public class Programhw {
	static ArrayList<Point> allpoints = new ArrayList<Point>();
	static ArrayList<Point> polarorder_points = new ArrayList<Point>();
	// static ArrayList<Point> correctorder_points = new ArrayList<Point>();
	static Stack<Point> correctorder_points = new Stack<Point>();
	static Point p0;
	static int n;
	static Point farx = new Point(9999999, 0);

	static Point Top;
	static Point nexttotop;
	static Point pi;
	
	static boolean flag = false;

	public static double distance(Point B) {

		double dist = Math.sqrt((B.x - p0.x) * (B.x - p0.x) + (B.y - p0.y) * (B.y - p0.y));
		return dist;
	}

//	public static double polar_angle(Line2D line1, Line2D line2)
//	{
//	    double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
//	                               line1.getX1() - line1.getX2());
//	    double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
//	                               line2.getX1() - line2.getX2());
//	    return angle1-angle2;
//	}
//	

	public static int polar_angle_compare(Point A, Point B) {

		Point Avector = new Point(A.x - p0.x, A.y - p0.y);
		Point Bvector = new Point(B.x - p0.x, B.y - p0.y);

		int number = Avector.x * Bvector.y - Avector.y * Bvector.x;

		if (number > 0) {
			return 1; // p0A smaller than p0B
		} else if (number < 0) {
			return 0; // p0A greater than p0B
		} else
			return 2; // same

	}

	public static int ifleft(Point A, Point target, Point pi) { // target p4, A p3, pi p2

		Point vectorApi = new Point(A.x - pi.x, A.y - pi.y); // vector P3P2
		Point targetA = new Point(target.x - A.x, target.y - A.y); // vector P4P3

		int number = vectorApi.x * targetA.y - vectorApi.y * targetA.x;

		if (number > 0) { // left from p3p2 to p4p3
			return 1;
		}
//		else if (number == 0)
//			return 2;
		else {
//			System.out.println(number);
			return 0;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdIn.fromFile("data/case1	.txt"); // Change this to your txt file address

		while (StdIn.hasNextLine()) {
			String[] input_point = StdIn.readLine().split(" ");
			// System.out.println(input_point);
			int cur_x = Integer.parseInt(input_point[0]);
			int cur_y = Integer.parseInt(input_point[1]);
			// System.out.println(cur_x+""+cur_y);
			Point cur = new Point(cur_x, cur_y);
			allpoints.add(cur);
		}

		p0 = allpoints.get(0);

//		n = allpoints.size(); // input size n

		// Step 1 to find p0
		for (Point i : allpoints) {
			if (i.getY() < p0.getY()) {
				p0 = i;
			} else if (i.getY() == p0.getY()) {
				if (i.getX() < p0.getX()) {
					p0 = i;
				}
			}
		}

		//System.out.println(p0);

		allpoints.remove(p0);
		polarorder_points.add(p0);

		// System.out.println(allpoints);
		int num_points = allpoints.size();

		// Step 2 sort with polar-angle with respect to p0, I didn't find good way to
		// sort it with respect to angle, so i did not make it O(nlgn)

		// System.out.println(Math.toDegrees(polar_angle(allpoints.get(1)))); // to
		// check degree

		// Line2D linex = new Line2D.Double(p0,farx);

		for (int i = 0; i < num_points; i++) {
			Point curmin_Polar = allpoints.get(0);
			// Line2D curline = new Line2D.Double(p0,curmin_Polar);

			for (int k = 1; k < allpoints.size(); k++) {
				// Line2D thisline = new Line2D.Double(p0,allpoints.get(k));
				Point other = allpoints.get(k);

				if (polar_angle_compare(curmin_Polar, other) == 1) {// increasing order
					// curmin_Polar = allpoints.get(k);
				} else if (polar_angle_compare(curmin_Polar, other) == 0) {
					curmin_Polar = allpoints.get(k);
				} else {
					if (distance(curmin_Polar) < distance(allpoints.get(k))) {
						// delete directly ??
						curmin_Polar = allpoints.get(k);
					}

				}
			}
			polarorder_points.add(curmin_Polar);
			allpoints.remove(curmin_Polar);

		}

		int sizeofpolar = polarorder_points.size();
		//System.out.println(sizeofpolar);
		ArrayList<Point> removelist = new ArrayList<Point>();

		for (int i4 = 0; i4 < (polarorder_points.size() - 1); i4++) { // discard coline point with short distance
			// Line2D thisone = new Line2D.Double(p0,polarorder_points.get(i4));
			// Line2D nextone = new Line2D.Double(p0,polarorder_points.get(i4+1));
			// System.out.println(i4);
			if (polar_angle_compare(polarorder_points.get(i4), polarorder_points.get(i4 + 1)) == 2) {

				if (distance(polarorder_points.get(i4)) < distance(polarorder_points.get(i4 + 1))) {
					removelist.add(polarorder_points.get(i4));
				} else if (distance(polarorder_points.get(i4)) > distance(polarorder_points.get(i4 + 1))) {
					removelist.add(polarorder_points.get(i4 + 1));
				} else {

				}

			}
		}

		//System.out.println(removelist);

		for (Point remove : removelist) {
			polarorder_points.remove(remove);
		}

		//System.out.println(polarorder_points + "Polarorder list");
		// step 3 already finished
		// step 4 push p0 p1 p2

		correctorder_points.push(p0); // first point
		correctorder_points.push(polarorder_points.get(0));

		if (polarorder_points.size() >= 2) {
			correctorder_points.push(polarorder_points.get(1));
		}

//		Point asd = new Point(3,1);
//		Point sdf = new Point(2,4);
//		System.out.println(ifleft(sdf,asd));
		n = polarorder_points.size();
//		System.out.println(n + " " + correctorder_points.size());

		// step 5
		for (int i2 = 2; i2 < n; i2++) {
			flag = false;
			Top = polarorder_points.get(i2);
			//System.out.println("check " +i2);
			
			nexttotop = correctorder_points.pop();
			pi = correctorder_points.pop();
			correctorder_points.push(pi);
			correctorder_points.push(nexttotop); // we are not take it out right now, just need the value
			//System.out.println(Top+"   top");

			do {
				if (ifleft(nexttotop, Top, pi) == 1) {
					correctorder_points.push(Top);
					//System.out.println("add " +Top);
					flag = true;					
				}

				else if(ifleft(nexttotop, Top, pi) == 0){
					Point a =correctorder_points.pop();
					//System.out.println("get rid of point"+a);
					
					nexttotop = correctorder_points.pop();
					//System.out.println(nexttotop+"nexttop");
					pi = correctorder_points.pop();
					//System.out.println(pi+"next pi");
					
					correctorder_points.push(pi);
					correctorder_points.push(nexttotop);
					
				}

				
			} while (ifleft(nexttotop, Top, pi) == 0 && flag == false);
			

			correctorder_points.push(Top);
			//System.out.println("add " +Top);
		}

		Point last1 = correctorder_points.pop();
		Point last2 = correctorder_points.pop();
		if(last1 == last2) {
			correctorder_points.push(last2);
		}else {
			correctorder_points.push(last2);
			correctorder_points.push(last1);
		}
		
		
		
		int thesize = correctorder_points.size();
		ArrayList<Point> ans = new ArrayList<Point>();

		for (int i3 = 0; i3 < thesize; i3++) {
			// System.out.println("asdasd");
			ans.add(correctorder_points.pop());
		}
		for (int m = thesize - 1; m != -1; m--) {
			System.out.println(ans.get(m));
		}

	}
}
