import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class TaskManager {

    public static void main(String[] args) {

        List<Task> tasks = new ArrayList<>();

        TaskManagerFrame frame = new TaskManagerFrame();

        setupAddButton(frame, tasks);

        frame.setVisible(true);
    }


        // =========================
        // 追加ボタンのイベント
        // =========================
    public static void setupAddButton(
        TaskManagerFrame frame,
        List<Task> tasks
        
    ) {
        frame.addButton.addActionListener(
            e -> {

                String month =
                    (String)
                    frame.monthBox.getSelectedItem();

                String day =
                    (String)
                    frame.dayBox.getSelectedItem();

                String hour =
                    (String)
                    frame.hourBox.getSelectedItem();

                String minute =
                    (String)
                    frame.minuteBox.getSelectedItem();

                String note =
                    frame.noteField.getText();


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

                TaskPanel taskPanel =
                    new TaskPanel(title);

                    frame.unfinishedTaskPanel.add(
                        taskPanel.panel
                    );

                setupCheckBox(frame, task, taskPanel);
                
                taskPanel.editButton.addActionListener(
                    event -> {

                        openEditDialog(frame, tasks, task, taskPanel);

                    }
                );
                
                // =========================
                // 画面更新
                // =========================

                frame.taskPanel.revalidate();

                frame.taskPanel.repaint();


                // 備考欄を空にする
                frame.noteField.setText("");
            }
        );
    }

    public static void setupCheckBox(
        TaskManagerFrame frame,
        Task task,
        TaskPanel taskPanel
    ){
                // =========================
                // チェックボックスのイベント
                // =========================
        taskPanel.checkBox.addActionListener(
                event -> {

                    if (
                        taskPanel.checkBox.isSelected()
                    ) {

                        task.completed =
                            true;

                        frame.unfinishedTaskPanel.remove(
                            taskPanel.panel
                        );

                        frame.completedTaskPanel.add(
                            taskPanel.panel
                        );

                    } else {

                        task.completed =
                            false;

                        frame.completedTaskPanel.remove(
                            taskPanel.panel
                        );

                        frame.unfinishedTaskPanel.add(
                            taskPanel.panel
                        );
                    }


                    frame.taskPanel.revalidate();

                    frame.taskPanel.repaint();
                }
        );
    }

    public static void openEditDialog(
    TaskManagerFrame frame,
    List<Task> tasks,
    Task task,
    TaskPanel taskPanel
) {

    JDialog editDialog =
        new JDialog(
            frame,
            "タスクを編集",
            true
        );

    editDialog.setSize(
        500,
        180
    );

    editDialog.setLayout(
        new BorderLayout()
    );


    // =========================
    // 入力部分
    // =========================

    JPanel editInputPanel =
        new JPanel(
            new FlowLayout()
        );


    JComboBox<String> editMonthBox =
        new JComboBox<>(
            frame.months
        );

    JComboBox<String> editDayBox =
        new JComboBox<>(
            frame.days
        );

    JComboBox<String> editHourBox =
        new JComboBox<>(
            frame.hours
        );

    JComboBox<String> editMinuteBox =
        new JComboBox<>(
            frame.minutes
        );

    JTextField editNoteField =
        new JTextField(
            task.note,
            15
        );


    // 現在の値を選択
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


    editInputPanel.add(
        editMonthBox
    );

    editInputPanel.add(
        editDayBox
    );

    editInputPanel.add(
        editHourBox
    );

    editInputPanel.add(
        editMinuteBox
    );

    editInputPanel.add(
        editNoteField
    );


    // =========================
    // 下部ボタン
    // =========================

    ImageIcon trashIcon =
        IconUtil.createIcon(
            "/images/trash.png",
            20,
            20
        );

    JPanel buttonPanel =
        new JPanel(
            new BorderLayout()
        );

    JButton editDeleteButton =
        new JButton(
            trashIcon
        );

    JButton saveButton =
        new JButton(
            "保存"
        );

    editDeleteButton.setPreferredSize(
        new Dimension(
            45,
            30
        )
    );

    editDeleteButton.setToolTipText(
        "タスクを削除します"
    );

    buttonPanel.add(
        editDeleteButton,
        BorderLayout.WEST
    );

    buttonPanel.add(
        saveButton,
        BorderLayout.EAST
    );


    // =========================
    // 保存
    // =========================

    saveButton.addActionListener(
        saveEvent -> {

            task.month =
                (String)
                editMonthBox.getSelectedItem();

            task.day =
                (String)
                editDayBox.getSelectedItem();

            task.hour =
                (String)
                editHourBox.getSelectedItem();

            task.minute =
                (String)
                editMinuteBox.getSelectedItem();

            task.note =
                editNoteField.getText();

            taskPanel.label.setText(
                task.getTitle()
            );

            editDialog.dispose();
        }
    );


    // =========================
    // 削除
    // =========================

    editDeleteButton.addActionListener(
        deleteEvent -> {

            tasks.remove(
                task
            );

            frame.unfinishedTaskPanel.remove(
                taskPanel.panel
            );

            frame.completedTaskPanel.remove(
                taskPanel.panel
            );

            frame.taskPanel.revalidate();
            frame.taskPanel.repaint();

            editDialog.dispose();
        }
    );


    // =========================
    // ダイアログへ配置
    // =========================

    editDialog.add(
        editInputPanel,
        BorderLayout.CENTER
    );

    editDialog.add(
        buttonPanel,
        BorderLayout.SOUTH
    );

    editDialog.setLocationRelativeTo(
        frame
    );

    editDialog.setVisible(
        true
    );
}
}