# Task Manager

Java SwingとSQLiteを使用して開発した、タスク管理アプリケーションです。

日時とメモを入力してタスクを登録し、編集・削除・完了状態の切り替えができます。

SQLiteによってタスク情報を永続化しているため、アプリケーションを終了・再起動しても登録内容が保持されます。

## スクリーンショット

![alt text](<スクリーンショット .png>)

## 主な機能

- タスクの追加
- 登録済みタスクの編集・削除
- 完了・未完了の切り替え
- 完了状態に応じたタスクの表示切り替え
- SQLiteへのデータ保存
- アプリ起動時の保存データ読み込み
- DB更新失敗時のエラー表示と画面状態の復元

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
├── README.md
├── pom.xml
├── .gitignore
└── src/
    └── main/
        ├── java/
        │   ├── TaskManager.java
        │   ├── TaskManagerFrame.java
        │   ├── TaskPanel.java
        │   ├── Task.java
        │   ├── DatabaseManager.java
        │   └── IconUtil.java
        └── resources/
            └── images/
                └── trash.png
```

## クラスの役割

| クラス | 主な役割 |
|---|---|
| `TaskManager` | アプリの起動、イベント処理、画面とDB処理の連携 |
| `TaskManagerFrame` | メイン画面、入力欄、タスク表示領域の管理 |
| `TaskPanel` | タスク1件分の表示、チェックボックス、編集ボタン |
| `Task` | タスクのID、日時、メモ、完了状態の保持 |
| `DatabaseManager` | SQLiteへの接続とCRUD処理 |
| `IconUtil` | アイコン画像の読み込みとサイズ調整 |

## データベース

SQLiteを使用してタスク情報を保存しています。

JavaからSQLiteへはJDBCを通じて接続し、以下のCRUD処理を実装しました。

- **Create**：タスクの追加
- **Read**：保存済みタスクの取得
- **Update**：日時・メモ・完了状態の更新
- **Delete**：タスクの削除

SQLの実行には`PreparedStatement`を使用しています。

データベースファイルは、ユーザーのホームディレクトリ配下の以下の場所に保存されます。

```text
~/.taskmanager-data/tasks.db
```

これにより、JARファイルの実行場所に依存せず、同じデータを参照できます。

## ビルド・実行方法

### 必要な環境

- Java 21
- Maven

### ビルド

`pom.xml`があるプロジェクト直下で、以下を実行します。

```bash
mvn clean package
```

Maven Shade Pluginにより、依存ライブラリを含む実行可能JARを作成します。

### 実行

ビルド後、`target`フォルダ内に生成された実行可能JARを、以下のコマンドで起動します。

```bash
task-manager-1.0-SNAPSHOT
```

## 開発で工夫した点

### 1. クラスごとの役割分担

画面構成、タスクデータ、データベース操作、イベント処理をそれぞれのクラスに分けました。

また、`Task`クラスではフィールドを`private`にし、getter・setterを使用してデータを管理しています。

### 2. データベースによる永続化

画面上の表示だけでなく、タスクの追加・編集・削除・完了状態をSQLiteへ保存する仕組みを実装しました。

アプリの再起動時にはDBからタスク情報を取得し、保存されている状態に応じて画面へ表示します。

### 3. DB更新失敗時のエラー処理

当初は、DBの更新に失敗しても画面側の表示が変更される可能性がありました。

そこで、DBの更新・削除処理の結果を`boolean`型で返し、成功した場合のみ画面に変更を反映するよう改善しました。

編集処理や完了状態の更新に失敗した場合は、変更前の値に戻すことで、画面上の状態と保存データの不一致を防ぐようにしています。

### 4. Mavenによるビルド管理

Mavenを導入し、SQLite JDBCなどの依存ライブラリを`pom.xml`で管理しました。

また、Maven Shade Pluginを使用し、依存ライブラリを含んだJARを作成できるようにしました。

## 今後の改善案


- タスクの期限順ソート・検索機能
- カレンダー追加・リンク
- DB初期化・読み込み失敗時のエラー処理改善
- Spring Bootを使用したWebアプリケーションへの発展
