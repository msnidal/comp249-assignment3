package payroll;

/**
 * This class describes a generic tax premium with a rate and a maximum. Objects of this class are specific premium taxes.
 * @author Mark
 * @see Deductions
 */
public class Premium extends Deductions {
	private double premiumRate, maximumPremium;

	/**
	 * Instantiates a premium tax.
	 * @param premiumRate Percent of gross income paid as premium.
	 * @param maximumPremium Maximum value of premium in dollars, per person.
	 */
	public Premium(double premiumRate, double maximumPremium) {
		super();
		this.premiumRate = premiumRate;
		this.maximumPremium = maximumPremium;
	}
	
	/**
	 * Calculates the premium's tax deduction for a passed salary.
	 */
	public double calculateTax(double grossSalary) {
		if (grossSalary < maximumPremium/premiumRate)
			return grossSalary*premiumRate;
		else
			return maximumPremium;
	}
}
