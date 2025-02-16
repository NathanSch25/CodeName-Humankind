import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Code Name - Human Kind");

        GamePanel game = new GamePanel();
        window.add(game);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        JFrame Actions = new JFrame();
        Actions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Actions.setResizable(false);
        Actions.setTitle("Actions");

        int MaxChoices = 5;
        JButton[] Choice = new JButton[MaxChoices + 1];
        for (int x = 0; x < MaxChoices + 1; x++) {
            Choice[x] = new JButton();
            Choice[x].setBounds(50, 125 * x + 25, 200, 100);
            Choice[x].setBackground(Color.WHITE);
            Choice[x].setVisible(MaxChoices != x);
            Actions.add(Choice[x]);
        }

        Actions.setPreferredSize(new Dimension(300, 100 * MaxChoices + 185));
        Actions.setBackground(Color.white);
        Actions.setFocusable(true);
        Actions.setLocationRelativeTo(null);
        Actions.setVisible(true);
        Actions.pack();
        
        //Level lvl = new Level();
            
        game.startGT(Actions, Choice);
        //System.out.println(lvl.SimpleSearch("1,1", "19,19"));
    }
}
