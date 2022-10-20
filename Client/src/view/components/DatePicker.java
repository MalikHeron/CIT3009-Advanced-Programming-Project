package view.components;

import view.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Malik Heron & Shawn Grant
 */
public class DatePicker extends JPanel {

    private final JComboBox<Integer> dayBox;
    private final JComboBox<String> monthBox;
    private final JComboBox<Integer> yearBox;
    private final Integer[] days = new Integer[31];
    private final Integer[] years = new Integer[100];

    public DatePicker() {
        super(new FlowLayout(FlowLayout.LEFT, 1, 0));
        setPreferredSize(new Dimension(270, 35));

        for (int i = 1; i < 32; i++) {
            days[i - 1] = i;
        }
        int y = 1960;
        for (int i = 0; i < 100; i++) {
            years[i] = y;
            y++;
        }

        Dimension boxSize = new Dimension(70, 35);

        dayBox = new JComboBox<>(days);
        dayBox.setPreferredSize(boxSize);
        dayBox.setBorder(new RoundedBorder(7));
        dayBox.setFocusable(false);

        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        monthBox = new JComboBox<>(months);
        monthBox.setPreferredSize(new Dimension(108, 35));
        monthBox.setBorder(new RoundedBorder(7));
        monthBox.setFocusable(false);

        yearBox = new JComboBox<>(years);
        yearBox.setPreferredSize(boxSize);
        yearBox.setBorder(new RoundedBorder(7));
        yearBox.setFocusable(false);
        yearBox.setSelectedIndex(40);

        add(dayBox);
        add(monthBox);
        add(yearBox);
    }

    // return the date in YYYY-MM-DD format
    public Date getSelectedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, days[dayBox.getSelectedIndex()]);
        calendar.set(Calendar.MONTH, monthBox.getSelectedIndex());
        calendar.set(Calendar.YEAR, years[yearBox.getSelectedIndex()]);
        return calendar.getTime();
    }
}
