import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static org.apache.poi.ss.usermodel.CellType.*;

public class MainClass {
    public static void main(String[] args) throws IOException {
        final String NAMEFILEFIRST;
        final String NAMEFILESECOND;
        final String NAMEFIRSTFILEONE = "Материал";
        final String NAMESECONDFILEONE = "Сумма в ВВ";
        final String NAMEFIRSTFILETWO ="Материал";
        final String NAMESECONDFILETWO ="ОбщСтоим(ср)";
        double sumOne =0;
        double sumTwo =0;
        ArrayList<Double> [] arrayFirst = new ArrayList[2];
        ArrayList<Double> [] arraySecond = new ArrayList[2];
        System.out.println("Введите путь к файлу Отчет для сверки");
        Scanner in = new Scanner(System.in);
        NAMEFILEFIRST = in.nextLine();
        System.out.println("Введите путь к файлу Отчет по остаткам");
        NAMEFILESECOND = in.nextLine();
        arrayFirst = getDataTable(NAMEFILEFIRST,NAMEFIRSTFILEONE, NAMESECONDFILEONE);
        arraySecond = getDataTable(NAMEFILESECOND,NAMEFIRSTFILETWO, NAMESECONDFILETWO);

        for (int i = 0; i < arrayFirst[1].size(); i++) {
            sumOne += arrayFirst[1].get(i);
        }
        for (int i = 0; i < arraySecond[1].size(); i++) {
            sumTwo += arraySecond[1].get(i);
        }
        System.out.println(sumOne + "  " +sumTwo);


    }
  public static  ArrayList<Double> [] getDataTable(String nameFile,String nameFirst, String nameSecond) throws IOException {
      int counterFirst = -1;
      int counterSecond = -1;
      int count;
      InputStream in = new FileInputStream(nameFile);
      XSSFWorkbook workbook = new XSSFWorkbook(in);
      XSSFSheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();
      ArrayList<Double> list1 = new ArrayList<Double>();
      ArrayList<Double> list2= new ArrayList<Double>();
      boolean flag =false;

      while (rowIterator.hasNext()) {
          Row row = rowIterator.next();
          // Get iterator to all cells of current row
          Iterator<Cell> cellIterator = row.cellIterator();
          count = 0;
          while (cellIterator.hasNext()) {
              Cell cell = cellIterator.next();
              CellType cellType = cell.getCellType();
              CellStyle cellStyle = cell.getCellStyle();
              Color color = cellStyle.getFillForegroundColorColor();
              if (!flag) {
                  if (cellType == STRING && cell.getStringCellValue().equals(nameFirst)) {
                      counterFirst = count;
                  } else if (cellType == STRING && cell.getStringCellValue().equals(nameSecond)) {
                      counterSecond = count;

                  }
                  if (counterFirst != -1 && counterSecond != -1) {
                      flag = true;
                      break;
                  }
              }else {
                      if (counterFirst == count && color != null && cellType == STRING) {
                          if (cell.getStringCellValue().length() != 0){
                              list1.add(Double.parseDouble(cell.getStringCellValue()));
                          } else{
                                break;
                          }
                      }
                        else if (counterSecond == count && color != null && cellType == NUMERIC) {
                              list2.add(cell.getNumericCellValue());
                          }
                  }
              count++;
          }
          }
      ArrayList<Double>[] arrayToReturn = new ArrayList[2];
      arrayToReturn[0] = list1;
      arrayToReturn[1] = list2;

      return arrayToReturn;
    }
}
