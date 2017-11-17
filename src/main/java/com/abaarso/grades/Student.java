package com.abaarso.grades;
import java.util.ArrayList;

/**
 * @author Laura
 *
 */


public class Student {
	private int id;
	private String name;
	private Double gpa;
	private ArrayList<CourseGrade> courseGrades;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the gPA
	 */
	public double getgpa() {
		return gpa;
	}


	/**
	 * @param curGpa the gPA to set
	 */
	public void setgpa(Double curGpa) {
		gpa = curGpa;
	}

	public ArrayList<CourseGrade> getCourseGrades() {
		return courseGrades;
	}


	public void addStudentCourse(String courseName, String coursePercentage, String courseLetterGrade) {
		CourseGrade course = new CourseGrade(courseName, coursePercentage, courseLetterGrade);
		if(this.courseGrades == null)
		{
			this.courseGrades = new ArrayList<CourseGrade>();
		}
		this.courseGrades.add(course);
	}

}
