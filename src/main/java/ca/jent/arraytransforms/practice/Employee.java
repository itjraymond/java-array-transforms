package ca.jent.arraytransforms.practice;

import java.util.Random;

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
