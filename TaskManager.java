import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TaskManager {

    public static void main(String[] args) {

        List<Task> tasks = new ArrayList<>();

        JFrame frame = new JFrame("タスク管理アプリ");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // 月
        String[] months = {
            "1月", "2月", "3月", "4月", "5月", "6月",
            "7月", "8月", "9月", "10月", "11月", "12月"
        };

        JComboBox<String> monthBox = new JComboBox<>(months);

        // 日
        String[] days = new String[31];

        for (int i = 0; i < 31; i++) {
            days[i] = (i + 1) + "日";
        }

        JComboBox<String> dayBox = new JComboBox<>(days);

        // 時
        String[] hours = new String[24];

        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d時", i);
        }

        JComboBox<String> hourBox = new JComboBox<>(hours);

        // 分
        String[] minutes = {
            "00分", "10分", "20分", "30分", "40分", "50分"
        };

        JComboBox<String> minuteBox = new JComboBox<>(minutes);

        // 備考欄
        JTextField noteField = new JTextField();

        // 追加ボタン
        JButton addButton = new JButton("追加");

        // 固定タイトル
        JLabel unfinishedLabel = new JLabel("未完了タスク");

        // タスク表示
        JLabel taskLabel = new JLabel("タスクはまだありません");

        // 部品の位置
        monthBox.setBounds(30, 40, 70, 30);
        dayBox.setBounds(110, 40, 70, 30);

        hourBox.setBounds(200, 40, 70, 30);
        minuteBox.setBounds(280, 40, 70, 30);

        noteField.setBounds(30, 90, 300, 30);
        addButton.setBounds(340, 90, 80, 30);

        unfinishedLabel.setBounds(30, 150, 200, 30);
        taskLabel.setBounds(30, 190, 400, 30);

        // 追加ボタンを押したとき
        addButton.addActionListener(e -> {

            String month = (String) monthBox.getSelectedItem();
            String day = (String) dayBox.getSelectedItem();
            String hour = (String) hourBox.getSelectedItem();
            String minute = (String) minuteBox.getSelectedItem();
            String note = noteField.getText();

            String title =
                month + day + " "
                + hour.replace("時", "")
                + ":"
                + minute.replace("分", "")
                + " ～" + note;

            tasks.add(new Task(title));

            taskLabel.setText(title);

            noteField.setText("");
        });

        // JFrameに部品を追加
        frame.add(monthBox);
        frame.add(dayBox);
        frame.add(hourBox);
        frame.add(minuteBox);
        frame.add(noteField);
        frame.add(addButton);
        frame.add(unfinishedLabel);
        frame.add(taskLabel);

        frame.setVisible(true);
    }
}
