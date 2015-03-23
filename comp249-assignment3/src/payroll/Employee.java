package payroll;

/**
 * Describes a salaried company employee. 
 * @author Mark
 */
public class Employee {
	private long employeeNumber;
	private double hoursWorked, hourlyWage;
	private String firstName, lastName;
	
	/**
	 * Default constructor which sets local vars to test values. Only use this if you are planning to set values after.
	 */
	public Employee () {
		employeeNumber = -1;
		hoursWorked = 0;
		hourlyWage = 0;
		firstName = "";
		lastName = "";
	}

	/**
	 * Parameterized constructor which instantiates a salaried employee.
	 * @param employeeNumber Internal employee number.
	 * @param hoursWorked Hours worked per week by employee.
	 * @param hourlyWage Wage paid to employee per hour worked.
	 * @param firstName Employee's first name.
	 * @param lastName Employee's last name.
	 */
	public Employee(long employeeNumber, double hoursWorked, double hourlyWage,
			String firstName, String lastName) {
		this.employeeNumber = employeeNumber;
		this.hoursWorked = hoursWorked;
		this.hourlyWage = hourlyWage;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	// Getters and setters for local variables follow.
	public long getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(long employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public double getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public double getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(double hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Function which evaluates an employee's weekly gross salary, before tax deductions.
	 * @return Employee's weekly gross salary.
	 */
	public double weeklyGrossSalary() {
		return hoursWorked * hourlyWage;
	}
	
	/**
	 * Function which evaluates an employee's annual gross salary, before tax.
	 * @return Employee's annual gross salary.
	 */
	public double annualGrossSalary() {
		return weeklyGrossSalary() * 52; // 52 weeks in a year...
	}
	
	/**
	 * This toString method returns the employee in the format used in the corporate text file database.
	 */
	public String toString() {
		return String.format("%05d", employeeNumber) + "\t\t" + 
				String.format("%-16s", firstName) + 
				String.format("%-16s", lastName) + "$" +
				String.format("%-15.2f", annualGrossSalary());
	}
	
}
