import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class ExcelParser {
	static HashMap<String, Integer> courseHours = new HashMap<String, Integer>();
	static HashMap<String, Double> gradeGPAs = new HashMap<String, Double>();
	static HashMap<Integer, Student> students = new HashMap<Integer, Student>();

	public static void main(String[] args) throws FileNotFoundException {
		courseHours = parseStringToInt("/Users/Laura/Downloads/CourseHours.csv");
		gradeGPAs = parseStringToDouble("/Users/Laura/Downloads/gradeGPAs.csv");

		BufferedReader br = null;
		String curLine = "";
		try {
			int curId = 0;
			Student curStudent = new Student();
			br = new BufferedReader(new FileReader("/Users/Laura/Downloads/Grades.csv"));

			// skip first row (titles)
			br.readLine();

			while ((curLine = br.readLine()) != null)// && count < 6)
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
				int columnIndex = 0;
				StringBuilder curName = new StringBuilder();

				while (columnIndex < row.length && row != null) {
					String curCell = row[columnIndex];

					// System.out.printf("columnIndex: %d, Cell: %s\n", columnIndex, curCell);

					switch (columnIndex) {
					case 8:
						curId = Integer.parseInt(curCell);
						if (students.get(curId) != null) {
							curStudent = students.get(curId);
						} else {
							curStudent = new Student();
						}
						curStudent.setId(curId);
						break;
					case 4:
						courseName = curCell;
						break;
					case 6:
						curName.append(curCell);
						break;
					case 7:
						curName.append(" " + curCell);
						break;
					case 9:
						courseGrade = curCell;
						break;
					}
					columnIndex++;
				}

				curStudent.addCourse(courseName, courseGrade);
				if (curStudent.getName() == null) {
					curStudent.setName(curName.toString());
				} else if (!curStudent.getName().equals(curName.toString())) {
					System.err.printf("Error - you have two students ('%s' and '%s') with the same ID (%d)!",
							curStudent.getName(), curName.toString(), curId);
					return;
				}
				students.put(curId, curStudent);
			}

			calculateGpas();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static void calculateGpas() throws IOException {
		FileWriter writer = new FileWriter("ReportCard.csv");
		
		for (int studentId : students.keySet()) {
			Student s = students.get(studentId);
			System.out.println("----------------");
			System.out.printf("StudentId: %d \t Student Name: %s\n", s.getId(), s.getName());
			System.out.println("Courses:");
			HashMap<String, String> courses = s.getCourses();
			Double curGpa = 0.00;
			int totalHours = 0;
			StringBuffer buff = new StringBuffer();
			
			for (String key : courses.keySet()) {
				System.out.println("Course name: " + key);
				
				int curHours = courseHours.get(key);
				System.out.println("Course hours: " + curHours);
				String letterGrade = courses.get(key);
				System.out.println("Letter grade: " + letterGrade);
				double gpa = gradeGPAs.get(letterGrade);
				System.out.println("Course gpa: " + gpa);
				curGpa = (curGpa + (gpa * curHours));
				totalHours += curHours;
				System.out.println("Total Hours: " + totalHours);
				
				buff.append(key);
				buff.append(",instructor name");
				buff.append("," + letterGrade);
				buff.append("," + curHours);
				buff.append("," + Double.toString(gpa));
				buff.append("\n");
				
				
			}
			//calculate overall GPA
			curGpa = (curGpa / totalHours);
			//round to two decimal places
			curGpa = round(curGpa, 2);
			s.setgpa(curGpa);
			
			System.out.println("Overall GPA: " + s.getgpa());
			writer.write(s.getName() + ',');
			writer.write("ID,");
			writer.write(Integer.toString(s.getId()) + ',');
			writer.write("GPA,");
			writer.write(Double.toString(s.getgpa()) + '\n');
			writer.write("Class, Instructor, Grade, Hours, GPA\n");
			writer.write(buff.toString());
			writer.write("\n\n");
		}
		
		writer.flush();
		writer.close();
	}

	private static HashMap<String, Double> parseStringToDouble(String fileName) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		String curLine = "";
		HashMap<String, Double> mapping = new HashMap<String, Double>();
		try {
			br = new BufferedReader(new FileReader(fileName));
			// skip first row (titles)
			br.readLine();

			while ((curLine = br.readLine()) != null) // && count < 6)
			{
				if (!curLine.contains(",")) {
					break;
				}
				String[] row = curLine.split(",");
				mapping.put(row[0], Double.parseDouble(row[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mapping;
	}

	private static HashMap<String, Integer> parseStringToInt(String fileName) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		String curLine = "";
		HashMap<String, Integer> courseHours = new HashMap<String, Integer>();
		try {
			br = new BufferedReader(new FileReader(fileName));
			// skip first row (titles)
			br.readLine();

			while ((curLine = br.readLine()) != null) // && count < 6)
			{
				if (!curLine.contains(",")) {
					break;
				}
				String[] row = curLine.split(",");
				courseHours.put(row[0], Integer.parseInt(row[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return courseHours;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
