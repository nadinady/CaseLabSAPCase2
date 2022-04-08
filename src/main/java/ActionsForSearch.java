import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class ActionsForSearch {
    public static ArrayList<Double>[] getDataTable(String nameFile, String nameFirst, String nameSecond) throws IOException {
        int counterFirst = -1;
        int counterSecond = -1;
        int count;
        InputStream in = new FileInputStream(nameFile);
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        ArrayList<Double> list1 = new ArrayList<Double>();
        ArrayList<Double> list2 = new ArrayList<Double>();
        boolean flag = false;

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
                } else {
                    if (counterFirst == count && color != null && cellType == STRING) {
                        if (cell.getStringCellValue().length() != 0) {
                            list1.add(Double.parseDouble(cell.getStringCellValue()));
                        } else {
                            break;
                        }
                    } else if (counterSecond == count && color != null && cellType == NUMERIC) {
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

    public static void isNewMaterial(ArrayList<Double>[] arrayFirst, ArrayList<Double>[] arraySecond, String message) {
        boolean isThisMaterial;
        for (int i = 0; i < arraySecond[0].size(); i++) {
            isThisMaterial = false;
            if (arraySecond[1].get(i) != 0) {
                for (int j = 0; j < arrayFirst[0].size(); j++) {
                    if (arrayFirst[0].get(j).equals(arraySecond[0].get(i))) {
                        isThisMaterial = true;
                        break;
                    }
                }
                if (isThisMaterial == false) {
                    System.out.println("нет материала " + String.format("%.0f", arraySecond[0].get(i)) + " " + message);
                }
            }


        }
    }

    public static void searchSumError(ArrayList<Double>[] arrayFirst, ArrayList<Double>[] arraySecond) {
        for (int i = 0; i < arrayFirst[0].size(); i++)
            for (int j = 0; j < arraySecond[0].size(); j++) {
                if (arrayFirst[0].get(i).equals(arraySecond[0].get(j)) && arrayFirst[1].get(i) - arraySecond[1].get(j) != 0) {
                    System.out.println("Разница по материалу " + String.format("%.0f", arraySecond[0].get(i)) +" на " + (arrayFirst[1].get(i) - arraySecond[1].get(j)));
                }
            }

    }
}
