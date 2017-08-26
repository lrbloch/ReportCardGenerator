import java.util.HashMap;

/**
 * 
 */

/**
 * @author Laura
 *
 */
public class Student {
	private int id;
	private String name;
	private Double gpa;
	private HashMap<String, String> courseGrades;
	
	
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


	/**
	 * @return the classes
	 */
	public HashMap<String, String> getCourses() {
		return courseGrades;
	}


	/**
	 * @param newCourse new course to add to list
	 */
	public void addCourse(String newCourse, String grade) {
		if(this.courseGrades == null)
		{
			this.courseGrades = new HashMap<String, String>();
		}
		this.courseGrades.put(newCourse, grade);
	}

}
