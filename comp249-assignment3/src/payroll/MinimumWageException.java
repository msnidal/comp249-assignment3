package payroll;

/**
 * This exception is used in cases where an employee is being paid a wage below the provincially defined minimum.
 * @author Mark
 * @see Driver
 */
public class MinimumWageException extends Exception {

	public MinimumWageException() {
		super("Employee is being paid below minimum wage.");
	}

	public MinimumWageException(String arg0) {
		super(arg0);
	}
}
