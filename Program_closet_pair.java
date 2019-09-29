package csc421;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import stdlib.StdIn;

public class Program_closet_pair {

	static ArrayList<Point> allpoints = new ArrayList<Point>();
	static ArrayList<Point> allpoints_x = new ArrayList<Point>();
	static ArrayList<Point> allpoints_y = new ArrayList<Point>();

	static double get_distance(Point a, Point b) {

		return (double) Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
	}

	static ArrayList<Point> brutalforce(ArrayList<Point> Q, int n) {

		double min = 9999999;
		ArrayList<Point> themin = new ArrayList<Point>();
		for (int i = 0; i < n; ++i)
			for (int j = i + 1; j < n; ++j)
				if (get_distance(Q.get(i), Q.get(j)) < min) {
					min = get_distance(Q.get(i), Q.get(j));
					themin.clear();
					themin.add(Q.get(i));
					themin.add(Q.get(j));
				}
		return themin;
	}

	static ArrayList<Point> find_closest_pair(ArrayList<Point> X_array, ArrayList<Point> Y_array, int n) { // this
																											// X_array
																											// is sorted
																											// wrt x
		// divide is recursive, merge maybe is not. all apply Mid points when merge
		// back.
		if (n <= 3) {
			ArrayList<Point> Ans = new ArrayList<Point>();
			for (Point i : X_array) {
//				System.out.println(i);
			}
			return brutalforce(X_array, n);

		} else {
			// for Sl and Sr
			int mid = (int) (X_array.size() / 2);
			ArrayList<Point> XL = new ArrayList<Point>(); // xleft
			ArrayList<Point> XR = new ArrayList<Point>(); // xright
			ArrayList<Point> YL = new ArrayList<Point>(); // yleft
			ArrayList<Point> YR = new ArrayList<Point>(); // yright

//			for(int i =0;i< mid;i++) {
//				A.add(X_array.get(i));
////				System.out.println("A: "+X_array.get(i));
//			}
//			for(int i =mid; i<X_array.size();i++) {
//				B.add(X_array.get(i));
////				System.out.println("B: "+X_array.get(i));
//			}

			// 5, mid = 2, then 0 1 2 3 4, take 2,1; if 6, mid =3 , 0 1 2 3 4 5 mid = take 2
			// 3
			double l = (double) (X_array.get(mid - 1).getX() + X_array.get(mid).getX()) / 2; // x=l
//			System.out.println(l);
			for (Point i : X_array) {
//			System.out.println("X"+i);
			}
			for (int i = 0; i < Y_array.size(); i++) {
				if (Y_array.get(i).getX() < l) {
					YL.add(Y_array.get(i));
//					System.out.println("YL"+i);
				} else {
					YR.add(Y_array.get(i));
//					System.out.println("YR"+i);
				}
			}
			for (int i = 0; i < X_array.size(); i++) {
				if (X_array.get(i).getX() < l) {
					XL.add(X_array.get(i));
//					System.out.println("XL"+i);
				} else {
					XR.add(X_array.get(i));
//					System.out.println("XR"+i);
				}
			}

//			ArrayList<Point> theta = new ArrayList<Point>();
			ArrayList<Point> L_min = new ArrayList<Point>();
			ArrayList<Point> R_min = new ArrayList<Point>();
			L_min = find_closest_pair(XL, YL, mid);
			R_min = find_closest_pair(XR, YR, n - mid);
			double A_min = get_distance(L_min.get(0), L_min.get(1));
			double B_min = get_distance(R_min.get(0), R_min.get(1));

			ArrayList<Point> side_min = new ArrayList<Point>();

			double theta;
			if (A_min < B_min) {
				theta = A_min;
				side_min.add(L_min.get(0));
				side_min.add(L_min.get(1));
			} else {
				theta = B_min;
				side_min.add(R_min.get(0));
				side_min.add(R_min.get(1));
			}

			// Smid
			ArrayList<Point> Smid = new ArrayList<Point>();

			for (int i = 0; i < n; i++) {
				if (l - theta <= Y_array.get(i).getX() && Y_array.get(i).getX() <= l + theta) {
					Smid.add(Y_array.get(i));
				}
			}

			// Ymid
			ArrayList<Point> Ymid = new ArrayList<Point>();
			for (Point i : allpoints_y) {
				if (Smid.contains(i)) {
					Ymid.add(i);
//					if(i.getX()==42980 ||i.getX()==42989)
//					System.out.println("Ymid add "+i);
				}
			}

			// Step 8
			double mid_min = 99999999;
			ArrayList<Point> mid_closet = new ArrayList<Point>();

//			System.out.println(Ymid.size());

			if (Ymid.size() >= 2) {

				mid_min = get_distance(Ymid.get(0), Ymid.get(1));
				mid_closet.add(Ymid.get(0));
				mid_closet.add(Ymid.get(1));
				for (int i = 0; i < Ymid.size() - 1; i++) {
					for (int i1 = i + 1; i1 < i + 8 && i1 < Ymid.size(); i1++) {
						if (i != i1 && mid_min > get_distance(Ymid.get(i), Ymid.get(i1))) {
							mid_closet.clear();
							mid_min = get_distance(Ymid.get(i), Ymid.get(i1));
							mid_closet.add(Ymid.get(i));
							mid_closet.add(Ymid.get(i1));

						}
					}
				}
			}

			//
			if (theta > mid_min) {
//				System.out.println("this is Mid_min");
				return mid_closet;
			} else {
//				System.out.println("this is side_min");
				return side_min;
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdIn.fromFile("data/1000points.txt");

		int point_count = 0;
		// set up for input data
		while (StdIn.hasNextLine()) {
			String[] input = StdIn.readLine().split(" ");

			Point cur_point = new Point(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
			allpoints.add(point_count, cur_point);
			allpoints_x.add(point_count, cur_point);
			allpoints_y.add(point_count, cur_point);
//			System.out.println(allpoints.get(point_count));
			point_count++;
		}
		// Sort wrt x and y
//		allpoints_x = allpoints;
//		allpoints_y = allpoints;  // here we can't give allpoints to array directly, it must have its own references
		allpoints_x.sort(Comparator.comparing(Point::getX));
		allpoints_y.sort(Comparator.comparing(Point::getY));

//		for(Point a: allpoints_y) {
//			System.out.println(a);
//		}
		ArrayList<Point> Ans = new ArrayList<Point>();
		Ans = find_closest_pair(allpoints_x, allpoints_y, allpoints_x.size());

		for (Point i : Ans) {
			System.out.println(i);
		}
		System.out.println("The minimum distance is: " + get_distance(Ans.get(0), Ans.get(1)));

	}

}
