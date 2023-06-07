import javax.swing.*;
public class Main {
    public static void main(String[] args)
    {
        JFrame f = new JFrame("Java Kurs2");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1400,740);
        f.add(new Road());
        f.setVisible(true);
    }
}