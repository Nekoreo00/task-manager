import java.awt.*;

import javax.swing.*;

public class TaskManagerFrame extends JFrame {

    String[] months;
    String[] days;
    String[] hours;
    String[] minutes;


    JComboBox<String> monthBox;
    JComboBox<String> dayBox;
    JComboBox<String> hourBox;
    JComboBox<String> minuteBox;

 
    JTextField noteField;

    JButton addButton;

    JPanel taskPanel;
    JPanel unfinishedTaskPanel;
    JPanel completedTaskPanel;

    public TaskManagerFrame() {

        setTitle("タスク管理アプリ");

        setSize(
            600,
            450
        );

        setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );

        setLayout(
            new BorderLayout()
        );


        // 入力エリア
        JPanel inputPanel =
            new JPanel(
                new FlowLayout()
            );


        // 月
        months = new String[]{
            "1月", "2月", "3月", "4月",
            "5月", "6月", "7月", "8月",
            "9月", "10月", "11月", "12月"
        };

        monthBox =
            new JComboBox<>(months);


        // 日
        days = new String[31];

        for (int i = 0; i < 31; i++) {

            days[i] =
                (i + 1) + "日";
        }

        dayBox =
            new JComboBox<>(days);


        // 時
        hours = new String[24];

        for (int i = 0; i < 24; i++) {

            hours[i] =
                String.format(
                    "%02d時",
                    i
                );
        }

        hourBox =
            new JComboBox<>(hours);


        // 分
        minutes = new String[]{
            "00分",
            "10分",
            "20分",
            "30分",
            "40分",
            "50分"
        };

        minuteBox =
            new JComboBox<>(minutes);


        // 備考
        noteField =
            new JTextField(15);


        // 追加ボタン
        addButton =
            new JButton("追加");


        inputPanel.add(monthBox);
        inputPanel.add(dayBox);
        inputPanel.add(hourBox);
        inputPanel.add(minuteBox);
        inputPanel.add(noteField);
        inputPanel.add(addButton);


        add(
            inputPanel,
            BorderLayout.NORTH
        );

         // =========================
        // タスク表示エリア
        // =========================

        taskPanel =
            new JPanel();

        taskPanel.setLayout(
            new BoxLayout(
                taskPanel,
                BoxLayout.Y_AXIS
            )
        );


        // =========================
        // 未完了タスク
        // =========================

        JLabel unfinishedLabel =
            new JLabel(
                "【未完了タスク】"
            );

        unfinishedTaskPanel =
            new JPanel();

        unfinishedTaskPanel.setLayout(
            new BoxLayout(
                unfinishedTaskPanel,
                BoxLayout.Y_AXIS
            )
        );

        unfinishedLabel.setAlignmentX(
            0.0f
        );

        unfinishedTaskPanel.setAlignmentX(
            0.0f
        );


        // =========================
        // 完了タスク
        // =========================

        JLabel completedLabel =
            new JLabel(
                "【完了タスク】"
            );

        completedTaskPanel =
            new JPanel();

        completedTaskPanel.setLayout(
            new BoxLayout(
                completedTaskPanel,
                BoxLayout.Y_AXIS
            )
        );

        completedLabel.setAlignmentX(
            0.0f
        );

        completedTaskPanel.setAlignmentX(
            0.0f
        );


        // taskPanel に追加
        taskPanel.add(
            unfinishedLabel
        );

        taskPanel.add(
            unfinishedTaskPanel
        );

        taskPanel.add(
            completedLabel
        );

        taskPanel.add(
            completedTaskPanel
        );

     // タスク一覧を上側に詰める
        JPanel taskArea =
            new JPanel(
                new BorderLayout()
            );

        taskArea.add(
            taskPanel,
            BorderLayout.NORTH
        );

        add(
            taskArea,
            BorderLayout.CENTER
        );
    }
}