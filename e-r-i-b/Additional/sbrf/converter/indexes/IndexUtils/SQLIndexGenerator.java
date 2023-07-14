package IndexUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Создает Sql запрос на основе старого и нового запросов. 
 * Применяется для ТОЛЬКО для генерации индексов.
 * 
 * @author bogdanov
 * @ created 7.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class SQLIndexGenerator {

   private Scanner in;
   /**
    * Консоль.
    */
   private PrintWriter out;
   /**
    * Открытый в текущий момент времени файл.
    */
   private InputStream inFile;
   /**
    * Обрабатываемый в текущий момент времени символ из файла.
    */
   private int ch;

   /**
    * Информация о таблице.
    */
   private class TableInfo {

      /**
       * Название таблицы.
       */
      final String tableName;
      /**
       * Имеется ли модификатор UNIQUE.
       */
      final boolean isUnique;
      /**
       * Список столбцов.
       */
      final ArrayList<String> columns;

      /**
       * Создает данные о таблице.
       * 
       * @param tableName имя таблицы.
       * @param columns столбцы.
       * @param isUnique есть ли модификатор UNIQUE.
       */
      public TableInfo(String tableName, ArrayList<String> columns, boolean isUnique) {
         this.tableName = tableName;
         this.columns = columns;
         this.isUnique = isUnique;
      }

      @Override
      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         }
         if (getClass() != obj.getClass()) {
            return false;
         }
         final TableInfo other = (TableInfo) obj;
         if ((this.tableName == null) ? (other.tableName != null) : !this.tableName.equals(other.tableName)) {
            return false;
         }
         if (this.columns != other.columns && (this.columns == null || !this.columns.equals(other.columns))) {
            return false;
         }
         return true;
      }

      @Override
      public int hashCode() {
         int hash = 7;
         hash = 13 * hash + (this.tableName != null ? this.tableName.hashCode() : 0);
         hash = 17 * hash + (this.columns != null ? this.columns.hashCode() : 0);
         return hash;
      }

      @Override
      public String toString() {
         return "TableInfo{" + "tableName=" + tableName + ", isUnique=" + isUnique + ", columns=" + columns + '}';
      }
   }
   /**
    * Мап из индексов, определенных в старом файле.
    */
   private HashMap<TableInfo, String> oldIndexies = new HashMap<TableInfo, String>();
   /**
    * Мап из индексов, определенных в новом файле.
    */
   private HashMap<TableInfo, String> newIndexies = new HashMap<TableInfo, String>();
   private HashMap<String, Integer> ununicalIndeciex = new HashMap<String, Integer>();

   /**
    * исключение: конец файла.
    */
   private class FileEndException extends RuntimeException {
   }

   /**
    * Читает следующий символ.
    */
   private void nextChar() {
      try {
         if (inFile.available() > 0) {
            ch = inFile.read();
            System.out.print((char) ch);
         } else {
            throw new FileEndException();
         }
      } catch (IOException ex) {
         throw new FileEndException();
      }
   }
   /**
    * Текущий идентификатор.
    */
   private String name;

   /**
    * Является ли символ буковй.
    * 
    * @param ch символ.
    * @return  буква ли.
    */
   private boolean isLetter(int ch) {
      return ('A' <= ch && ch <= 'Z') || ('a' <= ch && ch <= 'z') || ch == '_';
   }

   /**
    * Является ли символ числом.
    * 
    * @param ch символ.
    * @return число ли.
    */
   private boolean isDigit(int ch) {
      return '0' <= ch && ch <= '9';
   }

   /**
    * Читает следующий идентификтор, если это возможно.
    */
   private void identifire() {
      cutSpaces();
      if (isLetter(ch)) {
         name = "" + (char) ch;
         nextChar();
      }
      while (isDigit(ch) || isLetter(ch)) {
         name += (char) ch;
         nextChar();
      }
   }

   /**
    * Сравнивает две строки на равенство. 
    * Предварительно происходит преобразование в верхний регистр.
    * 
    * @param s1 1 строка.
    * @param s2 2 строка.
    * @return равны ли.
    */
   private boolean eq(String s1, String s2) {
      return s1.toUpperCase().equals(s2.toUpperCase());
   }

   /**
    * Читает следующую лексему, если это возможно.
    * 
    * @param view  вид лексемы.
    */
   private void lexem(String view) {
      cutSpaces();
      name = "";
      for (int i = 0; i < view.length(); i++) {
         if (ch == view.charAt(i)) {
            name += (char) ch;
            nextChar();
         } else {
            throw new RuntimeException("ожидается " + view);
         }
      }
   }

   /**
    * Пропускает пробелы.
    */
   private void cutSpaces() {
      while (ch <= ' ') {
         nextChar();
      }
   }

   /**
    * Читает колонки для создания индекса.
    * 
    * @return прочитанные колонки.
    */
   private ArrayList<String> readFields() {
      ArrayList<String> al = new ArrayList<String>();

      lexem("(");

      cutSpaces();
      while (isLetter(ch)) {
         identifire();
         al.add(name);
         cutSpaces();
         if (isLetter(ch)) {
            identifire();
         }
         cutSpaces();
         if (ch == ',') {
            nextChar();
            cutSpaces();
         }
      }

      lexem(")");
      return al;
   }

   /**
    * Переходит к следующему вхождению CREATE в файле.
    * 
    * @param indexies мап для заполнения.
    * @throws IOException если ошибка ввода вывода.
    */
   private void goToNextCreate(HashMap<TableInfo, String> indexies) throws IOException {
      while (ch != 'c') {
         nextChar();
      }
      boolean unique = false;

      identifire();
      if (!eq(name, "create")) {
         return;
      }

      identifire();
      if (eq(name, "unique")) {
         unique = true;
         identifire();
      }

      if (!eq(name, "index")) {
         return;
      }

      cutSpaces();
      if (ch == '"') {
         name = "\"";
         nextChar();
         while (ch != '"') {
            name += (char) ch;
            nextChar();
         }
         nextChar();
         name += "\"";
      } else {
         identifire();
      }
      String indexName = name;
      if (indexies == newIndexies) {
         if (ununicalIndeciex.containsKey(name)) {
            ununicalIndeciex.put(name, ununicalIndeciex.get(name) + 1);
         } else {
            ununicalIndeciex.put(name, 0);
         }
      }
      identifire();

      if (!eq(name, "on")) {
         throw new RuntimeException("ожидается on");
      }
      identifire();
      String tableName = name;

      ArrayList<String> fields = readFields();

      TableInfo ti = new TableInfo(tableName, fields, unique);
      if (indexies.containsKey(ti)) {
         //throw new RuntimeException("повторяющиеся параметры таблицы: " + ti);
         out.println("повторяющиеся параметры таблицы: " + ti);
         return;
      }
      indexies.put(ti, indexName);
   }

   /**
    * Запускает соединитель.
    * 
    * @param arg 
    */
   public static void main(String[] arg) {
      new SQLIndexGenerator().run();
   }

   /**
    * Производит поиск всех индексов в файле.
    * 
    * @param indexies мап для хранения индексов.
    * @param fileName имя файла, в котором необходимо найти индексы.
    * @throws IOException
    */
   private void findAllIndexies(HashMap<TableInfo, String> indexies, String fileName) throws IOException {
      inFile = new FileInputStream(new File(fileName));

      try {
         nextChar();
         while (true) {
            goToNextCreate(indexies);
            out.flush();
         }
      } catch (FileEndException ex) {
      } finally {
         inFile.close();
      }
   }

   /**
    * Генерирует DROP INDEX [name].
    * 
    * @param out выходной поток.
    * @param name имя индекса.
    * @throws IOException 
    */
   private void generateDropIndex(OutputStream out, String name) throws IOException {
      out.write(("DROP INDEX " + name + "\r\ngo\r\n").getBytes());
   }

   /**
    * Генерирует ALTER INDEX [oldName] RENAME TO [newName].
    * 
    * @param out выходной поток.
    * @param oldName что переименовать.
    * @param newName во что переименовать.
    * @throws IOException 
    */
   private void generateAlterIndex(OutputStream out, String oldName, String newName) throws IOException {
      out.write(("ALTER INDEX " + oldName + " RENAME TO " + newName + "\r\ngo\r\n").getBytes());
   }

   /**
    * Генерирует CREATE INDEX [name] ON [tableName] ( ... ).
    * 
    * @param out выходной поток.
    * @param info информация о таблице.
    * @param name имя индекса для создания.
    * @throws IOException 
    */
   private void generateCreateIndex(OutputStream out, TableInfo info, String name) throws IOException {
      String unique = info.isUnique ? "UNIQUE" : "";
      out.write(("CREATE " + unique + " INDEX " + name + " ON " + info.tableName + " (\r\n").getBytes());
      boolean first = true;
      for (String column : info.columns) {
         if (!first) {
            out.write(",\r\n".getBytes());
         }
         first = false;
         out.write(("   " + column + " ASC").getBytes());
      }
      out.write(("\r\n)\r\ngo\r\n").getBytes());
   }

   /**
    * Возвращает длину названия индекса. Убирает кавычки в начале и в конце, если они есть.
    * 
    * @param indexName название индекса.
    * @return длина индекса.
    */
   private int getIndexNameLength(String indexName) {
      int len = indexName.length();
      if (indexName.charAt(0) == '"' && indexName.charAt(len - 1) == '"') {
         return len - 2;
      }
      return len;
   }

   /**
    * Создает SQl сценарий.
    * 
    * Создаение происходит следующим образом: проверяем все старые индексы (
    * если индекс изменился, то делаем rename, если индекс перестал существовать, то --- drop,
    * если старое название больше 30 символов, то создаем индекс, иначе ничего не предпринимаем)
    * Затем выполняем create для всех новых индексов.
    * 
    * @param fileName имя выходного файла.
    * @throws IOException
    */
   private void writeNewSql(String fileName) throws IOException {
      OutputStream outFile = new FileOutputStream(new File(fileName));

      try {
         for (TableInfo ti : oldIndexies.keySet()) {
            out.print("Генерация индекса для " + ti.tableName + ": ");
            String oldName = oldIndexies.get(ti);
            String newName = newIndexies.get(ti);
            out.println("old = " + oldName + " new = " + newName);
            newIndexies.remove(ti);
            if (newName == null || getIndexNameLength(newName) > 30) {
               generateDropIndex(outFile, oldName);
               continue;
            }
            if (!eq(oldName, newName) && getIndexNameLength(oldName) <= 30) {
               generateAlterIndex(outFile, oldName, newName);
            } else if (getIndexNameLength(oldName) > 30 && getIndexNameLength(newName) <= 30) {
               generateCreateIndex(outFile, ti, newName);
            } else if (getIndexNameLength(newName) > 30) {
               throw new RuntimeException("Не возможно добавить индекс. И сторое, и новое имя имеют длину больше 30 символов");
            }
         }

         for (TableInfo ti : newIndexies.keySet()) {
            generateCreateIndex(outFile, ti, newIndexies.get(ti));
         }

         outFile.flush();
      } finally {
         outFile.close();
      }
   }

   /**
    * Создает SQL скрипт на основе двух других. для генерации индексов.
    * Созданный программой файл находится в директории старого скрипта.
    */
   private void run() {
      out = new PrintWriter(System.out, false);
      in = new Scanner(System.in);

      out.println("Выберите как производить генерацию:");
      out.println("1 - Из старого файла при использовании нового");
      out.println("2 - Из нового файла при использовании старого");
      out.flush();
      try {
         int i = in.nextInt();

         out.println("old file name:");
         String oldFileName = "D:\\work\\PhizIC - Oracle.sql";//in.nextLine();
         out.println("new file name:");
         String newFileName = "D:\\work\\v1.18\\docs\\models\\PhizIC - Oracle.sql";//in.nextLine();
         out.flush();
         if (i == 1) {
            findAllIndexies(oldIndexies, oldFileName);
            findAllIndexies(newIndexies, newFileName);
         } else {
            findAllIndexies(oldIndexies, newFileName);
            findAllIndexies(newIndexies, oldFileName);
         }

         writeNewSql(oldFileName + ".generated.sql");

         out.println("Неуникальные имена индексов");
         for (String name : ununicalIndeciex.keySet()) {
            if (ununicalIndeciex.get(name) > 0) {
               out.println(name);
            }
         }
      } catch (Exception ex) {
         out.println();
         out.println(ex);
         ex.printStackTrace(out);
      } finally {
         in.close();
         out.flush();
         out.close();
      }
   }
}
