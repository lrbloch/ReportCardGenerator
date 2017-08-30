
/**
 * @author Laura
 *
 */
public class Course {

	/**
	 * @param args
	 */
	private int hours;
	private String courseName;
	private String instructorName;
	private boolean isTypeA;
	
	public Course()
	{
		this.isTypeA = false;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public boolean isTypeA() {
		return isTypeA;
	}

	public void setTypeA(boolean isTypeA) {
		this.isTypeA = isTypeA;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

}
