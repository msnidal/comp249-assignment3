package payroll;

/**
 * This abstract class describes a generic tax deduction type.
 * @author Mark
 * @see Premium
 * @see TieredTax
 */
public abstract class Deductions {	
	/**
	 * Generic tax deduction calculation.
	 * @param grossSalary Gross salary to calculate tax deduction from.
	 * @return
	 */
	public abstract double calculateTax(double grossSalary);

}
