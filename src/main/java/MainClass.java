
import java.util.ArrayList;
import java.util.Scanner;


public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        final String NAMEFILEFIRST;
        final String NAMEFILESECOND;
        final String NAMEFIRSTFILEONE = "Материал";
        final String NAMESECONDFILEONE = "Сумма в ВВ";
        final String NAMEFIRSTFILETWO = "Материал";
        final String NAMESECONDFILETWO = "ОбщСтоим(ср)";
        ArrayList<Double>[] arrayFirst;
        ArrayList<Double>[] arraySecond;
        try {
            System.out.println("Введите путь к файлу Отчет для сверки (с указанием имени и расширения файла)");
            Scanner in = new Scanner(System.in, "cp866");
// При вводе данных с консоли в IDE кодировка должна быть Cp866 (DOS 866), иначе не будет поддерживаться русский ввод, или необходимо убрать параметр Cp866 в функции
            NAMEFILEFIRST = in.nextLine();
            System.out.println("Введите путь к файлу Отчет по остаткам (с указанием имени и расширения файла)");
            NAMEFILESECOND = in.nextLine();
            arrayFirst = ActionsForSearch.getDataTable(NAMEFILEFIRST, NAMEFIRSTFILEONE, NAMESECONDFILEONE);
            arraySecond = ActionsForSearch.getDataTable(NAMEFILESECOND, NAMEFIRSTFILETWO, NAMESECONDFILETWO);
            ActionsForSearch.isNewMaterial(arrayFirst, arraySecond, "в отчете о сверке");
            ActionsForSearch.isNewMaterial(arraySecond, arrayFirst, "в отчете об остатках");
            ActionsForSearch.searchSumError(arrayFirst, arraySecond);
            Thread.sleep(5000);
        }
        catch (Exception e) {
            System.out.println("Введены неверные данные");
        }
        Thread.sleep(5000);

    }


}
