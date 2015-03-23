package payroll;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This program reads a list of employees from a payroll text file which includes a company ID, first and last
 * name, wage and hours worked for the company, then  checks for errors (including any wages under minimum wage)
 * then calculates taxes for each employee based on their gross salary and outputs the relevant information to a
 * second text file.
 * 
 * Created for Jeremy Clark's COMP 249 2014/4 PP at Concordia University.
 * 
 * @author Mark Snidal
 * @see Deductions
 * @see MinimumWageException
 * @see Employee
 */
public class Driver {
	
	public static final String payrollFileName = "payroll.txt",
			errorFileName = "payrollError.txt", reportFileName = "payrollReport.txt";
	
	public static void main(String[] args) {
		int numErrorLines = 0, numLinesRead = 0;
		String currentLine = "";
		
		// Lists for employees as well as the error and final report text collections (more flexible than arrays...)
		List<Employee> employees = new ArrayList<Employee>();
		List<String> errors = new ArrayList<String>();
		List<String> report = new ArrayList<String>();
		
		// Opening file payroll in try-with-resources per-line scanner
		System.out.println("Opening file payroll...");
		
		try (Scanner lineScanner = new Scanner(new FileInputStream(payrollFileName))) {
			
			System.out.println("Reading file payroll...");
			
			// Store each string in a line until there are no more lines left
			while (lineScanner.hasNextLine()) {
				currentLine = lineScanner.nextLine();
				numLinesRead++;
				
				// Extract relevant data from individual line string
				try (Scanner inputStream = new Scanner(currentLine)) {
					long employeeNumber;
					double hoursWorked, hourlyWage;
					String firstName, lastName;				
					
					employeeNumber = Long.parseLong(inputStream.next());
					firstName = inputStream.next();
					lastName = inputStream.next();
					hoursWorked = Double.parseDouble(inputStream.next());
					hourlyWage = Double.parseDouble(inputStream.next());
						
					if (hourlyWage < 10.35)
						throw new MinimumWageException();
					
					// Create new employee in our list
					employees.add(new Employee(employeeNumber, hoursWorked, hourlyWage, firstName, lastName));
				}
				
				// In the case of an employee being paid under min wage, add to the error file. Never reaches the creation of a new employee object
				catch (MinimumWageException e) {
					errors.add(currentLine + " - under minimum wage.");
					numErrorLines++;
				}
				// If there is a format error, similarly add to the error file.
				catch (NumberFormatException e) {
					errors.add(currentLine + " - format error.");
					numErrorLines++;
				}
			}	
		}

		// If payroll file not found, tell user then exit
		catch (FileNotFoundException e) {
			System.out.println("Payroll not found.");
			System.exit(0);
		}
		
		// Print errors and relevant data
		System.out.println("\nErrors found in file payroll:");
		printList(errors);
		System.out.println("\n" + numLinesRead + " lines read from payroll file.");
				
		// Write error string to file
		writeFile(errorFileName, errors);		
		System.out.println(numErrorLines + " lines written to error file.\n");
	
		// Creating taxes and premiums
		TieredTax provincialTax = new TieredTax(41495,82985,100970,0.16,0.20,0.24,0.2575);
		TieredTax federalTax = new TieredTax(43953,87907,136270,0.15,0.22,0.26,0.29);
		Premium employmentInsurance = new Premium(0.0153,743.58);
		Premium quebecParentalInsurancePlan = new Premium(0.00559,385.71);
		Premium quebecPensionPlan = new Premium(0.05175,2535.75);
		
		// Calculating deductions for each valid employee
		System.out.println("Calculating deductions...");
		
		double[] deductionsTotal = new double[employees.size()];
		
		for (int i = 0; i < employees.size(); i++) {
			double grossSalary = employees.get(i).annualGrossSalary();
			deductionsTotal[i] = provincialTax.calculateTax(grossSalary) + federalTax.calculateTax(grossSalary) + 
					employmentInsurance.calculateTax(grossSalary) + quebecParentalInsurancePlan.calculateTax(grossSalary) + 
					quebecPensionPlan.calculateTax(grossSalary);
		}
				
		// Creating report file string, starting with header
		report.add("\t\t\t\t<< Harbour Industries >>");
		report.add("------------------------------------------------------------------------------------------");
		report.add("Employee #\tFirst Name\tLast Name\tGross Salary\tDeductions\tNet Salary");
		report.add("------------------------------------------------------------------------------------------");
		
		// Add each employee to list as a string
		for (int i = 0; i < employees.size(); i++) {
			report.add(employees.get(i) + "$" + String.format("%-15.2f", deductionsTotal[i]) + "$" +
					String.format("%-15.2f", (employees.get(i).annualGrossSalary() - deductionsTotal[i])));
			}
			
		// Printing report to file & output
		System.out.println("Writing report file...\n");
		writeFile(reportFileName,report);
		printList(report);
	}
	
	/**
	 * Method to write to file using a string list and a filename
	 * @param fileName Name of file to be written
	 * @param list Each element is a line in the text file (due to difficulty translating newline chars to text files)
	 */
	public static void writeFile(String fileName, List<String> list) {
		try (PrintWriter outputStream = new PrintWriter(new FileOutputStream(fileName))) {
			for (int i = 0; i < list.size(); i++)
				outputStream.println(list.get(i));
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: Unable to write to file.");
			System.exit(0);
		}
	}
	
	/**
	 * Method to print out the elements of a list 
	 * @param list Each element is a line of text
	 */
	public static void printList(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
