import java.awt.*;
import java.util.List;

import javax.swing.*;

public class TaskManager {

    public static void main(String[] args) {

        DatabaseManager.initialize();

        List<Task> tasks = DatabaseManager.loadTasks();

        TaskManagerFrame frame = new TaskManagerFrame();

            for (Task task : tasks) {

                TaskPanel taskPanel =
                    new TaskPanel(
                        task.getTitle()
                    );

        if (task.isCompleted()) {
            taskPanel.checkBox.setSelected(
                true
            );

            frame.completedTaskPanel.add(
                    taskPanel.panel
                );
            } else {
                frame.unfinishedTaskPanel.add(
                    taskPanel.panel
                );
            }

        setupCheckBox(
            frame,
            task,
            taskPanel
        );

        taskPanel.editButton.addActionListener(
                event -> {

                    openEditDialog(
                        frame,
                        tasks,
                        task,
                        taskPanel
                    );
                }
            );
        }

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

                int id =
                    DatabaseManager.insertTask(
                        task
                    );

                if (id == -1) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "タスクの追加に失敗しました",
                        "エラー",
                        JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                task.setId(id);

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

                        task.setCompleted(true);

                        frame.unfinishedTaskPanel.remove(
                            taskPanel.panel
                        );

                        frame.completedTaskPanel.add(
                            taskPanel.panel
                        );

                    } else {

                        task.setCompleted(false);

                        frame.completedTaskPanel.remove(
                            taskPanel.panel
                        );

                        frame.unfinishedTaskPanel.add(
                            taskPanel.panel
                        );
                    }

                    DatabaseManager.updateCompleted(
                        task
                    );


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
            task.getNote(),
            15
        );


    // 現在の値を選択
    editMonthBox.setSelectedItem(
        task.getMonth()
    );

    editDayBox.setSelectedItem(
        task.getDay()
    );

    editHourBox.setSelectedItem(
        task.getHour()
    );

    editMinuteBox.setSelectedItem(
        task.getMinute()
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

            saveTaskedit(
                task,
                taskPanel,
                editMonthBox,
                editDayBox,
                editHourBox,
                editMinuteBox,
                editNoteField
            );

            editDialog.dispose();
        }
    );


    // =========================
    // 削除
    // =========================

    editDeleteButton.addActionListener(
        deleteEvent -> {

            // DBから削除
            DatabaseManager.deleteTask(
                task
            );

            // Listから削除
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

    private static void saveTaskedit(
        Task task,
        TaskPanel taskPanel,
        JComboBox<String> editMonthBox,
        JComboBox<String> editDayBox,
        JComboBox<String> editHourBox,
        JComboBox<String> editMinuteBox,
        JTextField editNoteField
    ) {

        task.setMonth(
            (String)
            editMonthBox.getSelectedItem()
        );

        task.setDay(
            (String)
            editDayBox.getSelectedItem()
        );

        task.setHour(
            (String)
            editHourBox.getSelectedItem()
        );
            editHourBox.getSelectedItem();

        task.setMinute(
            (String)
            editMinuteBox.getSelectedItem()
        );

        task.setNote(
            editNoteField.getText()
        );

        DatabaseManager.updateTask(
            task
        );

        taskPanel.label.setText(
            task.getTitle()
        );
    }
}