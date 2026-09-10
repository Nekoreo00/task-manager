import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TaskManager {

    public static void main(String[] args) {

        List<Task> tasks = new ArrayList<>();

        JFrame frame = new JFrame("タスク管理アプリ");

        frame.setSize(600, 450);

        frame.setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );

        frame.setLayout(
            new BorderLayout()
        );


        // =========================
        // 入力エリア
        // =========================

        JPanel inputPanel =
            new JPanel(
                new FlowLayout()
            );


        // 月
        String[] months = {
            "1月", "2月", "3月", "4月",
            "5月", "6月", "7月", "8月",
            "9月", "10月", "11月", "12月"
        };

        JComboBox<String> monthBox =
            new JComboBox<>(months);


        // 日
        String[] days =
            new String[31];

        for (int i = 0; i < 31; i++) {

            days[i] =
                (i + 1) + "日";
        }

        JComboBox<String> dayBox =
            new JComboBox<>(days);


        // 時
        String[] hours =
            new String[24];

        for (int i = 0; i < 24; i++) {

            hours[i] =
                String.format(
                    "%02d時",
                    i
                );
        }

        JComboBox<String> hourBox =
            new JComboBox<>(hours);


        // 分
        String[] minutes = {
            "00分",
            "10分",
            "20分",
            "30分",
            "40分",
            "50分"
        };

        JComboBox<String> minuteBox =
            new JComboBox<>(minutes);


        // 備考欄
        JTextField noteField =
            new JTextField(15);


        // 追加ボタン
        JButton addButton =
            new JButton("追加");


        // 入力エリアに追加
        inputPanel.add(monthBox);
        inputPanel.add(dayBox);
        inputPanel.add(hourBox);
        inputPanel.add(minuteBox);
        inputPanel.add(noteField);
        inputPanel.add(addButton);

        // =========================
        // タスク表示エリア
        // =========================

        JPanel taskPanel =
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
                "未完了タスク"
            );

        JPanel unfinishedTaskPanel =
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
                "完了タスク"
            );

        JPanel completedTaskPanel =
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


        // =========================
        // 追加ボタンのイベント
        // =========================

        addButton.addActionListener(
            e -> {

                String month =
                    (String)
                    monthBox.getSelectedItem();

                String day =
                    (String)
                    dayBox.getSelectedItem();

                String hour =
                    (String)
                    hourBox.getSelectedItem();

                String minute =
                    (String)
                    minuteBox.getSelectedItem();

                String note =
                    noteField.getText();


                // =========================
                // Taskインスタンス作成
                // =========================

                Task task =
                    new Task(
                        month,
                        day,
                        hour,
                        minute,
                        note
                    );

                tasks.add(task);

                String title =
                    task.getTitle();



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


                //編集ボタン
                JButton editButton =
                    new JButton("編集");



                // =========================
                // チェック・削除部品のサイズ
                // =========================

                Dimension controlSize =
                    new Dimension(
                        45,
                        30
                    );

                checkBox.setPreferredSize(
                    controlSize
                );

                deleteButton.setPreferredSize(
                    controlSize
                );


                // =========================
                // 1件分のPanelへ追加
                // =========================

                oneTaskPanel.add(
                    taskLabel
                );

                oneTaskPanel.add(
                    checkBox
                );

                oneTaskPanel.add(
                    deleteButton
                );

                oneTaskPanel.add(
                    editButton
                );


                // =========================
                // 新しいタスクは未完了へ追加
                // =========================

                unfinishedTaskPanel.add(
                    oneTaskPanel
                );


                // =========================
                // チェックボックスのイベント
                // =========================

                checkBox.addActionListener(
                    event -> {

                        if (
                            checkBox.isSelected()
                        ) {

                            task.completed =
                                true;

                            unfinishedTaskPanel.remove(
                                oneTaskPanel
                            );

                            completedTaskPanel.add(
                                oneTaskPanel
                            );

                        } else {

                            task.completed =
                                false;

                            completedTaskPanel.remove(
                                oneTaskPanel
                            );

                            unfinishedTaskPanel.add(
                                oneTaskPanel
                            );
                        }


                        taskPanel.revalidate();

                        taskPanel.repaint();
                    }
                );
                
                editButton.addActionListener(
                    event -> {
                        
                        //編集用プルダウン

                        JComboBox<String> editMonthBox = new JComboBox<>(months);
                        JComboBox<String> editDayBox = new JComboBox<>(days);
                        JComboBox<String> editHourBox = new JComboBox<>(hours);
                        JComboBox<String> editMinuteBox = new JComboBox<>(minutes);
                        JTextField editNoteField = new JTextField(task.note, 15);
                        JButton editDeleteButton = new JButton("削除");

                        //現在の値を選択状態にする
                        editMonthBox.setSelectedItem(
                            task.month
                        );

                        editDayBox.setSelectedItem(
                            task.day
                        );

                        editHourBox.setSelectedItem(
                            task.hour
                        );

                        editMinuteBox.setSelectedItem(
                            task.minute
                        );


                        //編集画面
                        JPanel editPanel = new JPanel();

                        editPanel.add(editMonthBox);
                        editPanel.add(editDayBox);
                        editPanel.add(editHourBox);
                        editPanel.add(editMinuteBox);
                        editPanel.add(editNoteField);
                        editPanel.add(editDeleteButton);
                        

                        int result = JOptionPane.showConfirmDialog(
                            frame,
                            editPanel,
                            "タスクを編集",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                        );

                        if (result == JOptionPane.OK_OPTION) {
                            // 編集された値をタスクに反映
                            task.month = (String) editMonthBox.getSelectedItem();
                            task.day = (String) editDayBox.getSelectedItem();
                            task.hour = (String) editHourBox.getSelectedItem();
                            task.minute = (String) editMinuteBox.getSelectedItem();
                            task.note = editNoteField.getText();
                        }
                    }
                );

                editDeleteButton.addActionListener(
                    deleteEvent -> {
                        // Listから削除
                        tasks.remove(task);

                        // 未完了側から削除
                        unfinishedTaskPanel.remove(oneTaskPanel);

                        // 完了側から削除
                        completedTaskPanel.remove(oneTaskPanel);

                        // 画面更新
                        taskPanel.revalidate();
                        taskPanel.repaint();
                    }
                );


                // =========================
                // 削除ボタンのイベント
                // =========================

                deleteButton.addActionListener(
                    event -> {

                        // Listから削除
                        tasks.remove(
                            task
                        );


                        // 未完了側から削除
                        unfinishedTaskPanel.remove(
                            oneTaskPanel
                        );


                        // 完了側から削除
                        completedTaskPanel.remove(
                            oneTaskPanel
                        );


                        // 画面更新
                        taskPanel.revalidate();

                        taskPanel.repaint();
                    }
                );


                // =========================
                // 画面更新
                // =========================

                taskPanel.revalidate();

                taskPanel.repaint();


                // 備考欄を空にする
                noteField.setText("");
            }
        );


        // =========================
        // JFrameへ配置
        // =========================

        frame.add(
            inputPanel,
            BorderLayout.NORTH
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

        frame.add(
            taskArea,
            BorderLayout.CENTER
        );


        // 画面表示
        frame.setVisible(true);
    }
}