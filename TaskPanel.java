import java.awt.FlowLayout;
import javax.swing.*;

public class TaskPanel {

    JPanel panel;
    JLabel label;
    JCheckBox checkBox;
    JButton editButton;

    public TaskPanel(
        String title
    ) {

        panel =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );

        label =
            new JLabel(title);

        checkBox =
            new JCheckBox();

        editButton =
            new JButton("編集");

        panel.add(checkBox);
        panel.add(label);
        panel.add(editButton);
    }
}