import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TaskManager {

    public static void main(String[] args) {

        List<Task> tasks = new ArrayList<>();

        JFrame frame = new JFrame("タスク管理アプリ");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // =========================
        // 入力エリア
        // =========================

        JPanel inputPanel = new JPanel(new FlowLayout());

        // 月
        String[] months = {
            "1月", "2月", "3月", "4月", "5月", "6月",
            "7月", "8月", "9月", "10月", "11月", "12月"
        };

        JComboBox<String> monthBox =
            new JComboBox<>(months);

        // 日
        String[] days = new String[31];

        for (int i = 0; i < 31; i++) {
            days[i] = (i + 1) + "日";
        }

        JComboBox<String> dayBox =
            new JComboBox<>(days);

        // 時
        String[] hours = new String[24];

        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d時", i);
        }

        JComboBox<String> hourBox =
            new JComboBox<>(hours);

        // 分
        String[] minutes = {
            "00分", "10分", "20分",
            "30分", "40分", "50分"
        };

        JComboBox<String> minuteBox =
            new JComboBox<>(minutes);

        // 備考欄
        JTextField noteField =
            new JTextField(15);

        // 追加ボタン
        JButton addButton =
            new JButton("追加");

        // 入力エリアへ追加
        inputPanel.add(monthBox);
        inputPanel.add(dayBox);
        inputPanel.add(hourBox);
        inputPanel.add(minuteBox);
        inputPanel.add(noteField);
        inputPanel.add(addButton);


        // =========================
        // タスク表示エリア
        // =========================

        JPanel taskPanel = new JPanel();

        taskPanel.setLayout(
            new BoxLayout(
                taskPanel,
                BoxLayout.Y_AXIS
            )
        );

        JLabel unfinishedLabel =
            new JLabel("未完了タスク");

        taskPanel.add(unfinishedLabel);


        // =========================
        // 追加ボタンのイベント
        // =========================

        addButton.addActionListener(e -> {

            String month =
                (String) monthBox.getSelectedItem();

            String day =
                (String) dayBox.getSelectedItem();

            String hour =
                (String) hourBox.getSelectedItem();

            String minute =
                (String) minuteBox.getSelectedItem();

            String note =
                noteField.getText();

            String title =
                month
                + day
                + " "
                + hour.replace("時", "")
                + ":"
                + minute.replace("分", "")
                + " ～"
                + note;


            // =========================
            // Taskインスタンス作成
            // =========================

            Task task =
                new Task(title);

            tasks.add(task);


            // =========================
            // 1件分の表示エリア
            // =========================

            JPanel oneTaskPanel =
                new JPanel(
                    new FlowLayout(
                        FlowLayout.LEFT
                    )
                );

            // タスク内容
            JLabel taskLabel =
                new JLabel(title);

            // チェックボックス
            JCheckBox checkBox =
                new JCheckBox();

            // 削除ボタン
            JButton deleteButton =
                new JButton("×");


            // =========================
            // チェック・削除部品のサイズ
            // =========================

            Dimension controlSize =
                new Dimension(45, 30);

            checkBox.setPreferredSize(
                controlSize
            );

            deleteButton.setPreferredSize(
                controlSize
            );


            // =========================
            // 1件分のパネルへ追加
            // =========================

            oneTaskPanel.add(taskLabel);

            oneTaskPanel.add(checkBox);

            oneTaskPanel.add(deleteButton);


            // タスク一覧へ追加
            taskPanel.add(oneTaskPanel);


            // =========================
            // チェックボックスのイベント
            // =========================

            checkBox.addActionListener(event -> {

                if (checkBox.isSelected()) {

                    taskLabel.setText(
                        "【完了】 " + title
                    );

                } else {

                    taskLabel.setText(
                        title
                    );
                }
            });


            // =========================
            // 削除ボタンのイベント
            // =========================

            deleteButton.addActionListener(event -> {

                // Listから削除
                tasks.remove(task);

                // 画面から削除
                taskPanel.remove(
                    oneTaskPanel
                );

                // 画面を更新
                taskPanel.revalidate();

                taskPanel.repaint();
            });


            // =========================
            // 画面更新
            // =========================

            taskPanel.revalidate();

            taskPanel.repaint();


            // 備考欄を空にする
            noteField.setText("");
        });


        // =========================
        // JFrameへ配置
        // =========================

        frame.add(
            inputPanel,
            BorderLayout.NORTH
        );

        frame.add(
            taskPanel,
            BorderLayout.CENTER
        );

        frame.setVisible(true);
    }
}