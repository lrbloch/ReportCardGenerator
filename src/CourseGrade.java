
public class CourseGrade {
	
	private String courseName;
	private String percentage;
	private String letterGrade;
	
	
	public CourseGrade(String courseName2, String coursePercentage, String courseLetterGrade) {
		// TODO Auto-generated constructor stub
		this.courseName = courseName2;
		this.percentage = coursePercentage;
		this.letterGrade = courseLetterGrade;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getPercentage() {
		return percentage;
	}
	public String getLetterGrade() {
		return letterGrade;
	}

}
