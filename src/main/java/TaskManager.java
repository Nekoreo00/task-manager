
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class TaskManager {

    public static void main(String[] args) {

        DatabaseManager.initialize();

        List<Task> tasks = DatabaseManager.loadTasks();

        TaskManagerFrame frame = new TaskManagerFrame();

        // 保存済みタスクを画面に表示
        for (Task task : tasks) {

            TaskPanel taskPanel =
                new TaskPanel(task.getTitle());

            if (task.isCompleted()) {

                taskPanel.checkBox.setSelected(true);

                frame.completedTaskPanel.add(
                    taskPanel.panel
                );

            } else {

                frame.unfinishedTaskPanel.add(
                    taskPanel.panel
                );
            }

            setupCheckBox(frame, task, taskPanel);

            taskPanel.editButton.addActionListener(
                event -> openEditDialog(
                    frame,
                    tasks,
                    task,
                    taskPanel
                )
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

        frame.addButton.addActionListener(event -> {

            String month =
                (String) frame.monthBox.getSelectedItem();

            String day =
                (String) frame.dayBox.getSelectedItem();

            String hour =
                (String) frame.hourBox.getSelectedItem();

            String minute =
                (String) frame.minuteBox.getSelectedItem();

            String note = frame.noteField.getText();

            // Taskインスタンス作成
            Task task = new Task(
                month,
                day,
                hour,
                minute,
                note
            );

            // DBへ保存
            int id = DatabaseManager.insertTask(task);

            if (id == -1) {

                JOptionPane.showMessageDialog(
                    frame,
                    "タスクの追加に失敗しました",
                    "追加エラー",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            task.setId(id);
            tasks.add(task);

            TaskPanel taskPanel =
                new TaskPanel(task.getTitle());

            frame.unfinishedTaskPanel.add(
                taskPanel.panel
            );

            setupCheckBox(frame, task, taskPanel);

            taskPanel.editButton.addActionListener(
                editEvent -> openEditDialog(
                    frame,
                    tasks,
                    task,
                    taskPanel
                )
            );

            // 画面更新
            frame.taskPanel.revalidate();
            frame.taskPanel.repaint();

            // 備考欄を空にする
            frame.noteField.setText("");
        });
    }


    // =========================
    // チェックボックスのイベント
    // =========================

    public static void setupCheckBox(
        TaskManagerFrame frame,
        Task task,
        TaskPanel taskPanel
    ) {

        taskPanel.checkBox.addActionListener(event -> {

            // 変更前の状態を保持
            boolean previousState = task.isCompleted();

            // チェック操作後の状態
            boolean newState =
                taskPanel.checkBox.isSelected();

            // DBに保存する値を設定
            task.setCompleted(newState);

            boolean success =
                DatabaseManager.updateCompleted(task);

            if (!success) {

                // DB保存失敗時は元に戻す
                task.setCompleted(previousState);

                taskPanel.checkBox.setSelected(
                    previousState
                );

                JOptionPane.showMessageDialog(
                    frame,
                    "タスクの状態を更新できませんでした",
                    "更新エラー",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // DB更新成功後に画面を変更
            if (newState) {

                frame.unfinishedTaskPanel.remove(
                    taskPanel.panel
                );

                frame.completedTaskPanel.add(
                    taskPanel.panel
                );

            } else {

                frame.completedTaskPanel.remove(
                    taskPanel.panel
                );

                frame.unfinishedTaskPanel.add(
                    taskPanel.panel
                );
            }

            frame.taskPanel.revalidate();
            frame.taskPanel.repaint();
        });
    }


    // =========================
    // 編集ダイアログ
    // =========================

    public static void openEditDialog(
        TaskManagerFrame frame,
        List<Task> tasks,
        Task task,
        TaskPanel taskPanel
    ) {

        JDialog editDialog = new JDialog(
            frame,
            "タスクを編集",
            true
        );

        editDialog.setSize(500, 180);
        editDialog.setLayout(new BorderLayout());

        // =========================
        // 入力部分
        // =========================

        JPanel editInputPanel =
            new JPanel(new FlowLayout());

        JComboBox<String> editMonthBox =
            new JComboBox<>(frame.months);

        JComboBox<String> editDayBox =
            new JComboBox<>(frame.days);

        JComboBox<String> editHourBox =
            new JComboBox<>(frame.hours);

        JComboBox<String> editMinuteBox =
            new JComboBox<>(frame.minutes);

        JTextField editNoteField =
            new JTextField(task.getNote(), 15);

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

        editInputPanel.add(editMonthBox);
        editInputPanel.add(editDayBox);
        editInputPanel.add(editHourBox);
        editInputPanel.add(editMinuteBox);
        editInputPanel.add(editNoteField);

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
            new JPanel(new BorderLayout());

        JButton editDeleteButton =
            new JButton(trashIcon);

        JButton saveButton =
            new JButton("保存");

        editDeleteButton.setPreferredSize(
            new Dimension(45, 30)
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
        // 保存ボタンのイベント
        // =========================

        saveButton.addActionListener(saveEvent -> {

            boolean success = saveTaskedit(
                task,
                taskPanel,
                editMonthBox,
                editDayBox,
                editHourBox,
                editMinuteBox,
                editNoteField
            );

            if (!success) {

                JOptionPane.showMessageDialog(
                    editDialog,
                    "タスクの編集内容を保存できませんでした",
                    "保存エラー",
                    JOptionPane.ERROR_MESSAGE
                );

                // 失敗時はダイアログを閉じない
                return;
            }

            // 成功した場合のみ閉じる
            editDialog.dispose();
        });

        // =========================
        // 削除ボタンのイベント
        // =========================

        editDeleteButton.addActionListener(
            deleteEvent -> {

                // DBから削除
                boolean success =
                    DatabaseManager.deleteTask(task);

                if (!success) {

                    JOptionPane.showMessageDialog(
                        editDialog,
                        "タスクの削除に失敗しました",
                        "削除エラー",
                        JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                // DB削除成功後にListから削除
                tasks.remove(task);

                // 画面から削除
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

        editDialog.setLocationRelativeTo(frame);
        editDialog.setVisible(true);
    }


    // =========================
    // タスク編集・DB保存
    // =========================

    private static boolean saveTaskedit(
        Task task,
        TaskPanel taskPanel,
        JComboBox<String> editMonthBox,
        JComboBox<String> editDayBox,
        JComboBox<String> editHourBox,
        JComboBox<String> editMinuteBox,
        JTextField editNoteField
    ) {

        // =========================
        // 変更前の値を保持
        // =========================

        String previousMonth = task.getMonth();
        String previousDay = task.getDay();
        String previousHour = task.getHour();
        String previousMinute = task.getMinute();
        String previousNote = task.getNote();

        // =========================
        // 編集内容をTaskに反映
        // =========================

        task.setMonth(
            (String) editMonthBox.getSelectedItem()
        );

        task.setDay(
            (String) editDayBox.getSelectedItem()
        );

        task.setHour(
            (String) editHourBox.getSelectedItem()
        );

        task.setMinute(
            (String) editMinuteBox.getSelectedItem()
        );

        task.setNote(
            editNoteField.getText()
        );

        // =========================
        // DBを更新
        // =========================

        boolean success =
            DatabaseManager.updateTask(task);

        if (!success) {

            // DB更新失敗時は元の値に戻す
            task.setMonth(previousMonth);
            task.setDay(previousDay);
            task.setHour(previousHour);
            task.setMinute(previousMinute);
            task.setNote(previousNote);

            return false;
        }

        // =========================
        // DB更新成功後に画面を変更
        // =========================

        taskPanel.label.setText(
            task.getTitle()
        );

        return true;
    }
}
