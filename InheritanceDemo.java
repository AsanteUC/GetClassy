import java.util.ArrayList;

/**
 * Demonstrates inheritance and polymorphism across Person, Worker and
 * SalaryWorker. Builds a mixed ArrayList of Workers (some plain hourly
 * Workers, some SalaryWorkers) and runs three simulated weekly pay periods,
 * calling calculateWeeklyPay() and displayWeeklyPay() polymorphically —
 * the correct overridden version runs for each object without the loop
 * needing to know which subclass it is holding.
 *
 * @author Your Name
 * @version 1.0
 */
public class InheritanceDemo
{
    /**
     * Program entry point.
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        ArrayList<Worker> workers = new ArrayList<>();

        // Three hourly Workers
        workers.add(new Worker("Sam", "Carter", "100001", "Ms.", 1990, 18.50));
        workers.add(new Worker("Miguel", "Torres", "100002", "Mr.", 1985, 22.00));
        workers.add(new Worker("Aisha", "Bello", "100003", "Ms.", 1995, 16.75));

        // Three salaried Workers
        workers.add(new SalaryWorker("Dana", "Kim", "200001", "Dr.", 1980, 78000.00));
        workers.add(new SalaryWorker("Leo", "Nguyen", "200002", "Mr.", 1975, 95000.00));
        workers.add(new SalaryWorker("Priya", "Patel", "200003", "Prof.", 1988, 110000.00));

        // Week 1: normal, Week 2: crunch time, Week 3: back to normal
        double[] weeklyHours = { 40.0, 50.0, 40.0 };

        for (int week = 1; week <= weeklyHours.length; week++)
        {
            double hours = weeklyHours[week - 1];

            System.out.println();
            System.out.println("===== Week " + week + " - " + hours + " hours worked =====");
            System.out.printf("%-16s %-16s %-10s %12s%n", "Name", "Type", "Hours", "Weekly Pay");
            System.out.println("-".repeat(58));

            for (Worker w : workers)
            {
                // Polymorphic call: runs Worker's or SalaryWorker's version automatically
                double pay = w.calculateWeeklyPay(hours);
                String type = (w instanceof SalaryWorker) ? "Salary" : "Hourly";
                System.out.printf("%-16s %-16s %-10.1f %12s%n",
                        w.fullName(), type, hours, String.format("$%.2f", pay));
            }
        }

        System.out.println();
        System.out.println("===== Detailed pay breakdown, all weeks =====");
        for (int week = 1; week <= weeklyHours.length; week++)
        {
            double hours = weeklyHours[week - 1];
            System.out.println("\n--- Week " + week + " - " + hours + " hours worked ---");

            for (Worker w : workers)
            {
                // Polymorphic call: Worker prints regular/overtime, SalaryWorker prints the salary note
                w.displayWeeklyPay(hours);
            }
        }
    }
}