import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class ExcelParser {

	static HashMap<String, Integer> courseHours;
	static HashMap<String, Double> gradeGPAs;
	static HashMap<String, Double> typeAGradeGPAs;
	static HashMap<Integer, Student> students = new HashMap<Integer, Student>();
	static HashMap<String, Course> allCourses = new HashMap<String, Course>();

	private static String GradeSpreadsheetPath = "Resources/Grades.csv";
	private static String ReportCardPath = System.getProperty("user.dir") + "/ReportCard.csv";
	private static String ReportCardHeader = "2017-2018 Term 1 Midterm Grade Report\n";

	@SuppressWarnings("unchecked")
	public static void parse() throws FileNotFoundException {

		try {
			FileInputStream fileInputStream = new FileInputStream("Resources/courseHoursMap.txt");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			courseHours = new HashMap<String, Integer>();
			courseHours = (HashMap<String, Integer>) objectInputStream.readObject();
			objectInputStream.close();
			
//			FileOutputStream fileOutputStream = new FileOutputStream("Resources/courseHoursMap.txt"); 
//			ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
//			objectOutputStream.writeObject(courseHours); 
//			objectOutputStream.close();

			// courseHours = (HashMap<String, Integer>)
			// readStringIntHashMap("Resources/courseHoursMap.txt");
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println(e1.getMessage());
			GeneratorGui.showError(e1.getMessage());
		}

		// Properties properties = new Properties();
		// properties.putAll(typeAGradeGPAs);
		// try (OutputStream output = new
		// FileOutputStream("src/ExcelParser.properties")){
		// try {
		// properties.store(output, null);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// System.err.println(e.getMessage());
		// e.printStackTrace();
		// }
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// System.err.println(e1.getMessage());
		// }
		//
		// properties = new Properties();
		// properties.putAll(gradeGPAs);
		// try (OutputStream output = new
		// FileOutputStream("src/ExcelParser.properties")){
		// try {
		// properties.store(output, null);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// System.err.println(e.getMessage());
		// e.printStackTrace();
		// }
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// System.err.println(e1.getMessage());
		// }
		// properties = new Properties();
		// properties.putAll(courseHours);
		// try (OutputStream output = new
		// FileOutputStream("src/CourseHours.properties")){
		// try {
		// properties.store(output, null);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// System.err.println(e.getMessage());
		// e.printStackTrace();
		// }
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// System.err.println(e1.getMessage());
		// }

		if (courseHours == null) {
			// GeneratorGui.showError("courseHours is null");
			System.err.println("coursehours is null");
		}
		// courseHours = parseStringToInt(CourseHourPath);

		BufferedReader br = null;
		String curLine = "";
		try {
			int curId = 0;
			Student curStudent = new Student();
			br = new BufferedReader(new FileReader(GradeSpreadsheetPath));

			// skip first row (titles)
			br.readLine();

			while ((curLine = br.readLine()) != null)// && count < 1)
			{
				if (!curLine.contains(",")) {
					break;
				}
				String[] row = curLine.split(",");
				if (row.length == 0) {
					break;
				}
				String courseName = "";
				String courseGrade = "";
				String instructorName = "";
				String coursePercentage = "";
				int columnIndex = 0;
				StringBuilder curName = new StringBuilder();

				while (columnIndex < row.length && row != null) {
					String curCell = row[columnIndex];

					// System.out.printf("columnIndex: %d, Cell: %s\n", columnIndex, curCell);
					switch (columnIndex) {
					case 8:
						//Get student id
						curId = Integer.parseInt(curCell);
						if (students.get(curId) != null) {
							curStudent = students.get(curId);
						} else {
							curStudent = new Student();
						}
						curStudent.setId(curId);
						break;

					case 4:
						//get course name
						courseName = curCell;
						break;
					case 5:
						//get instructor's name
						instructorName = curCell;
						break;
					case 6:
						//get student's name
						curName.append(curCell);
						break;
					case 7:
						//get student's last name
						curName.append(" " + curCell);
						break;
					case 9:
						//get student's grade
						courseGrade = curCell;
						break;
					case 10:
						//get student's percentage
						coursePercentage = curCell;
						break;
					}
					columnIndex++;
				}

				if (!allCourses.containsKey(courseName)) {
					Course newCourse = new Course();
					newCourse.setCourseName(courseName);
					newCourse.setHours(0);
					if (courseHours.get(courseName) == null) {
						GeneratorGui.showError("Couldn't find hours for course: " + courseName);
						System.err.println("Couldn't find hours for course: " + courseName);
						break;
					} else {
						System.out.println(courseHours.get(courseName));
						newCourse.setHours(courseHours.get(courseName));
					}

					// todo: use regular expressions
					if (courseName.contains("7A ") || courseName.contains("8A ") || courseName.contains("9A ")
							|| courseName.contains("10A ") || courseName.contains("11A ")
							|| courseName.contains("12A ")) {
						System.out.println("Type A Course Name: " + courseName);
						newCourse.setTypeA(true);
					}
					newCourse.setInstructorName(instructorName);
					allCourses.put(courseName, newCourse);
				}

				if (!(courseName == null) && !(courseHours.get(courseName) == null)) {
					curStudent.addStudentCourse(courseName, coursePercentage, courseGrade);
					if (curStudent.getName() == null) {
						curStudent.setName(curName.toString());
					} else if (!curStudent.getName().equals(curName.toString())) {
						String errorMessage = String.format(
								"Error - you have two students ('%s' and '%s') with the same ID (%d)!",
								curStudent.getName(), curName.toString(), curId);
						GeneratorGui.showError(errorMessage);
						return;
					}
					students.put(curId, curStudent);
				}
			}

			for(Course c : allCourses.values()) { 
				System.out.println(c.getCourseName());
				System.out.println(c.getHours()); 
				System.out.println(c.getInstructorName());
				System.out.println("Type A?: " + c.isTypeA()); 
			}

			calculateGpas();

		} catch (FileNotFoundException e) {
			System.err.printf(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.printf(e.toString());
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.err.printf(e.toString());
					e.printStackTrace();
				}
			}
		}

	}

	private static void calculateGpas() throws IOException {
		FileWriter writer = new FileWriter(ReportCardPath);

		for (int studentId : students.keySet()) {
			if (students.get(studentId) == null) {
				System.err.println("Couldn't find student ID" + studentId);
			}
			Student s = students.get(studentId);
			System.out.println("----------------");
			System.out.printf("StudentId: %d \t Student Name: %s\n", s.getId(), s.getName());
			System.out.println("Courses:");
			ArrayList<CourseGrade> courseGrades = s.getCourseGrades();

			Double curGpa = 0.00;
			int totalHours = 0;
			StringBuffer buff = new StringBuffer();

			// for (String key : courses.keySet()) {
			for (CourseGrade courseGrade : courseGrades) {
				double gpa = 0;
				Course genericCourse = allCourses.get(courseGrade.getCourseName());

				System.out.println("Course name: " + courseGrade.getCourseName());

				int curHours = courseHours.get(courseGrade.getCourseName());
				System.out.println("Course hours: " + curHours);
				String letterGrade = courseGrade.getLetterGrade();
				//System.out.println("Letter grade: " + letterGrade);
				gpa = getCourseGPA(letterGrade, genericCourse.isTypeA());
				if(gpa == 666) {
					//letter grade can't be looked up, let's not mess up their GPA
					curHours = 0;
					gpa = 0;
				}
				//System.out.println("Course gpa: " + gpa);
				curGpa = (curGpa + (gpa * curHours));
				totalHours += curHours;
				System.out.println("Total Hours: " + totalHours);

				buff.append(courseGrade.getCourseName());
				buff.append("," + genericCourse.getInstructorName());
				buff.append("," + letterGrade);
				buff.append("," + courseGrade.getPercentage());
				buff.append("," + curHours);
				buff.append("," + String.format("%.2f", gpa));// Double.toString(gpa));
				buff.append("\n");

			}
			// calculate overall GPA
			curGpa = (curGpa / totalHours);
			// round to two decimal places
			// curGpa = round(curGpa, 2);
			s.setgpa(curGpa);

			//System.out.println("Overall GPA: " + String.format("%.2f", s.getgpa()));
			writer.write(ReportCardHeader);

			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			Date date = new Date();
			writer.write("(Updated " + df.format(date) + ")\n"); // (Updated September 7, 2016)

			writer.write(s.getName() + ',');
			writer.write("ID,");
			writer.write(Integer.toString(s.getId()) + ", ,");
			writer.write("GPA,");
			writer.write(String.format("%.2f", s.getgpa()) + '\n');
			writer.write("Class, Instructor, Grade, Percentage, Hours, GPA\n");
			writer.write(buff.toString());
			writer.write("\n\n");
		}

		writer.flush();
		writer.close();
		GeneratorGui.showComplete("Created File " + ReportCardPath + "!");

	}

	private static double getCourseGPA(String letterGrade, boolean typeA) {
		
		double gpa = 666;
		Boolean plus = letterGrade.contains("+");
		Boolean minus = letterGrade.contains("-");
		
		if(letterGrade.contains("A") || letterGrade.contains("a"))
		{
			if(plus) gpa = (typeA) ? 5.00 : 4.333;
			else if(minus) gpa = (typeA) ? 4.33 : 3.667;
			else gpa = (typeA) ? 4.667 : 4.00;
		}
		else if(letterGrade.contains("B") || letterGrade.contains("b"))
		{
			if(plus) gpa = (typeA) ? 4.00 : 3.333;
			else if(minus) gpa = (typeA) ? 3.33 : 2.667;
			else gpa = (typeA) ? 3.667 : 3.00;
		} else if(letterGrade.contains("C") || letterGrade.contains("c"))
		{
			if (plus) gpa = (typeA) ? 3.00 : 2.333;
			else if (minus) gpa = (typeA) ? 2.33 : 1.667;
			else gpa = (typeA) ? 2.667 : 2.00;
		}else if(letterGrade.contains("D") || letterGrade.contains("d"))
		{
			if(plus) gpa = (typeA) ? 2.00 : 1.333;
			else if (minus) gpa = (typeA) ? 1.33 : 0.667;
			else gpa = (typeA) ? 1.667 : 1.00;
		}else if(letterGrade.contains("F") || letterGrade.contains("f"))
		{
			gpa = 0.00;
		}
		return gpa;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static void setGradeSpreadsheetPath(String name) {
		GradeSpreadsheetPath = name;
	}

	public static void setOutputDir(String absolutePath) {
		ReportCardPath = absolutePath + "//ReportCard.csv";
	}

	public static String getOutputDir() {
		return ReportCardPath;
	}

	public static String getReportCardHeader() {
		return ReportCardHeader;
	}

	public static void setReportCardHeader(String reportCardHeader) {
		ReportCardHeader = reportCardHeader;
	}

	/*
	 * private static void storeHashMap(Object mapName, String fileName) throws
	 * IOException { // TODO Auto-generated method stub FileOutputStream
	 * fileOutputStream = new FileOutputStream(fileName); ObjectOutputStream
	 * objectOutputStream= new ObjectOutputStream(fileOutputStream);
	 * objectOutputStream.writeObject(mapName); objectOutputStream.close(); }
	 */
	/*
	 * private static HashMap<String, Double> parseStringToDouble(String fileName) {
	 * // TODO Auto-generated method stub String curLine = ""; HashMap<String,
	 * Double> mapping = new HashMap<String, Double>(); try { BufferedReader br =
	 * new BufferedReader(new FileReader(fileName)); // skip first row (titles)
	 * br.readLine();
	 * 
	 * while ((curLine = br.readLine()) != null) // && count < 6) { if
	 * (curLine.contains(",")) { String[] row = curLine.split(",");
	 * mapping.put(row[0], Double.parseDouble(row[1])); } } if (br != null) { try {
	 * br.close(); } catch (IOException e) { System.err.printf(e.toString());
	 * e.printStackTrace(); } } } catch (FileNotFoundException e) {
	 * System.err.printf(e.toString()); e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); System.err.printf(e.toString()); } return mapping; }
	 * 
	 * private static HashMap<String, Integer> parseStringToInt(String fileName) {
	 * // TODO Auto-generated method stub BufferedReader br = null; String curLine =
	 * ""; HashMap<String, Integer> courseHours = new HashMap<String, Integer>();
	 * try { br = new BufferedReader(new FileReader(fileName)); // skip first row
	 * (titles) br.readLine();
	 * 
	 * while ((curLine = br.readLine()) != null) // && count < 6) { if
	 * (!curLine.contains(",")) { break; } String[] row = curLine.split(",");
	 * courseHours.put(row[0], Integer.parseInt(row[1])); } } catch
	 * (FileNotFoundException e) { System.err.printf(e.toString());
	 * e.printStackTrace(); } catch (IOException e) {
	 * System.err.printf(e.toString()); e.printStackTrace(); } finally { if (br !=
	 * null) { try { br.close(); } catch (IOException e) {
	 * System.err.printf(e.toString()); e.printStackTrace(); } } } return
	 * courseHours; }
	 */
}
