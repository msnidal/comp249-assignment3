package payroll;

/**
 * Describes a generic tiered tax, with four rates separated by three threshold points.
 * @author Mark
 * @see Deductions
 */
public class TieredTax extends Deductions {
	private double firstThreshold, secondThreshold, thirdThreshold, firstRate, secondRate, thirdRate, surplusRate;

	/**
	 * Instantiates a tiered tax
	 * @param firstThreshold Lowest tax threshold point at which to switch to the second rate.
	 * @param secondThreshold Middle threshold point at which to switch to the third rate.
	 * @param thirdThreshold Final threshold point, all income above this uses the surplus rate.
	 * @param firstRate Rate for income in the lowest bracket
	 * @param secondRate Rate for all income in the bracket between the first and second threshold points
	 * @param thirdRate Rate for all income within the second and third tax points
	 * @param surplusRate Rate for all income above the third threshold point
	 */
	public TieredTax(double firstThreshold, double secondThreshold,
			double thirdThreshold, double firstRate, double secondRate,
			double thirdRate, double surplusRate) {
		super();
		this.firstThreshold = firstThreshold;
		this.secondThreshold = secondThreshold;
		this.thirdThreshold = thirdThreshold;
		this.firstRate = firstRate;
		this.secondRate = secondRate;
		this.thirdRate = thirdRate;
		this.surplusRate = surplusRate;
	}
	
	/**
	 * Calculates the total tax deduction for any given salary.
	 * Each if statement represents a salary within a specific range, reflecting the generic tiered tax system used across Canada.
	 */
	public double calculateTax(double grossSalary) {
		// If the gross annual salary is below the first threshold rate, pay the first rate for the whole salary
		if (grossSalary <= firstThreshold) 
			return grossSalary*firstRate;
		// If the salary is between the first and second rates, pay up to the first threshold at the first rate and the remainder at the second rate.
		else if (grossSalary > firstThreshold && grossSalary <= secondThreshold)
			return firstThreshold*firstRate + (grossSalary-firstThreshold)*secondRate;
		// If the salary is between the 2nd and 3rd rates, pay the first two thresholds' worth of salary at their rates and the remainder at the third rate.
		else if (grossSalary > secondThreshold && grossSalary <= thirdThreshold)
			return firstThreshold*firstRate + (secondThreshold-firstThreshold)*secondRate 
					+ (grossSalary-secondThreshold)*thirdRate;
		// If the salary is above the third threshold, pay the respective rate up to the final threshold then the surplus rate for all additional income.
		else
			return firstThreshold*firstRate + (secondThreshold-firstThreshold)*secondRate 
					+ (thirdThreshold-secondThreshold)*thirdRate + (grossSalary-thirdThreshold)*surplusRate;
	}
}