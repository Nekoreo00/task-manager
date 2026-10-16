# Task Manager

Java SwingとSQLiteを使用して作成したタスク管理アプリです。

日時とメモを入力してタスクを登録し、
未完了・完了の切り替え、編集、削除ができます。

SQLiteを使用しているため、
アプリを終了してもタスク情報が保存されます。

## 主な機能

- タスクの追加
- タスクの編集
- タスクの削除
- 完了 / 未完了の切り替え
- SQLiteへのタスク保存
- アプリ起動時の保存データ読み込み

## 使用技術

- Java 21
- Java Swing
- SQLite
- JDBC
- Maven
- Maven Shade Plugin

## プロジェクト構成

```text
task-manager/
├─ README.md
├─ pom.xml
├─ .gitignore
└─ src/
   └─ main/
      ├─ java/
      │  ├─ TaskManager.java
      │  ├─ TaskManagerFrame.java
      │  ├─ TaskPanel.java
      │  ├─ Task.java
      │  ├─ DatabaseManager.java
      │  └─ IconUtil.java
      │
      └─ resources/
         └─ images/
            └─ trash.png

## クラスの役割

- `TaskManager`
  - アプリケーションの起動処理
  - ボタンやチェックボックスなどのイベント処理
  - 各クラスをつなぐ制御を担当

- `TaskManagerFrame`
  - メイン画面全体のGUIを担当
  - 入力欄、未完了タスク欄、完了タスク欄を管理

- `TaskPanel`
  - 1件分のタスク表示を担当
  - チェックボックス、タスク名、編集ボタンを管理

- `Task`
  - タスク1件分のデータを管理
  - 日時、メモ、完了状態、IDを保持

- `DatabaseManager`
  - SQLiteとの接続を担当
  - タスクの追加、取得、更新、削除を担当

- `IconUtil`
  - 画像ファイルの読み込み
  - アイコン画像のサイズ調整を担当

  ## データベース

SQLiteを使用してタスク情報を保存しています。

JavaからSQLiteへはJDBCを使用して接続し、
タスクに対してCRUD処理を実装しています。

- Create：タスクの追加
- Read：保存済みタスクの読み込み
- Update：タスク内容・完了状態の更新
- Delete：タスクの削除

データベースファイルはユーザーのホームディレクトリ配下に保存されるため、
実行場所に依存せず同じデータを参照できます。

## ビルド・実行方法

### ビルド

プロジェクト直下で以下を実行します。

```bash
mvn clean package

## 学習・工夫した点

- Java Swingを使用してGUIアプリケーションを作成し、
  イベント処理や画面更新の流れを学びました。

- `Task`、`TaskPanel`、`TaskManagerFrame`、
  `DatabaseManager` などに役割を分け、
  1つのクラスに処理が集中しないよう整理しました。

- `Task` クラスのフィールドを `private` にし、
  getter / setterを使用してカプセル化しました。

- SQLiteとJDBCを使用し、
  タスクの追加・取得・更新・削除を行うCRUD処理を実装しました。

- UI上の表示だけでなく、
  完了状態や編集内容がアプリ再起動後も保持されるよう
  データベースと画面の状態を連携させました。

- `DatabaseManager` のDB接続処理を共通化し、
  重複するコードを減らしました。

- 保存処理や削除処理を別メソッドに切り出し、
  イベント処理が長くなりすぎないよう整理しました。

- Mavenを導入し、
  SQLite JDBCなどの依存関係を `pom.xml` で管理する形に変更しました。

- Maven Shade Pluginを使用し、
  依存ライブラリを含んだ実行可能JARを作成できるようにしました。

- データベースの保存先をユーザーのホームディレクトリ配下にすることで、
  JARの実行場所に依存せず同じデータを参照できるようにしました。


  ## 今後の改善予定

- Spring Bootを使用したWebアプリケーション版への移行
- HTML / CSS / JavaScriptを使用したブラウザ画面の作成
- SQLiteからMySQLやPostgreSQLなどのDBへの移行
- タスクの期限順ソートや検索機能の追加
- 入力値チェックやエラー処理の改善
- テストコードの追加