package ca.jent.arraytransforms.practice;

import java.util.Random;

/**
 * What can we put into an interface? (Java 17)
 * 1. member variable
 * 2. static member variable
 * 3. static method
 * 4. default method impl.
 * 5. private method with impl.
 * 6. abstract method
 */
public interface Employee {

    Double gst = 5.0;
    static String dummyName = "John Doe";

    static Double calculatePayWithGst(Double pay) {
        return pay * gst;
    }

    default Double calculateBonus(Double pay, Boolean flag) {
        int bonus = supplyRandomBonus();
        if (flag) {
            return calculatePayWithGst(pay) + bonus;
        }
        return calculatePayWithGst(pay);
    }

    private int supplyRandomBonus() {
        return (new Random()).nextInt(100);
    }

    Double getSalary();

}
