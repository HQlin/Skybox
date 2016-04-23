package com.example.skybox.util;

import android.util.FloatMath;

public class Geometry {
	
	public static class Point{
		public final float x,y,z;
		public Point(float x, float y, float z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Point translateY(float distance){
			return new Point(x, y+distance, z);
		}

		public Point translate(Vector vector) {
			// TODO Auto-generated method stub
			return new Point(x+vector.x, y+vector.y, z+vector.z);
		}
	}
	
	public static class Circle{
		public final Point center;
		public final float radius;
		
		public Circle(Point center, float radius) {
			// TODO Auto-generated constructor stub
			this.center = center;
			this.radius = radius;
		}
		
		public Circle scale(float scale){
			return new Circle(center, radius*scale);
		}
	}

	public static class Cylinder{
		public final Point center;
		public final float radius;
		public final float height;
		
		public Cylinder(Point center, float radius, float height) {
			// TODO Auto-generated constructor stub
			this.center = center;
			this.radius = radius;
			this.height = height;
		}
	}
	
	public static class Ray{
		public final Point point;
		public final Vector vector;
		
		public Ray(Point point, Vector vector) {
			// TODO Auto-generated constructor stub
			this.point = point;
			this.vector = vector;
		}
	}
	
	public static class Vector{
		public final float x,y,z;
		
		public Vector(float x, float y, float z) {
			// TODO Auto-generated constructor stub
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public float length() {
			// TODO Auto-generated method stub
			return FloatMath.sqrt(x*x+y*y+z*z);
		}

		/**
		 * 计算两个向量的交叉乘积，即得到向量长度是这两个向量构造三角形面积的两倍
		 * @param other
		 * @return
		 */
		public Vector crossProduce(Vector other) {
			// TODO Auto-generated method stub
			return new Vector(
					(y*other.z) - (z*other.y), 
					(z*other.x) - (x*other.z),
					(x*other.y) - (y*other.x));
		}

		/**
		 * 两个向量的点积
		 * @param other
		 * @return
		 */
		public float dotProduct(Vector other) {
			// TODO Auto-generated method stub
			return x*other.x
					+ y*other.y
					+ z*other.z;
		}

		/**
		 * 向量缩放
		 * @param f
		 * @return
		 */
		public Vector scale(float f) {
			// TODO Auto-generated method stub
			return new Vector(x*f, y*f, z*f);
		}
	}
	
	public static class Sphere{
		public final Point center;
		public final float radius;
		
		public Sphere(Point center, float radius) {
			// TODO Auto-generated constructor stub
			this.center = center;
			this.radius = radius;
		}
	}

	public static Vector vectorBetween(Point from, Point to) {
		// TODO Auto-generated method stub
		return new Vector(to.x-from.x, to.y-from.y, to.z-from.z);
	}

	public static boolean intersects(Sphere sphere, Ray ray) {
		// TODO Auto-generated method stub
		return distanceBetween(sphere.center, ray) < sphere.radius;
	}

	private static float distanceBetween(Point point, Ray ray) {
		//射线起点和终点到球心的向量
		Vector p1ToPoint = vectorBetween(ray.point, point);
		Vector p2ToPoint = vectorBetween(ray.point.translate(ray.vector), point);
		//以上三点构成三角形，两向量的交叉乘积向量长度为三角形面积的两倍
		float areaOfTriangleTimesTwo = p1ToPoint.crossProduce(p2ToPoint).length();
		float lengthOfBase = ray.vector.length();
		//射线与球距离 = 两向量的交叉乘积向量长度/射线长度
		return areaOfTriangleTimesTwo/lengthOfBase;
	}
	
	public static class Plane{
		public final Point point;
		public final Vector normal;
		
		public Plane(Point point, Vector normal) {
			// TODO Auto-generated constructor stub
			this.point = point;
			this.normal = normal;
		}
	}
	
	public static Point intersectionPoint(Ray ray, Plane plane){
		//计算射线到平面的向量
		Vector rayToPlaneVector = vectorBetween(ray.point, plane.point);
		//缩放因子 = 射线到平面的向量与平面法向量向量之间的点积 / 射线向量与平面法向量向量的点积
		float scaleFactor = rayToPlaneVector.dotProduct(plane.normal)
				/ ray.vector.dotProduct(plane.normal);
		//使用缩放因子平移射线点找出相交点
		Point intersectionPoint = ray.point.translate(ray.vector.scale(scaleFactor));
		return intersectionPoint;
	}
}
